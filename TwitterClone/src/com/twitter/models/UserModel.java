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
		
		Integer newprofuser = Integer.valueOf(UserId(profuser));
		Integer newuser = Integer.valueOf(UserId(user));
		
		
		
		System.out.println("PROFILE USERid: " + newprofuser);
		System.out.println("CURRENT USERid: " + newuser);
		
		String add = "INSERT INTO Following (followuserid, flwinguserid)" + " VALUE (";
		add += "'";
		add += newprofuser;
		add += "'";
		add += ",";
		add += "'";
		add += newuser;
		add += "'";
		add += ");";
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			
		}
		
		try{
			stmt = Conn.createStatement();
		       stmt.executeUpdate(add);
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
		
		Integer newprofuser = Integer.valueOf(UserId(profuser));
		Integer newuser = Integer.valueOf(UserId(user));
		
		
		
		System.out.println("PROFILE USERid: " + newprofuser);
		System.out.println("CURRENT USERid: " + newuser);
		
		String add = "DELETE FROM Following WHERE followuserid='" + newprofuser + "' AND flwinguserid='" + newuser + "'";
		
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			
		}
		
		try{
			stmt = Conn.createStatement();
		       stmt.executeUpdate(add);
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
	
		
		String sqlQuery = "SELECT * FROM Following WHERE followuserid='" + newprofuser + "' AND flwinguserid='" + newuser + "'";
		
		System.out.println("Tweets Query " + sqlQuery);
		try {
			try {
					stmt = Conn.createStatement();
			} catch (Exception et) {
				System.out.println("Can't create preare statement");
				following = false;
			}
			System.out.println("Created prepare");
			try {
				rs=stmt.executeQuery(sqlQuery);
						
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
	
	
	//Get userid
	public String UserId(String user)
	{
		String uid = null;
		
		Connection Conn;
		TweetStore ps = null;
		ResultSet rs = null;
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			return null;
		}
		
		Statement stmt = null;
		
		String sqlQuery = "SELECT * FROM Users WHERE username='" + user + "'";
		
		System.out.println("Tweets Query " + sqlQuery);
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
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			return null;
		}
		
		PreparedStatement pmst = null;
		Statement stmt = null;
		
		String sqlQuery = "select t1.*,t2.username from Tweets AS t1 INNER JOIN Users AS t2 ON t1.user = t2.idUsers WHERE username='" + loguser + "' ORDER by idTweets DESC";
		
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
