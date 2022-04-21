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
@WebServlet("/ProfileEdit2")
public class ProfileEdit2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileEdit2() {
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
		
		// Get HTML parameter values
		String[] ratings_reviews = request.getParameterValues("ratings_reviews");
	
		// JDBC driver name and database URL
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		String DB_URL = "jdbc:mysql://localhost:3306/recommended_reads";

		// Database credentials
		String USER = "root";
		String PASS = "";

		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		  	System.out.println("Profile2: Connecting to database...");
		    conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = (Statement) conn.createStatement();
		    String sql;
		    if(ratings_reviews != null) {
				for(int i = 0; i < ratings_reviews.length; i++) {
					String[] book_info = ratings_reviews[i].split("###");
					String title = book_info[0];
					String author = book_info[1];
					System.out.println(title);
					System.out.println(author);
					sql = "DELETE FROM ratings_reviews WHERE email=\"" + session.getAttribute("email") + "\" AND title=\"" 
							+ title + "\" AND author=\"" + author + "\";";
					stmt.executeUpdate(sql);
				}
		    }
			response.sendRedirect("Profile");
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
