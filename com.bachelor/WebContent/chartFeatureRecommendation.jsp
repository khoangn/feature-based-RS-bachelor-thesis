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
	var list_value = ${listvalue} ;
	var list_count = ${listcount} ;
	var list_data = ${listdata} ;
</script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>


</head>
<body>
	<div style="padding: 8px 32px 0 0">
		<h5 style="padding:0 40px 0 40px">
			<c:choose>
				<c:when test="${type eq 'TEXT'}">
					<b>${recommendedfeature}</b> with a value of <b>${recommendedvalue}</b> is recommended to you because it is most frequently selected by your 20 most similar users.
				</c:when>
				<c:otherwise>
					<b>${recommendedfeature}</b> in the range of <b>${recommendedvalue}</b> is recommended to you because it contains the most frequently selected value by your 20 most similar users.
				</c:otherwise>
			</c:choose>
		</h5>
		<div id="chart5" style="height:450px; margin:0 auto; left:0; right:0; padding:0 40px 0 24px"></div>
		<script type="text/javascript" src="js/chartFeatureRecommendation.js"></script>
	</div>
</body>