package library_manager;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuerySet {

	public static StringBuffer search(Statement stmt, String sql) throws SQLException {
		// stmt.executeQuery("use library");
		ResultSet rs = stmt.executeQuery(sql);
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body><table>");
		sb.append(
				"<tr><th>ISBN</th><th>Title</th><th>Author</th><th>BranchId</th><th>Num_copies</th><th>BranchName</th></tr>");
		if (rs.next()) {
			sb.append("<tr>");
			sb.append("<td>" + rs.getString("ISBN10") + "</td>");
			sb.append("<td>" + rs.getString("Title") + "</td>");
			sb.append("<td>" + rs.getString("Author") + "</td>");
			sb.append("<td>" + rs.getString("branch_id") + "</td>");
			sb.append("<td>" + rs.getString("no_of_copies") + "</td>");
			sb.append("<td>" + rs.getString("branch_name") + "</td>");
			sb.append("</tr>");
		} else
			sb.append("no result");
		while (rs.next()) {
			sb.append("<tr>");
			sb.append("<td>" + rs.getString("ISBN10") + "</td>");
			sb.append("<td>" + rs.getString("Title") + "</td>");
			sb.append("<td>" + rs.getString("Author") + "</td>");
			sb.append("<td>" + rs.getString("branch_id") + "</td>");
			sb.append("<td>" + rs.getString("no_of_copies") + "</td>");
			sb.append("<td>" + rs.getString("branch_name") + "</td>");
			sb.append("</tr>");
		}
		sb.append("</table></body></html>");
		return sb;
	}

	public static StringBuffer checkout(Statement stmt, String isbn, String branchid, String cardno)
			throws SQLException {
		String sql = "select * from book_copies where branch_id='" + branchid + "'and book_id='" + isbn + "'";
		ResultSet rs = stmt.executeQuery(sql);
		StringBuffer sb = new StringBuffer();
        if(isbn==""||branchid==""||cardno==""){
        	sb.append("all fields are necessary!!!");
        	return sb;
        }
		sb.append("<html><body><table>");
		sb.append("<tr><th>Book_Id</th><th>Branch_Id</th><th>Num_Of_Copies</th></tr>");
		int num = 0;
		if (rs.next()) {
			sb.append("<tr>");
			sb.append("<td>" + rs.getString("book_id") + "</td>");
			sb.append("<td>" + rs.getString("branch_id") + "</td>");
			num = Integer.parseInt(rs.getString("no_of_copies"));
			sb.append("<td>" + num + "</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		rs = stmt.executeQuery("select * from book_loans where branch_id='" + branchid + "'and isbn='" + isbn
				+ "'and date_in is null");
		if (rs.next())
			sb.append("This book has been checked out!!!");
		else if (num == 0)
			sb.append("No Copy Available Now !!!");
		else {
			rs = stmt.executeQuery("select count(*) from book_loans where ID0000id='" + cardno + "'");
			int count = 0;
			if (rs.next())
				count = Integer.parseInt(rs.getString(1));
			if (count == 3)
				sb.append("Can not check out more than three books !!!");
			else {
				rs = stmt.executeQuery("select count(*) from book_loans");
				if (rs.next())
					count = Integer.parseInt(rs.getString(1));
				int loan_id = count + 1;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String date = df.format(new Date());
				String due_date = df.format(new Date(new Date().getTime() + 14 * 24 * 60 * 60 * 1000));
				sql = "insert into book_loans values ('" + loan_id + "','" + isbn + "','" + branchid + "','" + cardno
						+ "','" + date + "','" + due_date + "',null)";
				stmt.executeUpdate(sql);
				sb.append("Check Out Successfully !!!");
			}

		}
		sb.append("</body></html>");
		return sb;

	}

	public static StringBuffer locate(Statement stmt, String bookid, String cardno, String name) throws SQLException {
		String sql = "select * from book_loans LEFT JOIN borrowers on book_loans.ID0000id=borrowers.ID0000id "
				+ "where (isbn='" + bookid + "'or book_loans.ID0000id='" + cardno + "'or("
				+ "book_loans.ID0000id=borrowers.ID0000id and (first_name='" + name + "'or last_name ='" + name
				+ "')))and date_in is null";
		ResultSet rs = stmt.executeQuery(sql);
		StringBuffer sb = new StringBuffer();
        if(bookid==""&&cardno==""&&name==""){
        	sb.append("At least one field need to be filled!!!");
        	return sb;
        }
		if (rs.next()) {
			sb.append("<html><body><table>");
			sb.append(
					"<tr><th>loan_id</th><th>isbn</th><th>branch_id</th><th>ID0000id</th><th>date_out</th><th>due_date</th><th>date_in</th></tr>");
			sb.append("<tr>");
			sb.append("<td>" + rs.getString("loan_id") + "</td>");
			sb.append("<td>" + rs.getString("isbn") + "</td>");
			sb.append("<td>" + rs.getString("branch_id") + "</td>");
			sb.append("<td>" + rs.getString("ID0000id") + "</td>");
			sb.append("<td>" + rs.getString("date_out") + "</td>");
			sb.append("<td>" + rs.getString("due_date") + "</td>");
			sb.append("<td>" + rs.getString("date_in") + "</td>");
			sb.append("</tr>");
			while (rs.next()) {
				sb.append("<tr>");
				sb.append("<td>" + rs.getString("loan_id") + "</td>");
				sb.append("<td>" + rs.getString("isbn") + "</td>");
				sb.append("<td>" + rs.getString("branch_id") + "</td>");
				sb.append("<td>" + rs.getString("ID0000id") + "</td>");
				sb.append("<td>" + rs.getString("date_out") + "</td>");
				sb.append("<td>" + rs.getString("due_date") + "</td>");
				sb.append("<td>" + rs.getString("date_in") + "</td>");
				sb.append("</tr>");
			}
			sb.append("</table></body></html>");
		} else
			sb.append("No record");
		return sb;

	}

	public static String checkin(Statement stmt, String loanid, String bookid, String cardno, String name)
			throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		ResultSet rs;
		String Qsql = "select * from book_loans LEFT JOIN borrowers on book_loans.ID0000id=borrowers.ID0000id "
				+ "where (isbn='" + bookid + "'or book_loans.ID0000id='" + cardno + "'or("
				+ "book_loans.ID0000id=borrowers.ID0000id and (first_name='" + name + "'or last_name ='" + name
				+ "')))and date_in is null" + " and loan_id='" + loanid + "'";
		rs = stmt.executeQuery(Qsql);
		String Usql = "update book_loans LEFT JOIN borrowers on book_loans.ID0000id=borrowers.ID0000id set date_in='"
				+ date + "' " + "where (isbn='" + bookid + "'or book_loans.ID0000id='" + cardno + "'or("
				+ "book_loans.ID0000id=borrowers.ID0000id and (first_name='" + name + "'or last_name ='" + name
				+ "')))and date_in is null" + " and loan_id='" + loanid + "'";
		if (rs.next()) {
			stmt.executeUpdate(Usql);
			return "Check in successfully";
		}
		return "Please check in a book from the table above !!!";
	}

	public static String createbow(Statement stmt, String ssn, String fname, String lname, String email, String addr,
			String city, String state, String phone) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from borrowers where ssn='"+ssn+"'");
		if(ssn==""||fname==""||lname==""||email==""||addr==""||city==""||state==""||phone=="")return "All fields are necesarry !!!";
		else if(rs.next())return "Borrowers are allowed to possess exactly one library card";
		else{
			rs=stmt.executeQuery("SELECT COUNT(*) FROM borrowers");
			int ct=0;
			if(rs.next())ct=Integer.parseInt(rs.getString(1));
			String id = "ID"+String.format("%06d", ct+1);
			stmt.executeUpdate("insert into borrowers(ID0000id,ssn,first_name,last_name,email,address,city,state,phone)"
					+ "values('"+id+"','"+ssn+"','"+lname+"','"+fname+"','"+email+"','"+addr+"','"+city+"','"+state+"','"+phone+"')");
		
		}
		return "Borrower Created !!!";
	}

	public static String refresh(Statement stmt, Statement stmt2) throws SQLException, ParseException {

		ResultSet rs = stmt.executeQuery("select * from book_loans where date_in is null");
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
	     rs = stmt.executeQuery("select * from book_loans left join fine on fine.loan_id=book_loans.loan_id where paid='0'");
			while(rs.next()){
				long due = ((Date) fmt.parse(rs.getString("due_date"))).getTime();
				long now = new Date().getTime();
				float amt = (float) (0.25*(now-due)/(24 * 60 * 60 * 1000));
				stmt2.executeUpdate("update fine set fine_amt ='"+amt+"'where loan_id='"+rs.getString("loan_id")+"'");
			}
	     

		return "Refresh finished!";
	}

	public static String checkpaid(Statement stmt, String cardno) throws SQLException {
		ResultSet rs = stmt.executeQuery("select sum(fine_amt) from fine left join book_loans on fine.loan_id=book_loans.loan_id "
				+ "where ID0000id='"+cardno+"'and paid=0");
		if(rs.next())return rs.getString(1);
		return "No record";
	}

	public static String paidall(Statement stmt, Statement stmt2, String cardno) throws SQLException {

		ResultSet rs = stmt.executeQuery("select * from book_loans where ID0000id='"+cardno+"'and date_in is not null");
		if(rs.next()){
			stmt2.executeUpdate("update fine set paid='"+1+"' where loan_id='"+rs.getString("loan_id")+"'");	
		}
		return "You fine is 0 now !";
	}




}
