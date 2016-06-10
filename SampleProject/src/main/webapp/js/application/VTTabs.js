/*JS files to handle tab navigation*/
function VTTabs(){}

$(document).ready(function() {
    $(".tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("selected");
        $(this).parent().siblings().removeClass("selected");
        var tab = $(this).attr("href");
        $(".tab-content").not(tab).css("display", "none");
        // Show the tab
        $(tab).fadeIn();
        // Also align the columns for datatables as mentioned
        // https://datatables.net/release-datatables/examples/api/tabs_and_scrolling.html
        $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
    });
});



/**
 * Generic function to show validation message.
 * Used to show validation message in the forms
 * The form name is hard coded 
 * @param message
 */
function showValidationMessage(formId, message){
	$(formId + " #validationMsg").text(message);
	$(formId + " #validationMsgDiv").css("display","block");
	$(formId + " #updateBtnDiv").css("margin-top","160px");
	$(formId + " #createBtnDiv").css("margin-top","50px");
	$(formId + " #cloneBtnDiv").css("margin-top","160px");
}

function showModalValidationMessage(formId, message){
	$(formId + " #modalValidationMsg").text(message);
	$(formId + " #modalValidationMsgDiv").css("display","block");
}

function clearPopValidations(formId){
	$(formId + " #modalValidationMsg").text("");
	$(formId + " #modalValidationMsgDiv").css("display","none");
}