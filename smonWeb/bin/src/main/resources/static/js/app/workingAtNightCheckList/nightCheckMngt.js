$(document).ready(function() {
	init();
	initDatePicker();
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
var partNmComboRef = null;
var sysNmComboRef = null;
//var infoTypeInputRef = null;
//var exceptFlagComboRef = null;
	
/*
 * 초기화
 */
function init () {
	
	tableRef = $("#dataTables");
	searchBtnRef = $(".search-btn");
	saveBtnRef = $(".save-btn");
	partNmComboRef = $("#partNm");
	sysNmComboRef = $("#sysNm");
	//infoTypeInputRef = $("#infoType");
	//exceptFlagComboRef = $("#exceptFlag");
	
	staticBindEvent();
	initCombopartNm();
	initComboOwner();
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
	
    $('#btn_save').click(function() {
        fn_save();
    });

	
	
}

/*
 * 파트 명 콤보박스 옵션 초기화
 */
function initCombopartNm() {
	$.ajax({
		url: contextPath + "/nightCheckMngt/partName.do",
		type: "POST",
		dataType: "json",
		async: false,
		success: function(data) {
			partNmComboRef.html("");
			_.each(data, function(option, idx) {
				partNmComboRef.append('<option value="' + option.PART_CD + '">' + option.PART_NM + '</option>');
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
		url: contextPath + "/nightCheckMngt/search/ajax/list/system.do",
		type: "POST",
		dataType: "json",
		data: {
			partCd: partNmComboRef.val()
		},
		success: function(data) {
			sysNmComboRef.html("");
			//sysNmComboRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				sysNmComboRef.append('<option value="' + option.SYS_CD + '">' + option.SYS_NM + '</option>');
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

//modal창에서 사용하는 달력객체를 생성한다.
function initDatePicker() {

    var dp1 = $('#dp1').datepicker({
        format : 'yyyy-mm-dd',
        todayBtn : 'linked'
    });

  var dp2 = $('#dp2').datepicker({
        format : 'yyyy-mm-dd',
        todayBtn : 'linked'
    });
  /* 
    var dp3 = $('#dp3').datepicker({
        format : 'yyyy-mm-dd',
        todayBtn : 'linked'
    });

    var dp4 = $('#dp4').datepicker({
        format : 'yyyy-mm-dd',
        todayBtn : 'linked'
    }); */

    dp1.on('changeDate', function(ev) {
        if (ev.date.valueOf() > planEndDate.valueOf()) {
            $('#alert1').show().find('strong').text('계획 : 시작일이 종료일보다 클 수 없습니다');
        } else {
            $('#alert1').hide().find('strong').text("");
            planStartDate = new Date(ev.date);
        }
        $('#dp1').datepicker('hide');
    });

    dp2.on('changeDate', function(ev) {
        if (ev.date.valueOf() < planStartDate.valueOf()) {
            $('#alert1').show().find('strong').text('계획 : 종료일이 시작일보다 작을 수 없습니다');
        } else {
            $('#alert1').hide().find('strong').text("");
            planEndDate = new Date(ev.date);
        }
        $('#dp2').datepicker('hide');
    });

  /*  dp3.on('changeDate', function(ev) {
        if ((ev.date.valueOf() > endDate.valueOf())
                && (endDate.valueOf() != 1467936000000)) {
            $('#alert2').show().find('strong').text('실적 : 시작일이 종료일보다 클 수 없습니다');
        } else {
            $('#alert2').hide().find('strong').text("");
            startDate = new Date(ev.date);
        }
        $('#dp3').datepicker('hide');
    });

    dp4.on('changeDate', function(ev) {
        if (ev.date.valueOf() < startDate.valueOf()) {
            $('#alert2').show().find('strong').text('실적 : 종료일이 시작일보다 작을 수 없습니다');
        } else {
            $('#alert2').hide().find('strong').text("");
            endDate = new Date(ev.date);
        }
        $('#dp4').datepicker('hide');
    });*/
}
/**
 * modal 이 뜰 때 이벤트
 **/
function fn_onModal() {
	  modal = $('#myModal').modal({
          backdrop : true,
          keyboard : true
      });

    var modal = $('#myModal');

    modal.find('.modal-title1').text( '('+ ')');
    modal.find('.modal-title3').text('--');
    //modal.memo
  
   //     $('#exceptCheckbox').prop("checked", true);
 
}
function fn_save() {
    
	//저장버튼 클릭
	    var paramMap = new Object();
	    var checkYn ;
	    
	    var checkSdate   =  $('#dp1').val();
	    var checkEdate =  $('#dp2').val();
	    var dtl  = $('#memo').val();		
       
	    var comnGrpCd  = $('#COMN_GRP_CD').val();	
	    var comnCd  = $('#COMN_CD').val();	
	    var qComnCd  = $('#Q_COMN_CD').val();	
	    var questionCd  = $('#QUESTION_CD').val();	
		
	    if ($('#checkbox').prop("checked")) {
	    	checkYn  = 'Y';
	    } else {
	    	checkYn  = 'N';
	    }
		var updateDatas = [];
		
		var rowDatas = tableRef.getRowData();
		_.each(rowDatas, function(rowData, idx) {
			updateDatas.push({
				checkSdate: checkSdate,
				checkEdate: checkEdate,
				dtl: dtl,
				checkYn: checkYn,
				comnGrpCd: comnGrpCd,
				comnCd:comnCd,
				qComnCd:qComnCd,
				questionCd:questionCd
			});
		});
		
		$.ajax({
			url: contextPath + "/nightCheckMngt/search/ajax/add/exception.do",
			type: "POST",
			dataType: "json",
			data: {
				exceptionList: JSON.stringify(updateDatas)
			},
			success: function(result) {
			/*	searchTable();
				partNmComboRef.on("change", function() {
					initComboOwner();
				});
			*/
				alert("저장되었습니다.");
				
				$(window).resize(function() {
					var tableWidth = $(".table-wrapper").css("width");
					$(".ui-jqgrid").css("width", tableWidth);
					$(".ui-jqgrid-view").css("width", tableWidth);
					$(".ui-jqgrid-hdiv").css("width", tableWidth);
					$(".ui-jqgrid-bdiv").css("width", tableWidth);
					
					$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
				});
				
			    $('#myModal').modal('hide');
			    searchTable();			
			},
			error: function() {
				alert("에러가 발생했습니다.");
			}
		});		
}

function checkWay(row){
	 modal = $('#myModal').modal({
         backdrop : true,
         keyboard : true
     });

   var modal = $('#myModal');

   modal.find('.modal-title1').text( '('+tableRef.jqGrid('getCell', row, 'COMN_GRP_NM')+' '+tableRef.jqGrid('getCell', row, 'OWNER')+' '+tableRef.jqGrid('getCell', row, 'COMN_NM')+ ' 야간 작업 내용)');
   modal.find('.modal-title3').text('※'+tableRef.jqGrid('getCell', row, 'QUESTION_NM'));
   //modal.memo
 
   alert(tableRef.jqGrid('getCell', row, 'RCHECK_YN'));
   
   if (tableRef.jqGrid('getCell', row, 'RCHECK_YN') =='Y') {
       $('#exceptCheckbox').prop("checked", true);
   } else {
       $('#exceptCheckbox').prop("checked", false);
   }
 
   $('#memo').val(tableRef.jqGrid('getCell', row, 'DTL'));   
   
   $('#dp1').val(tableRef.jqGrid('getCell', row, 'CHECK_SDATE'));
   $('#dp2').val(tableRef.jqGrid('getCell', row, 'CHECK_EDATE'));
   
   $('#COMN_GRP_CD').val(tableRef.jqGrid('getCell', row, 'COMN_GRP_CD'));
   $('#COMN_CD').val(tableRef.jqGrid('getCell', row, 'COMN_CD'));
   $('#Q_COMN_CD').val(tableRef.jqGrid('getCell', row, 'Q_COMN_CD'));
   $('#QUESTION_CD   ').val(tableRef.jqGrid('getCell', row, 'QUESTION_CD'));

}
/*
 * 그리드 조회
 */
function searchTable () {
	var temp = null;
	tableRef.setGridParam({
		postData: {
			partNm: partNmComboRef.val() != "" ? partNmComboRef.val() : null,
			sysNm:  sysNmComboRef.val() != "" ? sysNmComboRef.val() : '01'
		//	infoType: infoTypeInputRef.val() != "" ? infoTypeInputRef.val() : null,
		//	exceptFlag: exceptFlagComboRef.val() != "" ? exceptFlagComboRef.val() : null
		}
	}).trigger("reloadGrid");
}

/*
 * 그리드 초기화
 */
function initTable () {
	var temp = null;
	tableRef.jqGrid({
		url: contextPath + "/nightCheckMngt/search/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		global: false,
		postData: {
			partNm: partNmComboRef.val() ,
			sysNm:  '01'		
			//exceptFlag: exceptFlagComboRef.val() != "" ? exceptFlagComboRef.val() : null
		},
		colNames: [ "점검대상여부","체크", "시스템","업무명", "구분","점검날자1","점검날자2","점검시간", "점검내용", "메모","A","B","C","D","점검방법" ],
		colModel : [{
			width: "160px",
			id: "CHECK_YN",
			name: "CHECK_YN",
			index: "CHECK_YN",
			align: "center",
			formatter: function(val, ref, row) {
				var rowId = ref.rowId;
				var pinfoFlag = val == 'Y' ? 'checked' : '';
				return '<input class="inline_input_checkbox" type="checkbox" id="checkYn_' + rowId + '" '+ pinfoFlag +' />';
			}
		},{
			width: "100px",
			id:"RCHECK_YN",
			name: "CHECK_YN",
			index: "RCHECK_YN",
			//hidden:false
			//formatter: function(val, ref, row) {
			//	var rowId = ref.rowId;
			//	var title = "수정하시려면 더블클릭해주세요.";
			//	return '<input class="inline_input_text inline_memo" type="text" maxlength="1000" id="memo_' + rowId + '" value="'+ val +'" title="' + title + '" readonly />';
			//}
		},{
			width: "100px",
			id:"COMN_GRP_NM",
			name: "COMN_GRP_NM",
			index: "COMN_GRP_NM"
		},{
			width: "100px",
			id:"OWNER",
			name: "OWNER",
			index: "OWNER"
		}, {
			width: "120px",
			id:"COMN_NM",
			name: "COMN_NM",
			index: "COMN_NM"
		}, {
			width: "-1px",
			id:"CHECK_SDATE",
			name: "CHECK_SDATE",
			index: "CHECK_SDATE",
			hidden:true
			//formatter: function(val, ref, row) {
			//	var rowId = ref.rowId;
			//	var title = "수정하시려면 더블클릭해주세요.";
			//	return '<input class="inline_input_text inline_memo" type="text" maxlength="1000" id="memo_' + rowId + '" value="'+ val +'" title="' + title + '" readonly />';
			//}
		}, 
		 {
			width: "-1px",
			id:"CHECK_EDATE",
			name: "CHECK_EDATE",
			index: "CHECK_EDATE",
			hidden:true
			//formatter: function(val, ref, row) {
			//	var rowId = ref.rowId;
			//	var title = "수정하시려면 더블클릭해주세요.";
			//	return '<input class="inline_input_text inline_memo" type="text" maxlength="1000" id="memo_' + rowId + '" value="'+ val +'" title="' + title + '" readonly />';
			//}
		},		{
			width: "-1px",
			id:"CHECK_TIME",
			name: "CHECK_TIME",
			index: "CHECK_TIME",
			hidden:true
			//formatter: function(val, ref, row) {
			//	var rowId = ref.rowId;
			//	var title = "수정하시려면 더블클릭해주세요.";
			//	return '<input class="inline_input_text inline_memo" type="text" maxlength="1000" id="memo_' + rowId + '" value="'+ val +'" title="' + title + '" readonly />';
			//}
		}, {
			width: "550px",
			id:"QUESTION_NM",
			name: "QUESTION_NM",
			index: "QUESTION_NM"
		}, {
			width: "5px",
			id:"DTL",
			name: "DTL",
			index: "DTL",
			hidden:true
		}, 
		{
			width: "-1px",
			id:"COMN_GRP_CD",
			name: "COMN_GRP_CD",
			index: "COMN_GRP_CD",
			hidden:true
		},
		{
			width: "-1px",
			id:"COMN_CD",
			name: "COMN_CD",
			index: "COMN_CD",
			hidden:true
		},		
		{
			width: "-1px",
			id:"Q_COMN_CD",
			name: "Q_COMN_CD",
			index: "Q_COMN_CD",
			hidden:true
		},	
		{
			width: "-1px",
			id:"QUESTION_CD",
			name: "QUESTION_CD",
			index: "QUESTION_CD",
			hidden:true
		},			
		{
			width: "100px",
			id:"BUTTON",
			name: "BUTTON",
			index: "BUTTON",
			formatter: function(val, ref, row) {
                return '<button class="btn"  onclick="checkWay('+  ref.rowId +')">점검방법</button>';
            },
            
		
		},
		],
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
