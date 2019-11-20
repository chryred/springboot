var g_dataTable;

$(document).ready(function() {
	fn_start();
	fn_initDataTable();
	fn_eventDataTable();
});

$.ajaxSetup({  
    global: false
}); 

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 290;

var tableTemplateHtml = null;
var tableRef = null;
var dataTable = null;
var searchBtnRef = null;
var obj_saveBtnRef = null;
var dbNameComboRef = null;
var ownerComboRef = null;
var infoTypeInputRef = null;
var exceptFlagComboRef = null;

/***********************
 * [공통] 초기화 
 ***********************/
function fn_start() {
	obj_saveBtnRef = $("#btn_save"); // 저장버튼
	//fn_staticBindEvent(); // 이벤트 바인딩
	
	/*tableRef = $("#dataTables");
	searchBtnRef = $(".search-btn");
	saveBtnRef = $(".save-btn");
	dbNameComboRef = $("#dbName");
	ownerComboRef = $("#owner");
	infoTypeInputRef = $("#infoType");
	exceptFlagComboRef = $("#exceptFlag");
	
	
	initComboDBName();
	initComboOwner();
	initTable();
	var clipboard = new Clipboard('.btn');*/
}

/*
 * 정적DOM 이벤트 바인딩
 */
function fn_staticBindEvent() {
	/*
	//조회버튼 클릭
	searchBtnRef.on("click", function() {
		searchTable();
	});
	*/
	
	//제외버튼 클릭
	obj_saveBtnRef.on("click", function() {
		fn_save();
	});
	
	dbNameComboRef.on("change", function() {
		initComboOwner();
	});
	
	$(window).resize(function() {
		var tableWidth = $(".table-wrapper").css("width");
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", tableWidth);
		$(".ui-jqgrid-hdiv").css("width", tableWidth);
		$(".ui-jqgrid-bdiv").css("width", tableWidth);
		
		$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
	});
}

/***********************
 * [데이터테이블] 초기화 
 ***********************/
function fn_initDataTable() {	
	$("#dataTable").dataTable({
		responsive : true
	});	
	
	g_dataTable = $("#dataTable");
}

/***********************
 * [데이터테이블] 이벤트 
 ***********************/
function fn_eventDataTable() {
	
}