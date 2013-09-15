
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="java.io.PrintWriter"%>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />

	<style type="text/css">
        /*
        * OK sous IE8, C2 et F3,
        * se positionne au-dessus du dialogue de z-index 1000,
        * se positionne juste en dessous de la ligne d'input.
        * Sous F3, si la fen�tre Firebug est ouverte
        * le datepicker remonte sur le dialog par manque de place, normal.
        */
        div#ui-datepicker-div {
            z-index: 4000;
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

#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
    </style>

 <script type="text/javascript">
	$(function() {
	$('#datepicker').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
	changeMonth: true,
		changeYear: true
	});
	});
</script>

<script>
  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }
</script>
<h2><c:choose>
			<c:when test='${message.id == null }'>
				New message
			</c:when>
			<c:otherwise>
				Lire message 
			</c:otherwise>
		</c:choose>
</h2>
<div id="global">
<form action="${ctx}/showEmployeeRegisterPage.htm" method="post" class="niceform">

	<fieldset style="background-color: #eee;" >
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:purple;font-weight: bold;">
		<c:choose>
			<c:when test='${message.id == null }'>
				New message
			</c:when>
			<c:otherwise>
				Lire message 
			</c:otherwise>
		</c:choose>
		</span>
			</td>
		</tr>
	</table> 
    </fieldset>
	<fieldset style="background-color: #eee;">
    	<legend>
		<span  style="color:purple;font-weight: bold;">Properties</span>
		</legend>
        
			<dl class="global2">
				<label style="color:#039;"  for="de">de:</label>
				<div>
					<spring:bind path="message.from.fullName" >
						<input style="width:300px;"  type="text" size="30" name="${status.expression}" value="${status.value}">						
					</spring:bind>
				</div>
            </dl>
			<dl class="global2">
					<label style="color:#039;"  for="to">� :</label>
					<div>
						
						<spring:bind path="message.to.id" >
					<select  name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
					<%--c:forEach var="option" items="${teamMembers}">
						<option value="${option.id}" 
						<c:if test="${option.id == status.value}">selected</c:if>>${option.name}
						</option>
					</c:forEach--%>
					</select>
					
					
				</spring:bind>
					</div>
			</dl>
			
			<dl class="global2">
					<label style="color:#039;"  for="to">client :</label>
					<div>
						
						<spring:bind path="message.mission.id" >
					<select  name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
					<%--c:forEach var="option" items="${missionOptions}">
						<option value="${option.id}" 
						<c:if test="${option.id == status.value}">selected</c:if>>${option.name}
						</option>
					</c:forEach--%>
					</select>
					
					
				</spring:bind>
					</div>
			</dl>
			
			<dl class="global2">
				<label style="color:#039;"  for="de">date :</label>
				<div>
					<spring:bind path="message.createDate" >
						<input style="width:300px;"  type="text" size="30" name="${status.expression}" value="${status.value}">						
					</spring:bind>
				</div>
            </dl>
            
            <dl class="global2">
				<label style="color:#039;"  for="de">objet :</label>
				<div>
					<spring:bind path="message.subject" >
						<input style="width:300px;"  type="text" size="30" name="${status.expression}" value="${status.value}">						
					</spring:bind>
				</div>
            </dl>
            
            <dl class="global2">			
            <div>
			<spring:bind path="message.contents" >
				 <textarea name="${status.expression}" id="contents" rows="3" cols="42">${status.value}</textarea>
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
            
			
    </fieldset>

    
 
   <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>
				<c:choose>
					<c:when test='${message.id == null }'>
						<input style="width:100px;
				" type="submit" name="submit" id="submit" class="button120"  value="Envoyer" />
					</c:when>
					<c:otherwise>
						<input style="width:100px;
				" type="submit" name="submit" id="submit" class="button120"  value="Repondre" />
					</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
	</table> 
    </fieldset>
    
    
</form>

</div>

