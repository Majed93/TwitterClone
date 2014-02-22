package com.twitter.models;

import javax.sql.DataSource;

import java.sql.*;
import java.util.LinkedList;

import com.twitter.lib.*;
import com.twitter.stores.TweetStore;


public class UserModel {

	
	private DataSource _ds = null;
	
	public UserModel()
	{
		
	}
	public void setDatasource(DataSource _ds){
		this._ds=_ds;
		System.out.println("Set Data Source in Model"+_ds.toString());
	}
	
	//Follows user
	public void follow(String profuser, String user)
	{
		Connection Conn = null;
		Statement stmt;
		PreparedStatement pstmt = null;
		
		
		Integer newprofuser = Integer.valueOf(UserId(profuser));
		Integer newuser = Integer.valueOf(UserId(user));
		
		
		
		System.out.println("PROFILE USERid: " + newprofuser);
		System.out.println("CURRENT USERid: " + newuser);
		
		String add = "INSERT INTO Following (followuserid, flwinguserid) VALUE (?,?);";
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			
		}
		
		try{
			//stmt = Conn.createStatement();
			pstmt = Conn.prepareStatement(add);
			pstmt.setInt(1, newprofuser);
			pstmt.setInt(2, newuser);

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
	
	//Unfollow user
	public void unfollow(String profuser, String user)
	{
		Connection Conn = null;
		Statement stmt;
		PreparedStatement pstmt = null;
		
		Integer newprofuser = Integer.valueOf(UserId(profuser));
		Integer newuser = Integer.valueOf(UserId(user));
		
		
		
		System.out.println("PROFILE USERid: " + newprofuser);
		System.out.println("CURRENT USERid: " + newuser);
		
		String add = "DELETE FROM Following WHERE followuserid=? AND flwinguserid=?;";
		
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			
		}
		
		try{
			//stmt = Conn.createStatement();
			pstmt = Conn.prepareStatement(add);
			pstmt.setInt(1, newprofuser);
			pstmt.setInt(2, newuser);

			pstmt.executeUpdate();
		    //stmt.executeUpdate(add);
		       System.out.println("Unfollowed!");
		}catch(Exception e){

		System.out.println(e.toString());
		 
		}
		
		try {
			Conn.close();
		} catch (Exception ex) {
			System.out.println("Unable to close. Error: " + ex);
		}
		
		
	
	}
	
	public boolean check(String profuser, String user)
	{
		Connection Conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		boolean following;
		Integer newprofuser = Integer.valueOf(UserId(profuser));
		Integer newuser = Integer.valueOf(UserId(user));
		
		ResultSet rs = null;
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			following = false;
		}
	
		
		String sqlQuery = "SELECT * FROM Following WHERE followuserid=? AND flwinguserid=?;";
		
		System.out.println("Tweets Query " + sqlQuery);
		try {
			try {
				//stmt = Conn.createStatement();
				pstmt = Conn.prepareStatement(sqlQuery);
				pstmt.setInt(1, newprofuser);
				pstmt.setInt(2, newuser);

			} catch (Exception et) {
				System.out.println("Can't create preare statement");
				following = false;
			}
			System.out.println("Created prepare");
			try {
			
				rs = pstmt.executeQuery();
			
				//rs=stmt.executeQuery(sqlQuery);
						
			} catch(Exception et) {
				System.out.println("Can not execut query " + et);
				following = false;
		
			}
			System.out.println("Statement executed");
		
			if (rs.wasNull()) {
				
			System.out.println("result set was null");
			following = false;
			
			} else {
				
			System.out.println("Well it wasn't null");
			following = false;
			}
			while (rs.next()) {
				
				System.out.println("Getting RS");
			
				following = true;
			}
		} catch (Exception ex) {
			System.out.println("Oops, error in query" + ex);
			following = false;
		}
		try {
			Conn.close();
		} catch (Exception ex) {
			following = false;
		}
		
		
		return following;
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

	
	//Get users tweets
	public LinkedList<TweetStore> getTweets(String loguser) 

	{
	
		LinkedList<TweetStore> tweetList = new LinkedList<TweetStore>();
		Connection Conn;
		TweetStore ps = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			return null;
		}
		
		
		
		String sqlQuery = "SELECT t1.*,t2.username FROM Tweets AS t1 INNER JOIN Users AS t2 ON t1.user = t2.idUsers WHERE username='" + loguser + "' ORDER by idTweets DESC;";
		
		System.out.println("Tweets Query " + sqlQuery);
		try {
			try {
					stmt = Conn.createStatement();
					//pstmt = Conn.prepareStatement(sqlQuery);
					//pstmt.setString(1, loguser);
					
			} catch (Exception et) {
				System.out.println("Can't create preare statement " + et);
				return null;
			}
			System.out.println("Created prepare");
			try {
				rs=stmt.executeQuery(sqlQuery);
				//rs=pstmt.executeQuery(sqlQuery);	
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
