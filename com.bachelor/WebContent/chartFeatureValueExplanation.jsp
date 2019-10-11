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

<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-camo.css">

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/home.css">
<script>
	var feature_type = ${featuretype} ;
	var feature_name = ${featurename} ;
	if (feature_type == "TEXT") {
		 var list_data = ${listdata} ;
		 var list_value_text = ${listvaluetext} ;
		 var list_count_text = ${listcounttext} ;
		 var list_value_active_user_index = ${listvalueactiveuserindex} ;
	} else {
		 var list_value_number = ${listvaluenumber} ;
		 var list_count_number = ${listcountnumber} ;
		 var min_index = ${minindex} ;
		 var max_index = ${maxindex} ;
	}
	 var value_this_cam_index = ${valuethiscamindex} ;
	 var name_this_cam = ${namethiscam};
</script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

</head>
<body>
	<div style="padding:8px 32px 0 0">
		<h5 style="padding:0 40px 0 40px"><span class="w3-text-deep-orange" style="font-weight:bold"> ${thiscam} </span> is recommended to you 
			<c:choose>
				<c:when test="${type eq 'TEXT'}"> because this camera has <span style="font-weight:bold">${feature}: ${valuetext}</span>
				and your preference is <span style="font-weight:bold">${yourtext}</span>
				</c:when>
			
				<c:otherwise>
					because this camera has <span style="font-weight:bold">${feature}: ${valuenumber}</span>
					and your preference is <span style="font-weight:bold">${yourmin} - ${yourmax}</span>
				</c:otherwise>
			</c:choose>
			
			and this is how your most similar user selected <b>${feature}</b>.
		</h5>
		
		<div id="chart4" style="height:440px; margin:0 auto; left:0; right:0; padding:8px 40px 0 24px"></div>
		<script type="text/javascript" src="js/chartFeatureValueExplanation.js"></script>
	</div>
</body>