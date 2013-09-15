	 
	
	



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

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercise</strong> </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" >
				  <option>2009</option>
				  <option selected><span >2008 </span></option>
				  <option>2007</option>
				  <option>2006</option>
				  <option>2005</option>
				  <option>2004</option>
				  <option>2003</option>
				  <option>2002</option>
				  <option>2001</option>
				</select> 
				
				<input style="font:12px Verdana, sans-serif;" type="button" class="button120" value="Search" onclick="Javascript:searchFilter();"/>		

				</form>
			</div>


 
	


 
				<div>
									<br/>
									<div id="navcontainer" >
										<ul id="navlist">
											<!-- CSS Tabs -->
											<li><a id="current" href="Home.html">1er trimestre</a></li>
											<li><a href="Products.html">2eme trimestre</a></li>
											<li><a href="Services.html">3eme trimestre</a></li>
											<li><a href="Support.html">4eme trimestre</a></li>
			   
			   
										</ul>
								</div>
			    
			        <table id="newspaper-a" width="100%" cellspacing="0" >
									<!--caption><span style="color:orange;">Manager Performance over 2008 exercise</span></caption-->
									<thead>
									<tr>
										<th scope="col"></th>
										<th scope="col">Janvier</th>
										<th scope="col">Fevrier</th>
										<th scope="col">Mars</th>
										<th scope="col">Total/Manager</th>
									</tr>
									</thead>
									<tbody>
									
							
									<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th>Manager 1</th>
									<td>10 000€</td>
									<td>12 000€</td>
									<td>9 000€</td>
									<td>10 000€</td>
									</tr>
									
									
									<tr style="background: #dddddd;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th>Manager 2</th>
									<td>10 000€</td>
									<td>12 000€</td>
									<td>9 000€</td>
									<td>10 000€</td>
									</tr>
									
									<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th>Manager 3</th>
									<td>10 000€</td>
									<td>12 000€</td>
									<td>9 000€</td>
									<td>10 000€</td>
									</tr>
									
									
									<tr style="background: #dddddd;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th>Manager 4</th>
									<td>10 000€</td>
									<td>12 000€</td>
									<td>9 000€</td>
									<td>10 000€</td>
									</tr>
									
									
									</tbody>
								</table>
								<br/>
						</div>
	
  

 