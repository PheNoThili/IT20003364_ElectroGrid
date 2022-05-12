package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;



public class Notice 
{ 
		//A common method to connect to the DB
		private Connection connect(){ 
			
						Connection con = null; 
						
						try{ 
								Class.forName("com.mysql.cj.jdbc.Driver"); 
 
								//Provide the correct details: DBServer/DBName, username, password 
								con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notice", "root", ""); 
						} 
						catch (Exception e) {
							e.printStackTrace();
							} 
						
						return con; 
			} 
		
		//View notices
		public String readNotices() 
		{ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
		 if (con == null) 
		 { 
			 return "Error while connecting to the database for reading."; 
		 } 
		 
		 // Prepare the html table to be displayed
		 output = "<table border='1' class='table'>"
					+"<th>Admin Id</th>"
			 		+ "<th>Notice Subject</th><th>Notice Body</th>"
			 		+ "<th>Published Date</th>"
			 		+ "<th>Update</th>"
			 		+ "<th>Remove</th></tr>"; 
		
		 String query = "select * from notices"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query);
		 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
			 String NoticeId = Integer.toString(rs.getInt("NoticeId")); 
			 String userId = rs.getString("userId"); 
			 String noticeSubject = rs.getString("noticeSubject"); 
			 String noticeBody = rs.getString("noticeBody"); 
			 String date = rs.getString("date"); 
		 
		 // Add into the html table
		 output += "<tr><td><input id='hidNoticeIDUpdate' name='hidNoticeIDUpdate' type='hidden' value='"+NoticeId+"'>"+userId+"</td>"; 
		 output += "<td>" + noticeSubject + "</td>"; 
		 output += "<td>" + noticeBody + "</td>"; 
		 output += "<td>" + date + "</td>"; 
		 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' "
				 + "class='btnUpdate btn btn-secondary' data-noticeid='" + NoticeId + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove' "
				 + "class='btnRemove btn btn-danger' data-noticeid='" + NoticeId + "'></td></tr>"; 
		 
		 } 
		 con.close(); 
		 
		 // Complete the html table
		 output += "</table>"; 
		 } 
		 
		catch (Exception e) 
		 { 
		 output = "Error while reading the notices."; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}
		
		//Insert Notices
		public String insertNotice(String userId, String noticeSubject, String noticeBody){ 
			
			String output = ""; 
			
			try
			{ 
				Connection con = connect(); 
				
				if (con == null) 
				{
					return "Error while connecting to the database for inserting."; 
					
				} 
				
				// create a prepared statement
				String query = "insert into notices(`NoticeId`,`userId`,`noticeSubject`,`noticeBody`,`date`)"+" values (?, ?, ?, ?, ?)"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				
				// binding values
				preparedStmt.setInt(1, 0); 
				preparedStmt.setString(2, userId); 
				preparedStmt.setString(3, noticeSubject); 
				preparedStmt.setString(4, noticeBody); 
				Date date = new Date();  
				preparedStmt.setDate(5, new java.sql.Date(date.getTime()));
				 
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				
				String newNotices = readNotices(); 
				output = "{\"status\":\"success\",\"data\":\""+newNotices+"\"}"; 
			} 
			
			catch (Exception e) 
			{ 
				output = "{\"status\":\"error\", \"data\":\"Error while inserting the notice.\"}"; 
				System.err.println(e.getMessage()); 
			} 
			return output; 
	} 
	
		
	//Update Notices	
	public String updateNotice(String  NoticeId, String userId, String noticeSubject, String noticeBody){ 
			
			String output = ""; 
				
				try{ 
						Connection con = connect(); 
						if (con == null){
							return "Error while connecting to the database for updating.";
							} 
						
						// create a prepared statement
						String query = "UPDATE notices SET userId=?,noticeSubject=?,noticeBody=?,date=? WHERE NoticeId=?";  
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						
						// binding values
						preparedStmt.setString(1, userId); 
						preparedStmt.setString(2, noticeSubject); 
					    preparedStmt.setString(3, noticeBody); 
					    Date date = new Date();  
						preparedStmt.setDate(4, new java.sql.Date(date.getTime()));
						preparedStmt.setInt(5, Integer.parseInt(NoticeId)); 
						
						// execute the statement
						preparedStmt.execute(); 
						con.close(); 
						String newNotices = readNotices(); 
						output = "{\"status\":\"success\",\"data\":\""+newNotices+"\"}"; 

				} 
				
				catch (Exception e){ 
					
					output = "{\"status\":\"error\",\"data\":\"Error while updating the notice.\"}"; 

					System.err.println(e.getMessage()); 
					
				} 
				
				return output; 
		} 
		
		//Delete Notices
		public String deleteNotice(String NoticeId){ 
		
			String output = ""; 
		
			try{ 
				Connection con = connect(); 
			
			if (con == null){
				return "Error while connecting to the database for deleting."; 
				} 
			
			// create a prepared statement
			String query ="delete from notices where NoticeId=?";  
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(NoticeId)); 
			
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newNotices = readNotices(); 
			 output = "{\"status\":\"success\",\"data\":\""+newNotices+"\"}"; 

		} 
		
		catch (Exception e){ 
			output = "{\"status\":\"error\",\"data\":\"Error while deleting the notice.\"}";
			System.err.println(e.getMessage()); 
		} 
		return output; 
} 

		
}
