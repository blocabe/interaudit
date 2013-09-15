<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- START ADDED PART -->







			<!-- START ADDED PART -->   

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
	padding: 6px 7px;
	border-top: 1px solid #0066aa;
	border-right: 1px solid #0066aa;
	border-bottom: 1px solid #0066aa;
	border-left: 1px solid #fff;
	color: #039;
	background: #eff2ff;
}
#ver-zebra td
{
	padding: 4px 7px;
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

a {text-decoration:none;color: blue;}

 .normal { background-color: white }
 .highlight { border: 4px solid red;font-family:Arial,Helvetica,sans-serif;font-size: 200%;font-weight:bold;color: blue;}
  
</style>
			<%--div>
			
			 <div class="nav_bottom" style=" text-align:center; ">
				<>div class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">
					<span class="galphabet_center select"><a href="javascript:go_letter('')"><span style="color:orange;">All</span></a></span>			
					<span class="galphabet_center"><a href="javascript:go_letter('A')"><span style="color:orange;">A</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('B')"><span style="color:orange;">B</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('C')"><span style="color:orange;">C</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('D')"><span style="color:orange;">D</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('E')"><span style="color:orange;">E</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('F')"><span style="color:orange;">F</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('G')"><span style="color:orange;">G</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('H')"><span style="color:orange;">H</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('I')"><span style="color:orange;">I</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('J')"><span style="color:orange;">J</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('K')"><span style="color:orange;">K</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('L')"><span style="color:orange;">L</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('M')"><span style="color:orange;">M</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('N')"><span style="color:orange;">N</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('O')"><span style="color:orange;">O</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('P')"><span style="color:orange;">P</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('Q')"><span style="color:orange;">Q</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('R')"><span style="color:orange;">R</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('S')"><span style="color:orange;">S</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('T')"><span style="color:orange;">T</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('U')"><span style="color:orange;">U</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('V')"><span style="color:orange;">V</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('W')"><span style="color:orange;">W</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('X')"><span style="color:orange;">X</span></a></span>
					<span class="galphabet_center"><a href="javascript:go_letter('Y')"><span style="color:orange;">Y</span></a></span>
					<span class="galphabet_center" ><a href="javascript:go_letter('Z')"><span style="color:orange;">Z</span></a></span>
				</div>
			<div class="nav_alphabet" style="border-bottom: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 11px Verdana, sans-serif;">
			
			<form name="listForm" action="requestlist.htm" method="post" >

				

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercise</strong> &nbsp;<select style="font:10px Verdana, sans-serif;margin-right:10pt;" >
				  <option>2009</option>
				  <option selected>2008</option>
				  <option>2007</option>
				  <option>2006</option>
				</select> 
				</span>			
				&nbsp;

				<span class="galphabet_center"><input type="button" class="button120" value="Search" onclick="Javascript:searchFilter();"/></span>		
							
				</form>
			</div>
	</div--%>						
 
<div style="border-bottom: 1px solid gray;">
			    
			        <table id="ver-zebra" width="100%" cellspacing="0" >
			        	<caption><span style="color:red;">Employee Financial Data  Overview</span></caption>
									<colgroup>
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
										<col class="vzebra-odd" />
									</colgroup>
									<thead>
									<tr class="odd">
									<th scope="col">Employee</th>
									<th scope="col">Prix Revient</th>									
									<th scope="col">Prix Vente</th>											
									<th scope="col">Cout Horaire</th>
									<th scope="col">Statut</th>
									</tr>
									</thead>
									<tbody>
									
										 <c:set var="row" value="0"/>
										 <c:forEach var="item" items="${viewFinancialDataUsers}" varStatus="loop">
											 	<c:choose>
										    		<c:when test='${row % 2 eq 0 }'>
										      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
										      		</c:when>
										      		<c:otherwise>
										      			<tr style="background: #dddddd;"  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
										      		</c:otherwise>
										     	</c:choose>
												<th align="left"><span style="color:purple;" >${item.lastName}&nbsp;${item.firstName}</span></th>
												<td><span style="color:red;">${item.prixRevient}</span></td>
												<td><span style="color:red;">${item.prixVente}</span></td>
												<td><span style="color:red;">${item.coutHoraire}</span></td>
												<td>${item.position.name}</td>
											</tr>
										<c:set var="row" value="${row + 1}"/>
										</c:forEach>
								</tbody>
								</table>
						</div>
						<br/>
					</div>
						

						 


		<!-- END ADDED PART -->   