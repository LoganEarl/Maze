package maze.model.question;

import java.util.List;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class SqLiteDatabase implements MazeDatabase {
	
	public String fullFileName;
	Object lock = new Object();
	
	public SqLiteDatabase(String fullFileName)
	{
		this.fullFileName = fullFileName;
		
		System.out.println(fullFileName);
		
		synchronized(lock)
		{
			Connection conn = null;

			try
			{
				conn = this.openConnection();
			}
			catch(Exception e)
			{
				exceptionHandling(e);
			}
			finally
			{
				closeConnection(conn);
			}
			
		}
	}
	
	boolean questionExists(Connection conn, String question) throws SQLException
	{
	    String sql = "SELECT * FROM question WHERE question=?" ;
	    
        PreparedStatement pstmt  = conn.prepareStatement(sql);
        
        pstmt.setString(1, question);
        
        ResultSet rs  = pstmt.executeQuery();
        
        return rs.next();
	}
	
	public MazeQuestion add(MazeQuestion q)
	{
		synchronized(lock)
		{
			Connection conn = null;

			try
			{
				conn = this.openConnection();
				
				if(questionExists(conn, q.question))
					return null;
					
				String keywords = "";
				
				if(q.keywords.size() > 0)
					keywords = String.join(",", q.keywords);
				
			    String sql = "INSERT INTO question(question, type, keywords) VALUES(?, ?, ?)";
			        
			      PreparedStatement pstmt = conn.prepareStatement(sql);
			      pstmt.setString(1, q.question);
			      pstmt.setInt(2, q.type.getValue());
			      pstmt.setString(3, keywords);
			      pstmt.executeUpdate();
			      
			      ResultSet rs = pstmt.getGeneratedKeys();
			      if(rs.next())
			      {
		             q.id = (int) rs.getLong(1);
		             
		             for(MazeAnswer a: q.answers) {
		 			    sql = "INSERT INTO answer (question_id, answer, correct) VALUES(?, ?, ?)";
		 			    
		 			   pstmt = conn.prepareStatement(sql);
					      pstmt.setInt(1, q.id);
					      pstmt.setString(2, a.answer);
					      pstmt.setInt(3, a.correct ? 1 : 0);
					      pstmt.executeUpdate();

					      rs = pstmt.getGeneratedKeys();
					      
					      if(rs.next()) {
					    	  a.id = (int) rs.getLong(1);
					      }
		             }
			      }
			      
			      return q;
			}
			catch(Exception e)
			{
				exceptionHandling(e);
			}
			finally
			{
				closeConnection(conn);
			}
			
		}        
        
		return null;
	}
	
	public void exceptionHandling(Exception e)
	{
		System.out.println(e.getMessage());
	}
	
	public MazeQuestion remove(int questionId)
	{		
		synchronized(lock)
		{
			Connection conn = null;

			try
			{
				conn = this.openConnection();
			}
			catch(Exception e)
			{
				exceptionHandling(e);
			}
			finally
			{
				closeConnection(conn);
			}
			
			return null;
		}	
		
	}
	
	public boolean update(MazeQuestion q)
	{
		synchronized(lock)
		{
			Connection conn = null;

			try
			{
				conn = this.openConnection();
			}
			catch(Exception e)
			{
				exceptionHandling(e);
			}
			finally
			{
				closeConnection(conn);
			}
			
		}
		//todo
		return false;
	}
	
	
	@Override
	public Question getNextQuestion(QuestionType questionType) {
		return null;
	}

	@Override
	public List<Question> readAllRecords() {
		return null;
	}
	
    private static String getURL(String fileName){
        return "jdbc:sqlite:" + fileName;
    }
	
    Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }

        String url = getURL(fullFileName);

        
        Connection conn = null;
        try {
            
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    void closeConnection(Connection conn)
    {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    
    
}
