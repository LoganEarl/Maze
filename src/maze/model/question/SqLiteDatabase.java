package maze.model.question;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SqLiteDatabase implements MazeDatabase {

    public static final String QUESTION_TABLE = "CREATE TABLE IF NOT EXISTS \"question\" (\r\n" +
            "	\"id\"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" +
            "	\"question\"	TEXT NOT NULL,\r\n" +
            "	\"type\"	INTEGER NOT NULL DEFAULT 0,\r\n" +
            "	\"keywords\"	TEXT DEFAULT 0\r\n" +
            ");";

    public static final String ANSWER_TABLE = "CREATE TABLE IF NOT EXISTS \"answer\" (\r\n" +
            "	\"id\"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" +
            "	\"question_id\"	INTEGER NOT NULL,\r\n" +
            "	\"answer\"	TEXT NOT NULL,\r\n" +
            "	\"correct\"	INTEGER NOT NULL\r\n" +
            ");";

    public String fullFileName;
    public final Object lock = new Object();


    public SqLiteDatabase(String fullFileName) {
        this.fullFileName = fullFileName;

        System.out.println(fullFileName);

        synchronized (lock) {
            Connection conn = null;

            try {
                conn = this.openConnection();
                conn.createStatement().execute(QUESTION_TABLE);
                conn.createStatement().execute(ANSWER_TABLE);

            } catch (Exception e) {
                exceptionHandling(e);
            } finally {
                closeConnection(conn);
            }

        }
    }

    boolean questionExists(Connection conn, String question) throws SQLException {
        String sql = "SELECT * FROM question WHERE question=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, question);

        ResultSet rs = pstmt.executeQuery();

        return rs.next();
    }

    public boolean insert(Question q) {
        synchronized (lock) {
            Connection conn = null;

            try {
                conn = this.openConnection();

                if (questionExists(conn, q.getQuestion()))
                    return false;

                String keywords = "";

                if (q.getKeywords().size() > 0)
                    keywords = String.join(",", q.getKeywords());

                String sql = "INSERT INTO question(question, type, keywords) VALUES(?, ?, ?)";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, q.getQuestion());
                pstmt.setInt(2, q.getType().getValue());
                pstmt.setString(3, keywords);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = (int) rs.getLong(1);

                    for (MazeAnswer a : q.getAnswers()) {
                        sql = "INSERT INTO answer (question_id, answer, correct) VALUES(?, ?, ?)";

                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, q.getId());
                        pstmt.setString(2, a.answer);
                        pstmt.setInt(3, a.correct ? 1 : 0);
                        pstmt.executeUpdate();

                        rs = pstmt.getGeneratedKeys();

                        if (rs.next()) {
                            a.id = (int) rs.getLong(1);
                        }
                    }
                }

                return true;
            } catch (Exception e) {
                exceptionHandling(e);
            } finally {
                closeConnection(conn);
            }

        }

        return false;
    }

    public void exceptionHandling(Exception e) {
        System.out.println(e.getMessage());
    }

    public boolean delete(int questionId) {
        synchronized (lock) {
            Connection conn = null;

            try {
                conn = this.openConnection();
            } catch (Exception e) {
                exceptionHandling(e);
            } finally {
                closeConnection(conn);
            }

            return false;
        }

    }

    public boolean update(Question q) {
        synchronized (lock) {
            Connection conn = null;

            try {
                conn = this.openConnection();
            } catch (Exception e) {
                exceptionHandling(e);
            } finally {
                closeConnection(conn);
            }

        }
        //todo
        return false;
    }

    public static void createNewTable(Connection conn, String sql) throws SQLException {
        conn.createStatement().execute(sql);
    }

    @Override
    public Question getNextQuestion(QuestionType questionType) {
        return null;
    }

    public QuestionType toQuestionType(int i) throws Exception {
        switch (i) {
            case 0:
                return QuestionType.MULTIPLE;
            case 1:
                return QuestionType.TRUE_FALSE;
            case 2:
                return QuestionType.SHORT;
        }

        throw new Exception("Not suppoerted Question Type: " + i);
    }

    @Override
    public List<Question> readAllRecords() {
        List<Question> list = new ArrayList<Question>();

        synchronized (lock) {
            Connection conn = null;

            try {
                conn = this.openConnection();
                String sql = "SELECT t1.id, t1.question, t1.type, t1.keywords, t2.id as aid, t2.answer, t2.correct " +
                        "FROM \"question\" t1, \"answer\" t2 " +
                        "WHERE t1.id = t2.question_id";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                int curId = 0;
                MazeQuestion q = null;

                while (rs.next()) {


                    int id = rs.getInt("id");
                    String question = rs.getString("question");
                    int type = rs.getInt("type");
                    String keywords = rs.getString("keywords");
                    int answerId = rs.getInt("aid");
                    String answer = rs.getString("answer");
                    int correct = rs.getInt("correct");

                    if (curId != id) {
                        if (q != null) {
                            list.add(q);
                        }

                        curId = id;
                        q = new MazeQuestion();
                    }

                    q.id = id;
                    q.question = question;
                    q.type = toQuestionType(type);
                    if (keywords.length() > 0) {
                        String[] parts = keywords.split(",");

                        for (String part : parts) {
                            q.keywords.add(part);
                        }
                    }
                    q.answers.add(new MazeAnswer(answerId, answer, correct != 0));

                }

            } catch (Exception e) {
                exceptionHandling(e);
            } finally {
                closeConnection(conn);
            }

        }
        return list;

    }

    private static String getURL(String fileName) {
        return "jdbc:sqlite:" + fileName;
    }

    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = getURL(fullFileName);


        Connection conn = null;
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            // System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


