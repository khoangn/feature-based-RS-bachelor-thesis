//MAINTAIN SCROLL POSITION
$(window).scroll(function() {
	  sessionStorage.scrollTop = $(this).scrollTop();
});

$(document).ready(function() {
	  if (sessionStorage.scrollTop != "undefined") {
	    $(window).scrollTop(sessionStorage.scrollTop);
	  }
});

//SHOW RECOMMENDATION (BUTTON)
$(document).ready(function(){
	var count = parseInt(item_rated_count, 10);
	if (item_rated_count < 10) {
		localStorage.setItem("show-recommendation-itemBased", "false");
		document.getElementById("recommended-item").display = "none";
		$('#item-to-rate').show();
	} else if (item_rated_count >= 10){
		localStorage.setItem("show-recommendation-itemBased", "true");
		localStorage.removeItem('rate-more');
		$('#item-to-rate').hide();
		//document.getElementById("recommended-item").display = "block";
	}
	
	var show_recommendation = localStorage.getItem("show-recommendation-itemBased");
	if (show_recommendation == "true") {
    	document.getElementById("recommended-item").style.display = "block";
	} else if (show_recommendation == "false" || show_recommendation == null){
    	document.getElementById("recommended-item").style.display = "none";
	}
});

//ON CLICK SHOW RECOMMENDATION BUTTON
$(function(){
    $("#show-recommendation-button").click(function(){
    	localStorage.setItem("show-recommendation-itemBased", "true");
    });   
});