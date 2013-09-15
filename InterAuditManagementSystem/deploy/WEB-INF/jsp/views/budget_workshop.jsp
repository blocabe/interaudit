﻿<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<!--link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css" /-->


<!-- Script par KevBrok :) -->
<script type="text/javascript">
function is_numeric(num)
	{
		var exp = new RegExp("^[0-9-.]*$","g");
		return exp.test(num);
	}

function is_numeric_positif(num)
	{
		return age.matches("^\\s*\\d+\\s*$");
	}

</script>
<!--begin custom header content for this example-->
<style type="text/css">
#myAutoComplete {
    width:30em; /* set width here or else widget will expand to fit its container */
    padding-bottom:2em;
	z-index:9000;
}
</style>

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
	<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
	<tiles:put name="content" value="budget-workshop-content.jsp"/>
</tiles:insert>

<div id="content"></div>

<script type="text/javascript">
    YAHOO.namespace("example.container");
	/*
	if (YAHOO.example.container.wait){
		 alert("return from the server");
		 YAHOO.example.container.wait.hide();
		  YAHOO.example.container.wait = null;  
	}
*/
    function buildExercise() {

		var answer = confirm("Do you really want to build the next exercise?");
        if (answer){

       var content = document.getElementById("content");
       content.innerHTML = "";

        if (!YAHOO.example.container.wait) {
            YAHOO.example.container.wait = 
                    new YAHOO.widget.Panel("wait",  
                                                    { width: "300px", 
                                                      fixedcenter: true, 
                                                      close: false, 
                                                      draggable: false, 
                                                      zindex:4,
                                                      modal: true,
                                                      visible: false
                                                    } 
                                                );
            YAHOO.example.container.wait.setHeader("Processing the build, please wait...");
            YAHOO.example.container.wait.setBody("<img src=\"images/rel_interstitial_loading.gif\"/>");
            YAHOO.example.container.wait.render(document.body);
        }

        var callback3 = {
            success : function(o) {
                 var responseText = o.responseText;
                if(responseText == 'OK'){
                    alert("The exercise has been sucessfully built...The page will be refreshed soon...");
                    window.location = "${ctx}/showGeneralBudgetPageFromMenu.htm?forceReload=true";
                    YAHOO.example.container.wait.hide();             
                }
                else{
                    YAHOO.example.container.wait.setHeader(responseText);
                }   
            	   
               
            },
            failure : function(o) {
				alert("Failure to build the exercise...Contact your system administrator...");
                content.innerHTML = o.responseText;
                content.style.visibility = "visible";
                content.innerHTML = "CONNECTION FAILED!";
                YAHOO.example.container.wait.hide();
            }
        }
    
        // Show the Panel
        YAHOO.example.container.wait.show();
        var conn = YAHOO.util.Connect.asyncRequest("GET", "${ctx}/buildNextExercisePage.htm", callback3);

		}
       //var url =  "${ctx}/buildNextExercisePage.htm";	
		//document.location=url;
		}
    
    
    function updateBudget(id){   
		var answer = confirm("Do you really want to recalcule exercise [ " + ${viewExercise.exercise}+" ] ?");
        if (answer){
	
				var content = document.getElementById("content");
				content.innerHTML = "";

				 if (!YAHOO.example.container.wait) {
					 YAHOO.example.container.wait = 
							 new YAHOO.widget.Panel("wait",  
															 { width: "300px", 
															   fixedcenter: true, 
															   close: false, 
															   draggable: false, 
															   zindex:4,
															   modal: true,
															   visible: false
															 } 
														 );
					 YAHOO.example.container.wait.setHeader("Updating budget [ "+ ${viewExercise.exercise}+" ] , please wait...");
					 YAHOO.example.container.wait.setBody("<img src=\"images/rel_interstitial_loading.gif\"/>");
					 YAHOO.example.container.wait.render(document.body);
				 }

				 var callback78 = {
					 success : function(o) {
						 alert("Budget [ "+ ${viewExercise.exercise}+" ] sucessfully updated...The page will be refreshed soon...");
						 window.location = "${ctx}/showGeneralBudgetPageFromMenu.htm?forceReload=true";
						 YAHOO.example.container.wait.hide();
						
					 },
					 failure : function(o) {
						 alert("Failed to update the budget !!!...Contact your system administrator...");
						 content.innerHTML = o.responseText;
						 content.style.visibility = "visible";
						 content.innerHTML = "CONNECTION FAILED!";
						 YAHOO.example.container.wait.hide();
					 }
				 }
				 
				  YAHOO.example.container.wait.show();

			  // Connect to our data source and load the data
				 var conn = YAHOO.util.Connect.asyncRequest("GET", "${ctx}/updateExerciceAndBudget.htm?id="+ id, callback78);
			}
    }
    
    
    function searchFromExpressionFilter(){      
        var content = document.getElementById("content");
        content.innerHTML = "";

         if (!YAHOO.example.container.wait) {
             YAHOO.example.container.wait = 
                     new YAHOO.widget.Panel("wait",  
                                                     { width: "300px", 
                                                       fixedcenter: true, 
                                                       close: false, 
                                                       draggable: false, 
                                                       zindex:4,
                                                       modal: true,
                                                       visible: false
                                                     } 
                                                 );
             YAHOO.example.container.wait.setHeader("Update budget, please wait...");
             YAHOO.example.container.wait.setBody("<img src=\"images/rel_interstitial_loading.gif\"/>");
             YAHOO.example.container.wait.render(document.body);
         }

         var callback788 = {
             success : function(o) {                 
                 window.location = "${ctx}/refreshBudgetsPages.htm";
                 YAHOO.example.container.wait.hide();                 
             },
             failure : function(o) {
                 alert("Failed to update the budget !!!...Contact your system administrator...");
                 content.innerHTML = o.responseText;
                 content.style.visibility = "visible";
                 content.innerHTML = "CONNECTION FAILED!";
                 YAHOO.example.container.wait.hide();
             }
         }
         
          YAHOO.example.container.wait.show();
         var expression = document.getElementById("filter").value;
         var url = "${ctx}/findBudgetsForExpression.htm?budget_expression=" + expression;

      // Connect to our data source and load the data
         var conn = YAHOO.util.Connect.asyncRequest("GET", url, callback788);
    }
	
	function searchFromExpressionFilterForCustCode(){      
        var content = document.getElementById("content");
        content.innerHTML = "";

         if (!YAHOO.example.container.wait) {
             YAHOO.example.container.wait = 
                     new YAHOO.widget.Panel("wait",  
                                                     { width: "300px", 
                                                       fixedcenter: true, 
                                                       close: false, 
                                                       draggable: false, 
                                                       zindex:4,
                                                       modal: true,
                                                       visible: false
                                                     } 
                                                 );
             YAHOO.example.container.wait.setHeader("Update budget, please wait...");
             YAHOO.example.container.wait.setBody("<img src=\"images/rel_interstitial_loading.gif\"/>");
             YAHOO.example.container.wait.render(document.body);
         }

         var callback78888 = {
             success : function(o) {                 
                 window.location = "${ctx}/refreshBudgetsPages.htm";
                 YAHOO.example.container.wait.hide();                 
             },
             failure : function(o) {
                 alert("Failed to update the budget !!!...Contact your system administrator...");
                 content.innerHTML = o.responseText;
                 content.style.visibility = "visible";
                 content.innerHTML = "CONNECTION FAILED!";
                 YAHOO.example.container.wait.hide();
             }
         }
         
          YAHOO.example.container.wait.show();
         var expression = document.getElementById("currentCustCode").value;
         var url = "${ctx}/findBudgetsForExpression.htm?customer_code=" + expression;

      // Connect to our data source and load the data
         var conn = YAHOO.util.Connect.asyncRequest("GET", url, callback78888);
    }
    
    function deletePendingExercise(id){
        var answer = confirm("Do you really want to delete exercise [ " + ${viewExercise.exercise} +" ] ?")
        if (answer){
           
           
             var content = document.getElementById("content");
             content.innerHTML = "";

              if (!YAHOO.example.container.wait) {
                  YAHOO.example.container.wait = 
                          new YAHOO.widget.Panel("wait",  
                                                          { width: "300px", 
                                                            fixedcenter: true, 
                                                            close: false, 
                                                            draggable: false, 
                                                            zindex:4,
                                                            modal: true,
                                                            visible: false
                                                          } 
                                                      );
                  YAHOO.example.container.wait.setHeader("Deleting exercise [ " + ${viewExercise.exercise} +" ], please wait...");
                  YAHOO.example.container.wait.setBody("<img src=\"images/rel_interstitial_loading.gif\"/>");
                  YAHOO.example.container.wait.render(document.body);
              }

              var callback799 = {
                  success : function(o) {    
                	  alert("Exercise [ " + ${viewExercise.exercise} +" ] sucessfully deleted...The page will be refreshed soon...");
                      window.location = "${ctx}/showGeneralBudgetPageFromMenu.htm?forceReload=true";
                      YAHOO.example.container.wait.hide();
                     
                  },
                  failure : function(o) {
                      alert("Failed to delete the exercise [ " + ${viewExercise.exercise} +" ]!!!...Contact your system administrator...");
                      content.innerHTML = o.responseText;
                      content.style.visibility = "visible";
                      content.innerHTML = "CONNECTION FAILED!";
                      YAHOO.example.container.wait.hide();
                  }
              }
              
               YAHOO.example.container.wait.show();

           // Connect to our data source and load the data
              var conn = YAHOO.util.Connect.asyncRequest("GET", "${ctx}/deletePendingExercise.htm?id="+ id, callback799);
       }
    }

    
    
    function confirmationApprove(id) {
        var answer = confirm("Do you really want to approve exercise [ " + ${viewExercise.exercise} +" ]?")
        if (answer){
           // var url ="${ctx}/approveExercise.htm?id=";
           // window.location = url + id;
           
        	 var content = document.getElementById("content");
             content.innerHTML = "";

              if (!YAHOO.example.container.wait) {
                  YAHOO.example.container.wait = 
                          new YAHOO.widget.Panel("wait",  
                                                          { width: "300px", 
                                                            fixedcenter: true, 
                                                            close: false, 
                                                            draggable: false, 
                                                            zindex:4,
                                                            modal: true,
                                                            visible: false
                                                          } 
                                                      );
                  YAHOO.example.container.wait.setHeader("Approving budget [ " + ${viewExercise.exercise} +" ], please wait...");
                  YAHOO.example.container.wait.setBody("<img src=\"images/rel_interstitial_loading.gif\"/>");
                  YAHOO.example.container.wait.render(document.body);
              }

              var callback79 = {
                  success : function(o) { 
                	  alert("Budget [ " + ${viewExercise.exercise} +" ] sucessfully approved...The page will be refreshed soon...");
                      window.location = "${ctx}/showGeneralBudgetPageFromMenu.htm?forceReload=true";
                      YAHOO.example.container.wait.hide();
                     
                  },
                  failure : function(o) {
                      alert("Failed to approve the exercise !!!...Contact your system administrator...");
                      content.innerHTML = o.responseText;
                      content.style.visibility = "visible";
                      content.innerHTML = "CONNECTION FAILED!";
                      YAHOO.example.container.wait.hide();
                  }
              }
              
               YAHOO.example.container.wait.show();

           // Connect to our data source and load the data
              var conn = YAHOO.util.Connect.asyncRequest("GET", "${ctx}/approveExercise.htm?id="+ id, callback79);
       }
    }	
    
    function confirmationClose(id,year) {
        var answer = confirm("Do you really want to close exercise [ " + ${viewExercise.exercise} +" ]?")
        if (answer){
           // var url ="${ctx}/closeExercise.htm?id=";
           // window.location = url + id;
           
        	var content = document.getElementById("content");
            content.innerHTML = "";

             if (!YAHOO.example.container.wait) {
                 YAHOO.example.container.wait = 
                         new YAHOO.widget.Panel("wait",  
                                                         { width: "300px", 
                                                           fixedcenter: true, 
                                                           close: false, 
                                                           draggable: false, 
                                                           zindex:4,
                                                           modal: true,
                                                           visible: false
                                                         } 
                                                     );
                 YAHOO.example.container.wait.setHeader("Closing exercise [ " + ${viewExercise.exercise} +" ], please wait...");
                 YAHOO.example.container.wait.setBody("<img src=\"images/rel_interstitial_loading.gif\"/>");
                 YAHOO.example.container.wait.render(document.body);
             }

             var callback80 = {
                 success : function(o) {
                	 //alert("Exercise [ " + ${viewExercise.exercise} +" ] sucessfully closed...The page will be refreshed soon...");
					 YAHOO.example.container.wait.show();
                     window.location = "${ctx}/showGeneralBudgetPageFromMenu.htm?forceReload=true";
                     YAHOO.example.container.wait.hide();
                    
                 },
                 failure : function(o) {
                     alert("Failed to close exercise[ " + ${viewExercise.exercise} +" ]!!!...Contact your system administrator...");
                     content.innerHTML = o.responseText;
                     content.style.visibility = "visible";
                     content.innerHTML = "CONNECTION FAILED!";
                     YAHOO.example.container.wait.hide();
                 }
             }
             
              YAHOO.example.container.wait.show();

          // Connect to our data source and load the data
             var conn = YAHOO.util.Connect.asyncRequest("GET", "${ctx}/closeExercise.htm?id="+ id, callback80);
       }
        
    }
    
    function referenceBudget(id){   

       var answer = confirm("Do you really want to tag exercise [ " + ${viewExercise.exercise} +" ]?")
       if (answer){
            
           // var url ="${ctx}/referenceBudgetPage.htm?id=";      
           // window.location = url + id;
           
        	var content = document.getElementById("content");
            content.innerHTML = "";

             if (!YAHOO.example.container.wait) {
                 YAHOO.example.container.wait = 
                         new YAHOO.widget.Panel("wait",  
                                                         { width: "300px", 
                                                           fixedcenter: true, 
                                                           close: false, 
                                                           draggable: false, 
                                                           zindex:4,
                                                           modal: true,
                                                           visible: false
                                                         } 
                                                     );
                 YAHOO.example.container.wait.setHeader("Tagging budget [ " + ${viewExercise.exercise} +" ], please wait...");
                 YAHOO.example.container.wait.setBody("<img src=\"images/rel_interstitial_loading.gif\"/>");
                 YAHOO.example.container.wait.render(document.body);
             }

             var callback81 = {
                 success : function(o) {
                	 alert("Budget [ " + ${viewExercise.exercise} +" ] sucessfully tagged...The page will be refreshed soon...");
                     window.location = "${ctx}/showGeneralBudgetPageFromMenu.htm?forceReload=true";
                     YAHOO.example.container.wait.hide();
                     
                 },
                 failure : function(o) {
                     alert("Failed to tag the budget [ " + ${viewExercise.exercise} +" ]!!!...Contact your system administrator...");
                     content.innerHTML = o.responseText;
                     content.style.visibility = "visible";
                     content.innerHTML = "CONNECTION FAILED!";
                     YAHOO.example.container.wait.hide();
                 }
             }
             
              YAHOO.example.container.wait.show();

          // Connect to our data source and load the data
             var conn = YAHOO.util.Connect.asyncRequest("GET", "${ctx}/referenceBudgetPage.htm?id="+ id, callback81);
        }
    }

