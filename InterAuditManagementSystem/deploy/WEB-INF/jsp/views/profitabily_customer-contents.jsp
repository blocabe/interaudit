<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>

<style>

.pagination2 span.current {
	
}


.pagination2 span.off,
.pagination2 span a,
.pagination2 span.current {
	border:2px solid #9AAFE5;
	padding-left:5px;
	padding-right:5px;
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
	/*padding: 12px 15px;*/
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

			<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 11px Verdana, sans-serif;">
				<%--div class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">
					<span class="galphabet_center select"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=*"><span style="color:orange;">All</span></a></span>			
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=A"><span style="color:orange;">A</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=B"><span style="color:orange;">B</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=C"><span style="color:orange;">C</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=D"><span style="color:orange;">D</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=E"><span style="color:orange;">E</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=F"><span style="color:orange;">F</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=G"><span style="color:orange;">G</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=H"><span style="color:orange;">H</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=I"><span style="color:orange;">I</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=J"><span style="color:orange;">J</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=K"><span style="color:orange;">K</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=L"><span style="color:orange;">L</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=M"><span style="color:orange;">M</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=N"><span style="color:orange;">N</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=O"><span style="color:orange;">O</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=P"><span style="color:orange;">P</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=Q"><span style="color:orange;">Q</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=R"><span style="color:orange;">R</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=S"><span style="color:orange;">S</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=T"><span style="color:orange;">T</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=U"><span style="color:orange;">U</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=V"><span style="color:orange;">V</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=W"><span style="color:orange;">W</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=X"><span style="color:orange;">X</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=Y"><span style="color:orange;">Y</span></a></span>
					<span class="galphabet_center" ><a href="${ctx}/showProfitabilityPerCustomerPage.htm?startingLetterName=Z"><span style="color:orange;">Z</span></a></span>
				</div--%>
				<form name="listForm" action="${ctx}/showProfitabilityPerCustomerPage.htm" method="post" >


					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Associe</strong> 
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="profitability_associe" onchange="document.listForm.submit();">
					  <option value="-1">Any</option>
					  <c:forEach var="y" items="${viewprofitability.associeOptions}">
                		<option value="${y.id}"<c:if test='${viewprofitability.param.associe==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
					</select> 
					</span>
						
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Manager</strong> 
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="profitability_manager" onchange="document.listForm.submit();">
					  <option value="-1">Any</option>
					  <c:forEach var="y" items="${viewprofitability.managerOptions}">
                		<option value="${y.id}"<c:if test='${viewprofitability.param.manager==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
					</select> 
					</span>	
					
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Customer</strong> 
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="profitability_customer" onchange="document.listForm.submit();">
					  <option value="-1">Any</option>
					  <c:forEach var="y" items="${viewprofitability.customerOptions}">
                		<option value="${y.id}"<c:if test='${viewprofitability.param.customer==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
					</select> 
					</span>

					<!--span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="submit" class="button120" value="Search" onclick="Javascript:searchFilter();"/></span-->	
					
					<input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Select years" id="show"/>
				</form>

			
			</div>
	<br/>
						

<div style="border-bottom: 1px solid gray; background-color: white;">
						
			    
			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
								<caption><span style="color:blue;">Analyse de la rentabilit&eacute; par client  </span></caption>
			        	
									<colgroup>
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
									</colgroup>
									<thead>
									<tr class="odd">
									    <th scope="col">Exercise</th>
										<th scope="col">Mandat</th>
										<th scope="col">Ass</th>
										<th scope="col">Man</th>
										<th scope="col">Client</th>
										<th scope="col">Statut</th>
										<!--th scope="col">Exercice</th-->
										<!--th scope="col">Devise</th-->
										<th scope="col">Prix de revient</th>
										<th scope="col">Prix de vente</th>
										<th scope="col">Facturation</th>
										<th scope="col">Marge PR</th>											
										<th scope="col">Marge PR (%)</th>
										<th scope="col">Realisation (%)</th>
									</tr>
									</thead>
									<tbody>
										<c:set var="row" value="0"/>
										
										<c:set var="totalPrixRevient" value="0"/>
										<c:set var="totalPrixVente" value="0"/>
										<c:set var="totalFacture" value="0"/>

										<c:set var="line" value="even"/>
										
										<c:forEach var="item" items="${viewprofitability.data}" varStatus="loop">
											
										

												<c:set var="totalPrixRevient" value="${item.prixRevient + totalPrixRevient}"/>
												<c:set var="totalPrixVente" value="${item.prixVente + totalPrixVente}"/>
												<c:set var="totalFacture" value="${item.prixFacture + totalFacture}"/>

												<c:choose>
													<c:when test='${row % 2 eq 0 }'>
													 <c:set var="line" value="odd" />
														<tr class="${line}" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
													</c:when>
													<c:otherwise>
													 <c:set var="line" value="odd" />
														<tr class="${line}" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
													</c:otherwise>
												</c:choose>

									     		<%--c:set var="countYears" value="${fn:length(viewprofitability.param.years)}"/--%>
									     		<c:set var="countYears" value="0"/>
												<c:if test='${countYears eq 0}'>
													<c:set var="countYears" value="1"/>
												</c:if>
												<th scope="row" class="column1">
                                                        <span style=" color: purple;">
                                                            <%--fmt:formatNumber  minFractionDigits="0">
                                                                ${1+ (loop.index / countYears)}
                                                            </fmt:formatNumber--%>
                                                            ${item.exercise}
                                                        </span>
                                                    </th>
												<th scope="row" class="column1">
														<span style=" color: purple;">
															<%--fmt:formatNumber  minFractionDigits="0">
																${1+ (loop.index / countYears)}
															</fmt:formatNumber--%>
															${item.year}
														</span>
													</th>
									     		<c:if test='${row % countYears eq 0}'>
													

													<td rowspan="${countYears}">${item.associate}</td>
													<td rowspan="${countYears}">${item.manager}</td>

													<td scope="row" class="column1" rowspan="${countYears}">
														<span style=" color: blue;"><i>${item.customerName}</i></span>
													</td>


												</c:if>

												<td scope="row" class="column1">
														
														<c:choose>
														<c:when test='${item.status eq "PENDING" }'>
														 	<span id="${item.id}.status" style=" color: blue;">En Attente</span>
														</c:when>

														<c:when test='${item.status eq "ONGOING" }'>
														 	<span id="${item.id}.status" style=" color: green;">En cours</span>
														</c:when>

														<c:when test='${item.status eq "STOPPED" }'>
														 	<span id="${item.id}.status" style=" color: black;">Arr&eacute;t&eacute;</span>
														</c:when>

														<c:when test='${item.status eq "CANCELLED" }'>
														 	<span id="${item.id}.status" style=" color: bleck;">Annul&eacute;</span>
														</c:when>

														<c:when test='${item.status eq "CLOSED" }'>
														 	<span id="${item.id}.status" style=" color: red;">Termin&eacute;</span>
														</c:when>
														
														<c:when test='${item.status eq "TRANSFERED" }'>
														 	<span id="${item.id}.status" style=" color: blue;">Transf&eacute;r&eacute;</span>
														</c:when>

														<c:when test='${item.status eq "TO_TRANSFERED" }'>
														 	<span id="${item.id}.status" style=" color: blue;">A Transf&eacute;r&eacute;</span>
														</c:when>
													</c:choose>
													</td>
												
												<%--td>
													<span style="color:rgb(218,69,37);">
														${item.year}
													</span>
												</td--%>
												<!--td><strong>EURO</strong></td-->
												<td><span style=" color: black;"><fmt:formatNumber type="currency" currencySymbol="&euro;">${item.prixRevient}</fmt:formatNumber></span></td>
												<td><span style=" color: black;"><fmt:formatNumber type="currency" currencySymbol="&euro;">${item.prixVente}</fmt:formatNumber></span></td>
												<td><span style=" color: black;"><fmt:formatNumber type="currency" currencySymbol="&euro;">${item.prixFacture}</fmt:formatNumber></span></td>
												<c:choose>
														<c:when test='${item.margePrixRevient < 0 }'>
															
															<c:set var="backgroundStyle" value="color:blue;background-color:#FF6262;font-weight: bold;"/>
														</c:when>
														
														<c:when test='${item.margePrixRevient > 0 }'>
															
															<c:set var="backgroundStyle" value="color:blue;background-color:cyan;font-weight: bold;"/>
														</c:when>
								
														<c:otherwise>
															<c:set var="backgroundStyle" value="color:blue;background-color:white;font-weight: bold;"/>
														</c:otherwise>
													</c:choose>
												<td style="${backgroundStyle}">
													
													<fmt:formatNumber type="currency" currencySymbol="&euro;">${ item.margePrixRevient}</fmt:formatNumber></span>
												</td>
												<c:choose>
														<c:when test='${item.percentageMargePrixRevient < 0 }'>
															
															<c:set var="backgroundStyle2" value="color:blue;background-color:#FF6262;font-weight: bold;"/>
														</c:when>
														<c:when test='${item.percentageMargePrixRevient > 0 }'>
															
															<c:set var="backgroundStyle2" value="color:blue;background-color:cyan;font-weight: bold;"/>
														</c:when>
								
														<c:otherwise>
															<c:set var="backgroundStyle2" value="color:blue;background-color:white;font-weight: bold;"/>
														</c:otherwise>
													</c:choose>
												<td style="${backgroundStyle2}">													
													<fmt:formatNumber type="percent">${item.percentageMargePrixRevient}</fmt:formatNumber>
													
												</td>
												
												<c:choose>
														<c:when test='${item.realisation < 0 }'>
															
															<c:set var="backgroundStyle3" value="color:blue;background-color:#FF6262;font-weight: bold;"/>
														</c:when>
														
														<c:when test='${item.realisation > 0 }'>
															
															<c:set var="backgroundStyle3" value="color:blue;background-color:cyan;font-weight: bold;"/>
														</c:when>
								
														<c:otherwise>
															<c:set var="backgroundStyle3" value="color:blue;background-color:white;font-weight: bold;"/>
														</c:otherwise>
													</c:choose>
												<td style="${backgroundStyle3}">
													
													<fmt:formatNumber type="percent" >${ item.realisation}</fmt:formatNumber>
													
												</td>
											</tr>

											

											
											<c:set var="row" value="${row + 1}"/>
											<c:if test='${row % countYears eq 0}'>
													<c:set var="margePrTotal" value="${totalFacture - totalPrixRevient}"/>
													<c:set var="margePrTotalPercentage" value="0"/>
													<c:set var="realisationTotale" value="0"/>
													<c:choose>
														<c:when test='${totalPrixRevient != 0 }'>
															<c:set var="margePrTotalPercentage" value="${ (margePrTotal /totalPrixRevient) * 100 }"/>
														</c:when>
														<c:otherwise>
															<c:set var="margePrTotalPercentage" value="0"/>
														</c:otherwise>
													</c:choose>

													<c:choose>
														<c:when test='${totalPrixVente != 0 }'>
															<c:set var="realisationTotale" value="${ (totalFacture /totalPrixVente) * 100 }"/>
														</c:when>
														<c:otherwise>
															<c:set var="realisationTotale" value="0"/>
														</c:otherwise>
													</c:choose>
												<%--tr class="even">
												<td colspan="5">Total</td>
												<td><strong><fmt:formatNumber type="currency" currencySymbol="&euro;">${totalPrixRevient}</fmt:formatNumber></strong></td>
												<td><strong><fmt:formatNumber type="currency" currencySymbol="&euro;">${totalPrixVente}</fmt:formatNumber></strong></td>
												<td><strong><fmt:formatNumber type="currency" currencySymbol="&euro;">${totalFacture}</fmt:formatNumber></strong></td>
												<td>
												
												
													<c:choose>
														<c:when test='${margePrTotal < 0 }'>
															<span style="color:red;"><img src="images/vote_arrow_down.gif" border="0" />
														</c:when>
														<c:otherwise>
															<span style="color:green;"><img src="images/progress_arrow_up.png" border="0"/>
														</c:otherwise>
													</c:choose>
													<strong><fmt:formatNumber type="percent">${ margePrTotal}</fmt:formatNumber></strong>
													</span>
												
												
												
												
												</td>
												<td>
													<c:choose>
														<c:when test='${margePrTotalPercentage < 0 }'>
															<span style="color:red;"><img src="images/vote_arrow_down.gif" border="0" />
														</c:when>
														<c:otherwise>
															<span style="color:green;"><img src="images/progress_arrow_up.png" border="0"/>
														</c:otherwise>
													</c:choose>
													<strong><fmt:formatNumber type="percent">${ margePrTotalPercentage}</fmt:formatNumber></strong>
													</span>
												</td>
												<td><strong>
												<c:choose>
														<c:when test='${realisationTotale < 50 }'>
															<span style="color:red;"><img src="images/vote_arrow_down.gif" border="0" />
														</c:when>
														<c:otherwise>
															<span style="color:green;"><img src="images/progress_arrow_up.png" border="0"/>
														</c:otherwise>
													</c:choose>
													<fmt:formatNumber type="percent">${realisationTotale}</fmt:formatNumber>
													</span></strong></td>
												
												</tr--%>
												
												<c:set var="totalPrixRevient" value="0"/>
												<c:set var="totalPrixVente" value="0"/>
												<c:set var="totalFacture" value="0"/>
											</c:if>
									  </c:forEach>
									</tbody>
								</table>
								<br/>
						</div>
	</div>
</div>

<!-- END ADDED PART -->   
	<p class="pagination2" align="center">
			<span class="pagelinks">
				<!--span style="color:purple;">${ fn:length(viewbudget.budgets) } budgets found...</span-->
				<c:if test="${totalPage > 1}">
				    <c:set var="url" value="p=" />
				    <c:if test="${profitability_associe != null}">
				        <c:set var="url" value="profitability_associe=${profitability_associe}&${url}" />
				    </c:if>
				    <c:if test="${profitability_manager != null}">
                        <c:set var="url" value="profitability_manager=${profitability_manager}&${url}" />
                    </c:if>
                    <c:if test="${profitability_customer != null}">
                        <c:set var="url" value="profitability_customer=${profitability_customer}&${url}" />
                    </c:if>
                    


					<span class="pagination2" align="center">
						<span><a href="${ctx}/showProfitabilityPerCustomerPage.htm?${url}${1}">|</a></span>
				    
				        <c:choose>
				            <c:when test="${currentPage <= 1}">
								<span> <a href="${ctx}/showProfitabilityPerCustomerPage.htm?${url}${currentPage }">Previous</a></span>
				            </c:when>
				            <c:otherwise>
				                <span> <a href="${ctx}/showProfitabilityPerCustomerPage.htm?${url}${currentPage - 1}">Previous</a></span>
				            </c:otherwise>
				        </c:choose>
					
						<c:set var="max_page" value="10" />
						
						<c:set var="page_toshow" value="${totalPage}" />
						<c:set var="start_toshow" value="${currentPage}" />
						
						<c:if test="${currentPage + max_page > totalPage}">
							<c:set var="page_toshow" value="${totalPage}" />
						</c:if>
						
						<c:if test="${currentPage + max_page <= totalPage}">
							<c:set var="page_toshow" value="${currentPage + max_page}" />
						</c:if>
						
						<c:if test="${page_toshow - currentPage < max_page}">
							<c:if test="${page_toshow - max_page > 0}">
								<c:set var="start_toshow" value="${page_toshow - max_page}" />
							</c:if>			
						</c:if>
					
				        <c:forEach var="x" begin="${start_toshow}" end="${page_toshow}" step="1">
				            <c:choose>
				                <c:when test="${currentPage == x}">
									<span style="margin-left:5px; margin-right:5px;"><a style='background-color: #7C9FCB;'><c:out value="${x}"/></a></span></b>
				                </c:when>
				                <c:otherwise>
									<span><a href="${ctx}/showProfitabilityPerCustomerPage.htm?${url}${x}"><c:out value="${x}"/></a></span>
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
					
				        <c:choose>
				            <c:when test="${currentPage >= totalPage}">
				               <span><a href="${ctx}/showProfitabilityPerCustomerPage.htm?${url}${currentPage }">  Next </a></span>
				            </c:when>
				            <c:otherwise>
				                <span><a href="${ctx}/showProfitabilityPerCustomerPage.htm?${url}${currentPage + 1}"> Next </a></span>
				            </c:otherwise>
				        </c:choose>
						<span><a href="${ctx}/showProfitabilityPerCustomerPage.htm?${url}${totalPage}">|</a></span>
						</span>
					</c:if>
			</span>
			</p>

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
        <span id="formTitle" style="color:rgb(218,69,37);">Select exercises</span>
    </div>
    <div class="bd">
	<form name="addExercisesForm" action="${ctx}/showProfitabilityPerCustomerPage.htm" method="post">
			<table id="ver-zebra2" width="100%"  class="formlist">
			 <c:set var="line" value="even"/>
			 
			<c:forEach var="allItem" items="${viewprofitability.exercicesOptions}">
			<c:set var="present" value="false"/>
			<c:choose>
                        <c:when test='${line == "even"}'>
                            <c:set var="line" value="odd" />
                        </c:when>
                        <c:otherwise>
                            <c:set var="line" value="even" />
                        </c:otherwise>
                     </c:choose>
				<tr class="${line}">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="year_${allItem.id}" value="${allItem.id}" 
								<c:forEach var="yearItem" items="${viewprofitability.param.years}" varStatus="loop">
									<c:if test='${yearItem == allItem.id}'> <c:set var="present" value="true"/></c:if>
								 </c:forEach>
								 <c:if test='${present == "true"}'> checked</c:if>
							/>
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);"><strong>${allItem.name}</strong></span>
						</td>
				</tr>
			 </c:forEach>
			</table>
	</form>
	</div>
	
</div>
	
