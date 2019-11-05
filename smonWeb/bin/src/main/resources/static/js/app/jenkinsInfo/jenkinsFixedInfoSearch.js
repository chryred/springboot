var gTargetSystemCd = "TOTAL";
var oChartLinePmd;
var oChartLineFindBugs;

$(document).ready(function() {
	initialize();
	fn_event();
	fn_search();
});

$.ajaxSetup({
    global: false
});

function initialize() {
	fn_loadComboBox("JENKINS_SYSTEM");
}

/***********************
 * [공통] 이벤트
 ***********************/
function fn_event() {
	$("select#JENKINS_SYSTEM_COMBO").change(function(){
		gTargetSystemCd = $(this).val();
        fn_search();
    });

	$("#btn_search").on("click", function() {
		fn_search();
	});
}

function fn_loadComboBox(comboID) {
    var paramMap = {};
    var data = fn_selectTran('jenkinsTotalSystemList.do', false, paramMap);
    var divHtml = '<SELECT id="' + comboID + '_COMBO" name="' + comboID + '" class="form-control" style="display:inline;width:70%;vertical-align:middle">';
    divHtml += '<option value="TOTAL">전체</option>';

    $.each(data, function(index, item) {
        divHtml += '<option value="'+this.JOB_NM+'">' + this.JOB_NM + '</option>';
    });
    divHtml += '</SELECT>';
    $("#" + comboID + "_COMBO").remove();
    $("#jenkinsRegisteredcomboSystem").append(divHtml);
}

/***********************
 * [공통] 조회
 ***********************/
function fn_search() {
	fn_lineChart(oChartLinePmd, "P", $("#lineChartPmd"));
	fn_lineChart(oChartLineFindBugs, "F", $("#lineChartFindBugs"));
}

/*
 * chart 초기화
 */
function fn_lineChart(oChartId, scrtyTypeCd, targetId) {
	var chartLabels = [];
	var chartData = [];
	var nTtlWrknCnt = 0;

	if(oChartId != undefined) {
		oChartId.reset();
		oChartId.destroy();
	}

	$.ajax({
        url: 'jenkinsFixedInfoList.do',
        type: "POST",
        async: false,
        dataType: "JSON",
        //파라미터 설정
        data: {
        	scrtyTypeCd: scrtyTypeCd
           , jobNm : gTargetSystemCd
        },
        success: function(list) {
        	var backgroundColors = [];
        	var backgroundBoarderColors = [];
            _.each(list, function(obj, idx) {
            	if(idx == 0)
        		{
        			nTtlWrknCnt = obj.TTL_WRN_CNT;
        		} else {
	            	chartLabels.push(obj.MON + "월");
	            	chartData.push(parseInt(obj.FXD_WRN_CNT));
        		}
            });
            oChartId = undefined;
            oChartId = new Chart(targetId, {
        	    type: 'line',
        	    data: {
        	        labels: chartLabels,
        	        datasets: [
        	            {
        	                label: $("#JENKINS_SYSTEM_COMBO option:selected").html(),
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
        	    	title: {
        	    		display: true,
        	            text: $("#JENKINS_SYSTEM_COMBO option:selected").html() + ' 잔여건수  :  ' + comma(nTtlWrknCnt) + ' 건'
        	    	},
        	    	/*
        	    	legend: {
        	    		display: false
        	    	},
        	    	*/
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
}
//oChartId

function getToday() {
	var v_date = new Date();

	return getFommattedDate(v_date);
}


function comma(num) {
	if(num == undefined) {
		return 0;
	}
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
