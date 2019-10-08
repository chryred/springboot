$(document).ready(function() {
	init();
});

/*
 * private decalre
 */

var GRAPH_PERIOD_DAY_MIN = 7; //day
var GRAPH_PERIOD_MONTH_MIN = 12; //month

var emailLineChart = null;
var telnoLineChart = null;
var juminLineChart = null;
var dbNameComboboxRef = null;
var sidAllBtnRef = null;
var sidClearBtnRef = null;
var emailChartRef = null;
var telnoChartRef = null;
var juminChartRef = null;
var periodTypeRefs = null;

//DB 명 별 그래프 색상 관리
var dbNameColors = {};

//DB 명 전체 데이터 
var dbNameAllDatas = [];

//그래프 전체 데이터
var _datasets = {
	email: null,
	telno: null,
	jumin: null
};

//그래프 기본 옵션
var defaultOptions = {
		legend: {
			display: true
		},
		animation: {
			duration: 0 
		},
		tooltips: {
			callbacks: {
				title: function(tooltipItem, data) {
//					var pointedItemObj = tooltipItem[0];
//					var pointedItemData = data.datasets[pointedItemObj.datasetIndex];
//					return pointedItemData.label;
				}
			}
		},
        scales: {
            yAxes: [{
                ticks: {
                    min: 0,
                    beginAtZero: true
                }
            }]
        },
        onClick: function(e, arr) {
        	if(arr.length > 0) {
        		console.log(this);
        	}
        }
};

//그래프 dataset 템플릿
var datasetTemplate = {
    label: "Sample",
    fill: false,
    lineTension: 0,
    data: null
};

var currentDetailType = null;

/*
 * 초기화
 */
function init () {
	dbNameComboboxRef = $("#dbName");
	sidAllBtnRef = $("#all");
	sidClearBtnRef = $("#clear");
	emailChartRef = $("#emailChart");
	telnoChartRef = $("#telnoChart");
	juminChartRef = $("#juminChart");
	periodTypeRefs = $(".periodType");
	
	initComboDBName();
	
	initComboboxComponent(dbNameComboboxRef, {
		allowClear: true
	});
	
	var currentPeriodInfo = getCurrentPeriodInfo();
	
	progressGraphList(currentPeriodInfo.periodType, function(datas) {
		var list = null;
		var chartInfoType = null;
		var chartOptions = {};
		var dbNameCntFlag = false;
		
		_.each(datas, function(data, key) {
			switch (key) {
			case "emailList":
				chartInfoType = "email";
				list = datas.emailList;
				break;
			case "telnoList":
				chartInfoType = "telno";
				list = datas.telnoList;
				break;
			case "juminList":
				chartInfoType = "jumin";
				list = datas.juminList;
				break;
			default:
				console.info("조회된 데이터가 존재하지 않습니다.");
				break;
			}

			var graphDatas = {};
			_.each(list, function(data, idx) {
				if(graphDatas[data.DB_NAME] == undefined) {
					graphDatas[data.DB_NAME] = [];
				} 
				graphDatas[data.DB_NAME].push({
					dbName: data.DB_NAME,
					count: data.CNT
				});
			});
			
			//dbName 수를 count하지 못했다면 dbName 수 count
			if(!dbNameCntFlag) {
				var count = 1;
				_.each(graphDatas, function(data, idx) {
					dbNameColors[idx] = window.RGBA_DATA_ARRAY[count++]; //window.RGBA_DATA_ARRAY(rgba-data.js)
				});
				dbNameCntFlag = true;
			}
			
			initLineChart(currentPeriodInfo.periodType, chartInfoType, graphDatas, chartOptions);
		});
		
	});
	
	staticBindEvent();

};

/**
 * select2 콤보박스 초기화
 */
function initComboboxComponent(ref, options, callback) {
	if(!_.isUndefined(ref) && !_.isUndefined(options)) {
		ref.select2(options);
	} else {
		console.error("select2 초기화 실패");
	}
	
	isCallback(callback);
};

/*
 * DB 명 콤보박스 데이터 초기화
 */
function initComboDBName() {
	$.ajax({
		url: contextPath + "/personalInfo/search/ajax/list/dbName.do",
		type: "POST",
		dataType: "json",
		success: function(data) {
			_.each(data, function(option, idx) {
				dbNameComboboxRef.append('<option value="' + option.DB_NAME + '">' + option.DB_NAME + '</option>');
				dbNameAllDatas.push(option.DB_NAME);
			});
			dbNameComboboxRef.val(dbNameAllDatas);
		},
		error: function() {
			alert("에러가 발생했습니다.");
		}
	});
};

/**
 * 현재 선택된 기간 정보
 */
