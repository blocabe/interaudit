<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>	
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<script type="text/javascript">
$(document).ready(function()  {
			<c:if test="${not empty actionErrors}">
				<c:forEach var="actionError" items="${actionErrors}">					
					showMessage("${actionError}","error");
				</c:forEach>
				<c:set var="actionErrors" value="" scope="session" />
			</c:if>
			<c:if test="${not empty successMessage}">
				<c:forEach var="message" items="${successMessage}">
					showMessage("${message}","ok");
				</c:forEach>
				<c:set var="successMessage" value="" scope="session" />
			</c:if>
});

</script> 
<script>
	var selectedColumn = null;
	var selectedStatus = true;
  // set the check box status from the controller
  function setCheckBox(boxId)
  {
    var box = document.getElementById(boxId);
    if (box != null)
    {
      box.checked = true;
    }
  } 

  // format the data to the controller 
  // that match withe the cheched boxes
  // moduleId##roleId, ....... 
  function getCheckBoxes()
  {
    var result = "";
    var boxes = document.getElementsByName("userRights");
    var count = boxes.length;
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].checked)
        {
          if (result.length > 0) result += ",";
          result += boxes[index].id; 
        }
      }
    }
    return result;
  }

  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }

  function updateRightsUser()
  {
    document.userRightsForm.rights.value = getCheckBoxes();
	 document.userRightsForm.type.value = document.TypeForm.type.value;
	 document.userRightsForm.employee.value = document.TypeForm.employee.value;
    document.userRightsForm.submit();
  }


   function swapCol(obj)
  {
    var id = obj.id;
    
    if (selectedColumn != id ){
		setCheckCol(id, true); 
		selectedColumn = id; 
		selectedStatus = true;
    }
    else 
	{
    
		if (!selectedStatus){
			setCheckCol(selectedColumn, true); 
		}
		else {
			setCheckCol(selectedColumn, false); 
		}
    
        selectedStatus = !selectedStatus;
    }
    

  }
  
  function setCheckCol(id, flag)
  {
 
    var result = "";
    var boxes = document.getElementsByTagName("input");
    var count = boxes.length;
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].id.indexOf("##"+id) > 0)
        {
          boxes[index].checked = flag;
        }
	
      }
    }  
  }
  
  function setCheckRow(id, flag)
  {
	
    var result = "";
    var boxes = document.getElementsByName("userRights");
    var count = boxes.length;
	
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].id.indexOf(id+"##") == 0)
        {
          boxes[index].checked = flag;
        }
      }
    }  
  }

  function swapRow(obj)
  {
    var id = obj.id;
	
    if (id != null && id.charAt(0)=='s')
    {
      obj.id = id.substring(1);
      setCheckRow(obj.id, false);
    }
    else
    {
      obj.id = 's'+id; 
      setCheckRow(id, true);
    }   
  }
  
 
</script>



<!-- START ADDED PART -->   

<style>
tr.odd td.menu-disabled {
  background-color: #edf5fa;
}

td.region, td.module, td.container, td.category {
  border-top: 1.5em solid #fff;
  border-bottom: 1px solid #b4d7f0;
  background-color: #d4e7f3;
  color: #455067;
  font-weight: bold;
}

input.button120 {
	width:120px;
	margin:1px 0 1px 0;
	background-color:rgb(241,241,237);
	font-family:tahoma;
	font-size:11px;
	border:1px solid;
	border-top-color:rgb(0,60,116);
	border-left-color:rgb(0,60,116);
	border-right-color:rgb(0,60,116);
	border-bottom-color:rgb(0,60,116);
	border-style: outset;
}


