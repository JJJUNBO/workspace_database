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
 * Servlet implementation class DBconnect3
 */
@WebServlet("/DBconnect3")
public class DBconnect3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DataSource ds;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();	
		String bookid = request.getParameter("bookid");
		String cardno = request.getParameter("cardno");
		String name = request.getParameter("name");
		try{
			Context ctx = new InitialContext();
			Context envContext  = (Context)ctx.lookup("java:/comp/env");
             ds = (DataSource)envContext.lookup("jdbc/library");
            Connection conn = ds.getConnection();			
		    Statement stmt = conn.createStatement();
		    StringBuffer rs = QuerySet.locate(stmt,bookid,cardno,name); 
		    out.println(rs);
		    conn.close();
						
		}
		catch(SQLException | NamingException ex){
			out.println("Error in connection: " + ex.getMessage());	
		}
	}

}
