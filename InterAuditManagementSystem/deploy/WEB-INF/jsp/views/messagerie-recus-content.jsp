<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>

<script type="text/javascript">
// <![CDATA[
function showHide1()
{
	new Effect.toggle('div_1','blind');
}

function showHide2()
{
	new Effect.toggle('div_2','blind');
}


function showHide3()
{
	new Effect.toggle('div_3','blind');
}

function showHide4()
{
	new Effect.toggle('div_4','blind');
}
//]]>
</script>

<script>
  function newMessage()
  {
      window.location="${ctx}/createMessagePage.htm";
  }
</script>

<style>

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

.ver-zebra
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
.ver-zebra td
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

/* CSS Tabs */
#navlist {
	padding: 3px 0;
	margin-left: 0;
	margin-bottom: 0;
	border-bottom: 1px solid #778;
	/*border-bottom: 2px solid #778;*/
	font: bold 12px Verdana, sans-serif;
}

#navlist li {
	list-style: none;
	margin: 0;
	display: inline;
}

#navlist li a {
	padding: 3px 0.5em;
	/*margin-left: 3px;*/
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
</style>
<style type="text/css">
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
</style>
		<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 11px Verdana, sans-serif;">
				<form style="text-align:center; name="form1" action="${ctx}/showMessageRecusPage.htm" method="post" >

					

					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercice</strong> 
						<select style="font:10px Verdana, sans-serif;margin-right:10pt;"  name="year">
							<c:forEach var="y" items="${message_recus.years}">
							<option value="${y}" <c:if test='${message_recus.exercise==y}'> selected</c:if> >${y}</option>
							</c:forEach>
						</select> 
					</span>

					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Client</strong> 
						<select style="font:10px Verdana, sans-serif;margin-right:10pt;"  name="mission">
							<option value="-1">Any</option>
							<c:forEach var="y" items="${message_recus.missionOptions}">
							<option value="${y.id}" <c:if test='${message_recus.customerId==y.id}'> selected</c:if>>${y.name}</option>
							</c:forEach>
						</select> 
					</span>
						
						
					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="submit" class="button120" value="Search" /></span>	


					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input  style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" onclick="newMessage();"class="button120" value="New message" /></span>	
				</form>
		</div>
<br/>

<div id="navcontainer">
                        <ul id="navlist">
                                <!-- CSS Tabs -->
<li><a id="current" href="${ctx}/showMessageRecusPage.htm"><img src="images/picto_inbox.gif" border="0"/> re�us</a></li>
<li><a  href="${ctx}/showMessageEnvoyesPage.htm"><img src="images/picto_sent.gif" border="0"/>  envoy�s</a></li>

                        </ul>
                </div>
                
         

<div style="border-bottom: 1px solid gray; background-color: white;">
<table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
									<caption><span style="color:purple;">Messages re�us</span></caption>
									<thead>
									<tr>
										<th style="border-bottom: 1px solid #0066aa;" scope="col">Type</th>
										<th style="border-bottom: 1px solid #0066aa;" scope="col"><span style="color:purple;">Nom du client</span></th>
										
										<th style="border-bottom: 1px solid #0066aa;" scope="col"><span style="color:purple;">Objet</span></th>
										<th style="border-bottom: 1px solid #0066aa;" scope="col"><span style="color:purple;">Contents</span></th>
										<th style="border-bottom: 1px solid #0066aa;" scope="col"><span style="color:purple;">De</span></th>
										<th style="border-bottom: 1px solid #0066aa;" scope="col"><span style="color:purple;">Date</span></th>

										
										<%--th style="border-bottom: 1px solid #0066aa;" scope="col"><span style="color:purple;">A</span></th--%>

										
										<th style="border-bottom: 1px solid #0066aa;" scope="col">Edit</th>
										
									</tr>
									</thead>
									<tbody>
					
									<c:set var="row" value="0"/>
									 <c:forEach var="item" items="${message_recus.messages}" varStatus="loop">
										 	<c:choose>
									    		<c:when test='${item.read == false}'>
									      			<tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr style="background: white;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
												
									     		<td style="border-bottom: 1px solid #0066aa;" align="center">												
														 <c:choose>
														<c:when test='${item.read == true }'>
														    
														 	<img src="images/mail_reply.gif" border="0"/>
														</c:when>
														<c:otherwise>
														
														 <img src="images/picto_letter.gif" border="0"/>
														</c:otherwise>
													</c:choose>														
												</td>
												<td style="border-bottom: 1px solid #0066aa;" align="center">
												<span style="color:purple;">${item.customerName}</span></td>
												
												<td style="border-bottom: 1px solid #0066aa;" align="center">${item.subject}</td>
												<td style="border-bottom: 1px solid #0066aa;" align="center" title=""  >

												<t:tooltip>  
														   <t:text><em>
														   ${fn:substring(item.contents,0,10)}...
														   </em></t:text>
														  <t:help width="400" >
														   <font color="STEELBLUE"></font> ${item.contents}
															
															</t:help> 
												</t:tooltip>
												
												</td>
												<td style="border-bottom: 1px solid #0066aa;" align="center">${item.from}</td>
												<td style="border-bottom: 1px solid #0066aa;" align="center">${item.createDate}</td>											
												
												<%--td style="border-bottom: 1px solid #0066aa;" align="center">${item.to}</td--%>
												<td style="border-bottom: 1px solid #0066aa;" align="center">

												<a href="${ctx}/createMessagePage.htm?id=${item.id}" alt="" title=""/>&nbsp;
														    <img src="images/puce.gif" border="0""/></a>
														
												</td>												
											</tr>
										<c:set var="row" value="${row + 1}"/>
									  </c:forEach>
									</tbody>
								</table>						
			    
							

								

								
								
</div>		
</div>
</div>


<div id="addDialog">
	<div class="hd">
        <span id="formTitle" style="color:#039;">Add message</span>
    </div>
	<div id="container">
	<form  action="${ctx}/handleMissionMessagePage.htm" name="addMessageForm" method="post">
	<input type="hidden" name="messageId" />
	<fieldset>    
		<dl>
				<dt><label for="recipient">Nom du client:</label></dt>
				<dd>
				<select style="width:300px;" size="1" name="id" id="id">
                    <c:forEach var="y" items="${message_recus.missionOptions}">
						<option value="${y.id}">${fn:substring(fn:toUpperCase(y.name), 0, 19)}</option>
					</c:forEach>
            	</select>
				</dd>				
		 </dl>
    </fieldset>
	
	<fieldset>    
		<dl>
				<dt><label for="recipient">Destinataire:</label></dt>
				<dd>
				<select style="width:300px;" size="1" name="recipient" id="recipient">
                    <c:forEach var="y" items="${message_recus.teamMembers}">
						<option value="${y.id}">${y.lastName} ${y.firstName}</option>
					</c:forEach>
            	</select>
				</dd>				
		 </dl>
    </fieldset>

	<fieldset>		
        <dl>
        	<dt><label for="subject">Objet:</label></dt>
            <dd><input style="width:300px;" type="input" name="subject" id="subject" size="32" maxlength="32" /></dd>
		 </dl>
    </fieldset>

	<fieldset>
		<dl>
			<dt><label for="description">Message:</label></dt>
            <dd><textarea style="width:350px;" name="description" id="description" rows="16" cols="100"></textarea></dd>
		</dl>
    </fieldset>

    
    
    
</form>

</div>
	
</div>
	

