package com.twitter.models;


import javax.sql.DataSource;

import java.sql.*;

import com.twitter.lib.*;
import com.twitter.stores.RegStore;
		


public class RegModel {


	private DataSource _ds = null;
	public boolean added;

	public RegModel(){
	
	}
	
	
	
	public void setDatasource(DataSource _ds){
		this._ds=_ds;
		System.out.println("Set Data Source in Model"+_ds.toString());
	}
	
	//Adds user
	public boolean Adduser(String email, String user, String pass)
	{
		 
		Connection Conn = null;
		Statement stmt;
		PreparedStatement pstmt = null;
		
		if(check(email,user) == true)
		{
			added = false;
			return added;
		}
		else
		{
			String add = "INSERT INTO Users (email, username, password) VALUE (?,?,?);";
			
			try {
				Conn = _ds.getConnection();
				
			} catch (Exception et) {
				System.out.println("No Connection in Tweets Model");
				
			}
			
			try{
				//stmt = Conn.createStatement();
				pstmt = Conn.prepareStatement(add);
				pstmt.setString(1, email);
				pstmt.setString(2, user);
				pstmt.setString(3, pass);
			       //stmt.executeUpdate(add);
				pstmt.executeUpdate();
			       
			       added = true;
			}catch(Exception e){
					added = false;
			System.out.println(e.toString());
			 
			}
			
			try {
				Conn.close();
			} catch (Exception ex) {
				added = false;
				System.out.println("Unable to close. Error: " + ex);
			}
			added = true;
		}
		return added;
	}

	//Checks if user exists
	public boolean check(String email, String user)
	{
		boolean exists;
		
		Connection Conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			Conn = _ds.getConnection();
		} catch (Exception et) {
			System.out.println("No Connection in Tweets Model");
			
		}
	
		
		String sqlQuery = "SELECT * FROM Users WHERE username=? OR email=?;";
		
		System.out.println("Tweets Query " + sqlQuery);
		try {
			try {
					//stmt = Conn.createStatement();
					pstmt = Conn.prepareStatement(sqlQuery);
					pstmt.setString(1, user);
					pstmt.setString(2, email);
					
			} catch (Exception et) {
				System.out.println("Can't create preare statement");
				exists = false;
			}
			System.out.println("Created prepare");
			try {
				//rs=stmt.executeQuery(sqlQuery);
						
				rs = pstmt.executeQuery();	
			} catch(Exception et) {
				System.out.println("Can not execut query " + et);
				exists = false;
		
			}
			System.out.println("Statement executed");
		
			if (rs.wasNull()) {
				
			System.out.println("result set was null");
			exists = false;
			
			} else {
				
			System.out.println("Well it wasn't null");
			exists = false;
			}
			while (rs.next()) {
				
				System.out.println("Getting RS");
				exists = true;
			}
		} catch (Exception ex) {
			System.out.println("Oops, error in query" + ex);
			exists = false;
		}
		try {
			Conn.close();
		} catch (Exception ex) {
			exists = false;
		}
		
	System.out.println("EXISTS: " + exists);
	return exists;
	}

}