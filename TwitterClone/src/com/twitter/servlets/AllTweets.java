package com.twitter.servlets;

import java.io.IOException;
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




import javax.sql.DataSource;

import com.twitter.lib.*;
import com.twitter.models.*;
import com.twitter.stores.*;

/**
* Servlet implementation class Tweet
*/
@WebServlet(
urlPatterns = {
"/AllTweets",
"/AllTweets/*"
},
initParams = {
@WebInitParam(name = "data-source", value = "jdbc/twitter")
})

public class AllTweets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllTweets() {
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
		Iterator<TweetStore> iterator;
		TweetModel tm= new TweetModel();
		LinkedList<TweetStore> tweetList = null;
		
	
		tm.setDatasource(_ds);
			tweetList = tm.getAllTweets();
	
		request.setAttribute("AllTweets", tweetList); //Set a bean with the list in it
		RequestDispatcher rd = request.getRequestDispatcher("/AllTweets.jsp");
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
