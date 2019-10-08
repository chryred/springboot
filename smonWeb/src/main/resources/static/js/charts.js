var system = "";
var bubbleChart = "";

//cpu 모니터링
function fn_loadingCpuUsage(boolean){

    var container = $("#flot-line-chart-moving");

    var maximum = container.outerWidth() / 2 || 300;

    var series = [{
        data: '',
        lines: {
            fill: true
        }
    }];

    function selectCpuUsage(){

        var requestUrl = 'selectCpuUsage.do';
        var res = [];

        var paramMap = new Object();

        paramMap.row = Math.round(maximum);
        paramMap.system = system;


        $.ajax({
            url: requestUrl,
            type:"POST",
            async : true,
            global: false,
            dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
            //파라미터 설정
            data : paramMap,
            success : function(data) {

                var index =0;
                $.each(data, function() {
                    res.push([index, this.CPU]);
                    index++;
                });

                series[0].data = res;
                doPlot();

             },
            error : function(xhr, status, error) {
               return null;
            }
        });

        return res;
    }

    function doPlot(){
        var plot = $.plot(container, series, {
            grid: {
                borderWidth: 1,
                minBorderMargin: 20,
                labelMargin: 10,
                backgroundColor: {
                    colors: ["#fff", "#e4f4f4"]
                },
                margin: {
                    top: 8,
                    bottom: 20,
                    left: 20
                },
                markings: function(axes) {
                    var markings = [];
                    var xaxis = axes.xaxis;
                    for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
                        markings.push({
                            xaxis: {
                                from: x,
                                to: x + xaxis.tickSize
                            },
                            color: "rgba(232, 232, 255, 0.2)"
                        });
                    }
                    return markings;
                }
            },
            xaxis: {
                tickFormatter: function() {
                    return "";
                }
            },
            yaxis: {
                min: 0,
                max: 100
            },
            legend: {
                show: true
            }
        });
    }


    selectCpuUsage();

    if(boolean){
        setInterval(function updateRandom() {
           selectCpuUsage();
        }, 10000);
    }

}

function fn_loadingLongQuery(){

	function generateData() {

		var dbData = [];

		var requestUrl  = 'selectLongQuery.do';
        var paramMap = new Object();

        paramMap.system = system;

		$.ajax({
            url: requestUrl,
            type:"POST",
            async : false,
            global: false,
            dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
           //파라미터 설정
            data : paramMap,
            success : function(data) {

                var res = new Array();

                $.each(data, function() {
                	dbData.push({
        				x: this.LAST_LOAD_TIME,
        				y: this.ELAPSED_TIME,
        				SQL_ID: this.SQL_ID,
        				LAST_LOAD_YMD : this.LAST_LOAD_YMD,
        				DB_NAME : this.DB_NAME,
        				SQL : this.SQL_FULLTEXT,
        				LAST_LOAD_TIME:this.LAST_LOAD_TIME
        			});
                });

                var bubbleData = {
                		datasets: [{
                			data: dbData
                		}]
                	};

                var options = {
            			//aspectRatio: 1,
            			legend: false,
            			tooltips : true,
            			title: {
            	            display: true,
            	            text: ' 총  :  ' + data.length + ' 건'
            	        },
            	        scales: {
            	    		xAxes: [{
            	    			scaleLabel: {
            	                	display: true,
            	                	labelString: '호출시간'
            	                }
            	            }],
            	            yAxes: [{
            	                scaleLabel: {
            	                	display: true,
            	                	labelString: '실행시간'
            	                }
            	            }]
            	        },
            		};

                bubbleChart = new Chart('chart-0', {
            		type: 'bubble',
            		data: bubbleData,
            		options: options
            	});

             },
            error : function(xhr, status, error) {
               alert(error);
            }
        });

	}

	generateData();

}


