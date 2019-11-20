var g_page = 1;
var g_page2 = 1;
var g_intervalId;
var g_colid;
var g_sorting;
var g_chkRank = 1;
var g_chkList;
var table;

var obj_searchBtnRef;
var obj_saveBtnRef;

$(document).ready(function() {
	fn_start();
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
	//fn_setDateCombo();	
	obj_searchBtnRef = $("#btn_search"); // 저장버튼
	obj_saveBtnRef = $("#btn_save"); // 저장버튼
	
	fn_event(); // 이벤트 바인딩	
	//fn_search();
}

/*
 * 정적DOM 이벤트 바인딩
 */
function fn_event() {
	// 토글버튼
	$('#chk_toggle').change(function() {
		if ($(this).prop('checked')) {
			g_chkRank = 1;
		} else {
			g_chkRank = null;
		};
		//fn_search(); 
	});
	/*
	$("#dataTable thead tr th").click(function () {
		$("#dataTable thead tr th").not(this).removeClass("sorting");
		$("#dataTable thead tr th").not(this).removeClass("sorting_asc");
		$("#dataTable thead tr th").not(this).removeClass("sorting_desc");
		$("#dataTable thead tr th").not(this).addClass("sorting"); 
		
		if ($(this).hasClass("sorting")) {
			g_sorting = "ASC";
			$(this).removeClass("sorting");
			$(this).addClass("sorting_asc");
		} else if ($(this).hasClass("sorting_desc")) {
			g_sorting = "ASC";
			$(this).removeClass("sorting_desc");
			$(this).addClass("sorting_asc");
		} else if ($(this).hasClass("sorting_asc")) {
			g_sorting = "DESC";
			$(this).removeClass("sorting_asc");
			$(this).addClass("sorting_desc");
		} else {
			g_sorting = "ASC";
			$(this).addClass("sorting_asc");
		}
		
		var v_colidx = $(this).attr("colidx");
		
		switch (v_colidx) {
			case "0" :
				g_colid = "SYSTEM_NAME";
				break;
			case "1" :
				g_colid = "PROJECT_NAME";
				break;
			case "2" :
				g_colid = "CHK_YMD";
				break;				
			case "3" :
				g_colid = "CHK_DGR";
				break;
			case "4" :
				g_colid = "PMD_CNT";
				break;
		}
		
		//alert(g_colid + g_sorting);
		
		//fn_search();
		
	}); 
	*/

	
	//조회버튼 클릭
	obj_searchBtnRef.on("click", function() {
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


$.fn.gridCheck = function(parent, target, elements, callback) {
	console.log(target);
	var $this = $(this);
	var $element = $("#" + $this.attr("id") + ("_wrapper")).find("#" + parent);
	$element.prop("checked", false);
	// 전체선택 클릭
	$element.unbind("click").bind("click", function() {
		var checked = $element.is(':checked');
		$(target).prop("checked", checked);
		$(target).each(function() {
			if (checked) {
				$(this).parents('tr:first').addClass("selected");
			} else {
				$(this).parents('tr:first').removeClass("selected");
			}
		});
		
		getCheckData($this, null);
	});
	
	// 개별선택 클릭
	$(target).each(function() {
		$(this).unbind("click").bind("click", function() {
			var checked = $(this).is(':checked');
			var checkAll = true;
			$(target).each(function() {
				if (!$(this).is(':checked')) {
					checkAll = false;
					return false;
				}
			});
			if (checked) {
				$(this).parents('tr:first').addClass("selected");
			} else {
				$(this).parents('tr:first').removeClass("selected");
			}
			$element.prop("checked", checkAll);
			this.blur();
		});
		
		var checked = $(this).is(':checked');
		var checkAll = true;
		$(target).each(function() {
			if (!$(this).is(':checked')) {
				checkAll = false;
				return false;
			}
		});
		if (checked) {
			$(this).parents('tr:first').addClass("selected");
//		} else {
//			$(this).parents('tr:first').removeClass("selected");
		}
		$element.prop("checked", checkAll);
	});
	
}



/***********************
 * [공통] 조회
 ***********************/
function fn_search() {
	/*
	var gridConfig =  [
		{ title: "번호", data: "rnum", className:"center", width: "30"},
		{ title: "<input type='checkbox' id='checkAll'>", className:"center", width: "20", orderable : false},
		{ title: "revision", data: "revision", className : "center", width: "50" }
	];
	
	*/
	$('#dataTable').dataTable().fnClearTable();
    $('#dataTable').dataTable().fnDestroy();
    $('#dataTable2').dataTable().fnClearTable();
    $('#dataTable2').dataTable().fnDestroy();
    
    $.blockUI({ css: {color: '#fff'} });
	
	$('#dataTable').DataTable({
        select: {
        	style: 'single'
        },
        pageLength: 20,
        bInfo : false,
        bSort: false,
        bPaginate: false,
        bLengthChange: false,
        bAutoWidth: false,
        processing: false,
        ordering: false,
        order : [],
        bServerSide: false,
        searching: false,
        scrollY: "295px",
        dom: 'Bfrtip',
        buttons: [
        	'copyHtml5',
            'excelHtml5'
        ], ajax: {
        	  url : contextPath + "/tool/getSvnHistoryList.do"
        	, data : function(d){
      			d.pageScale = $("#pageScale").val();
      			d.curPage = g_page;
      			d.systemCode = $("#systemCode").val();
      			d.comment = $("#comment").val();
      		}
        }, initComplete : function(settings, result) {
        	fn_pageDiv('dataTable', result, 0); // 페이지 처리
        	
        	$(".dataTable thead tr th").removeClass("sorting_asc");
        	
        	$.unblockUI();
        }, columnDefs: [
        	  {width : "10%", targets : 0}
            , {width : "10%", targets : 1, render : 
					function (data, type, row) {
						var checked = "";
						if (row.chk == 1) {
							checked = "checked";
						} else {
							checked = "";
						}
						return "<input type='checkbox' data-checkAll='true' " + checked + " value=\"" + row.revision + "\" onClick=\"fn_clickCheck(this);\">";
					}
              }
            , {width : "10%", targets : 2}
            , {width : "15%", targets : 3}
            , {width : "10%", targets : 4}
            , {width : "15%", targets : 5}
            , {width : "30%", targets : 6, render : 
	            	function (data, type, row) {
						return "<textarea rows=\"1\" style=\"width:100%\">" + row.comment + "</textarea>";
					}
              }
        ], columns: [
        	  {title : "순번", data: "rnum", className: "text-center", orderable : false}
        	, {title : "<input type='checkbox' id='checkAll'>", className: "text-center", orderable : false}
        	, {title : "Rivision", data: "revision", className: "text-center"}
        	, {title : "Date", data: "date", className: "text-center"}
        	, {title : "Changes", data: "changes", className: "text-center"}
        	, {title : "Author", data: "author", className: "text-center"}
        	, {title : "Comment", data: "comment", className: "text-left"}
        ], drawCallback : function( settings ) {
        	$("#dataTable").gridCheck("checkAll", $("#dataTable").find("input[data-checkAll='true']:not(:disabled)"));
		}
    });
	
	
}	

/***********************
 * [공통] 조회
 ***********************/
function fn_search2() {
	$("#dataTable thead tr th").removeClass("sorting_asc");
	
	$('#dataTable2').dataTable().fnClearTable();
    $('#dataTable2').dataTable().fnDestroy();
    
    $.blockUI({ css: {color: '#fff'} });
	
	$('#dataTable2').DataTable({
        select: {
        	style: 'single'
        },
        pageLength: 20,
        bInfo : false,
        bSort: false,
        bPaginate: false,
        bLengthChange: false,
        bAutoWidth: false,
        processing: false,
        ordering: false,
        order : [],
        bServerSide: false,
        searching: false,
        scrollY: "295px",
        dom: 'Bfrtip',
        buttons: [
        	'copyHtml5',
            'excelHtml5'
        ], ajax: {
        	  url : contextPath + "/tool/getSvnHistoryDetailList.do"
        	, data : function(d){
      			d.pageScale = $("#pageScale2").val();
      			d.curPage = g_page2;
      			d.systemCode = $("#systemCode").val();
      			d.chkList = JSON.stringify(g_chkList);
      		}
        }, initComplete : function(settings, result) {
        	fn_pageDiv('dataTable2', result, 1); // 페이지 처리
        	
        	$(".dataTable thead tr th").removeClass("sorting_asc");
        	
        	$.unblockUI();
        }, columnDefs: [
        	  {width : "10%", targets : 0}
            , {width : "10%", targets : 1}
            , {width : "10%", targets : 2}
            , {width : "35%", targets : 3, render : 
	            	function (data, type, row) {
						return "<textarea rows=\"1\" style=\"width:100%\">" + row.path + "</textarea>";
					}
              }
            , {width : "35%", targets : 4, render : 
	            	function (data, type, row) {
						return "<textarea rows=\"1\" style=\"width:100%\">" + row.comment + "</textarea>";
					}
              }
        ], columns: [
        	  {title : "순번", data: "rnum", className: "text-center", orderable : false}
        	, {title : "Rivision", data: "revision", className: "text-center"}
        	, {title : "Type", data: "type", className: "text-center"}
        	, {title : "Path", data: "path", className: "text-left"}
        	, {title : "Comment", data: "comment", className: "text-left"}
        ]
    });
}	

/********************************
 * [공통] 페이지변경
 ********************************/
function fn_clickCheck(p_obj) {
	debugger;
	getCheckData($("#dataTable"), null);
}

//checked 된 Row Data
function getCheckData(p_obj, callbackFunc) {
	debugger;	
	var data = new Array();
	var $this = p_obj;
	$this.find("input[data-checkAll='true']:checked").each(function() {
		data.push($(this).val());
	})
	if(callbackFunc){
		callbackFunc(data);
	}
	
	g_chkList = data;
	
	fn_search2();
	
	return data;
}


function list(p_page, p_target) {
	debugger;
	if (p_target == "dataTable") {
		g_page = p_page;
		fn_search();
	} else if (p_target == "dataTable2") {
		g_page2 = p_page;
		fn_search2();
	}
	
	
}

/********************************
 * [공통] 페이징
 ********************************/
function fn_pageDiv(p_target, p_result, p_idx) {
	debugger;
	var p_obj = p_result.pageVO;
	//var p_paramCondition = p_result.paramCondition;
	var p_count = p_result.count;
	var p_start = p_result.start;
	var p_end = p_result.end;
	
	var html = '';
	html += '<nav aria-label="Page navigation example">';
	html += '<ul class="pagination justify-content-center">';
	html += '<!-- **처음페이지로 이동 : 현재 페이지가 1보다 크면  [처음]하이퍼링크를 화면에 출력-->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'1\', \'' + p_target + '\')">처음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">처음</a></li>';
	}
	html += '<!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.prevPage + '\', \'' + p_target + '\')">이전</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">이전</a></li>';
	}
	html += '<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->'
	
	for (var idx=p_obj.blockBegin;idx<=p_obj.blockEnd;idx++) {
		if (p_obj.curPage == idx) {
			html += '<!-- **현재페이지이면 하이퍼링크 제거 -->';
			html += '<li class="page-item"><a class="page-link" style="background-color:#EAEAEA;"href="#"><span style="color:red;">' + idx + '</span></a></li>';
		} else {
			html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + idx + '\', \'' + p_target + '\')">' + idx + '</a></li>';
		}
	}

	html += '<!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.nextPage + '\', \'' + p_target + '\')">다음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:list(\'' + p_obj.nextPage + '\', \'' + p_target + '\')">다음</a></li>';
	}
	
	html += '<!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.totPage + '\', \'' + p_target + '\')">끝</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:list(\'' + p_obj.totPage + '\', \'' + p_target + '\')">끝</a></li>';
	}
	
	html += '</ul>';
	html += '</nav>';
	
	$("#div_page_" + p_idx).html(html);
	$("#div_count_" + p_idx).html('<span>Showing ' + p_start + ' to ' + p_end + ' of ' + p_count + ' entries</span>');
}

function comma(num){
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


