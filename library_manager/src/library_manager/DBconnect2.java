package library_manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.*;
/**
 * Servlet implementation class DBconnect2
 */
@WebServlet("/DBconnect2")
public class DBconnect2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();	
		String isbn = request.getParameter("isbn");
		String branchid = request.getParameter("branchid");
		String cardno = request.getParameter("cardno");
		try{
			Context ctx = new InitialContext();
			Context envContext  = (Context)ctx.lookup("java:/comp/env");
             ds = (DataSource)envContext.lookup("jdbc/library");
            Connection conn = ds.getConnection();			
		    Statement stmt = conn.createStatement();
		    StringBuffer rs = QuerySet.checkout(stmt,isbn,branchid,cardno); 
		    out.println(rs);
		    conn.close();
						
		}
		catch(SQLException | NamingException ex){
			out.println("Error in connection: " + ex.getMessage());	
		}
	}

}
