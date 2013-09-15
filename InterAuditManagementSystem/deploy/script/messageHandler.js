/**
 * <p>Title: Javascript Message handler</p>
 * <p>Description: Handle message that are displayed on top of a page.
 * 
 * The message can be an "ok" message
 * or error message.
 * 
 * For error messages, a java exception - human readable message translation is provided 
 * 
 * </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Intrasoft International</p>
 * @author Francois Talleu
 * @version $Id: messageHandler.js,v 1.21 2011/05/11 13:07:36 dlastovicka Exp $
 * <pre>
 * Changes
 * =======
 * Date          Author     Modification
 * 11/12/09      FTA        created doc, added translation to exceptions
 * 15/12/09		 FTA		Improved error handling : when the ajax returned page is success, but
 * 							contain an error (this can happen because of the defaultError.jsp
 * 							this error is displayed on top on the screen
 * </pre>
 */

/*
 * java exeption to human readable text  
 */
javaExceptionTranslation = {
	FileNotFoundInContentRepositoryException  : "Error : The file was not found in the content repository",
	ContractReferenceAlreadyExistsException : "Cannot save the contract : the choosen contract reference already exists",
	ErrorInWizardException : "The wizard cannot be saved if it contains error(s). Please solve all errors and try again.",
	AuthorizationException : "User not authorized.",
	PrintShopNotAvailableException : "Printshop not available at the moment." ,
	MaxUploadSizeExceededException : "Maximum size allowed for file upload is 10Mo." 
}


function showMessage(messageText,messageType,id) {
	msg="<div id='infoZone' class='" + messageType + "'><span id='infoZone_messageId' class='hide'>" + id + "</span>" + messageText + "<span id='infoZone_close' class='hide button' style='float:right;'>Close</span></div>";
	$("#infoZone").remove();
	$("BODY").prepend(msg);
	$("#infoZone").mouseover(function() {
		$(this).find("#infoZone_close").show();
	}).mouseout(function() {
		$(this).find("#infoZone_close").hide();
	});
	if (id) {
		$("#infoZone_close").click(function(){
			var msgId = $("#infoZone_messageId").text();
			removeMessageId(msgId);
		});
	} else {
		$("#infoZone_close").click(function(){
			removeMessage();
		});
	}
}

/**
 messages is expected to be an array with array elements of size 2
 messages[x][0] is messageType
 messages[x][1] is messageText
 */
function showMessageList(caption, messageType, messages) {
	showMessageIdList(null, caption,messageType, messages);
}

/**
 messages is expected to be an array with array elements of size 2
 messages[x][0] is messageType
 messages[x][1] is messageText
 */
function showMessageIdList(id, caption, messageType, messages, exceptionsText) {
	var list = "";
	for (i = 0; i<messages.length; i++) {
		if (i==0) {
			list = "<ul class='standard'>"
		}
		list += "<li class='" + messages[i][0] + "'>" + messages[i][1] + "</li>";
		if (i==messages.length-1) {
			list += "</ul>"
		}
	}
	var msg = "<span class='standard'>" + caption + "</span>" + list;
	if (id) {
		showMessageId(id, msg, messageType);
	} else {
		showMessage(msg, messageType, messageType);
	}
	
	addHiddenVerboseMsgToInfoZone(exceptionsText);
}


function showAjaxErrorMessage(data) {
	showAjaxErrorMessageId(null, data);
}

function showAjaxErrorMessageId(id,data) {
 	var errorMsg = data.responseText;
 	//here I would like to have something like:
 	//  var locMsg = $(errorMsg).find("#localizedMessage");
 	//unfortunatelly it somehow does not work...
	var errorMsgObj = $(errorMsg);
	var locMsg; 
	errorMsgObj.each(function() {
		var id = this.id; 
		if (id === "localizedMessage") {
			locMsg = $(this).html(); 
		}
	});
	var messageText = locMsg;
	
	//show the error message provided in the parameter
	if (id) {
		showMessageId(id, messageText, "error");
	} else {
		showMessage(messageText, "error");
	}
	
	addHiddenVerboseMsgToInfoZone(errorMsg);
}

/*
 * If the user double click on the message, he will be able to
 * see more information about the error
 */
function showMessageWithErrorData(messageText,messageType,data) {
	showMessageIdWithErrorData(null, messageText, messageType, data);
}

function showMessageIdWithErrorData(id,messageText,messageType,data) {
 	errorMsg = data.responseText;
	
	/* we search in the stack trace if we have translation for
	 * an exception thrown in the stacktrace
	 */ 
	
	for (var exceptionName in javaExceptionTranslation) {
		 if (errorMsg.match(exceptionName)) {
		 	messageText = javaExceptionTranslation[exceptionName];
			break;
		 }
	}
	
	//show the error message provided in the parameter
	if (id) {
		showMessageId(id, messageText, messageType);
	} else {
		showMessage(messageText, messageType);
	}
	
	addHiddenVerboseMsgToInfoZone(errorMsg);
	

}