#ver-zebra
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 10px;
	/*margin: 45px;*/
	/*width: 480px;*/
	text-align: center;
	border-collapse: collapse;
}
#ver-zebra th
{
	font-size: 12px;
	font-weight: normal;
	/*padding: 4px 7px;*/
	border-top: 1px solid #0066aa;
	border-right: 1px solid #0066aa;
	border-bottom: 1px solid #0066aa;
	border-left: 1px solid #fff;
	color: #039;
	background: #eff2ff;
}
#ver-zebra td
{
	/*padding: 4px 7px;*/
	border-right: 1px solid #0066aa;
	/*border-left: 2px solid #fff;*/
	border-bottom: 1px solid #0066aa;
	color: #669;
}
.vzebra-odd
{
	/*background: #eff2ff;*/
	background: #fff;
}
.vzebra-even
{
	background: #e8edff;
}
#ver-zebra #vzebra-adventure, #ver-zebra #vzebra-children
{
	background: #d0dafd;
	border-bottom: 1px solid #c8d4fd;
}
#ver-zebra #vzebra-comedy, #ver-zebra #vzebra-action
{
	background: #dce4ff;
	border-bottom: 1px solid #d6dfff;
}

.normal { background-color: white }
.highlight { background-color: orange }
</style>
			
<div style="border-bottom: 1px solid gray; background-color: white;">

			

<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
	<form name="TypeForm" action="${ctx}/showUserAccessRightsPage.htm" method="post" >
		 
	
	
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Menu</strong> : </span>
		<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="type" onchange="document.TypeForm.submit();">
	            <!--option value="All" <c:if test='${viewAccessRights.type=="All"}'> selected</c:if>>Any</option-->
				<option value="BUDGET" <c:if test='${viewAccessRights.type=="BUDGET"}'> selected</c:if>>BUDGETS</option>
				<option value="PLANNING" <c:if test='${viewAccessRights.type=="PLANNING"}'> selected</c:if>>PLANNING</option>
				<option value="TIMESHEET" <c:if test='${viewAccessRights.type=="TIMESHEET"}'> selected</c:if>>TIMESHEETS</option>
				<option value="INVOICE" <c:if test='${viewAccessRights.type=="INVOICE"}'> selected</c:if>>FACTURATION</option>
				<option value="SEARCH" <c:if test='${viewAccessRights.type=="SEARCH"}'> selected</c:if>>SEARCH</option>
				<option value="NEW" <c:if test='${viewAccessRights.type=="NEW"}'> selected</c:if>>NEW</option>
				<option value="DROITS" <c:if test='${viewAccessRights.type=="DROITS"}'> selected</c:if>>ADMINISTRATION</option>

				<!--option value="BANK" <c:if test='${viewAccessRights.type=="BANK"}'> selected</c:if>>BANK</option>
				<option value="DOCUMENT" <c:if test='${viewAccessRights.type=="DOCUMENT"}'> selected</c:if>>DOCUMENT</option>
				<option value="PAYMENT" <c:if test='${viewAccessRights.type=="PAYMENT"}'> selected</c:if>>PAYMENT</option>
				<option value="EMPLOYEE" <c:if test='${viewAccessRights.type=="EMPLOYEE"}'> selected</c:if>>EMPLOYEE</option>				
				<option value="CONTRAT" <c:if test='${viewAccessRights.type=="CONTRAT"}'> selected</c:if>>CONTRAT</option>
				<option value="CONTACT" <c:if test='${viewAccessRights.type=="CONTACT"}'> selected</c:if>>CONTACT</option>
				<option value="CLIENT" <c:if test='${viewAccessRights.type=="CLIENT"}'> selected</c:if>>CLIENT</option>
				<option value="DECLARATION" <c:if test='${viewAccessRights.type=="DECLARATION"}'> selected</c:if>>DECLARATION</option-->
				
		</select>
		
		<%--span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Profile</strong> : </span>
						<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="role">
						   <option value="All">Any</option>
							<c:forEach var="r" items="${viewAccessRights.roles}">
								<option value="${r.name}"<c:if test='${viewAccessRights.roleName==r.name}'> selected</c:if>>${r.name}</option>
							</c:forEach>
						</select--%> 

		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employee</strong> : </span>
						<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="employee" onchange="document.TypeForm.submit();">						
							<c:forEach var="e" items="${viewAccessRights.employeeOptions}">
								<option value="${e.id}" <c:if test='${viewAccessRights.employeeId eq e.id}'> selected</c:if>>${e.name}</option>
							</c:forEach>
						</select> 
		

		&nbsp;
		<!--input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/-->		
	</form>
</div>
			
<br/>


