package com.firstattempt.serveltdemo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlets
 */
@WebServlet("/Servlets")
public class Servlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Servlets() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //User defined code for getting inputs from web forms and submitting it to another server
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html><html>");		
		out.println("<head><style> "
				+ "hr {color:sienna;} "
				+ "body {background-color:rgb(132,112,255);"
				+ "font-size:medium;"
				+ "background-image:url('C:\\Users\\Kaavya Srinivasan\\workspace\\WebServlet\\src\\zappos_logo.gif');"
				+ "background-repeat:no-repeat;"
				+ "background-attachment:fixed;background-position:right bottom;}</style></head><body>");
		out.println("<center><h2>Welcome to the Zappos API item search application</h2></center>");
		out.println("<form action=\"http://localhost:8080/WebServlet/Servlets2\" method=\"GET\">");
		out.println("How many items do you want to buy <br><input type=text name=\"num\"/><br>"+
					"Tell us your budget <br><input type=text name=\"sum\"/><br><br>"+
					"<input type=submit value=\"Get Me Something!\" width=\"100\" height=\"48\"><br></form></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
