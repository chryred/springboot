/** Global Variable */
var gSelectedCell;
var gCellTotCnt  = 266;  // 출력할 셀 카운트
var gCellChkCnt  = 255;  // 출력할 셀 카운트
var gSplitStdNum = 12;   // 자르는 기준
var gSelectedRow;
var gStndrdIp    = "10.174.179.";
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

/**===============================================================================
// DataTable - IP Sort를 위한 설정
//===============================================================================*/
jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	"ip-address-pre": function ( a ) {
		var i, item;
		var m = a.split("."),
			n = a.split(":"),
			x = "",
			xa = "";

		if (m.length == 4) {
			// IPV4
			for(i = 0; i < m.length; i++) {
				item = m[i];

				if(item.length == 1) {
					x += "00" + item;
				}
				else if(item.length == 2) {
					x += "0" + item;
				}
				else {
					x += item;
				}
			}
		}
		else if (n.length > 0) {
			// IPV6
			var count = 0;
			for(i = 0; i < n.length; i++) {
				item = n[i];

				if (i > 0) {
					xa += ":";
				}

				if(item.length === 0) {
					count += 0;
				}
				else if(item.length == 1) {
					xa += "000" + item;
					count += 4;
				}
				else if(item.length == 2) {
					xa += "00" + item;
					count += 4;
				}
				else if(item.length == 3) {
					xa += "0" + item;
					count += 4;
				}
				else {
					xa += item;
					count += 4;
				}
			}

			// Padding the ::
			n = xa.split(":");
			var paddDone = 0;

			for (i = 0; i < n.length; i++) {
				item = n[i];

				if (item.length === 0 && paddDone === 0) {
					for (var padding = 0 ; padding < (32-count) ; padding++) {
						x += "0";
						paddDone = 1;
					}
				}
				else {
					x += item;
				}
			}
		}

		return x;
	},

	"ip-address-asc": function ( a, b ) {
		return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	},

	"ip-address-desc": function ( a, b ) {
		return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	}
});

/**===============================================================================
//DOM 준비시..
//===============================================================================*/
$(document).ready(function(){
	
	fnControlScreen("AFTREADY");
	
	 $("#slist a").click(function(e){
		 $(this).next('p').toggle(200);
	 });
	 
	 // 업무설명 로딩 시 숨기기
	 $('.explain').toggle();
	
});

/**===============================================================================
//화면 제어
//===============================================================================*/
function fnControlScreen(strClsfy) {
	if(strClsfy=="AFTREADY"){
		
		/** 초기화 등 기본적인 사항 적기 */
		
		$('#reqNewIp').click(fnInsertNewIp);   // 신규발급		
		$('#updateIp').click(fnUpdateIp);      // 수정		
		$('#deleteIp').click(fnDeleteIp);      // 삭제
		$('#btn_search').click(fnInquiry);     // 조회
		$('#btnTableSrch').click(fnInitTable); // 테이블조회
		$('.btn_newIp').click(fnNewIpPopup);   // 신규발급(선택가능)
		
		//검색
		fnInquiry();
		fnInitTable();
	}else if(strClsfy=="AFT_INQRY"){
		
	}
	
}

/**===============================================================================
// 초기화
//===============================================================================*/
function fnInit(){
	// 입력 폼 초기화
	$("#userIp").val('');
	$("#userName").val('');
	$("#reason").val('');
	$("#passwd").val('');
	$("#entDate").val('');
}

/**===============================================================================
// 버튼 컨트롤(NEW : 신규발급 / UPDATE : 수정/삭제)
//===============================================================================*/
function fnBtnCtrl(Gubn){
	if(Gubn == "NEW"){
		$('#reqNewIp').show(); // 발급버튼 출력
    	$('#updateIp').hide(); // 수정버튼 숨김
    	$('#deleteIp').hide(); // 삭제버튼 숨김
	}else if("UPDATE"){
		$('#reqNewIp').hide(); // 발급버튼 숨김
    	$('#updateIp').show(); // 수정버튼 출력
    	$('#deleteIp').show(); // 삭제버튼 출력
	}
}

/**===============================================================================
// IP 발급 요청/수정/삭제
//===============================================================================*/