</script>



<script>
YAHOO.namespace("example.container");

// Define various event handlers for Dialog
	var handleSubmit = function() {
		this.submit();		
	};
	var handleCancel = function() {
		this.cancel();
	};

	var myButtonsAll = [ { text:"Submit", handler:handleSubmit, isDefault:true },{ text:"Cancel", handler:handleCancel } ];
	var myButtonsCancelOnly = [{ text:"Cancel", handler:handleCancel } ];

function init() {
	
	var handleSuccess = function(o) {
		alert("Submission ok");
	};
	var handleFailure = function(o) {
		alert("Submission failed: " + o.status);
	};
	
	// Define various event handlers for Dialog
	var handleYes = function() {		
		 buildExercise();
		this.hide();
	};
	
	var handleCloseExerciseYes = function() {		
		confirmationClose(${currentExercise.id},${viewExercise.exercise});
		this.hide();
	};
	
	
	
	var handleApproveExerciseYes = function() {		
		confirmationApprove(${viewbudget.exercises[0].id});
		this.hide();
	};
	
	
	var handleNo = function() {
		this.hide();
	};

	
		YAHOO.example.container.acceptTermsDialog = new YAHOO.widget.SimpleDialog("acceptTermsDialog", { 
																										width: "80em", 
																										effect:{
																											effect: YAHOO.widget.ContainerEffect.FADE,
																											duration: 0.25
																										}, 
																										fixedcenter: true,
																										modal: true,
																										visible: false,
																										draggable: false,
																										icon: YAHOO.widget.SimpleDialog.ICON_WARN,
																										constraintoviewport: false, 
																										buttons: [ { text:"Proceed", handler:handleYes, isDefault:true }, 
																													{ text:"Cancel",  handler:handleNo } ] 

																										});
																										

		YAHOO.example.container.closeExerciseDialog = new YAHOO.widget.SimpleDialog("closeExerciseDialog", { 
																										width: "80em", 
																										effect:{
																											effect: YAHOO.widget.ContainerEffect.FADE,
																											duration: 0.25
																										}, 
																										fixedcenter: true,
																										modal: true,
																										visible: false,
																										draggable: false,
																										icon: YAHOO.widget.SimpleDialog.ICON_WARN,
																										constraintoviewport: false, 
																										buttons: [ { text:"Proceed", handler:handleCloseExerciseYes, isDefault:true }, 
																													{ text:"Cancel",  handler:handleNo } ] 

																										});	


		YAHOO.example.container.approveExerciseDialog = new YAHOO.widget.SimpleDialog("approveExerciseDialog", { 
																										width: "80em", 
																										effect:{
																											effect: YAHOO.widget.ContainerEffect.FADE,
																											duration: 0.25
																										}, 
																										fixedcenter: true,
																										modal: true,
																										visible: false,
																										draggable: false,
																										icon: YAHOO.widget.SimpleDialog.ICON_WARN,
																										constraintoviewport: false, 
																										buttons: [ { text:"Proceed", handler:handleApproveExerciseYes, isDefault:true }, 
																													{ text:"Cancel",  handler:handleNo } ] 

																										});																											
																										
	

	 
	
	
	
	
	// Instantiate the Dialog1
	YAHOO.example.container.dialog1 = new YAHOO.widget.Dialog("addDialog",
																{ width : "30em",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
																	      	  { text:"Cancel", handler:handleCancel } ]
																}
							);

	// Instantiate the Dialog2
	YAHOO.example.container.dialog2 = new YAHOO.widget.Dialog("addDialog2",
																{ width : "500px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false
																,
																  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
																	      	  { text:"Cancel", handler:handleCancel } ]
																			  
																}
							);

	// Instantiate the Dialog3
	YAHOO.example.container.dialog3 = new YAHOO.widget.Dialog("addDialog3",
																{ width : "200px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
																	      	  { text:"Cancel", handler:handleCancel } ]
																}
							);

	// Instantiate the addDialogColumns
	YAHOO.example.container.dialog4 = new YAHOO.widget.Dialog("addDialogColumns",
																{ width : "30em",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
																	      	  { text:"Cancel", handler:handleCancel } ]
																}
							);


	// Instantiate the Dialog1
	/*
	YAHOO.example.container.dialog11 = new YAHOO.widget.Dialog("addDialog11",
																{ width : "850px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [{ text:"Close", handler:handleCancel, isDefault:true } ]
																}
							);

	// Instantiate the Dialog2
	YAHOO.example.container.dialog12 = new YAHOO.widget.Dialog("addDialog12",
																{ width : "850px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [{ text:"Close", handler:handleCancel, isDefault:true } ]
																	      	  
																}
							);

	*/
	

	
	// Validate the entries in the form to require that both first and last name are entered
	YAHOO.example.container.dialog2.validate = function() {
		var data = this.getData();
		/*
		if (data.prevision == "" || data.reported == "") {
			alert("Please enter previson  and reported amount.");
			return false;
		} 
		*/
			
		if (data.prevision != "" && ! is_numeric(data.prevision) ){
			alert("Le budget initial n'est pas valide...");
			return false;
		} 

		if (data.prevision != "" && data.prevision < 0 ){
			alert("Le budget initial doit être supérieur ou égale 0.");
			return false;
		} 



		if (data.reported != "" && ! is_numeric(data.reported) ){
			alert("Le budget reporté n'est pas valide...");
			return false;
		} 

		
		if (data.reported != "" && data.reported < 0 ){
			alert("Le budget reporté doit être supérieur ou égale 0.");
			return false;
		} 



		if (data.contract == "") {
			alert("Entrez la référence du contrat");
			return false;
		}
		
		if (data.mandat == "-1") {
			alert("Entrez le mandat du contrat");
			return false;
		} 

		/*
		if (data.associate == "") {
			alert("Please enter associate");
			return false;
		}
		
		
		if (data.manager == "") {
			alert("Please enter manager");
			return false;
		} 
		*/

		return true;
		
	};


	// Validate the entries in the form to require that both first and last name are entered
	YAHOO.example.container.dialog3.validate = function() {
		var data = this.getData();
	
			
		if (! is_numeric(data.inflation) ){
			alert("Please  inflation  must be a number.");
			return false;
		} 

		
		return true;
		
	};
	
	// Render the Dialog
	YAHOO.example.container.acceptTermsDialog.render();
	YAHOO.util.Event.addListener("showBuildExerciceAcceptTerms", "click", YAHOO.example.container.acceptTermsDialog.show, YAHOO.example.container.acceptTermsDialog, true);
	YAHOO.example.container.acceptTermsDialog.hide();
	
	
	YAHOO.example.container.closeExerciseDialog.render();
	YAHOO.util.Event.addListener("showCloseExerciseDialog", "click", YAHOO.example.container.closeExerciseDialog.show, YAHOO.example.container.closeExerciseDialog, true);
	YAHOO.example.container.closeExerciseDialog.hide();
	
	YAHOO.example.container.approveExerciseDialog.render();
	YAHOO.util.Event.addListener("showApproveExerciceAcceptTerms", "click", YAHOO.example.container.approveExerciseDialog.show, YAHOO.example.container.approveExerciseDialog, true);
	YAHOO.example.container.approveExerciseDialog.hide();
	
	
	


	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog1.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show", "click", YAHOO.example.container.dialog1.show, YAHOO.example.container.dialog1,true);
	YAHOO.example.container.dialog1.render();

	
	YAHOO.example.container.dialog2.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog2.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show2", "click", YAHOO.example.container.dialog2.show, YAHOO.example.container.dialog2,true);
	YAHOO.example.container.dialog2.render();

	YAHOO.example.container.dialog3.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog3.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show3", "click", YAHOO.example.container.dialog3.show, YAHOO.example.container.dialog3,true);
	YAHOO.example.container.dialog3.render();


	YAHOO.example.container.dialog4.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog4.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show5", "click", YAHOO.example.container.dialog4.show, YAHOO.example.container.dialog4,true);
	YAHOO.example.container.dialog4.render();

	/*
	YAHOO.example.container.dialog11.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog11.callback = {success: handleSuccess,failure: handleFailure };
	//YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.dialog11.show, YAHOO.example.container.dialog11,true);
	YAHOO.example.container.dialog11.render();

	
	YAHOO.example.container.dialog12.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog12.callback = {success: handleSuccess,failure: handleFailure };
	//YAHOO.util.Event.addListener("show2", "click", YAHOO.example.container.dialog12.show, YAHOO.example.container.dialog12,true);
	YAHOO.example.container.dialog12.render();
	*/
	 

	 window.onload=function(){
		
	 }
	
	

	
}

