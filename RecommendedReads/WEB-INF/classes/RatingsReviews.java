import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Servlet implementation class RatingsReviews
 */
@WebServlet("/RatingsReviews")
public class RatingsReviews extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RatingsReviews() {
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
		
		// Get HTML parameter value
		String genre = request.getParameter("genre");
	    session.setAttribute("selectedGenre", genre);

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
		  	System.out.println("RatingsReviews: Connecting to database...");
		    conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = (Statement) conn.createStatement();
		    String sql;
		    
		    // Obtain books for specific genre or all genres
		    ArrayList<String> values = new ArrayList<>(Arrays.asList("Fiction", "Nonfiction", "Action & Adventure", "Autobiography", "Biography", 
		              "Business", "Children\'s", "Classic", "Comic & Graphic Novel", "Coming-of-age", 
		              "Comedy", "Crime & True Crime", "Diary", "Drama", "Dystopian", "Educational", 
		              "Fantasy", "Health & Fitness", "Historical Fiction", "Home & Cooking & Garden", 
		              "Horror", "Memoir", "Mystery", "Poetry", "Political", "Romance", 
		              "Religious & Spirituality", "Satire", "Science Fiction", "Self Help", 
		              "Short Story & Folklore", "Sports & Leisure & Travel", "Thriller & Suspense", 
		              "Western", "Young Adult"));
			if(values.contains((String) session.getAttribute("selectedGenre")) && session.getAttribute("selectedGenre") != null) {
				sql = "SELECT * FROM book_info where genre=\"" + session.getAttribute("selectedGenre") + "\";";
			}
			else if(!values.contains((String) session.getAttribute("selectedGenre"))) {
				sql = "SELECT * FROM book_info;";
			}
			else {
				sql = "SELECT * FROM book_info;";
			    session.setAttribute("selectedGenre", "All Genres");
			}
		    ArrayList<String> titles = new ArrayList<String>();
			ArrayList<String> authors = new ArrayList<String>();
 			ResultSet rs1 = (ResultSet) stmt.executeQuery(sql);
			while(rs1.next()) {
				titles.add(rs1.getString("title"));
				authors.add(rs1.getString("author"));
			}

		    // Obtain ratings and reviews from database for each book
			ArrayList<ArrayList<String>> names = new ArrayList<ArrayList<String>>();
		    ArrayList<ArrayList<String>> ratings = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> reviews = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> posted = new ArrayList<ArrayList<String>>();
		    for(int i = 0; i < titles.size(); i++) {
		    	ArrayList<String> currentRatings = new ArrayList<String>();
		    	ArrayList<String> currentReview = new ArrayList<String>();
		    	ArrayList<String> currentPosted = new ArrayList<String>();
		    	ArrayList<String> currentEmails = new ArrayList<String>();
		    	ArrayList<String> currentNames = new ArrayList<String>();
				sql = "SELECT * FROM ratings_reviews WHERE title=\"" + titles.get(i) + "\" AND author=\"" + authors.get(i) + "\";";;
				ResultSet rs2 = (ResultSet) stmt.executeQuery(sql);
			    while(rs2.next()) {
			    	currentEmails.add(rs2.getString("email"));
			    	currentRatings.add(rs2.getString("rating"));
			    	currentReview.add(rs2.getString("review"));
			    	
			    	SimpleDateFormat oldPosted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    	Date datetime = oldPosted.parse(rs2.getString("posted"));
			    	oldPosted.applyPattern("EEE, d MMM yyyy HH:mm:ss");
			    	String newPosted = oldPosted.format(datetime);
			   
			    	currentPosted.add(newPosted);
			    }
			    for(int j = 0; j < currentEmails.size(); j++) {
				    sql = "SELECT * FROM user_info WHERE email=\"" + currentEmails.get(j) + "\";";;
					ResultSet rs3 = (ResultSet) stmt.executeQuery(sql);
					while(rs3.next()) {
						currentNames.add(rs3.getString("firstName") + " " + rs3.getString("lastName"));
				    }
			    }
			    names.add(currentNames);
			    ratings.add(currentRatings);
			    reviews.add(currentReview);
			    posted.add(currentPosted);
		    }
		    
		    // Set session attributes
		    session.setAttribute("browseNames", names);
		    session.setAttribute("browseRatings", ratings);
		    session.setAttribute("browseReviews", reviews);
		    session.setAttribute("browsePosted", posted);
		    
		    response.sendRedirect("Browse");
		} catch (ClassNotFoundException | SQLException | ParseException e) {
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
