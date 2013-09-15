<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
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

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script>

<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>
<link href="${ctx}/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/script/jquery.treeTable.js"></script>
<!--script type="text/javascript" src="${ctx}/script/budgetEdit.js"></script-->

<style type="text/css">



				.container23 {
						width: 100%;
						padding: 15px;
						/*margin: 3px 0 20px 0;*/
						border: 1px solid #ccc;
						background: #eee;
						text-align:center;
				}

				/* rounded */

				#nav23 {
						margin: 0;
						padding: 0 0 20px 10px;
						border-bottom: 1px solid #9FB1BC;
				}

				#nav23 li {
						margin: 0;
						width: 146px;
						padding: 0;
						display: inline;
						list-style-type: none;
				}

				#nav23 a:link, #nav23 a:visited {
						float: left;
						font-size: 10px;
						line-height: 14px;
						font-weight: bold;
						padding: 0 12px 6px 12px;
						text-decoration: none;
						
				}

				#nav23 a:link.active, #nav23 a:visited.active, #nav23 a:hover {
						color: #000;
						background: url(images/Pyramid.gif) no-repeat bottom center;
				}


				ul#nav23
	{  margin-bottom:0;  padding:0px 0; list-style:none;/* width:100%; height:19px;  border-bottom:1px solid #b9121b; font:normal 8pt verdana, arial, helvetica;*/}

ul#nav23 li
	{ margin:0; padding:0; display:block; float:left; position:relative; /*width:148px; border:2px solid yellow;*/ }
ul#nav23 li a:link,
ul#nav23 li a:visited
	{ padding:4px 0; display:block; text-align:center; text-decoration:none; /*background:#b9121b; color:#ffffff; width:148px; height:13px; */}
ul#nav23 li:hover a,
ul#nav23 li a:hover,
ul#nav23 li a:active
	{ padding:4px 0; display:block; text-align:center; text-decoration:none;/* background:#ec454e; color:#ffffff; width:146px; height:13px; border-left:1px solid #ffffff; border-right:1px solid #ffffff; */}