YAHOO.util.Event.onDOMReady(init);
function showUploadDialog() {
	YAHOO.example.container.dialog1.show();
	YAHOO.example.container.dialog2.show();
	YAHOO.example.container.dialog3.show();
	YAHOO.example.container.dialog4.show();
}
/*
 function chartPerAssocie(){
    YAHOO.example.container.dialog11.render();
    YAHOO.example.container.dialog11.show();
}

function chartPerManager(){
    YAHOO.example.container.dialog12.render();
    YAHOO.example.container.dialog12.show();
}
*/

function resetForm(){

    document.forms["addBudgetForm"].elements["budgetId"].value = "";
	// document.forms["addBudgetForm"].elements["year"].value = "";
    //document.forms["addBudgetForm"].elements["currency"].value = "";
    document.forms["addBudgetForm"].elements["prevision"].value = "";
    document.forms["addBudgetForm"].elements["reported"].value = "";
	document.forms["addBudgetForm"].elements["comments"].value = "";
    //document.forms["addBudgetForm"].elements["customer"].value = "";
	document.forms["addBudgetForm"].elements["contract"].value = "";
	document.forms["addBudgetForm"].elements["contract"].disabled = false;
	
	document.forms["addBudgetForm"].elements["status"].value = "PENDING";
	document.forms["addBudgetForm"].elements["associate"].value = "";
    document.forms["addBudgetForm"].elements["manager"].value = "";
    document.forms["addBudgetForm"].elements["fiable"].checked = true;
	document.forms["addBudgetForm"].elements["interim"].checked = false;

    document.getElementById('formTitle2').innerHTML = 'New budget';

	
	YAHOO.example.container.dialog2.cfg.queueProperty("buttons", myButtonsAll);  

    YAHOO.example.container.dialog2.render();
    YAHOO.example.container.dialog2.show();
	
}



