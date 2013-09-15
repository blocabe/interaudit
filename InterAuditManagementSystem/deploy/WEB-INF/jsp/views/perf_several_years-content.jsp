
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


.oce-first
{
	background: #d0dafd;
	border-right: 10px solid transparent;
	border-left: 10px solid transparent;
}
#one-column-emphasis tr:hover td
{
	color: #339;
	background: #eff2ff;
}

#newspaper-a
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	margin-top: 15px;	
	text-align: left;
	border-collapse: collapse;
	border: 1px solid #69c;
}
#newspaper-a th
{
	/*padding: 12px 17px 12px 17px;*/
	font-weight: normal;
	font-size: 14px;
	color: #039;
	border-bottom: 1px dashed #69c;
}
#newspaper-a td
{
	/*padding: 7px 17px 7px 17px;*/
	color: #669;
}
#newspaper-a tbody tr:hover td
{
	color: #339;
	background: #d0dafd;
}

 .normal { background-color: white }
		 .highlight { background-color: orange }
		 
/* CSS Tabs */
#navlist {
	padding: 3px 0;
	margin-left: 0;
	margin-bottom: 0;
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
    


 <div class="nav_alphabet" style="border-bottom: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
			<form name="listForm" action="requestlist.htm" method="post" >

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Type</strong> </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" >
				  <option>Orgine</option>
				  <option selected><span >Manager </span></option>
				</select> 

				
				
				<input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Search" onclick="Javascript:searchFilter();"/>		

				</form>
			</div>

<div>

					<br/>
									<div id="navcontainer">
										<ul id="navlist">
										<li><a id="current" href="Home.html">Resultats</a></li>
										<li><a href="Products.html">Graphiques</a></li>
										</ul>
									</div>
			    
			        <table id="newspaper-a" width="100%" cellspacing="0" >
									<caption><span style="color:orange;">Manager Performance over several exercises</span></caption>
									<thead>
									<tr>
									<th scope="col"></th>
									<th scope="col">2009</th>
									<th scope="col">2008</th>
									<th scope="col">2007</th>
									<th scope="col">2006</th>
									<th scope="col">2005</th>
									<th scope="col">2004</th>
									</tr>
									</thead>
									<tbody>
									
							
									<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th>Manager 1</th>
									<td>10 000€</td>
									<td>12 000€</td>
									<td>9 000€</td>
									<td>10 000€</td>
									<td>17 000€</td>
									<td>10 500€</td>
									</tr>
									
									
									<tr style="background: #dddddd;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th>Manager 2</th>
									<td>10 000€</td>
									<td>12 000€</td>
									<td>9 000€</td>
									<td>10 000€</td>
									<td>17 000€</td>
									<td>10 500€</td>
									</tr>
									
									<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th>Manager 3</th>
									<td>10 000€</td>
									<td>12 000€</td>
									<td>9 000€</td>
									<td>10 000€</td>
									<td>17 000€</td>
									<td>10 500€</td>
									</tr>
									
									
									<tr style="background: #dddddd;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th>Manager 4</th>
									<td>10 000€</td>
									<td>12 000€</td>
									<td>9 000€</td>
									<td>10 000€</td>
									<td>17 000€</td>
									<td>10 500€</td>
									</tr>
									
									</tbody>
								</table>
						</div>
	

  
  <!-- END ADDED PART -->   