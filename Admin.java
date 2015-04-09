import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession userSession = request.getSession();
		String userid = (String)userSession.getAttribute("user");
        String user = (String)userSession.getAttribute("username"); 
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		String title ="Welcome To Amart Insurance Company";
		String docType =
				  "<!doctype html public \"-//w3c//dtd html 4.0 "+
				  "transitional//en\">\n";
		out.println(
						  docType +
				  "<html>\n"+
				  "<head><title>"+ title +"</title></head>\n"+
				  "<link type='text/css' rel='stylesheet'  href='"+ request.getRequestDispatcher("WEB-INF/styles.css") +"' media='screen'/>" +
				  "<body bgcolor=\"#ffffff\">\n"+
				  "<h1 align=\"center\">"+ title +"</h1>\n");
		out.println("<h1 align=\"center\" style=\"color:blue; font-size:30px;\" >Welcome "+ user +"</h1><br>");
		out.println("<hr><br>");
		out.println("<div style=margin-left:400px; ></br>");
		out.println("<a href='admin?action=addNewEquipment'>Add new Equipment</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("<a href='admin?action=addNewUser'>Add new User</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("<a href='admin?action=showUsers'>Show All users</a>&nbsp;&nbsp;&nbsp;&nbsp;\n");
		out.println("<a href='admin?action=showEquipments'>Show All Equipments</a>&nbsp;&nbsp;&nbsp;&nbsp;\n");
	    out.println("<a href='admin?action=logout'>Logout</a>"); 
	    out.println("</h2><br/><br/><br/>" );
	    out.println("</div>");
	    out.println("<div style=margin-left:250px; ></br>");
		if (action.equals("addNewUser")){
        	registrationForm(request, response); 		 
		}
		
		else if (action.equals("addNewEquipment")){
			addEquipment(request, response);
		}
		
		else if (action.equals("showUsers")){
			try {
				out.println("<table width='80%' border='1px'><tr><th>User Id</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Password</th><th>Action</th></tr>");
				showUsers(request, response);
				out.println("</table>");
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (action.equals("showEquipments")){
			try {
				out.println("<table width='80%' border='1px'><tr><th>Equipment Name</th><th>Insurance Fee</th><th>Action</th></tr>");
				displayEquipments(request, response);
				out.println("</table>");
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		else if (action.equals("logout")){
			HttpSession session = request.getSession(true);
				session.invalidate();
				response.sendRedirect("/amart/");
			
		}	
		out.println("</body>");
	    out.println("</html>");
			
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
            
       if (action.equals("register")){
            	try {
            		insertRegisteredUsers(request, response);
    			} catch (ClassNotFoundException | SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} 	
            	 
		}//end of client
		
		else if (action.equals("addEquipments")){
			try {
				insertEquipments(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
}

public void registrationForm(HttpServletRequest request,HttpServletResponse response)  throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter regForm = response.getWriter();
    regForm.println("<div style=margin-left:200px; ></br>");
    regForm.println("<h1>User Registration</h1>"); 
    regForm.println("</div>");
    regForm.println("<div class=\"login-form\" style=\"float:left; margin-left:250px;\">" +
			"<form method=\"POST\" action=\"admin?action=register\" id=\"RegistrationForm\">" +
						"<h2>First Name:</h2>" +
					"<label>" +
						"<input type=\"text\" id=\"firstName\"  name=\"firstName\" maxlength=\"255\" value=\"\" />" +
					"</label>" +
					"<h2>Last Name:</h2>" +
					"<label>" +
						"<input type=\"text\" id=\"lastName\"  name=\"lastName\" maxlength=\"255\" value=\"\" />" +
					"</label>" +
					"<h2>Email:</h2>" +
					"<label>" +
						"<input type=\"text\" id=\"email\"  name=\"email\" maxlength=\"255\" value=\"\" />" +
					"</label>" +
					
					"<h2>Password:</h2>" +
					"<label>" +
						"<input type=\"text\" id=\"password\"  name=\"password\" maxlength=\"255\" value=\"\" />" +
					"</label>" +
						 
					"<h2>Confirm Password:</h2>" +
					"<label>" +
						"<input type=\"text\" id=\"firstName\"  name=\"firstName\" maxlength=\"255\" value=\"\" />" +
					"</label>" +		 
				"</br>" +
					"<div style=\"margin-top:20px;\">" +
					"<input type=\"submit\"  class=\"login-btns\" id=\"btn-send\" name=\"action\" value=\"Register\" /> " +
					"</div>" + 
				
			"</form>" +
		"</div>");
    
     
    
}

public void addEquipment(HttpServletRequest request,HttpServletResponse response)  throws ServletException, IOException {
    PrintWriter addEquip=response.getWriter();
    addEquip.println("<div style=margin-left:150px; ></br>");
    addEquip.println("<h1>Add An Insurance Equipment</h1>"); 
    addEquip.println("</div>");
    addEquip.println("<div class=\"login-form\" style=\"float:left; margin-left:250px;\">" +
			"<form method=\"POST\" action=\"Admin?action=addEquipments\" id=\"EquipmentForm\">" +
					"<h2>Equipment Name:</h2>" +
				"<label>" +
					"<input type=\"text\" id=\"equipmentName\"  name=\"equipmentName\" maxlength=\"255\" value=\"\" />" +
				"</label>" +
				"<h2>Insurance Fee:</h2>" +
				"<label>" +
					"<input type=\"text\" id=\"insuranceFee\"  name=\"insuranceFee\" maxlength=\"255\" value=\"\" />" +
				"</label>" +
				"<div style=\"margin-top:20px;\">" +
				"<input type=\"submit\"  class=\"login-btns\" id=\"btn-send\" name=\"action\" value=\"Add Equipment\" /> " +
				"</div>" + 
			
		"</form>" +
	"</div>");
    
    
    
}

public void insertEquipments(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ClassNotFoundException{
	PrintWriter out = response.getWriter();
	try{
		 //loading driver 
	         Class.forName("com.mysql.jdbc.Driver");
	 	 //creating connection with the database 
	         Connection connect=DriverManager.getConnection
	                        ("jdbc:mysql://localhost:3306/amart","cmsuser","cmspass");
	
		String equipmentName=request.getParameter("equipmentName");
		String insuranceFee=request.getParameter("insuranceFee");
		String sql = "insert into equipment values (?,?,?)"; 
		PreparedStatement prep = connect.prepareStatement(sql); // Setting the values which we got from JSP form 
		prep.setString(1,null); 
		prep.setString(2, equipmentName); 
		prep.setString(3, insuranceFee);
		int i = prep.executeUpdate(); 
		if(i>0)
	    {
	        out.println("You are sucessfully Add the Equipment");
	        response.sendRedirect("admin?action=showEquipments&&message=success");
	    }
		prep.close();

	}catch(Exception e)
    {
        e.printStackTrace();
    }
}

public void showUsers(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ClassNotFoundException{
	PrintWriter out = response.getWriter();
	try{
		 //loading driver 
	         Class.forName("com.mysql.jdbc.Driver");
	 	 //creating connection with the database 
	         Connection connect=DriverManager.getConnection
	                        ("jdbc:mysql://localhost:3306/amart","cmsuser","cmspass");
         
    	PreparedStatement ps =connect.prepareStatement
                ("select * from users");
    	ResultSet results =ps.executeQuery();
     
    	while (results.next()){
  		  
  		  		//String equipmentId = results.getString("id");
    			int userId = results.getInt("id");
  		  		String firstName = results.getString("firstName");
            	String lastName = results.getString("lastName");
            	String email = results.getString("email");
            	String password = results.getString("password");
            	
        		out.println("<form method='POST' action=?action=userDelete>");
        		out.println("<tr><td width = '30%'>" + userId + "</td>" +
                      "<td width = '30%'>" + firstName + "</td>"
                      +"<td width = '30%'>"+ lastName + "</td>"    
                      +"<td width = '30%'>" + email + "</td>"
                      +"<td width = '30%'>" + password + "</td>");
        		 
        		out.println("<td width = '30%'><input type='submit' name='action' value='Delete User'/></td></tr>");
            		
        		out.println("</form>");
	  }
	  
	    	 
    }catch(Exception e)
    {
        e.printStackTrace();
    }
    
}

public  void displayEquipments(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ClassNotFoundException{
	PrintWriter out = response.getWriter();
	
	try{
		 //loading driver 
	         Class.forName("com.mysql.jdbc.Driver");
	 	 //creating connection with the database 
	         Connection connect=DriverManager.getConnection
	                        ("jdbc:mysql://localhost:3306/amart","cmsuser","cmspass");
         
    	PreparedStatement ps =connect.prepareStatement
                ("select * from equipment");
    	ResultSet results =ps.executeQuery();
     
    	while (results.next()){
  		  
  		  //String equipmentId = results.getString("id");
    		String equipmentId = results.getString("id");
  		  	String equipmentName = results.getString("equipmentName");
            String insuranceFee = results.getString("insuranceFee");
            	
        		out.println("<form method='POST' action=?action=addBonus>");
        		out.println("<tr><td width = '30%'>" + equipmentName + "</td>" +
                      "<td width = '30%'>" + insuranceFee + "</td>");
        		out.println("<input type='hidden' name='equipmentName' value='" + equipmentId +"'/>");
        		out.println("<td width = '30%'><input type='submit' name='action' value='Delete'/></td></tr>");
        		out.println("</form>");
	  }
	  
	    	   
    }catch(Exception e)
    {
        e.printStackTrace();
    }
    
}
public void insertRegisteredUsers(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ClassNotFoundException{
	PrintWriter out = response.getWriter();
	HttpSession userSession = request.getSession();
	  String userid = (String)userSession.getAttribute("user");
	try{
		 //loading driver 
	         Class.forName("com.mysql.jdbc.Driver");
	 	 //creating connection with the database 
	         Connection connect=DriverManager.getConnection
	                        ("jdbc:mysql://localhost:3306/amart","cmsuser","cmspass");
	        	String firstName=request.getParameter("firstName");
			String lastName=request.getParameter("lastName");
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			//String password1=request.getParameter("password1");
			String level = "1";
			
			String sql = "insert into users values (?,?,?,?,?,?)"; 
			PreparedStatement prep = connect.prepareStatement(sql); // Setting the values which we got from JSP form 
			prep.setString(1,null); 
			prep.setString(2, firstName); 
			prep.setString(3, lastName);
			prep.setString(4, email); 
			prep.setString(5, password);
			prep.setString(6, level);
			int i = prep.executeUpdate(); 
			if(i>0)
		    {
		        out.println("You are sucessfully Registered");
		        if (userid != null){
		        	response.sendRedirect("admin?action=showUsers&&message=success");
		        }else{
		        	response.sendRedirect("/amart/?message=Registration was successful please Login");
		        	
		        }
		    }
			prep.close();
	}catch(Exception e)
    {
        e.printStackTrace();
    }
	}
}
