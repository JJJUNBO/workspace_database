package library_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Date;
public class fineinit {
     public static void main(String[] args){

    	 DataSource ds;
    	 try {		
    		 
    		    Connection conn = null;
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "8825191");
				Statement stmt = conn.createStatement();
				stmt.execute("use library;");
				ResultSet rs = stmt.executeQuery("select * from book_loans");
				DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
				while(rs.next()){
					//System.out.println(rs.getString(1));
					long datein = ((Date) fmt.parse(rs.getString("date_in"))).getTime();
					long due = ((Date) fmt.parse(rs.getString("due_date"))).getTime();
					String id=rs.getString("loan_id");
					if(datein>due){
						int fine = (int) (0.25*(datein-due)/(24 * 60 * 60 * 1000));
						System.out.println((datein-due)/(24 * 60 * 60 * 1000));
						stmt.executeUpdate("insert into fine(loan_id,fine_amt,paid)"
								+ "values('"+id+"','"+fine+"',1)");

					}
				}
    	 }
			catch(SQLException | ParseException ex) {
				System.out.println("Error in connection: " + ex.getMessage());
			}
	}

}