function getCurrentPeriodInfo() {
	var dt = new Date();
	var start = null;
	var end = null;
	var format = null;
	
	var periodType = null;
	_.each(periodTypeRefs, function(periodTypeRef) {
		if($(periodTypeRef).prop("checked")) {
			periodType = $(periodTypeRef).val();//day, month
		}
	});
	
	switch(periodType) {
		case "day":
			dt.setDate(dt.getDate() - GRAPH_PERIOD_DAY_MIN);
			start = dt;
			end = new Date();
			format = "yyyyMMdd";
			break;
		case "month":
			dt.setMonth(dt.getMonth() - GRAPH_PERIOD_MONTH_MIN);
			start = dt;
			end = new Date();
			format = "yyyyMM";
			break;
	}
	
	return {
		periodType: periodType,
		start: start,
		end: end,
		format: format
	};
};

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	/**
	 * DB 명 선택 값 변경 시, 차트 갱신
	 */
	dbNameComboboxRef.on("change", function() {
		var selectedDatas = $(this).val();
		var selectedArr = null;
		var emailDatasets = {};
		var telnoDatasets = {};
		var juminDatasets = {};
		
		if(selectedDatas) { //1개 이상 선택된 경우 차트 갱신
			_.each(selectedDatas, function(selectedData) {
				_.each(_datasets.email, function(dataset, key) {
					if(dataset.dbName == selectedData) {
						emailDatasets[selectedData] = dataset;
					}
				});
				_.each(_datasets.telno, function(dataset, key) {
					if(dataset.dbName == selectedData) {
						telnoDatasets[selectedData] = dataset;
					}
				});
				_.each(_datasets.jumin, function(dataset, key) {
					if(dataset.dbName == selectedData) {
						juminDatasets[selectedData] = dataset;
					}
				});
			});
			
			udpateLineChart("email", emailDatasets);
			udpateLineChart("telno", telnoDatasets);
			udpateLineChart("jumin", juminDatasets);
		}
	});
	
	/**
	 * DB 명 전체 선택
	 */
	sidAllBtnRef.on("click", function() {
		dbNameComboboxRef.val(dbNameAllDatas).trigger("change");
	});
	
	/**
	 * DB 명 선택 초기화
	 */
	sidClearBtnRef.on("click", function() {
		dbNameComboboxRef.val(null).trigger("change");
	});
	
	/**
	 * 조회 범주 선택
	 */
	periodTypeRefs.on("click", function() {
		var selectedPeriodType = null;
		selectedPeriodType = $(this).val(); 
		
		progressGraphList(selectedPeriodType, function(datas) {
			var list = null;
			var chartInfoType = null;
			
			_.each(datas, function(data, key) {
				switch (key) {
				case "emailList":
					chartInfoType = "email";
					list = datas.emailList;
					break;
				case "telnoList":
					chartInfoType = "telno";
					list = datas.telnoList;
					break;
				case "juminList":
					chartInfoType = "jumin";
					list = datas.juminList;
					break;
				default:
					console.info("조회된 데이터가 존재하지 않습니다.");
					break;
				}
				
				var graphDatas = {};
				_.each(list, function(data) {
					if(graphDatas[data.DB_NAME] == undefined) {
						graphDatas[data.DB_NAME] = [];
					} 
					graphDatas[data.DB_NAME].push({
						dbName: data.DB_NAME,
						count: data.CNT
					});
				});
				
				initLineChart(selectedPeriodType, chartInfoType, graphDatas);
			});
			
		});
	});
	
};

/**
 * 추이 그래프 데이터 조회
 */
function progressGraphList(periodType, callback) {
	var currentPeriodInfo = getCurrentPeriodInfo();
	
	$.ajax({
		url: contextPath + "/personalInfo/progress/ajax/list/graph.do",
		type: "POST",
		dataType: "json",
		data: {
			checkStartDate: currentPeriodInfo.start.format(currentPeriodInfo.format),
			checkEndDate: currentPeriodInfo.end.format(currentPeriodInfo.format),
			periodType: currentPeriodInfo.periodType
		},
		success: function(data) {
			isCallback(callback, data);
		},
		error: function() {
			alert("에러가 발생했습니다.");
		}
	});
}

/**
 * 차트 초기화
 * 
 * [Array] transDatas
 * [Object] options
 * [Function] callback
 * 
 */
