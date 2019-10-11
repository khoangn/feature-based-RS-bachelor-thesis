var type = feature_type;
var list_x_number = list_value_number;
var list_y_number = list_count_number;
var list_x_text = list_value_text;
var list_y_text = list_count_text;
var feature = feature_name;

// CHART 20 SIMILAR USER

if (type == "CUSTOM_RANGE") {
	var chart = Highcharts.chart('chart2', {
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
			categories : list_x_number,
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
			data : list_y_number,
			color: '#505050'
		}],
		exporting : {
			enabled : false
		}
	});
	chart.xAxis[0].addPlotBand({
		color : '#99ccff',
		from : min_index - 0.02,
		to : max_index + 0.02,
		zIndex : 2,
		label : {
			text : 'Your choice',
			y : -4,
			style : {
				color : '#0066cc',
				fontSize : '14px',
			}
		}
	});
} else {
	var chart = Highcharts.chart('chart2', {
		chart : {
			plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
			type : 'pie'
		},
		title : {
			text : 'How your 20 most similar users selected ' + feature.bold()
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
	var i;
	for (i = 0; i < list_value_active_user_index.length; i++) {
		chart.xAxis[0].addPlotLine({
			value : list_value_active_user_index[i],
			color : '#99ccff',
			zIndex : 3,
			width : 5,
			id : 'your-choice',
			label : {
				text : 'Your choice',
				rotation : 1,
				y : -5,
				style : {
					color : '#0066cc',
					fontSize : '14px',
				}
			}
		});
	}
}


//Highcharts.chart('chart2', {
//    chart: {
//        type: 'spline'
//    },
//    title: {
//        text: feat_name + ' selections of all similar users'
//    },
//    subtitle: {
//        text: ''
//    },
//    xAxis: {
//        categories: list_value,
//        crosshair: true
//    },
//    yAxis: {
//        min: 0,
//        title: {
//            text: 'Number of selections'
//        }
//    },
//    tooltip: {
//        headerFormat: '<span style="font-size:12px">{point.key}</span><table>',
//        pointFormat: '<div style="padding:0"><b>{point.y:.0f} selections </b></div>',
//        footerFormat: '</table>',
//        shared: true,
//        useHTML: true
//    },
//    plotOptions: {
//        column: {
//            pointPadding: 0.2,
//            borderWidth: 0
//        }
//    },
//    series: [{
//        name: feat_name,
//        data: list_count
//    }],
//    exporting: {
//    	enabled: false
//    }
//});