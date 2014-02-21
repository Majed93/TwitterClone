package com.twitter.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;
import com.twitter.lib.*;
import com.twitter.models.*;
import com.twitter.stores.*;

import javax.sql.DataSource;

import java.sql.*;


/**
 * Servlet implementation class Login
 */
@WebServlet(
urlPatterns = {
"/Login",
"/Login/*"
},
initParams = {
@WebInitParam(name = "data-source", value = "jdbc/twitter")
})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;

	 
	private static final String LOGIN_QUERY = "SELECT * from Users where username=? and password=?";
	private static final String HOME_PAGE = "Tweet";
	private static final String LOGIN_PAGE = "/Login.jsp";
	
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		
		RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		        String username = request.getParameter("username");
		        String password = request.getParameter("password");
		        String loggedin = "";
		        HttpSession session = null;
		    
		        RequestDispatcher rd = null;  
		        
		        boolean isValidLogon = false;
		        
		        LoginModel lm= new LoginModel();

		        lm.setDatasource(_ds);
		    	
		        try {
					isValidLogon = lm.authenticateLogin(username, password, LOGIN_QUERY);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     if(isValidLogon)  {
		            session = request.getSession();
		            session.setAttribute("username", username);
		            response.sendRedirect(HOME_PAGE);
		    	
		    			            
		        } else {
		        	 try {
		     
				         loggedin = "true";
				        
						 //System.out.println(error);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							
							System.out.println(e);
							//e.printStackTrace();
						}
		        	request.setAttribute("loggedin", loggedin);
		        	request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
			        	
		        	
		        }
	   
	}

}
