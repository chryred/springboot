$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 416;
var CONTENTS_HEIGHT_MINUS_PIXEL2 = 219;

var tableTemplateHtml = null;
var tableRef1 = null;
var tableRef2 = null;
var searchBtnRef = null;
var saveBtnRef = null;
var eventDateInputRef = null;
var dbNameComboboxRef = null;
var ownerComboboxRef = null;
var objectTypeComboboxRef = null;
var personalInfoFlagComboboxRef = null;
var statusComboboxRef = null;
var objectNameInputRef = null;
var dateTimePickerOptions = null;

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
	objectNameInputRef = $("#objectName");
	eventDateInputRef = $("#eventDate");
	
	dateTimePickerOptions = {
        format: "yyyymmdd",
        language: "ko",
        todayBtn: true,
        minView: 2,
        autoclose: true
	};
	
//	eventDateInputRef.datetimepicker(dateTimePickerOptions);
	
	staticBindEvent();
	initCombo('system', dbNameComboboxRef, {
		teamCode: '003'//백화점팀
	});
	initCombo('owner', ownerComboboxRef, {
		dbName: dbNameComboboxRef.val()
	});
	initCombo('objectType', objectTypeComboboxRef);
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
		var updateDatas = [];
		
		
		var rowDatas = tableRef1.getRowData();
		
		$.each($(".inline_input_checkbox"), function(idx, ref) {
			if($(ref).prop("checked")) {
				var selectedData = rowDatas[$(ref).attr("rel")];
				var ymd = selectedData.EVENT_YMD.split("-");
				updateDatas.push({
					eventYmd: ymd[0]+ymd[1]+ymd[2],
					dbName: selectedData.DB_SID,
					owner: selectedData.OWNER,
					objectName: selectedData.OBJECT_NAME,
					objectType: selectedData.OBJECT_TYPE
				});
			}
		});
		
		$.ajax({
			url: contextPath + "/changeObjectMonitor/ajax/save.do",
			type: "POST",
			dataType: "json",
			data: {
				authedDatas: JSON.stringify(updateDatas)
			},
			success: function(data) {
				searchTable();
			},
			error: function() {
				alert("에러가 발생했습니다.");
			}
		});
	
		tableRef2.clearGridData();
	});
	
	dbNameComboboxRef.on("change", function() {
		initCombo('owner', ownerComboboxRef, {
			dbName: $(this).val()
		});
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
 * 그리드 조회
 */
function searchTable () {
	var temp = null;
	tableRef1.setGridParam({
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			objectType: objectTypeComboboxRef.val() != "" ? objectTypeComboboxRef.val() : null,
			objectName: objectNameInputRef.val() != "" ? objectNameInputRef.val() : null
		}
	}).trigger("reloadGrid");
	
	tableRef2.clearGridData();
}

/*
 * 그리드 초기화
 */
function initTable () {
	var temp = null;
	
	tableRef1.jqGrid({
		url: contextPath + "/changeObjectMonitor/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			objectType: objectTypeComboboxRef.val() != "" ? objectTypeComboboxRef.val() : null,
			objectName: objectNameInputRef.val() != "" ? objectNameInputRef.val() : null
		},
		colNames: [ "승인여부", "검출일자", "DB_SID", "구분", "DB명", "소유자", "타입", "오브젝트명", "코멘트", "검출일자", "생성일시", "변경일시"],
		colModel : [{
			width: "60px",
			id:"CONFIRM_YN",
			name: "CONFIRM_YN",
			index: "CONFIRM_YN",
			sortable: false,
			align: "center",
			frozen: true,
			formatter: function(val, ref, row) {
				var rowId = ref.rowId;
				return '<input class="inline_input_checkbox" type="checkbox" id="pinfo_' + (rowId-1) + '" rel="' + (rowId-1) + '" />';
//				if(val == 'Y') {
//					return '<input class="inline_input_checkbox" type="checkbox" id="pinfo_' + rowId + '" checked />';
//				} else {
//					return '<input class="inline_input_checkbox" type="checkbox" id="pinfo_' + rowId + '" />';
//				}
			}
		}, {
			id:"EVENT_YMD",
			name: "EVENT_YMD",
			index: "EVENT_YMD",
			hidden: true
		}, {
			id:"DB_SID",
			name: "DB_SID",
			index: "DB_SID",
			hidden: true
		}, {
			width: "60px",
			id:"STATUS",
			name: "STATUS",
			index: "STATUS",
			sortable: false,
			align: "center",
			formatter: function(val, ref, row) {
				if(val != "") {
					switch(val) {
						case "01":
							return "신규";
							break;
						case "02":
							return "변경";
							break;
					}
				} else {
					return val;
				}
			}
		}, {
			width: "120px",
			id:"DB_NAME",
			name: "DB_NAME",
			index: "DB_NAME",
			sortable: false
		}, {
			width: "80px",
			id:"OWNER",
			name: "OWNER",
			index: "OWNER",
			sortable: false
		}, {
			width: "50px",
			id:"OBJECT_TYPE",
			name: "OBJECT_TYPE",
			index: "OBJECT_TYPE",
			sortable: false
		}, {
			width: "180px",
			id:"OBJECT_NAME",
			name: "OBJECT_NAME",
			index: "OBJECT_NAME",
			sortable: false
		}, {
			width: "180px",
			id:"COMMENTS",
			name: "COMMENTS",
			index: "COMMENTS",
			sortable: false
		}, {
			width: "80px",
			id: "EVENT_YMD",
			name: "EVENT_YMD",
			index: "EVENT_YMD",
			sortable: false,
			align: "center",
			formatter: function(val, ref, row) {
				if(val != "") {
					return val.substring(0, 4) + "-" + val.substring(4, 6) + "-" + val.substring(6, 8);
				} else {
					return val;
				}
			}
		}, {
			width: "120px",
			id:"CREATED_DATE",
			name: "CREATED_DATE",
			index: "CREATED_DATE",
			sortable: false,
			align: "center",
			formatter: function(val, ref, row) {
				if(val != "") {
					return val.substring(0, 4) + "-" + val.substring(4, 6) + "-" + val.substring(6, 8) + " / " + val.substring(8, 10) + ":" + val.substring(10, 12);
				} else {
					return val;
				}
			}
		}, {
			width: "120px",
			id:"LAST_DDL_DATE",
			name: "LAST_DDL_DATE",
			index: "LAST_DDL_DATE",
			sortable: false,
			align: "center",
			formatter: function(val, ref, row) {
				if(val != "") {
					return val.substring(0, 4) + "-" + val.substring(4, 6) + "-" + val.substring(6, 8) + " / " + val.substring(8, 10) + ":" + val.substring(10, 12);
				} else {
					return val;
				}
			}
		}/*, {
			width: "120px",
			id:"CONFIRM_DATE",
			name: "CONFIRM_DATE",
			index: "CONFIRM_DATE",
			sortable: false,
			align: "center",
			formatter: function(val, ref, row) {
				if(val != "") {
					return val.substring(0, 2) + ":" + val.substring(2, 4);
				} else {
					return val;
				}
			}
		}, {
			width: "60px",
			id:"CONFIRM_EMPNO",
			name: "CONFIRM_EMPNO",
			index: "CONFIRM_EMPNO",
			sortable: false
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
		}*/],
		autowidth: false,
		shrinkToFit: false,
		width: $('.table-wrapper-first').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		rowNum:0,
//        pager : '#pager',
		loadonce: false,
		multiselect: false,
		onSelectRow: function(rowid, status, e) {
			SELECTED_TABLE_ROWID = rowid;
			searchTable2(tableRef1.getRowData(rowid));
		}
	})//.jqGrid("setFrozenColumns");

}