function addHiddenVerboseMsgToInfoZone(msg) {
	$("#infoZone").append("<div class='hide' id='infoZoneVerboseMsg' >"+msg+ " <span>");
	$("#infoZone").dblclick(function () {
		$("#infoZoneVerboseMsg").toggle();
		if ($("#infoZone").hasClass("enlarged")) {
			$("#infoZone").removeClass("enlarged");
		} else {
			$("#infoZone").addClass("enlarged");
		}		
	});	
}

/*
 * if the exception is found in the list of user friendly 
 * messages, we show the error message in a alert box, and 
 * go back to the previous page doing
 * an history.back();
 */
function showErrorMessageAndGoBack(stackTrace) {
		for (var exceptionName in javaExceptionTranslation) {
		 if (stackTrace.match(exceptionName)) {
		 	messageText = javaExceptionTranslation[exceptionName];
			$("body").remove();
			alert(messageText);
		    history.back();
			
			break;
		 }
	}
}

function removeMessage(){
	/*
	 * we introduice a delay of 1 sec before removing a message
	 * so the user will not be frustrated when seeing a 'flashing' message
	 * with not enough time to read.
	 */
	setTimeout( function() {
		$("#infoZone").remove();
		}, 1000);
	
}

function removeMessageNow(){
	$("#infoZone").remove();
}

/**
 * special message to be displayed after an ajax call when the server
 * is not responding anymore.
 * @param {Object} messageText
 */

function showSessionErrorMessage(messageText) {
	msg="<div id='infoZoneSessionError' class='error'> An error occured : " + messageText + "</div>";
	$("#infoZone,#infoZoneSessionError").remove();
	
	$("BODY").prepend(msg);	

	
}

/*
 * If the user double click on the message, he will be able to
 * see more information about the error
 */
function showSessionErrorMessageWithData(messageText, data){
	errorMainMessageText = $(data).find("#mainErrorMsg").text();
	
	/*
	 * if the page is an error, we try to find the main
	 * error message and we display the msg.
	 */
	if (errorMainMessageText != "" ) {
		showSessionErrorMessage(errorMainMessageText);

		
	} else {
		showSessionErrorMessage(messageText);
		
	}
 	
	errorMessageText = $(data).find("#errorMsg").text();
	
	$("#infoZoneSessionError").append("<div class='hide' id='infoZoneVerboseMsg' >"+errorMessageText+ " <span>");
	$("#infoZoneSessionError").dblclick(function () {
		$("#infoZoneVerboseMsg").toggle();
		if ($("#infoZoneSessionError").hasClass("enlarged")) {
			$("#infoZoneSessionError").removeClass("enlarged");
		} else {
			$("#infoZoneSessionError").addClass("enlarged");
		}
		
	});			
	
	
}


	/**
		auxiliary message methods which allows to handle multiple messages displayed with concurent ajax calls
		These methods keep stack of messages displayed and redisplays message from the top when the displayed message is removed.
		
		Example of difference between showMessage() and methods bellow:
		showMessage("msg1", "error");
		showMessage("msg2", "info");
		removeMessage();
		//result: nothing displayed
		
		showMessageId("a","msg1", "error");
		showMessageId("b","msg2", "info");
		removeMessageId("b");
		//result: message "msg1" displayed
		
	*/

	var messages_hash=new Object();
	var messages_stack=new Array();
	var messages_stack_len=0;
	var messages_lastMessageId;
	
	function showMessageId(id, msg, type) {
		if (!messages_hash[id]) {
			messages_stack[messages_stack_len]=id;
			messages_stack_len++;
		}
		messages_hash[id] = new Array(id,msg,type);
		messages_lastMessageId = id;
		showMessage(msg,type,id);
	}
	
	function removeMessageId(id) {
		delete messages_hash[id];
		if (messages_lastMessageId==id) {
			removeMessageNow();
			//display previous message
			//-remove nonexisting messages from the top of stack
			if (messages_stack_len>0) {
				while(messages_stack_len>0 && !messages_hash[messages_stack[messages_stack_len-1]]) {
					delete messages_stack[messages_stack_len-1];
					messages_stack_len--;
				}
			}
			//-display the message on the top of the stack
			if (messages_stack_len>0) {
				messages_lastMessageId=messages_stack[messages_stack_len-1];
				showMessage(messages_hash[messages_lastMessageId][1],messages_hash[messages_lastMessageId][2],messages_lastMessageId);
			}
		}
	}
	
	function removeAllMessageId() {
		messages_hash=new Object();
		messages_stack=new Array();
		messages_lastMessageId="";
		messages_stack_len=0;
		removeMessageNow();
	}
