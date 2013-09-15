<jsp:directive.page contentType="text/html;charset=UTF-8" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
	<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
	<tiles:put name="content" value="list-balanceagee-content.jsp"/>
</tiles:insert>


<script>
YAHOO.namespace("example.container");

   //Define various event handlers for Dialog	
	var handleCancel = function() {
		this.cancel();
	};

	// Instantiate the addDialogColumns
	YAHOO.example.container.dialog4 = new YAHOO.widget.Dialog("showInvoiceHistoryDialog",
																{ width : "45em",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : []																	      	  
																}
							);

var handleSuccess = function(o){

	var myColumnDefs = [
            
            {key:"action"},
			{key:"date"},
            {key:"user"}
        ];
	
   // var form = document.forms["addBudgetForm"];
    if(o.responseText !== undefined){
        // Use the JSON Utility to parse the data returned from the server   
         try {   
			 
            //var myJSONArray = YAHOO.lang.JSON.parse(o.responseText);
            var myJSONObject = YAHOO.lang.JSON.parse(o.responseText);

			var myDataSource = new YAHOO.util.DataSource(myJSONObject.history);
			myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
			myDataSource.responseSchema = {
				fields: ["action","date","user"]
			};

			var myDataTable = new YAHOO.widget.DataTable("containerTableHistory", myColumnDefs, myDataSource);
    		
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML =  'Historique facture [ ' + myJSONObject.invoiceId + ' ]' ;
			
			
         }
         catch (x) {   
             alert("JSON Parse failed !");
             return;
         } 

		

        YAHOO.example.container.dialog4.render();
        YAHOO.example.container.dialog4.show();	
		
	}
};

var handleFailure = function(o){
    if(o.responseText !== undefined){
        div.innerHTML = "<li>Transaction id: " + o.tId + "<\/li>";
        div.innerHTML += "<li>HTTP status: " + o.status + "<\/li>";
        div.innerHTML += "<li>Status code message: " + o.statusText + "<\/li>";
    }
};

var callback =
{
  success:handleSuccess,
  failure:handleFailure
};

var sEditInvoiceHistoryItemUrl = "${ctx}/editInvoiceHistoryItemPage.htm";

function editInvoiceHistoryItem(invoiceId,invoiceReference){
    var postData = "invoiceId="+invoiceId+"&invoiceRef="+invoiceReference;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditInvoiceHistoryItemUrl, callback, postData);
}
</script>
</html>