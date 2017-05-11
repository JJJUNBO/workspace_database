package library_manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DBconnect5
 */
@WebServlet("/DBconnect5")
public class DBconnect5 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataSource ds;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();	
		String ssn = request.getParameter("ssn");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String addr = request.getParameter("addr");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String phone = request.getParameter("phone");
		try{
			Context ctx = new InitialContext();
			Context envContext  = (Context)ctx.lookup("java:/comp/env");
             ds = (DataSource)envContext.lookup("jdbc/library");
            Connection conn = ds.getConnection();			
		    Statement stmt = conn.createStatement();
		    String rs = QuerySet.createbow(stmt,ssn,fname,lname,email,addr,city,state,phone); 
		    out.println(rs);
		    conn.close();
						
		}
		catch(SQLException | NamingException ex){
			out.println("Error in connection: " + ex.getMessage());	
		}
	}

}
