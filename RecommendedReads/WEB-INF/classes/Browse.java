import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
 * Servlet implementation class Browse
 */
@WebServlet("/Browse")
public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Browse() {
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
		String email = (String) session.getAttribute("email");

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
		  	System.out.println("Browse: Connecting to database...");
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
			    session.setAttribute("selectedGenre", session.getAttribute("selectedGenre"));
			}
			else if(!values.contains((String) session.getAttribute("selectedGenre"))) {
				sql = "SELECT * FROM book_info;";
			    session.setAttribute("selectedGenre", "All Genres");
			}
			else {
				sql = "SELECT * FROM book_info;";
			    session.setAttribute("selectedGenre", "All Genres");
			}
			ArrayList<String> titles = new ArrayList<String>();
			ArrayList<String> authors = new ArrayList<String>();
			ArrayList<String> genres = new ArrayList<String>();
			ArrayList<String> pages = new ArrayList<String>();
			ArrayList<String> years = new ArrayList<String>();
			ResultSet rs1 = (ResultSet) stmt.executeQuery(sql);
		    while(rs1.next()) {
		    	titles.add(rs1.getString("title"));
		    	authors.add(rs1.getString("author"));
		    	genres.add(rs1.getString("genre"));
		    	pages.add(rs1.getString("pages"));
		    	years.add(rs1.getString("year"));
		    }
			
		    // Set session attributes
		    session.setAttribute("browseTitles", titles);
		    session.setAttribute("browseAuthors", authors);
		    session.setAttribute("browseGenres", genres);
		    session.setAttribute("browsePages", pages);
		    session.setAttribute("browseYears", years);
		    
		    // Obtain values from database for ratings and reviews for each book
		    ArrayList<String> avgRatings = new ArrayList<String>();
		    ArrayList<String> totalRatings = new ArrayList<String>();
		    ArrayList<String> writeError = new ArrayList<String>();
		    for(int i = 0; i < titles.size(); i++) {
		    	ArrayList<Integer> currentBookRatings = new ArrayList<Integer>();
		    	sql = "SELECT rating FROM ratings_reviews WHERE title=\"" + titles.get(i) + "\" AND author=\"" + authors.get(i) + "\";";;
		    	ResultSet rs3 = (ResultSet) stmt.executeQuery(sql);
		    	while(rs3.next()) {
		    		currentBookRatings.add(rs3.getInt("rating"));
		    	}
		    	double sum = 0;
		    	for(int j = 0; j < currentBookRatings.size(); j++) {
		    		sum += currentBookRatings.get(j);
		    	}
		    	double avg = 0;
		    	if(currentBookRatings.size() != 0)
		    		avg = Math.round((sum / currentBookRatings.size()) * 100.0) / 100.0;
		    	else
		    		avg = 0.0;
		    	avgRatings.add(String.valueOf(avg));
		    	totalRatings.add(String.valueOf(currentBookRatings.size()));
		    	
		    	// Check if user has review book yet
			    sql = "SELECT * FROM ratings_reviews WHERE email =\"" + email + "\" AND title=\"" + titles.get(i) + "\" AND author=\"" + authors.get(i) + "\";";;
			    ResultSet rs4 = (ResultSet) stmt.executeQuery(sql);
			    boolean exists = rs4.next();
			    if(exists)
			    	writeError.add("err");
			    else
			    	writeError.add("no");	
		    }
		    // Set session attributes
		    session.setAttribute("browseAvgRatings", avgRatings);
		    session.setAttribute("browseTotalRatings", totalRatings);
		    session.setAttribute("browseWriteError", writeError);
		    
		    rd = request.getRequestDispatcher("browse.jsp");
		    rd.forward(request, response);
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
