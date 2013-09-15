<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>

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
.ver-zebra th
{
	font-size: 12px;
	font-weight: normal;
	/*padding: 12px 15px;*/
	border-top: 1px solid #0066aa;
	border-right: 1px solid #0066aa;
	border-bottom: 1px solid #0066aa;
	border-left: 1px solid #fff;
	color: #039;
	background: #eff2ff;
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
 a {text-decoration:none;color: blue;}

 .normal { background-color: white }
 .highlight { border: 4px solid red;font-family:Arial,Helvetica,sans-serif;font-size: 200%;font-weight:bold;color: blue;}
</style>

		<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 11px Verdana, sans-serif;">
				<form style="text-align:center; name="form1" action="${ctx}/showBudgetResultsPage.htm" method="post" >

					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercice</strong> 
						<select style="font:10px Verdana, sans-serif;margin-right:10pt;"  name="year">
							<c:forEach var="y" items="${viewExercise.years}">
							<option value="${y}" <c:if test='${viewExercise.exercise==y}'> selected</c:if> >${y}</option>
							</c:forEach>
						</select> 
					</span>
						
					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="submit" class="button120" value="Search" /></span>	
				</form>
		</div>
<br/>

<div id="navcontainer">
                        <ul id="navlist">
                                <!-- CSS Tabs -->
<li><a  href="${ctx}/showBudgetResultsPage.htm">Objectifs annuels</a></li>
<li><a id="current" href="${ctx}/showBudgetResultsMensuelsPage.htm">Resultats mensuels</a></li>
<li><a href="${ctx}/showBudgetResultsTrimestrielsPage.htm">Resultat trimestriels</a></li>

                        </ul>
                </div>

<div style="border-bottom: 1px solid gray; background-color: white;">
						
			    <%--
							<table class="ver-zebra" width="100%" cellspacing="0" >
								<caption><span style="color:blue;">Objectifs & Resultats  </span></caption>
									<tbody>
											<tr>
												<th align="left"><span style="color:rgb(218,69,37);"><strong>Budget generale</span></strong>&nbsp;<img src="images/more.gif" border="0" alt="Show or Hide" onclick="javascript:showHide1();"/></th>
											</tr>
											<tr >
											
												<table id="div_1" class="ver-zebra" width="100%" cellspacing="0" >
												 <tbody>
													<tr  class="odd">
														<th scope="col"><span style="color:rgb(218,69,37);">Prevision</span></th>
														<th scope="col"><span style="color:rgb(218,69,37);">Report</span></th>
														<th scope="col"><span style="color:rgb(218,69,37);">Total</span></th>
														<th scope="col"><span style="color:rgb(218,69,37);">Inactifs</span></th>	
														<th scope="col"><span style="color:rgb(218,69,37);">Factur�</span></th>
													</tr>
													<tr>
														<td><strong>
															<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.expectedBudget}
															</fmt:formatNumber>
														</strong></td>
														<td><strong>
															<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.reportedBudget}
															</fmt:formatNumber>
														</strong></td>
														<td><strong>
															<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.expectedBudget + viewExercise.reportedBudget}
															</fmt:formatNumber>
														</strong></td>
														<td><strong>
															<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.totalInactifsBudget}
															</fmt:formatNumber>
														</strong></td>
														<td><strong><span style="color:green;">
															<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.totalBudget}
															</fmt:formatNumber></span>
														</strong></td>
													</tr>
													</tbody>
												</table>
												
											</tr>
									</tbody>
								</table>
								<br/>

								<table class="ver-zebra" width="100%" cellspacing="0" >
									<tbody>
											<tr>
												<th colspan ="2" align="left"><span style="color:rgb(218,69,37);"><strong>Objectif par associe</span></strong>&nbsp;<img src="images/more.gif" border="0" alt="Show or Hide" onclick="javascript:showHide2();"/>&nbsp;<img src="images/timeicon.gif" border="0"  id="show1" onclick="javascript:chartPerAssocie();"/></th>
											</tr>

											<tr>
												<td width="70%"  style="border-right: 0px solid #778;" valign="top">
												<table id="div_2" class="ver-zebra" width="100%" cellspacing="0" >
												 <tbody>
												 <tr class="odd">
														
                											<th scope="col"><span style="color:rgb(218,69,37);">Associe</span></th>
															<th scope="col"><span style="color:rgb(218,69,37);">Percentage</span></th>
															<th scope="col"><span style="color:rgb(218,69,37);">Amount</span></th>            										
													</tr>

													<c:forEach var="e" items="${viewExercise.associeOptions}">
													<c:set var="key" value="${e.name}"/>
													<tr>
                											<th border="0" scope="col"><span style="color:rgb(218,69,37);">${e.name}</span></th>
															<td><strong>
																	<fmt:formatNumber type="percent">
																		${viewExercise.objectifsPerAssocie[key].percentage}
																	</fmt:formatNumber>
																</strong>
															</td>
															<td><strong>
																		<fmt:formatNumber type="currency" currencySymbol="&euro;">
																			${viewExercise.objectifsPerAssocie[key].annualAmount}
																		</fmt:formatNumber>
																	</strong>
															</td>
													</tr>
            										</c:forEach>

													
													
												</tbody>
											</table>
											</td>
											<td width="30%" border="0">
											<img src="${ctx}/showObjectifPerAssocieBartChartPage.htm" border="1" WIDTH=200 HEIGHT=100 onclick="javascript:chartPerAssocie();" style="cursor:hand;"/>
											</td>


											</tr>
											
									
									</tbody>
								</table>
								<br/>

								<table class="ver-zebra" width="100%" cellspacing="0" >
									<tbody>
											<tr>
												<th colspan ="2" align="left"><span style="color:rgb(218,69,37);"><strong>Objectif par manager</span></strong>&nbsp;<img src="images/more.gif" border="0" alt="Show or Hide" onclick="javascript:showHide3();"/>&nbsp;<img src="images/timeicon.gif" border="0"  id="show2" onclick="javascript:chartPerManager();"/></th>
											</tr>
											<tr>
											<td width="70%"  style="border-right: 0px solid #778;" valign="center">
												<table id="div_3" class="ver-zebra" width="100%" cellspacing="0" >
												 <tbody>

												 <tr class="odd">
														
                											<th scope="col"><span style="color:rgb(218,69,37);">Manager</span></th>
															<th scope="col"><span style="color:rgb(218,69,37);">Percentage</span></th>
															<th scope="col"><span style="color:rgb(218,69,37);">Amount per year</span></th>            				
															<th scope="col"><span style="color:rgb(218,69,37);">Amount per month</span></th> 						
													</tr>

													<c:forEach var="e" items="${viewExercise.managerOptions}">
													<c:set var="key" value="${e.name}"/>
													<tr>
                											<th border="0" scope="col"><span style="color:rgb(218,69,37);">${e.name}</span></th>
															<td><strong>
																	<fmt:formatNumber type="percent">
																		${viewExercise.objectifsPerManager[key].percentage}
																	</fmt:formatNumber>
																</strong>
															</td>
															<td><strong>
																		<fmt:formatNumber type="currency" currencySymbol="&euro;">
																			${viewExercise.objectifsPerManager[key].annualAmount}
																		</fmt:formatNumber>
																	</strong>
															</td>
															<td>
																<strong>
																<fmt:formatNumber type="currency" currencySymbol="&euro;">
																	${viewExercise.objectifsPerManager[key].grossMonthlyAmount}
																</fmt:formatNumber>
																</strong>
															</td>
													</tr>
            										</c:forEach>

											
												 </tbody>
												</table>
											</td>
											<td width="30%"  style="border-right: 0px solid #778;" valign="center">
											<img src="${ctx}/showObjectifPerManagerBartChartPage.htm" border="1" WIDTH=200 HEIGHT=100 onclick="javascript:chartPerManager();" style="cursor:hand;"/>
											</td>
											</tr>
											
									
									</tbody>
								</table>
								--%>
								<%--br/>
								<table class="ver-zebra" width="100%" cellspacing="0" >
									<tbody>
											<tr>
												<th colspan ="7" align="left"><span style="color:rgb(218,69,37);"><strong>Resultats mensuel par associe</span></strong>&nbsp;<img src="images/more.gif" border="0" alt="Show or Hide" onclick="javascript:showHide4();"/></th>
											</tr>
											
											<tr>
													<table id="div_4" class="ver-zebra" width="100%" cellspacing="0" >
														
														<tbody>
																	<tr>
																		<th scope="col">&nbsp;</th>
																		<th scope="col">Objectif</th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Janvier</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Fevrier</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Mars</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Avril</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Mai</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Juin</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Juillet</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Aout</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Septembre</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Octobre</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Novembre</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Decembre</span></th>
																	</tr>
																	<c:forEach var="e" items="${viewExercise.associeOptions}">
																	<c:set var="key" value="${e.name}"/>
																	<c:choose>
																			<c:when test='${viewExercise.resultsPerManager[key][entry] == 0}'>												
																		 <c:set var="backgroundStyle" value="background-color:white;"/>
																		</c:when>
																		<c:when test='${viewExercise.objectifsPerManager[key].grossMonthlyAmount > viewExercise.resultsPerManager[key][entry]}'>												
																		 <c:set var="backgroundStyle" value="background-color:red;"/>
																		</c:when>
																		
																		<c:otherwise>
																		 <c:set var="backgroundStyle" value="background-color:#55FF55;"/>
																		</c:otherwise>
																	</c:choose>
																	<tr class="odd" >
																			<td><span style="color:rgb(218,69,37);">${e.name}</span></td>
																			<td style="background-color:purple;">
																				<strong><span style="color:rgb(218,69,37);">
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${viewExercise.objectifsPerManager[key].grossMonthlyAmount}
																				</fmt:formatNumber></span>
																				</strong>
																			</td>
																			<c:forEach var="entry" begin="0" end="11">
																				<td style=${backgroundStyle}>
																					<fmt:formatNumber type="currency" currencySymbol="&euro;">
																						${viewExercise.resultsPerAssociate[key][entry]}
																					</fmt:formatNumber>
																				</td>
																			</c:forEach>
																	</tr>
																	</c:forEach>
														</tbody>
													</table>
											</tr>
									</tbody>
								</table>
								<br/--%>

								<table class="ver-zebra" width="100%" cellspacing="0" >
									<tbody>
											<tr>
												<th colspan ="7" align="left"><span style="color:rgb(218,69,37);"><strong>Resultats mensuel par manager</span></strong>&nbsp;<img src="images/more.gif" border="0" alt="Show or Hide" onclick="javascript:showHide4();"/></th>
											</tr>
											
											<tr>
													<table id="div_4" class="ver-zebra" width="100%" cellspacing="0" >
														
														<tbody>
																	<tr>
																		<th scope="col">&nbsp;</th>
																		<th scope="col">Objectif</th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Janvier</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Fevrier</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Mars</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Avril</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Mai</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Juin</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Juillet</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Aout</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Septembre</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Octobre</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Novembre</span></th>
																		<th scope="col"><span style="color:rgb(218,69,37);">Decembre</span></th>
																	</tr>

																	<c:set var="totalFacture" value="0"/>

																	<c:set var="totalJanvier" value="0"/>
																	<c:set var="totalFevrier" value="0"/>
																	<c:set var="totalMars" value="0"/>
																	<c:set var="totalAvril" value="0"/>
																	<c:set var="totalMai" value="0"/>
																	<c:set var="totalJuin" value="0"/>
																	<c:set var="totalJuillet" value="0"/>
																	<c:set var="totalAout" value="0"/>
																	<c:set var="totalSeptembre" value="0"/>
																	<c:set var="totalOctobre" value="0"/>
																	<c:set var="totalNovembre" value="0"/>
																	<c:set var="totalDecembre" value="0"/>

																	<c:forEach var="e" items="${viewExercise.managerOptions}">
																	<c:set var="key" value="${e.name}"/>

																	<c:set var="row" value="${totalFacture + viewExercise.objectifsPerManager[key].grossMonthlyAmount}"/>

																	<tr class="odd" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
																			<td><span style="color:rgb(218,69,37);">${e.name}</span></td>
																			<td style="background-color:white;">
																				<span style="color:blue;">
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${viewExercise.objectifsPerManager[key].grossMonthlyAmount}
																				</fmt:formatNumber></span>
																				
																				<c:set var="totalFacture" value="${totalFacture + viewExercise.objectifsPerManager[key].grossMonthlyAmount}"/>
																			</td>
																			<c:forEach var="entry" begin="0" end="11">
																				<c:choose>
																					<c:when test='${viewExercise.resultsPerManager[key][entry] == 0}'>												
																					 <c:set var="backgroundStyle" value="background-color:white;color:black;"/>
																					 
																					</c:when>
																				
																					<c:when test='${viewExercise.objectifsPerManager[key].grossMonthlyAmount > viewExercise.resultsPerManager[key][entry]}'>												
																					 <c:set var="backgroundStyle" value="background-color:white;color:red;"/>
																					
																					</c:when>
																				
																					<c:otherwise>
																						 <c:set var="backgroundStyle" value="background-color:white;color:green;"/>
																						 
																					</c:otherwise>
																				</c:choose>

																				<c:choose>
																					<c:when test='${entry == 0}'>
																					 <c:set var="totalJanvier" value="${totalJanvier + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 1}'>
																					 <c:set var="totalFevrier" value="${totalFevrier + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 2}'>
																					 <c:set var="totalMars" value="${totalMars + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 3}'>
																					 <c:set var="totalAvril" value="${totalAvril + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 4}'>
																					 <c:set var="totalMai" value="${totalMai + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 5}'>
																					 <c:set var="totalJuin" value="${totalJuin + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 6}'>
																					 <c:set var="totalJuillet" value="${totalJuillet + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 7}'>
																					 <c:set var="totalAout" value="${totalAout + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 8}'>
																					 <c:set var="totalSeptembre" value="${totalSeptembre + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 9}'>
																					 <c:set var="totalOctobre" value="${totalOctobre + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 10}'>
																					 <c:set var="totalNovembre" value="${totalNovembre + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>

																					<c:when test='${entry == 11}'>
																					 <c:set var="totalDecembre" value="${totalDecembre + viewExercise.resultsPerManager[key][entry]}"/>
																					</c:when>
																				</c:choose>
																				<td style=${backgroundStyle}>
																					<fmt:formatNumber type="currency" currencySymbol="&euro;">
																						${viewExercise.resultsPerManager[key][entry]}
																					</fmt:formatNumber>
																				</td>
																			</c:forEach>
																	</tr>
																	</c:forEach>

																	<tr>
																		<th scope="col" colspan="15">&nbsp;</th>
																		
																	</tr>

																	<tr class="odd"  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
																			<td><span style="color:rgb(218,69,37);">Tot</span></td>
																			<td style="background-color:white;">
																				<strong><span style="color:blue;">
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalFacture}
																				</fmt:formatNumber></span>
																				</strong>
																			</td>
																			
																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalJanvier}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalFevrier}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalMars}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalAvril}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalMai}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalJuin}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalJuillet}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalAout}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalSeptembre}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalOctobre}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalNovembre}
																				</fmt:formatNumber>
																			</td>

																			<td >
																				<fmt:formatNumber type="currency" currencySymbol="&euro;">
																					${totalDecembre}
																				</fmt:formatNumber>
																			</td>

																			
																			
																	</tr>
																	<tr>
																		<th align="center" scope="col" colspan="15">
																		<img src="${ctx}/showBudgetResultsMensuelsLineChartPage.htm" border="1" WIDTH=700 HEIGHT=500"/>
																		</th>
																		
																	</tr>
														</tbody>
													</table>
											</tr>
									</tbody>
								</table>
								
								
