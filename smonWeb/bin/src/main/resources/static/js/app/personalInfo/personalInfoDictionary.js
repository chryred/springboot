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
	tableRef2 = $("#dataTable2");
	searchBtnRef = $(".search-btn");
	saveBtnRef = $(".save-btn");
	dbNameComboboxRef = $("#dbName");
	ownerComboboxRef = $("#owner");
	objectTypeComboboxRef = $("#objectType");
	personalInfoFlagComboboxRef = $("#personalInfoFlag");
	statusComboboxRef = $("#status");
	objectNameInputRef = $("#object_name");
	
	staticBindEvent();
	initComboDBName();
	initComboOwner();
	initTable();
	initTable2();
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	//조회버튼 클릭
	searchBtnRef.on("click", function() {
		searchTable();
	});
	
	//저장버튼 클릭
	saveBtnRef.on("click", function() {
		var datasBefore = tableRef2.getRowData();
		var datasAfter = [];
		
		_.each(datasBefore, function(rowData, idx) {
			var data = rowData;
			var selectBoxHtml = data.PERSONALINFO_YN;
			data.PERSONALINFO_YN = $('#personal_flag_' + (idx + 1)).val();
			datasAfter.push(data);
		});
		
		$.ajax({
			url: contextPath + "/personalInfo/dictionary/ajax/column/save.do",
			type: "POST",
			dataType: "json",
			data: {
				selColumList: JSON.stringify(datasAfter)
			},
			success: function(data) {
				searchTable();
				searchTable2(tableRef1.getRowData(tableRef1.getGridParam('selrow')));
				console.log(SELECTED_TABLE_ROWID);
			},
			error: function() {
				alert("에러가 발생했습니다.");
			}
		});
		
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
		url: contextPath + "/personalInfo/dictionary/search/ajax/list/dbName.do",
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
		url: contextPath + "/personalInfo/dictionary/search/ajax/list/owner.do",
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
			type: objectTypeComboboxRef.val() != "" ? objectTypeComboboxRef.val() : null,
			personalInfoFlag: personalInfoFlagComboboxRef.val() != "" ? personalInfoFlagComboboxRef.val() : null,
			status: statusComboboxRef.val() != "" ? statusComboboxRef.val() : null,
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
		url: contextPath + "/personalInfo/dictionary/search/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			type: objectTypeComboboxRef.val() != "" ? objectTypeComboboxRef.val() : null,
			personalInfoFlag: personalInfoFlagComboboxRef.val() != "" ? personalInfoFlagComboboxRef.val() : null,
			status: statusComboboxRef.val() != "" ? statusComboboxRef.val() : null,
			objectName: objectNameInputRef.val() != "" ? objectNameInputRef.val() : null
		},
		colNames: [ "DB명", "소유자", "오브젝트타입", "오브젝트명", "코멘트", "상태", "개인정보유무" ],
		colModel : [{
			width: "80px",
			id: "DB_SID",
			name: "DB_SID",
			index: "DB_SID"
		}, {
			width: "80px",
			id:"OWNER",
			name: "OWNER",
			index: "OWNER"
		}, {
			width: "80px",
			id:"OBJECT_TYPE",
			name: "OBJECT_TYPE",
			index: "OBJECT_TYPE"
		}, {
			width: "160px",
			id:"OBJECT_NAME",
			name: "OBJECT_NAME",
			index: "OBJECT_NAME"
		}, {
			width: "160px",
			id:"COMMENTS",
			name: "COMMENTS",
			index: "COMMENTS"
		}, {
			width: "50px",
			id:"STATUS",
			name: "STATUS",
			index: "STATUS"
		}, {
			width: "80px",
			id:"PERSONALINFO_FLAG",
			name: "PERSONALINFO_FLAG",
			index: "PERSONALINFO_FLAG",
			formatter: function(val, ref, row) {
				if(val == "") {
					return "<p style='color: #f00'>미확정</p>"
				} else {
					return val;
				}
			}
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

/*
 * 그리드 조회
 */
function searchTable2 (params) {
	var temp = null;
	tableRef2.setGridParam({
		url: contextPath + "/personalInfo/dictionary/search/ajax/list2.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			dbName: params.DB_SID,
			owner: params.OWNER,
			tableName: params.OBJECT_NAME
		}
	}).trigger("reloadGrid");
}

function initTable2 () {
	var temp = null;
	
	tableRef2.jqGrid({
		colNames: [ "DB명", "소유자", "테이블명", "컬럼명", "데이터타입", "데이터길이", "코멘트", "개인정보유무" ],
		colModel : [{
			id: "DB_SID",
			name: "DB_SID",
			index: "DB_SID",
			hidden: true
		}, {
			id:"OWNER",
			name: "OWNER",
			index: "OWNER",
			hidden: true
		}, {
			id:"TABLE_NAME",
			name: "TABLE_NAME",
			index: "TABLE_NAME",
			hidden: true
		}, {
			width: "150px",
			id:"COLUMN_NAME",
			name: "COLUMN_NAME",
			index: "COLUMN_NAME"
		}, {
			width: "80px",
			id:"DATA_TYPE",
			name: "DATA_TYPE",
			index: "DATA_TYPE"
		}, {
			width: "80px",
			id:"DATA_LENGTH",
			name: "DATA_LENGTH",
			index: "DATA_LENGTH"
		}, {
			width: "170px",
			id:"COMMENTS",
			name: "COMMENTS",
			index: "COMMENTS"
		}, {
			width: "80px",
			id:"PERSONALINFO_YN",
			name: "PERSONALINFO_YN",
			index: "PERSONALINFO_YN",
			formatter: function(val, ref, row) {
				var rowId = ref.rowId;
				var selectBoxHtml = null;
				if(val == 'Y') {
					selectBoxHtml = ""+
						"<select id='personal_flag_" + rowId + "'>"+
							"<option value='Y' selected>포함</option>"+
							"<option value='N'>미포함</option>"+
						"</select>";
				} else if(val == 'N') {
					selectBoxHtml = ""+
						"<select id='personal_flag_" + rowId + "'>"+
							"<option value='Y'>포함</option>"+
							"<option value='N' selected>미포함</option>"+
						"</select>";
				} else {
					selectBoxHtml = ""+
						"<select id='personal_flag_" + rowId + "'>"+
							"<option value='' selected>선택</option>"+
							"<option value='Y'>포함</option>"+
							"<option value='N'>미포함</option>"+
						"</select>";
				}
				return selectBoxHtml;
			}
		}],
		autowidth: false,
		shrinkToFit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL2,
		rowNum: 0,
        pager : false,
		loadonce: false,
		multiselect: false
	});

}