function applyInflation(){
	document.getElementById('formTitle3').innerHTML = 'Apply inflation';
    YAHOO.example.container.dialog3.render();
    YAHOO.example.container.dialog3.show();
}

var handleSuccess = function(o){
	
    var form = document.forms["addBudgetForm"];
    if(o.responseText !== undefined){
        // Use the JSON Utility to parse the data returned from the server   
         try {   
            var object = YAHOO.lang.JSON.parse(o.responseText);

            form.elements["budgetId"].value = object.budgetId;
            form.elements["year"].value = object.year;
			//form.elements["year"].disabled = true;
            form.elements["currency"].value = object.currency;
			//form.elements["currency"].disabled = true;
            form.elements["prevision"].value = object.prevision;
            form.elements["reported"].value = object.reported;
			form.elements["comments"].value = object.comments;
			//form.elements["customer"].value = object.customer;
			form.elements["contract"].value = object.contract;
			form.elements["contract"].disabled = true;
			form.elements["status"].value = object.status;
			form.elements["associate"].value = object.associate;
			form.elements["manager"].value = object.manager;
			form.elements["fiable"].checked = object.fiable;
			form.elements["mandat"].value = object.mandat;
			form.elements["interim"].checked = object.interim;
			
            
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle2').innerHTML =  object.customerName + ':' +object.year ;
         }
         catch (x) {   
             alert("JSON Parse failed in parsing intervention!");
             return;
         } 

		if( form.elements["status"].value == "CLOSED"){			
			YAHOO.example.container.dialog2.cfg.queueProperty("buttons", myButtonsCancelOnly);
		}else{			
			YAHOO.example.container.dialog2.cfg.queueProperty("buttons", myButtonsAll);
		}

        YAHOO.example.container.dialog2.render();
        YAHOO.example.container.dialog2.show();		
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

var sEditBudgetItemUrl = "${ctx}/editBudgetItemPage.htm";

function editBudgetItem(budgetId){
    var postData = "budgetId="+budgetId;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditBudgetItemUrl, callback, postData);
}

function deleteBudgetItem(id,year, name, type)
  {
	   var question = "Do you really want to cancel budget "+name + " [ " + type + " ] ";
    if (confirm(question))
    {
      window.location="${ctx}/deletesBudgetItemPage.htm?budgetId="+id + "&year=" +year;
    }
  }

 
</script>






<script type="text/javascript">
YAHOO.example.ItemSelectHandler = function() {
    // Use a LocalDataSource
  var oDS = new YAHOO.util.XHRDataSource('${ctx}/editBudgetValidContractReferencesAsAjaxStream.htm'); 
  oDS.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;   
 // Define the schema of the JSON results   
 oDS.responseSchema = {   
 resultsList : "events",   
 fields : ["name"]   
 };   

   
    // Instantiate the AutoComplete
    var oAC = new YAHOO.widget.AutoComplete("contract", "myContainer", oDS);
    oAC.resultTypeList = false;
	oAC.applyLocalFilter = true;
	oAC.queryDelay = .5;
    
    // Define an event handler to populate a hidden form field
    // when an item gets selected
	
    //var missionId = YAHOO.util.Dom.get("missionId");
    //var myHandler = function(sType, aArgs) {
       // var myAC = aArgs[0]; // reference back to the AC instance
       // var elLI = aArgs[1]; // reference to the selected LI element
       // var oData = aArgs[2]; // object literal of selected item's result data
        
        // update hidden form field with the selected item's ID
      //  missionId.value = oData.id;
    //};
    //oAC.itemSelectEvent.subscribe(myHandler);
    
    
    return {
        oDS: oDS,
        oAC: oAC
    };
}();


</script>

</html>