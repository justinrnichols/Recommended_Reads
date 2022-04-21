import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
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
 * Servlet implementation class WriteReview
 */
@WebServlet("/WriteReview")
public class WriteReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteReview() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    // Get current session
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		
		// Get HTML parameter value
		String email = (String) session.getAttribute("email");
		String rating = request.getParameter("rating");
		String review = request.getParameter("review");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		
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
		  	System.out.println("WriteReview: Connecting to database...");
		    conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = (Statement) conn.createStatement();
		    String sql;
	    	if(rating == "6")
		    	rating = "5";
		    
		    // Add new rating and review to database
		    sql = "INSERT INTO ratings_reviews (email, title, author, rating, review, posted)" 
		    		+ " VALUES (\"" + email + "\", \"" + title + "\", \"" + author + "\", \"" + rating + "\", \"" + review + "\", STR_TO_DATE(\"" + datetime + "\", \"%Y-%m-%d %T\")" + ");"; 
			stmt.executeUpdate(sql);
		    
		    response.sendRedirect("RatingsReviews");
		} catch (ClassNotFoundException | SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
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