ul#nav23 li ul.navigation-2
	{ margin:0; padding:1px 1px 0; list-style:none; display:none; background:#eee; width:146px; position:absolute; top:21px; left:-1px; /*border:1px solid #b9121b;*/ border-top:none; }
ul#nav23 li:hover ul.navigation-2
	{ display:block; }
ul#nav23 li ul.navigation-2 li
	{ width:146px; clear:left; width:146px; }

ul#nav23 li ul.navigation-2 li a:link,
ul#nav23 li ul.navigation-2 li a:visited
	{ clear:left; background:#white; padding:4px 0; width:146px; border:none; border-bottom:1px solid #ffffff; position:relative; z-index:1000; }
ul#nav23 li ul.navigation-2 li:hover a,
ul#nav23 li ul.navigation-2 li a:active,
ul#nav23 li ul.navigation-2 li a:hover
	{ clear:left; background:#ec454e; padding:4px 0; width:146px; border:none; border-bottom:1px solid #ffffff; position:relative; z-index:1000; }

#menu{background-color:#f2f2f2; border: 1px solid  #0066aa;height:25px;}
#menu ul{float:left;height:15px;list-style:none;margin:0;padding:0 0px 0 0;}
#menu ul li{background-color:#2a5a7a;display:block;float:left;margin:0 0 0 5px;padding:0;}
#menu ul li a{background-color: #bbb;color:#333;display:block;font-size:1.4em;padding:0px 12px;text-decoration:none;}
#menu ul li a:hover{color:#fff;}
#menu ul li a.current{background-color:#2a5a7a;color:#ddd;border-bottom:1px solid #eee;}


		</style>



<script type="text/javascript">


  
$(document).ready(function()  {


  $("#ver-zebra").treeTable({
  expandable: true,
  initialState:"expanded"
});

<c:if test='${currentExercise.status eq "CLOSED"}'> 	
          showMessage("Aucun budget actif défini... Plusieurs fonctionnalités ne sont pas utilisables..","error");                    
    
  </c:if> 


<c:if test='${currentExercise.status != "CLOSED"}'> 
		<%--c:forEach var="allItem" items="${viewbudget.exercicesOptions}">
		  <c:if test='${viewExercise.exercise == allItem.id}'> 	
			  <c:if test='${ allItem.needUpdate == true}'>
				  showMessage("Please update this exercise [ ${viewExercise.exercise} ]","warning");                    
			  </c:if>
		  </c:if>                 
		</c:forEach--%>    


		<c:if test="${not empty actionErrors}">
						<c:forEach var="actionError"
							items="${actionErrors}">					
							showMessage("${actionError}","error");
						</c:forEach>
						<c:set var="actionErrors" value="" scope="session" />
					</c:if>
		<c:if test="${not empty successMessage}">

			<c:forEach var="message"
				items="${successMessage}">
				showMessage("${message}","ok");
			</c:forEach>
			<c:set var="successMessage" value="" scope="session" />
		</c:if>
  </c:if> 


});



  
</script>

<script type="text/javascript">

function show_hide_column(col_no, do_show) {

    var stl;
    if (do_show) stl = 'block'
    else         stl = 'none';

    var tbl  = document.getElementById('ver-zebra');
    var rows = tbl.getElementsByTagName('tr');

    for (var row=0; row<rows.length;row++) {
      var cels = rows[row].getElementsByTagName('td')
      cels[col_no].style.display=stl;
    }
  }

function quelle_touche(evenement)
{
	var touche = window.event ? evenement.keyCode : evenement.which;
	if (touche == 13) {

		var expression = document.getElementById("filter").value;
		if (expression.length < 3)
		{
			alert("Entrez 3 caractères au minimun...!");
			return false;
		}
		else{
			//return searchFromExpressionFilter();
			var url ="${ctx}/showGeneralBudgetPage.htm?budget_expression=" + expression;;
			window.location = url;
		}
	}
}


function quelle_touche2()
{
	//return searchFromExpressionFilterForCustCode();
	 var expression = document.getElementById("currentCustCode").value;
        // var url = "${ctx}/findBudgetsForExpression.htm?=" + expression;
	var url ="${ctx}/showGeneralBudgetPage.htm?customer_code=" + expression;;
	window.location = url;
 }





function exportExcel(id){
	var url ="${ctx}/showBudgetExcelViewPage.htm?id="+ id;
	window.location = url;
}



function filter2 (phrase, _id){
	var words = phrase.value.toLowerCase().split(" ");
	var table = document.getElementById(_id);
	var ele;
	for (var r = 1; r < table.rows.length; r++){
		ele = table.rows[r].innerHTML.replace(/<[^>]+>/g,"");
	        var displayStyle = 'none';
	        for (var i = 0; i < words.length; i++) {
		    if (ele.toLowerCase().indexOf(words[i])>=0)
			displayStyle = '';
		    else {
			displayStyle = 'none';
			break;
		    }
	        }
		table.rows[r].style.display = displayStyle;
	}
}




</script>



<script type="text/javascript">
	function tester1()
	{
		new Effect.toggle('div_1','blind');
	}
</script>


<style>
ul.pagination {
	margin: 0 auto;
	padding: 0;
	display: inline-block;
	background: #f2f2f2;	
	border: 1px solid #e6e6e6;
	color: #000;
	font-size: 10pt;
	font-weight: normal;
	list-style-type: none;
	white-space: nowrap;
	zoom:1;
	*display:inline;
}
ul.pagination li {
	float: left;
	padding: 1px;	
}
ul.pagination li.active {
	padding: 3px 1px;
	/*background: #e0e0e0;
	color: #174962;*/
	background: #11547a;
	color: #def;
}
ul.pagination a {
	display: block;
	float: left;
	padding: 3px 1px;
	/*background:url("../image/deco/menu_bg_pagination.gif") repeat-x #ffffff;*/
	background: #11547a;
	border: 1px solid #05314a;
	color: #fff;
	line-height: 1em;
	text-decoration: none;
}
ul.pagination a:hover {
	/*background: #11547a;*/
	background: #fff;
	border: 1px solid #036;
	/*color: #def;*/
	color:#11547a;
}

</style>
<style>
.pagination2 span.current {
	
}


.pagination2 span.off,
.pagination2 span a,
.pagination2 span.current {
	/*border:2px solid #9AAFE5;
	padding-left:5px;
	padding-right:5px;*/
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
		color: #039;
		background: #eff2ff;
	}
	#ver-zebra td
	{
		/*padding: 4px 7px;*/
		border-right: 0px solid #0066aa;
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
</style>

			<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 11px Verdana, sans-serif;">
				
				
			
				
				<div  class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">
				
				
				
				<ul class="pagination">
							
							  <li style="margin-right:15px;border: 3px solid red;">							
								<img src="images/Search.png" border="0" alt="Edit"/>&nbsp;<span style="color:blue;"><b>Quick Search:</b></span> 
								<input style="padding:3pt;"  WIDTH="150" name="filter" id="filter" type="text"  value="${budget_expression}" onKeyDown="return quelle_touche(event);" > 
								<span style="color: blue;"><i>(3 car min)</i></span>
							  
							  </li>
							  <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=*">
										<c:if test='${viewbudget.param.customerStartString == null}'><span style="color:orange;font-weight:bold">Any</span></c:if>
										<c:if test='${viewbudget.param.customerStartString != null}'>Any</c:if>					
							  </a></li>
							  <li>
								  <a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=A">
										<c:if test='${viewbudget.param.customerStartString eq "A"}'><span style="color:orange;font-weight:bold">A</span></c:if>
										<c:if test='${viewbudget.param.customerStartString != "A"}'>A</c:if>					
								  </a>
							  </li>
							  <li>
								  <a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=B">
										<c:if test='${viewbudget.param.customerStartString eq "B"}'><span style="color:orange;font-weight:bold">B</span></c:if>
										<c:if test='${viewbudget.param.customerStartString != "B"}'>B</c:if>
								  </a>
							  </li>
							  <li>
								  <a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=C">
										<c:if test='${viewbudget.param.customerStartString eq "C"}'><span style="color:orange;font-weight:bold">C</span></c:if>
										<c:if test='${viewbudget.param.customerStartString != "C"}'>C</c:if>
								  </a>
							  </li>
							  <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=D">
									<c:if test='${viewbudget.param.customerStartString eq "D"}'><span style="color:orange;font-weight:bold">D</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "D"}'>D</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=E">
									<c:if test='${viewbudget.param.customerStartString eq "E"}'><span style="color:orange;font-weight:bold">E</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "E"}'>E</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=F">
									<c:if test='${viewbudget.param.customerStartString eq "F"}'><span style="color:orange;font-weight:bold">F</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "F"}'>F</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=G">
									<c:if test='${viewbudget.param.customerStartString eq "G"}'><span style="color:orange;font-weight:bold">G</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "G"}'>G</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=H">
									<c:if test='${viewbudget.param.customerStartString eq "H"}'><span style="color:orange;font-weight:bold">H</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "H"}'>H</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=I">
									<c:if test='${viewbudget.param.customerStartString eq "I"}'><span style="color:orange;font-weight:bold">I</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "I"}'>I</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=J">
									<c:if test='${viewbudget.param.customerStartString eq "J"}'><span style="color:orange;font-weight:bold">J</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "J"}'>J</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=K">
									<c:if test='${viewbudget.param.customerStartString eq "K"}'><span style="color:orange;font-weight:bold">K</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "K"}'>K</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=L">
									<c:if test='${viewbudget.param.customerStartString eq "L"}'><span style="color:orange;font-weight:bold">L</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "L"}'>L</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=M">
										<c:if test='${viewbudget.param.customerStartString eq "M"}'><span style="color:orange;font-weight:bold">M</span></c:if>
										<c:if test='${viewbudget.param.customerStartString != "M"}'>M</c:if>
									</a>
							 </li>
							 <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=N">
									<c:if test='${viewbudget.param.customerStartString eq "N"}'><span style="color:orange;font-weight:bold">N</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "N"}'>N</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=O">
									<c:if test='${viewbudget.param.customerStartString eq "O"}'><span style="color:orange;font-weight:bold">O</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "O"}'>O</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=P">
									<c:if test='${viewbudget.param.customerStartString eq "P"}'><span style="color:orange;font-weight:bold">P</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "P"}'>P</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=Q">
									<c:if test='${viewbudget.param.customerStartString eq "Q"}'><span style="color:orange;font-weight:bold">Q</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "Q"}'>Q</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=R">
									<c:if test='${viewbudget.param.customerStartString eq "R"}'><span style="color:orange;font-weight:bold">R</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "R"}'>R</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=S">
									<c:if test='${viewbudget.param.customerStartString eq "S"}'><span style="color:orange;font-weight:bold">S</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "S"}'>S</c:if>
								</a>
							 </li>
							  <li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=T">
									<c:if test='${viewbudget.param.customerStartString eq "T"}'><span style="color:orange;font-weight:bold">T</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "T"}'>T</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=U">
									<c:if test='${viewbudget.param.customerStartString eq "U"}'><span style="color:orange;font-weight:bold">U</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "U"}'>U</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=V">
									<c:if test='${viewbudget.param.customerStartString eq "V"}'><span style="color:orange;font-weight:bold">V</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "V"}'>V</c:if>
								</a>
							</li>
							<li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=W">
									<c:if test='${viewbudget.param.customerStartString eq "W"}'><span style="color:orange;font-weight:bold">W</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "W"}'>W</c:if>
								</a>
							</li>
							<li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=X">
									<c:if test='${viewbudget.param.customerStartString eq "X"}'><span style="color:orange;font-weight:bold">X</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "X"}'>X</c:if>
								</a>
							</li>
							<li><a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=Y">
									<c:if test='${viewbudget.param.customerStartString eq "Y"}'><span style="color:orange;font-weight:bold">Y</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "Y"}'>Y</c:if>
								</a>
							</li>
							<li>
								<a href="${ctx}/showGeneralBudgetPage.htm?startingLetterName=Z">
									<c:if test='${viewbudget.param.customerStartString eq "Z"}'><span style="color:orange;font-weight:bold">Z</span></c:if>
									<c:if test='${viewbudget.param.customerStartString != "Z"}'>Z</c:if>
								</a>
							</li>
							
							<c:if test='${viewbudget.customerOptions != null}'>							
							<li style="margin-left:15px;border: 3px solid red;">									
								<select style="font:10px Verdana, sans-serif;margin-right:10pt;" id="currentCustCode" onchange="return quelle_touche2();">	
								  <option value="-1" selected >Select one...</option>								
								  <c:forEach var="item" items="${viewbudget.customerOptions}" varStatus="loop">									
										<option value="${item.id}" <c:if test='${customer_code==item.id}'> selected</c:if>>${item.name}</option>	
								  </c:forEach>
								</select> 
							  </li>
							  </c:if>
							</ul>
							
							
				</div>
				<div>
					<ul id="nav23"  >
						<li>
						<a  style="background-image: url(images/met.gif);background-repeat:no-repeat;border: 1pt black solid;background-color: rgb(248, 246, 233);" href="${ctx}/homePage.htm"><span style="color: blue;">Manage</span></a>	
						<ul class="navigation-2">
						
									<interaudit:accessRightSet right="EXPORT_BUDGET_TO_EXCEL">
									 <li>
										<a  href="#" title="Export to excel" onclick="javascript:exportExcel(${viewExercise.id})">
										<span style="color: white;">Export to excel</span>
										</a>
									 </li>
									 </interaudit:accessRightSet>
									
									
									
									<interaudit:accessRightSet right="UPDATE_BUDGET_STATUS">
									 <c:if test='${viewbudget.exercises[0].status != "CLOSED"}'> 
										 <li>
											<a  href="#" title="Update Budget" onclick="javascript:updateBudget(${viewExercise.id})">
											<span style="color: white;">Update Budget</span>
											</a>
										 </li>
									 </c:if>
									  </interaudit:accessRightSet>
									
									
									<interaudit:accessRightSet right="ADD_MANDAT_FROM_BUDGET">
										 <c:if test='${viewbudget.exercises[0].status != "CLOSED"}'> 
											 <li>
												<a  href="#" title="Add mandat" onclick="resetForm()">
												<span style="color: white;">Add mandat</span>
												</a>
											 </li>
										 </c:if>
									</interaudit:accessRightSet>
										 
									<interaudit:accessRightSet right="SELECT_COLUMNS_FROM_BUDGET">
									 <li>
										<a  href="#" title="Select columns">
										<span id="show5" style="color: white;">Select columns</span>
										</a>
									 </li>
									  </interaudit:accessRightSet>
									  
									
									<interaudit:accessRightSet right="BUILD_BUDGET">
										<li>
											<a id="showBuildExerciceAcceptTerms" href="#" title="Build Next Budget"  >
											<span style="color: white;">Build Next Exercise</span>
											</a>
										 </li>
										<c:if test='${viewbudget.exercises[0].status != "CLOSED"}'> 
											 <li>
												<a  href="#" title="Apply inflation" onclick="applyInflation()">
												<span id="show3" style="color: white;">Apply inflation</span>
												</a>
											 </li>
										</c:if>
										<c:if test='${ (currentExercise != null) && (currentExercise.status eq "CLOSED") && ( (currentExercise.year+1) eq viewbudget.param.years[0]) && ( viewbudget.exercises[0].appproved == false) }'> 
											<li>
												<a  id="showApproveExerciceAcceptTerms" href="#" title="Approve exercise" >
												<span style="color: white;">Approve Exercise</span>
												</a>
											 </li>
									     </c:if>
									 
										   <c:if test='${ (currentExercise != null) && (currentExercise.status != "CLOSED") && (currentExercise.year eq viewbudget.param.years[0]) && (currentExercise.taggedDate == null)}'>
											 <li>
												<a  href="#" title="Bloquer budget"  onclick="javascript:referenceBudget(${currentExercise.id});">
												<span style="color: white;">Tag Exercise</span>
												</a>
											 </li>
										   </c:if>
									  
										   <c:if test='${ (viewExercise != null) && (viewExercise.deletable == true)}'>
											  <li>
												<a  href="#" title="Delete exercise"  onclick="javascript:deletePendingExercise(${viewExercise.id});">
												<span style="color: white;">Delete Exercise</span>
												</a>
											 </li>
											</c:if>
									 </interaudit:accessRightSet>
									 
									 <interaudit:accessRightSet right="CLOSE_EXERCISE_FROM_BUDGET">
										 <c:if test='${ (currentExercise != null) && (currentExercise.status != "CLOSED") && (currentExercise.year eq viewbudget.param.years[0])}'>
											 <li>
												<a id="showCloseExerciseDialog" href="#" title="Close exercise">
												<span style="color: white;">Close Exercise</span>
												</a>
											 </li>
										  </c:if>
									  </interaudit:accessRightSet>
									 					 
							</ul>
						</li>
					</ul>
				</div>
				<form style="margin:3pt" name="searchBudgetForm" action="${ctx}/showGeneralBudgetPage.htm" method="post" >

					<label  style="color:#039;"> Associe</label>:<span style="font:10px Verdana, sans-serif;margin-right:10pt;"> 
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="budget_associe" onchange="document.searchBudgetForm.submit();">
					  <option value="-1">Any</option>
					  <c:forEach var="y" items="${viewbudget.associeOptions}">
                		<option value="${y.id}"<c:if test='${viewbudget.param.associe==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
            		  <c:forEach var="y" items="${viewbudget.directorsOptions}">
                        <option value="${y.id}"<c:if test='${viewbudget.param.associe==y.id}'> selected</c:if>>${y.name}</option>
                      </c:forEach>
            		  
					</select> 
					</span>
						
					<label  style="color:#039;"> Manager</label>:<span style="font:10px Verdana, sans-serif;margin-right:10pt;"> 
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="budget_manager" onchange="document.searchBudgetForm.submit();">
					  <option value="-1">Any</option>
					  <c:forEach var="y" items="${viewbudget.managerOptions}">
                		<option value="${y.id}"<c:if test='${viewbudget.param.manager==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
            		  <c:forEach var="y" items="${viewbudget.directorsOptions}">
                        <option value="${y.id}"<c:if test='${viewbudget.param.associe==y.id}'> selected</c:if>>${y.name}</option>
                      </c:forEach>
					</select> 
					</span>	

					

					<label  style="color:#039;"> Origine</label>:<span style="font:10px Verdana, sans-serif;margin-right:10pt;">
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="budget_origin" onchange="document.searchBudgetForm.submit();">
					  <option value="-1">Any</option>
					  <c:forEach var="y" items="${viewbudget.originOptions}">
                		<option value="${y.id}"<c:if test='${viewbudget.param.origin==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
					</select> 
					</span>	
					
					<label  style="color:#039;"> Type</label>:<span style="font:10px Verdana, sans-serif;margin-right:10pt;" > 
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="budget_type" onchange="document.searchBudgetForm.submit();">
					 <option value="-1">Any</option>
					  <c:forEach var="y" items="${viewbudget.typeOptions}">
                		<option value="${y.id}"<c:if test='${viewbudget.param.type==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
					</select> 
					</span>	
					
					<label  style="color:#039;"> To close only:</label>
					<input type="checkbox" name="TO_CLOSE" id="TO_CLOSE" <c:if test='${viewbudget.param.toClose == true }'>checked</c:if> />
					
					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="submit" class="button120" value="Search"/></span>	
					
					<label  style="color:#039;"> Mode Edit:</label>
					<input type="checkbox" name="TO_EDIT" id="TO_EDIT" <c:if test='${viewbudget.param.toClose == true }'>checked</c:if> />
				    
				    
				
				</form>
				
			</div>

			

			<div id="menu"> 
				<ul> 
					<c:forEach var="allItem" items="${viewbudget.exercicesOptions}">
						<c:set var="present" value="false"/>
						<c:forEach var="yearItem" items="${viewbudget.param.years}" varStatus="loop">
									<c:if test='${yearItem == allItem.id}'> <c:set var="present" value="true"/></c:if>
								 </c:forEach>				
						<li>
						
						 
						
						<a  <c:if test='${present == "true"}'> class="current"</c:if> href="${ctx}/showGeneralBudgetPage.htm?year_${allItem.id}=${allItem.id}">
						
						
						<c:if test='${ allItem.needUpdate == true}'>
									
									<t:tooltip>  
	                                                               <t:text><em>
																	<img src="images/warning.gif" border="0"/>	                                                                                                                 
	                                                               </em>${allItem.name}</t:text>
	                                                              <t:help width="200">
																	<font color="red">Please update this budget</font><br/><hr/> 	                                                                                                                   
	                                                               </t:help> 
	                                 </t:tooltip>
									 
						</c:if>
						<c:if test='${ allItem.needUpdate == false}'>
							${allItem.name}
						</c:if>
						
							
						 </a>	
						
						
						</li>				
					</c:forEach>					
				</ul>
				
				
			</div>
			
	
			

			
	
			
						

