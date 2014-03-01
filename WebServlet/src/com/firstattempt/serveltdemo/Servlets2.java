package com.firstattempt.serveltdemo;
import ZapposSrc.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlets2
 */
@WebServlet("/Servlets2")
public class Servlets2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlets2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //User defined code for getting input from Servlets.java and send to the class files.
    //The out object is passed to the class which can print texts
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
		PrintWriter out = response.getWriter();		
		//out.println(request.getParameter("num"));		
		//out.println(request.getParameter("sum"));
		//ApiConnect apiobject = new ApiConnect(Integer.valueOf(request.getParameter("num")),Double.valueOf(request.getParameter("sum")),out);
		Results apiobject = new Results(Integer.valueOf(request.getParameter("num")).intValue(),Double.valueOf(request.getParameter("sum")).doubleValue(),out); 
		//apiobject.main();
		apiobject.testSet();
		apiobject.test();
		}catch(Exception e){e.printStackTrace();}
		/*out.println("<!DOCTYPE html><html>");
		 *
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