//bubble chart onClick 이벤트
$("#chart-0").on("click", function(evt) {
	var activePoints = bubbleChart.getElementsAtEvent(evt);
	var firstPoint = activePoints[0];

	if(firstPoint !== undefined){
		var label = bubbleChart.data.labels[firstPoint._index];
		var value = bubbleChart.data.datasets[firstPoint._datasetIndex].data[firstPoint._index];
		/*
		alert(  "DB명 : " + value.DB_NAME + "\n"  +
				"실행일시 : " + value.LAST_LOAD_YMD + "/" + value.x + "\n"  +
				"SQL_ID : " + value.SQL_ID + "\n" +
			    "수행시간 : " + value.y + '초'  + '\n\n' +
			    "SQL 문 \n\n" + value.SQL);
		*/

		longModal = $('#longQueryDetailModal').modal({
			backdrop : true,
			keyboard : true,
			refresh : true,
			remote:"selectLongOpQueryDetail.do"
				  + "?last_load_ymd=" + value.LAST_LOAD_YMD
				  + "&db_name=" + value.DB_NAME
				  + "&sql_id=" + value.SQL_ID
				  + "&last_load_time=" + value.LAST_LOAD_TIME
		});

		// remote refresh용으로 처리
		$('#longQueryDetailModal').on('hidden.bs.modal', function() {
			$(this).removeData();
		});
	}
});

//Flot Multiple Axes Line Chart
function fn_loadingUndoTablespace(boolean){

    var series = new Array();

    var requestUrl  = 'selectUndoTableSpaceState.do';

    function getData(){

        var paramMap = new Object();

        paramMap.system = system;

        $.ajax({
            url: requestUrl,
            type:"POST",
            async : true,
            global: false,
            dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
           //파라미터 설정
            data : paramMap,
            success : function(data) {

                var res = new Array();

                var arrayIdx = -1;
                var tablespaceName = "";
                $.each(data, function() {

                    if(tablespaceName != this.TABLESPACE_NAME){
                        if(arrayIdx > -1){
                            series[arrayIdx] = {data : res, label : tablespaceName};
                        }
                        arrayIdx ++;
                        tablespaceName = this.TABLESPACE_NAME;
                        res = [];
                    }

                    res.push([this.CHECK_TIME, this.USED_RATE]);

                });
                series[arrayIdx] = {data : res, label : tablespaceName};
;
                doPlot();

             },
            error : function(xhr, status, error) {

            }
        });
    }


    function doPlot(){
        var plot = $.plot($("#flot-line-chart-multi"), series, {
                    xaxes: [{
                    },{} ],
                    yaxes: [{
                        min: 0
                    }, {
                        alignTicksWithAxis:  1 ,
                        position: "right",
                    }],
                    legend: {
                        position: 'sw'
                    },
                    grid: {
                        hoverable: true //IMPORTANT! this is needed for tooltip to work
                    },
                    tooltip: true,
                    tooltipOpts: {
                        content: "%s 사용률 %y%"

                    },
                    yaxis: {
                        min: 0
                        //,max: 100
                    }
                });

    }
    getData();

    if(boolean){
        setInterval(function updateRandom() {
            getData();
        },  240000);
    }
}


var morisData = "";

var chartTablespace = Morris.Bar({
    element: 'morris-bar-chart',
    data: morisData,
    xkey: 'TABLESPACE_NAME',
    ykeys: ['CHANGE_RATE', 'FREE_RATE'],
    labels: ['증가률%', '가용률%'],
    hideHover: 'auto',
    resize: true
});

function fn_loadingTablespace(term){

    var grid = "<thead><tr><th align='center'>TS명</th><th align='center'>증가량</th><th align='center'>가용량</th><th align='center'>사용량</th><th align='center'>전체</th></tr></thead>";

    var requestUrl = "selectTableSpaceState.do";


    function getData(){
        var paramMap = new Object();

        paramMap.term = term;
        paramMap.system = system;

        $.ajax({
            url: requestUrl,
            type:"POST",
            async : true,
            dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
           //파라미터 설정
            data : paramMap,
            success : function(data) {
                chartTablespace.setData(data);


                grid += "<tbody>";
                $.each(data, function() {
                    grid += "<tr>";
                    grid += "<td>"+this.TABLESPACE_NAME + "</td>";
                    grid += "<td align='right'>"+this.CHANGE_SIZE + "</td>";
                    grid += "<td align='right'>"+this.FREE_SIZE + "</td>";
                    grid += "<td align='right'>"+this.USED_SIZE + "</td>";
                    grid += "<td align='right'>"+this.CURRENT_SIZE + "</td>";
                    grid += "</tr>";
                });
                grid += "</tbody>";

                $("#tsGrid").html(grid);
             },
            error : function(xhr, status, error) {
                alert("테이블스페이스 정보 조회 중 오류 발생 :" + error);
            }
        });
    }

    getData();

}

