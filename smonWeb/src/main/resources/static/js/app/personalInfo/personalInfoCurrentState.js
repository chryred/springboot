$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 549;

var tableRef = null;
var initBtnRef = null;
var newBtnRef = null;
var searchBtnRef = null;
var saveBtnRef = null;
var dbNameComboRef = null;
var ownerComboRef = null;
var infoTypeInputRef = null;
var exceptFlagComboRef = null;

var teamComboRef = null;
var systemComboRef = null;
var operationFlagComboRef = null;
var saveTypeComboRef = null;
var objectTypeComboRef = null;
var personalInfoTypeComboRef = null;
var dataTypeComboRef = null;
var ifTypeComboRef = null;
var actionTypeComboRef = null;
var infoTypeComboRef = null;
var actionFlagComboRef = null;
var leaderConfirmFlagComboRef = null;
var searchTypeComboRef = null;
var searchTextRef = null;

var actionStartDateRef = null;
var actionEndDateRef = null;
var leaderStartDateRef = null;
var leaderEndDateRef = null;
var entStartDateRef = null;
var entEndDateRef = null;
var modStartDateRef = null;
var modEndDateRef = null;

var dateTimePickerOptions = null;

var defaultParams = {
	teamName: null,
	serverKorName: null,
	runGubn: null,
	serverIp: null,
	serverType: null,
	dbName: null,
	owner: null,
	objectType: null,
	objectComment: null,
	personalInfoKorType: null,
	dataType: null,
	ifGubn: null,
	actionType: null,
	actionDate: null,
	CompleteYn: null,
	memo: null,
	gatherType: null,
	personalInfoType: null,
	progressCode: null,
	relationProgramCnt: null,
};

/*
 * 초기화
 */
