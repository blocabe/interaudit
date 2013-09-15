<div id="container">
<form  action="${ctx}/showDocumentRegisterPage.htm" method="post" class="niceform">
<fieldset>
    	<legend>Document Info</legend>
        <dl>
        	<dt><label for="password">Title:</label></dt>
            <dd><input type="input" name="lastName" id="lastName" value="Contract 2008 DIMPEX SA" size="32" maxlength="32" /></dd>
		 </dl>

		<dl>
			<dt><label for="password">Description:</label></dt>
            <dd><textarea name="comments" id="comments" rows="4" cols="60">Details of contract contents...</textarea></dd>
		</dl>

		<dl>
			<dt><label for="upload">Upload a File:</label></dt>
            <dd><input type="file" name="upload" id="upload" /></dd>
		</dl>
    </fieldset>

    
    <fieldset>
    	<legend>Administration</legend>
		
		<dl>
				<dt><label for="gender">Owner:</label></dt>
				<dd>
				<select size="1" name="sex" id="sex">
                    <option value="Male">V.Blocail</option>
                    <option value="Female">B.Blocail</option>
                    <option value="Don't Ask">M.Blocail</option>
            	</select>
				</dd>
				
		 </dl>

		 <dl>
				<dt><label for="email">Visible all:</label></dt>
				<dd>
				<input type="checkbox" name="interests[]" id="interestsNews" value="News" checked/><label for="interestsNews"></label>
				</dd>
		 </dl>

		<dl>
			<dt><label for="gender">Entity type:</label></dt>
            <dd>
				<select size="1" name="sex" id="sex">
                    <option value="Male">Mission</option>
                    <option value="Female">Customer</option>
                    <option value="Don't Ask">Employee</option>
            	</select>
            </dd>
			&nbsp;
			<label for="gender">Category:</label>
			<select size="1" name="sex" id="sex">
                    <option value="Male">Contract</option>
                    <option value="Female">Invoice</option>
                    <option value="Don't Ask">Other</option>
            	</select>
		</dl>

		<dl>
			<dt><label for="gender">Entity ref:</label></dt>
			<dd><input type="input" name="lastName" id="lastName" value="16000_1998" size="32" maxlength="32" /></dd>
		</dl>
    </fieldset>
    <fieldset class="action">
    	<input type="submit" name="submit" id="submit" class="button120"  value="Submit" />
    </fieldset>
</form>

</div>