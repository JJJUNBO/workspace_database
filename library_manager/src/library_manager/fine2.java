package library_manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * Servlet implementation class fine2
 */
@WebServlet("/fine2")
public class fine2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 try {		
    		 
			 Context ctx = new InitialContext();
				Context envContext  = (Context)ctx.lookup("java:/comp/env");
	             ds = (DataSource)envContext.lookup("jdbc/library");
	            Connection conn = ds.getConnection();
	            Connection conn2 = ds.getConnection();	
				Statement stmt = conn.createStatement();
				Statement stmt2 = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from book_loans order by loan_id");
				DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
				while(rs.next()){
					System.out.println(rs.getString(1));
					long datein = ((Date) fmt.parse(rs.getString("date_in"))).getTime();
					long due = ((Date) fmt.parse(rs.getString("due_date"))).getTime();					
					if(datein>due){
						String id=rs.getString("loan_id");
						float fine = (float) (0.25*(datein-due)/(24 * 60 * 60 * 1000));
						System.out.println(fine);
						System.out.println((datein-due)/(24 * 60 * 60 * 1000));
						stmt2.executeUpdate("insert into fine(loan_id,fine_amt,paid)"
								+ "values('"+id+"','"+fine+"',0)");

					}
				}
 	 }
			catch(SQLException | ParseException | NamingException ex) {
				System.out.println("Error in connection: " + ex.getMessage());
			}
	}

}
