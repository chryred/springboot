$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 290;
//var CELL_COUNT = 19;
var tableTemplateHtml = null;
var tableRef = null;
var dataTable = null;
var searchBtnRef = null;
var exceptBtnRef = null;
var dbNameComboboxRef = null;
var ownerComboboxRef = null;
var viewComboboxRef = null;
var dbPersonalInfoCount = null;
var ownerInfoCount = null;

/*
 * 초기화
 */
function init () {
	tableRef = $("#dataTables");
	searchBtnRef = $("#search");
	autoSearchBtnRef = $("#autoSearch");
	exceptBtnRef = $(".except-btn");
	dbNameComboboxRef = $("#dbName");
	ownerComboboxRef = $("#owner");
	groupComboboxRef = $("#group");
	viewComboboxRef = $("#viewCombo");
	staticBindEvent();
	initComboDBName();
	initComboOwner();
	initComboView();
	initTable();
}

$.ajaxSetup({  
    global: false
}); 

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	//조회버튼 클릭
	searchBtnRef.on("click", function() {
		searchTable();
	});
	
	autoSearchBtnRef.on("click", function() {
        searchTable(true);
    });
	
	//제외버튼 클릭
	exceptBtnRef.on("click", function() {
		var exceptTargetRefs = $(".exceptTarget");
		var anythingChecked = false;
		var checkedExceptDatas = [];
		
		var selRowIds = tableRef.getGridParam("selarrrow");
		_.each(selRowIds, function(rowId, idx) {
			anythingChecked = true;
			var selRowData = tableRef.getRowData(rowId);
			checkedExceptDatas.push({
				dbName: selRowData.DB_NAME,
				owner: selRowData.OWNER,
				tableName: selRowData.TABLE_NAME,
				columnName: selRowData.COLUMN_NAME,
				tableComment: selRowData.TABLE_COMMENT,
				columnComment: selRowData.COLUMN_COMMENT
			});
		});
		
		//체크된 대상이 존재하는 경우
		if(anythingChecked && selRowIds.length > 0) {
			$.ajax({
				url: contextPath + "/personalInfoCheck/check/ajax/add/exception.do",
				type: "POST",
				dataType: "json",
				data: {
					exceptionList: JSON.stringify(checkedExceptDatas)
				},
				success: function(result) {
					searchTable();
				},
				error: function() {
					alert("에러가 발생했습니다.");
				}
			});
		} else {
			alert("제외 대상을 선택해주세요.");
		}
		
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
		url: contextPath + "/personalInfoCheck/check/ajax/list/dbName.do",
		type: "POST",
		dataType: "json",
		success: function(data) {
			//alert("initomboDBName1");
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
		url: contextPath + "/personalInfoCheck/check/ajax/list/owner.do",
		type: "POST",
		dataType: "json",
		data: {
			dbName: dbNameComboboxRef.val()
		},
		success: function(data) {
			//alert("initComboOwner2");
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
 * 뷰 콤보박스 옵션 초기화
 */
function initComboView() {
	viewComboboxRef.html("");
	viewComboboxRef.append('<option value="">전체</option>');
	viewComboboxRef.append('<option value="SID">SID</option>');
	viewComboboxRef.append('<option value="USER">소유자</option>');
	viewComboboxRef.append('<option value="TABLE">테이블</option>');
}

/*
 * 그리드 조회
 */
function searchTable (boolean) {

    //alert(groupComboboxRef.val());
    
	tableRef.setGridParam({
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			group : groupComboboxRef.val() != "" ? groupComboboxRef.val() : null
		}
	}).trigger("reloadGrid");
	
	if(boolean){
    	setInterval(function updateRandom() {
    	    searchTable();
         }, 600000);
	}
}

/*
 * 그리드 초기화
 */
function initTable () {
        
	tableRef.jqGrid({
		url: contextPath + "/personalInfoCheck/check/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			group: groupComboboxRef.val() != "" ? groupComboboxRef.val() : null
		},
		colNames: [  "데이터베이스",
					 "총 테이블수",
					 "총 컬럼수", 
					 "미확인 테이블수",
					 "미확인 컬럼수",
					 "등록률",
					 "개인정보컬럼수",
					 "고유고객번호", 
					 "운전자면허", 
					 "계좌",
					 "카드번호",
					 "여권번호", 
					 "암호",
					 "바이오", 
					 "주민번호", 
					 "전화번호/휴대폰", 
					 "이메일", 
					 "주소", 
					 "메모", 
					 "차량번호",
					 "기타"],
		colModel : [{
			width: "120px",
			id: "DB_NAME",
			name: "DB_NAME",
			index: "DB_NAME", 
			align: 'left',
			cellattr: function(rowId, tv, rowObject, cm, rdata) {
                if (rowObject.PROGRESS_PERCENTAGE == 100){ 
                    return 'style="background : #87FE7E"'; 
                }else if(rowObject.PROGRESS_PERCENTAGE > 50){
                    return 'style="background : #D4DAF8"'; 
                }else if(rowObject.PROGRESS_PERCENTAGE >= 0 && rowObject.TOT_OBJ_CNT > 0){
                    return 'style="background : #F6FA86"'; 
                }else{
                    return 'style="background : #F87979; font-weight: bold"'; 
                }
            }
		}, {
			width: "90px",
			id:"TOT_OBJ_CNT",
			name: "TOT_OBJ_CNT",
			index: "TOT_OBJ_CNT", 
			formatter: 'integer',
			align: 'right'
		}, {
			width: "90px",
			id:"TOT_COL_CNT",
			name: "TOT_COL_CNT",
			index: "TOT_COL_CNT", 
			formatter: 'integer',
			align: 'right'
		}, {
            width: "90px",
            id:"NOT_CHECK_TAB_SUM",
            name: "NOT_CHECK_TAB_SUM",
            index: "NOT_CHECK_TAB_SUM", 
            formatter: 'integer',
            align: 'right'
        },{
            width: "90px",
            id:"NOT_CHECK_SUM",
            name: "NOT_CHECK_SUM",
            index: "NOT_CHECK_SUM", 
            formatter: 'integer',
            align: 'right'
        },{
            width: "90px",
            id:"PROGRESS_PERCENTAGE",
            name: "PROGRESS_PERCENTAGE",
            index: "PROGRESS_PERCENTAGE", 
            formatter: 'integer',
            align: 'right'
        },{
			width: "90px",
			id:"TOT_PERINFO_SUM",
			name: "TOT_PERINFO_SUM",
			index: "TOT_PERINFO_SUM", 
			formatter: 'integer',
			align: 'right'
		}, {
            width: "70px",
            id:"CUSTID_SUM  ",
            name: "CUSTID_SUM",
            index: "CUSTID_SUM", 
            align: 'right'
        }, {
            width: "70px",
            id:"DRIVER_SUM",
            name: "DRIVER_SUM",
            index: "DRIVER_SUM", 
            align: 'right'
        }, {
			width: "70px",
			id:"ACCOUNT_SUM",
			name: "ACCOUNT_SUM",
			index: "ACCOUNT_SUM", 
			align: 'right'
		}, {
            width: "70px",
            id:"CARD_SUM",
            name: "CARD_SUM",
            index: "CARD_SUM"   , 
            align: 'right'
        }, {
            width: "70px",
            id:"PASSPORT_SUM",
            name: "PASSPORT_SUM",
            index: "PASSPORT_SUM", 
            align: 'right'  
        }, {
            width: "70px",
            id:"PASSWORD_SUM",
            name: "PASSWORD_SUM",
            index: "PASSWORD_SUM", 
            align: 'right'  
        }, {
            width: "70px",
            id:"BIO_SUM",
            name: "BIO_SUM",
            index: "BIO_SUM", 
            align: 'right'
        }, {
            width: "70px",
            id:"SSN_SUM",
            name: "SSN_SUM",
            index: "SSN_SUM", 
            align: 'right'  
        }, {
            width: "70px",
            id:"PHONE_SUM",
            name: "PHONE_SUM",
            index: "PHONE_SUM", 
            align: 'right'  
        }, {
            width: "70px",
            id:"EMAIL_SUM",
            name: "EMAIL_SUM",
            index: "EMAIL_SUM", 
            align: 'right'  
        }, {
			width: "70px",
			id:"ADDRESS_SUM",
			name: "ADDRESS_SUM",
			index: "ADDRESS_SUM", 
			align: 'right'
		}, {
			width: "70px",
			id:"MEMO_SUM",
			name: "MEMO_SUM",
			index: "MEMO_SUM", 
			align: 'right'	
		}, {
			width: "70px",
			id:"VEHICLENUMBER_SUM",
			name: "VEHICLENUMBER_SUM",
			index: "VEHICLENUMBER_SUM", 
			align: 'right'	
		}, {
			width: "70px",
			id:"ETC_SUM",
			name: "ETC_SUM",
			index: "ETC_SUM", 
			align: 'right'	
		}
		],
		shrinkToFit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		loadonce: false,
		pager: false,
		rowNum: -1,
		footerrow: true,
		userDataOnFooter: true,
		sortable: true,
		height: "auto",
		gridview: true,
		sortname: "indate",
		resizable: false,
		loadComplete : function(){
			
			//alert("loadComplete3");
			// .ui-th-column, .ui-jqgrid .ui-jqgrid-htable th.ui-th-column 
			$(".ui-jqgrid .ui-jqgrid-htable th div").css("height", "auto");
			$(".ui-jqgrid .ui-jqgrid-htable th div").css("overflow", "hidden");
			$(".ui-jqgrid .ui-jqgrid-htable th div").css("font-size", "1.1em");
			$(".ui-jqgrid tr.jqgrow td").css("font-size", "1.1em");
			var colNames1 = [ 
				 "TOT_OBJ_CNT",
				 "TOT_COL_CNT",
				 "NOT_CHECK_TAB_SUM",
				 "NOT_CHECK_SUM",
				 "PROGRESS_PERCENTAGE",
				 "TOT_PERINFO_SUM",
				 "CUSTID_SUM", 
				 "DRIVER_SUM", 
				 "ACCOUNT_SUM", 
				 "CARD_SUM", 
				 "PASSPORT_SUM", 
				 "PASSWORD_SUM",
				 "BIO_SUM", 
				 "SSN_SUM", 
				 "PHONE_SUM", 
				 "EMAIL_SUM", 
				 "ADDRESS_SUM", 
				 "MEMO_SUM", 
				 "VEHICLENUMBER_SUM",
				 "ETC_SUM"];
			
			var aryColSum = new Array();
			var $grid = $("#dataTables");
			var color = "";
			var totTeamCnt =  0;
			var teamNotCheckCnt = 0;
			var teamProgressPercentage = 0;
			
			for ( var i = 0 ; i <= colNames1.length ; i++ )
			{
				aryColSum[i] = $grid.jqGrid('getCol',colNames1[i],false,'sum');
				
				if(colNames1[i] == "TOT_COL_CNT"){
				    totTeamCnt = aryColSum[i];
				}
				
				if(colNames1[i] == "NOT_CHECK_TAB_SUM"){
				    teamNotCheckCnt = aryColSum[i];
                }
				
				
				if (aryColSum[i] > 0) {
				    if(colNames1[i] == "ETC_SUM" || colNames1[i] == "NOT_CHECK_SUM" || colNames1[i] == "NOT_CHECK_TAB_SUM"){
				        color = "red";
				    }else{
				        color = "blue";
				    }
					$('table.ui-jqgrid-ftable td:eq('+(i+1)+')').css("color", color);
	            }
			}
			
			teamProgressPercentage = (totTeamCnt - teamNotCheckCnt) / totTeamCnt * 100;
			
			teamProgressPercentage = Math.floor(teamProgressPercentage);
			
		    var index = 0;
			
			$grid.jqGrid('footerData','set',{TOT_OBJ_CNT                 : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{TOT_COL_CNT                 : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{NOT_CHECK_TAB_SUM           : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{NOT_CHECK_SUM               : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{PROGRESS_PERCENTAGE         : teamProgressPercentage});
			index++;
			$grid.jqGrid('footerData','set',{TOT_PERINFO_SUM 			 : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{CUSTID_SUM                   : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{DRIVER_SUM                   : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{ACCOUNT_SUM				 : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{CARD_SUM                     : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{PASSPORT_SUM                 : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{PASSWORD_SUM                 : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{BIO_SUM                  : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{SSN_SUM                  : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{PHONE_SUM                    : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{EMAIL_SUM                    : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{ADDRESS_SUM				 : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{MEMO_SUM					 : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{VEHICLENUMBER_SUM		     : aryColSum[index++]});
			$grid.jqGrid('footerData','set',{ETC_SUM 					 : aryColSum[index++]});
			
			var $footRow = $("#dataTables").closest(".ui-jqgrid-bdiv").next(".ui-jqgrid-sdiv").find(".footrow");
			var $self = $(this);
			$self.jqGrid(
                    "footerData",
                    "set",
                    {
                    	DB_NAME: "Total:"
                    },
                    false
                );

			//$self.jqGrid('setSelection', $footRow, false);
			//var $db_sid = $footRow.find('>td[aria-describedby="dataTables_DB_SID"]'),
				//$owner = $footRow.find('>td[aria-describedby="dataTables_OWNER"]'),
			    //$table_name = $footRow.find('>td[aria-describedby="dataTables_TABLE_NAME"]'),
			    //width3 = 120;

			//$owner.css("display", "none");
			//$table_name.css("display", "none");
			//$db_sid.attr("colspan", "1").width(width3);



			// setLabel 함수로 i번째 컬럼명을 '컬럼명 + 합계'로 변경
			/*
			var colModel = $("#dataTables").jqGrid('getGridParam', 'colModel'); // 컬럼명을 배열형태로 가져온다.
			var colNames = $("#dataTables").jqGrid('getGridParam', 'colNames');
			for (var i in colModel)
			{
				 맨 처음 3개 컬럼은 총합 카운트가 없으므로 i가 3부터 시작하도록 하였음.
				if( i > 3 )
				{
					 이 부분이 추가 되지 않으면 컬럼 클릭시 갯수 컬럼이 증가함.
					var set = colNames[i].indexOf("<");
					if(set != -1)
					{
						colNames[i] = colNames[i].substring(0, set);
					}
					
					 헤더에 합계 추가 
					jQuery("#dataTables").jqGrid("setLabel", colModel[i]['name'], colNames[i] +"<br>("+aryColSum[ i - 4 ]+")"); 
				}
			}*/
		}
	});

	
}