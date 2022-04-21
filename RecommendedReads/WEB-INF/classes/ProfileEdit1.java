import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * Servlet implementation class ProfileEdit1
 */
@WebServlet("/ProfileEdit1")
public class ProfileEdit1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileEdit1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated catch block
	    // Get current session
		HttpSession session = request.getSession();
	    RequestDispatcher rd = null;
		
		// Get HTML parameter values
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String favoriteGenre = request.getParameter("favoriteGenre");
		String favoriteAuthor = request.getParameter("favoriteAuthor");
		String booksPerYear = request.getParameter("booksPerYear");
	
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
		  	System.out.println("ProfileEdit1: Connecting to database...");
		    conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = (Statement) conn.createStatement();
		    String sql;
		    
		    // Check if account exists or not
		    sql = "SELECT email FROM user_info;";
		    ResultSet rs = (ResultSet) stmt.executeQuery(sql);
		    ArrayList<String> emailsDB = new ArrayList<String>();
		    while(rs.next()) {
		    	emailsDB.add(rs.getString("email"));
		    }
	    	System.out.println(session.getAttribute("email"));
	    	// To prevent account exists error if the user does not edit email field
		    if(email.equals(session.getAttribute("email")))
		    	emailsDB.remove(email);
		    // If email (account) exists 
		    if(emailsDB.contains(email)) {
		    	System.out.println("Email exists already.");
		    	request.setAttribute("err", email);
		    	rd = request.getRequestDispatcher("edit_profile.jsp");
		    	rd.forward(request, response);
		    }
		    // If email (account) does not exists 
		    else {
		    	System.out.println("Email does not exist.");
			    // Set session attribute
		    	session.setAttribute("email", email);
		    	sql = "UPDATE user_info SET email=\"" + email + "\", firstName=\"" + firstName + "\", lastName=\"" + lastName 
		    			+ "\", favoriteGenre=\"" + favoriteGenre + "\", favoriteAuthor=\"" + favoriteAuthor  
		    			+ "\", booksPerYear=\"" + booksPerYear + "\" WHERE email=\"" + session.getAttribute("email") + "\";";
			    stmt.executeUpdate(sql);
			    sql = "UPDATE user_login SET email=\"" + email + "\", password=\"" + password + "\" WHERE email=\"" 
			    		+ session.getAttribute("email") + "\";";
				stmt.executeUpdate(sql);
				response.sendRedirect("Profile");
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
