
<div id="wrapper"><bR>
<h1 align="center">Welcome Amart Insurance company</h1><bR>
<%
    if (request.getParameter("message") == null) {
        out.println();
    } else {
    	out.println("<div style=\" margin-left:450px;\">");
        out.println("<span style='color:green;'>Hello Your <b align='center'>"+request. getParameter("message")+"</b>!</div>");
        out.println("</div><br>");
    }

	if (request.getParameter("error") == null) {
	    out.println();
	} else {
		out.println("<div style=\" margin-left:450px;\">");
	    out.println("<span style='color:red; text-align:center;'><b>"+request. getParameter("error")+"</b>!</span>");
	    out.println("</div><br>");
	}
%>
<hr>

<div class="login-form" style="float:left; margin-left:150px;">
			<form method="POST" action="clients?action=clientLogin" id="LoginForm">  
				<h1>Memeber Login</h1>
					
						<h2>Email:</h2>
					<label for="email">
						<input type="email" id="email"  name="email" maxlength="255" value="" required />
					</label>
				 
				
						<h2>Password: </h2>
					<label for="password">	
						<input type="password"   name="password" id="password" maxlength="50" value=""/>
					<label>	
					 
					<div style="margin-top:20px;">
					<input type="submit"  class="login-btns" id="btn-send" name="action" value="Login" />
					</div>
					 
				
			</form>
		</div>
<div class="login-form" style="float:left; margin-left:250px;">
			<form method="POST" action="admin?action=register" id="LoginForm">  
				<h1>Registration</h1>
				 
						<h2>First Name:</h2>
					<label for="regno">
						<input type="text" id="firstName"  name="firstName" maxlength="255" value="" />
					</label>
				 
				
						<h2>Last Name: </h2>
					<label for="password">	
						<input type="text"   name="lastName" id="firstName" maxlength="50" value=""/>
					<label>	
					
						<h2>Email:</h2>
					<label for="email">
						<input type="email" id="email"  name="email" maxlength="255" value="" required />
					</label>
				 
				
						<h2>Password: </h2>
					<label for="password">	
						<input type="password"   name="password" id="password" maxlength="50" value=""/>
					<label>	
					
					<h2>Comfirm Password: </h2>
					<label for="password">	
						<input type="password"   name="password1" id="password1" maxlength="50" value=""/>
					<label>			 
				</br>
					<div style="margin-top:20px;">
					<input type="submit"  class="login-btns" id="btn-send" name="action" value="Register" />
					</div>
				
			</form>
		</div>