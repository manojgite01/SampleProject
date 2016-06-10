// Util object to add common methods
// Add common methods in this js

function VTUtil(){}
	
/**
 * Method returns true if any of the checkbox is checked
 */
VTUtil.isAnyCheckBoxChecked = function(name){
	var isChecked = false;
	$('input[name="'+name+'"]:not(:disabled)').each(function(){
 	 	 if(this.checked){
 	 		 isChecked = true;
 	 	 } 
	});
	return isChecked;
}



/**
 * Method returns true if any of the checkbox is checked
 */
VTUtil.areAllCheckBoxChecked = function(name){
	var isChecked = false;
	$('input[name="'+name+'"]:not(:disabled)').each(function(){
 	 	 if(this.checked){
 	 		 isChecked = true;
 	 	 }else{
 	 		 isChecked = false;
 	 	 }
 		 return isChecked;
	});
	return isChecked;
}


/**
 * Util method to submit form with action and userActionValue
 * 
 * @param form
 * @param action
 * @param userActionValue
 */
VTUtil.submitForm = function(form, action, userActionValue){
	if(action==undefined || action =='') {
		console.log("Please define an action.");
		return;
	}
	form.action = action;
	form.userAction.value = userActionValue;
	form.submit();
}


