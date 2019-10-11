var type = feature_type;
var list_x_number = list_value_number;
var list_y_number = list_count_number;
var list_x_text = list_value_text;
var list_y_text = list_count_text;
var feature = feature_name;

// CHART MORE EXPLANATION PRO CON

if (type == 'CUSTOM_RANGE') {
	// RANGE VALUES
	var chart = Highcharts.chart('chart4', {
		chart: {
			type: 'spline'
		},
		title: {
			text: 'How your 20 most similar users selected ' +  feature.bold()
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
	    plotOptions: {
	        series: {
	            lineWidth: 2
	        }
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

	chart.xAxis[0].addPlotLine({
		value : value_this_cam_index,
		color : '#dd4124',
		zIndex : 3,
		width : 5,
		id : 'this-camera',
		label : {
			text : 'This camera', //name_this_cam,
			y : -5,
			x : -40,
			rotation: 1,
			style : {
				color : '#dd4124',
				fontSize : '14px',
			}
		}
	});

	chart.xAxis[0].addPlotBand({
		color : '#99ccff',
		from : min_index - 0.02,
		to : max_index + 0.02,
		zIndex : 2,
		label : {
			text : 'Your choice',
			zIndex : 6,
			style : {
				color : '#0066cc',
				fontSize : '14px',
			}
		}
	});
} else {
	// TEXT VALUES
	var chart = Highcharts.chart('chart4',{
		chart : {
			plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
			type : 'pie'
		},
		title : {
			text : 'How your 20 most similar users selected ' + feature.bold()
		},
		xAxis : {
			categories : list_x_text,
			crosshair : true
		},
		yAxis : {
			min : 0,
			title : {
				text : 'Number of Selections <br> by your 20 most similar users'
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
//			color: '#505050'
		}],
		exporting : {
			enabled : false
		}
	});

	chart.xAxis[0].addPlotLine({
		value : value_this_cam_index - 0.03,
		color : '#dd4124',
		zIndex : 4,		
		width : 6,
		id : 'this-camera',
		label : {
			text : 'This camera', //name_this_cam,
			y : -6,
			x : -80,
			rotation: 1,
			style : {
				color : '#dd4124',
				fontSize : '13px',
			}
		}
	});
	var i;
	for (i = 0; i < list_value_active_user_index.length; i++) {
		chart.xAxis[0].addPlotLine({
			value : list_value_active_user_index[i] + 0.03,
			color : '#99ccff',
			zIndex : 4,
			width : 6,
			id : 'your-choice',
			label : {
				text : 'Your choice',
				rotation: 1,
				y : -4,
				style : {
					color : '#0066cc',
					fontSize : '14px',
				}
			}
		});
	}
}
