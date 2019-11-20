$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 450;
var CONTENTS_HEIGHT_MINUS_PIXEL2 = 425;

var tableTemplateHtml = null;
var tableRef1 = null;
var searchBtnRef = null;
var dbNameComboboxRef = null;
var ownerComboboxRef = null;
var objectTypeComboboxRef = null;
var personalInfoFlagComboboxRef = null;
var statusComboboxRef = null;
var objectNameInputRef = null;

var SELECTED_TABLE_ROWID = null;
	
/*
 * 초기화
 */
function init () {
	tableRef1 = $("#dataTable1");
	searchBtnRef = $(".search-btn");
	dbNameComboboxRef = $("#dbName");
	ownerComboboxRef = $("#owner");
	objectNameInputRef = $("#object_name");
	
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
		
	dbNameComboboxRef.on("change", function() {
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
		url: contextPath + "/personalInfo/personalPolicy/search/ajax/list/dbName.do",
		type: "POST",
		dataType: "json",
		success: function(data) {
			dbNameComboboxRef.html("");
			dbNameComboboxRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				dbNameComboboxRef.append('<option value="' + option.DB_SID + '">' + option.DB_SID + '</option>');
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
		url: contextPath + "/personalInfo/personalPolicy/search/ajax/list/owner.do",
		type: "POST",
		dataType: "json",
		data: {
			dbName: dbNameComboboxRef.val()
		},
		success: function(data) {
			ownerComboboxRef.html("");
			ownerComboboxRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				ownerComboboxRef.append('<option value="' + option.USERNAME + '">' + option.USERNAME + '</option>');
			});
		},
		error: function(err) {
			alert("에러가 발생했습니다.");
		}
	});
}

/*
 * 그리드 조회
 */
function searchTable () {
	var temp = null;
	tableRef1.setGridParam({
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,			
			objectName: objectNameInputRef.val() != "" ? objectNameInputRef.val() : null
		}
	}).trigger("reloadGrid");
}

/*
 * 그리드 초기화
 */
function initTable () {
	var temp = null;
	
	tableRef1.jqGrid({
		url: contextPath + "/personalInfo/personalPolicy/search/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,			
			objectName: objectNameInputRef.val() != "" ? objectNameInputRef.val() : null
		},
		colNames: [ "DB명", "소유자", "테이블명", "검증쿼리", "설명" ],
		colModel : [{
			width: "160px",
			id: "DB_SID",
			name: "DB_SID",
			index: "DB_SID"
		}, {
			width: "160px",
			id:"OWNER",
			name: "OWNER",
			index: "OWNER"
		}, {
			width: "160px",
			id:"TABLE_NAME",
			name: "TABLE_NAME",
			index: "TABLE_NAME"
		}, {
			width: "350px",
			id:"SQL",
			name: "SQL",
			index: "SQL"
		}, {
			width: "320px",
			id:"INFO",
			name: "INFO",
			index: "INFO"
		}],
		autowidth: false,
		shrinkToFit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		rowNum:50,
        rowList:[25,50,100],
        pager : '#pager',
		loadonce: false,
		multiselect: false,
		onSelectRow: function(rowid, status, e) {
			SELECTED_TABLE_ROWID = rowid;
			searchTable2(tableRef1.getRowData(rowid));
		}
	});

}
