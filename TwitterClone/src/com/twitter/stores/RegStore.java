package com.twitter.stores;


import java.text.*;

public class RegStore {

	
	String email;
	String username;
	String password;
	
	public String getEmail()
	{
		return email;
	}
	
	public String getUser()
	{
		return username;
	}
	
	public String getPass()
	{
		return password;
	}
	
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setUser(String username)
	{
		this.username = username;
	}
	
	public void setPass(String password)
	{
		this.password = password;
	}
}