function init () {
	
	tableRef = $("#dataTables");
	
	initBtnRef = $("#init-btn");
	newBtnRef = $("#new-btn");
	searchBtnRef = $("#search-btn");
	saveBtnRef = $("#save-btn");
	
	teamComboRef = $("#team");
	systemComboRef = $("#system");
	operationFlagComboRef = $("#operationFlag");
	saveTypeComboRef = $("#saveType");
	objectTypeComboRef = $("#objectType");
	personalInfoTypeComboRef = $("#personalInfoType");
	dataTypeComboRef = $("#dataType");
	ifTypeComboRef = $("#ifType");
	actionTypeComboRef = $("#actionType");
	infoTypeComboRef = $("#infoType");
	actionFlagComboRef = $("#actionFlag");
	leaderConfirmFlagComboRef = $("#leaderConfirmFlag");
	searchTypeComboRef = $("#searchType");
	searchTextRef = $("#searchText");
	
	actionStartDateRef = $("#actionStartDate");
	actionEndDateRef = $("#actionEndDate");
	leaderStartDateRef = $("#leaderStartDate");
	leaderEndDateRef = $("#leaderEndDate");
	entStartDateRef = $("#entStartDate");
	entEndDateRef = $("#entEndDate");
	modStartDateRef = $("#modStartDate");
	modEndDateRef = $("#modEndDate");
	
	dateTimePickerOptions = {
        format: "yyyy-mm-dd",
        language: "ko",
        todayBtn: true,
        minView: 2,
        autoclose: true
	};
	
	actionStartDateRef.datetimepicker(dateTimePickerOptions);
	actionEndDateRef.datetimepicker(dateTimePickerOptions);
	leaderStartDateRef.datetimepicker(dateTimePickerOptions);
	leaderEndDateRef.datetimepicker(dateTimePickerOptions);
	entStartDateRef.datetimepicker(dateTimePickerOptions);
	entEndDateRef.datetimepicker(dateTimePickerOptions);
	modStartDateRef.datetimepicker(dateTimePickerOptions);
	modEndDateRef.datetimepicker(dateTimePickerOptions);
	
	staticBindEvent();
	initCombo('team', teamComboRef, {}, function() {
		initCombo('system', systemComboRef, {
			teamCode: teamComboRef.val()
		});
	}, true, function() {
		teamComboRef.val('003').trigger('change');
	});
	initCombo('saveType', saveTypeComboRef);
	initCombo('objectType', objectTypeComboRef);
	initCombo('personalInfoType', personalInfoTypeComboRef);
	initCombo('dataType', dataTypeComboRef);
	initCombo('actionType', actionTypeComboRef);
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
	/*
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
	*/
	
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
/*
 * 그리드 조회
 */
function searchTable () {
	var temp = null;
	tableRef.setGridParam({
		postData: {
		}
	}).trigger("reloadGrid");
}

/*
 * 그리드 초기화
 */
function initTable () {
	var temp = null;
	tableRef.jqGrid({
		url: contextPath + "/personalInfo/currentState/search/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		jsonReader: {
			id: "SEQ"
		},
		postData: {
		},
		colNames: [ "SEQ","운영팀","업무시스템","운영구분","접속IP","유형","시스템ID","USER","Object구분","Object설명","개인정보내역","Data Type","I/F구분","조치방법","조치(예정)일자","조치여부","비고","GATHER_TYPE","PERSONALINFO_TYPE","PROGRESS_CODE","RELATION_PROGRAM_CNT" ],
		colModel : [{
				id:"SEQ", name: "SEQ", index: "SEQ", hidden: true
			}, {
				width: "50px", id:"TEAM_NAME", name: "TEAM_NAME", index: "TEAM_NAME", editable: true
			}, {
				width: "120px", id:"SERVER_KOR_NAME", name: "SERVER_KOR_NAME", index: "SERVER_KOR_NAME", editable: true
			}, {
				width: "60px", id:"RUN_GUBN", name: "RUN_GUBN", index: "RUN_GUBN", editable: true
			}, {
				width: "90px", id:"SERVER_IP", name: "SERVER_IP", index: "SERVER_IP", editable: true
			}, {
				width: "40px", id:"SERVER_TYPE", name: "SERVER_TYPE", index: "SERVER_TYPE", editable: true
			}, {
				width: "60px", id:"DB_NAME", name: "DB_NAME", index: "DB_NAME", editable: true
			}, {
				width: "60px", id:"OWNER", name: "OWNER", index: "OWNER", editable: true
			}, {
				width: "70px", id:"OBJECT_TYPE", name: "OBJECT_TYPE", index: "OBJECT_TYPE", editable: true
			}, {
				width: "100px", id:"OBJECT_COMMENT", name: "OBJECT_COMMENT", index: "OBJECT_COMMENT", editable: true
			}, {
				width: "100px", id:"PERSONALINFO_KOR_TYPE", name: "PERSONALINFO_KOR_TYPE", index: "PERSONALINFO_KOR_TYPE", editable: true
			}, {
				width: "80px", id:"DATA_TYPE", name: "DATA_TYPE", index: "DATA_TYPE", editable: true
			}, {
				width: "60px", id:"IF_GUBN", name: "IF_GUBN", index: "IF_GUBN", editable: true
			}, {
				width: "70px", id:"ACTION_TYPE", name: "ACTION_TYPE", index: "ACTION_TYPE", editable: true
			}, {
				width: "90px", id:"ACTION_DATE", name: "ACTION_DATE", index: "ACTION_DATE", editable: true
			}, {
				width: "60px", id:"COMPLETE_YN", name: "COMPLETE_YN", index: "COMPLETE_YN", editable: true
			}, {
				width: "80px", id:"MEMO", name: "MEMO", index: "MEMO", editable: true
			}, {
				width: "80px", id:"GATHER_TYPE", name: "GATHER_TYPE", index: "GATHER_TYPE", editable: true
			}, {
				width: "80px", id:"PERSONALINFO_TYPE", name: "PERSONALINFO_TYPE", index: "PERSONALINFO_TYPE", editable: true
			}, {
				width: "80px", id:"PROGRESS_CODE", name: "PROGRESS_CODE", index: "PROGRESS_CODE", editable: true
			}, {
				width: "80px", id:"RELATION_PROGRAM_CNT", name: "RELATION_PROGRAM_CNT", index: "RELATION_PROGRAM_CNT", editable: true
			}],
		autowidth: false,
		shrinkToFit: false,
		viewrecords: true,
		cellEdit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		loadonce: false,
		pager : '#pager',
		pgbuttons: false,
		pginput: false,
		rowNum: -1,
		multiselect: false,
		loadComplete: function() {
		},
		ondblClickRow: function(rowId) {
			$('#dataTables_iledit').trigger('click');
		}
	}).jqGrid('navGrid', '#pager', {
		edit: false,
		add: false,
		del: false,
		search:false
	}).jqGrid('inlineNav', '#pager', {
		edit: true,
		add: true,
		save: true,
		cancel:true,
		editParams: {
			mtype : "POST",
			url: contextPath + "/personalInfo/currentState/oper/ajax/list.do",
			oneditfunc: function(rowId) {
				
			},
			successfunc: function(result) {
				alert("수정완료");
			},
			restoreAfterError: true
		}
	});

}
