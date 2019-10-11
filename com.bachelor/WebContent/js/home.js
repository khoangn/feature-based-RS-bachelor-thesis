

//STAR RATING
$.fn.stars = function() {
    return $(this).each(function() {
        var rating = $(this).data("rating");
        var numStars = $(this).data("numStars");
        var fullStar = new Array(Math.floor(rating + 1)).join('<i class="fa fa-star"></i>');
        var halfStar = ((rating%1) !== 0) ? '<i class="fa fa-star-half-empty"></i>': '';
        var noStar = new Array(Math.floor(numStars + 1 - rating)).join('<i class="fa fa-star-o"></i>');
        $(this).html(fullStar + halfStar + noStar);
    });
}
$('.stars').stars();

// USER PROFILE ICON
$(document).ready(function(){
    $(".menu-icon").click(function(){
        $(this).find(".user-menu").fadeIn("fast");
    });
});
$(document).on("click", function(event){
    var $trigger = $(".menu-icon");
    if($trigger !== event.target && !$trigger.has(event.target).length){
        $(".user-menu").fadeOut("fast");
    }            
});

//ADD FEATURE
//$(document).ready(function(){
//    $(".add-feature").click(function(){
//        $(this).find(".false-feature").fadeIn("fast");
//    });
//});
//$(document).on("click", function(event){
//    var $trigger = $(".add-feature");
//    if($trigger !== event.target && !$trigger.has(event.target).length){
//        $(".false-feature").fadeOut("fast");
//    }            
//});

/* When the user clicks on the button, 
toggle between hiding and showing the dropdown content */
function showFeature() {
  document.getElementById("false-feature").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.add-feature')) {
    var dropdowns = document.getElementsByClassName("false-feature");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

// SHOW RECOMMENDATION (BUTTON)
$(document).ready(function(){
	var count = parseInt(feature_count, 10);
	if (feature_count == 0) {
		document.getElementById("filter").style.display = "none";
		localStorage.setItem("show-alert", "true");
		localStorage.setItem("show-recommendation", "false");
	} else {
		localStorage.setItem("show-alert", "false");
	}
	
	var show_alert = localStorage.getItem("show-alert");
	if (show_alert == "true") {
		document.getElementById("alert-feature").style.display = "block";
	} else {
		document.getElementById("alert-feature").style.display = "none";
		document.getElementById("show-recommendation-button").style.display = "block";
	}
	
	var show_recommendation = localStorage.getItem("show-recommendation");

	if (show_recommendation == "true") {
    	document.getElementById("recommended-item").style.display = "block";
    	document.getElementById("top-rated").style.display = "none";

	} else if (show_recommendation == "false" || show_recommendation == null) {
    	document.getElementById("recommended-item").style.display = "none";
    	document.getElementById("top-rated").style.display = "block";    	
	}
});

// ON CLICK SHOW RECOMMENDATION BUTTON
$(function(){
    $("#show-recommendation-button").click(function(){
    	localStorage.setItem("show-recommendation", "true");
    });   
});


// SCROLL POSITION
$(window).scroll(function() {
	  sessionStorage.scrollTop = $(this).scrollTop();
});

$(document).ready(function() {
	  if (sessionStorage.scrollTop != "undefined") {
	    $(window).scrollTop(sessionStorage.scrollTop);
	  }
});




