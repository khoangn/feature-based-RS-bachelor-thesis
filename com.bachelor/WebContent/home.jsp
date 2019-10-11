<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="http://localhost:8080/com.bachelor/">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<meta http-equiv="Content-Style-Type" content="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-metro.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/home.css">
<style>


</style>

<script>
	var feature_count =  "${feature_count}" ;
	var username = "${username}" ;
	
	//CLICK COUNTER
	var filter_clicks = 0;
	var feature_recommendation_clicks = 0;
	var item_recommendation_clicks = 0;
	var use_feature_recommendation_clicks = 0;
	var explain_feature_recommendation_clicks = 0;
	var more_explanation_clicks = 0;
	
	function click_filter(){
		if (!localStorage.getItem('${username}-filter_clicks')) {
			filter_clicks += 1;
			localStorage.setItem('${username}-filter_clicks', filter_clicks);
		} else if (localStorage.getItem('${username}-filter_clicks')) {
			filter_clicks = parseInt(localStorage.getItem('${username}-filter_clicks'), 10);
			filter_clicks += 1;
			localStorage.setItem('${username}-filter_clicks', filter_clicks);
		}
	}
	
	function click_feature_recommendation(){
		if (!localStorage.getItem('${username}-feature_recommendation_clicks')) {
			feature_recommendation_clicks += 1;
			localStorage.setItem('${username}-feature_recommendation_clicks', feature_recommendation_clicks);
		} else if (localStorage.getItem('${username}-feature_recommendation_clicks')) {
			feature_recommendation_clicks = parseInt(localStorage.getItem('${username}-feature_recommendation_clicks'), 10);
			feature_recommendation_clicks += 1;
			localStorage.setItem('${username}-feature_recommendation_clicks', feature_recommendation_clicks);
		}
	}
	
	function click_use_feature_recommendation(){
		if (!localStorage.getItem('${username}-use_feature_recommendation_clicks')) {
			use_feature_recommendation_clicks += 1;
			localStorage.setItem('${username}-use_feature_recommendation_clicks', use_feature_recommendation_clicks);
		} else if (localStorage.getItem('${username}-use_feature_recommendation_clicks')) {
			use_feature_recommendation_clicks = parseInt(localStorage.getItem('${username}-use_feature_recommendation_clicks'), 10);
			use_feature_recommendation_clicks += 1;
			localStorage.setItem('${username}-use_feature_recommendation_clicks', use_feature_recommendation_clicks);
		}
	}
	
	function click_explain_feature_recommendation(){
		if (!localStorage.getItem('${username}-explain_feature_recommendation_clicks')) {
			explain_feature_recommendation_clicks += 1;
			localStorage.setItem('${username}-explain_feature_recommendation_clicks', explain_feature_recommendation_clicks);
		} else if (localStorage.getItem('${username}-use_feature_recommendation_clicks')) {
			explain_feature_recommendation_clicks = parseInt(localStorage.getItem('${username}-explain_feature_recommendation_clicks'), 10);
			explain_feature_recommendation_clicks += 1;
			localStorage.setItem('${username}-explain_feature_recommendation_clicks', explain_feature_recommendation_clicks);
		}
	}
	
	function click_item_recommendation(){
		if (!localStorage.getItem('${username}-item_recommendation_clicks')) {
			item_recommendation_clicks += 1;
			localStorage.setItem('${username}-item_recommendation_clicks', item_recommendation_clicks);
		} else if (localStorage.getItem('${username}-item_recommendation_clicks')) {
			item_recommendation_clicks = parseInt(localStorage.getItem('${username}-item_recommendation_clicks'), 10);
			item_recommendation_clicks += 1;
			localStorage.setItem('${username}-item_recommendation_clicks', item_recommendation_clicks);
		}
	}
	
	function click_more_explanation(){
		if (!localStorage.getItem('${username}-more_explanation_clicks')) {
			more_explanation_clicks += 1;
			localStorage.setItem('${username}-more_explanation_clicks', more_explanation_clicks);
		} else if (localStorage.getItem('${username}-more_explanation_clicks')) {
			more_explanation_clicks = parseInt(localStorage.getItem('${username}-more_explanation_clicks'), 10);
			more_explanation_clicks += 1;
			localStorage.setItem('${username}-more_explanation_clicks', more_explanation_clicks);
		}
	}
	
	//SAVE CLICK DATA
	function saveData(data, fileName) {
		var a = document.createElement('a');
		document.body.appendChild(a);
		a.style = 'display: none';

		var json = JSON.stringify(data),
		blob = new Blob([data], {type: 'text/plain;charset=utf-8'}),
		url = window.URL.createObjectURL(blob);
		a.href = url;
		a.download = fileName;
		a.click();
		window.URL.revokeObjectURL(url);
	}

	function main() {
		var logout = document.getElementById("logout-button");
		//var home = document.getElementById("home-button");
		logout.addEventListener("click", saveFile, false);
		//home.addEventListener("click", home, false);
	}
	
	//SAVE FILE 
	function saveFile() {
		var clicks = username + '\n' + 'filter clicks: ' + localStorage.getItem('${username}-filter_clicks') + '\n' + 
					 'feature clicks: ' + localStorage.getItem('${username}-feature_recommendation_clicks') + ' || use feature clicks: ' + localStorage.getItem('${username}-use_feature_recommendation_clicks') +
					 ' || explain feature clicks: ' + localStorage.getItem('${username}-explain_feature_recommendation_clicks') + '\n' + 
					 'item clicks: ' + localStorage.getItem('${username}-item_recommendation_clicks') + ' || more explanation clicks: ' + localStorage.getItem('${username}-more_explanation_clicks');
		saveData(clicks, "${username}-clicks.txt");
		localStorage.removeItem('${username}-filter_clicks');
		localStorage.removeItem('${username}-feature_recommendation_clicks');
		localStorage.removeItem('${username}-item_recommendation_clicks');
		localStorage.removeItem('${username}-more_explanation_clicks');
		localStorage.removeItem('${username}-use_feature_recommendation_clicks');
		localStorage.removeItem('${username}-explain_feature_recommendation_clicks');
	}

	window.onload = main;
	
	/* LOADING SCREEN */
	$(function(){
	    $('body').on('click', '#show-recommendation-button', function() {
	        $('body').append(
	            '<div id="overlay">' +
	            '<img id="loading" src="css/loading.gif">' +
	            '</div>'
	        );	        
	        setTimeout(function(){
	          $('#overlay').remove();
	        }, 30000); // 30 seconds
	    });
	})
	
	$(function(){
	    $('body').on('click', '#explain-feature-recommendation', function() {
	        $('body').append(
	            '<div id="overlay">' +	          
	            '<img id="loading" src="css/loading.gif">' +
	            '</div>'
	        );
	        
	        setTimeout(function(){
	          $('#overlay').remove();
	          $('#chart-recommended-feature').show();
	        }, 3000); // 3 seconds
	    });
	})
	
	$(function(){
	    $('body').on('click', '.more-explanation', function() {
	        $('body').append(
	            '<div id="overlay">' +	          
	            '<img id="loading" src="css/loading.gif">' +
	            '</div>'
	        );
	        
	        setTimeout(function(){
	          $('#overlay').remove();
	          $('#chart-more-explanation').show();
	        }, 3500); // 3 seconds
	    });
	})	 	 	
