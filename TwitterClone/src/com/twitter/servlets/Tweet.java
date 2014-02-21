package com.twitter.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


//import com.datastax.driver.core.Cluster;



import com.twitter.lib.*;
import com.twitter.models.*;
import com.twitter.stores.*;

/**
* Servlet implementation class Tweet
*/
@WebServlet(
urlPatterns = {
"/Tweet",
"/Tweet/*"
},
initParams = {
@WebInitParam(name = "data-source", value = "jdbc/twitter")
})
public class Tweet extends HttpServlet {
private static final long serialVersionUID = 1L;
private DataSource _ds = null;
String user;
String loggedin = "";
   /* private Cluster cluster;
    /**
* @see HttpServlet#HttpServlet()
*/
    public Tweet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
// TODO Auto-generated method stub
   	Dbutils db = new Dbutils();
    	db.createSchema();
        _ds=db.assemble(config);
}
    
/**
* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
*/
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub
//String args[]=Convertors.SplitRequestPath(request);
	//System.out.println("Starting GET");
	HttpSession session = request.getSession();
    user = (String) session.getAttribute("username");
	
	Iterator<TweetStore> iterator;
	TweetModel tm= new TweetModel();
	LinkedList<TweetStore> tweetList = null;
	
//tm.setCluster(cluster);
	tm.setDatasource(_ds);
	if(user == null)
	{
		tweetList = tm.getAllTweets();
		loggedin="";
	}
	else
	{
		tweetList = tm.getTweets(user);
		loggedin="true";
	}
	request.setAttribute("loggedin", loggedin);
	System.out.println("Logged in? " + loggedin + " AS " + user);
	session.setAttribute("username", user);
	request.setAttribute("Tweets", tweetList); //Set a bean with the list in it
	RequestDispatcher rd = request.getRequestDispatcher("/RenderTweets.jsp");
	
	rd.forward(request, response);
	
}

/**
* @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
*/
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub
	HttpSession session = request.getSession();
	user = (String) session.getAttribute("username");
	System.out.println(user);
	String tweet = request.getParameter("tweettext");
	
	System.out.println(tweet);
	
	
	Iterator<TweetStore> iterator;
	TweetModel tm= new TweetModel();

	tm.setDatasource(_ds);
	tm.insertTweet(tweet, user);
	
	LinkedList<TweetStore> tweetList = tm.getTweets(user);
	
	session.setAttribute("username", user);
	request.setAttribute("Tweets", tweetList); //Set a bean with the list in it
	response.sendRedirect("Tweet");

}



}