<div  id="div_1" style="border-bottom: 1px solid gray; background-color: white;">
					
			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">			      
			        			<c:choose>
						    		<c:when test='${colNumero eq "true" }'>												
									 <c:set var="showcolNumero" value="display:table-cell;border-right: 1px solid #0066aa;background: white;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolNumero" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colClient eq "true" }'>												
									 <c:set var="showcolClient" value="display:table-cell;border-right: 1px solid #0066aa;background: white;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolClient" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colType eq "true" }'>												
									 <c:set var="showcolType" value="display:table-cell;border-right: 1px solid #0066aa;background: white;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolType" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colExercice eq "true" }'>												
									 <c:set var="showcolExercice" value="display:table-cell;border-right: 1px solid #0066aa;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolExercice" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colBudgetprevu eq "true" }'>												
									 <c:set var="showcolBudgetprevu" value="display:table-cell;border-right: 1px solid #0066aa;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolBudgetprevu" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colBudgetreporte eq "true" }'>												
									 <c:set var="showcolBudgetreporte" value="display:table-cell;border-right: 1px solid #0066aa;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolBudgetreporte" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colBudgettotal eq "true" }'>												
									 <c:set var="showcolBudgettotal" value="display:table-cell;border-right: 1px solid #0066aa;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolBudgettotal" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
		
						     	<c:choose>
						    		<c:when test='${colBudgetnonFiable eq "true" }'>												
									 <c:set var="showcolBudgetnonFiable" value=" background-color:#ffffcc;display:table-cell;background: white;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolBudgetnonFiable" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	
						     	<c:choose>
						    		<c:when test='${colFacturation eq "true" }'>												
									 <c:set var="showcolFacturation" value="display:table-cell;border-right: 1px solid #0066aa"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolFacturation" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colAssocie eq "true" }'>												
									 <c:set var="showcolAssocie" value="display:table-cell;border-right: 1px solid #0066aa;background: white;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolAssocie" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colManager eq "true" }'>												
									 <c:set var="showcolManager" value="display:table-cell;border-right: 1px solid #0066aa;background: white;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolManager" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colComment eq "true" }'>												
									 <c:set var="showcolComment" value="display:table-cell;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolComment" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colStatut eq "true" }'>												
									 <c:set var="showcolStatut" value="display:table-cell;border-right: 1px solid #0066aa;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolStatut" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	<c:choose>
						    		<c:when test='${colOrigine eq "true" }'>												
									 <c:set var="showcolOrigine" value="display:table-cell;border-right: 1px solid #0066aa;background: white;"/>
						      		</c:when>
						      		<c:otherwise>
									 <c:set var="showcolOrigine" value="display:none;"/>
						      		</c:otherwise>
						     	</c:choose>
						     	
						     	
									     	
			        			<caption>
								 [ 
								 
								
								 
								 <c:if test='${ viewbudget.exercises[0].status eq "CLOSED"}'> 
									<span style="color:grey;"><strong>${viewExercise.exercise} <c:out  value = "CLOTURE"/></strong></span>
								 </c:if>
								
								<c:if test='${viewbudget.exercises[0].status eq "ONGOING"}'> 
									<span style="color:green;"><strong>${viewExercise.exercise} <c:out  value = "ACTIF"/></strong></span>
								</c:if>
								
								<c:if test='${viewbudget.exercises[0].status eq "PENDING"}'> 
									<span style="color:red;"><strong>${viewExercise.exercise} <c:out  value = "PREVISIONNEL"/></strong></span>
								</c:if>
								
								]</caption>
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
									<%--th scope="col">&nbsp;</th--%>
									<th style=${showcolClient} scope="col"><span style="color: purple;">Client</span></th>
									<th style=${showcolExercice} scope="col"><span style="color: purple;">Exercice</span></th>
									<th style=${showcolExercice} scope="col"><span style="color: purple;">Mandat</span></th>
									<th style=${showcolNumero} scope="col"><span style="color: purple;">Numero</span></th>											
									<th style=${showcolType} scope="col"><span style="color: purple;">Type</span></th>
									<th style=${showcolOrigine} scope="col"><span style="color: purple;">Origine</span></th>
									<th style=${showcolAssocie} scope="col"><span style="color: purple;">Ass</span></th>
									<th style=${showcolManager} scope="col"><span style="color: purple;">Man</span></th>											
									<th style=${showcolBudgetprevu} scope="col"><span style="color: purple;">Prévu</span></th>
									<th style=${showcolBudgetreporte} scope="col"><span style="color: purple;">Reporté</span></th>
									<th style=${showcolBudgettotal} scope="col"><span style="color: purple;">Total</span></th>
									<th style=${showcolBudgetnonFiable} scope="col"><span style="color: purple;">Inactifs</span></th>
									<th style=${showcolFacturation} scope="col"><span style="color: purple;">Facturé</span></th>								
									<th style=${showcolComment} scope="col"><span style="color: purple;">Comment</span></th>
									<th style=${showcolStatut} scope="col"><span style="color: purple;">Statut</span></th>
									<th scope="col">Administrer</th>
									</tr>
									</thead>
									<tbody>
									<c:choose>
										<c:when test='${ fn:length(viewbudget.budgets) == 0}'>
											<tr><td align="center" colspan="14"><span  style="color:red;font-weight: bold;">No matching budgets found... </span></td></tr>                                                 
										</c:when>
										
										<c:otherwise>
											<%@ include file="/WEB-INF/jsp/views/budgets.jsp"%>		
										</c:otherwise>
									</c:choose>
									</tbody>
								</table>
								</div>
						

			<div  class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">			
				<ul class="pagination" align="center">
				<c:if test="${totalPage > 1}">
				    <c:set var="url" value="p=" />
				    <c:if test="${!empty  budget_status}">
				        <c:set var="url" value="budget_status=${budget_status}&${url}" />
				    </c:if>
				    <c:if test="${!empty budget_origin}">
				        <c:set var="url" value="budget_origin=${budget_origin}&${url}" />
				    </c:if>
				    <c:if test="${!empty budget_manager}">
				        <c:set var="url" value="budget_manager=${budget_manager}&${url}" />
				    </c:if>
				    <c:if test="${!empty budget_associe}">
				        <c:set var="url" value="budget_associe=${budget_associe}&${url}" />
				    </c:if>
				    <c:if test="${!empty startingLetterName}">
				        <c:set var="url" value="startingLetterName=${startingLetterName}&${url}" />
				    </c:if>
					<c:if test="${!empty budget_type}">
				        <c:set var="url" value="budget_type=${budget_type}&${url}" />
				    </c:if>
					
				     <%--c:if test="${TO_CLOSE != null}">
                        <c:set var="url" value="TO_CLOSE=${TO_CLOSE}&${url}" />
                    </c:if--%>
					
						<li><a style="background:#fff;" href="${ctx}/showGeneralBudgetPage.htm?${url}${1}"><img src="images/pg-first.gif" border="0"/></a></li>
				    
				        <c:choose>
				            <c:when test="${currentPage <= 1}">
								<li> <a style="background:#fff;" href="${ctx}/showGeneralBudgetPage.htm?${url}${currentPage }"><img src="images/pg-prev.gif" border="0"/></a></li>
				            </c:when>
				            <c:otherwise>
				                <li> <a style="background:#fff;" href="${ctx}/showGeneralBudgetPage.htm?${url}${currentPage - 1}"><img src="images/pg-prev.gif"  border="0"/></a></li>
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
									<li><a style='background-color: #7C9FCB;'><c:out value="${x}"/></a></li>									
				                </c:when>
				                <c:otherwise>
									<li><a href="${ctx}/showGeneralBudgetPage.htm?${url}${x}"><c:out value="${x}"/></a></li>
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
					
				        <c:choose>
				            <c:when test="${currentPage >= totalPage}">
				               <li><a  style="background:#fff;" href="${ctx}/showGeneralBudgetPage.htm?${url}${currentPage }">   <img src="images/pg-next.gif" border="0"/></a></li>
				            </c:when>
				            <c:otherwise>
				                <li>
								<a  style="background:#fff;" href="${ctx}/showGeneralBudgetPage.htm?${url}${currentPage + 1}"> <img src="images/pg-next.gif" border="0"/></a></li>
				            </c:otherwise>
				        </c:choose>
						<li><a  style="background:#fff;" href="${ctx}/showGeneralBudgetPage.htm?${url}${totalPage}"><img src="images/pg-last.gif" border="0"/></a></li>						
					</c:if>

					</ul>
					</div>
		

				
					
			

			

							<table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
			        			<caption>
								<span style="color:blue;"> Exercises :  </span> [
								<c:forEach var="yearItem" items="${viewbudget.param.years}" varStatus="loop">
									<span style="color:blue;"><c:out  value = "${yearItem}"/></span>
								 </c:forEach>
								]</caption>
									
									<thead>
									<tr class="odd">									
									<th scope="col"><span style="color: purple;">Exercice</span></th>
									<th scope="col"><span style="color: purple;">Date tag</span></th>
									<th scope="col"><span style="color: purple;">Budget prévu</span></th>
									<th scope="col"><span style="color: purple;">Budget reporté</span></th>
									<th scope="col"><span style="color: purple;">Budget total</span></th>
									<th scope="col"><span style="color: purple;">Budget inactif</span></th>
									<th scope="col"><span style="color: purple;">Facturation annuelle</span></th>
									<th scope="col"><span style="color: purple;">Approved</span></th>
									<th scope="col"><span style="color: purple;">Statut</span></th>
									
									</tr>
									</thead>									
									<tbody>
										<c:forEach var="y" items="${viewbudget.exercises}" varStatus="loop">
												<tr tr class="odd" class="${childClassNode}${indexNode}" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
													
													<td style="border-right: 1px solid #0066aa;"><span style="color: blue;"><i>${y.year}</i></span></td>
													<td style="border-right: 1px solid #0066aa;"><span style="color: RGB(219,78,36);">${y.taggedDate}</span></td>
													<td style="border-right: 1px solid #0066aa;">
													<span style="color: blue;">
														<fmt:formatNumber  type="currency" currencySymbol="&euro;">
															${y.totalExpectedAmount}
														</fmt:formatNumber>
													</span>|
													<span style="color:RGB(219,78,36);">
														<fmt:formatNumber  type="currency" currencySymbol="&euro;">
															${y.totalExpectedAmountRef}
														</fmt:formatNumber>
													</span>
													</td>

													<td style="border-right: 1px solid #0066aa;">
													<span style="color: blue;">
														<fmt:formatNumber  type="currency" currencySymbol="&euro;">
															${y.totalReportedAmount}
														</fmt:formatNumber>
													</span>|
													<span style="color:RGB(219,78,36);">
														<fmt:formatNumber  type="currency" currencySymbol="&euro;">
															${y.totalReportedAmountRef}
														</fmt:formatNumber>
													</span>
													</td>

													<td style="border-right: 1px solid #0066aa;">
													<span style="color: blue;"><strong>
														<fmt:formatNumber  type="currency" currencySymbol="&euro;">
															${y.totalExpectedAmount + y.totalReportedAmount}
														</fmt:formatNumber></strong>
													</span>|
													<span style="color:RGB(219,78,36);"><strong>
														<fmt:formatNumber  type="currency" currencySymbol="&euro;">
															${y.totalExpectedAmountRef + y.totalReportedAmountRef}
														</fmt:formatNumber></strong>
													</span>
													</td>
													
													<td style="border-right: 1px solid #0066aa;">
													<span style="color: red;"><i>
														<fmt:formatNumber  type="currency" currencySymbol="&euro;">
															${y.totalInactifAmount}
														</fmt:formatNumber></i>
													</span>|
													<span style="color: red;"><i>
														<fmt:formatNumber  type="currency" currencySymbol="&euro;">
															${y.totalInactifAmountRef}
														</fmt:formatNumber></i>
													</span>

													</td>

													<td style="border-right: 1px solid #0066aa;">
													<span style="color: green;">
													<i>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${y.totalEffectiveAmount}
														</fmt:formatNumber>
														</i>
													</span>
													</td>
													<td style="border-right: 1px solid #0066aa;"><span style=" color: blue;">
													
													<c:choose>
														<c:when test='${y.appproved eq true }'>
														 <img src="images/success.png" border="0"/>
														</c:when>
														<c:otherwise>
														 <img src="images/delete.gif" border="0"/>
														</c:otherwise>
													</c:choose>
													
													</span></td>

													<td style="border-right: 1px solid #0066aa;">
													
													<c:choose>
														<c:when test='${y.status eq "PENDING" }'>												
															<span style=" color: blue;">PREVISIONNEL</span>
														</c:when>
														<c:when test='${y.status eq "ONGOING" }'>												
															<span style=" color: green;">ACTIF</span>
														</c:when>
														<c:when test='${y.status eq "CLOSED" }'>												
															<span style=" color: grey;">CLOTURE</span>
														</c:when>
														<c:otherwise>
															<span style=" color: green;">${y.status}</span>
														</c:otherwise>
													</c:choose>
												
												</tr>

											</c:forEach>
										</tbody>
								</table>
								<br/>
						
	</div>
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
        <span id="formTitle" style="color:blue;">Select exercises</span>
    </div>
    <div class="bd">
	<form name="addExercisesForm" action="${ctx}/showGeneralBudgetPage.htm" method="post">
			<table id="ver-zebra2" width="100%"  class="formlist">
			 <c:set var="line" value="even"/>
			 
			<c:forEach var="allItem" items="${viewbudget.exercicesOptions}">
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
								<c:forEach var="yearItem" items="${viewbudget.param.years}" varStatus="loop">
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