/*
 * 그리드 조회
 */
function searchTable2 (params) {
	
	var temp = null;
	tableRef2.setGridParam({
		url: contextPath + "/changeObjectMonitor/ajax/column/list.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			dbName: params.DB_SID,
			owner: params.OWNER,
			tableName: params.OBJECT_NAME
		}
	}).jqGrid("setFrozenColumns").trigger("reloadGrid");
}

function initTable2 () {
	var temp = null;
	
	tableRef2.jqGrid({
		colNames: [ "DB명", "소유자", "테이블명", "변경구분", "컬럼명", "데이터타입", "데이터길이", "코멘트"],
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
			width: "60px",
			id:"CHANGE_GUBN",
			name: "CHANGE_GUBN",
			index: "CHANGE_GUBN",
			formatter: function(val, ref, row) {
				var rowId = ref.rowId;
				var returnVal = "";
				
				color = "white";
				
				if(val == 'A'){
	    		    color = "yellow";
	    		    returnVal = "컬럼 추가"
				}else if(val == 'D'){
					color = "#ACE3A9";
					returnVal = "타입 변경"
			 	}else if(val == 'L'){
			 		color = "#9FE4E4";
			 		returnVal = "길이 변경"
				}else{
					returnVal = "변경 없음"
				}
				
				return '<span style="background-color:' + color + '"><b>' + returnVal + '</b></span>';
			}
		}, {
			width: "150px",
			id:"COLUMN_NAME",
			name: "COLUMN_NAME",
			index: "COLUMN_NAME",
			sortable: false
		}, {
			width: "80px",
			id:"DATA_TYPE",
			name: "DATA_TYPE",
			index: "DATA_TYPE",
			sortable: false
		}, {
			width: "80px",
			id:"DATA_LENGTH",
			name: "DATA_LENGTH",
			index: "DATA_LENGTH",
			sortable: false
		}, {
			width: "170px",
			id:"COMMENTS",
			name: "COMMENTS",
			index: "COMMENTS",
			sortable: false
		}],
		autowidth: false,
		shrinkToFit: false,
		width: $('.table-wrapper-second').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		rowNum: 0,
        pager : false,
		loadonce: false,
		multiselect: false
		/*,loadComplete : function(data){
		    var ids = tableRef2.getDataIDs();
		     $.each(
		    	 ids, function(idx, rowId){
		    		 rowData = tableRef2.getRowData(rowId);
		    		 
		    		 if(rowData.CHANGE_GUBN == 'A'){
		    			 console.log(rowId);
		    			 tableRef2.setRowData(rowId, false, { background: "red"});
					 }else if(rowData.CHANGE_GUBN == 'D'){
						
				 	 }else if(rowData.CHANGE_GUBN == 'L'){
						
					 }else{
						
					 }
		    	 }
		     )
			
		}*/
		
	});

}
