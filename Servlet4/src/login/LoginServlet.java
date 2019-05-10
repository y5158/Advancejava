package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("\nDriver loaded");
			con =DriverManager.getConnection("jdbc:mysql://localhost/test", "root","");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		try {
			PreparedStatement ps=con.prepareStatement("select password from login2 where username=?");
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next() && password.equals(rs.getString(1))) {
				RequestDispatcher rd=request.getRequestDispatcher("home.html");
				rd.forward(request, response);
				
			}else {
				out.println("<h1>Login not Successful</h1>");
				RequestDispatcher rd=request.getRequestDispatcher("login.html");
				rd.include(request, response);
				
			}
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}