<%--
								<br/>

								<table class="ver-zebra" width="100%" cellspacing="0" >
									<tbody>
											<tr>
												<th colspan ="7" align="left"><span style="color:rgb(218,69,37);"><strong>Resultats trimestriels par associe</span></strong>&nbsp;<img src="images/more.gif" border="0" alt="Show or Hide" onclick="javascript:showHide4();"/></th>
											</tr>
											<tr>
													<table id="div_4" class="ver-zebra" width="100%" cellspacing="0" >
														<tbody>
																	<tr class="odd">
												<th scope="col">&nbsp;</th>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
                									<th scope="col"><span style="color:rgb(218,69,37);">${e.name}</span></th>
            									  </c:forEach>
											</tr>

											<tr>
												<th colspan ="${ fn:length(viewExercise.associeOptions) +1}" ><span style="color:purple;">Trimestre 1 [Janvier- Fevrier-Mars]</span></th>
											</tr>
											
											
											<tr>
												<td><strong>Total<strong></td>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
												<c:set var="key" value="${e.name}"/>
                									<td>
													<span style="color:black;"><strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.resultsPerAssociate[key][0]+ viewExercise.resultsPerAssociate[key][1]+viewExercise.resultsPerAssociate[key][2]}
														</fmt:formatNumber>
													</strong></span>
													</td>
            									 </c:forEach>
											</tr>

											
															
															
											<tr>
												<td><strong>Delta</strong></td>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
												<c:set var="key" value="${e.name}"/>
												<c:set var="result" value="${(viewExercise.resultsPerAssociate[key][0] + viewExercise.resultsPerAssociate[key][1]+
													viewExercise.resultsPerAssociate[key][2]) -
													(viewExercise.objectifsPerAssocie[key].grossMonthlyAmount *3 )}"/>
                									<td>
													<c:choose>
														<c:when test='${result < 0 }'>
															<img src="images/vote_arrow_down.gif" border="0" /><span style="color:red;">
														</c:when>
														<c:otherwise>
															<img src="images/progress_arrow_up.png" border="0"/><span style="color:green;">
														</c:otherwise>
													</c:choose>
													<strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${ result}
														</fmt:formatNumber>
													</strong></span></td>
                			
            									 </c:forEach>
											</tr>

											<tr>
												<th colspan ="${ fn:length(viewExercise.associeOptions) +1}" ><span style="color:purple;">Trimestre 2 [Avril-Mai-Juin]</span></th>
											</tr>
											
											
											<tr>
												<td><strong>Total</strong></td>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
												<c:set var="key" value="${e.name}"/>
                									<td><span style="color:black;"><strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.resultsPerAssociate[key][3]+ viewExercise.resultsPerAssociate[key][4]+viewExercise.resultsPerAssociate[key][5]}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
											</tr>
											
											<tr>
												<td><strong>Delta</strong></td>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
												<c:set var="key" value="${e.name}"/>
												<c:set var="result" value="${(viewExercise.resultsPerAssociate[key][3] + viewExercise.resultsPerAssociate[key][4]+
													viewExercise.resultsPerAssociate[key][5]) -
													(viewExercise.objectifsPerAssocie[key].grossMonthlyAmount *3 )}"/>
                									<td>
													<c:choose>
														<c:when test='${result < 0 }'>
															<img src="images/vote_arrow_down.gif" border="0" /><span style="color:red;">
														</c:when>
														<c:otherwise>
															<img src="images/progress_arrow_up.png" border="0"/><span style="color:green;">
														</c:otherwise>
													</c:choose>
													<strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${ result}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
											</tr>

											<tr>
												<th colspan ="${ fn:length(viewExercise.associeOptions) +1}" ><span style="color:purple;">Trimestre 3  [Juillet-Aout-Septembre]</span></th>
											</tr>
											
											
											<tr>
												<td><strong>Total</strong></td>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
												<c:set var="key" value="${e.name}"/>
                									<td><span style="color:black;"><strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.resultsPerAssociate[key][6]+ viewExercise.resultsPerAssociate[key][7]+viewExercise.resultsPerAssociate[key][8]}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
											</tr>

											
											<tr>
												<td><strong>Delta</strong></td>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
												<c:set var="key" value="${e.name}"/>
												<c:set var="result" value="${(viewExercise.resultsPerAssociate[key][6] + viewExercise.resultsPerAssociate[key][7]+
													viewExercise.resultsPerAssociate[key][8]) -
													(viewExercise.objectifsPerAssocie[key].grossMonthlyAmount *3 )}"/>
                									<td>
													<c:choose>
														<c:when test='${result < 0 }'>
															<img src="images/vote_arrow_down.gif" border="0" /><span style="color:red;">
														</c:when>
														<c:otherwise>
															<img src="images/progress_arrow_up.png" border="0"/><span style="color:green;">
														</c:otherwise>
													</c:choose>
													<strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${ result}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
                									
            									 
											</tr>

											<tr>
												<th colspan ="${ fn:length(viewExercise.associeOptions) +1}" ><span style="color:purple;">Trimestre 4  [Octobre-Novembre-Decembre]</span></th>
											</tr>
										
											
											
											<tr>
												<td><strong>Total</strong></td>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
												<c:set var="key" value="${e.name}"/>
                									<td><span style="color:black;"><strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.resultsPerAssociate[key][9]+ viewExercise.resultsPerAssociate[key][10]+viewExercise.resultsPerAssociate[key][11]}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
											</tr>
											
											<tr>
												<td><strong>Delta</strong></td>
												<c:forEach var="e" items="${viewExercise.associeOptions}">
												<c:set var="key" value="${e.name}"/>
												<c:set var="result" value="${(viewExercise.resultsPerAssociate[key][9] + viewExercise.resultsPerAssociate[key][10]+
													viewExercise.resultsPerAssociate[key][11]) -
													(viewExercise.objectifsPerAssocie[key].grossMonthlyAmount *3 )}"/>
                									<td>
													<c:choose>
														<c:when test='${result < 0 }'>
															<img src="images/vote_arrow_down.gif" border="0" /><span style="color:red;">
														</c:when>
														<c:otherwise>
															<img src="images/progress_arrow_up.png" border="0"/><span style="color:green;">
														</c:otherwise>
													</c:choose>
													<strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${ result}
														</fmt:formatNumber>
														</strong></span></td>
            									 </c:forEach>
											</tr>

														</tbody>
													</table>
											</tr>
											
									
									</tbody>
								</table>

								
								<br/>

								<table class="ver-zebra" width="100%" cellspacing="0" >
									<tbody>
											<tr>
												<th colspan ="7" align="left"><span style="color:rgb(218,69,37);"><strong>Resultats trimestriels par manager</span></strong>&nbsp;<img src="images/more.gif" border="0" alt="Show or Hide" onclick="javascript:showHide4();"/></th>
											</tr>
											<tr>
													<table id="div_4" class="ver-zebra" width="100%" cellspacing="0" >
														<tbody>
																	<tr class="odd">
												<th scope="col">&nbsp;</th>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
                									<th scope="col"><span style="color:rgb(218,69,37);">${e.name}</span></th>
            									  </c:forEach>
											</tr>

											<tr>
												<th colspan ="${ fn:length(viewExercise.managerOptions) +1}" ><span style="color:purple;">Trimestre 1 [Janvier- Fevrier-Mars]</span></th>
											</tr>
											
											
											<tr>
												<td><strong>Total<strong></td>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
												<c:set var="key" value="${e.name}"/>
                									<td>
													<span style="color:black;"><strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.resultsPerManager[key][0]+ viewExercise.resultsPerManager[key][1]+viewExercise.resultsPerManager[key][2]}
														</fmt:formatNumber>
													</strong></span>
													</td>
            									 </c:forEach>
											</tr>

											
															
															
											<tr>
												<td><strong>Delta</strong></td>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
												<c:set var="key" value="${e.name}"/>
												<c:set var="result" value="${(viewExercise.resultsPerManager[key][0] + viewExercise.resultsPerManager[key][1]+
													viewExercise.resultsPerManager[key][2]) -
													(viewExercise.objectifsPerManager[key].grossMonthlyAmount *3 )}"/>
                									<td>
													<c:choose>
														<c:when test='${result < 0 }'>
															<img src="images/vote_arrow_down.gif" border="0" /><span style="color:red;">
														</c:when>
														<c:otherwise>
															<img src="images/progress_arrow_up.png" border="0"/><span style="color:green;">
														</c:otherwise>
													</c:choose>
													<strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${ result}
														</fmt:formatNumber>
													</strong></span></td>
                			
            									 </c:forEach>
											</tr>

											<tr>
												<th colspan ="${ fn:length(viewExercise.managerOptions) +1}" ><span style="color:purple;">Trimestre 2 [Avril-Mai-Juin]</span></th>
											</tr>
											
											
											<tr>
												<td><strong>Total</strong></td>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
												<c:set var="key" value="${e.name}"/>
                									<td><span style="color:black;"><strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.resultsPerManager[key][3]+ viewExercise.resultsPerManager[key][4]+viewExercise.resultsPerManager[key][5]}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
											</tr>
											
											<tr>
												<td><strong>Delta</strong></td>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
												<c:set var="key" value="${e.name}"/>
												<c:set var="result" value="${(viewExercise.resultsPerManager[key][3] + viewExercise.resultsPerManager[key][4]+
													viewExercise.resultsPerManager[key][5]) -
													(viewExercise.objectifsPerManager[key].grossMonthlyAmount *3 )}"/>
                									<td>
													<c:choose>
														<c:when test='${result < 0 }'>
															<img src="images/vote_arrow_down.gif" border="0" /><span style="color:red;">
														</c:when>
														<c:otherwise>
															<img src="images/progress_arrow_up.png" border="0"/><span style="color:green;">
														</c:otherwise>
													</c:choose>
													<strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${ result}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
											</tr>

											<tr>
												<th colspan ="${ fn:length(viewExercise.managerOptions) +1}" ><span style="color:purple;">Trimestre 3  [Juillet-Aout-Septembre]</span></th>
											</tr>
											
											
											<tr>
												<td><strong>Total</strong></td>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
												<c:set var="key" value="${e.name}"/>
                									<td><span style="color:black;"><strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.resultsPerManager[key][6]+ viewExercise.resultsPerManager[key][7]+viewExercise.resultsPerManager[key][8]}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
											</tr>

											
											<tr>
												<td><strong>Delta</strong></td>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
												<c:set var="key" value="${e.name}"/>
												<c:set var="result" value="${(viewExercise.resultsPerManager[key][6] + viewExercise.resultsPerManager[key][7]+
													viewExercise.resultsPerManager[key][8]) -
													(viewExercise.objectifsPerManager[key].grossMonthlyAmount *3 )}"/>
                									<td>
													<c:choose>
														<c:when test='${result < 0 }'>
															<img src="images/vote_arrow_down.gif" border="0" /><span style="color:red;">
														</c:when>
														<c:otherwise>
															<img src="images/progress_arrow_up.png" border="0"/><span style="color:green;">
														</c:otherwise>
													</c:choose>
													<strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${ result}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
                									
            									 
											</tr>

											<tr>
												<th colspan ="${ fn:length(viewExercise.managerOptions) +1}" ><span style="color:purple;">Trimestre 4  [Octobre-Novembre-Decembre]</span></th>
											</tr>
										
											
											
											<tr>
												<td><strong>Total</strong></td>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
												<c:set var="key" value="${e.name}"/>
                									<td><span style="color:black;"><strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${viewExercise.resultsPerManager[key][9]+ viewExercise.resultsPerManager[key][10]+viewExercise.resultsPerManager[key][11]}
														</fmt:formatNumber>
													</strong></span></td>
            									 </c:forEach>
											</tr>
											
											<tr>
												<td><strong>Delta</strong></td>
												<c:forEach var="e" items="${viewExercise.managerOptions}">
												<c:set var="key" value="${e.name}"/>
												<c:set var="result" value="${(viewExercise.resultsPerManager[key][9] + viewExercise.resultsPerManager[key][10]+
													viewExercise.resultsPerManager[key][11]) -
													(viewExercise.objectifsPerManager[key].grossMonthlyAmount *3 )}"/>
                									<td>
													<c:choose>
														<c:when test='${result < 0 }'>
															<img src="images/vote_arrow_down.gif" border="0" /><span style="color:red;">
														</c:when>
														<c:otherwise>
															<img src="images/progress_arrow_up.png" border="0"/><span style="color:green;">
														</c:otherwise>
													</c:choose>
													<strong>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${ result}
														</fmt:formatNumber>
														</strong></span></td>
            									 </c:forEach>
											</tr>

														</tbody>
													</table>
											</tr>
											
									
									</tbody>
								</table>
								--%>
						</div>		
	</div>
</div>
	

<div id="addDialog1" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:#039;">Repartition par associ�s</span>
    </div>
	<div class="bd">
			<fieldset> 
				<dl>
					<img src="${ctx}/showObjectifPerAssocieBartChartPage.htm" border="1" WIDTH=800 HEIGHT=600/>
				 </dl>					 
			</fieldset>  
	</div>
</div>

<div id="addDialog2" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:#039;">Repartition par managers</span>
    </div>
	<div class="bd">
			<fieldset> 
				<dl>
					<img src="${ctx}/showObjectifPerManagerBartChartPage.htm" border="1" WIDTH=800 HEIGHT=600/>
				 </dl>					 
			</fieldset>  
	</div>
</div>