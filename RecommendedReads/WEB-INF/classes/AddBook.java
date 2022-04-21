

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class AddBook
 */
@WebServlet("/AddBook")
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBook() {
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
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String genre = request.getParameter("genre");
		String pages = request.getParameter("pages");
		String year = request.getParameter("year");
		
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
		  	System.out.println("AddBook: Connecting to database...");
		    conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = (Statement) conn.createStatement();
		    String sql;

		    // Check if book already exists in database
		    String addError;
		    sql = "SELECT * FROM book_info WHERE title =\"" + title + "\" AND author=\"" + author + "\";";;
		    ResultSet rs1 = (ResultSet) stmt.executeQuery(sql);
		    boolean exists = rs1.next();
		    if(exists) {
		    	System.out.println("Book not added because it already exists.");
		    	addError = "err";
		    }
		    // If book does not exist, add it
		    else {
		    	System.out.println("Book successfully added.");
		    	addError = "no";
		    	// Add new book to database
			    sql = "INSERT INTO book_info (title, author, genre, pages, year)" 
			    		+ " VALUES (\"" + title + "\", \"" + author + "\", \"" + genre + "\", \"" + pages + "\", \"" + year + "\");"; 
				System.out.println(sql);
			    stmt.executeUpdate(sql);
		    }
		    // Set session attributes
		    session.setAttribute("browseAddError", addError);
		    
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