<div id="addDialog2" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:blue;">Add budget</span>
    </div>

	<div class="bd">
	
	<form name="addBudgetForm" action="${ctx}/handleBudgetFormPage.htm" method="post" >
		<input type="hidden" name="budgetId" />
		<input type="hidden" name="page" value="${currentPage}" />
		<input type="hidden" name="startingLetterName" value="${viewbudget.param.customerStartString}" />
		
    	<fieldset > 
						<dl>
							<label for="year"><span style="color:#039;">Exercise </span></label>
							<input type="text" size="1" name="year" value="${viewbudget.exercises[0].year}" id="year" readonly/>
						&nbsp;
							<label for="currency"><span style="color:#039;">Currency </span></label>
							<input type="text" size="1" name="currency" id="currency"  value="EUR" readonly/>
						&nbsp;
							<label for="currency"><span style="color:#039;">Mandat </span></label>
							<select style="color:#039;" size="1" name="mandat" id="mandat">
								<option value="-1" selected>...</option>
								<option value="2003">2003</option>
								<option value="2004">2004</option>
								<option value="2005">2005</option>
								<option value="2006">2006</option>
								<option value="2007">2007</option>
								<option value="2008">2008</option>
								<option value="2009">2009</option>
								<option value="2010">2010</option>
								<option value="2011">2011</option>
								<option value="2012">2012</option>
								<option value="2013">2013</option>
								<option value="2014">2014</option>
								<option value="2015">2015</option>								
							</select>
						 </dl>
						 <dl>
							<label for="contract"><span style="color:#039;">Contrat Client </span><span style="color:red;"><i>(obligatoire)</i></span> </label>
							<div id="myAutoComplete">   
								<input style="width: 360px;" type="text" name="contract"  id="contract" size="32" maxlength="32"/>
								 <div id="myContainer"></div>  
							</div> 
							
						</dl>
						<dl>
						<label for="prevision"><span style="color:green;"><i> (optionels) </i></span> </label><br>
							<label for="associate"><span style="color:#039;">Associé</span></label>
							<select size="1" name="associate" id="associate">
								<option value="-1" selected>Choose one...</option>
								<c:forEach var="y" items="${viewbudget.associeOptions}">
									<option value="${y.id}">${y.name}</option>
								</c:forEach>
								<c:forEach var="y" items="${viewbudget.directorsOptions}">
                                    <option value="${y.id}">${y.name}</option>
                                </c:forEach>
							</select>
							&nbsp;
							<label for="manager"><span style="color:#039;">Manager </span></label>
							<select size="1" name="manager" id="manager">
								<option value="-1">Choose one...</option>
								<c:forEach var="y" items="${viewbudget.managerOptions}">
                					<option value="${y.id}">${y.name}</option>
            					</c:forEach>
            					<c:forEach var="y" items="${viewbudget.directorsOptions}">
                                    <option value="${y.id}">${y.name}</option>
                                </c:forEach>
							</select>
						</dl>
						 
    </fieldset>

    
    <fieldset>
    	
						 
						 
						<dl>
							<label for="prevision"><span style="color:#039;">Budget initial </span><span style="color:green;"><i>(optionel)</i></span> </label>
							<div>   
								<input style="width: 360px;" type="text" name="prevision"  id="prevision" value="" size="32" maxlength="32" />
							</div> 
						 </dl>
						 <dl>
							<label for="reported"><span style="color:#039;">Budget reporté</span> <span style="color:green;"><i>(optionel)</i></span></label>
							<div>   
								<input style="width: 360px;" type="text" name="reported"  id="reported" value="2000" size="32" maxlength="32" />
							<div>   
						 </dl>
						 <dl>
							<label for="comments"><span style="color:#039;">Commentaire </span><span style="color:green;"><i>(optionel)</i></span></label>
							<div>   
							<textarea name="comments" id="comments" rows="4" cols="55"></textarea>
							</div> 
						</dl>
						<dl>
							<label for="status"><span style="color:#039;">Status </span></label>
							<input border="0" type="text" size="15" name="status" id="status" readonly/>
							&nbsp;
							<label for="fiable"><span style="color:#039;">Fiable </span></label>
							<input type="checkbox" name="fiable" value="1" checked/>
							&nbsp;
                            <label for="fiable"><span style="color:#039;">Interim </span></label>
                            <input type="checkbox" name="interim" value="1" checked/>	
						</dl>
						 
    </fieldset>
   
