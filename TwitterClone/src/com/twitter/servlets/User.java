package com.twitter.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.twitter.lib.Dbutils;
import com.twitter.models.TweetModel;
import com.twitter.models.UserModel;
import com.twitter.stores.TweetStore;

/**
 * Servlet implementation class User
 */
@WebServlet(
urlPatterns = {
"/User",
"/User/*"
},
initParams = {
@WebInitParam(name = "data-source", value = "jdbc/twitter")
})
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
	String user;
	String profuser;
	String loggedin;
	String followed;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
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
		HttpSession session = request.getSession();
		user = (String) session.getAttribute("username");
		//profuser = (String) request.getParameter("user");
		UserModel um = new UserModel();
		
		um.setDatasource(_ds);
		String[] uris =request.getRequestURI().split("/");

		System.out.println("URI: " + request.getRequestURI());
		profuser=uris[3];
		
		boolean checkfollowed = um.check(profuser, user);
		
		Iterator<TweetStore> iterator;
		LinkedList<TweetStore> tweetList = null;
		tweetList = um.getTweets(profuser);
		
		followed = String.valueOf(checkfollowed);
		if(followed == "false")
		{
			followed = "";
		}
		if(user == null)
		{
			loggedin="";
		}
		else
		{
			loggedin="true";
		}

		System.out.println("Following? " + followed);
		request.setAttribute("followed", followed);
		request.setAttribute("loggedin", loggedin);
		request.setAttribute("profuser", profuser);
		request.setAttribute("UserTweets", tweetList); //Set a bean with the list in it
		
		RequestDispatcher rd = request.getRequestDispatcher("/User.jsp");
		rd.forward(request, response);
 	
		System.out.println("logged in? " + loggedin);
		System.out.println("CURRENT USER logged in: " + user);
		System.out.println("PROFILE OF USER: " + profuser);
 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		System.out.println("CURRENT USER logged in: " + user);
		System.out.println("PROFILE OF USER: " + profuser);
		
		UserModel um = new UserModel();
		
		um.setDatasource(_ds);
		
		if(followed == "")
		{
			um.follow(profuser, user);
			
		}
		else
		{
			um.unfollow(profuser, user);
		}
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
		//response.sendRedirect("/Tweet");
		
		
	}

}
