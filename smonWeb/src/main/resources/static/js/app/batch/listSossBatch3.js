var g_dataTable;

$(document).ready(function() {
	fn_start();
	fn_staticBindEvent();
	//fn_eventDataTable();
	fn_searchTree();
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

var defaultData = [ {
	text : 'Parent 1',
	href : '#parent1',
	tags : [ '4' ],
	itemKey : "P1",
	nodes : [ {
		text : 'Child 1',
		href : '#child1',
		tags : [ '2' ],
		itemKey : "C1",
		nodes : [ {
			text : 'Grandchild 1',
			href : '#grandchild1',
			tags : [ '0' ]
		}, {
			text : 'Grandchild 2',
			href : '#grandchild2',
			tags : [ '0' ]
		} ]
	}, {
		text : 'Child 2',
		href : '#child2',
		tags : [ '0' ]
	} ]
}, {
	text : 'Parent 2',
	href : '#parent2',
	tags : [ '0' ]
}, {
	text : 'Parent 3',
	href : '#parent3',
	tags : [ '0' ]
}, {
	text : 'Parent 4',
	href : '#parent4',
	tags : [ '0' ]
}, {
	text : 'Parent 5',
	href : '#parent5',
	tags : [ '0' ]
} ];


function fn_searchTree() {
	var $searchableTree = $('#treeview-searchable').treeview({
		data : defaultData
	});
	
	/******************************************
	 * BootStrap TreeView 이벤트 종류
	    nodeChecked (event, node)  - A node is checked.
 		nodeCollapsed (event, node)  - A node is collapsed.
 		nodeDisabled (event, node)  - A node is disabled.
 		nodeEnabled (event, node)  - A node is enabled.
 		nodeExpanded (event, node)  - A node is expanded.
 		nodeSelected (event, node)  - A node is selected.
 		nodeUnchecked (event, node)  - A node is unchecked.
 		nodeUnselected (event, node)  - A node is unselected.
 		searchComplete (event, results)  - After a search completes
 		searchCleared (event, results)  - After search results are cleared
	 *******************************************/
	
	$('#treeview-searchable').on('nodeSelected', function(event, data) {
		//alert(JSON.stringify(data));
		
		alert(data.itemKey);
	});
	// Node to cancel the selected trigger
	$('#treeview-searchable').on('nodeUnselected', function(event, data) {
		//alert(2);
	});


	
}

// Last trigger node Id
var lastSelectedNodeId = null;
// Last time trigger 
var lastSelectTime = null;


function clickNode(event, data) {
	if (lastSelectedNodeId && lastSelectTime) {
		var time = new Date().getTime();
		var t = time - lastSelectTime;
		if (lastSelectedNodeId == data.nodeId && t < 300) {
			customBusiness(data);
		}
	}
	lastSelectedNodeId = data.nodeId;
	lastSelectTime = new Date().getTime();
}

function customBusiness(data) {
	alert(" Double click to get the node name ： " + data.text);
}




/*******************************************************************************
 * [공통] 초기화
 ******************************************************************************/
function fn_start() {
	
	obj_saveBtnRef = $("#btn_search"); // 저장버튼
	searchBtnRef = $(".search-btn");
	// fn_staticBindEvent(); // 이벤트 바인딩
	
	/*
	 * tableRef = $("#dataTables"); searchBtnRef = $(".search-btn"); saveBtnRef =
	 * $(".save-btn"); dbNameComboRef = $("#dbName"); ownerComboRef =
	 * $("#owner"); infoTypeInputRef = $("#infoType"); exceptFlagComboRef =
	 * $("#exceptFlag");
	 * 
	 * 
	 * initComboDBName(); initComboOwner(); initTable(); var clipboard = new
	 * Clipboard('.btn');
	 */
}

/*
 * 정적DOM 이벤트 바인딩
 */
function fn_staticBindEvent() {
	// 조회버튼 클릭
	$("#btn_search").on("click", function() {
		fn_search();
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
 * [공통] 조회 
 ***********************/
function fn_search() {
	var postData = $("#form1").serializeArray();
	var formURL = contextPath + "/batch/listSossBatchJson.do";
	//$("#form1").attr("action");
	
	$.ajax({
		url : formURL,
		type : "POST",
		data : postData,
		dataType : "json", //text, json, html, xml, csv, script, jsonp
		async : true,
		timeout : 2 * 60 * 1000, //2 min,
		beforeSend : function() {
		},
		complete : function() {
		},
		success : function(result) {		
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기

			//로그인 처리시
			if (v_chkResult == "OK") {
				//alert("정상처리 되었습니다.");
				var v_data = makeGanttData(result.rstList);
				
				makeGantt(v_data);
			} else {
				alert("에러가 발생하였습니다.")
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
		}
	});
}

/**************************
 * 간트 차트 Data 만들기
 **************************/
function makeGanttData(data) {
	var v_dataArray = new Array();
	
	$.each(data, function (key, item) {
		//alert("key : " + key + "item : " + item);
		var v_obj = new Object();
		var v_name = item.jobName;
		var v_desc = "설명";
		var v_valueVOs = new Array();
		var v_valueVO = new Object();
		
		//alert(item.runStartDate + item.runStartTime);
		
		//v_valueVO.from = "/Date(" + getUnixTime(item.runStartDate + item.runStartTime) + ")/";
		//v_valueVO.to = "/Date(" + getUnixTime(item.runEndDate + item.runEndTime) + ")/";
		v_valueVO.from = "/Date(" + getUnixTime("20170815230000") + ")/";
		v_valueVO.to = "/Date(" + getUnixTime("20170816220000") + ")/";
		v_valueVO.label = ".";
		v_valueVO.customClass = "ganttRed";
		
		var startUnixTime = getUnixTime("20170816010000");
		var startDateTime = new Date(getUnixTime("20170816130000"));
		
		//alert("unixTime : " + startUnixTime + "\ndateTime : " + startDateTime.getHours());
		//alert("unixTime : " + startUnixTime + "\ndateTime : " + startDateTime.getHours());
		
		v_valueVOs.push(v_valueVO);
		
		v_obj.name = v_name;
		v_obj.desc = v_desc;
		v_obj.values = v_valueVOs;
		
		v_dataArray.push(v_obj);
	});
	
	return v_dataArray;
}

function getUnixTime(str) {
	//alert(str.substr(0, 4) + str.substr(4, 2) + str.substr(6, 2) + str.substr(8, 2) + str.substr(10, 2) + str.substr(12, 2));
	var v_year = str.substr(0, 4);
	var v_month = parseInt(str.substr(4, 2))-1;
	var v_day = str.substr(6, 2);
	var v_hh24 = str.substr(8, 2);
	var v_mi = str.substr(10, 2);
	var v_ss = str.substr(12, 2);
	
	return new Date(v_year, v_month, v_day, v_hh24, v_mi, v_ss).getTime();
}

/**************************
 * 간트 차트 만들기
 **************************/
function makeGantt(p_data) {
	alert(JSON.stringify(p_data));
	"use strict";
	
	var date = new Date();

	$(".gantt").gantt({
		source: p_data/*
			[{
			name: "J_BULD_MST",
			desc: "설명",
			values: [{
				from: "/Date(1320192000000)/",
				to:   "/Date(1320202200000)/",
				label: "2", 
				customClass: "ganttRed"
			}]
			
		}]
		*/,
		navigate: "scroll",
		scale: "hours",
		maxScale: "days",
		minScale: "hours",
		itemsPerPage: 15,
		onItemClick: function(data) {
			alert("Item clicked - show some details");
		},
		onAddClick: function(dt, rowId) {
			alert("Empty space clicked - add an item!");
		},
		onRender: function() {
			if (window.console && typeof console.log === "function") {
				console.log("chart rendered");
			}
		}
	});

	$(".gantt").popover({
		selector: ".bar",
		title: "I'm a popover",
		content: "And I'm the content of said popover.",
		trigger: "hover"
	});
	//prettyPrint();
}