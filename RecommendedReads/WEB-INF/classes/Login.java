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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
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
		  	System.out.println("Login: Connecting to database...");
		    conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		    stmt = (Statement) conn.createStatement();
		    String sql;
		    
		    // Check if account exists or not
		    sql = "SELECT email FROM user_login;";
		    ResultSet rs1 = (ResultSet) stmt.executeQuery(sql);
		    ArrayList<String> emailsDB = new ArrayList<String>();
		    while(rs1.next()) {
		    	emailsDB.add(rs1.getString("email"));
		    }
		    
		    // Email is valid
		    if(emailsDB.contains(email)) {
		    	System.out.println("Email is valid.");
		    	sql = "SELECT password FROM user_login WHERE email = \"" + email + "\";";
		    	ResultSet rs2 = (ResultSet) stmt.executeQuery(sql);
		    	rs2.next();
		    	String accountPassword = rs2.getString("password");
		    	// Password is valid
		    	// Update database with new login
		    	if(accountPassword.equals(password)) {
		    		System.out.println("Password is valid.");
				    // Get current session and set session attribute
		    		HttpSession session = request.getSession();
		    		session.setAttribute("email", email);
		    		sql = "UPDATE user_login SET lastLogin=\"" + datetime + "\" WHERE email=\"" 
				    		+ session.getAttribute("email") + "\";";
					stmt.executeUpdate(sql);
		    		response.sendRedirect("RatingsReviews");
		    	}
		    	// Password is not valid
		    	else {
		    		System.out.println("Password is not valid.");
			    	request.setAttribute("err", "");
			    	rd = request.getRequestDispatcher("login.jsp");
			    	rd.forward(request, response);
		    	}
		    }
		    // Email is not valid
		    else {
		    	System.out.println("Email is not valid.");
		    	request.setAttribute("err", email);
		    	rd = request.getRequestDispatcher("login.jsp");
		    	rd.forward(request, response);
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
