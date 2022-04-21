import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated catch block
	    RequestDispatcher rd = null;
		
	    // Get HTML parameter values
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String favoriteGenre = request.getParameter("favoriteGenre");
		String favoriteAuthor = request.getParameter("favoriteAuthor");
		String booksPerYear = request.getParameter("booksPerYear");
		
		// Get the time
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);
	
		// JDBC driver name and database URL
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		String DB_URL = "jdbc:mysql://localhost:3306/recommended_reads";

		// Database credentials
		String USER = "root";
		String PASS = "";

		Connection conn = null;
		Statement stmt = null;
		
		try {
			// Opening connection and executing query
		    Class.forName("com.mysql.jdbc.Driver");
		  	System.out.println("SignUp: Connecting to database...");
		    conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = (Statement) conn.createStatement();
		    String sql;
		    
		    // Check if account exists or not
		    sql = "SELECT email FROM user_login;";
		    ResultSet rs = (ResultSet) stmt.executeQuery(sql);
		    ArrayList<String> emailsDB = new ArrayList<String>();
		    while(rs.next()) {
		    	emailsDB.add(rs.getString("email"));
		    }
		    System.out.println(emailsDB);
		    // If email (account) exists 
		    if(emailsDB.contains(email)) {
		    	System.out.println("Email exists already.");
		    	request.setAttribute("err", email);
		    	rd = request.getRequestDispatcher("signup.jsp");
		    	rd.forward(request, response);
		    }
		    // If email (account) does not exists, add new user to database
		    else {
		    	System.out.println("Email does not exist.");
			    // Get current session and set session attribute
		    	HttpSession session = request.getSession();
	    		session.setAttribute("email", email);
		    	sql = "INSERT INTO user_info (email, firstName, lastName, favoriteGenre, favoriteAuthor, booksPerYear, joined)" 
			    		+ " VALUES (\"" + email + "\", \"" + firstName + "\", \"" + lastName + "\", \"" + favoriteGenre + "\", \"" 
			    		+ favoriteAuthor + "\", \"" + booksPerYear  + "\", " + "STR_TO_DATE(\"" + datetime + "\", \"%Y-%m-%d %T\")" + ");"; 
			    stmt.executeUpdate(sql);
			    sql = "INSERT INTO user_login (email, password, lastLogin)" 
			    		+ " VALUES (\"" + email + "\", \"" + password + "\", " + "STR_TO_DATE(\"" + datetime + "\", \"%Y-%m-%d %T\")" + ");"; 
				stmt.executeUpdate(sql);
				response.sendRedirect("RatingsReviews");
		    }
		} catch (ClassNotFoundException | SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
			// Close database connection
		    try {
		        stmt.close();
		        conn.close();
		    } catch (Exception e) {}
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