// 신규 IP 등록
function fnInsertNewIp(){
	// 파라미터 값 검증
	var paramIp       = $("#userIp").val(); // IP
	var paramUserName = $("#userName").val(); // 신청자 이름
	var paramReason   = $("#reason").val();   // 사용목적
	var paramPasswd   = $("#passwd").val();   // 비밀번호
	
	if(paramIp == "" || paramIp == null){
		alert("IP가 입력되지 않았습니다.");
		return;
	}else if(paramUserName == "" || paramUserName == null){
		alert("이름을 입력해주세요.");
		$('#userName').focus();
		return;
	}else if(paramReason == "" || paramReason == null){
		alert("사용목적을 입력해주세요.");
		$('#reason').focus();
		return;
	}else if(paramPasswd == "" || paramPasswd == null){
		alert("비밀번호를 입력해주세요.");
		$('#passwd').focus();
		return;
	}
	
	// 신규발급신청
	$.ajax({
	 	type: 'POST',
	 	dataType: 'json',
	 	url:  g_contextWebRoot+'insertNewIp.do',
	 	data: $('#form_userIp').serialize(true), // 넘길 파라미터
	 	success: function(objJsonRowData){
	 		if(objJsonRowData.resultCode == "SUCCESS"){
	 			alert(objJsonRowData.resultMsg);
	 			$('#userIpStatus').modal('hide');
	 			fnInit();
	 			fnInquiry();
	 			fnInitTable();
		 	}else{
		 		alert(objJsonRowData.resultMsg);
	 		}
	 	},
	 	error: fnInquiryError
	});
}

// IP 정보 수정
function fnUpdateIp(){
	// 파라미터 값 검증
	var paramIp       = $("#userIp").val(); // IP
	var paramUserName = $("#userName").val(); // 신청자 이름
	var paramReason   = $("#reason").val();   // 사용목적
	var paramPasswd   = $("#passwd").val();   // 비밀번호
	
	if(paramIp == "" || paramIp == null){
		alert("IP가 입력되지 않았습니다.");
		return;
	}else if(paramUserName == "" || paramUserName == null){
		alert("이름을 입력해주세요.");
		$('#userName').focus();
		return;
	}else if(paramReason == "" || paramReason == null){
		alert("사용목적을 입력해주세요.");
		$('#reason').focus();
		return;
	}else if(paramPasswd == "" || paramPasswd == null){
		alert("비밀번호를 입력해주세요.");
		$('#passwd').focus();
		return;
	}
	
	// IP 정보 수정
	$.ajax({
	 	type: 'POST',
	 	dataType: 'json',
	 	url:  g_contextWebRoot+'updateIp.do',
	 	data: $('#form_userIp').serialize(true), // 넘길 파라미터
	 	success: function(objJsonRowData){
	 		if(objJsonRowData.resultCode == "SUCCESS"){
	 			alert(objJsonRowData.resultMsg);
	 			$('#userIpStatus').modal('hide');
	 			fnInit();
	 			fnInquiry();
		 	}else{
		 		alert(objJsonRowData.resultMsg);
	 		}
	 	},
	 	error: fnInquiryError
	});
}

// 삭제
function fnDeleteIp(){
	
	var paramIp       = $("#userIp").val(); // IP
	var paramPasswd   = $("#passwd").val(); // 비밀번호
	
	if(paramIp == "" || paramIp == null){
		alert("IP가 입력되지 않았습니다.");
		return;
	}else if(paramPasswd == "" || paramPasswd == null){
		alert("비밀번호를 입력해주세요.");
		$('#passwd').focus();
		return;
	}
	
	if(confirm('정말 삭제하시겠습니까?')){
		// IP 정보 삭제
		$.ajax({
		 	type: 'POST',
		 	dataType: 'json',
		 	url:  g_contextWebRoot+'deleteIp.do',
		 	data: $('#form_userIp').serialize(true), // 넘길 파라미터
		 	success: function(objJsonRowData){
		 		if(objJsonRowData.resultCode == "SUCCESS"){
		 			alert(objJsonRowData.resultMsg);
		 			$('#userIpStatus').modal('hide');
		 			fnInit();
		 			fnInquiry();
		 			fnInitTable();
			 	}else{
			 		alert(objJsonRowData.resultMsg);
		 		}
		 	},
		 	error: fnInquiryError
		});
	}
		
}