</form>

</div>
</div>

<!--div id="addDialog11" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:#039;">Répartition par associés</span>
    </div>
	<div class="bd">
			<fieldset> 
				<dl>
					<img src="${ctx}/showObjectifPerAssocieBartChartPage.htm" border="1" WIDTH=800 HEIGHT=600/>
				 </dl>					 
			</fieldset>  
	</div>
</div>

<div id="addDialog12" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:#039;">Répartition par managers</span>
    </div>
	<div class="bd">
			<fieldset> 
				<dl>
					<img src="${ctx}/showObjectifPerManagerBartChartPage.htm" border="1" WIDTH=800 HEIGHT=600/>
				 </dl>					 
			</fieldset>  
	</div>
</div-->
	



<div id="addDialog3" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:#039;">Apply inflation to budget</span>
    </div>
	<div class="bd">
		<form name="applyInflationForm" action="${ctx}/applyInflationPage.htm" method="post" >
			<input type="hidden" name="exerciseId" value="${viewExercise.id}"/>
			<fieldset > 
							<dl>
								<label for="currency"><span style="color:#039;">Inflation(%) </span></label>
								<input type="text" size="1" name="inflation" id="inflation"  value="1"/>
							 </dl>					 
			</fieldset>  
		</form>
	</div>
</div>


