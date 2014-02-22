package com.twitter.models;


import javax.sql.DataSource;

import java.sql.*;

import com.twitter.lib.*;

public class LoginModel {

	private DataSource _ds = null;
	
	public LoginModel(){

	}
	
	public void setDatasource(DataSource _ds){
		this._ds=_ds;
		System.out.println("Set Data Source in Model"+_ds.toString());
	}
	
	//Check if login is valid
	public boolean authenticateLogin(String strUserName, String strPassword) throws Exception {
			String LOGIN_QUERY = "SELECT * from Users where username=? and password=?;";	
		   boolean isValid = false;
		   Connection conn = null;
		   try {
		     conn = _ds.getConnection();
		     PreparedStatement prepStmt = conn.prepareStatement(LOGIN_QUERY);
		     prepStmt.setString(1, strUserName);
		     prepStmt.setString(2, strPassword);
		     ResultSet rs = prepStmt.executeQuery();
		     if(rs.next()) {
		       System.out.println("User login is valid in DB");
		       isValid = true;
		     }
		  } catch(Exception e) {
		    System.out.println("validateLogon: Error while validating password: "+e.getMessage());
		    throw e;
		  } finally {
		     conn.close();
		  }
		  return isValid;
		}
	
	
}
