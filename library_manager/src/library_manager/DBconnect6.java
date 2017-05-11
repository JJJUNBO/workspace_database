package library_manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
 * Servlet implementation class DBconnect6
 */
@WebServlet("/DBconnect6")
public class DBconnect6 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();	
		try{
			Context ctx = new InitialContext();
			Context envContext  = (Context)ctx.lookup("java:/comp/env");
             ds = (DataSource)envContext.lookup("jdbc/library");
            Connection conn = ds.getConnection();			
		    Statement stmt = conn.createStatement();
		    Statement stmt2 = conn.createStatement();
		    String rs = QuerySet.refresh(stmt,stmt2); 
		    out.println(rs);
		    conn.close();
						
		}
		catch(SQLException | NamingException | ParseException ex){
			out.println("Error in connection: " + ex.getMessage());	
		}
	}
	

}