function fn_loadingAlertLog(boolean){

    var alertHtml ="";
    var requestUrl = "selectAlertLog.do";

    function getData(){
        alertHtml = "";
        $.ajax({
            url: requestUrl,
            type:"POST",
            async : true,
            global: false,
            dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
            //파라미터 설정
            data : {system : system
            },
            success : function(data) {

                $.each(data, function() {

                    if(this.ALERT_GUBN == 1){
                        alertHtml += '<div class="alert alert-danger alert-dismissable"><i class="fa fa-warning fa-fw"></i><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>';
                        alertHtml += '&nbsp;' + this.CHECK_INFO + '<span class="pull-right text-muted small">><em>' + this.TIME + '</em></span>';
                        alertHtml += '</div>';
                    }else{
                        alertHtml += '<div class="alert alert-warning alert-dismissable"><i class="fa fa-warning fa-fw"></i><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>';
                        alertHtml += '&nbsp;' + this.CHECK_INFO + '<span class="pull-right text-muted small">><em>' + this.TIME + '</em></span>';
                        alertHtml += '</div>';
                    }

                   /* alertHtml += '<a href="#" class="list-group-item">';
                    alertHtml += '<i class="fa fa-warning fa-fw"></i> ' + this.CHECK_INFO;
                    alertHtml += '<span class="pull-right text-muted small">><em>' + this.TIME + '</em></span>';*/

                });

                $("#alertList").html(alertHtml);
             },
            error : function(xhr, status, error) {
               alert("alert 메세지 조회중 오류발생 :" + error);
            }
        });
    }

    getData();

    if(boolean){
        setInterval(function updateAlert() {
           getData();
        }, 15000);
    }
}


function fn_loadingHitRatio(){

    var hitHtml ="";
    var requestUrl = "selectHitRatio.do";

    function getData(){
        hitHtml = "";
        $.ajax({
            url: requestUrl,
            type:"POST",
            async : true,
            dataType:"JSON", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
            //파라미터 설정
            data : {system : system},
            success : function(data) {
                var color ="";
                var icon ="";
                $.each(data, function() {

                    if(this.CSS_GUBN == 0){
                        color = "panel-primary";
                        icon  = "fa-thumbs-o-up";
                    }else{
                        color = "panel-red";
                        icon  = "fa-thumbs-o-down";
                    }

                    hitHtml += '<div class="col-lg-4 col-md-6"><div class="panel '+ color + '"><div class="panel-heading"><div class="row"><div class="col-xs-3">';
                    hitHtml += '<i class="fa ' + icon + ' fa-4x"></i></div>';
                    hitHtml += '<div class="col-xs-9 text-right"><div class="huge">' + this.HIT_RATIO + '</div><div>' + this.GUBN + '</div>' ;
                    hitHtml += '</div></div></div><a href="'+ this.URL + '"><div class="panel-footer"><span class="pull-left">상세보기</span><span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span><div class="clearfix"></div></div></a></div></div>';


                });

                $("#hitRatioDiv").html(hitHtml);
             },
            error : function(xhr, status, error) {
               alert("alert 메세지 조회중 오류발생 :" + error);
            }
        });
    }
    getData();
}

/**
 * description
 * 		Invalid Objects 대쉬보드 로딩
 */
