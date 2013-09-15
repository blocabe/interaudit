<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="container">
<form action="${ctx}/showExpenseRegisterPage.htm" method="post" class="niceform">
<fieldset>
    	<legend>Expense details</legend>
        <dl>
        	<dt><label for="password">Reason:</label></dt>
            <dd><textarea name="comments" id="comments" rows="3" cols="60">Hotel fees for mission in Brussels...</textarea></dd>
		 </dl>

		 <dl>
        	<dt><label for="password">mission ref:</label></dt>
            <dd><input type="text" name="lastName"  value="16000_1998" id="lastName" size="32" maxlength="32" /></dd>
		 </dl>

		 <dl>
			<dt><label for="password">Paid date:</label></dt>
            <dd><input type="input" name="lastName" id="lastName" value="20/01/2009" size="32" maxlength="32" /></dd>
		</dl>

		<dl>
			<dt><label for="password">Amount:</label></dt>
            <dd><input type="text" name="lastName"  value="5 000" id="lastName" size="32" maxlength="32" /></dd>
		</dl>

		<dl>
			<dt><label for="gender">Currency:</label></dt>
            <dd><select size="1" name="sex" id="sex">
                    <option value="Male">Euro</option>
                    <option value="Female">Dollar</option>
                    <option value="Don't Ask">Livre Sterling</option>
            	</select>
			</dd>
		</dl>
    </fieldset>

    
    <fieldset>
    	<legend>Administration</legend>
		<dl>
			<dt><label for="password">Owner:</label></dt>
            <dd><input type="text" name="lastName" value="VB" id="lastName" size="32" maxlength="32" /></dd>
		</dl>

        <dl>
        	<dt><label for="email">Reimbursed:</label></dt>
            <dd><input type="checkbox" name="interests[]" id="interestsNews" value="News" checked/><label for="interestsNews" class="opt"></label></dd>
        </dl>
    </fieldset>
    <fieldset class="action">
    	<input type="submit" name="submit" id="submit" class="button120"  value="Submit" />
    </fieldset>
</form>

</div>