
var gScrtyTypeCd = "P";
var tableRef;
var CONTENTS_HEIGHT_MINUS_PIXEL = 290;

$(document).ready(function() {
	fn_event();
	fn_searchTotalInfo();
	initialize();
	initTable();
});

$.ajaxSetup({
    global: false
});

function initialize() {
	tableRef = $("#dataTables");
}

/***********************
 * [공통] 이벤트
 ***********************/
function fn_event() {
	$("select#scrtyTypeCd").change(function(){
        var select_name = $(this).children("option:selected").text();
        $(this).siblings("label").text("[" + select_name + "]");
        gScrtyTypeCd = $(this).val();

        fn_searchTotalInfo();
        fn_search();
    });

	$("#jobName").on("keydown", function(e) {
		if(e.keyCode == 13) {
			fn_search();
		}
	});
	$("#btn_search").on("click", function() {
		fn_search();
	});
}

/***********************
 * 전체 건수 조회
 ***********************/
function fn_searchTotalInfo() {
	var totCnt = 0;
	var fixedCnt  = 0;
	var newCnt = 0;
	var trtmRate = 0;

	var postData = {
			scrtyTypeCd : gScrtyTypeCd
	};
	var formURL = contextPath + "/jekinsInfo/jenkinsTotalInfo.do";

	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		timeout : 2 * 60 * 1000, //2 min,
		beforeSend : function() {
			$.blockUI({ css: {color: '#fff'} });
		},
		complete : function() {
			$.unblockUI();
			$("#span_totCnt").html(totCnt);
			$("#span_fixedCnt").html(fixedCnt);
			$("#span_newCnt").html(newCnt);
			$("#span_trtmRate").html(trtmRate);
		},
		success : function(result) {
			totCnt = comma(result[0].TTL_WRN_CNT + result[0].FXD_WRN_CNT);
			fixedCnt  = comma(result[0].FXD_WRN_CNT);
			newCnt = comma(result[0].TTL_WRN_CNT);
			trtmRate = comma(result[0].TRTM_RATE) + '%';
			$.unblockUI();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
			$.unblockUI();
		}
	});
}

/***********************
 * [공통] 조회
 ***********************/
function fn_search() {
	tableRef.setGridParam({
		postData: {
			scrtyTypeCd : gScrtyTypeCd
		}
	}).trigger("reloadGrid");
}

function getToday() {
	var v_date = new Date();

	return getFommattedDate(v_date);
}