<form name="userRightsForm" action="admin-update-right-user.htm" method="post" >
  <input type="hidden" name="type">
   <input type="hidden" name="employee">
  <input type="hidden" name="rights">
					<%--table width="100%" border="0" cellspacing="0" cellpadding="3" class="axial">
					<tr>
					  <td align="center">
						<input type="button" class="button120" value="Apply changes" onclick="updateRightsUser()">
						<input type="button" class="button120" value="Cancel" onclick="cancelForm()">
					  </td>
					</tr>
					</table--%>
			    
			        <table  width="100%" cellspacing="0" >
			        	<caption align="top"><span style="color:blue;">${viewAccessRights.type} Access rights for &nbsp;${viewAccessRights.currentEmployeName}</span></caption>
									
									<thead>
									<tr class="odd"><td class="module" id="module-admin_menu" colspan="11">${viewAccessRights.type}</td> </tr>

									<%--tr class="odd">
										<th scope="col"></th>
										<c:set var="row" value="0"/>
										<c:forEach var="item" items="${viewAccessRights.employees}" varStatus="loop">
											<c:choose>
										    		<c:when test='${row % 2 eq 0 }'>
														<th style="background-color: #fff;" scope="col" id="${item.code}" onclick="swapCol(this)" style="cursor:hand;">
										      			
										      		</c:when>
										      		<c:otherwise>
													<th style="background-color: #edf5fa;" scope="col" id="${item.code}" onclick="swapCol(this)" style="cursor:hand;">
										      			
										      		</c:otherwise>
										     	</c:choose>
											
												<t:tooltip>  
														   <t:text><em>${fn:substring(fn:toUpperCase(item.code), 0, 3)}</em></t:text>
														  <t:help><font color="STEELBLUE"><strong>
															 ${item.lastName}  ${item.firstName}
															</strong></font></t:help> 
												</t:tooltip>
											</th>
										</c:forEach>										
									</tr--%>
									</thead>
									<tbody>
										<c:set var="row" value="0"/>
										<c:forEach var="right" items="${viewAccessRights.rights}" varStatus="loop">

											<c:choose>
										    		<c:when test='${row % 2 eq 0 }'>
										      			<tr style="background-color: #fff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
										      		</c:when>
										      		<c:otherwise>
										      			<tr style="background-color: #edf5fa;"  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
										      		</c:otherwise>
										     	</c:choose>
										
											<!--tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'"-->
												<th  onclick="swapRow(this)" id="${right.id}" style="cursor:hand;border-right:none;" scope="row" class="column1" align="left">&nbsp;
												<t:tooltip>  
	                                                               <t:text><em>
																																																											
																	<span style=" color: black;"><i>&nbsp;&nbsp;&nbsp;* &nbsp;${right.name}</i></span>
																	
	                                                               </em></t:text>
	                                                              <t:help width="200">
	                                                               <font color="blue"><strong>${right.description}</strong></font><br/><hr/> 
	                                                              
	                                                                </t:help> 
	                                                            </t:tooltip>
												
												</th>
												<c:forEach var="employee" items="${viewAccessRights.employees}" varStatus="loop">
													<td>
													<INPUT  type="checkbox" name="userRights" id="${right.id}##${employee.code}" value="1" 
													title="${right.description} for ${employee.lastName} ${employee.firstName}" >
													</td>
												</c:forEach>
												<%--th  onclick="swapRow(this)" id="${right.id}" style="cursor:hand;width : 200px;" scope="row" class="column1" align="left">&nbsp;<span style=" color: purple;"></span></th--%>
												
											</tr>
										<c:set var="row" value="${row + 1}"/>  
										</c:forEach>
									</tbody>
								</table>
						<hr/>
						<table width="100%" border="0" cellspacing="0" cellpadding="3" class="axial">
							<tr>
							  <td align="center">
								<input type="button" class="button120" value="Apply changes" onclick="updateRightsUser()">
								<input type="button" class="button120" value="Cancel" onclick="cancelForm()">
							  </td>
							</tr>
						</table>
						</div>
						<br/>
		</div>
		
		<script>
			<c:forEach var="c" items="${viewAccessRights.listBoxes}">
			    setCheckBox('${c}');
			</c:forEach>  
		</script>
 </form>