package com.twitter.models;

import java.util.LinkedList;

import javax.sql.DataSource;

import java.sql.*;

import com.twitter.lib.*;
import com.twitter.stores.TweetStore;
public class TweetModel {
	
	
	private DataSource _ds = null;
 
public TweetModel(){

}


public void setDatasource(DataSource _ds){
	this._ds=_ds;
	System.out.println("Set Data Source in Model"+_ds.toString());
}

//Adds tweet
public void insertTweet(String tweet, String user)
{
	 
	Connection Conn = null;
	Statement stmt;
	Integer uid = Integer.valueOf(UserId(user));
	PreparedStatement pstmt = null;
	
	
	String add = "INSERT INTO tweets (tweet, user) VALUE (?,?);";
	
	try {
		Conn = _ds.getConnection();
	} catch (Exception et) {
		System.out.println("No Connection in Tweets Model");
		
	}
	
	try{
		//stmt = Conn.createStatement();
		
		pstmt = Conn.prepareStatement(add);
		pstmt.setString(1, tweet);
		pstmt.setInt(2, uid);

		
		pstmt.executeUpdate();
	    //stmt.executeUpdate(add);
	}catch(Exception e){

	System.out.println(e.toString());
	 
	}
	
	try {
		Conn.close();
	} catch (Exception ex) {
		System.out.println("Unable to close. Error: " + ex);
	}
	
}

//Gets user id
	public String UserId(String user)

	{
		String uid = null;
		
		Connection Conn;
		TweetStore ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			return null;
		}
		
		
		
		String sqlQuery = "SELECT * FROM users WHERE username=?;";
		
		System.out.println("Tweets Query" + sqlQuery);
		try {
			/*
			try {
					stmt = Conn.createStatement();
			} catch (Exception et) {
				System.out.println("Can't create preare statement");
				return null;
			}
			System.out.println("Created prepare");
			*/
			try
			{
				pstmt = Conn.prepareStatement(sqlQuery);
				pstmt.setString(1, user);
			}
			catch(Exception e)
			{
				System.out.println("Can't create prepare statement");
				return null;
			}
			try {
				//rs=stmt.executeQuery(sqlQuery);
				rs = pstmt.executeQuery();	
				
			} catch(Exception et) {
				System.out.println("Can not execut query " + et);
				return null;
		
			}
			System.out.println("Statement executed");
			if (rs.wasNull()) {
			System.out.println("result set was null");
			} else {
			System.out.println("Well it wasn't null");
			}
			while (rs.next()) {
			
				System.out.println("Getting RS");
				ps = new TweetStore();
			
				ps.setUser(rs.getString("idUsers"));
				ps.setUsername(rs.getString("username"));
				
				uid = ps.getUser();
				
			}
		} catch (Exception ex) {
			System.out.println("Oops, error in query" + ex);
			return null;
		}
		try {
			Conn.close();
		} catch (Exception ex) {
			return null;
		}
		System.out.println(uid);
		
