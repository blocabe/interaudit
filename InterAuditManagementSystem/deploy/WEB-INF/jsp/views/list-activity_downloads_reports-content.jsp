<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>

<script type="text/javascript" src="${ctx}/script/tabs.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>

<script>
   
  function sendReminderTimesheet()
  {
	var answer = confirm("Do you really want to send emails remider for timesheets?")
    if (answer){
      window.location="${ctx}/sendTimesheetReminderEmails.htm";
	}
     
  }
</script>

<!-- START ADDED PART -->   

<style>
input.button120 {
	width:90px;
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

span.pagebanner {
	background-color: #eee;
	border: 1px dotted #999;
	padding: 2px 4px 2px 4px;
	width: 100%;
	margin-top: 10px;
	display: block;
	border-bottom: none;
}

span.pagelinks {
	background-color: #eee;
	border: 1px dotted #999;
	padding: 2px 4px 2px 4px;
	width: 100%;
	display: block;
	border-top: none;
	margin-bottom: -5px;
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
		/*padding: 12px 15px;*/
		border-top: 1px solid #0066aa;
		border-right: 1px solid #0066aa;
		border-bottom: 1px solid #0066aa;
		border-left: 1px solid #fff;
		/*color: #039;
		background: #eff2ff;*/
	}
	#ver-zebra td
	{
		/*padding: 4px 7px;*/
		border-right: 1px solid #0066aa;
		/*border-left: 2px solid #fff;*/
		border-bottom: 1px solid #0066aa;
		/*color: #669;*/
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
#hor-minimalist-b
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	background: #fff;
	/*margin: 10px;*/
	/*width: 750px;*/
	border-collapse: collapse;
	text-align: center;
}
#hor-minimalist-b th
{
	font-size: 12px;
	font-weight: normal;
	color: #039;
	padding: 5px 4px;
	border-bottom: 1px solid #6678b1;
	border-right: 1px solid #6678b1;
	border-top: 1px solid #6678b1;
	background: #eff2ff;
	
}
#hor-minimalist-b td
{
	border-bottom: 1px solid #ccc;
	border-right: 1px solid #ccc;
	color: #669;
	padding: 6px 8px;
	
}
#hor-minimalist-b tbody tr:hover td
{
	color: #009;
}

 
		 
		 
/* CSS Tabs */
#navlist {
	padding: 3px 0;
	margin-left: 0;
	border-bottom: 1px solid #778;
	font: bold 12px Verdana, sans-serif;
}

#navlist li {
	list-style: none;
	margin: 0;
	display: inline;
}

#navlist li a {
	padding: 3px 0.5em;
	margin-left: 3px;
	border: 1px solid #778;
	border-bottom: none;
	background: #DDE;
	text-decoration: none;
}

#navlist li a:link { color: #448; }
#navlist li a:visited { color: #667; }

#navlist li a:hover {
	color: #000;
	background: #AAE;
	border-color: #227;
}

#navlist li a#current {
	background: white;
	border-bottom: 1px solid white;
}

table.formlist tr.even{
	background: #dddddd;
	border:0px
}
table.formlist tr.even:hover{
	background-color: rgb(226,176,84);
	border:0px
}
table.formlist tr.odd{	
	background-color: #fbfbfb;
	border:0px
}
table.formlist tr.odd:hover{
	background-color: rgb(226,176,84);
	border:0px
}
table.formlist tr.selected{	
	background-color: #AAF;
	border:0px
}
table.formlist tr.selected:hover{
	background-color: rgb(226,176,84);
	border:0px
}

table.formlist th {
	border-top:1px solid #FFFFFF;
	border-left:1px solid #FFFFFF;
	border-right:1px solid #999999;
	border-bottom:1px solid #999999;	
	background-color: rgb(160,185,224);
	text-align:center;
	padding:5px 5px 5px 20px;
	vertical-align: middle;
	font-style: normal;
}

table.axial td .table.formlist 
{
	width:98%;
	/*clear:left;*/
	background-color: #fbfbfb;
	border-style : solid;
	border-width : 4px;  
	border-color: #cccccc;
}

table.axial td .table.formlist tr.even{
	background: #dddddd;
	border:0px
}

table.axial td .table.formlist tr.odd{	
	background-color: #fbfbfb;
	border:0px
}

table.axial td .table.formlist td.even{
	background-color: #dddddd;
	border:0px
}

table.axial td .table.formlist td.odd{	
	background-color: #fbfbfb;
	border:0px
}

table.axial td .table.formlist th {
	border-top:1px solid #FFFFFF;
	border-left:1px solid #FFFFFF;
	border-right:1px solid #999999;
	border-bottom:1px solid #999999;	
	background-color: rgb(160,185,224);
	text-align:center;
	padding:5px 5px 5px 20px;
	vertical-align: middle;
	font-style: normal;
}

a {text-decoration:none;color: blue;}

 .normal { background-color: white }
 .highlight { border: 4px solid red;font-family:Arial,Helvetica,sans-serif;font-size: 200%;font-weight:bold;color: blue;}
  
</style>
   
 		<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
			<form name="listForm" action="downLoadCvsFileData.htm" method="post" >

				<%--input type="hidden" name="type" value="${param['type']}"/>
    
				 <c:if test='${param["type"]==null || param["type"]==""}'> 
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"> <strong>Status</strong> : </span> &nbsp;
					<input type="checkbox" name="DRAFT" value="DRAFT" onchange="document.listForm.submit();" <c:if test='${viewTimesheetList.param.draftKey=="DRAFT"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: black;">Draft </span> &nbsp;

					<input type="checkbox" name="SUBMITTED" value="SUBMITTED" onchange="document.listForm.submit();" <c:if test='${viewTimesheetList.param.submittedKey=="SUBMITTED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: black;">Submitted </span> &nbsp;

					<input type="checkbox" name="VALIDATED" value="VALIDATED" onchange="document.listForm.submit();" <c:if test='${viewTimesheetList.param.validatedKey=="VALIDATED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: black;">Validated </span> 
					<br/>
				</c:if--%>
				
				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercise</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="year">
				    <c:forEach var="y" items="${yearOptions}">					
			             		<option value="${y.id}">${y.name}</option>
			        </c:forEach>
				</select> 

				 <%-- c:if test='${param["type"]==null || param["type"]==""}'> 
			
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Week</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="week" onchange="document.listForm.submit();">
						<option value="-1">Any</option>
						<c:forEach var="weekNumber" begin="1" end="53" varStatus="loop">
							<option value="${weekNumber}"<c:if test='${viewTimesheetList.param.week==weekNumber}'> selected</c:if>>${weekNumber}</option>
						</c:forEach>
					</select>
					<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,CONSULT_TS_ATRAITER">
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employee</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="employeeId" onchange="document.listForm.submit();">
							<option value="-1">Any</option>
							<c:forEach var="y" items="${viewTimesheetList.employeeOptions}">
									<option value="${y.id}"<c:if test='${viewTimesheetList.param.employeeId==y.id}'> selected</c:if>>${y.name}</option>
							</c:forEach> 
					</select>
				</interaudit:atLeastOneAccessRightSet>
				</c:if--%>
				
				
				<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Download"/>	
				
				

				</form>
			
			</div>
			

			<br/>
			
			<div>
								
		    
		        
							
			</div>
	
 