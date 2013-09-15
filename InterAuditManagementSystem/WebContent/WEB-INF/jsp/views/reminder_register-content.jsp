<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="container">
<form action="${ctx}/showReminderRegisterPage.htm" method="post" class="niceform">
<fieldset>
    	<legend>Invoice reminder</legend>
        
		 <dl>
        	<dt><label for="password">Customer ref:</label></dt>
            <dd>
			<spring:bind path="reminder.code" >
					<input type="text" name="${status.expression}" value="${status.value}"><img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		 </dl>

		 <dl>
			<dt><label for="password">Invoice ref:</label></dt>
            <dd>
				<spring:bind path="customer.code" >
					<input type="text" name="${status.expression}" value="${status.value}"><img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		</dl>

		 <dl>
        	<dt><label for="password">Message</label></dt>
            <dd>
			<spring:bind path="customer.code" >
					<textarea name="${status.expression}" id="${status.value}" rows="5" cols="60">${status.value}</textarea>
					<img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		 </dl>

		 <dl>
			<dt><label for="password">Sent date:</label></dt>
            <dd><input type="input" name="lastName" id="lastName" value="20/01/2009" size="32" maxlength="32" /></dd>
		</dl>

    </fieldset>

    
    <fieldset>
    	<legend>Administration</legend>
		<dl>
			<dt><label for="password">Created by:</label></dt>
            <dd>
				<spring:bind path="customer.code" >
					<input type="text" name="${status.expression}" value="${status.value}"><img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		</dl>

        <dl>
        	<dt><label for="email">Is sent:</label></dt>
            <dd><input type="checkbox" name="interests[]" id="interestsNews" value="News" checked/></dd>
        </dl>
    </fieldset>
    <fieldset class="action">
    	<input type="submit" name="submit" id="submit" class="button120"  value="Submit" />
    </fieldset>
</form>

</div>