<div id="addDialogColumns">
	<div class="hd">
        <span id="formTitle" style="color:blue;">Select columns</span>
    </div>
    <div class="bd">
	<form name="showColumnsForm" action="${ctx}/showDisplayColumnsPage.htm" method="post">
			<table id="ver-zebra2" width="100%"  class="formlist">
				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_numero" value="1" <c:if test='${colNumero eq "true" }'> checked</c:if>/>										
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Numero</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_client" value="1" <c:if test='${colClient eq "true" }'>checked</c:if>/>		 		
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Client</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_type" value="1" <c:if test='${colType eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Type</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_exercice" value="1" <c:if test='${colExercice eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Exercice</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_origine" value="1" <c:if test='${colOrigine eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Origine</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_budgetprevu" value="1" <c:if test='${colBudgetprevu eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Budget prevu</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_budgetreporte" value="1" <c:if test='${colBudgetreporte eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Budget reporte</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_budgettotal" value="1" <c:if test='${colBudgettotal eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Budget total</span>
						</td>
				</tr>
				
				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_budgetnonFiable" value="1" <c:if test='${colBudgetnonFiable eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Non fiable</span>
						</td>
				</tr>
				
				
				

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_facturation" value="1" <c:if test='${colFacturation eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Facturation</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_associe" value="1" <c:if test='${colAssocie eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Associe</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_manager" value="1" <c:if test='${colManager eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Manager</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_comment" value="1" <c:if test='${colComment eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Comment</span>
						</td>
				</tr>

				<tr class="odd">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="col_statut" value="1" <c:if test='${colStatut eq "true" }'>checked</c:if>/>							
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);">Statut</span>
						</td>
				</tr>
			</table>
	</form>
	</div>

	
</div>

<div id="acceptTermsDialog">
        <span id="termsFormTitle" style="color:blue;">Terms</span>
    
    <div class="bd">
		<form name="termsForm"  method="post">
			<div class="IAgree" title="Usage Terms">
			
					<div style="border: 1px solid blue">
						<p>
							<ul>
								<li>Les fonctionnalités suivantes permettent de gérer les exercices dans le système <b><i>I.A.M.S</i></b></li>
								<br/>
									<ul>
										<li><b><i>Génération d'un exercice </b></i> au statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> </li>
										<li><b><i>Approbation d'un exercice</b></i> du statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b>  au statut <b><i><span style="color:green;">ACTIF</span></i></b></li>
										<li><b><i>Marquage d'un exercice</b></i> pour référencer les données d'un exercice au statut <b><i><span style="color:green;">ACTIF</span></i></b> à une date spécifique</li>
										<li><b><i>Clôture d'un exercice</b></i> du statut <b><i><span style="color:green;">ACTIF</span></i></b>  au statut <b><i><span style="color:red;">TERMINE</span></i></b> </li>
										<li><b><i>Suppression d'un exercice</b></i> ayant le statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> </li>
									</ul>
									<br/>
								<li>Il n'y a qu'un seul exercice <b><i><span style="color:green;">ACTIF</span></i></b> à tout instant dans le système <b><i>I.A.M.S</i></b> .</li>	
								<li>Pour approuver un nouvel exercice et par conséquent le rendre  <b><i><span style="color:green;">ACTIF</span></i></b>, il faudra clôre l'exercice courant au préalable .</li>									
								<li>Seul l'exercice en  <b><i><span style="color:green;">ACTIF</span></i></b>  peut recevoir des factures, des payments et des timesheets. </li>								
								<li>Plusieurs exercices à l'état <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> peuvent subsister à tout instant .</li>	
								
							</ul>							
						</p>
					</div>
					<br/>
					<div style="border: 1px solid blue">
						<p>
							<ul>
								<li>Les lignes budgétaires vérifiants les conditions suivantes ne seront pas reportées dans le nouvel exercice:
									<ul>
										<li><b><i>Statut Annulé</b></i></li>
										<li><b><i>Statut Terminé</b></i></li>
										<li><b><i>Ayant une facture finale</b></i> </li>
									</ul>
								</li>
									<br/>
								<li>Les autres lignes budgétaires seront reportées en calculant le montant du report de la maniére suivante:</li>
									<ul>
										<li> <b><i>montant reporté</i></b> = <b><i>budget prevu</i></b> - <b><i>total facturé</i></b>.</li>
									</ul>									
								<li>Si le <b><i>budget prevu</b></i> est inférieur au <b><i>total facturé</b></i> alors le <b><i>montant reporté</b></i> est établi à zéro. </li>								
								<li>Une nouvelle ligne budgétaire est ajoutée au nouvel exercice pour chaque contrat valide. le <b><i>budget prevu</i></b> étant celui défini dans le contrat. </li>
								<li>Le statut d'une nouvelle ligne budgétaire est établi à <b><i><span style="color:blue;">REPORT</span></b></i> dans le nouvel exercice. </li>
							</ul>							
						</p>
					</div>
			</div>
		</form>
	</div>
</div>


<div id="closeExerciseDialog">
        <span id="closeExerciseDialogTitle" style="color:blue;">Terms</span>
    
    <div class="bd">
		<form name="closeExerciseDialogForm"  method="post">
			<div class="IAgree" title="Usage Terms">
			
					<div style="border: 1px solid blue">
						<p>
							<ul>
								<li>Les fonctionnalités suivantes permettent de gérer les exercices dans le système <b><i>I.A.M.S</i></b></li>
								<br/>
									<ul>
										<li><b><i>Génération d'un exercice </b></i> au statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> </li>
										<li><b><i>Approbation d'un exercice</b></i> du statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b>  au statut <b><i><span style="color:green;">ACTIF</span></i></b></li>
										<li><b><i>Marquage d'un exercice</b></i> pour référencer les données d'un exercice au statut <b><i><span style="color:green;">ACTIF</span></i></b> à une date spécifique</li>
										<li><b><i>Clôture d'un exercice</b></i> du statut <b><i><span style="color:green;">ACTIF</span></i></b>  au statut <b><i><span style="color:red;">TERMINE</span></i></b> </li>
										<li><b><i>Suppression d'un exercice</b></i> ayant le statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> </li>
									</ul>
									<br/>
								<li>Il n'y a qu'un seul exercice <b><i><span style="color:green;">ACTIF</span></i></b> à tout instant dans le système <b><i>I.A.M.S</i></b> .</li>	
								<li>Pour approuver un nouvel exercice et par conséquent le rendre  <b><i><span style="color:green;">ACTIF</span></i></b>, il faudra clôre l'exercice courant au préalable .</li>									
								<li>Seul l'exercice en  <b><i><span style="color:green;">ACTIF</span></i></b>  peut recevoir des factures, des payments et des timesheets. </li>								
								<li>Plusieurs exercices à l'état <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> peuvent subsister à tout instant .</li>	
								
							</ul>							
						</p>
					</div>
					<br/>
					<div style="border: 1px solid blue">
						<p>
							<ul>
								<li>Pour clôturer un exercice, les conditions suivantes doivent etre vérifiées:
									<ul>
										<li><b><span style="color:red;">L'exercice à clôturer doit être au statut <b><i><span style="color:green;">ACTIF</span></i></b>. </span> </b></li>
										<li><b><span style="color:red;">Un exercice au statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> doit exister pour l'année suivante.</span> </b></li>
										<!--li><b><span style="color:red;">Toutes les missions ayant une facture finale doivent être fermées. </span> </b></li-->
																				
									</ul>
								</li>
									
							</ul>							
						</p>
					</div>
			</div>
		</form>
	</div>
</div>


<div id="approveExerciseDialog">
        <span id="approveExerciseDialogTitle" style="color:blue;">Terms</span>
    
    <div class="bd">
		<form name="approveExerciseDialogForm"  method="post">
			<div class="IAgree" title="Usage Terms">
			
					<div style="border: 1px solid blue">
						<p>
							<ul>
								<li>Les fonctionnalités suivantes permettent de gérer les exercices dans le système <b><i>I.A.M.S</i></b></li>
								<br/>
									<ul>
										<li><b><i>Génération d'un exercice </b></i> au statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> </li>
										<li><b><i>Approbation d'un exercice</b></i> du statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b>  au statut <b><i><span style="color:green;">ACTIF</span></i></b></li>
										<li><b><i>Marquage d'un exercice</b></i> pour référencer les données d'un exercice au statut <b><i><span style="color:green;">ACTIF</span></i></b> à une date spécifique</li>
										<li><b><i>Clôture d'un exercice</b></i> du statut <b><i><span style="color:green;">ACTIF</span></i></b>  au statut <b><i><span style="color:red;">TERMINE</span></i></b> </li>
										<li><b><i>Suppression d'un exercice</b></i> ayant le statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> </li>
									</ul>
									<br/>
								<li>Il n'y a qu'un seul exercice <b><i><span style="color:green;">ACTIF</span></i></b> à tout instant dans le système <b><i>I.A.M.S</i></b> .</li>	
								<li>Pour approuver un nouvel exercice et par conséquent le rendre  <b><i><span style="color:green;">ACTIF</span></i></b>, il faudra clôre l'exercice courant au préalable .</li>									
								<li>Seul l'exercice en  <b><i><span style="color:green;">ACTIF</span></i></b>  peut recevoir des factures, des payments et des timesheets. </li>								
								<li>Plusieurs exercices à l'état <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> peuvent subsister à tout instant .</li>	
								
							</ul>							
						</p>
					</div>
					<br/>
					<div style="border: 1px solid blue">
						<p>
							<ul>
								<li>Pour approuver un exercice, les conditions suivantes doivent etre vérifiées:
									<ul>
									    <li><b><span style="color:red;">L' exercice  <b><i><span style="color:green;">ACTIF</span></i></b> doit être clôturer. </span> </b></li>
										<li><b><span style="color:red;">L'exercice à approuver doit être au statut <b><i><span style="color:green;">PREVISIONNEL</span></i></b>. </span> </b></li>
										<li><b><span style="color:red;">Toutes les missions ayant une facture finale doivent être fermées dans l'exercice courant. </span> </b></li>
										<li><b><span style="color:red;">Une fois approuvé, l'exercice passera au statut <b><i><span style="color:green;">ACTIF</span></i></b>. </span> </b></li>										
									</ul>
								</li>
									
							</ul>							
						</p>
					</div>
			</div>
		</form>
	</div>
</div>


	

