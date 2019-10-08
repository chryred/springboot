$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 355;

var tableRef = null;
var searchBtnRef = null;
var workNameComboRef = null;
var keywordTitleInputRef = null;
var keywordContentsInputRef = null;


/*
 * 초기화
 */
function init () {
	tableRef = $("#jqGridTable");
	searchBtnRef = $(".btn_search");
	workNameComboRef = $("#workName");
	keywordTitleInputRef = $("#keywordTitle");
	keywordContentsInputRef = $("#keywordContents");
	
	initCombo('work', workNameComboRef);
	staticBindEvent();
	initTable();
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	
	$(window).on('load resize', function() {
		var tableWidth = $(".table-wrapper").width();
		var contentsWidth = $(".table-wrapper").width() - 2;
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", contentsWidth);
		$(".ui-jqgrid-hdiv").css("width", contentsWidth);
		$(".ui-jqgrid-bdiv").css("width", contentsWidth);
		$(".ui-jqgrid-pager").css("width", contentsWidth);
		
		$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL));
	});
	
	searchBtnRef.on('click', function() {
		searchTable();
	});
	
	keywordTitleInputRef.on('keyup', function(e) {
		if(e.keyCode == 13) {
			searchTable();
		}
	});
	
	keywordContentsInputRef.on('keyup', function(e) {
		if(e.keyCode == 13) {
			searchTable();
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
			workCode: workNameComboRef.val(),
			keywordTitle: keywordTitleInputRef.val(),
			keywordContents: keywordContentsInputRef.val()
		}
	}).trigger("reloadGrid");
}

/*
 * 그리드 초기화
 */
function initTable () {
	var temp = null;
	
	tableRef.jqGrid({
		url: contextPath + "/wiki/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		jsonReader: {
			id: "SEQ"
		},
		postData: {
			workCode: workNameComboRef.val(),
			keywordTitle: keywordTitleInputRef.val(),
			keywordContents: keywordContentsInputRef.val()
		},
		colNames: [ "SEQ", "업무코드", "업무명", "제목", "최근 업데이트" ],
		colModel : [{
			width: "50px",
			id: "SEQ",
			name: "SEQ",
			index: "SEQ"
		}, {
			id: "WORK_CODE",
			hidden: true
		}, {
			width: "150px",
			id: "WORK_NAME",
			name: "WORK_NAME",
			index: "WORK_NAME"
		}, {
			width: "400px",
			id:"TITLE",
			name: "TITLE",
			index: "TITLE"
		}, {
			width: "200px",
			id:"MOD_DATE",
			name: "MOD_DATE",
			index: "MOD_DATE"
		}],
		autowidth: false,
		shrinkToFit: false,
		width: $('.table-wrapper').width() - 18,
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		rowNum:50,
        rowList:[25,50,100],
        pager : '#pager',
		loadonce: false,
		multiselect: false,
		ondblClickRow: function(rowid, status, e) {
			location.href = contextPath + "/wiki/detail.do?isNew=0&seq=" + rowid;
		}
	});

}

