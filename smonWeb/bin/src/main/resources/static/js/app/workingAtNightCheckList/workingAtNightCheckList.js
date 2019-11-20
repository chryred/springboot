$(document).ready(function() {
	init();
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
var saveBtnRef = null;
var dbNameComboRef = null;
var ownerComboRef = null;
var infoTypeInputRef = null;
var exceptFlagComboRef = null;
var msgComboRef = null;
/*
 * 초기화
 */
function init () {
	
	tableRef = $("#dataTables");
	searchBtnRef = $(".search-btn");
	saveBtnRef = $(".save-btn");
	dbNameComboRef = $("#dbName");
	ownerComboRef = $("#owner");
	msgComboRef = $("#exceptFlag");
	infoTypeInputRef = $("#infoType");
	exceptFlagComboRef = $("#exceptFlag");
	
	staticBindEvent();
	initComboDBName();
	initComboOwner();
	//initComboMsg();
	initTable();
	var clipboard = new Clipboard('.btn');
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	//조회버튼 클릭
	searchBtnRef.on("click", function() {
		searchTable();
	});
	
	//제외버튼 클릭
	saveBtnRef.on("click", function() {
		var updateDatas = [];
		
		var rowDatas = tableRef.getRowData();
		_.each(rowDatas, function(rowData, idx) {
			var personalInfoFlag = $('#pinfo_' + (idx+1)).prop("checked") ? "N" : "";
			var memo = $('#memo_' + (idx+1)).val();
			updateDatas.push({
				dbName: rowData.DB_NAME,
				checkGubn: rowData.CHECK_GUBN,
				owner: rowData.OWNER,
				tableName: rowData.TABLE_NAME,
				columnName: rowData.COLUMN_NAME,
				personinfoFlag: personalInfoFlag,
				memo: memo
			});
		});
		
		$.ajax({
			url: contextPath + "/workingAtNightCheckList/search/ajax/add/exception.do",
			type: "POST",
			dataType: "json",
			data: {
				exceptionList: JSON.stringify(updateDatas)
			},
			success: function(result) {
				searchTable();
			},
			error: function() {
				alert("에러가 발생했습니다.");
			}
		});
		
	});
	
	dbNameComboRef.on("change", function() {
		initComboOwner();
		//initComboMsg();
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

/*
 * DB 명 콤보박스 옵션 초기화
 */
function initComboDBName() {
	$.ajax({
		url: contextPath + "/workingAtNightCheckList/search/ajax/list/dbName.do",
		type: "POST",
		dataType: "json",
		async: false,
		success: function(data) {
			dbNameComboRef.html("");
			dbNameComboRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				dbNameComboRef.append('<option value="' + option.HOST_NAME + '">' + option.HOST_NAME + '</option>');
			});
		},
		error: function() {
			alert("에러가 발생했습니다.");
		}
	});
}

/*
 * 소유자 콤보박스 옵션 초기화
 */
function initComboOwner() {
	$.ajax({
		url: contextPath + "/workingAtNightCheckList/search/ajax/list/owner.do",
		type: "POST",
		dataType: "json",
		data: {
			dbName: dbNameComboRef.val()
		},
		success: function(data) {
			ownerComboRef.html("");
			ownerComboRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				ownerComboRef.append('<option value="' + option.HOST_IPV4 + '">' + option.HOST_IPV4 + '</option>');
			});
		},
		error: function(err) {
			alert("에러가 발생했습니다.");
		}
	});
}

function initComboMsg() {
	
}

function copyPersonalInfoCheckQuery(row){
    
    $.get(contextPath + "/views/mtr/data/personalInfoCheckQuery.xml", function(data) {
        
        //this.setting.global = false;
        $info = $(data).find(tableRef.jqGrid('getCell', row, 'CHECK_GUBN'));
        
        if($info.length > 0){
            
            var query = $info.find("contents").text();
            
            query = query.split("${COLUMN_NAME}").join(tableRef.jqGrid('getCell', row, 'COLUMN_NAME'));
            query = query.split("${OWNER}").join(tableRef.jqGrid('getCell', row, 'OWNER'));
            query = query.split("${TABLE_NAME}").join(tableRef.jqGrid('getCell', row, 'TABLE_NAME'));
            
            $("#query").val(query);
        }
    });
    
}

//이미지 생성
function formatOpt1(cellvalue, options, rowObject){      
	var str ="";
	//이미지 추가

	  if( cellvalue == "Y" ){ 
			str = "<img src=";
			str += contextPath;
		str += "/images/icons-png/chatting2.png />";
		
	  }else{ 
			//str += "/images/icons-png/no.png";
		  
		}
	  return str;﻿﻿﻿﻿﻿
}

function formatOpt2(cellvalue, options, rowObject){      
	var str ="";
	//이미지 추가

	  if( cellvalue == "Y" ){ 
			str = "<img src=";
			str += contextPath;
		str += "/images/icons-png/mobile-phone.png />";
	  }else{ 
				//str += "/images/icons-png/no.png />";
		}
	  return str;﻿﻿﻿﻿﻿
}

// 버튼 생성`
var rowObj;
function delButton (cellvalue, options, rowObject) {

  var link = '';
  var rowId = options.rowId;
  rowObj = rowObject;
  link = '<button type="button" onclick="addGridCustomer();">'+"   "+'<span class="glyphicon glyphicon-plus"></span>'+"   "+'</button>';
  return link;
};

function addGridCustomer() {
	/*
	console.log(rowObj);
	$.ajax({
		url: contextPath + "/workingAtNightCheckList/search/ajax/list/updatecount.do",
		type: "POST",
		dataType: "json",
		data: {
			count : (rowObj.CNT + 1),
			dbName: rowObj.HOST_NAME,
			owner: rowObj.HOST_IPV4,
			infoType: rowObj.BIZ_NAME
		},
		success: function(result) {
			alert("성공");
			searchTable();
		},
		error: function() {
			alert("에러가 발생했습니다.");
		}
	});*/
/*   $.ajax({ 
     type: "POST", 
     url: contextPath + "/workingAtNightCheckList/search/ajax/updateCount.do", 
     postData: {
			cnt : (rowObj.CNT + 1),
			dbName: rowObj.HOST_NAME,
			owner: rowObj.HOST_IPV4,
			infoType: rowObj.BIZ_NAME
		},  
		datatype: "json",
		mtype: "POST",
     dataType: "json",
     success: function(data) {
    	 tableRef.setGridParam({rowNum:10,datatype:"json" }).trigger('reloadGrid'); 
     }
  });*/
};

/*
 * 그리드 조회
 */
function searchTable () {
	var temp = null;
	tableRef.setGridParam({
		postData: {
			dbName: dbNameComboRef.val() != "" ? dbNameComboRef.val() : null,
			owner: ownerComboRef.val() != "" ? ownerComboRef.val() : null,
			infoType: infoTypeInputRef.val() != "" ? infoTypeInputRef.val() : null,
			exceptFlag: exceptFlagComboRef.val() != "" ? exceptFlagComboRef.val() : null
		}
	}).trigger("reloadGrid");
}

/*
 * 그리드 초기화
 */
function initTable () {
	var lastsel;
	var temp = null;
	tableRef.jqGrid({
		url: contextPath + "/workingAtNightCheckList/search/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		global: false,
		postData: {
			dbName: dbNameComboRef.val() != "" ? dbNameComboRef.val() : null,
			owner: ownerComboRef.val() != "" ? ownerComboRef.val() : null,
			infoType: infoTypeInputRef.val() != "" ? infoTypeInputRef.val() : null
		},
		colNames: [ "호스트명", "IP", "업무 시스템명", "메시지 키워드", "메시지 부가설명", "블톡 연락 여부", "전화 연락 여부", "담당자", "메시지 발생 누계", "메시지 발생 증감","id" ],
		colModel : [{
			width: "120px",
			id:"HOST_NAME",
			name: "HOST_NAME",
			index: "HOST_NAME",
			align: "center"
		}, {
			width: "120px",
			id:"HOST_IPV4",
			name: "HOST_IPV4",
			index: "HOST_IPV4",
			align: "center"
		}, {
			width: "320px",
			id:"BIZ_NAME",
			name: "BIZ_NAME",
			index: "BIZ_NAME",
			align: "center"
		},{
			width: "300px",
			id:"MSG_KEYWORD",
			name: "MSG_KEYWORD",
			index: "MSG_KEYWORD"
		}, {
			width: "360px",
			id:"MSG_EXPLAIN",
			name: "MSG_EXPLAIN",
			index: "MSG_EXPLAIN"
		}, {
			width: "120px",
			id:"TALK_YN",
			name: "TALK_YN",
			index: "TALK_YN",
			align: "center",
			formatter:formatOpt1
		}, {
			width: "120px",
			id:"PHONE_YN",
			name: "PHONE_YN",
			index: "PHONE_YN",
			align: "center",
			formatter:formatOpt2
		}, {
			width: "100px",
			id:"MANAGER_NAME",
			name: "MANAGER_NAME",
			index: "MANAGER_NAME"	,
			align: "center"
		}, {
			width: "100px",
			id: "CNT",
			name: "CNT",
			index: "CNT",
			align: "center"
		}, {
			width: "100px",
			id: "CNT_BUTTON",
			name: "CNT_BUTTON",
			index: "CNT_BUTTON",
			align: "center",
			formatter: delButton 
		}, {
			width: "0px",
			id: "ID_NUMBER",
			name: "ID_NUMBER",
			index: "ID_NUMBER",
			align: "center"
		}],
		autowidth: false,
		shrinkToFit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		loadonce: false,
		pager: false,
		rowNum: -1,
		multiselect: false,
		onCellSelect: function(rowid, iCol, content, event) {
			var rowData = $(this).jqGrid("getRowData", rowid);
			console.log(rowData);
			console.log(iCol);
			if(iCol == 9)
				{
					$.ajax({
						url: contextPath + "/workingAtNightCheckList/search/ajax/list/updatecount.do",
						type: "POST",
						dataType: "json",
						data: {
							count : (Number(rowData.CNT) + 1),
							dbName: rowData.HOST_NAME,
							owner: rowData.HOST_IPV4,
							infoType: rowData.ID_NUMBER
						},
						success: function(result) {
							searchTable();
						},
						error: function() {
							alert("에러가 발생했습니다.");
						}
					})
				}
    },
		loadComplete: function() {
			$(".inline_memo").unbind();
			$(".inline_memo").on("dblclick", function() {
			if($(this).prop("readonly")) {
				$(this).prop("readonly", false);
			};
	
		});
		}
	});

}
