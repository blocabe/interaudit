<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
<%--form name="listForm" action="${ctx}/showMissionPropertiesPage.htm" method="post" >					
				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Mission</strong> :</span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="id" onchange="document.listForm.submit();">					
				  	  <c:forEach var="y" items="${viewMissionDetails.missionOptions}">
                		<option value="${y.id}"<c:if test='${viewMissionDetails.missionData.id==y.id}'> selected</c:if>>${fn:substring(fn:toUpperCase(y.name), 0, 19)} [${viewMissionDetails.missionData.year}]</option>
            		  </c:forEach>
				</select> 				
				&nbsp;
				<!--input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Load"/-->	
</form--%>
</div>
<div id="header" style="background:#eee;border: 1px solid #ccc;">
<h4>
${viewMissionDetails.missionData.customerName} :
<c:if test='${viewMissionDetails.mission.parent != null}'>

<span style="color:purple;">
 
<a href="${ctx}/showMissionPropertiesPage.htm?id=${viewMissionDetails.mission.parent.id}">
 ${viewMissionDetails.missionData.exercice - 1} ${viewMissionDetails.mission.parent.exercise}</span>&nbsp;[ <span style=" color: blue;"> ${viewMissionDetails.mission.parent.type}</span> ]&nbsp;&nbsp;[ 

													<c:choose>
														<c:when test='${viewMissionDetails.mission.parent.status eq "PENDING" }'>
														 	<span style=" color: blue;">En Attente</span>
														</c:when>

														<c:when test='${viewMissionDetails.mission.parent.status eq "ONGOING" }'>
														 	<span style=" color: green;">En cours</span>
														</c:when>

														<c:when test='${viewMissionDetails.mission.parent.status eq "STOPPED" }'>
														 	<span style=" color: black;">Arrêté</span>
														</c:when>

														<c:when test='${viewMissionDetails.mission.parent.status eq "CANCELLED" }'>
														 	<span style=" color: black;">Annulé</span>
														</c:when>

														<c:when test='${viewMissionDetails.mission.parent.status eq "CLOSED" }'>
														 	<span style=" color: red;">Terminé</span>
														</c:when>	
														
														<c:when test='${viewMissionDetails.mission.parent.status eq "TRANSFERED" }'>
														 	<span  style=" color: black;">Transférée</span>
														</c:when>
													</c:choose> ] <span style=" color: red;"> > </span>
</a>

</c:if>


<span style="color:purple;"></span> [ <span style=" color: blue;">${viewMissionDetails.missionData.exercice} ${viewMissionDetails.missionData.year}</span> ]&nbsp;[ <span style=" color: blue;"> ${viewMissionDetails.mission.type}</span> ]&nbsp;[ 

													<c:choose>
														<c:when test='${viewMissionDetails.missionData.status eq "PENDING" }'>
														 	<span style=" color: blue;">En Attente</span>
														</c:when>

														<c:when test='${viewMissionDetails.missionData.status eq "ONGOING" }'>
														 	<span style=" color: green;">En cours</span>
														</c:when>

														<c:when test='${viewMissionDetails.missionData.status eq "STOPPED" }'>
														 	<span style=" color: black;">Arrêté</span>
														</c:when>

														<c:when test='${viewMissionDetails.missionData.status eq "CANCELLED" }'>
														 	<span style=" color: bleck;">Annulé</span>
														</c:when>

														<c:when test='${viewMissionDetails.missionData.status eq "CLOSED" }'>
														 	<span style=" color: red;">Terminé</span>
														</c:when>	
														
														<c:when test='${viewMissionDetails.missionData.status eq "TRANSFERED" }'>
														 	<span  style=" color: blue;">Transférée</span>
														</c:when>
													</c:choose> 
]</h4>
</div>


