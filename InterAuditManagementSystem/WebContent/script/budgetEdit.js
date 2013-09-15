/**
 * <p>Title: manage stage.js</p>
 * <p>Description:  Manage the jquery javascript events on the budget table,
 * 	 and handle the ajax request to the server in order to be able to add,remove,payment plan.
 * </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Intrasoft International</p>
 * @author bblocail
 * 
 * <pre>
 * Changes
 * =======
 * Date Name Issue Id Description
 * 27/09/11	bblocail	creation
 * </pre>
 */
 

$( function () {
	
	
	$("#TO_EDIT").bind("click", function () {
		
		showHideBudgetInputs();
	});
	
	/*
	initialise stage display: if other is ticked,
	the user can insert a list of price one by one.
	*/
	function showHideBudgetInputs() {
	    if($("#TO_EDIT").attr('checked')) {
			$(".TO_EDIT").show();
			$(".TO_READ").hide();
		} else {
			$(".TO_EDIT").hide();
			$(".TO_READ").show();
			 updateBudgetByQuery();
		}
	
	}
				
			
	showHideBudgetInputs();
	
	
	/*
	 * generate Payment Schedule for the 
	 */
	function updateBudgetByQuery() {
		
		//we make the ajax request to create the line on the server
		$.ajax({
			url: "updateBudgetByQuery.htm",
		    error: function(error){
		      showMessage("Error while updating the budget..","error");  ;
		    },
		    success: function(data){
				showMessage("Successfully updated the budget...","success"); 
		    },
		    complete: function(){
		      // ajax loader end.
		      $("#addPaymentPlan").attr('disabled',false);
		    }
		 });			 
	}	

 
	
	
	
}); 