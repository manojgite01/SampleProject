	//DA77241: function to find user name by User Id
	function findPersonByUserId(id,hiddenId){
		
		//var pattern =  /[a-z|A-Z][a-z|A-Z]+\d{5,}/g; 
		var userId = $('#'+id).val(); 	
		
		if(userId != ''){
					
			//if(userId.length == 7 && pattern.test(userId)) {				 
			if(userId.length == 7) {
				 $.ajax({  
				     type:"Get",   
				     url:"program",
				     data:"userAction=findPerson&userId="+userId,
				     datatype:"json",
				     success:function(json){
				    	 document.getElementById(id).value=json[0].text;	
						 document.getElementById(hiddenId).value=json[0].value;			 				
					 }  
				 });				
			} else {
				if(id=='userIdText' && hiddenId=='userIdSave'){
					$('#entryMsgDiv').text("Please provide valid User ID");
					$('#'+id).val("");
					$('#'+id).focus();
					return false;
				}else{
				$('#msg').text("Please provide valid User ID");
				$('#'+id).val("");
				$('#'+id).focus();
				return false;
				}
			}
		} 
	} 
	function contractText(textarea){
		setTimeout("contractTextTimeOut();", 200);
		textarea.rows = 1;
		textarea.cols = 37;
	}	