function initLineChart(periodType, chartInfoType, transDatas, options, callback) {
	var datasets = [];
	_.each(transDatas, function(transData, key) {
		var dataset = _.clone(datasetTemplate);
		dataset.label = key;
		dataset.borderColor = dbNameColors[key];
		dataset.backgroundColor = dbNameColors[key];
		dataset.data = [];
		dataset.dbName = transData[0].dbName;
		_.each(transData, function(data) {
			dataset["data"].push(data.count);
		})
		datasets.push(dataset);
	});

	var dt = new Date();
	var start = null;
	var end = null;
	var format = null;
	switch(periodType) {
		case "day":
			dt.setDate(dt.getDate() - GRAPH_PERIOD_DAY_MIN);
			start = dt;
			end = new Date();
			break;
		case "month":
			dt.setMonth(dt.getMonth() - GRAPH_PERIOD_MONTH_MIN);
			start = dt;
			end = new Date();
			break;
	}
	
	var chartRef = null;
	switch(chartInfoType) {
		case "email":
			_datasets.email = _.clone(datasets);
			chartRef = emailChartRef;
			if(!_.isUndefined(datasets)) {
				if(emailLineChart) {
					emailLineChart.destroy();
				}
				emailLineChart = new Chart(chartRef, {
				    type: "line",
				    data: {
				    	labels: makeDateToArray(periodType, start.format("yyyy-MM-dd"), end.format("yyyy-MM-dd")),
				        datasets: datasets
				    },
				    options: _.isUndefined(options) || _.isNull(options) ? defaultOptions : _.extend(defaultOptions, options)
				});
			} else {
				throw new Error("차트 초기화 실패");
			}
			break;
		case "telno":
			_datasets.telno = _.clone(datasets);
			chartRef = telnoChartRef;
			if(telnoLineChart) {
				telnoLineChart.destroy();
			}
			if(!_.isUndefined(datasets)) {
				telnoLineChart = new Chart(chartRef, {
				    type: "line",
				    data: {
				    	labels: makeDateToArray(periodType, start.format("yyyy-MM-dd"), end.format("yyyy-MM-dd")),
				        datasets: datasets
				    },
				    options: _.isUndefined(options) || _.isNull(options) ? defaultOptions : _.extend(defaultOptions, options)
				});
			} else {
				throw new Error("차트 초기화 실패");
			}
			break;
		case "jumin":
			_datasets.jumin = _.clone(datasets);
			chartRef = juminChartRef;
			if(juminLineChart) {
				juminLineChart.destroy();
			}
			if(!_.isUndefined(datasets)) {
				juminLineChart = new Chart(chartRef, {
				    type: "line",
				    data: {
				    	labels: makeDateToArray(periodType, start.format("yyyy-MM-dd"), end.format("yyyy-MM-dd")),
				        datasets: datasets
				    },
				    options: _.isUndefined(options) || _.isNull(options) ? defaultOptions : _.extend(defaultOptions, options)
				});
			} else {
				throw new Error("차트 초기화 실패");
			}
			break;
	}
	
	isCallback(callback);
};

/**
 * 차트 갱신
 * 
 * [Array] transDatas
 * [Function] callback
 * 
 */
function udpateLineChart(chartInfoType, transDatas, options, callback) {
	var updateDatasets = [];
	
	_.each(transDatas, function(transData, key) {
		var dataset = _.clone(datasetTemplate);
		dataset.label = key;
		dataset.borderColor = dbNameColors[key];
		dataset.backgroundColor = dbNameColors[key];
		dataset.data = transData.data;
		dataset.dbName = transData.dbName;
		
		updateDatasets.push(dataset);
	});
	
	if(!_.isUndefined(updateDatasets)) {
		switch(chartInfoType) {
			case "email":
				emailLineChart.data.datasets = updateDatasets;
				emailLineChart.options = _.isUndefined(options) || _.isNull(options) ? emailLineChart.options : _.extend(emailLineChart.options, options);
				emailLineChart.update();
				break;
			case "telno":
				telnoLineChart.data.datasets = updateDatasets;
				telnoLineChart.options = _.isUndefined(options) || _.isNull(options) ? telnoLineChart.options : _.extend(telnoLineChart.options, options);
				telnoLineChart.update();
				break;
			case "jumin":
				juminLineChart.data.datasets = updateDatasets;
				juminLineChart.options = _.isUndefined(options) || _.isNull(options) ? juminLineChart.options : _.extend(juminLineChart.options, options);
				juminLineChart.update();
				break;
		}
	} else {
		throw new Error("차트 초기화 실패");
	}
	
	isCallback(callback);
};

/**
 * callback 유효성 검사
 * 
 * dependancy
 * 		underscore.js
 * 
 */
function isCallback(callback, params) {
	if(!_.isUndefined(callback)) {
		if(_.isFunction(callback)) {
			callback(params);
		} else {
			throw new Error("invalid callback function...");
		}
	}
};

/**
 * 특정 기간 동안, 특정 format으로 배열 생성
 * 
 * @param symd
 * @param eymd
 * @returns {Array}
 */
function makeDateToArray(type, symd, eymd) {
	var result = [];
	
	var outFormat = null;
	
	var sDate = new Date(symd);
	var eDate = new Date(eymd);
	
	switch(type) {
		case "year":
			outFormat = "yyyy";
			break;
		case "month":
			outFormat = "yyyy/MM";
			break;
		case "day":
			outFormat = "MM/dd";
			break;
		case "fullDay":
			outFormat = "yyyy/MM/dd";
			break;
	}
	
	result.push(sDate.format(outFormat));
	
	while(sDate.format(outFormat) != eDate.format(outFormat)) {
		switch(type) {
			case "year":
				sDate.setFullYear(sDate.getFullYear() + 1);
				break;
			case "month":
				sDate.setMonth(sDate.getMonth() + 1);
				break;
			case "day":
			case "fullDay":
				sDate.setDate(sDate.getDate() + 1);
				break;
		}
		result.push(sDate.format(outFormat));
	}
	
	return result;
};