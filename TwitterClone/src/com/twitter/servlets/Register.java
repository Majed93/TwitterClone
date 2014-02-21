package com.twitter.servlets;

import java.io.IOException;

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
import com.twitter.models.RegModel;

/**
 * Servlet implementation class Register
 */
@WebServlet(
urlPatterns = {
"/Register",
"/Register/*"
},
initParams = {
@WebInitParam(name = "data-source", value = "jdbc/twitter")
})
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource _ds = null;
	String success = " ";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		request.setAttribute("success", success);
		RequestDispatcher rd = request.getRequestDispatcher("/Register.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RegModel rm= new RegModel();

		
		String email = request.getParameter("email");
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		HttpSession session = null;
		rm.setDatasource(_ds);
		
		
		
		if(email == "" || user == "" || pass == "")
		{
			success = "Please fill in all fields.";
			request.setAttribute("success", success);
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
		}
		else if(rm.Adduser(email, user, pass) == false)
		{
			success = "EMAIL OR USER NAME INVALID!";
			request.setAttribute("success", success);
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
		}
		else
		{
			success = "Registered!";
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
        
	}

}