		return uid;
	}

	
	//Get tweets of the following user
	public LinkedList<TweetStore> getTweets(String loguser) 

	{
		
		LinkedList<TweetStore> tweetList = new LinkedList<TweetStore>();
		Connection Conn;
		TweetStore ps = null;
		ResultSet rs = null;
		LinkedList<String> Addid = new LinkedList<String>();
		
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			return null;
		}
		
		Statement stmt = null;
		//String sqlQuery = "select t1.*,t2.username from tweets AS t1 INNER JOIN users AS t2 ON t1.user = t2.idUsers ORDER by idTweets DESC";
		
		String sqlQuery = "select t1.*,t2.username FROM following AS t1 INNER JOIN Users AS t2 ON t1.flwinguserid = t2.idUsers WHERE username=?";// ORDER by idTweets DESC";
		
		System.out.println("Tweets Query" + sqlQuery);
		try {
			try {
					//stmt = Conn.createStatement();
			} catch (Exception et) {
				System.out.println("Can't create preare statement");
				return null;
			}
			System.out.println("Created prepare");
			try {
				 PreparedStatement prepStmt = Conn.prepareStatement(sqlQuery);
			     prepStmt.setString(1, loguser);
			     rs = prepStmt.executeQuery();
				//rs=stmt.executeQuery(sqlQuery);
						
			} catch(Exception et) {
				System.out.println("Can not execut query " + et);
				return null;
		
			}
			System.out.println("Statement executed");
			if (rs.wasNull()) {
			System.out.println("result set was null");
			} else {
			System.out.println("Well it wasn't null");
			}
			while (rs.next()) {
				Addid.add(rs.getString("followuserid"));
				Addid.add(rs.getString("flwinguserid"));
	
			}
		} catch (Exception ex) {
			System.out.println("Oops, error in query" + ex);
			return null;
		}

		
		//////////
		
		
		
		
		String sqlQuery2 = "SELECT t1.*,t2.username from Tweets AS t1 INNER JOIN Users AS t2 ON t1.user = t2.idUsers ORDER by idTweets DESC;";
		System.out.println("Tweets Query" + sqlQuery2);
		try {
			try {
					stmt = Conn.createStatement();
			} catch (Exception et) {
				System.out.println("Can't create prepare statement");
				return null;
			}
			System.out.println("Created prepare");
			try {
				rs=stmt.executeQuery(sqlQuery2);
						
			} catch(Exception et) {
				System.out.println("Can not execut query " + et);
				return null;
		
			}
			System.out.println("Statement executed");
			if (rs.wasNull()) {
			System.out.println("result set was null");
			} else {
			System.out.println("Well it wasn't null");
			}
			while (rs.next()) {
				
				if(Addid.contains((String)rs.getString("user")))
				{
					System.out.println("Getting RS");
					ps = new TweetStore();
					ps.setTweetid(rs.getInt("idTweets"));
					ps.setTweet(rs.getString("tweet"));
					ps.setUser(rs.getString("user"));
					ps.setUsername(rs.getString("username"));
					ps.setTime(rs.getString("time"));
					tweetList.add(ps);
					System.out.println(ps.getUser());
				}
			}
		} catch (Exception ex) {
			System.out.println("Oops, error in query" + ex);
			return null;
		}
		try {
			Conn.close();
		} catch (Exception ex) {
			return null;
		}
		
		return tweetList;
		
	}

	
	//Gets all tweets
	public LinkedList<TweetStore> getAllTweets() 
	{
		
		LinkedList<TweetStore> tweetList = new LinkedList<TweetStore>();
		Connection Conn;
		TweetStore ps = null;
		ResultSet rs = null;
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			return null;
		}
		
		PreparedStatement pmst = null;
		Statement stmt = null;
	
		String sqlQuery = "select t1.*,t2.username from Tweets AS t1 INNER JOIN Users AS t2 ON t1.user = t2.idUsers ORDER by idTweets DESC;";
		
		System.out.println("Tweets Query" + sqlQuery);
		try {
			try {
					stmt = Conn.createStatement();
			} catch (Exception et) {
				System.out.println("Can't create preare statement");
				return null;
			}
			System.out.println("Created prepare");
			try {
				rs=stmt.executeQuery(sqlQuery);
						
			} catch(Exception et) {
				System.out.println("Can not execut query " + et);
				return null;
		
			}
			System.out.println("Statement executed");
			if (rs.wasNull()) {
			System.out.println("result set was null");
			} else {
			System.out.println("Well it wasn't null");
			}
			while (rs.next()) {
				System.out.println("Getting RS");
				ps = new TweetStore();
				ps.setTweetid(rs.getInt("idTweets"));
				ps.setTweet(rs.getString("tweet"));
				ps.setUser(rs.getString("user"));
				ps.setUsername(rs.getString("username"));
				ps.setTime(rs.getString("time"));
				tweetList.add(ps);
			}
		} catch (Exception ex) {
			System.out.println("Oops, error in query" + ex);
			return null;
		}
		try {
			Conn.close();
		} catch (Exception ex) {
			return null;
		}
		
		//////////
	
		
		return tweetList;
		
	}

}