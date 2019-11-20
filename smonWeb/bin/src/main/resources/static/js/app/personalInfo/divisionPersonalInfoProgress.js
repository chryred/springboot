$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var periodRefs = null;
var chartContainerRef = null;

//그래프 기본 옵션
var defaultOptions = {
	title: {
		text: "00월",
		display: true,
	},
	legend: {
		display: false
	}
};

var data = {
    labels: [ "1팀", "2팀", "3팀", "4팀", "5팀", "6팀"],
    datasets: [
        {
            label: "건수",
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1,
            data: [65, 59, 80, 81, 56, 55],
        }
    ]
};

function getTestData(periodType) {
	var graphDataSet = {
		email: [],
		tel: [],
		jumin: []
	};
	
	var itemCount = null;
	switch(periodType) {
		case "year":
			itemCount = 12;
			break;
		case "half":
			itemCount = 6;
			break;
		case "quater":
		case "recent":
			itemCount = 3;
			break;
	}
	_.each(graphDataSet, function(arr, personalInfoType) {
		for(var i=0; i<itemCount; i++) {
			graphDataSet[personalInfoType].push(data);
		}
	});
	
	return graphDataSet;
}

//그래프 dataset 템플릿
var datasetTemplate = {
    label: "Sample",
    fill: false,
    lineTension: 0,
    data: null
};

var pannelObjTemplate = ''+
	'<div class="panel panel-default">'+
		'<div class="panel-heading">'+
		    '<p><b></b></p>'+
		'</div>'+
		'<div class="panel-body pre-scrollable" style="height: 300px;">'+
		'</div>'+
	'</div>';
var chartCanvasTemplate = ''+
	'<div class="col-lg-4">'+
		'<canvas id=""></canvas>'+
	'</div>';

function initChart(chartDataSet) {
	
}

/*
 * 초기화
 */
function init () {
	periodRefs = $(".radio_period");
	chartContainerRef = (".chart-wrapper");
	
	staticBindEvent();
	
	var ctx1_1 = $("#myChart1-1");
	var ctx1_2 = $("#myChart1-2");
	var ctx1_3 = $("#myChart1-3");
	defaultOptions.title.text = "1월";
	var myBarChart1_1 = new Chart(ctx1_1, {
	    type: 'bar',
	    data: data,
	    options: defaultOptions
	});
	defaultOptions.title.text = "2월";
	var myBarChart1_2 = new Chart(ctx1_2, {
	    type: 'bar',
	    data: data,
	    options: defaultOptions
	});
	defaultOptions.title.text = "3월";
	var myBarChart1_3 = new Chart(ctx1_3, {
	    type: 'bar',
	    data: data,
	    options: defaultOptions
	});
	var ctx2_1 = $("#myChart2-1");
	var ctx2_2 = $("#myChart2-2");
	var ctx2_3 = $("#myChart2-3");
	defaultOptions.title.text = "1월";
	var myBarChart2_1 = new Chart(ctx2_1, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
	defaultOptions.title.text = "2월";
	var myBarChart2_2 = new Chart(ctx2_2, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
	defaultOptions.title.text = "3월";
	var myBarChart2_3 = new Chart(ctx2_3, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
	var ctx3_1 = $("#myChart3-1");
	var ctx3_2 = $("#myChart3-2");
	var ctx3_3 = $("#myChart3-3");
	var ctx3_4 = $("#myChart3-4");
	var ctx3_5 = $("#myChart3-5");
	var ctx3_6 = $("#myChart3-6");
	defaultOptions.title.text = "1월";
	var myBarChart1 = new Chart(ctx3_1, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
	defaultOptions.title.text = "2월";
	var myBarChart2 = new Chart(ctx3_2, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
	defaultOptions.title.text = "3월";
	var myBarChart3 = new Chart(ctx3_3, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
	defaultOptions.title.text = "4월";
	var myBarChart4 = new Chart(ctx3_4, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
	defaultOptions.title.text = "5월";
	var myBarChart5 = new Chart(ctx3_5, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
	defaultOptions.title.text = "6월";
	var myBarChart6 = new Chart(ctx3_6, {
		type: 'bar',
		data: data,
		options: defaultOptions
	});
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

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	periodRefs.on("click", function() {
		var periodType = $(this).val();
		initChart(getTestData(periodType));
	});
};

/**
 * 추이 그래프 데이터 조회
 */
function progressGraphList(periodType, callback) {
	
}

/**
 * 차트 초기화
 * 
 * [Array] transDatas
 * [Object] options
 * [Function] callback
 * 
 */
function initLineChart() {
	
};

/**
 * 차트 갱신
 * 
 * [Array] transDatas
 * [Function] callback
 * 
 */
function udpateLineChart() {
	
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
