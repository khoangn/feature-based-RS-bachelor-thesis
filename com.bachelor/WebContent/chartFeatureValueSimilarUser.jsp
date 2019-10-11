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

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script>
	var feature_type = ${featuretype};
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
</script>
</head>
<body>
	<div id="chart2" style="height:450px; width:900px; margin: 0 auto; padding:0 32px 32px 0"></div>
	<script type="text/javascript" src="js/chartFeatureValueSimilarUser.js"></script>
</body>