var invalidObjectsChart = null;
function fn_loadingInvalidObjects() {
	var chartLabels = [];
	var chartData = [];

	if(invalidObjectsChart != null) {
		invalidObjectsChart.destroy();
	}

	$.ajax({
        url: 'selectInvalidObjectsState.do',
        type: "POST",
        async: true,
        dataType: "JSON",
        //파라미터 설정
        data: {
        	system: system
        },
        success: function(list) {
        	var backgroundColors = [];
        	var backgroundBoarderColors = [];
            _.each(list, function(obj, idx) {
            	chartLabels.push(obj.OBJECT_TYPE);
            	chartData.push(parseInt(obj.CNT));
//            	backgroundColors.push(window.RGBA_DATA_ARRAY[idx*2]);
//            	backgroundBoarderColors.push(window.RGBA_DATA_ARRAY[list.length + idx*2]);
            });

            var $chartRef = $("#invalidObjectsChart");
        	invalidObjectsChart = new Chart($chartRef, {
        	    type: 'horizontalBar',
        	    data: {
        	        labels: chartLabels,
        	        datasets: [
        	            {
        	                label: "건수",
        	                backgroundColor: [
          	                    'rgba(255, 99, 132, 0.2)',
          	                    'rgba(54, 162, 235, 0.2)',
          	                    'rgba(255, 206, 86, 0.2)',
          	                    'rgba(75, 192, 192, 0.2)',
          	                    'rgba(153, 102, 255, 0.2)',
          	                    'rgba(255, 159, 64, 0.2)',
          	                    'rgba(255, 99, 132, 0.2)',
          	                    'rgba(54, 162, 235, 0.2)',
          	                    'rgba(255, 206, 86, 0.2)',
          	                    'rgba(75, 192, 192, 0.2)',
          	                    'rgba(153, 102, 255, 0.2)'
          	                ],
          	                borderColor: [
          	                    'rgba(255,99,132,1)',
          	                    'rgba(54, 162, 235, 1)',
          	                    'rgba(255, 206, 86, 1)',
          	                    'rgba(75, 192, 192, 1)',
          	                    'rgba(153, 102, 255, 1)',
          	                    'rgba(255, 159, 64, 1)',
          	                    'rgba(255,99,132,1)',
          	                    'rgba(54, 162, 235, 1)',
          	                    'rgba(255, 206, 86, 1)',
          	                    'rgba(75, 192, 192, 1)',
          	                    'rgba(153, 102, 255, 1)'
          	                ],
        	                borderWidth: 1,
        	                data: chartData,
        	            }
        	        ]
        	    },
        	    options: {
        	    	responsive: true,
        	    	legend: {
        	    		display: false
        	    	},
        	    	scales: {
        	    		xAxes: [{
        	                position: 'bottom'
        	            }],
        	            yAxes: [{
        	                ticks: {
        	                    min: 0,
        	                    beginAtZero: true
        	                }
        	            }]
        	        },
        	    }
        	});

        },
        error: function(xhr, status, error) {
           alert("alert 메세지 조회중 오류발생 :" + error);
        }
    });
};

$('#comboSystem').on('change', '#SYSTEM_COMBO',function(event) {
    system = $('#SYSTEM_COMBO').val();

    fn_loadingUndoTablespace(false);
    fn_loadingTablespace('1');
    //fn_loadingCpuUsage(false);
    fn_loadingAlertLog(false);
    fn_loadingHitRatio();
    fn_loadingInvalidObjects();
    fn_loadingLongQuery();

});


function fn_loadSystemCombo(comboID, callback) {//fn_selectGridData

    var paramMap = new Object();

    paramMap.team = "003";

    var data = fn_selectTran('searchSystemCombo.do', false, paramMap);

    var divHtml = '<SELECT id="' + comboID + '_COMBO" name="' + comboID + '" class="form-control">';

    var index = 0;

    $.each(data, function() {

        if(index == 0){
            system = this.CODE;
        }
        divHtml += '<option value="'+this.CODE+'">' + this.NAME
                + '</option>';

        index++;
    });

    divHtml += '</SELECT>';

    $("#" + comboID + "_COMBO").remove();

    $("#comboSystem").append(divHtml);

    if(callback != undefined) {
    	callback();
    }

}

