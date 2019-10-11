var type = feature_type;
var list_x = list_value;
var list_y = list_count;
var feature = feature_name;

// FEATURE RECOMMENDATION EXPLANATION

if (type == "CUSTOM_RANGE") {
	Highcharts.chart('chart5', {
		chart : {
			type : 'spline'
		},
		title : {
			text : 'How your 20 most similar users selected ' + feature.bold()
		},
		subtitle : {
			text : ''
		},
		xAxis : {
			categories : list_x,
			crosshair : true
		},
		yAxis : {
			min : 0,
			title : {
				text : 'Number of Selections <br> by your 20 most similar users'
			}
		},
		tooltip : {
			headerFormat : feature_name + '<span style="font-size:14px">: {point.key} </span><table>',
			pointFormat : '<div style="font-size:14px; padding:0"><b>{point.y:.0f} Selections by your similar users </b></div>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		series : [ {
			name : '20 similar users',
			data : list_y,
			color: '#505050'
		}],
		exporting : {
			enabled : false
		}
	});
} else {
	Highcharts.chart('chart5',{
		chart : {
			plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
			type : 'pie'
		},
		title : {
			text : 'How your 20 most similar users selected ' + feature.bold(),
			style : {
				fontSize : '20px'
			}
		},
		tooltip : {
			headerFormat: '<span style="font-size:14px">{series.name}</span><br>',
	        pointFormat: '<span style="color:{point.color}; font-size:14px"><b>{point.name}</b></span>: <b>{point.y:.2f}%</b> of total<br/>',
			shared : true,
			useHTML : true
		},
	    plotOptions: {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
	                distance: '20px',
	                format: '<span style="color:{point.color}; font-size:14px"><b>{point.name}</b></span>: <b>{point.y:.2f}%</b>',
	                style: {
	                    color: 'black'
	                },
				},
				showInLegend : true
			}
	    },		
		series : [{
			name : 'Selections of your 20 similar users',
			colorByPoint: true,
			data : list_data,
		}],
		exporting : {
			enabled : false
		}
	});
}
