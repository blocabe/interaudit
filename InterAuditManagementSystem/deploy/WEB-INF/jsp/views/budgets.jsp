<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<style>
.normal { background-color: white }
.highlight { border: 4px solid blue;font-family:Arial,Helvetica,sans-serif;font-size: 200%;font-weight:bold;color: blue;}
    
</style>
<form  method="post" class="standardForm"> 
<c:set var="row" value="0"/>
										<c:set var="line" value="even"/>
										
										<c:set var="iDNode" value="node-"/>
										<c:set var="indexNode" value="0"/>
										<c:set var="childClassNode" value="child-of-node-"/>
										<c:set var="indexChildClassNode" value="0"/>
										<c:set var="currentCustomerCode" value="-1"/>
										<c:if test='${fn:length(viewbudget.budgets) > 0}'>
										
												<spring:bind path="viewbudget.budgets">	
												<c:forEach var="item" items="${viewbudget.budgets}" varStatus="loop">
													
													
													

													<c:set var="countYears" value="${fn:length(viewbudget.param.years)}"/>
													<c:if test='${countYears eq 0}'>
														 <c:set var="countYears" value="1"/>											
													</c:if>
																								
													<c:if test='${item.customerCode != currentCustomerCode }'>
														<c:set var="indexNode" value="${indexNode + 1}"/>
														<tr id="${iDNode}${indexNode}">                                                                                                                                       
															<td style="${showcolClient};background-color:white;border:1px solid #0066aa;border-right:none;" align="left" scope="row"  colspan="2">
																	
																	<img src="images/folder.png" border="0"/>
																				<span style=" color: blue;">${item.customerName}</span>	
																	  <%--t:tooltip>  
																		   <t:text><em>
																																																 
																		   </em></t:text>
																		  <t:help width="200">
																		   <font color="blue"><strong>${item.customerName}</strong></font><br/><hr/> 
																		   <font color="black"><strong>${item.comments}</strong></font>                                                    
																			</t:help> 
																		</t:tooltip--%>
																	
																	
															</td>
															<!--td style="${showcolExercice};border-right:none" ><span style="color: blue;"><i></i></span></td-->
															<td style="${showcolExercice};border-right:none" ><span style="color: blue;"><i></i></span></td>                                                
															<td style="${showcolNumero};border-right:none"><span style=" color: blue;"><i></i></span></td>
															<td style="${showcolType};border-right:none"><span style=" color: blue;"><i></i></span></td>                                                                                                    
															<td style="${showcolOrigine};border-right:none"><span style=" color: blue;"><i></i></span></td>
															<td style="${showcolAssocie};border-right:none"><span style=" color: blue;"><i></i></span></td>
															<td style="${showcolManager};border-right:none"><span style=" color: blue;"><i></i></span></td>                                             
															<td style="${showcolBudgetprevu};border-right:none"><span style=" color: blue;"><i></i></span></td>                                                 
															<td style="${showcolBudgetreporte};border-right:none"><span style=" color: blue;"><i></i></span></td>
															<td style="${showcolBudgettotal};border-right:none" ><span style=" color: blue;"><i></i></span></td>
															<td style="${showcolBudgetnonFiable};border-right:none" ><span style=" color: blue;"><i></i></span></td>
															<td style="${showcolFacturation};border-right:none" ><span style=" color: blue;"><i></i></span></td>
															<td style="${showcolComment};border-right:none"><span style="color: blue;"><i></i></span></td>
															<td style="${showcolStatut};border-right:none"><span style=" color: blue;"><i></i></span></td>
															<td><span style=" color: blue;"><i></i></span></td>                                               
														</tr>                                            
														<c:set var="currentCustomerCode" value="${item.customerCode}"/>
														<c:set var="indexChildClassNode" value="0"/> 
													</c:if>
													
													<c:set var="indexChildClassNode" value="${indexChildClassNode + 1}"/>
													<tr id="${iDNode}${indexNode}-${indexChildClassNode}" class="${childClassNode}${indexNode}" onMouseOver="this.className='highlight';" onMouseOut="this.className='normal';">                                                                                                                                       
													<td style="${showcolClient} ;border-bottom:none;" align="center" scope="row"  rowspan="${countYears}">
																<img src="images/toggle-expand-dark.png" border="0"/>
																
																
														</td>
														<td style="${showcolExercice};background-color:#ffffcc;" >
														<c:choose>
																<c:when test='${(item.finalBill eq true) && (item.status != "CLOSED")}'>
																	<t:tooltip>  
																			   <t:text>
																			   <span style="color: red;"><strong>${item.year}</strong></span><em>
																				<img src="images/warning.gif" border="0"/>	                                                                                                                  
																			   </em>																   
																			  
																			   </t:text>
																			  <t:help width="200">
																				<font color="red"><strong>This mission has a final bill and is not closed...Please close it</strong></font><br/><hr/> 	                                                                                                                   
																			   </t:help> 
																	</t:tooltip>
																</c:when>
																<c:otherwise>
																	<span style="color: blue;"><strong>${item.year}</strong></span>
																</c:otherwise>
															</c:choose>
														
														</td>
														<td style="${showcolExercice};background-color:#ffffcc;" ><span style="color: red;"><strong>
														

																		<t:tooltip>  
																		   <t:text><em>
																		   <c:choose>
																			   <c:when test='${ viewbudget.exercises[0].status != "PENDING"}'>
																					<a href="${ctx}/showMissionPropertiesFromBudgetPage.htm?id=${item.id}">
																						<span style=" color: purple;"><i>${item.mandat}</i></span>
																					</a>
																				</c:when>
																				<c:otherwise>
																					<span style=" color: blue;"><i>${item.mandat}</i></span>
																				</c:otherwise>
																			</c:choose>
																		   </em></t:text>
																		  <t:help width="200">
																		   <font color="blue"><strong>${item.customerName}</strong></font><br/><hr/> 
																		   <font color="black"><strong>${item.comments}</strong></font>                                                    
																			</t:help> 
																		</t:tooltip>
														</strong></span></td>
														
														
														<td style="${showcolNumero};background-color:#ffffcc;"  scope="row" class="column1"  rowspan="${countYears}">
															&nbsp;<span style=" color: blue;"><i>${item.customerCode}</i></span>
														</td>
														
														
														<td style="${showcolType};background-color:#ffffcc;" align="center" scope="row" class="column1" rowspan="${countYears}">																										
															<t:tooltip>  
																		  <t:text><em>                                                                        
																			<span style=" color: blue;"><i>${fn:substring(item.missionType,0,15)}</i></span>
																			<c:if test='${ item.interim == true}'>
																			 <span style=" color: red;font-size:xx-small;"><i>(Interim)</i></span>
																			</c:if>
																			</span>
																		  </em></t:text>
																		  <t:help width="100">
																		   <font color="blue"><strong>${viewbudget.typeOptionsMap[item.missionType]}</strong></font><br/><hr/>                                                                                                                       
																		  </t:help> 
															</t:tooltip>
														</td>
														<td style="${showcolOrigine};background-color:#ffffcc;" rowspan="${countYears}"><span style=" color: blue;"><i>${item.origin}</i></span></td>

													
														<td style="${showcolAssocie};background-color:#ffffcc;"  rowspan="${countYears}"><span style=" color: blue;">${item.associate}</span></td>
														<td style="${showcolManager};background-color:#ffffcc;"  rowspan="${countYears}"><span style=" color: blue;">${item.manager}</span></td>
														
														
														
														<td style="${showcolBudgetprevu};background-color:#ffffcc;">
															<!--input type="text"  value="${item.expectedAmount}" onKeyDown="return quelle_touche(event);" -->
															<!--form:input size="30" maxlength="255" path="viewbudget.budgets[${loop.count-1}].expectedAmount"/-->		
															<span class="TO_EDIT">
															<!--form:input size="30" maxlength="255" path="viewbudget.budgets[${loop.count-1}].expectedAmountEdit" /-->
															</span>
															<span class="TO_READ" style="color: blue;">
																<fmt:formatNumber type="currency" currencySymbol="&euro;">
																${item.expectedAmount}
																</fmt:formatNumber>
															</span>|
															<span class="TO_READ"  style="color:RGB(219,78,36);">
																<fmt:formatNumber type="currency" currencySymbol="&euro;">
																${item.expectedAmountRef}
																</fmt:formatNumber>
															</span>
															
														
														</td>
														<td style="${showcolBudgetreporte};background-color:#ffffcc;" >
															<!--input type="text"  value="${item.reportedAmount}" onKeyDown="return quelle_touche(event);" -->
															
															<span class="TO_EDIT">
															<!--form:input size="30" maxlength="255" path="viewbudget.budgets[${loop.count-1}].reportedAmountEdit" /-->
															</span>
															<span class="TO_READ" style="color: blue;">
																<fmt:formatNumber type="currency" currencySymbol="&euro;">
																${item.reportedAmount}
																</fmt:formatNumber>
															</span>|
															<span class="TO_READ" style="color:RGB(219,78,36);">
																<fmt:formatNumber type="currency" currencySymbol="&euro;">
																${item.reportedAmountRef}
																</fmt:formatNumber>
															</span>
														</td>
														<td style="${showcolBudgettotal};background-color:#ffffcc;" >
															<c:if test='${item.fiable eq true}'>
																<span style="color: blue;"><strong>
																	<fmt:formatNumber  type="currency" currencySymbol="&euro;">
																		${item.expectedAmount + item.reportedAmount}
																	</fmt:formatNumber>
																</strong></span>|
																<span style="color:RGB(219,78,36);"><strong>
																	<fmt:formatNumber  type="currency" currencySymbol="&euro;">
																		${item.expectedAmountRef + item.reportedAmountRef}
																	</fmt:formatNumber>
																</strong></span>
															</c:if>
														</td>

														<td style="${showcolBudgetnonFiable};background-color:#ffffcc;" >
														<c:if test='${item.fiable eq false}'>
															<span style="color: red;"><strong>
																<fmt:formatNumber  type="currency" currencySymbol="&euro;">
																	${item.expectedAmount + item.reportedAmount}
																</fmt:formatNumber></strong>
															</span>
														</c:if>
														</td>
														<td style="${showcolFacturation};background-color:#ffffcc;" >
															
															
															<span style="color: green;">
																					<strong>
																						<fmt:formatNumber type="currency" currencySymbol="&euro;">
																						${item.effectiveAmount}
																						</fmt:formatNumber>
																					</strong>
																				</span>
														</td>
														<td style="${showcolComment};background-color:#ffffcc;" ><span style=" color: blue;">
														<t:tooltip>  
																   <t:text><em>
																   <span style="color:purple;"><i>${fn:substring(item.comments,0,15)}</i></span></em></t:text>
																  <t:help width="100" >
																   <font color="black"><strong>${item.comments}</strong></font> 														   
																	</t:help> 
														</t:tooltip>
														</span></td>
														<td style="${showcolStatut};background-color:#ffffcc;" >
															
															<c:choose>
																<c:when test='${item.status eq "PENDING" }'>												
																	<span style=" color: blue;">NEW</span>
																</c:when>
																<c:when test='${item.status eq "TRANSFERED" }'>
																	<c:choose>
																		<c:when test='${viewbudget.exercises[0].status != "CLOSED"}'>												
																		<span style=" color: purple;">REPORT</span>
																		</c:when>
																		<c:otherwise>
																		 <span style=" color: purple;">TRANSFERED</span>
																		</c:otherwise>
																	</c:choose>
																	
																</c:when>
																<c:when test='${item.status eq "CLOSED" }'>												
																	<span style=" color: red;">${item.status}</span>
																</c:when>
																<c:otherwise>
																	<span style=" color: green;">${item.status}</span>
																</c:otherwise>
															</c:choose>
														</td>
														<td  style="background-color:#ffffcc;">
														<interaudit:accessRightSet right="MODIFY_BUDGET">
														<a href="#">
														<img src="images/Table-Edit.png" tabindex="999" border="0" alt="Edit budget properties" onclick="editBudgetItem(${item.id})"/></a>
														 													
														<c:if test='${item.deletable == true }'>
															<a href="#">
															<img src="images/Table-Delete.png" tabindex="999" border="0" alt="Delete budget" onclick="deleteBudgetItem(${item.id},${item.year}, '${item.customerName}','${item.missionType}')"/></a>
															&nbsp;
														</c:if>
														</interaudit:accessRightSet>
														
														&nbsp;
														 <c:if test='${ (currentExercise != null) && (currentExercise.status != "CLOSED") && (currentExercise.year eq viewbudget.param.years[0])}'>
															<c:if test='${item.manager == context.currentUser.code }'>
																<a href="${ctx}/showAdminitrateMissionPage.htm?id=${item.missionId}"><span style=" color: blue;"><img src="images/engrenage.gif" tabindex="999" border="0"/></span></a>
															</c:if>
															<c:if test='${item.manager != context.currentUser.code }'>
																--
															</c:if>
														</c:if>

														 </td>

														
													</tr>
												<c:set var="row" value="${row + 1}"/>
											
											  </c:forEach>
											  </spring:bind>
											  <br/>
											  <%--tr>                                                                                                                                       
															<td style="border: 0px;" colspan="8">Total </td>															
															<td style="border: 0px;"><span style="color: blue;"><i></i></span></td>                                                
															<td style="border: 0px;"><span style=" color: blue;"><i></i></span></td>
															<td style="border: 0px;">
															<span style="color: red;"><strong>
																<fmt:formatNumber  type="currency" currencySymbol="&euro;">
																	${viewbudget.annualBudgetSumData.expectedAmount + viewbudget.annualBudgetSumData.reportedAmount}
																</fmt:formatNumber></strong>
															</span>
															</td>                                                                                                    
															<td style="color: green;background-color:#white; border-right: 1px solid #black;"><span ><strong>
																<fmt:formatNumber  type="currency" currencySymbol="&euro;">
																	${viewbudget.annualBudgetSumData.effectiveAmount}
																</fmt:formatNumber></strong>
															</span>
															</td>
															<td style="border: 0px;" colspan="2"></td>
															                                            
														</tr--%> 
									 
			
									</c:if>
</form>	