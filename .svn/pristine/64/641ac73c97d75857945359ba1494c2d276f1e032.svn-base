//#########

var init_dialog_flag = false;

var OK_FUNC=function(){
	
};
var CANCEL_FUNC=function(){
	
};
var BEFORE_CLOSE=function(){
	
};
var AFTER_CLOSE=function(){
	
};
var BEFORE_OK=function(){
	
};
var AFTER_OK=function(){
	
};
function setConfirmMessage(message){
	$("#confirm_dialog_message").html(message);
}

$(function(){
	if(!init_dialog_flag){
		$("#dialog-confirm").dialog({
			resizable : true,
			width : 400,
			height : 200,
			modal : true,
			buttons : {
				"OK" : function(){
					OK_FUNC();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
		init_dialog_flag=true;
		$("#dialog-confirm").dialog("close");
	}
		
});

