package utils;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatabaseManager {
    public static final String DATA_DIRECTORY = System.getProperty("user.dir").replace("\\", "/") + "/data/";

    private static Map<String, Connection> databaseConnections = new HashMap<>();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createDirectories() {
        File f = new File(DATA_DIRECTORY);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.mkdirs();
        }
    }

    private static void createDatabase(Connection conn){
       try {
           if (conn != null) {
               DatabaseMetaData meta = conn.getMetaData();
               System.out.println("The driver name is " + meta.getDriverName());
           }
       }catch (SQLException e){
           e.printStackTrace();
       }
    }

    public static void createDatabase(String fileName) {
        createDatabase(getDatabaseConnection(fileName));
        closeDatabaseConnection(fileName);
    }

    public static void closeDatabaseConnection(String fileName){
        String url = getDatabaseConnectionURL(fileName);
        if(databaseConnections.containsKey(url)) {
            Connection c = databaseConnections.get(url);
            try {
                if (!c.isClosed()) {
                    c.close();
                    databaseConnections.remove(url);
                }
            }catch (SQLException ignored){
            }
        }
    }

    public static Connection getDatabaseConnection(String fileName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }

        String url = getDatabaseConnectionURL(fileName);

        if(databaseConnections.containsKey(url)) {
            Connection c = databaseConnections.get(url);
            try {
                if (!c.isClosed())
                    return c;
                else
                    databaseConnections.remove(url);
            }catch (SQLException e){
                databaseConnections.remove(url);
            }
        }

        try {
            Connection c = DriverManager.getConnection(url);
            if(c != null)
                databaseConnections.put(url,c);
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createTables(Connection conn, List<DatabaseTable> tables){
        String curTableName = "";
        try{
            Statement stmt = conn.createStatement();

            for (DatabaseTable table : tables) {
                curTableName = table.getTableName();
                StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(table.getTableName()).append(" (");

                Map<String, String> columnDefinitions = table.getColumnDefinitions();
                boolean first = true;
                for (String columnName : columnDefinitions.keySet()) {
                    if (!first)
                        sql.append(",");
                    else
                        first = false;
                    sql.append(columnName).append(" ").append(columnDefinitions.get(columnName));
                }
                if (table.getConstraints() != null)
                    for (String constraint : table.getConstraints())
                        sql.append(",").append(constraint);


                sql.append(")");
                stmt.executeUpdate(sql.toString());
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Failed to create tables, table: " + curTableName);
            e.printStackTrace();
        }
    }

    public static void createTables(String fileName, List<DatabaseTable> tables) {
        createTables(getDatabaseConnection(fileName),tables);
    }

    private static String getDatabaseConnectionURL(String fileName) {
        return "jdbc:sqlite:" + DATA_DIRECTORY + fileName;
    }

    public interface DatabaseTable {
        String getTableName();

        Map<String, String> getColumnDefinitions();

        Set<String> getConstraints();
    }

    public static int executeStatement(String sql, String databaseName, Object... args) {
        try {
            Connection c = DatabaseManager.getDatabaseConnection(databaseName);
            if (c == null)
                return -1;
            PreparedStatement statement = c.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Integer)
                    statement.setInt(i + 1, (Integer) args[i]);
                else if (args[i] instanceof String)
                    statement.setString(i + 1, (String) args[i]);
                else if(args[i] instanceof  Double)
                    statement.setDouble(i+1, (Double)args[i]);
                else if(args[i] instanceof  Float)
                    statement.setDouble(i+1, (Float)args[i]);
                else if(args[i] instanceof  Long)
                    statement.setLong(i+1, (Long)args[i]);
                else if(args[i] == null)
                    statement.setNull(i+1,Types.VARCHAR);
            }

            int result = statement.executeUpdate();
            statement.close();
            //c.close();
            return result;
        } catch (SQLException e) {
            return -1;
        }
    }

    public interface DatabaseEntry {
        boolean removeFromDatabase(String databaseName);

        boolean updateInDatabase(String databaseName);

        boolean existsInDatabase(String databaseName);
    }
}
