$(document).ready(function() {
	init();
});

/*$.ajaxSetup({  
    global: false
}); 
*/
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
var selectedRow = 0;
var queryTextAreaRef = null;
var clipboard = new Clipboard('.btn');
	
/*
 * 초기화
 */
function init () {
	
	tableRef = $("#dataTables");
	searchBtnRef = $(".search-btn");
	saveBtnRef = $(".save-btn");
	dbNameComboRef = $("#dbName");
	ownerComboRef = $("#owner");
	infoTypeInputRef = $("#infoType");
	exceptFlagComboRef = $("#exceptFlag");
	queryTextAreaRef = $("#query");
	
	staticBindEvent();
	initComboDBName();
	initComboOwner();
	initTable();
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	//조회버튼 클릭
	searchBtnRef.on("click", function() {
		searchTable();
	});
	
	clipboard.on('success', function(e) {
	    console.info('Action:', e.action);
	    console.info('Text:', e.text);
	    console.info('Trigger:', e.trigger);

	    e.clearSelection();
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
			url: contextPath + "/personalInfo/search/ajax/add/exception.do",
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
		url: contextPath + "/personalInfo/search/ajax/list/dbName.do",
		type: "POST",
		dataType: "json",
		async: false,
		success: function(data) {
			dbNameComboRef.html("");
			_.each(data, function(option, idx) {
				dbNameComboRef.append('<option value="' + option.DB_SID + '">' + option.DB_NAME + '</option>');
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
		url: contextPath + "/personalInfo/search/ajax/list/owner.do",
		type: "POST",
		dataType: "json",
		data: {
			dbName: dbNameComboRef.val()
		},
		success: function(data) {
			ownerComboRef.html("");
			ownerComboRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				ownerComboRef.append('<option value="' + option.OWNER + '">' + option.OWNER + '</option>');
			});
		},
		error: function(err) {
			alert("에러가 발생했습니다.");
		}
	});
}

function copyPersonalInfoCheckQuery(row){
	
	if(selectedRow != row){
		selectedRow = row;
	}else{
		return;
	}
	
	$.ajax({
		url: contextPath + "/views/mtr/data/personalInfoCheckQuery.xml",
		type: "GET",
		async: false,
		success: function(data) {
			$info = $(data).find(tableRef.jqGrid('getCell', row, 'CHECK_GUBN'));
	        
	        if($info.length > 0){
	            
	            var query = $info.find("contents").text();
	            
	            query = query.split("${COLUMN_NAME}").join(tableRef.jqGrid('getCell', row, 'COLUMN_NAME'));
	            query = query.split("${OWNER}").join(tableRef.jqGrid('getCell', row, 'OWNER'));
	            query = query.split("${TABLE_NAME}").join(tableRef.jqGrid('getCell', row, 'TABLE_NAME'));
	            
	            $("#query").val(query);
	        }
		},
		error: function() {
			alert("복사 실패했습니다");
		}
	});

}
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
	var temp = null;
	tableRef.jqGrid({
		url: contextPath + "/personalInfo/search/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		global: false,
		postData: {
			dbName: dbNameComboRef.val() != "" ? dbNameComboRef.val() : null,
			owner: ownerComboRef.val() != "" ? ownerComboRef.val() : null,
			infoType: infoTypeInputRef.val() != "" ? infoTypeInputRef.val() : null,
			exceptFlag: exceptFlagComboRef.val() != "" ? exceptFlagComboRef.val() : null
		},
		colNames: [ "DB명", "구분", "소유자", "테이블명", "컬럼명", "테이블코멘트", "컬럼코멘트", "제외여부", "쿼리추출", "메모" ],
		colModel : [{
			width: "80px",
			id:"DB_NAME",
			name: "DB_NAME",
			index: "DB_NAME"
		}, {
			width: "80px",
			id:"CHECK_GUBN",
			name: "CHECK_GUBN",
			index: "CHECK_GUBN"
		}, {
			width: "80px",
			id:"OWNER",
			name: "OWNER",
			index: "OWNER"
		}, {
			width: "150px",
			id:"TABLE_NAME",
			name: "TABLE_NAME",
			index: "TABLE_NAME"
		}, {
			width: "150px",
			id:"COLUMN_NAME",
			name: "COLUMN_NAME",
			index: "COLUMN_NAME"
		}, {
			width: "200px",
			id:"TABLE_COMMENT",
			name: "TABLE_COMMENT",
			index: "TABLE_COMMENT"	
		}, {
			width: "200px",
			id:"COLUMN_COMMENT",
			name: "COLUMN_COMMENT",
			index: "COLUMN_COMMENT"	
		}, {
			width: "60px",
			id: "PERSONALINFO_YN",
			name: "PERSONALINFO_YN",
			index: "PERSONALINFO_YN",
			align: "center",
			formatter: function(val, ref, row) {
				var rowId = ref.rowId;
				var pinfoFlag = val == 'N' ? 'checked' : '';
				return '<input class="inline_input_checkbox" type="checkbox" id="pinfo_' + rowId + '" '+ pinfoFlag +' />';
			}
		}, {
            width: "60px",
            id: "COPY_QUERY",
            name: "COPY_QUERY",
            index: "COPY_QUERY",
            align: "center",
            formatter: function(val, ref, row) {
                return '<button id="cliipboardBtn" class="btn" data-clipboard-action="cut" data-clipboard-target="#query" onmouseover="copyPersonalInfoCheckQuery(' + ref.rowId + ')">클립보드</button>';
            }
        }, {
			width: "300px",
			id:"MEMO",
			name: "MEMO",
			index: "MEMO",
			formatter: function(val, ref, row) {
				var rowId = ref.rowId;
				var title = "수정하시려면 더블클릭해주세요.";
				return '<input class="inline_input_text inline_memo" type="text" maxlength="1000" id="memo_' + rowId + '" value="'+ val +'" title="' + title + '" readonly />';
			}
		}],
		autowidth: false,
		shrinkToFit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		loadonce: false,
		pager: false,
		rowNum: -1,
		multiselect: false,
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