</script>

<script type="text/javascript" charset="ISO-8859-1" src="js/home.js"></script>

<title>Home</title>

</head>

<body class="w3-light-grey">

<div class="w3-panel w3-metro-darken banner"> 
	<form action="./DeleteRecentlyAddedServlet" method="post"><button id="logout-button" type="submit" class="w3-large w3-button w3-right w3-hover-red" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative;top:3px; right:6px;">exit_to_app</i>log out</button></form>
	<form action="./shopping_basket" method="post"><button type="submit" class="w3-bar-item w3-button w3-large w3-right w3-hover-green" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative; top:3px; right:6px;">shopping_cart</i>shopping basket</button></form>
	<form action="./user_profile" method="post"><button type="submit" class="w3-bar-item w3-button w3-large w3-right w3-hover-light-grey" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative; top:3px; right:6px;">account_circle</i>user profile</button></form> 
	<!-- <form action="./home"><button id="home-button" class="w3-button w3-large w3-hover-light-grey" style="position:relative;top:0.2px"><i class="material-icons" aria-hidden="true" style="position:relative;top:3px; right:6px;">home</i>home</button></form> -->
</div>

<div class="w3-container whole-page">
	<div id="filter" class="w3-row-padding">
		<div class="w3-col" style="width:20%; padding: 16px 4px 0 20px">
			<div class="w3-left" style="padding: 8px; width:100%" >
			<form class="w3-center" action="./GetRecommendationServlet" method="post">
				<input type="hidden" name="user-id" value="${activeAppUser.id}">
				<button id="show-recommendation-button" type="submit" class="w3-button w3-block w3-small w3-indigo w3-card w3-round-small" style="font-weight:bold; text-align:center; display:none;">SHOW RECOMMENDATIONS</button>
			</form>
			</div>
		</div>
			
		<div class="w3-rest" style="font-size: 13px; padding: 20px 12px 0 18px">
			<c:forEach items="${au}" var="shows">
				<c:choose>
					<c:when test="${not empty shows.value}">
					<form action="./delete" method="post" >				
					<span class="w3-tag w3-blue-grey w3-card w3-round" style="padding: 2px 0px 2px 8px; margin: 4px; text-align: left; position: relative; float:left ">
						<input type="hidden" id="feature-to-delete" name="feature-to-delete"value="${shows.id}">
						<span>${shows.name} : ${shows.value}, </span>
						<span class="stars" data-rating="${shows.rating}" data-num-stars="5"></span>
						<script>
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
   						</script>
						<span><button type="submit" class="close-tag w3-right w3-text-white remove-feature">
							<i class="material-icons" aria-hidden="true" style="font-size: 14px; text-shadow: 0 1px 1px rgba(0,0,0,.5);">delete_forever</i>
						</button></span>
					</span>
					</form>					
					</c:when>	
				
					<c:otherwise>
					<form action="./delete" method="post" >
					<span class="w3-tag w3-blue-grey w3-card w3-round" style=" padding: 2px 0px 2px 8px; margin: 4px; text-align: left; position: relative; float:left">
						<input type="hidden" id="feature-to-delete" name="feature-to-delete" value="${shows.id}">
						<span>${shows.name} : ${shows.min} - ${shows.max}, </span>
						<span class="stars" data-rating="${shows.rating}" data-num-stars="5"></span>
						<script>
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
   						</script>	
						<span><button type="submit" class="close-tag w3-right w3-text-white remove-feature">
							<i class="material-icons" aria-hidden="true" style="font-size: 14px; text-shadow: 0 1px 1px rgba(0,0,0,.5);">delete_forever</i>
						</button></span>				
					</span>
					</form>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>		
	</div>
	<div id="confirm-remove" class="w3-tag w3-round-small w3-metro-darken"> Successfully Removed </div>
 		<script type="text/javascript">
			$('.remove-feature').click(function(){
				click_filter();
				localStorage.setItem('confirm-remove','true');
				$('#confirm-remove').fadeIn(500);
			});
									
			if(localStorage.getItem('confirm-remove') == 'true') {
				$('#confirm-remove').show();
		        setTimeout(function(){
		        	$('#confirm-remove').fadeOut(500);
		        	localStorage.removeItem('confirm-remove');
		        }, 2500); // 2.5 seconds     
			} else {
				$('#confirm-remove').hide();
			}										
		</script>
	<div class="w3-row-padding" style="padding: 16px 0;">
		<div class="w3-col" style="width:20%; padding: 0px 8px 0 32px">
			<h3> Feature filter </h3>
			<div class="w3-row" id="alert-feature" style=" display:none;">
				<div class="w3-col" style="width:10%; padding:4px 0; font-size:14px;">
					<i class="material-icons w3-text-deep-orange w3-right" style="vertical-align: middle;">warning</i>
				</div>
				<div style="width:90%; padding:0 0 16px 8px; font-size:14px; font-style:italic;" class="w3-col w3-text-red">Please select and rate at least one value to create your user profile.</div>
			</div>	
			<div class="w3-dropdown" style="width:100%; padding: 8px 0px 5px 0px;">
				<button onclick="showFeature(); click_filter();" id="show-feature-btn" class="add-feature w3-button w3-small w3-block w3-card-4 w3-deep-orange w3-round-small" style="font-weight:bold"> + ADD FEATURE </button>
				<div id="false-feature" class="false-feature w3-dropdown-content w3-border w3-card-4 w3-round-small" style="position: relative; width: 100%; overflow: auto; height:440px;z-index:9">
				<c:forEach items="${listFalseFeature}" var="falseFeature">
					<form action="./ShowFeatureServlet" method="post" target="_parent">			
						<input type="hidden" name="id-feature-to-show" value="${falseFeature.id}">
						<input type="hidden" name="user-id-feature-to-show" value="${activeAppUser.id}">
					<button type="submit" class="w3-button w3-white w3-small w3-block" onclick="click_filter();">${falseFeature.feature_name}</button>
					</form>	
   				</c:forEach>
				</div>
			</div>
			
			<c:forEach items="${listFeatureWithValueNoValue}" var="featureWithValueNoValue">
					
			<div class="w3-card w3-container w3-white w3-round-small" style="padding: 0 16px 8px; margin: 8px 0px; position: relative;">			
				<form name="hide-feature" action="./HideFeatureServlet" method="post">
					<h6>${featureWithValueNoValue.feature_name}
					<input type="hidden" name="user-id-feature-to-hide" value="${activeAppUser.id}">
					<input type="hidden" name="feature-to-hide" value="${featureWithValueNoValue.field_name}"></h6>				
					<span><button type="submit" class="close" onclick="click_filter();"><i class="material-icons" aria-hidden="true" style="font-size: 18px;">close</i></button></span>
				</form>							
				
				<form id="feature-rating-field" action="./AddValueServlet" method="post" style="font-size: 11px">
					<c:choose>
						<c:when test="${featureWithValueNoValue.feature_type eq 'TEXT'}">
							<input type="hidden" name="selected-text-field-name" value="${featureWithValueNoValue.field_name}">
							<select id="${featureWithValueNoValue.field_name}-select" onclick="click_filter();" style="font-size:11px;" id="text-value-selector" class="w3-select w3-border value" name="selected-text-value">
								<option style="font-size:11px;"value="" disabled selected>please select value</option>
								<c:forEach items="${featureWithValueNoValue.distinct_value}" var="distinct">
									<option style="font-size:11px;" onclick="click_filter();">${distinct}</option>
								</c:forEach>
							</select>
						</c:when>
						<c:when test="${featureWithValueNoValue.feature_type eq 'CUSTOM_RANGE'}">
							<input type="hidden" name="selected-number-field-name" value="${featureWithValueNoValue.field_name}">
 							<script>
							    $(function() {
							    	var minvalue = ${featureWithValueNoValue.min_value};
							    	var maxvalue = ${featureWithValueNoValue.max_value};
							    	var sliderRange = $('div[id^="slider${featureWithValueNoValue.field_name}"]').slider({
							    		range: true,
							            min: minvalue,
							            max: maxvalue,
							            values: [0, maxvalue],
							            slide: function(event, ui) {
							                if ( ui.values[0] >= ui.values[1] ) {
							                    return false;
							                } else {
							                	$('input[id^="min${featureWithValueNoValue.field_name}"]').val(ui.values[0]);
							                	$('input[id^="max${featureWithValueNoValue.field_name}"]').val(ui.values[1]);
/*  							                    for (var i = 0; i <= ui.values.length; ++i) {
							                        $("input.sliderValue[data-index=" + i + "]").val(ui.values[i]);
							                    } */ 
							                }
							            },
 	 						            change: function(event, ui) {
						                	$('input[id^="min${featureWithValueNoValue.field_name}"]').val(ui.values[0]);
						                	$('input[id^="max${featureWithValueNoValue.field_name}"]').val(ui.values[1]);
							            }  
							        });      
							    });
							</script>						
						    <div id="${featureWithValueNoValue.field_name}-slidebar" style="display:block; padding: 8px 4px 0">
						    	<div id="slider${featureWithValueNoValue.field_name}"></div>
						    </div>
						    <br>
						    <input style="font-size:14px;" name="min" id="min${featureWithValueNoValue.field_name}" readonly type="text" class="sliderValue min_val" data-index="0" value="${featureWithValueNoValue.min_value}"/>
						    <input style="font-size:14px;" name="max" id="max${featureWithValueNoValue.field_name}" readonly type="text" class="sliderValue max_val" data-index="1" value="${featureWithValueNoValue.max_value}"/>													
						</c:when>
					</c:choose>
					<script>
						$('div[id^="${featureWithValueNoValue.field_name}-slidebar"]').click(function(){
							click_filter();
							$('div[id^="${featureWithValueNoValue.field_name}-star-and-warning"]').show();
							$('div[id^="${featureWithValueNoValue.field_name}-feature-used"]').hide();
							localStorage.removeItem('${featureWithValueNoValue.field_name}-use-feature');
						});
						
						$('div[id^="slider${featureWithValueNoValue.field_name}"]').click(function(){
							click_filter();
							$('div[id^="${featureWithValueNoValue.field_name}-star-and-warning"]').show();
							$('div[id^="${featureWithValueNoValue.field_name}-feature-used"]').hide();
							localStorage.removeItem('${featureWithValueNoValue.field_name}-use-feature');
						});
						
						$('select[id^="${featureWithValueNoValue.field_name}-select"]').click(function(){
							click_filter();
							$('div[id^="${featureWithValueNoValue.field_name}-star-and-warning"]').show();
							$('div[id^="${featureWithValueNoValue.field_name}-feature-used"]').hide();
							localStorage.removeItem('${featureWithValueNoValue.field_name}-use-feature');
						});
						
					</script>
					<div id="${featureWithValueNoValue.field_name}-feature-used" style="display:none; width:100%; padding:4px 0 0 0; font-size:13px; font-style:italic" class="w3-col w3-text-deep-orange">
						You can now select and rate the value you prefer to add it to your user profile.
					</div>
						
					<div id="${featureWithValueNoValue.field_name}-star-and-warning" style="padding: 8px 0 16px; position: relative; bottom: 8px; display:none">
					<div class="w3-row">
						<div class="w3-col" style="width:10%; padding:16px 0; font-size:14px;"><i class="material-icons w3-text-red w3-right" style="vertical-align: middle;">warning</i></div>
						<div style="width:90%; padding:8px 0 8px 4px; font-size:13px; font-style:italic" class="w3-col w3-text-red">
							Please rate this value if you want to add it to your user profile.
						</div>
					</div>
					<div class="rating add-new-feature">
						<input type="radio" name="rating-value" id="${featureWithValueNoValue.field_name}5" value="5" onClick="this.form.submit(); click_filter();"><label for="${featureWithValueNoValue.field_name}5"></label>
						<input type="radio" name="rating-value" id="${featureWithValueNoValue.field_name}4" value="4" onClick="this.form.submit(); click_filter();"><label for="${featureWithValueNoValue.field_name}4"></label>
						<input type="radio" name="rating-value" id="${featureWithValueNoValue.field_name}3" value="3" onClick="this.form.submit(); click_filter();"><label for="${featureWithValueNoValue.field_name}3"></label>
						<input type="radio" name="rating-value" id="${featureWithValueNoValue.field_name}2" value="2" onClick="this.form.submit(); click_filter();"><label for="${featureWithValueNoValue.field_name}2"></label>
						<input type="radio" name="rating-value" id="${featureWithValueNoValue.field_name}1" value="1" onClick="this.form.submit(); click_filter();"><label for="${featureWithValueNoValue.field_name}1"></label>
					</div>
					
					</div>											
				</form>
			</div>
			<div id="confirm-add" class="w3-tag w3-round-small w3-metro-darken"> Successfully added </div>
		 		<script type="text/javascript">
					$('.add-new-feature').click(function(){
						localStorage.setItem('confirm-add','true');
						$('#confirm-add').fadeIn(500);
					});
											
					if(localStorage.getItem('confirm-add') == 'true') {
						$('#confirm-add').show();
				        setTimeout(function(){
				        	$('#confirm-add').fadeOut(500);
				        	localStorage.removeItem('confirm-add');
				        }, 2500); // 2.5 seconds     
					} else {
						$('#confirm-add').hide();
					}										
				</script>
			</c:forEach>			
			
			<c:forEach items="${listFeatureWithValue}" var="featureWithValue">
			<div class="w3-card w3-container w3-white w3-round-small" style="padding: 0 16px 8px; margin: 8px 0px; position: relative;">
				<form name="hide-feature" action="./HideFeatureServlet" method="post">
					<h6>${featureWithValue.feature_name}
					<input type="hidden" name="user-id-feature-to-hide" value="${activeAppUser.id}">
					<input type="hidden" name="feature-to-hide" value="${featureWithValue.field_name}">				
					<button type="submit" class="close" onclick="click_filter();"><i class="material-icons" aria-hidden="true" style="font-size: 18px;">close</i></button></h6>
				</form>
				
				<form id="feature-rating-field" action="./AddValueServlet" method="post" style="font-size: 12px">
					<c:choose>
						<c:when test="${featureWithValue.feature_type eq 'TEXT'}">
							<input type="hidden" name="selected-text-field-name" value="${featureWithValue.field_name}">
							<select onclick="click_filter(); document.getElementById('${featureWithValue.field_name}-star-and-warning').style.display='block';" id="text-value-selector" class="w3-select w3-border value" name="selected-text-value">
								<option value="" disabled selected>please select value</option>
								<c:forEach items="${featureWithValue.distinct_value}" var="distinct">
									<option style="font-size:11px;" onclick="click_filter();">${distinct}</option>
								</c:forEach>
							</select>
						</c:when>
						<c:when test="${featureWithValue.feature_type eq 'CUSTOM_RANGE'}">
							<input type="hidden" name="selected-number-field-name" value="${featureWithValue.field_name}">
 							<script>
							    $(function() {
							    	var minvalue = ${featureWithValue.min_value};
							    	var maxvalue = ${featureWithValue.max_value};
							    	var sliderRange = $('div[id^="slider${featureWithValue.field_name}"]').slider({
							    		range: true,
							            min: minvalue,
							            max: maxvalue,
							            values: [0, maxvalue],
							            slide: function(event, ui) {
							                if ( ui.values[0] >= ui.values[1] ) {
							                    return false;
							                } else {
							                	$('input[id^="min${featureWithValue.field_name}"]').val(ui.values[0]);
							                	$('input[id^="max${featureWithValue.field_name}"]').val(ui.values[1]);
/*  							                    for (var i = 0; i <= ui.values.length; ++i) {
							                        $("input.sliderValue[data-index=" + i + "]").val(ui.values[i]);
							                    } */ 
							                }
							            },
 	 						            change: function(event, ui) {
						                	$('input[id^="min${featureWithValue.field_name}"]').val(ui.values[0]);
						                	$('input[id^="max${featureWithValue.field_name}"]').val(ui.values[1]);
							            }  
							        });      
							    });
							</script>						
						    <div style="padding: 8px 4px 0" onclick="click_filter(); document.getElementById('${featureWithValue.field_name}-star-and-warning').style.display='block';">
						    	<div onclick="click_filter(); document.getElementById('${featureWithValue.field_name}-star-and-warning').style.display='block';" id="slider${featureWithValue.field_name}"></div>
						    </div>
						    <br />
						    <input style="font-size:14px;" name="min" id="min${featureWithValue.field_name}" readonly type="text" class="sliderValue min_val" data-index="0" value="${featureWithValue.min_value}"/>
						    <input style="font-size:14px;" name="max" id="max${featureWithValue.field_name}" readonly type="text" class="sliderValue max_val" data-index="1" value="${featureWithValue.max_value}"/>													
						</c:when>
					</c:choose>	

					
					<div id="${featureWithValue.field_name}-star-and-warning" style="padding: 8px 0 16px; position: relative; bottom: 8px; display:none">
						<div class="w3-row">
							<div class="w3-col" style="width:10%; padding:16px 0; font-size:14px;"><i class="material-icons w3-text-deep-orange w3-right" style="vertical-align: middle;">warning</i></div>
							<div style="width:90%; padding:8px 0 8px 4px; font-size:13px; font-style:italic" class="w3-col w3-text-red">Please rate this value if you want to add it to your user profile.</div>
						</div>
						<div class="rating update-feature">
							<input type="radio" name="rating-value" id="${featureWithValue.field_name}5" value="5" onClick="this.form.submit(); click_filter();"><label for="${featureWithValue.field_name}5"></label>
							<input type="radio" name="rating-value" id="${featureWithValue.field_name}4" value="4" onClick="this.form.submit(); click_filter();"><label for="${featureWithValue.field_name}4"></label>
							<input type="radio" name="rating-value" id="${featureWithValue.field_name}3" value="3" onClick="this.form.submit(); click_filter();"><label for="${featureWithValue.field_name}3"></label>
							<input type="radio" name="rating-value" id="${featureWithValue.field_name}2" value="2" onClick="this.form.submit(); click_filter();"><label for="${featureWithValue.field_name}2"></label>
							<input type="radio" name="rating-value" id="${featureWithValue.field_name}1" value="1" onClick="this.form.submit(); click_filter();"><label for="${featureWithValue.field_name}1"></label>
						</div>
					</div>											
				</form>
			</div>
			<div id="confirm-update" class="w3-tag w3-round-small w3-metro-darken"> Successfully updated </div>
		 		<script type="text/javascript">
					$('.update-feature').click(function(){
						localStorage.setItem('confirm-update','true');
						$('#confirm-update').fadeIn(500);
					});
											
					if(localStorage.getItem('confirm-update') == 'true') {
						$('#confirm-update').show();
				        setTimeout(function(){
				        	$('#confirm-update').fadeOut(500);
				        	localStorage.removeItem('confirm-update');
				        }, 2500); // 2.5 seconds     
					} else {
						$('#confirm-update').hide();
					}										
				</script>			
			</c:forEach>
		</div>		
		<div class="w3-rest">
			<div id="top-rated" class="top-rated" style="padding:0px 24px 0px 16px; font-size: 13px; display:none">
				<div style="padding: 0px 8px;"><h3>Top rated cameras</h3></div>					
				<div class="w3-row">
					<c:forEach items="${listTopRatedCamera}" var="topRatedCamera">
						<div class="w3-col" style="width:20%; padding: 0px 8px">
						<div class="w3-card w3-center w3-white w3-round-small" style="padding: 8px 0px; margin:8px 0px; height:180px; position:relative;">
							<form action="./GetItemProfileServlet" method="post" target="item-profile">
								<input type="hidden" name="item-id" value="${topRatedCamera.id}">
								<button type="submit" onclick="document.getElementById('item-profile').style.display='block'" class="btn-link w3-text-black" style="font-weight:bold">
									${topRatedCamera.product_name}
									<div class="item-image" style="padding:16px 0 0 0;position:absolute; margin: 0 auto; left: 0"><img src="${topRatedCamera.img_src}" alt="${topRatedCamera.product_name}" style="width:65%;"></div>
								</button>
							</form>
							<div id="item-profile" class="w3-modal w3-animate-opacity">
								<div class="w3-modal-content w3-display-middle" style="width:960px; overflow:hidden;">
									<div class="w3-container">	
										<button onclick="document.getElementById('item-profile').style.display='none'" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
										<iframe name="item-profile" style="border:none; height:480px; width:976px"></iframe>
									</div>
								</div>
							</div>
							<br>
							
							<h6 class="w3-bottom" style="position:absolute;"><span class="w3-text-red">${topRatedCamera.rating}</span> / 5 </h6>
						</div>
						</div>
					</c:forEach>
				</div>
			</div>		
		
			<div id="recommended-item" style="display:none; padding: 0px 24px 0px 16px;">
				<div>
					<div style="padding: 0px 8px 0px 8px">
						<form action="./Get20SimilarUsersExplanationServlet" method="post" target="20-similar-user-explanation">
							<h3>Recommendations based on your <button type="submit" onclick="document.getElementById('20-similar-user-explanation').style.display='block';  click_feature_recommendation();" class="btn-link"> 20 most similar users </button></h3>
							<input type="hidden" name="user-id" value="${activeAppUser.id}">
							<h3>Feature recommendations for you</h3>
						</form>
					
						<div id="20-similar-user-explanation" class="w3-modal w3-animate-opacity"style="padding:90px">
							<div class="w3-modal-content w3-display-middle" style="height:600px; width:950px;">
								<div class="container">
									<span>
										<button onclick="document.getElementById('20-similar-user-explanation').style.display='none';" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
									</span>
									<iframe name="20-similar-user-explanation" style="border:none; height:600px; width:966px"></iframe>
								</div>
							</div>
				 		</div>
					</div>
					<div class="w3-row">
						<c:forEach items="${listRecommendedFeature}" var="feat">
							<div class="w3-col" style="width:25%; padding: 0px 8px">
							<div class="w3-card w3-center w3-white w3-display-container w3-round-small" style="padding: 4px 0px; margin:8px 0px; height:180px;position:relative">
								<div>
									<form action="./ShowFeatureServlet" method="post" target="_parent">			
										<input type="hidden" name="user-id-feature-to-show" value="${activeAppUser.id}">
										<input type="hidden" name="id-feature-to-show" value="${feat.id}">
										<h5 class="w3-text-deep-orange" style="font-weight:bold">${feat.feature_name}</h5>							
									</form>
								</div>
								
								<div><h6 style="font-weight:bold;">${feat.feature_value}</h6></div>
								
								<div style="position:absolute; bottom:0">
									<div class="w3-row" style="padding:16px;">
										<div class="w3-col" style="width:60%; padding:0 4px">							
											<form action="./GetChartFeatureRecommendationServlet" method="post" target="chart-recommended-feature">									
												<input type="hidden" name="recommended-field-name" value="${feat.field_name}">
												<button id="explain-feature-recommendation" class="w3-button w3-card w3-tiny w3-round-small" style="background: #63b663; color: white; padding:8px" type="submit" onclick="click_feature_recommendation(); click_explain_feature_recommendation();"> explain recommendation </button>	
											</form>									
											
										</div>	
										<div class="w3-col" style="width:40%; padding:0 4px">
											<form action="./ShowFeatureServlet" method="post">
												<input type="hidden" name="user-id-feature-to-show" value="${activeAppUser.id}">
												<input type="hidden" name="id-feature-to-show" value="${feat.id}">
												<button id="${feat.field_name}-use-feature" class="w3-button w3-card w3-tiny w3-round-small" style="background: #63b663; color: white; padding:8px" type="submit"> use this feature </button>
											</form>
										</div>
										
										<script>
											$('button[id^="${feat.field_name}-use-feature"]').click(function(){
												localStorage.setItem('${feat.field_name}-use-feature', 'true');
												click_feature_recommendation();
												click_use_feature_recommendation();
											});
											
											if (localStorage.getItem('${feat.field_name}-use-feature') == 'true') {
												$('div[id^="${feat.field_name}-feature-used"]').show();
											} else {
												$('div[id^="${feat.field_name}-feature-used"]').hide();
											}
										</script>
																								
									</div>
								</div>
								
								<div id="chart-recommended-feature" class="w3-modal w3-animate-opacity"style="padding:90px">
									<div class="w3-modal-content w3-display-middle" style="height:600px; width:950px;">
										<div class="container">
											<span>
												<button onclick="document.getElementById('chart-recommended-feature').style.display='none';" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
											</span>
											<iframe name="chart-recommended-feature" style="border:none; height:600px; width:966px"></iframe>
										</div>
									</div>
								</div>
							</div>
							</div>
						</c:forEach>	
					</div>
				</div>
				
				<div style="padding: 0 8px">
					<div style="padding: 16px 0 8px 0">
						<form action="./GetChartAvgUserRatingServlet" method="post" target="chart-avg-user-rating">
							<input type="hidden" name="user-id" value="${activeAppUser.id}">
							<h3>Item recommendations for you</h3>
						</form>
					
						<div id="chart1" class="w3-modal w3-animate-opacity"style="padding:90px">
							<div class="w3-modal-content w3-display-middle" style="height:600px; width:950px;">
								<div class="container">
									<span>
										<button onclick="document.getElementById('chart1').style.display='none';" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
									</span>
									<iframe name="chart-avg-user-rating" style="border:none; height:600px; width:966px"></iframe>
								</div>
							</div>
				 		</div>
					</div>
					
					<table id="recommended-item-perfect-table" class="w3-table w3-white w3-bordered w3-centered w3-card w3-round-small">
						<col width="15%">
						<col width="25%">
						<col width="10%">
						<col width="15%">
						<col width="20%">
						<col width="6%">
						<col width="9%">
						<tr class="w3-deep-orange" style="height: 56px;">
							<th></th>
							<th style="vertical-align: middle">Product</th>
							<th style="vertical-align: middle">Price</th>
							<th style="vertical-align: middle">Pixel number</th>
							<th style="vertical-align: middle">Explanation</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach items="${listRecommendedItemPerfect}" var="listRecommendedItemPerfect">
							<tr style="font-size: 14px;">
								<td style="vertical-align: middle"><img src="${listRecommendedItemPerfect.img_src}" alt="(${listRecommendedItemPerfect.product_name})" style="width: 50%; font-size: 11px;"></td>
								<td style="vertical-align: middle">								
									<form action="./GetItemProfileServlet" method="post" target="item-profile-perfect">
										<input type="hidden" name="item-id" value="${listRecommendedItemPerfect.id}">
										<button type="submit" onclick="document.getElementById('item-profile-perfect').style.display='block'" class="btn-link w3-text-black" style="font-weight:bold">
											<h6 style="font-weight:bold;">${listRecommendedItemPerfect.product_name}</h6>
										</button>
									</form>
									<div id="item-profile-perfect" class="w3-modal w3-animate-opacity">
										<div class="w3-modal-content w3-display-middle" style="width:960px; overflow:hidden;">
											<div class="w3-container">	
												<button onclick="document.getElementById('item-profile-perfect').style.display='none'" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
												<iframe name="item-profile-perfect" style="border:none; height:480px; width:976px"></iframe>
											</div>
										</div>
									</div>									
								</td>
								<td style="vertical-align: middle">${listRecommendedItemPerfect.price} EUR</td>
								<td style="vertical-align: middle">${listRecommendedItemPerfect.pixel_number} MP</td>
								<td style="vertical-align: middle">									
									<button onclick="click_item_recommendation();" id="${auid}-explain-${listRecommendedItemPerfect.id}" class="explain-button w3-button w3-small w3-card w3-round-small w3-block" style="background: #63b663; color: white;"> explain recommendation <i style="vertical-align: middle" class="material-icons">arrow_drop_down</i></button>																											
									<button onclick="click_item_recommendation();" id="${auid}-close-${listRecommendedItemPerfect.id}" class="close-explain-button w3-button w3-small w3-card w3-round-small w3-block" style="background: #63b663; color: white; display:none;"> hide explanation <i style="vertical-align: middle" class="material-icons">arrow_drop_up</i></button>
								
									<script>
										//SHOW PRO-CON
 										$('button[id^="${auid}-explain-${listRecommendedItemPerfect.id}"]').click(function(){
 											localStorage.setItem('${auid}-procon-${listRecommendedItemPerfect.id}', 'true');
 											$('tr[id^="${auid}-more-about-this-item-${listRecommendedItemPerfect.id}"]').show();
 											$('tr[id^="${auid}-more-about-this-item-pro-${listRecommendedItemPerfect.id}"]').show();
 											$('tr[id^="${auid}-more-about-this-item-con-${listRecommendedItemPerfect.id}"]').show();
											$('button[id^="${auid}-explain-${listRecommendedItemPerfect.id}"]').hide();
 											$('button[id^="${auid}-close-${listRecommendedItemPerfect.id}"]').show();
    									});	
										
 										$('button[id^="${auid}-close-${listRecommendedItemPerfect.id}"]').click(function(){ 
 											localStorage.removeItem('${auid}-procon-${listRecommendedItemPerfect.id}');
											$('tr[id^="${auid}-more-about-this-item-${listRecommendedItemPerfect.id}"]').hide();
 											$('tr[id^="${auid}-more-about-this-item-pro-${listRecommendedItemPerfect.id}"]').hide();
 											$('tr[id^="${auid}-more-about-this-item-con-${listRecommendedItemPerfect.id}"]').hide();
 											$('button[id^="${auid}-explain-${listRecommendedItemPerfect.id}"]').show();
											$('button[id^="${auid}-close-${listRecommendedItemPerfect.id}"]').hide();
    									});								
									</script>
								</td>
								<td style="vertical-align: middle">
									<form class="tooltip" action="./DeleteRecommendedItemServlet" method="post">
										<input name="item-id" type="hidden" value="${listRecommendedItemPerfect.id}">
										<button onclick="click_item_recommendation();" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit"><i class="material-icons" style="font-size: 22px">delete_outline</i></button>
										<span class="tooltiptext w3-round-small">Remove this recommendation</span>
									</form>
								</td>
								<td style="vertical-align: middle">
									<form class="tooltip" action="./AddToBasketServlet" method="post">
										<input name="item-id" type="hidden" value="${listRecommendedItemPerfect.id}">
										<button id="add-cart-${listRecommendedItemPerfect.id}" onclick="click_item_recommendation();" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit">
											<i class="material-icons" style="color:#63b663 ;font-size: 22px">add_shopping_cart</i>
										</button>
										<span class="tooltiptext w3-round-small">Add to Basket</span>
									</form>
									<form class="tooltip" action="RemoveFromBasketServlet" method="post">
										<input name="item-id" type="hidden" value="${listRecommendedItemPerfect.id}">
										<button id="remove-cart-${listRecommendedItemPerfect.id}" onclick="click_item_recommendation();" style="display:none" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit">
											<i class="material-icons" style="color:red; font-size: 22px">remove_shopping_cart</i>
										</button>
										<span class="tooltiptext w3-round-small">Remove from Basket</span>
									</form>
									<script>
										$('button[id^="add-cart-${listRecommendedItemPerfect.id}"]').click(function(){
											localStorage.setItem('${auid}-cart-${listRecommendedItemPerfect.id}', 'true');
										});	
									
										$('button[id^="remove-cart-${listRecommendedItemPerfect.id}"]').click(function(){
											localStorage.removeItem('${auid}-cart-${listRecommendedItemPerfect.id}');
										});
										
										if (localStorage.getItem('${auid}-cart-${listRecommendedItemPerfect.id}') == 'true') {
											$('button[id^="add-cart-${listRecommendedItemPerfect.id}"]').hide();
											$('button[id^="remove-cart-${listRecommendedItemPerfect.id}"]').show();
										} else {
											$('button[id^="remove-cart-${listRecommendedItemPerfect.id}"]').hide();
											$('button[id^="add-cart-${listRecommendedItemPerfect.id}"]').show();
										}
										
									</script>
								</td>
							</tr>											
							<tr>
				
							</tr>							
							<tr id="${auid}-more-about-this-item-${listRecommendedItemPerfect.id}" style="background:#63b663; color:white; display:none">
								<th style="vertical-align: middle">Feature</th>
								<th style="vertical-align: middle">${listRecommendedItemPerfect.product_name}</th>
								<th style="vertical-align: middle">Your Preferences</th>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
							
							<c:forEach items="${listRecommendedItemPerfect.list_pro}" var="profeature">
							<tr id="${auid}-more-about-this-item-pro-${listRecommendedItemPerfect.id}" class="w3-sand" style="font-size: 13px; display:none">
								<td style="vertical-align: middle">${profeature.name}</td>
								<td style="vertical-align: middle">${profeature.text_value}</td>
								<td style="vertical-align: middle">${profeature.active_user_value}</td>
								<td style="vertical-align: middle"><i class="material-icons" aria-hidden="true" style="font-size: 24px; color: #63b663">check_circle</i></td>
								<td style="vertical-align: middle">
								<form action="./GetChartFeatureValueExplanationServlet" method="post" target="chart-more-explanation">
									<input type="hidden" name="selected-item-id" value="${listRecommendedItemPerfect.id}">
									<input type="hidden" name="selected-field-name" value="${profeature.field_name}">
									<button type="submit" onclick="click_item_recommendation(); click_more_explanation()" class="btn-link more-explanation">
										more explanation <i style="vertical-align: middle; font-size: 24px;" class="material-icons" aria-hidden="true">insert_chart</i>
									</button>
								</form>
								</td>
								<td></td>
								<td></td>
							</tr>							
							<div id="chart-more-explanation" class="w3-modal w3-animate-opacity"style="padding:90px">
								<div class="w3-modal-content w3-display-middle" style="height:600px; width:950px;">
									<div class="container">
										<span>
											<button onclick="document.getElementById('chart-more-explanation').style.display='none';" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
										</span>
										<iframe name="chart-more-explanation" style="border:none; height:600px; width:966px"></iframe>
									</div>
								</div>
							</div>
							</c:forEach>
							<script>
								if (localStorage.getItem('${auid}-procon-${listRecommendedItemPerfect.id}') == 'true') {
									$('tr[id^="${auid}-more-about-this-item-${listRecommendedItemPerfect.id}"]').show();
									$('tr[id^="${auid}-more-about-this-item-pro-${listRecommendedItemPerfect.id}"]').show();
									$('tr[id^="${auid}-more-about-this-item-con-${listRecommendedItemPerfect.id}"]').show();
									$('button[id^="${auid}-explain-${listRecommendedItemPerfect.id}"]').hide();
									$('button[id^="${auid}-close-${listRecommendedItemPerfect.id}"]').show();
								} else {
									$('tr[id^="${auid}-more-about-this-item-${listRecommendedItemPerfect.id}"]').hide();
									$('tr[id^="${auid}-more-about-this-item-pro-${listRecommendedItemPerfect.id}"]').hide();
									$('tr[id^="${auid}-more-about-this-item-con-${listRecommendedItemPerfect.id}"]').hide();
									$('button[id^="${auid}-explain-${listRecommendedItemPerfect.id}"]').show();
									$('button[id^="${auid}-close-${listRecommendedItemPerfect.id}"]').hide();
								}							
							</script>																					
						</c:forEach>
						<c:forEach items="${listRecommendedItem}" var="listRecommendedItem">
							<tr style="font-size: 14px;">
								<td style="vertical-align: middle"><img src="${listRecommendedItem.img_src}" alt="(${listRecommendedItem.product_name})" style="width: 50%; font-size: 11px;"></td>
								<td style="vertical-align: middle">
									<form action="./GetItemProfileServlet" method="post" target="item-profile-perfect">
										<input type="hidden" name="item-id" value="${listRecommendedItem.id}">
										<button type="submit" onclick="document.getElementById('item-profile-perfect').style.display='block'" class="btn-link w3-text-black" style="font-weight:bold">
											<h6 style="font-weight:bold;">${listRecommendedItem.product_name}</h6>
										</button>
									</form>
									<div id="item-profile-perfect" class="w3-modal w3-animate-opacity">
										<div class="w3-modal-content w3-display-middle" style="width:960px; overflow:hidden;">
											<div class="w3-container">	
												<button onclick="document.getElementById('item-profile-perfect').style.display='none'" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
												<iframe name="item-profile-perfect" style="border:none; height:480px; width:976px"></iframe>
											</div>
										</div>
									</div>
								</td>
								<td style="vertical-align: middle">${listRecommendedItem.price} EUR</td>
								<td style="vertical-align: middle">${listRecommendedItem.pixel_number} MP</td>
								<td style="vertical-align: middle">									
									<button onclick="click_item_recommendation();" id="${auid}-explain-${listRecommendedItem.id}" class="explain-button w3-button w3-small w3-card w3-round-small w3-block" style="background: #63b663; color: white;">explain recommendation <i style="vertical-align: middle" class="material-icons">arrow_drop_down</i></button>																											
									<button onclick="click_item_recommendation();" id="${auid}-close-${listRecommendedItem.id}" class="close-explain-button w3-button w3-small w3-card w3-round-small w3-block" style="background: #63b663; color: white; display:none;">hide explanation <i style="vertical-align: middle" class="material-icons">arrow_drop_up</i></button>
								
									<script>
										//SHOW PRO-CON
 										$('button[id^="${auid}-explain-${listRecommendedItem.id}"]').click(function(){
 											localStorage.setItem('${auid}-procon-${listRecommendedItem.id}', 'true');
 											$('tr[id^="${auid}-more-about-this-item-${listRecommendedItem.id}"]').show();
 											$('tr[id^="${auid}-more-about-this-item-pro-${listRecommendedItem.id}"]').show();
 											$('tr[id^="${auid}-more-about-this-item-con-${listRecommendedItem.id}"]').show();
											$('button[id^="${auid}-explain-${listRecommendedItem.id}"]').hide();
 											$('button[id^="${auid}-close-${listRecommendedItem.id}"]').show();
    									});	
										
 										$('button[id^="${auid}-close-${listRecommendedItem.id}"]').click(function(){ 
 											localStorage.removeItem('${auid}-procon-${listRecommendedItem.id}');
											$('tr[id^="${auid}-more-about-this-item-${listRecommendedItem.id}"]').hide();
 											$('tr[id^="${auid}-more-about-this-item-pro-${listRecommendedItem.id}"]').hide();
 											$('tr[id^="${auid}-more-about-this-item-con-${listRecommendedItem.id}"]').hide();
 											$('button[id^="${auid}-explain-${listRecommendedItem.id}"]').show();
											$('button[id^="${auid}-close-${listRecommendedItem.id}"]').hide();
    									});
									</script>
								</td>
								<td style="vertical-align: middle">
									<form class="tooltip" action="./DeleteRecommendedItemServlet" method="post">
										<input name="item-id" type="hidden" value="${listRecommendedItem.id}">
										<button onclick="click_item_recommendation();" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit"><i class="material-icons" style="font-size: 22px">delete_outline</i></button>
										<span class="tooltiptext w3-round-small">Remove this recommendation</span>
									</form>
								</td>
								<td style="vertical-align: middle">
									<form class="tooltip" action="./AddToBasketServlet" method="post">
										<input name="item-id" type="hidden" value="${listRecommendedItem.id}">
										<button id="add-cart-${listRecommendedItem.id}" onclick="click_item_recommendation();" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit">
											<i class="material-icons" style="color:#63b663 ;font-size: 22px">add_shopping_cart</i>
										</button>
										<span class="tooltiptext w3-round-small">Add to Basket</span>
									</form>
									<form class="tooltip" action="RemoveFromBasketServlet" method="post">
										<input name="item-id" type="hidden" value="${listRecommendedItem.id}">
										<button id="remove-cart-${listRecommendedItem.id}" onclick="click_item_recommendation();" style="display:none" class="w3-button w3-ripple w3-circle user-icon delete-rating-up" type="submit">
											<i class="material-icons" style="color:red; font-size: 22px">remove_shopping_cart</i>
										</button>
										<span class="tooltiptext w3-round-small">Remove from Basket</span>
									</form>
									<script>
										$('button[id^="add-cart-${listRecommendedItem.id}"]').click(function(){
											localStorage.setItem('${auid}-cart-${listRecommendedItem.id}', 'true');
										});	
									
										$('button[id^="remove-cart-${listRecommendedItem.id}"]').click(function(){
											localStorage.removeItem('${auid}-cart-${listRecommendedItem.id}');
										});
										
										if (localStorage.getItem('${auid}-cart-${listRecommendedItem.id}') == 'true') {
											$('button[id^="add-cart-${listRecommendedItem.id}"]').hide();
											$('button[id^="remove-cart-${listRecommendedItem.id}"]').show();
										} else {
											$('button[id^="remove-cart-${listRecommendedItem.id}"]').hide();
											$('button[id^="add-cart-${listRecommendedItem.id}"]').show();
										}
									</script>
								</td>
							</tr>

							<tr id="${auid}-more-about-this-item-${listRecommendedItem.id}" style="background:#63b663; color:white; display:none">
								<th style="vertical-align: middle">Feature</th>
								<th style="vertical-align: middle">${listRecommendedItem.product_name}</th>
								<th style="vertical-align: middle">Your Preferences</th>
								<th></th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
							
							<c:forEach items="${listRecommendedItem.list_pro}" var="profeature">
							<tr id="${auid}-more-about-this-item-pro-${listRecommendedItem.id}" class="w3-sand" style="font-size: 13px; display:none">
								<td style="vertical-align: middle">${profeature.name}</td>
								<td style="vertical-align: middle">${profeature.text_value}</td>
								<td style="vertical-align: middle">${profeature.active_user_value}</td>
								<td style="vertical-align: middle"><i class="material-icons" aria-hidden="true" style="font-size: 24px; color: #63b663">check_circle</i></td>
								<td style="vertical-align: middle">
								<form action="./GetChartFeatureValueExplanationServlet" method="post" target="chart-more-explanation">
									<input type="hidden" name="selected-item-id" value="${listRecommendedItem.id}">
									<input type="hidden" name="selected-field-name" value="${profeature.field_name}">
									<button type="submit" onclick="click_item_recommendation(); click_more_explanation();" class="btn-link more-explanation">
										more explanation <i style="vertical-align: middle; font-size: 24px;" class="material-icons" aria-hidden="true">insert_chart</i>
									</button>
								</form>
								</td>
								<td></td>
								<td></td>
							</tr>
							<div id="chart-more-explanation" class="w3-modal w3-animate-opacity"style="padding:90px">
								<div class="w3-modal-content w3-display-middle" style="height:600px; width:950px;">
									<div class="container">
										<span>
											<button onclick="document.getElementById('chart-more-explanation').style.display='none';" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
										</span>
										<iframe name="chart-more-explanation" style="border:none; height:600px; width:966px"></iframe>
									</div>
								</div>
							</div>
							</c:forEach>
							
							<c:forEach items="${listRecommendedItem.list_con}" var="confeature">
							<tr id="${auid}-more-about-this-item-con-${listRecommendedItem.id}" class="w3-sand" style="font-size: 13px; display:none">
								<td style="vertical-align: middle">${confeature.name}</td>
								<td style="vertical-align: middle">${confeature.text_value}</td>
								<td style="vertical-align: middle">${confeature.active_user_value}</td>
								<td style="vertical-align: middle"><i class="material-icons" aria-hidden="true" style="font-size: 24px; color: #e94b3c">cancel</i></td>
								<td style="vertical-align: middle">
								<form action="./GetChartFeatureValueExplanationServlet" method="post" target="chart-more-explanation">
									<input type="hidden" name="selected-item-id" value="${listRecommendedItem.id}">
									<input type="hidden" name="selected-field-name" value="${confeature.field_name}">
									<button type="submit" onclick="click_item_recommendation(); click_more_explanation();" class="btn-link more-explanation">
										more explanation <i style="vertical-align: middle; font-size: 24px;" class="material-icons" aria-hidden="true">insert_chart</i>
									</button>
								</form>
								</td>
								<td></td>
								<td></td>
							</tr>
							<div id="chart-more-explanation" class="w3-modal w3-animate-opacity"style="padding:90px">
								<div class="w3-modal-content w3-display-middle" style="height:600px; width:950px;">
									<div class="container">
										<span>
											<button onclick="document.getElementById('chart-more-explanation').style.display='none';" class="close"><i class="material-icons" aria-hidden="true" style="font-size: 20px;">close</i></button>
										</span>
										<iframe name="chart-more-explanation" style="border:none; height:600px; width:966px"></iframe>
									</div>
								</div>
							</div>							
							</c:forEach>
							<script>
								if (localStorage.getItem('${auid}-procon-${listRecommendedItem.id}') == 'true') {
									$('tr[id^="${auid}-more-about-this-item-${listRecommendedItem.id}"]').show();
									$('tr[id^="${auid}-more-about-this-item-pro-${listRecommendedItem.id}"]').show();
									$('tr[id^="${auid}-more-about-this-item-con-${listRecommendedItem.id}"]').show();
									$('button[id^="${auid}-explain-${listRecommendedItem.id}"]').hide();
									$('button[id^="${auid}-close-${listRecommendedItem.id}"]').show();
								} else {
									$('tr[id^="${auid}-more-about-this-item-${listRecommendedItem.id}"]').hide();
									$('tr[id^="${auid}-more-about-this-item-pro-${listRecommendedItem.id}"]').hide();
									$('tr[id^="${auid}-more-about-this-item-con-${listRecommendedItem.id}"]').hide();
									$('button[id^="${auid}-explain-${listRecommendedItem.id}"]').show();
									$('button[id^="${auid}-close-${listRecommendedItem.id}"]').hide();
								}							
							</script>							
						</c:forEach>
					</table>					
				</div>				
			</div>			
		</div>
	</div>
</div>
</body>
</html>