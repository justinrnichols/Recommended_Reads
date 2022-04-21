import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
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
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
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
		
		// Get HTML parameter values
		String firstName = "";
		String lastName = "";
		String email = "";
		String password = "";
		String favoriteGenre = "";
		String favoriteAuthor = "";
		int booksPerYear = 0;
		String joined = "";
		
		email = (String) session.getAttribute("email");
		
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
		  	System.out.println("Profile1: Connecting to database...");
		    conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = (Statement) conn.createStatement();
		    String sql;
		    
		    // Obtain values from database for Account
		    sql = "SELECT * FROM user_info WHERE email=\"" + email + "\";";
		    ResultSet rs1 = (ResultSet) stmt.executeQuery(sql);
		    while(rs1.next()){
				firstName =  rs1.getString("firstName");
				lastName =  rs1.getString("lastName");
				email = rs1.getString("email");
				favoriteGenre = rs1.getString("favoriteGenre");
				favoriteAuthor = rs1.getString("favoriteAuthor");
				booksPerYear = rs1.getInt("booksPerYear");
				joined = rs1.getString("joined");
		 	}
		    sql = "SELECT * FROM user_login WHERE email=\"" + email + "\";";
		    ResultSet rs2 = (ResultSet) stmt.executeQuery(sql);
		    while(rs2.next()){
		    	password = rs2.getString("password");
		 	}
		    // Set session attributes
			session.setAttribute("firstName", firstName);
			session.setAttribute("lastName", lastName);
			session.setAttribute("password", password);
			session.setAttribute("favoriteGenre", favoriteGenre);
			session.setAttribute("favoriteAuthor", favoriteAuthor);
			session.setAttribute("booksPerYear", booksPerYear);
			session.setAttribute("joined", joined);
			
		    // Obtain values from database for Ratings
			sql = "SELECT * FROM ratings_reviews WHERE email=\"" + email + "\";";
			ResultSet rs3 = (ResultSet) stmt.executeQuery(sql);
			ArrayList<String> titles = new ArrayList<String>();
			ArrayList<String> authors = new ArrayList<String>();
			ArrayList<Object> ratings = new ArrayList<Object>();
			ArrayList<String> reviews = new ArrayList<String>();
			ArrayList<String> posted = new ArrayList<String>();
		    while(rs3.next()) {
		    	titles.add(rs3.getString("title"));
		    	authors.add(rs3.getString("author"));
		    	ratings.add(rs3.getObject("rating"));
		    	reviews.add(rs3.getString("review"));
		    	
		    	SimpleDateFormat oldPosted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	Date datetime = oldPosted.parse(rs3.getString("posted"));
		    	oldPosted.applyPattern("EEE, d MMM yyyy HH:mm:ss");
		    	String newPosted = oldPosted.format(datetime);
		    	
		    	posted.add(newPosted);
		    }
		    // Set session attributes
		    session.setAttribute("profileTitles", titles);
		    session.setAttribute("profileAuthors", authors);
		    session.setAttribute("profileRatings", ratings);
		    session.setAttribute("profileReviews", reviews);
		    session.setAttribute("profilePosted", posted);
		    rd = request.getRequestDispatcher("profile.jsp");
		    rd.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (ParseException e) {
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
