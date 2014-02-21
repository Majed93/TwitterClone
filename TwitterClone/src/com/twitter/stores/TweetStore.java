package com.twitter.stores;

import java.text.*;
import java.util.Date;
public class TweetStore {
	
	Integer Tweetid;
	String Tweet;
	String User;
    String Username;
    String Time;
    
    public Integer getTweetid(){
    	return Tweetid;
    }
    public String getTweet(){
    return Tweet;
    }
    
    public String getUser(){
    return User;
    }
    
    public String getUsername(){
        return Username;
    }
    
    public String getTime(){

    	/*
    	Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String newString = new SimpleDateFormat("HH:mm dd/MM").format(date); // 09:00 05/02
    	
    	
        return newString;
        */
    	return Time;
    }
    
    public void setTweetid(Integer Tweetid){
    	this.Tweetid = Tweetid;
    }
    public void setTweet(String Tweet){
    this.Tweet=Tweet;
    }
    
    public void setUser(String User){
    this.User=User;
    }
    
    public void setUsername(String Username){
        this.Username=Username;
   }
    
    public void setTime(String Time){
        this.Time=Time;
   }
    
}
