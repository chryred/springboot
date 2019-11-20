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
var tableRef2 = null;
var searchBtnRef = null;
var saveBtnRef = null;
var ifYmdComboboxRef = null;
var dbNameComboboxRef = null;
var ownerComboboxRef = null;
var tableNameComboboxRef = null;
var sqlInputRef = null;
var infoInputRef = null;
var cntInputRef = null;

var SELECTED_TABLE_ROWID = null;

/*
 * 초기화
 */
function init() {
	tableRef1 = $("#dataTable3");
	searchBtnRef = $(".search-btn");
	saveBtnRef = $(".save-btn");
	ifYmdComboboxRef = $("#ifYmd");
	dbNameComboboxRef = $("#dbName");
	ownerComboboxRef = $("#owner");
	tableNameComboboxRef = $("#tableName");
	sqlInputRef = $("#sql");
	infoInputRef = $("#info");
	cntInputRef = $("#cnt");
	staticBindEvent();
	initComboDBName();
	initComboOwner();
	initComboTableName();
	initTable();
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	// 조회버튼 클릭
	searchBtnRef.on("click", function() {
		searchTable();
	});

	dbNameComboboxRef.on("change", function() {
		initComboOwner();
		initComboTableName();
	});
	ownerComboboxRef.on("change", function() {
		initComboTableName();
	});

	$(window).resize(
			function() {
				var tableWidth = $(".table-wrapper").css("width");
				$(".ui-jqgrid").css("width", tableWidth);
				$(".ui-jqgrid-view").css("width", tableWidth);
				$(".ui-jqgrid-hdiv").css("width", tableWidth);
				$(".ui-jqgrid-bdiv").css("width", tableWidth);

				$(".ui-jqgrid-bdiv").css(
						"height",
						($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL)
								+ "px");
			});
}

/*
 * 날짜 콤보박스 옵션 초기화
 
function initComboifYdm() {
	$.ajax({
		url : contextPath
				+ "/personalInfo/destroySearch/search/ajax/list/ifYmd.do",
		type : "POST",
		dataType : "json",
		success : function(data) {
			ifYmdComboboxRef.html("");
			ifYmdComboboxRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				ifYmdComboboxRef.append('<option value="' + option.IF_YMD
						+ '">' + option.IF_YMD + '</option>');
			});
		},
		error : function() {
			alert("에러가 발생했습니다1.");
		}
	});
}
*/

/*
 * DB 명 콤보박스 옵션 초기화
 */
function initComboDBName() {
	$.ajax({
		url : contextPath
				+ "/personalInfo/destroySearch/search/ajax/list/dbName.do",
		type : "POST",
		dataType : "json",
		success : function(data) {
			dbNameComboboxRef.html("");
			dbNameComboboxRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				dbNameComboboxRef.append('<option value="' + option.DB_NAME
						+ '">' + option.DB_NAME + '</option>');
			});
		},
		error : function() {
			alert("에러가 발생했습니다2.");
		}
	});
}

/*
 * 소유자 콤보박스 옵션 초기화
 */
function initComboOwner() {
	$.ajax({
		url : contextPath
				+ "/personalInfo/destroySearch/search/ajax/list/owner.do",
		type : "POST",
		dataType : "json",
		data : {
			dbName : dbNameComboboxRef.val()
		},
		success : function(data) {
			ownerComboboxRef.html("");
			ownerComboboxRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				ownerComboboxRef.append('<option value="' + option.OWNER
						+ '">' + option.OWNER + '</option>');
			});
		},
		error : function(err) {
			alert("에러가 발생했습니다3.");
		}
	});
}

/*
 * 테이블이름 콤보박스 옵션 초기화
 */
function initComboTableName() {
	$.ajax({
		url : contextPath
				+ "/personalInfo/destroySearch/search/ajax/list/TableName.do",
		type : "POST",
		dataType : "json",
		data : {
			owner : ownerComboboxRef.val()
		},
		success : function(data) {
			tableNameComboboxRef.html("");
			tableNameComboboxRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				tableNameComboboxRef.append('<option value="' + option.TABLE_NAME
						+ '">' + option.TABLE_NAME + '</option>');
			});
		},
		error : function(err) {
			alert("에러가 발생했습니다4.");
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
			//ifYmd: ifYmdComboboxRef.val() != "" ? ifYmdComboboxRef.val() : null,
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			
		}
	}).trigger("reloadGrid");
}

/*
 * 그리드 초기화
 */
function initTable() {
	var temp = null;
	tableRef1.jqGrid({
		url : contextPath + "/personalInfo/destroySearch/search/ajax/list.do",
		datatype : "json",
		mtype : "POST",
		postData : {
			//ifYmd: ifYmdComboboxRef.val() != "" ? ifYmdComboboxRef.val() : null,
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			tableName: tableNameComboboxRef.val() != "" ? tableNameComboboxRef.val() : null,
			//sql: sqlInputRef.val() != "" ? sqlInputRef.val() : null,
			//info: infoInputRef.val() != "" ? infoInputRef.val() : null,
			//cnt: cntInputRef.val() != "" ? cntInputRef.val() : null
		},
		colNames : [ "검출일자", "DB명", "소유자", "테이블이름", "SQL문", "설명", "COUNT" ],
		colModel : [ {
			width : "80px",
			id : "IF_YMD",
			name : "IF_YMD",
			index : "IF_YMD"
		}, {
			width : "80px",
			id : "DB_NAME",
			name : "DB_NAME",
			index : "DB_NAME"
		}, {
			width : "80px",
			id : "OWNER",
			name : "OWNER",
			index : "OWNER"
		}, {
			width : "160px",
			id : "TABLE_NAME",
			name : "TABLE_NAME",
			index : "TABLE_NAME"
		}, {
			width : "480px",
			id : "SQL",
			name : "SQL",
			index : "SQL"
		}, {
			width : "400px",
			id : "INFO",
			name : "INFO",
			index : "INFO"
		}, {
			width : "50px",
			id : "CNT",
			name : "CNT",
			index : "CNT",
		} ],
		autowidth : false,
		shrinkToFit : false,
		width : $('.table-wrapper').width(),
		height : $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		rowNum : 50,
		rowList : [ 25, 50, 100 ],
		pager : '#pager',
		loadonce : false,
		multiselect : false,
		onSelectRow : function(rowid, status, e) {
			SELECTED_TABLE_ROWID = rowid;
			searchTable2(tableRef1.getRowData(rowid));
		}
	});
}

/*
 * 그리드 조회
 */

function searchTable2(params) {
	var temp = null;
	tableRef2.setGridParam({
		url : contextPath + "/personalInfo/destroySearch/search/ajax/list2.do",
		datatype : "json",
		mtype : "POST",
		postData : {
			//ifYmd : params.IF_YMD,
			dbName : params.DB_NAME,
			owner : params.OWNER,
		}
	}).trigger("reloadGrid");
}