// 신규IP 발급(선택가능)
function fnNewIpPopup(){
	fnInit();
	$('#userIpForm').empty();
	
	/** 사용 가능한 IP 조회 */
	$.ajax({
	 	type: 'POST',
	 	dataType: 'json',
	 	url:  g_contextWebRoot+'ajax/selectAvailableIp.do',
	 	success: function(resultData){
	 		if(resultData.resultCode == "SUCCESS"){
	 			var ipInfo = resultData.data;
	 			var avaiableIp = "<select id=\"userIp\" class=\"form-control\" name=\"userIp\">";
	 			if(ipInfo != null && ipInfo.length > 0){
	 				for(var i=0; i<ipInfo.length; i++){
	 					avaiableIp = avaiableIp + "<option value=\""+ipInfo[i].ip+"\">"+ipInfo[i].ip+"</option>";
	 				}
	 			}
	 			avaiableIp = avaiableIp + "</select>";
	 			$('#userIpForm').html(avaiableIp);
		 	}else{
	 			fnInquiryError();
	 			return;
	 		}
	 	},
	 	error: fnInquiryError
	});
	
	fnBtnCtrl('NEW');
	$('#userIpStatus').modal('show'); //모달 띄우기        	
}

/**===============================================================================
//테이블 초기화 이벤트(dataTable 사용)
//===============================================================================*/
function fnInitTable(){
	
	$('#dataTable').dataTable().fnClearTable();
    $('#dataTable').dataTable().fnDestroy();
	
	$('#dataTable').DataTable( {
        select: {
        	style: 'single'
        },
        pageLength: 20,
        bPaginate: true,
        bLengthChange: true,
        bAutoWidth: false,
        processing: true,
        ordering: true,
        serverSide: false,
        searching: true,
        dom: 'Bfrtip',
        buttons: [
        	'copyHtml5',
            'excelHtml5'
        ],
        ajax: {
        	"url": g_contextWebRoot+"ajax/selectUserIp.do",
        	"dataType": "JSON"
        },
        columnDefs: [
            { type : 'ip-address', targets: 0},
            { width: "15%",        targets: 0},
            { width: "10%",        targets: 1},
            { width: "40%",        targets: 2},
            { width: "5%",         targets: 3},
            { width: "10%",        targets: 4},
            { width: "10%",        targets: 5},
          ],
        columns: [
        	{data: "ip",         className: "text-center"},
        	{data: "recentUser", className: "text-center"},
        	{data: "forWhat",    className: "text-center"},
        	{data: "useYn",      className: "text-center"},
        	{data: "entDate",    className: "text-center"},
        	{data: "modDate",    className: "text-center"}
        ]
    } );
	
	fnInit();
	fnEventTableV2();
}

/**===============================================================================
// 테이블 클릭 이벤트
//===============================================================================*/

// dataTable 사용 버전
function fnEventTableV2(){
	
	// 더블 클릭 시, 상세정보 조회
	$('#dataTable tbody').on( 'dblclick', 'tr', function () {
		var rowData      = $('#dataTable').DataTable().row(this).data();
		var vSelectedIp  = rowData.ip;
        var vUseYn       = rowData.useYn;
        $('#userIpForm').empty();
        $('#userIpForm').html("<input id=\"userIp\" type=\"text\" class=\"form-control\" name=\"userIp\" autofocus readonly>");
        
        /** 사용여부에 따라서 버튼 출력여부 결정 */
        if(vUseYn == "Y"){
        	$.ajax({
        	 	type: 'POST',
        	 	dataType: 'json',
        	 	url:  g_contextWebRoot+'ajax/selectUserIp.do',
        	 	data: {userIp : vSelectedIp}, // 넘길 파라미터
        	 	success: function(resultData){
        	 		if(resultData.resultCode == "SUCCESS"){
        	 			var userInfo = resultData.data;
        	 			if(userInfo != null && userInfo.length > 0){
        	 				$("#userName").val(userInfo[0].recentUser);
        	 				$("#reason").val(userInfo[0].forWhat);
        	 				var entDate = (userInfo[0].useYn=='Y')?userInfo[0].entDate:"";
        	 				$('#entDate').val(entDate);
        	 			}
        		 	}else{
        	 			fnInquiryError();
        	 			return;
        	 		}
        	 	},
        	 	error: fnInquiryError
        	});
        	
        	fnBtnCtrl('UPDATE');
        }else{
        	fnBtnCtrl('NEW');        	
        }
        $('#userIp').val(vSelectedIp);
        $('#userIpStatus').modal('show'); //모달 띄우기
        
    } );
}

/**===============================================================================
// IP 리스트 검색
//===============================================================================*/
function fnInquiry(page){		

	$.ajax({
	 	type: 'POST',
	 	dataType: 'json',
	 	url:  g_contextWebRoot+'ajax/selectUserIp.do',
	 	//data: $('#'+g_strFrm).serialize(true), // 넘길 파라미터
	 	success: function(objJsonRowData){
	 		if(objJsonRowData.resultCode == "SUCCESS"){
	 			// Table Display
	 			fnSetTable(objJsonRowData);
		 	}else{
	 			fnInquiryError();
	 		}
	 	},
	 	error: fnInquiryError
	});
}

