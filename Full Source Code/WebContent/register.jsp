<!--  <%@ include file="header.html" %>-->
<div id="wrapper">
<div class="login-form" style="float:left;">
			<form method="POST" action="admin/add?action=equipments" id="EquipmentForm">  
				<h1>Add Equipment</h1>
				 
						<h2>Equipment Name:</h2>
					<label for="name">
						<input type="text" id="equipmentName"  name="equipmentName" maxlength="255" value="" />
					</label>
				 		
						<h2>Insurance Fee: </h2>
					<label for="fee">	
						<input type="text"   name="insuranceFee" id="insuranceFee" maxlength="50" value=""/>
					<label>	
					<input type="submit"  class="login-btns" id="btn-send" name="action" value="Add" />
					</div>
				
			</form>
		</div>
		
	
	<!-- include the footer html file -->
	<!-- <%@ include file="footer.html" %> -->