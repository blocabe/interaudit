<div id="container">
<form action="${ctx}/showBudgetRegisterPage.htm" method="post" class="niceform">
<fieldset>
    	<legend>Budget</legend>

		<dl>
			<dt><label for="password">Exercise:</label></dt>
            <dd><select size="1" name="dobYear" id="dobYear">
                	<option value="2000" select>2000</option>
                    <option value="1999">1999</option>
                    <option value="1998">1998</option>
                    <option value="1997">1997</option>
                    <option value="1996">1996</option>
                    <option value="1995">1995</option>
					<option value="1994">1994</option>
					<option value="1993">1993</option>
					<option value="1992">1992</option>
					<option value="1991">1991</option>
					<option value="1990">1990</option>
					<option value="1989">1989</option>
					<option value="1988">1988</option>
					<option value="1987">1987</option>
					<option value="1986">1986</option>
					<option value="1985">1985</option>
					<option value="1984">1984</option>
					<option value="1983">1983</option>
					<option value="1982">1982</option>
					<option value="1981">1981</option>
					<option value="1980">1980</option>
					<option value="1979">1979</option>
					<option value="1978">1978</option>
					<option value="1977">1977</option>
					<option value="1976">1976</option>
					<option value="1975">1975</option>
					<option value="1974">1974</option>
					<option value="1973">1973</option>
					<option value="1972">1972</option>
					<option value="1971">1971</option>
					<option value="1970">1970</option>
					<option value="1969">1969</option>
					<option value="1968">1968</option>
					<option value="1967">1967</option>
					<option value="1966">1966</option>
					<option value="1965">1965</option>
					<option value="1964">1964</option>
					<option value="1963">1963</option>
					<option value="1962">1962</option>
					<option value="1961">1961</option>
					<option value="1960">1960</option>
					<option value="1959">1959</option>
					<option value="1958">1958</option>
					<option value="1957">1957</option>
					<option value="1956">1956</option>
					<option value="1955">1955</option>
					<option value="1954">1954</option>
					<option value="1953">1953</option>
					<option value="1952">1952</option>
					<option value="1951">1951</option>
					<option value="1950">1950</option>
                </select></dd>
				&nbsp;
				<label for="gender">Currency:</label>
				<select size="1" name="sex" id="sex">
                    <option value="Male">Euro</option>
                    <option value="Female">Dollar</option>
                    <option value="Don't Ask">Livre Sterling</option>
            	</select>
		</dl>

		

		<dl>
			<dt><label for="password">Previson:</label></dt>
            <dd><input type="text" name="lastName"  id="lastName" value="20 000" size="32" maxlength="32" /></dd>
		</dl>

		<dl>
			<dt><label for="password">Report:</label></dt>
            <dd><input type="text" name="lastName"  id="lastName" value="2000" size="32" maxlength="32" /></dd>
		</dl>

		<dl>
			<dt><label for="password">Total:</label></dt>
            <dd><p>22 000</p>
		</dl>

		<dl>
			<dt><label for="password">Comment:</label></dt>
            <dd><textarea name="comments" id="comments" rows="3" cols="60">Mauvais payeur</textarea>
		</dl>

		
    </fieldset>

    
    <fieldset>
    	<legend>Administration</legend>
		<dl>
			<dt><label for="password">Customer ref:</label></dt>
            <dd><input type="text" name="lastName" value="LUXLAIT" id="lastName" size="32" maxlength="32" /></dd>
		</dl>

		<dl>
			<dt><label for="password">Contract ref:</label></dt>
            <dd><input type="text" name="lastName"  value="1600" id="lastName" size="32" maxlength="32" /></dd>
		</dl>

        <dl>
        	<dt><label for="email">Status:</label></dt>
            <dd>
					<select size="1" name="sex" id="sex">
						<option value="-1">Any</option>
						<option value="PENDING" selected="selected">Pending</option>
						<option value="ONGOING">On going</option>
						<option value="CLOSED" >Closed</option>
						<option value="CANCELLED">Cancelled</option>
						<option value="CANCELLED">Idle</option>
					  </select>
			</dd>
        </dl>

		 <dl>
			<dt><label for="gender">Associate :</label></dt>
            <dd>
            	<select size="1" name="sex" id="sex">
					<option value="Luxembourg">EK</option>
                    <option value="France">VD</option>
            	</select>
            </dd>
			&nbsp;
			<label for="gender">Manager :</label>
			<select size="1" name="sex" id="sex">
                    <option value="Luxembourg">LM</option>
                    <option value="France">VB</option>
            </select>
		</dl>

    </fieldset>
    <fieldset class="action">
    	<input type="submit" name="submit" id="submit" class="button120"  value="Submit" />
    </fieldset>
</form>

</div>