/**===============================================================================
// IP 결과 Display
//===============================================================================*/
function fnSetTable(objJsonRowData){
	var rowData = objJsonRowData.data;
	
	/**
	 * IP 현황 그리드 출력
	 * # 클래스 속성
	 * 1) 회색 - active
	 * 2) 초록 - success
	 * 3) 파란 - info
	 * 4) 빨간 - danger
	 */
	var ipList = "";
	for(var i=2;i<gCellTotCnt;i++){
		
		var ipSrchYn = false; // 데이터 존재여부
		
		// <tr> 시작점
		if(i%12 == 2){
			ipList = ipList + " <tr>";		
		}
		
		// td 데이터 입력
		if(i < gCellChkCnt){
			var stdIp = gStndrdIp+i; // 기준 IP 변경되도록 수정

			if(rowData != null && rowData.length > 0){
				for(var m=0;m<rowData.length;m++){
					if(rowData[m].ip == stdIp && rowData[m].useYn == "Y"){ // 값이 같으면
						ipList = ipList + "<td lastIp=\""+rowData[m].ip+"\" useYn=\""+rowData[m].useYn+"\" class=\"success ipCell\">";
						ipList = ipList + "		<span>"+rowData[m].ip+"</span><br>";
						ipList = ipList + "		<span><b>"+rowData[m].recentUser+"</b></span>";
						ipList = ipList + "</td>";
						ipSrchYn = true;
					}else if(rowData[m].ip == stdIp && rowData[m].useYn == "N"){
						ipList = ipList + "   <td lastIp=\""+rowData[m].ip+"\" useYn=\""+rowData[m].useYn+"\" class=\"active ipCell\">"+rowData[m].ip+"</td>";
						ipSrchYn = true;
					}
				}
			}
			
			if(!ipSrchYn){
				ipList = ipList + "   <td lastIp=\""+stdIp+"\" useYn=\"N"+"\" class=\"active ipCell\">"+stdIp+"</td>";
			}
		}else{
			ipList = ipList + "   <td></td>";
		}
		if(i%12 == 1){
			ipList = ipList + " </tr>";
		}
	}
	
	// 테이블 세팅
	$('#ipList').html(ipList);
	
	// 클릭 이벤트 설정
	$('#ipList tr td').on( 'dblclick', function (e) {
		fnInit();
		$('#userIpForm').empty();
        $('#userIpForm').html("<input id=\"userIp\" type=\"text\" class=\"form-control\" name=\"userIp\" autofocus readonly>");
		e.preventDefault();
		
		if(gSelectedCell != null){
			gSelectedCell.removeClass('selected');
		}
		$(this).addClass('selected');
		gSelectedCell = $(this);
	
		/** 선택된 변수 받아서 출력할 방법 찾기 */
        var vSelectedIp  = gSelectedCell.attr("lastIp");
        var vUseYn       = gSelectedCell.attr("useYn");

        /** 사용여부에 따라서 버튼 출력여부 결정 */
        if(vUseYn == "Y"){
        	
        	$.ajax({
        	 	type: 'POST',
        	 	dataType: 'json',
        	 	url:  g_contextWebRoot+'ajax/selectUserIp.do',
        	 	data: {userIp : vSelectedIp}, // 넘길 파라미터
        	 	success: function(resultData){
        	 		if(resultData.resultCode == "SUCCESS"){
        	 			var userInfo = resultData.data;
        	 			if(userInfo != null && userInfo.length > 0){
        	 				$("#userName").val(userInfo[0].recentUser);
        	 				$("#reason").val(userInfo[0].forWhat);
        	 				var entDate = (userInfo[0].useYn=='Y')?userInfo[0].entDate:"";
        	 				$('#entDate').val(entDate);
        	 			}
        		 	}else{
        	 			fnInquiryError();
        	 			return;
        	 		}
        	 	},
        	 	error: fnInquiryError
        	});
        	
        	fnBtnCtrl('UPDATE');
        }else{
        	fnBtnCtrl('NEW');       	
        }
        $('#userIp').val(vSelectedIp);
        $('#userIpStatus').modal('show'); //모달 띄우기
	});
	
	// 조회 후 화면처리
	//fnControlScreen("AFT_INQRY");
}

/**===============================================================================
// IP 조회 중 에러 발생
//===============================================================================*/
function fnInquiryError() {
	alert("조회 중 오류가 발생하였습니다.");	
}