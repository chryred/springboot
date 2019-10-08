$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 355;

var tableRef 				= 	null;
var searchBtnRef 			= 	null;
var effectComboRef 			= 	null;
var effectRangeComboRef 	= 	null;
var targetSystemComboRef 	= 	null;
var statusComboRef 			= 	null;
var defectGradeComboRef 	= 	null;
var periodRef 				= 	null;

/*
 * 초기화
 */
function init () {
	tableRef 				= 	$("#jqGridTable")	;
	searchBtnRef 			= 	$(".btn_search")	;
	effectComboRef 			= 	$("#effect")		;
	effectRangeComboRef 	= 	$("#effectRange")	;
	targetSystemComboRef 	= 	$("#targetSystem")	;
	statusComboRef 			= 	$("#status")		;
	defectGradeComboRef 	= 	$("#defectGrade")	;
	periodRef				= 	$("#period")		;
	
	initCombo('effect'			, 	effectComboRef)			;
	initCombo('effectRange'		, 	effectRangeComboRef)	;
	initCombo('targetSystem'	, 	targetSystemComboRef)	;
	initCombo('status'			, 	statusComboRef)			;
	initCombo('defectGrade'		, 	defectGradeComboRef)	;
	
	staticBindEvent();
	initTable();
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	
	$(window).on('load resize', function() {
		var tableWidth 		= 	$(".table-wrapper").width();
		var contentsWidth 	= 	$(".table-wrapper").width() - 2;
		
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", contentsWidth);
		$(".ui-jqgrid-hdiv").css("width", contentsWidth);
		$(".ui-jqgrid-bdiv").css("width", contentsWidth);
		$(".ui-jqgrid-pager").css("width", contentsWidth);
		$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL));
	});
	
	searchBtnRef.on('click', function() {
		searchTable();
	});
}

/*
 * 그리드 조회
 */
function searchTable () {
	var temp = null;
	
	tableRef.setGridParam({
		postData: {
			effect			: 	effectComboRef.val()		,
			effectRange		: 	effectRangeComboRef.val()	,
			targetSystem	: 	targetSystemComboRef.val()	,
			status			: 	statusComboRef.val()		,
			defectGrade		:	defectGradeComboRef.val()	,
			period			:	periodRef.val()
		}
	}).trigger("reloadGrid");
}

/*
 * 그리드 초기화
 */

function initTable () {
	var temp = null;
	var contentsWidth 	= 	$(".table-wrapper").width() - 2;
	
	tableRef.jqGrid({
		url			: 	contextPath + "/defect/ajax/list.do",
		datatype	: 	"json",
		mtype		: 	"POST",
		jsonReader: {
			id: "SEQ"
		},
		postData: {
			effect			: 	effectComboRef.val()		,
			effectRange		: 	effectRangeComboRef.val()	,
			targetSystem	: 	targetSystemComboRef.val()	,
			status			: 	statusComboRef.val()		,
			defectGrade		:	defectGradeComboRef.val()	,
			period			:	periodRef.val()
		},
		colNames: [ "등록일자","등록순번", "등록자 사번", "장애등급", "대상시스템", "업무영향", "장애시스템종류", "장애시스템범위", "장애발생시각", "장애종료시각","장애보고시각", "장애보고내용", "현재상태" ],
		colModel : [{
			width	: 	contentsWidth * (6.6/100)  ,
			id		: 	"ENT_DATE"	,	
			name	: 	"ENT_DATE"	,
			index	: 	"ENT_DATE"	,
			align	:	"center"
		},{
			width	: 	contentsWidth * (3.3/100)	,
			id		: 	"SEQ"		,	
			name	: 	"SEQ"		,
			index	: 	"SEQ"		,
			align	:	"center"
		}, {
			width	: 	contentsWidth * (5.3/100)	,
			id		: 	"ENT_EMPNO"	,	
			name	: 	"ENT_EMPNO"	,
			index	: 	"ENT_EMPNO"	,
			align	:	"center"
		}, {
			width	: 	contentsWidth * (5/100) ,
			id		: 	"GRADE"	,
			name	:	"GRADE"	,
			index	: 	"GRADE"	,
			align	:	"center"
		}, {
			width	: 	contentsWidth * (8/100)		,
			id		: 	"TARGET"	,
			name	:	"TARGET"	,
			index	: 	"TARGET"	,
			align	:	"center"
		}, {
			width	: 	contentsWidth * (10/100)		,
			id		:	"EFFECT"	,
			name	: 	"EFFECT"	,
			index	: 	"EFFECT"	,
			align	:	"center"
		},{
			width	: 	contentsWidth * (6.6/100)	,
			id		:	"SYS_TYPE"	,
			name	: 	"SYS_TYPE"	,
			index	: 	"SYS_TYPE"	,
			align	:	"center"
		},  {
			width	: 	contentsWidth * (6.6/100)	,
			id		:	"RANGE"		,
			name	: 	"RANGE"		,
			index	: 	"RANGE"		,
			align	:	"center"	
		}, { 
			width	: 	contentsWidth * (8.9/100)	,
			id		:	"START_DATE"	,
			name	:	"START_DATE"	,
			index	: 	"START_DATE"	,
			align	:	"center"
		}, {
			width	: 	contentsWidth * (8.9/100)	,
			id		:	"END_DATE"	,
			name	: 	"END_DATE"	,
			index	: 	"END_DATE"	,
			align	:	"center"
		}, {
			width	: 	contentsWidth * (8.9/100)	,
			id		:	"REPORT_DATE"	,
			name	: 	"REPORT_DATE"	,
			index	: 	"REPORT_DATE"	,
			align	:	"center"
		}, {
			width	: 	contentsWidth * (12.5/100)	,
			id		:	"REPORT_MSG"	,
			name	: 	"REPORT_MSG"	,
			index	: 	"REPORT_MSG"	,
			align	:	"center"
		}, {
			width	:	contentsWidth * (8/100)	,
			id		:	"STATUS"	,
			name	: 	"STATUS"	,
			index	: 	"STATUS"	,
			align	:	"center"			
		}],
		shrinkToFit	: 	false,
		//width		: 	$('.table-wrapper').width() - 18,
		height		: 	$(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		rowNum		:	50,
        rowList		:	[25,50,100],
        pager 		: 	'#pager',
		loadonce	: 	false,
		multiselect	:	false,
		
		loadComplete:	function() {
			var ids = $("#jqGridTable").getDataIDs();
			
			   
			$.each(
				ids,function(ids, rowId){
					rowData = $("#jqGridTable").getRowData(rowId);
					
					if(rowData.STATUS=="등록"){					
						$("#jqGridTable").jqGrid("setCell",rowId,"STATUS",'',{background:"#EAEAEA"});

					}
					else if(rowData.STATUS=="처리완료"){
						$("#jqGridTable").jqGrid("setCell",rowId,"STATUS",'',{background:"#CEFBC9"});
					}
					else if(rowData.STATUS=="미처리종료"){
						$("#jqGridTable").jqGrid("setCell",rowId,"STATUS",'',{background:"#FFD8D8"});

					}
						
				}	
			);
		},
		
		onCellSelect: function(rowId, iCol){
        	var data = $(this).jqGrid('getRowData', rowId);
			if(iCol == 12){
				$('#rowId').val(rowId);
				$('#myModal').modal('show');				
			}
        }
	});
	

}