function comma(num){
	if(num == undefined) {
		return 0;
	}
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/*
 * 그리드 초기화
 */
function initTable () {
	tableRef.jqGrid({
		url: contextPath + "/jekinsInfo/jenkinsInfoList.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			scrtyTypeCd : gScrtyTypeCd
		},
		colNames: [ "작업명",
					   "총 건수",
					   "상반기",
					   "하반기",
					   "3개월간",
					   "1개월간",
					   "1주일간",
					   "총 건수*",
					   "High Level건수*",
					   "Normal Level건수*",
					   "Low Level건수*",
					   "총 건수",
					   "처리율(%)",
					   "신규 건수",
					   "최종 빌드일시*",
					   "Jenkins_url"
					 ],
		colModel : [
	        { width: "200px", id: "JOB_NM", name: "JOB_NM", index: "JOB_NM", align: 'left',
				 cellattr: function(rowId, tv, rowObject, cm, rdata) {
				 	// 잔여 건수가 20보다 작을 경우 안전
	                if (rowObject.REMAIN_WRN_CNT < 20){
	                    return 'style="background : #5cb85c; font-weight: bold; color:#fff; vertical-align:middle"';
	                }
	                // 개선 건수가 0건일 경우 위험
	                else if(rowObject.FXD_WRN_CNT == 0){
	                    return 'style="background : #d9534f; font-weight: bold; color:#fff; vertical-align:middle"';
	                }
	                // 그외는 진행중
	                else{
	                    return 'style="background:#f8fc8a; font-weight: bold; color:#000; vertical-align:middle"';
	                }
	            }
	        },
			{ width: "100px", id:"FXD_WRN_CNT", name: "FXD_WRN_CNT", index: "FXD_WRN_CNT", formatter: 'integer', align: 'right',
	        	cellattr: function(rowId, tv, rowObject, cm, rdata) {
					return 'style="font-weight: bold; color:#5cb85c; vertical-align:middle"';
	            }
			},
			{ width: "70px", id:"FRST_HALF_CNT", name: "FRST_HALF_CNT", index: "FRST_HALF_CNT", formatter: 'integer', align: 'right' },
			{ width: "70px", id:"SCND_HALF_CNT", name: "SCND_HALF_CNT", index: "SCND_HALF_CNT", formatter: 'integer', align: 'right' },
			{ width: "70px", id:"THRE_MNTH_CNT", name: "THRE_MNTH_CNT", index: "THRE_MNTH_CNT" , formatter: 'integer', align: 'right' },
			{ width: "70px", id:"ONE_MNTH_CNT", name: "ONE_MNTH_CNT", index: "ONE_MNTH_CNT", formatter: 'integer', align: 'right' },
			{ width: "70px", id:"ONE_WEEK_CNT", name: "ONE_WEEK_CNT", index: "ONE_WEEK_CNT", formatter: 'integer', align: 'right' },
			{ width: "90px", id:"REMAIN_WRN_CNT", name: "REMAIN_WRN_CNT", index: "REMAIN_WRN_CNT",
				formatter: function(val, ref, row) {
					if(row["JOB_NM"] == undefined) {
						return comma(val);
					}
					var rowId = ref.rowId;
					var title = "Jenkins 화면으로 이동";
					return '<a href="' + row.JOB_URL + '" target="_blank" title="' + title + '">'  + comma(val) +'</a>';
				}, unformat: function(val, ref, row) {
					return val.replace(/,/g, "");
				}, align: 'right',
				cellattr: function(rowId, tv, rowObject, cm, rdata) {
					return 'style="font-weight: bold; color:#d9534f; vertical-align:middle"';
	            }
			},
			{ width: "100px", id:"HGH_PRTY_WRN_CNT", name: "HGH_PRTY_WRN_CNT", index: "HGH_PRTY_WRN_CNT",
				formatter: function(val, ref, row) {
					if(row["JOB_NM"] == undefined) {
						return comma(val);
					}
					var rowId = ref.rowId;
					var title = "Jenkins High Level 화면으로 이동";
					return '<a href="' + row.JOB_URL + '/HIGH" target="_blank" title="' + title + '">'  + comma(val) +'</a>';
				},
				unformat: function(val, ref, row) {
					return val.replace(/,/g, "");
				}, align: 'right',
				/*
				cellattr: function(rowId, tv, rowObject, cm, rdata) {
					return 'style="font-weight: bold; color:blue; vertical-align:middle"';
	            }
	            */
			},
			{ width: "110px", id:"NRML_PRTY_WRN_CNT", name: "NRML_PRTY_WRN_CNT", index: "NRML_PRTY_WRN_CNT",
				formatter: function(val, ref, row) {
					if(row["JOB_NM"] == undefined) {
						return comma(val);
					}
					var rowId = ref.rowId;
					var title = "Jenkins Normal Level 화면으로 이동";
					return '<a href="' + row.JOB_URL + '/NORMAL" target="_blank" title="' + title + '">' + comma(val) +'</a>';
				}, unformat: function(val, ref, row) {
					return val.replace(/,/g, "");
				}, align: 'right',
				/*
				cellattr: function(rowId, tv, rowObject, cm, rdata) {
					return 'style="font-weight: bold; color:blue; vertical-align:middle"';
	            }
	            */
			},
			{ width: "100px", id:"LOW_PRTY_WRN_CNT", name: "LOW_PRTY_WRN_CNT", index: "LOW_PRTY_WRN_CNT",
				formatter: function(val, ref, row) {
					if(row["JOB_NM"] == undefined) {
						return comma(val);
					}
					var rowId = ref.rowId;
					var title = "Jenkins Low Level 화면으로 이동";
					return '<a href="' + row.JOB_URL + '/LOW" target="_blank" title="' + title + '">'  + comma(val) +'</a>';
				}, unformat: function(val, ref, row) {
					return val.replace(/,/g, "");
				}, align: 'right',
				/*
				cellattr: function(rowId, tv, rowObject, cm, rdata) {
					return 'style="font-weight: bold; color:blue; vertical-align:middle"';
	            }
	            */
			},
			{ width: "100px",id:"TTL_WRN_CNT", name: "TTL_WRN_CNT", index: "TTL_WRN_CNT", formatter: 'integer', align: 'right',
				cellattr: function(rowId, tv, rowObject, cm, rdata) {
					return 'style="font-weight: bold; color:#337ab7; vertical-align:middle"';
	            }
			},
			{ width: "100px",id:"TRTM_RATE", name: "TRTM_RATE", index: "TRTM_RATE", formatter: 'integer', align: 'right' },
			{ width: "90px", id:"NEW_WRN_CNT", name: "NEW_WRN_CNT", index: "NEW_WRN_CNT", formatter: 'integer', align: 'right' },
			{ width: "140px", id:"BLD_DT", name: "BLD_DT", index: "BLD_DT", formatter: 'date', formatoptions: { srcformat: "Y/m/d H:i:s", newformat: "Y/m/d H:i:s"}, align: 'center' },
			{ width: "0px", id:"JOB_URL", name: "JOB_URL", index: "JOB_URL", align: 'right', hidden:true }
		],
		shrinkToFit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		loadonce: false,
		pager: false,
		rowNum: -1,
		footerrow: true,
		userDataOnFooter: true,
		sortable: true,
		height: "auto",
		gridview: true,
		resizable: true,
		loadComplete : function() {
			// .ui-th-column, .ui-jqgrid .ui-jqgrid-htable th.ui-th-column
			$(".ui-jqgrid .ui-jqgrid-htable th div").css("height", "auto");
			$(".ui-jqgrid .ui-jqgrid-htable th div").css("overflow", "hidden");
			$(".ui-jqgrid .ui-jqgrid-htable th div").css("font-size", "1.1em");
			$(".ui-jqgrid tr.jqgrow td").css("font-size", "1.1em");
			var colNames1 = [
				  "FXD_WRN_CNT"
				, "HGH_PRTY_WRN_CNT"
				, "NRML_PRTY_WRN_CNT"
				, "LOW_PRTY_WRN_CNT"
				, "NEW_WRN_CNT"
				, "TTL_WRN_CNT"
				, "REMAIN_WRN_CNT"
				, "FRST_HALF_CNT"
				, "SCND_HALF_CNT"
				, "THRE_MNTH_CNT"
				, "ONE_MNTH_CNT"
				, "ONE_WEEK_CNT"];

			var mapColSum = {};
			var $grid = tableRef;
			var color = "";
			var totTalJobCnt =  $grid.getGridParam("reccount")

			for ( var i = 0 ; i <= colNames1.length ; i++ )
			{
				mapColSum[colNames1[i]] = $grid.jqGrid('getCol',colNames1[i],false,'sum');
//				var nColRowCnt = 0;
//
//				for(var nRowIdx = 1; nRowIdx <= totTalJobCnt; nRowIdx ++ ){
//					nColRowCnt += parseInt($grid.getRowData(nRowIdx)[colNames1[i]]);
//					console.log(colNames1[i] + " : " + $grid.getRowData(nRowIdx)[colNames1[i]]);
//				}
//				mapColSum[colNames1[i]] = nColRowCnt;

				if (mapColSum[colNames1[i]] > 0) {
				    if(colNames1[i] == "HGH_PRTY_WRN_CNT" || colNames1[i] == "NRML_PRTY_WRN_CNT" || colNames1[i] == "NEW_WRN_CNT" || colNames1[i] == "REMAIN_WRN_CNT"){
				        color = "red";
				    }else{
				        color = "blue";
				    }
//					$('table.ui-jqgrid-ftable td:eq('+(i+1)+')').css("color", color);
				    $('table.ui-jqgrid-ftable td[aria-describedby=dataTables_' + colNames1[i] + ']').css("color", color);
	            }
			}
			$grid.jqGrid('footerData','set',{JOB_NM                 : 'Total[등록작업수: ' + comma(totTalJobCnt) +"건] :"});
			$grid.jqGrid('footerData','set',{FXD_WRN_CNT                 : mapColSum["FXD_WRN_CNT"]});
			$grid.jqGrid('footerData','set',{HGH_PRTY_WRN_CNT           : mapColSum["HGH_PRTY_WRN_CNT"]});
			$grid.jqGrid('footerData','set',{NRML_PRTY_WRN_CNT               : mapColSum["NRML_PRTY_WRN_CNT"]});
			$grid.jqGrid('footerData','set',{LOW_PRTY_WRN_CNT         : mapColSum["LOW_PRTY_WRN_CNT"]});
			$grid.jqGrid('footerData','set',{NEW_WRN_CNT   			 		: mapColSum["NEW_WRN_CNT"]});
			$grid.jqGrid('footerData','set',{TTL_WRN_CNT                      : mapColSum["TTL_WRN_CNT"]});
			$grid.jqGrid('footerData','set',{REMAIN_WRN_CNT               : mapColSum["REMAIN_WRN_CNT"]});
			$grid.jqGrid('footerData','set',{FRST_HALF_CNT				 	  : mapColSum["FRST_HALF_CNT"]});
			$grid.jqGrid('footerData','set',{SCND_HALF_CNT                  : mapColSum["SCND_HALF_CNT"]});
			$grid.jqGrid('footerData','set',{THRE_MNTH_CNT                 : mapColSum["THRE_MNTH_CNT"]});
			$grid.jqGrid('footerData','set',{ONE_MNTH_CNT                  : mapColSum["ONE_MNTH_CNT"]});
			$grid.jqGrid('footerData','set',{ONE_WEEK_CNT                   : mapColSum["ONE_WEEK_CNT"]});

//			var $footRow = $("#dataTables").closest(".ui-jqgrid-bdiv").next(".ui-jqgrid-sdiv").find(".footrow");
//			var $self = $(this);
//			$self.jqGrid(
//                    "footerData",
//                    "set",
//                    {
//                    	JOB_NM: "Total:"
//                    },
//                    false
//                );
		}
	});

	tableRef.jqGrid('setGroupHeaders', {
	  useColSpanStyle: true,
	  groupHeaders:[{startColumnName: 'REMAIN_WRN_CNT', numberOfColumns: 4, titleText: '미처리 건수'},
	                	 {startColumnName: 'FXD_WRN_CNT', numberOfColumns: 6, titleText: '개선 건수'}	]
	});

	$('.ui-th-ltr').css('text-align','center');
	$('.ui-th-ltr').css('vertical-align','middle');
}
