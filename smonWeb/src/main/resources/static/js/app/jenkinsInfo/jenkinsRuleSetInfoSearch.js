
var gScrtyTypeCd = "P";
var tableRef;
var CONTENTS_HEIGHT_MINUS_PIXEL = 290;
var gColNames;
var gColModel;

$(document).ready(function() {
	fn_event();
	initialize();
});

$.ajaxSetup({
    global: false
});

function initialize() {
	tableRef = $("#dataTables");
	fn_searchJenkinsResigerSystem();

}

/***********************
 * [공통] 이벤트
 ***********************/
function fn_event() {
	$("select#scrtyTypeCd").change(function(){
        var select_name = $(this).children("option:selected").text();
        $(this).siblings("label").text(select_name);
        gScrtyTypeCd = $(this).val();
        fn_searchJenkinsResigerSystem();
    });

	$("#jobName").on("keydown", function(e) {
		if(e.keyCode == 13) {
			fn_search();
		}
	});
	$("#btn_search").on("click", function() {
		fn_search();
	});

//	$(window).bind('resize', function() {
//		tableRef.setGridHeight($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL);
//	})
//	.trigger('resize');
}

/***********************
 * 등록 시스템 조회
 ***********************/
function fn_searchJenkinsResigerSystem() {
	var totCnt = 0;
	var fixedCnt  = 0;
	var newCnt = 0;
	var trtmRate = 0;

	gColModel = [];
	gColNames =  ["룰셋명", "우선순위", "추가순위", "룰셋적용일자"];
	var postData = {
		scrtyTypeCd : gScrtyTypeCd
	};
	var formURL = contextPath + "/jekinsInfo/jenkinsRegisteredSystem.do";

	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json",
		async : true,
		timeout : 2 * 60 * 1000, //2 min,
		beforeSend : function() {
			$.blockUI({ css: {color: '#fff'} });
		},
		complete : function() {
			$.unblockUI();
		},
		success : function(result) {
			$.unblockUI();

			for(var nIdx = 0; nIdx < result.length; nIdx++) {
				gColNames.push(result[nIdx].JOB_NM);
			}
			gColNames.push("합 계");
			createColModel(result);
			// 테이블 초기화
			initTable();
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

function createColModel(systemList) {
	gColModel.push(
			{ width: "330px", id: "RULE_NM", name: "RULE_NM", index: "RULE_NM", align: 'left', frozen: true,
				 cellattr: function(rowId, tv, rowObject, cm, rdata) {
				 	// high는 red, normal : yellow, low : black
	                if (rowObject.PRTY_SBST == 'high'){
	                    return 'style="font-weight: bold; color:red; vertical-align:middle"';
	                }
	                else if (rowObject.PRTY_SBST == 'normal') {
	                    return 'style="font-weight: bold; color:blue; vertical-align:middle"';
	                }
	                else{
	                    return 'style="font-weight: bold; color:black; vertical-align:middle"';
	                }
	            },
	            formatter:function(val, ref, row) {
	            	if(row.ADD_RANK == 1) {
	            		return val + '&nbsp;<img src="' + contextPath +'/images/icon/new.gif">';
	            	} else {
	            		return val;
	            	}
	            }
	        },
			{ width: "100px", id:"PRTY_SBST", name: "PRTY_SBST", index: "PRTY_SBST", align: 'right', hidden: true },
			{ width: "100px", id:"ADD_RANK", name: "ADD_RANK", index: "ADD_RANK", formatter:"integer", align: 'right', hidden: true },
			{ width: "140px", id:"APLY_DT", name: "APLY_DT", index: "APLY_DT", formatter: 'date', formatoptions: { srcformat: "Y/m/d H:i:s", newformat: "Y/m/d H:i:s"}, align: 'center' }
			/*
			{ width: "110px", id:"NRML_PRTY_WRN_CNT", name: "NRML_PRTY_WRN_CNT", index: "NRML_PRTY_WRN_CNT",
				formatter: function(val, ref, row) {
					if(row["JOB_NM"] == undefined) {
						return comma(val);
					}
					var rowId = ref.rowId;
					var title = "Jenkins Normal Level 화면으로 이동";
					return '<a href="' + row.JOB_URL + '/NORMAL" target="_blank" title="' + title + '">'  + comma(val) +'</a>';
				}, unformat: function(val, ref, row) {
					return val.replace(/,/g, "");
				}, align: 'right' }
			*/
	);

	// 시스템 정보 추가
	for(var nIdx = 0; nIdx < systemList.length; nIdx ++) {
		gColModel.push(
			{ width: (systemList[nIdx].JOB_NM.length * 3 + 100) + "px", id: "COL" + nIdx, name: "COL" + nIdx, index: "COL" + nIdx, formatter:"integer", align: 'right' }
		);
	}
	gColModel.push(
		{ width: "100px", id: "TOTAL_CNT", name: "TOTAL_CNT", index: "TOTAL_CNT", formatter:"integer", align: 'right' }
	);
//	console.log(gColModel);
}

/*
 * 그리드 초기화
 */
function initTable () {
	if(tableRef["GridUnload"] != undefined) {
		tableRef.jqGrid("clearGridData");
		tableRef.jqGrid("GridUnload");
		tableRef = $("#dataTables");
	}

	tableRef.jqGrid({
		url: contextPath + "/jekinsInfo/jenkinsRuleSetInfoList.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			scrtyTypeCd : gScrtyTypeCd
		},
		colNames: gColNames,
		colModel : gColModel,
		shrinkToFit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		loadonce: false,
		pager: false,
		rowNum: -1,
//		footerrow: true,
//		userDataOnFooter: true,
//		sortable: true,
		rownumbers: true,
		height: "auto",
		gridview: true,
		resizable: true,
		caption: "시스템별 룰셋 정보",
		loadComplete : function() {
			/*
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
			$grid.jqGrid('footerData','set',{BLD_DT                 : '등록작업수: ' + comma(totTalJobCnt)});
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

			var $footRow = $("#dataTables").closest(".ui-jqgrid-bdiv").next(".ui-jqgrid-sdiv").find(".footrow");
			var $self = $(this);
			$self.jqGrid(
                    "footerData",
                    "set",
                    {
                    	JOB_NM: "Total:"
                    },
                    false
                );
        */
			gridResize("dataTables"); // window resize에 등록
		    setGridResize("dataTables"); // 최초 그리드 사이즈 조정
		}
	});
	tableRef.jqGrid('setFrozenColumns');
}

/*
 * 그리드 리사이즈 셋팅
   - GridParam 중 width 관련한 값 (autowidth, width) 는 주석처리

 * 주의사항
   - gridResize 함수 중 $("#cont").width() 부분은 그리드가 그려질 div 너비를 가져오는 것임...
   - setGroupHeaders 를 설정 할 경우 리사이즈 오작동함 (setGroupHeaders 를 사용하지 않으면 정상작동함, 아래 주석 무시)
    - colModel에 resizeble:false를 설정하거나, resizableFlag 변수에 false를 대입,
    - shrinkFlag 를 false로 설정해서 리사이즈 대신 박스만 리사이즈하고 내부 스크롤생성방법으로 변경
 */

//그리드 리사이즈
var preGridWidth = 0;  //
var preGridHeight = 0; // 최소 그리드 사이즈
var prewindowHeight = 0; // 이전 창 크기
var shrinkFlag = true; // true : 사이즈조정, false : 사이즈고정, 스크롤 생성

function setGridResize(gridId) {
	var windowWidth = $(".ui-jqgrid-view").width(); // 창크기
	var newGridWidth = windowWidth - 1; // 그리드의 새로운 width
	var windowHeight = $(window).height(); // 창크기
	var newGridHeight = windowHeight - 350; // 그리드의 새로운 width

    if (preGridWidth != newGridWidth) {
//    	$('#' + gridId).setGridWidth(newGridWidth, false);
    }

    // 그리드에 적용할 width가 최대크기보다 작고 and 그리드에 적용할 width가 최소 크기보다 크고
    if (preGridHeight != newGridHeight) {
        $('#' + gridId).setGridHeight(newGridHeight, shrinkFlag);
    }

    preGridWidth = newGridWidth;
    preGridHeight = newGridHeight;
}

function gridResize(gridId) {
    preGridWidth = $(".ui-jqgrid-view").width();
    preGridHeight = $(".ui-jqgrid-view").height();

    $(window).resize(function() {
        setGridResize(gridId);
    });
}
