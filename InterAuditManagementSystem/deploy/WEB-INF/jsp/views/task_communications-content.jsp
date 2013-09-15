<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="common_css.jsp"/>

<script>
#right { float:left; width:385px; }
#right a { font-weight:bold; }
#right a:hover { border-bottom:1px dotted;  }
#right .postinfo { display:block; width:90%; margin:5px; padding:6px; background-color:#EFEFEF; border:1px solid #D4D4D4; }
#right blockquote { width:80%; margin:5px; padding:6px; border-left:1px solid #D4D4D4; }
.welcome { float:left; display:block; width:150px; margin:10px; padding:5px; background-color:#FFF; border:1px solid #D4D4D4;  }
#right p a { color:#666666; }
</script>

<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <jsp:include page="taskDetailHeader.jsp"/>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
		<li><a href="${ctx}/showTaskPropertiesPage.htm?id=${activityDetails.interventionId}">Proprietes</a></li>
		<li><a href="${ctx}/showTaskDocumentsPage.htm?id=${activityDetails.interventionId}">Documents</a></li>
		<li><a id="current" href="${ctx}/showTaskCommunicationsPage.htm?id=${activityDetails.interventionId}">Communications</a></li>
		<li><a href="${ctx}/showTaskWorkDonePage.htm?id=${activityDetails.interventionId}">Logged work</a></li>

		</ul>
     </div>

	 <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a id="show" onclick="resetForm()"  href="#">Add Message</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">

   <table id="hor-minimalist-b" summary="Messages related to the mission">
		<thead>
			<tr>
				<th scope="col">From</th>
				<th scope="col">Date</th>
				<th scope="col">Message</th>
				<th scope="col">actions</th>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${activityDetails.messages}" >
				<tr>
				<td>
				<div id="right">
				<span class="postinfo">  ${item.from.firstName} ${item.from.lastName}  </span>
				</div>
				</td> 

				<td>
				<div id="right">
				<span class="postinfo">  ${item.createDate}  </span>
				</div>
				</td>
				
				<td>						
			  <div id="right">

				

				<h5>${item.subject}</h5>
				  <%--p><span style="color:black;">${item.contents}</span></p--%>
				  <span class="postinfo"> Posted by <a href="http://www.free-css.com/">${item.from.firstName} ${item.from.lastName}</a> on ${item.createDate}  </span>
				  
				  
													
				
			 </div>
			</td> 
			<td> <img src="images/email2.png" border="0" alt="Edit cost" onclick="editMessage(${item.id})"/></td>
			</tr>
			</c:forEach>
		</tbody>
 </table>
 
  
  
  
  </div>

  <style>
#ver-zebra2
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 10px;
	text-align: center;
}
#ver-zebra2 th
{
	font-size: 12px;
	font-weight: normal;
	
	color: #039;
	background: #eff2ff;
}
#ver-zebra2 td
{
	color: #669;
}
</style>



<div id="addDialog">
	<div class="hd">
        <span id="formTitle" style="color:#039;">Add message</span>
    </div>
	<div id="container">
	<form  action="${ctx}/handleTaskMessagePage.htm" name="addMessageForm" method="post" class="niceform">
	<input type="hidden" name="messageId" />
	<input type="hidden" name="taskId"  value="${activityDetails.interventionData.id}"/>
	<fieldset>
		<hr/>
    	<legend>Message</legend>
        <dl>
        	<dt><label for="subject">Subject:</label></dt>
            <dd><input type="input" name="subject" id="subject" value="Luxlait status" size="32" maxlength="32" /></dd>
		 </dl>

		<dl>
			<dt><label for="description">Message:</label></dt>
            <dd><textarea name="description" id="description" rows="8" cols="60">Any news about customer luxlait?</textarea></dd>
		</dl>
    </fieldset>

    
    <fieldset>
    	<legend>Administration</legend>
		
		<dl>
				<dt><label for="recipient">Recipient:</label></dt>
				<dd>
				<select style="width:300px;" size="1" name="recipient" id="recipient">
                    <c:forEach var="y" items="${activityDetails.teamMembersOptions}">
						<option value="${y.id}">${y.lastName}</option>
					</c:forEach>
            	</select>
				</dd>
				
		 </dl>

    </fieldset>
    
</form>

</div>
	
</div>
  
 </div>
<!-- End Wrapper -->
						
					


	