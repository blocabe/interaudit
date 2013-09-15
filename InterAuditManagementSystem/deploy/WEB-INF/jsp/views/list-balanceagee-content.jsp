
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>





<script type="text/javascript" src="${ctx}/script/tabs.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>
<!-- START ADDED PART -->

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>


<link rel="stylesheet" type="text/css" href="${ctx}/script/build/datatable/assets/skins/sam/datatable.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/element/element-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datasource/datasource.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datatable/datatable.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-1.6.1.js"></script>


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
		border-right: 0px solid #0066aa;
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

  a {text-decoration:none;color: blue;}

 .normal { background-color: white }
 .highlight { border: 4px solid red;font-family:Arial,Helvetica,sans-serif;font-size: 200%;font-weight:bold;color: blue;}
    	 
		 
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
</style>






    
	
	
			

			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
									<caption><span style="color:black;font-weight: bold;">Balance agee ${ fn:length(viewbalanceagee.items) } entries found...</span></caption>
									<thead>
									<tr>
										<th scope="col" style="width:20%;">Client</th>
										<th scope="col" >Code</th>
										<th scope="col">Invoice value</th>
										<th scope="col">Current Month</th>
										<th scope="col">31-60 days</th>
										<th scope="col">61-90 days</th>
										<th scope="col">91-120 days</th>
										<th scope="col">121-150 days</th>
										<th scope="col">151-180 days</th>
										<th scope="col">> 180 days</th>
									</tr>
									</thead>
									
									 
									
									
									<tbody>
									<c:set var="row" value="0"/>
									<c:set var="totalFacture" value="0"/>
									<c:set var="totalCurrentMonth" value="0"/>
									<c:set var="total30Days" value="0"/>
									<c:set var="total60Days" value="0"/>
									<c:set var="total90Days" value="0"/>
									<c:set var="total120Days" value="0"/>
									<c:set var="total150Days" value="0"/>
									<c:set var="total180Days" value="0"/>
									<c:set var="totalPaid" value="0"/>
									
									 <c:forEach var="item" items="${viewbalanceagee.items}" varStatus="loop">

										<%--c:if test='${item.totalAmount > 0 }'--%>		
											<c:set var="totalFacture" value="${item.totalAmount + totalFacture }"/>
		                                    <c:set var="totalCurrentMonth" value="${item.amount[0] + totalCurrentMonth }"/>
		                                    <c:set var="total30Days" value="${item.amount[1] + total30Days }"/>
		                                    <c:set var="total60Days" value="${item.amount[2] + total60Days }"/>
		                                    <c:set var="total90Days" value="${item.amount[3] + total90Days }"/>
		                                    <c:set var="total120Days" value="${item.amount[4] + total120Days }"/>
		                                    <c:set var="total150Days" value="${item.amount[5] + total150Days }"/>
		                                    <c:set var="total180Days" value="${item.amount[6] + total180Days }"/>
											
											

											
									 	
										 	<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
									     	
											<td  style="color:black;text-align:left;">	
											 ${ fn:toUpperCase(item.customerName) }
											</td>											
											<td> <span style="color:black;text-align:left;">${item.customerCode}</span></td>
											<td style="color:black;background-color:#bdb76b;font-weight: bold;">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${item.totalAmount}
													</fmt:formatNumber>
											</td>
											<c:choose>
									    		<c:when test='${item.amount[0] > 0 }'>												
												 <c:set var="backgroundStyle" value="color:blue;background-color:#adff2f;font-weight: bold;"/>
									      		</c:when>
									      		<c:otherwise>
												 <c:set var="backgroundStyle" value="color:black;width:20em;"/>
									      		</c:otherwise>
									     	</c:choose>
											<td style="${backgroundStyle}">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${item.amount[0]}
													</fmt:formatNumber>
											</td>
											<c:choose>
									    		<c:when test='${item.amount[1] > 0 }'>												
												 <c:set var="backgroundStyle" value="color:blue;background-color:#FFd700  ;font-weight: bold;"/>
									      		</c:when>
									      		<c:otherwise>
												 <c:set var="backgroundStyle" value="color:black;"/>
									      		</c:otherwise>
									     	</c:choose>
											<td style="${backgroundStyle}">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${item.amount[1]}
													</fmt:formatNumber>
											</td>
											<c:choose>
									    		<c:when test='${item.amount[2] > 0 }'>												
												 <c:set var="backgroundStyle" value="color:blue;background-color:#ffa500;font-weight: bold;"/>
									      		</c:when>
									      		<c:otherwise>
												 <c:set var="backgroundStyle" value="color:black;"/>
									      		</c:otherwise>
									     	</c:choose>
											<td style="${backgroundStyle}">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${item.amount[2]}
													</fmt:formatNumber>
											</td>
											<c:choose>
									    		<c:when test='${item.amount[3] > 0 }'>												
												 <c:set var="backgroundStyle" value="color:blue;background-color:#FF6262 ;font-weight: bold;"/>
									      		</c:when>
									      		<c:otherwise>
												 <c:set var="backgroundStyle" value="color:black;"/>
									      		</c:otherwise>
									     	</c:choose>
											<td style="${backgroundStyle}">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${item.amount[3]}
													</fmt:formatNumber>
											</td>
											<c:choose>
									    		<c:when test='${item.amount[4] > 0 }'>												
												 <c:set var="backgroundStyle" value="color:blue;background-color:#FF4500;font-weight: bold;"/>
									      		</c:when>
									      		<c:otherwise>
												 <c:set var="backgroundStyle" value="color:black;"/>
									      		</c:otherwise>
									     	</c:choose>
											<td style="${backgroundStyle}">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${item.amount[4]}
													</fmt:formatNumber>
											</td>
											<c:choose>
									    		<c:when test='${item.amount[5] > 0 }'>												
												 <c:set var="backgroundStyle" value="color:blue;background-color:#DC143c;font-weight: bold;"/>
									      		</c:when>
									      		<c:otherwise>
												 <c:set var="backgroundStyle" value="color:black;"/>
									      		</c:otherwise>
									     	</c:choose>
											<td style="${backgroundStyle}">
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${item.amount[5]}
													</fmt:formatNumber>
											</td>
											
											<c:choose>
									    		<c:when test='${item.amount[6] > 0 }'>												
												 <c:set var="backgroundStyle" value="color:blue;background-color:#ff0000;font-weight: bold;"/>
									      		</c:when>
									      		<c:otherwise>
												 <c:set var="backgroundStyle" value="color:black;"/>
									      		</c:otherwise>
									     	</c:choose>
											<td style="${backgroundStyle}">
											
													<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${item.amount[6]}
													</fmt:formatNumber>
											</td>
											
										
											
										</tr>
									
										<c:set var="row" value="${row + 1}"/>
										<%--/c:if--%>
									 
									  </c:forEach>

									 

									

									  
									
									</tbody>
									
										<tr>
										
										<td><span></span></td>
										<td><span style="font-weight: bold;">Total</span></td>
										<th>
										
											<span style="color:blue;font-weight: bold;">
										
											<i><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
															${totalFacture}
											</fmt:formatNumber></i>
											</span>
										</th>
										
										<th>
											<span style="color:blue;font-weight: bold;">
										
											<i><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
															${totalCurrentMonth}
											</fmt:formatNumber></i>
											
											</span>
										</th>
										<th>
											<span style="color:blue;font-weight: bold;" >
										
											<i><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${total30Days}
											</fmt:formatNumber></i>
											</span>
										</th>
										<th style="color:blue;font-weight: bold;">
											<i>
												<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
																${total60Days}
												</fmt:formatNumber>
											</i>										
										</th>
										
										<th style="color:blue;font-weight: bold;">
											<i>
											<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
															${total90Days}
											</fmt:formatNumber>
											</i>										
										</th>
										
										<th style="color:blue;font-weight: bold;">
										<i>
										<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${total120Days}
										</fmt:formatNumber>
										</i>
										
										</th>
										
										<th style="color:blue;font-weight: bold;">
										<i>
										<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${total150Days}
										</fmt:formatNumber>
										
										</i>
										</th>
										
										<th style="color:blue;font-weight: bold;">
										<i>
										<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
														${total180Days}
										</fmt:formatNumber>
										
										</i>
										</th>
										
										
									  </tr>
										
								</table>
						
							

	<!-- END ADDED PART -->  
	
	