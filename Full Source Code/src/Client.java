 /*
AMPAIRE CHRISTINE    210008949 10/U/9183/PS
ABOK ISAAC 210007924 10/U/9168/PS
OTIM FREDRICK 212022189 12/U/13682/PS
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.*;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Client
 */
@WebServlet(name="clients",urlPatterns={"/clients"})
public class Client extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	//HashMap which will hold details about items in the shopping Cart.
    HashMap<String, Integer> insuranceItems;
    public Client() {
        super();
        insuranceItems = new HashMap<>();
        // TODO Auto-generated constructor stub
    }
    
    //Function to retrieve insured items.
    public HashMap getInsuranceItems(){
        return insuranceItems;
    }
    
    //Function to retrieve Bonus items.
    public HashMap getInsuranceBonusItems(){
        return insuranceItems;
    }
    
    //Function to add insurance items.
    public void addToInsuranceItems(String itemId, int fee){
    	insuranceItems.put(itemId, fee);
    }
    //Function to delete insured items.
    public void deleteFromCart(String itemId){
    	insuranceItems.remove(itemId);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String action = request.getParameter("action");
			
			//Main Menu
			if(action.equals("home")){
				try {
					showAllEquipments(request, response);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				//view the items insured
			 }else if(action.equals("viewInsurance")){
			    		try {
							bonusProcess(request, response);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    //Agrees with the terms and conditons		
			  }else if(action.equals("agreeTerms")){
				    	  
						try {
							agreeTerms(request, response);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	          
							    	  
				} 
			    else if(action.equals("viewtotalInsuranceCover")){
				    	  
			    		try {
							totalInsuredItems(request, response);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	          
							    	  
				}//Add bonus items
			    else if(action.equals("addBonusItem")){
			    	  
					try {
						displayEquipments(request, response);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	          
						    	  
				}else if (action.equals("logout")){
					HttpSession session = request.getSession();
					session.invalidate();
					response.sendRedirect("/amart/");
				
			}
	                     
}   

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		//out.println("You Are login in");
		boolean status = false;
		try{
			 //loading driver 
		         Class.forName("com.mysql.jdbc.Driver");
		 	 //creating connection with the database 
		         Connection con=DriverManager.getConnection
		                        ("jdbc:mysql://localhost:3306/amart","cmsuser","cmspass");
		         
		         if (action.equals("clientLogin")){
		        	 String email = request.getParameter("email");
		 			 String pass = request.getParameter("password");
			         PreparedStatement ps =con.prepareStatement
			                             ("select * from users where email=? and password=?");
			         ps.setString(1, email);
			         ps.setString(2, pass);
			         ResultSet rs =ps.executeQuery();
			         status = rs.next();
		         
		         if (status){
		        	 String userid = rs.getString("id");
		        	 String name = rs.getString("firstName");
		        	 String password = rs.getString("password");
		        	 //out.println("You are Login" + userid);
		        	 HttpSession userSession = request.getSession();
		        	 userSession.setAttribute("user", userid);
		        	 userSession.setAttribute("username", name);
		             
		             if(password.equals("admin123")){  
			             out.print("Welcome, "+ name);   
			             userSession.setAttribute("name",name);  
			             response.sendRedirect("admin?action=showEquipments");
			         }else{
		             //out.println("Welcome" + name);
		             response.sendRedirect("clients?action=home");
			         }   
		             
		         } else{
		        	 out.println("Wrong Username or Password");
		        	 response.sendRedirect("/amart/?error=Wrong Username or Password Please Try Again");
		        	 
		         }
		      }
		         else if(action.equals("addInsurance")){
		    	  
		    	  processInsuranceCover(request, response);	 
		    	  
		    	  
		         }
				else if(action.equals("addBonus")){
						    	  
					processBonusInsuranceCover(request, response);	          
						    	  
				}
				  
				   
				else if(action.equals("add-more")){
			    	  
					response.sendRedirect("clients?action=home");          
						    	  
				}
		      else if(action.equals("addInsuranceCover")){
		    	  	
		    	  insertInsuranceCover(request, response);
		    	  	   
		    	   
                  response.sendRedirect("clients?action=home&&message=success");
		    	  
		      }
		        
		      }catch(Exception e)
		      {
		          e.printStackTrace();
		      }
		                       
		  }
	
	//methods shows all the equipments
	public void showAllEquipments(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ClassNotFoundException{
		
		PrintWriter out = response.getWriter();
		
		try{
		 //loading driver 
	         Class.forName("com.mysql.jdbc.Driver");
	 	 //creating connection with the database 
	         Connection con=DriverManager.getConnection
	                        ("jdbc:mysql://localhost:3306/amart","cmsuser","cmspass");
		
	    	  HttpSession userSession = request.getSession();
	    	  String userid = (String)userSession.getAttribute("user");
	          String user = (String)userSession.getAttribute("username");  
	          
	    	  //out.println("Welcome " + user + "And User id is "+ userid);
	    	  PreparedStatement ps =con.prepareStatement
                        ("select * from equipment");
	    	  ResultSet results =ps.executeQuery();
	    	  
	    	  String message = request.getParameter("message");
	    	  
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
	    	  out.println("<h1 align=\"center\" style=\"color:blue; font-size:30px;\" >Welcome "+ user + "  <a href='clients?action=logout'>Logout</a></h1> <br>");
	    	  out.println("<hr><br>");
	    	  out.println("<div style=margin-left:250px; ></br>");
	    	  out.println("<table width='80%' border='1px'><tr><th>Equipment Name</th><th>Insurance Fee</th><th>Year</th><th>Action</th></tr>");
	    	   if (message == null){
	    		   out.println();
	    	   }
	    	   else{
	    		   out.println("<div style=\"color:green\">Your Record Was Added successfully</div>");
	    		   out.println("</br>");
	    	   }
	    	   
	    	  while (results.next()){
	    		  
	    		  //String equipmentId = results.getString("id");
	    		  	String equipmentName = results.getString("equipmentName");
	              	String insuranceFee = results.getString("insuranceFee");
	              	
             		out.println("<form method='POST' action=?action=addInsurance>");
             		out.println("<tr><td width = '30%'>" + equipmentName + "</td>" +
	                        "<td width = '30%'>" + insuranceFee + "</td>"+
	                		"<td width = '30%'><select name='year'>"
	                		+ "<option value='1'>1</option>"
	                		+ "<option value='2'>2</option>"
	                		+ "<option value='3'>3</option>"
	                		+ "<option value='4'>4</option>"
	                		+ "<option value='5'>5</option>"
	                		+ "</select></td>");
             		out.println("<input type='hidden' name='equipmentName' value='" + equipmentName +"'/>");
             		out.println("<input type='hidden' name='insuranceFee' value='" + insuranceFee +"'/>");
             		out.println("<td width = '30%'><input type='submit' name='action' value='Add Insurance'/></td></tr>");
	              		
             		out.println("</form>");
	    	  }
	    	  
			    	  out.println("</table>");
			    	  
			    	  out.println("<h1>Eqiupments you have insured:</h1>");
			    	  
				         PreparedStatement insuranceresults =con.prepareStatement
				                             ("select * from insurance where user_id=? and bonus=?");
				         insuranceresults.setString(1, userid);
				         insuranceresults.setInt(2, 0);
				         ResultSet rs =insuranceresults.executeQuery();
				         boolean status = rs.next();
				         out.println("<table width='80%' border='1px'><tr><th>Ref. No</th><th>Equipment Name</th><th>End Year</th></tr>");
				         if(status){
				    	  while(rs.next())
				    	  {
					    	  Integer refId = rs.getInt("id");
					    	  String equipmentName = rs.getString("equipmentName");
					    	  //String insuracefee = rs.getString("insuranceFee");
					    	  String year =rs.getString("year");
					    	  out.println("<tr>");
					    	  out.println("<td width = '30%'>"+refId+"</td>");
					    	  out.println("<td width = '30%'>"+equipmentName+"</td>");
					    	  //out.println("<td width = '30%'>"+fee+"</td>");
					    	  out.println("<td >"+year+"</td>");
					    	  out.println("</tr>");
					    	  
				    	  }  
				    	  out.println("</table>");
				         }else{
				        	 out.println("No Items Founds");
				        	 
				         }
				         
				         out.println("<h1>Eqiupments insured Under Bonus:</h1>");
				    	  
				         PreparedStatement insuranceres =con.prepareStatement
				                             ("select * from insurance where user_id=? and bonus=?");
				         insuranceres.setString(1, userid);
				         insuranceres.setInt(2, 1);
				         ResultSet rss =insuranceres.executeQuery();
				         boolean status1 = rss.next();
				         out.println("<table width='80%' border='1px'><tr><th>Ref. No</th><th>Equipment Name</th><th>End Year</th></tr>");
				         if(status1){
				    	  while(rss.next())
				    	  {
					    	  Integer refId1 = rss.getInt("id");
					    	  String equipmentName1 = rss.getString("equipmentName");
					    	  //String insuracefee = rs.getString("insuranceFee");
					    	  String year1 =rss.getString("year");
					    	  out.println("<tr>");
					    	  out.println("<td width = '30%'>"+refId1+"</td>");
					    	  out.println("<td width = '30%'>"+equipmentName1+"</td>");
					    	  //out.println("<td width = '30%'>"+fee+"</td>");
					    	  out.println("<td >"+year1+"</td>");
					    	  out.println("</tr>");
					    	  
				    	  }  
				    	  out.println("</table>");
				         }else{
				        	 out.println("No Items Founds");
				        	 
				         }
			    	  out.println("</div");
			    	  out.println("</body></html>");
		}catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	//method displays the bonus equipments when the total exceeds 1 million
	public void displayEquipments(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ClassNotFoundException{
		PrintWriter out = response.getWriter();
		try{
			 //loading driver 
		         Class.forName("com.mysql.jdbc.Driver");
		 	 //creating connection with the database 
		         Connection con=DriverManager.getConnection
		                        ("jdbc:mysql://localhost:3306/amart","cmsuser","cmspass");
		         
		    	  HttpSession session = request.getSession();
		    	   
		    	  String userid = (String)session.getAttribute("user");
		          String user = (String)session.getAttribute("username");  
		    	  PreparedStatement ps =con.prepareStatement
                            ("select * from equipment");
		    	  ResultSet results =ps.executeQuery();
		    	  
		    	  String message = request.getParameter("message");
		    	  out.println("<table width='80%' border='1px'><tr><th>Equipment Name</th><th>Insurance Fee</th><th>Year</th><th>Action</th></tr>");
		    	   if (message == null){
		    		   out.println();
		    	   }
		    	   else{
		    		   out.println("Your Record Was Added successfully");
		    	   }
		    	   
		    	   while (results.next()){
			    		  
			    		  //String equipmentId = results.getString("id");
			    		  	String equipmentName = results.getString("equipmentName");
			              	String insuranceFee = results.getString("insuranceFee");
			              	
		              		out.println("<form method='POST' action=?action=addBonus>");
		              		out.println("<tr><td width = '30%'>" + equipmentName + "</td>" +
			                        "<td width = '30%'>" + insuranceFee + "</td>"+
			                		"<td width = '30%'><select name='year'>"
			                		+ "<option value='1'>1</option>"
			                		+ "<option value='2'>2</option>"
			                		+ "<option value='3'>3</option>"
			                		+ "<option value='4'>4</option>"
			                		+ "<option value='5'>5</option>"
			                		+ "</select></td>");
		              		out.println("<input type='hidden' name='equipmentName' value='" + equipmentName +"'/>");
		              		out.println("<input type='hidden' name='insuranceFee' value='" + insuranceFee +"'/>");
		              		out.println("<td width = '30%'><input type='submit' name='action' value='Add Insurance'/></td></tr>");
			              		
		              		out.println("</form>");
		    	  }
		    	  
				    	  out.println("</table>");
				 	 	  
       
	    }catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	//methods proccesses the bonus equipments
	public void processBonusInsuranceCover(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Client shoppingBonusCart;
        shoppingBonusCart = (Client) session.getAttribute("bonusCart");
        if(shoppingBonusCart == null){
        	shoppingBonusCart = new Client();
          session.setAttribute("bonusCart", shoppingBonusCart);  
        }
        String name = request.getParameter("equipmentName");
        Integer fee = Integer.parseInt(request.getParameter("insuranceFee"));
        String year = request.getParameter("year");
        double bonusAmount1 = (double)session.getAttribute("bonusAmount");
        
        if (request.getParameter("delete") != null){
        	shoppingBonusCart.deleteFromCart(name);
        	
        }  
        Date dateNow = new Date( );
        SimpleDateFormat dateFormatYear  =  new SimpleDateFormat ("yyyy");
        SimpleDateFormat dateFormatMonth  =  new SimpleDateFormat ("MMMM");
        
        int totalYear = Integer.parseInt(dateFormatYear.format(dateNow)) + Integer.parseInt(year);
       
        String startYear = dateFormatMonth.format(dateNow) +" "+  dateFormatYear.format(dateNow);
        String endYear = dateFormatMonth.format(dateNow)+ " " + Integer.toString(totalYear);
        
        if(fee > bonusAmount1){
        	
        }else{
        	shoppingBonusCart.addToInsuranceItems(name, fee);
            session.setAttribute("bonusCart", shoppingBonusCart);
        }
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Your Bonus Cart</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=margin-left:250px; ></br>");
            out.println("</div>");
            out.println("<hr>");
            out.println("<div style=margin-left:250px; ></br>");
            out.println("<h2>You Bonus Equipment Cart</h2>");
            HashMap<String, Integer> items = shoppingBonusCart.getInsuranceBonusItems();
            out.println("<table border='1px'><tr><th>Equipment Name</th><th>Insurance Fee</th><th>Start Year</th><th>End Year</th></tr>");
             
        	int total=0;
        	if (items.isEmpty()) {
         	   out.println("<h1>Don't Have Bonus Items</h1>");
        		}else{
            for(String key: items.keySet()){
            	 total= total + items.get(key);
                //out.println("<tr><td>"+key+" - </td><td>"+"$"+items.get(key)+"</td>");
                out.println("<tr><td>"+key+"  </td><td>"+"UGX "+items.get(key)+"</td>"
                		+ "<td>" + startYear + "</td>"
                		+"<td>" + endYear + "</td>"
                		+ "<td><form method='GET' action=?action=addInsurance'>"
                		+ "<input type='hidden' name='equipmentName' value='"+key+"'><input type='submit' name='delete' value='delete'></td></tr></form>");
            		}
        		}
            out.println("<table><br><br>");
            
            out.println("Total: UGX " + total);
            out.println("</div></br>");
            out.println("</div></br>");
          //Keep the total in a session
            session.setAttribute("totalAmount", total);
            
            double bonusAmount = (double)session.getAttribute("bonusAmount");
            int totalAmount = (int)session.getAttribute("totalAmount");
            double bonusBalance = bonusAmount - totalAmount;
             
             
            	 
            	if(fee > bonusAmount){
             	out.println("<h1>Bonus Balance: UGX "+ bonusBalance +"</h1>");
             	out.println("<h1>You cannot Add More Items Click Next</h1>");
                 
             	
	             }else{
	             out.println("<h1>Bonus Insurance Item successfully added to cart </h1>");
	             out.println("Add more Insurance Equipment<a href='/amart/clients?action=addBonusItem'><br></br><button>Add More</button></a>");
	             }
             
           
            out.println("<br><br><a href='clients?action=agreeTerms'><button>Next Step</button></a>");
            
            out.println("<hr>");
           
            
            out.println("</body>");
            out.println("</html>");
             
       
       }

	}
	
	//methods processes the main insurance cover
	public void processInsuranceCover(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Client shoppingCart;
        shoppingCart = (Client) session.getAttribute("cart");
        if(shoppingCart == null){
          shoppingCart = new Client();
          session.setAttribute("cart", shoppingCart);  
        }
        String name = request.getParameter("equipmentName");
        Integer fee = Integer.parseInt(request.getParameter("insuranceFee"));
        String year = request.getParameter("year");
        
        if (request.getParameter("delete") != null){
        	shoppingCart.deleteFromCart(name);
        	
        }  
        Date dateNow = new Date( );
        SimpleDateFormat dateFormatYear  =  new SimpleDateFormat ("yyyy");
        SimpleDateFormat dateFormatMonth  =  new SimpleDateFormat ("MMMM");
        
        int totalYear = Integer.parseInt(dateFormatYear.format(dateNow)) + Integer.parseInt(year);
       
        String startYear = dateFormatMonth.format(dateNow) +" "+  dateFormatYear.format(dateNow);
        String endYear = dateFormatMonth.format(dateNow)+ " " + Integer.toString(totalYear);
        
        shoppingCart.addToInsuranceItems(name, fee);
        session.setAttribute("cart", shoppingCart);
        session.setAttribute("year", year);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Your Cart</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=margin-left:250px; ></br>");
            out.println("<h1>Insurance Item successfully added to cart </h1>");
            out.println("Add more Insurance Equipment<a href='/amart/clients?action=home'><br></br><button>Add More</button></a>");
            out.println("</div>");
            out.println("<hr>");
            out.println("<div style=margin-left:250px; ></br>");
            out.println("<h2>You Equipment Cart</h2>");
            HashMap<String, Integer> items = shoppingCart.getInsuranceItems();
            out.println("<table border='1px'><tr><th>Equipment Name</th><th>Insurance Fee</th><th>Start Year</th><th>End Year</th></tr>");
             
        	int total=0;
            for(String key: items.keySet()){
            	 total= total + items.get(key);
                //out.println("<tr><td>"+key+" - </td><td>"+"$"+items.get(key)+"</td>");
                out.println("<tr><td>"+key+"  </td><td>"+"UGX "+items.get(key)+"</td>"
                		+ "<td>" + startYear + "</td>"
                		+"<td>" + endYear + "</td>"
                		+ "<td><form method='GET' action=?action=addInsurance'>"
                		+ "<input type='hidden' name='equipmentName' value='"+key+"'><input type='submit' name='delete' value='delete'></td></tr></form>");
            }
            out.println("<table><br><br>");
            out.println("Total: UGX " + total);
            out.println("<br><br><a href='?action=viewInsurance'><button>Next Step</button></a>");
            //Keep the total in a session
            //session.setAttribute("totalAmout", total);
            out.println("</div></br>");
            out.println("</body>");
            out.println("</html>");
             
        }

	}


	//this method process the bonus equipments
	public void bonusProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException{
	       PrintWriter out=response.getWriter();

	       HttpSession session = request.getSession();
	        Client shoppingCart;
	        shoppingCart = (Client) session.getAttribute("cart");
	        out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Agree with the terms and conditions</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=margin-left:250px; ></br>");
            HashMap<String, Integer> items = shoppingCart.getInsuranceItems();
 	       int total = 0;
 	       for(String key: items.keySet()){
           	 total= total + items.get(key);
 	       }
 	       out.println("<h1>Total Amount: UGX " + total + "</h1><br>");
 	       if (total < 1000000 ){
 	    	  out.println("<hr>");
 	       	  out.println("<div style=margin-left:250px; ></br>");
 	       	  out.println("<h1>Click Next to Continue</h1><br>");
 	       	  out.println("<br><br><a href='?action=agreeTerms'><button>Next Step</button></a>");
              out.println("</div>");
              out.println("<hr>");
 	    	  
 	       }else{
 	    	  
              double bonusAmount = (0.25*total); 
 	    	  session.setAttribute("bonusAmount", bonusAmount);
 	    	  out.println("<h1>Total Bonus Amount: UGX " + bonusAmount + "</h1><br>");
 	    	  out.println("<h1>Choose a Bonus Insurance Equipment</h1><br>");
 	    	  displayEquipments(request, response);
 	       }
             
	    }
	
	//methods shows the terms to be agreed
	public void agreeTerms(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException{
	       PrintWriter out=response.getWriter();
	       out.println("<hr>");
	       out.println("<div style=margin-left:250px; ></br>");
	       out.println("<div><h1>By Clicking I Agree, You Accept our terms and conditions</h1></div>");
	       out.println("<br><br><a href='?action=viewtotalInsuranceCover'><button>I Agree</button></a>");
	       out.println("</div><br><br>");
	       out.println("<hr>");
	    }
	
	//methods shows the total insurance cover
	public void totalInsuredItems(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException{
	       PrintWriter out=response.getWriter();
	       HttpSession session = request.getSession();
	        Client shoppingCart;
	        Client shoppingBonusCart;
	    
	        shoppingCart = (Client) session.getAttribute("cart");
	        shoppingBonusCart = (Client) session.getAttribute("bonusCart");
	        String insuranceYear = (String) session.getAttribute("year");
	        Date dateNow = new Date( );
	        SimpleDateFormat dateFormatYear  =  new SimpleDateFormat ("yyyy");
	        SimpleDateFormat dateFormatMonth  =  new SimpleDateFormat ("MMMM");
	        
	        int totalYear = Integer.parseInt(dateFormatYear.format(dateNow)) + Integer.parseInt(insuranceYear);
	       
	        String startYear = dateFormatMonth.format(dateNow) +" "+  dateFormatYear.format(dateNow);
	        String endYear = dateFormatMonth.format(dateNow)+ " " + Integer.toString(totalYear);
	        
	        out.println("<!DOCTYPE html>");
           out.println("<html>");
           out.println("<head>");
           out.println("<title>Total Items Insured</title>");            
           out.println("</head>");
           out.println("<body>");
           out.println("<div style=margin-left:250px; >");
           out.println("<h1>Total Items Insured</h1>");
           HashMap<String, Integer> items = shoppingCart.getInsuranceItems();
           out.println("<table border='1px'><tr><th>Equipment Name</th><th>Insurance Fee</th><th>Start Year</th><th>End Year</th></tr>");
           int total=0;
            
           for(String key: items.keySet()){
           	 total= total + items.get(key);
          	  
              //out.println("<tr><td>"+key+" - </td><td>"+"$"+items.get(key)+"</td>");
              out.println("<tr><td>"+key+"  </td><td>"+"UGX "+items.get(key)+"</td>"
              		+ "<td>" + startYear + "</td>"
              		+"<td>" + endYear + "</td>"
              		+ "</tr>");
          }
           out.println("</table>");
           out.println("<h1>Total Insurance Cover: UGX " + total+" /=</h1>");
           out.println("</div><br>");
	       
           out.println("<div style=margin-left:250px; ></br>");
           if(shoppingBonusCart == null){
        	   out.println("</table>");
               out.println("</div><br>");
    	       out.println("<hr><br><br>");
    	       out.println("<div style=margin-left:250px; ></br>");
    	       out.println("<br><br><form method='POST' action='?action=addInsuranceCover'><button>Submit</button></form>");
    	       out.println("</div><br><br>");
    	       out.println("<hr><br>");
             }else{
            out.println("<h1>Total Bonus Items Insured</h1>");
           HashMap<String, Integer> bonusItems = shoppingBonusCart.getInsuranceBonusItems();
           if (bonusItems.isEmpty()) {
        	   out.println("<h1>Don't Have Bonus Items</h1>");
       		}else{
           out.println("<table border='1px'><tr><th>Equipment Name</th><th>Insurance Fee</th><th>Start Year</th><th>End Year</th></tr>");
           for(String key: bonusItems.keySet()){
          	  
              //out.println("<tr><td>"+key+" - </td><td>"+"$"+items.get(key)+"</td>");
              out.println("<tr><td>"+key+"  </td><td>"+"UGX "+bonusItems.get(key)+"</td>"
              		+ "<td>" + startYear + "</td>"
              		+"<td>" + endYear + "</td>"
              		+ "</tr>");
          }
         }
           out.println("</table>");
           out.println("</div><br>");
	       out.println("<hr><br><br>");
	       out.println("<div style=margin-left:250px; ></br>");
	       out.println("<br><br><form method='POST' action='?action=addInsuranceCover'><button>Submit</button></form>");
	       out.println("</div><br><br>");
	       out.println("<hr><br>");
          }
	    }
	
	//method that inserts all the equipments selected to the database
	public void insertInsuranceCover(HttpServletRequest request, HttpServletResponse response)throws IOException, SQLException, ClassNotFoundException{
		PrintWriter out = response.getWriter();
		try{
			 //loading driver 
		         Class.forName("com.mysql.jdbc.Driver");
		 	 //creating connection with the database 
		         Connection con=DriverManager.getConnection
		                        ("jdbc:mysql://localhost:3306/amart","cmsuser","cmspass");
		         
		    	  HttpSession userSession = request.getSession();
		    	  String userid = (String)userSession.getAttribute("user");
		    	  
		    	  HttpSession session1 = request.getSession(false);
		    	  
		          Client shoppingCart;
			      Client shoppingBonusCart;
			        
			      shoppingCart = (Client) session1.getAttribute("cart");
			      shoppingBonusCart = (Client) session1.getAttribute("bonusCart");
			      String insuranceYear = (String) session1.getAttribute("year");
			      Date dateNow = new Date( );
			      SimpleDateFormat dateFormatYear  =  new SimpleDateFormat ("yyyy");
			      SimpleDateFormat dateFormatMonth  =  new SimpleDateFormat ("MMMM");
			        
			      int totalYear = Integer.parseInt(dateFormatYear.format(dateNow)) + Integer.parseInt(insuranceYear);
			       
			      String startYear = dateFormatMonth.format(dateNow) +" "+  dateFormatYear.format(dateNow);
			      String endYear = dateFormatMonth.format(dateNow)+ " " + Integer.toString(totalYear);
		          
			      if(shoppingCart != null){
			    	  HashMap<String, Integer> items = shoppingCart.getInsuranceItems();
			    	  int bonus = 0;
			    	  for(String key: items.keySet()){
			    	    String sql = "insert into insurance values (?,?,?,?,?,?)"; 
		            	PreparedStatement prep = con.prepareStatement(sql);
		            	prep.setString(1,null); 
		            	prep.setString(2, userid); 
		            	prep.setString(3, key);
		            	prep.setString(4, items.get(key).toString());
		            	prep.setString(5, endYear);
		            	prep.setInt(6, bonus);
		            	prep.executeUpdate();
		            	//prep.close();
		            	
			    	  }
			    	  /*
			    	  if (session1 != null){
			    		  session1.invalidate();
			    	  }*/
			    	  
			    	  
			      }
			      
			      if(shoppingBonusCart != null){
			    	  HashMap<String, Integer> items = shoppingBonusCart.getInsuranceBonusItems();
			    	  int bonus = 1;
			    	  for(String key: items.keySet()){
			    	    String sql = "insert into insurance values (?,?,?,?,?,?)"; 
		            	PreparedStatement prep = con.prepareStatement(sql);
		            	prep.setString(1,null); 
		            	prep.setString(2, userid); 
		            	prep.setString(3, key);
		            	prep.setString(4, items.get(key).toString());
		            	prep.setString(5, endYear);
		            	prep.setInt(6, bonus);
		            	prep.executeUpdate();
		            	//prep.close();
		            	
			    	  }
			    	  /*
			    	  if (session1 != null){
			    		  session1.invalidate();
			    	  }	*/
			      }
		          
				 	 	  
       
	    }catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	}
}
