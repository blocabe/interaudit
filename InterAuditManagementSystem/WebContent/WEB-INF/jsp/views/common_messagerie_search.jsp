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
				<form style="text-align:center; name="form1" action="${ctx}/showBudgetResultsPage.htm" method="post" >

					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input id="show" style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="New message" /></span>	

					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercice</strong> 
						<select style="font:10px Verdana, sans-serif;margin-right:10pt;"  name="year">
							<c:forEach var="y" items="${message_recus.years}">
							<option value="${y}" <c:if test='${message_recus.exercise==y}'> selected</c:if> >${y}</option>
							</c:forEach>
						</select> 
					</span>
						
					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="submit" class="button120" value="Search" /></span>	
				</form>
		</div>
<br/>