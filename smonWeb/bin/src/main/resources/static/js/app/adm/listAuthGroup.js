
var g_page = 1; 
var gSelectedCell;
var check_yn ="N";
var Gubn_chk="";
var g_intervalId;
var g_colid;
var g_sorting;

$(document).ready(function() {

	fn_start();
	fn_event();
	fn_search();	 
	
});

$.ajaxSetup({  
    global: false
}); 

function getChecked() {
	console.log($(".cb"));
}

/***********************
 * [공통] 시작
 ***********************/
function fn_start() {
 
	$("#baseDay").val(getToday());
	
	$("#dataTable thead tr th").addClass("sorting"); 	
	/*
	$("#dataTable").dataTable({
		responsive : true
	});	
	
	g_dataTable = $("#dataTable");
	*/
}


/***********************
 * [공통] 이벤트
 ***********************/
function fn_event() {
	$("#dataTable thead tr th").click(function () {
		$("#dataTable thead tr th").not(this).removeClass("sorting");
		$("#dataTable thead tr th").not(this).removeClass("sorting_asc");
		$("#dataTable thead tr th").not(this).removeClass("sorting_desc");
		$("#dataTable thead tr th").not(this).addClass("sorting"); 
		
		if ($(this).hasClass("sorting")) {
			g_sorting = "ASC";
			$(this).removeClass("sorting");
			$(this).addClass("sorting_asc");
		} else if ($(this).hasClass("sorting_desc")) {
			g_sorting = "ASC";
			$(this).removeClass("sorting_desc");
			$(this).addClass("sorting_asc");
		} else if ($(this).hasClass("sorting_asc")) {
			g_sorting = "DESC";
			$(this).removeClass("sorting_asc");
			$(this).addClass("sorting_desc");
		} else {
			g_sorting = "ASC";
			$(this).addClass("sorting_asc");
		}
		
		var v_colidx = $(this).attr("colidx");
		
		switch (v_colidx) {
			case "0" :
				g_colid = "AUTH_GROUP_ID";
				break;
			case "1" :
				g_colid = "AUTH_GROUP_NM";
				break;
			case "2" :
				g_colid = "USE_YN";
				break;				
			case "3" :
				g_colid = "ROLE_GROUP";
				break;
		 	
		}
		
		//alert(g_colid + g_sorting);
		
		fn_search();
	}); 
	
	
	// 추가버튼
	$("#btn_NEW").on("click", function() {
		fnInit();
		fnBtnCtrl('NEW');
		$('#groupStatus').modal('show'); //모달 띄우기        	 
	});
	// 조회버튼 클릭
	$("#btn_search").on("click", function() {
		fn_search();
	}); 
	
	// 권한생성버튼
	$("#reqNewGroup").on("click", function() {	 
		fn_save();			
	});
	
	// 수정버튼
	$("#updateGroup").on("click", function() {	 
		fn_update();			
	});
	
	// 수정버튼
	$("#deleteGroup").on("click", function() {	 
		fn_delete();			
	});
	//권한 중복 체크
	$('#authGroupid').blur(function(){		
		if(Gubn_chk == "NEW" && $("#authGroupid").val() != "") fn_check();
	});	
	
	//권한코드 숫자만 입력
	 $('#authGroupid').keyup(function(){
         $(this).val($(this).val().replace(/[^0-9]/g,""));
    }); 
	
}
 
 
function fnInit(){
	// 입력 폼 초기화
	$("#authGroupid").val('');
	$("#authGroupnm").val('');
	$("#authGroupremark").val('');
	$("#useyn").val('');
	$("#roleGroup").val('');
} 

/**===============================================================================
//버튼 컨트롤(NEW : 신규발급 / UPDATE : 수정/삭제)
//===============================================================================*/
function fnBtnCtrl(Gubn){
	Gubn_chk = Gubn
	if(Gubn == "NEW"){
		$('#reqNewGroup').show(); // 발급버튼 출력
	 	$('#updateGroup').hide(); // 수정버튼 숨김
	 	$('#deleteGroup').hide(); // 삭제버튼 숨김
	 	$('#authGroupid').prop('readonly' , false);
	}else if("UPDATE"){
		$('#reqNewGroup').hide(); // 발급버튼 숨김
	 	$('#updateGroup').show(); // 수정버튼 출력
	 	$('#deleteGroup').show(); // 삭제버튼 출력	 	
	 	$('#authGroupid').prop('readonly' , true);
	}
}
	/***********************
	 * [공통] 조회
	 ***********************/
	function fn_search() {
		
		var v_totCnt = 0;
		var v_okCnt  = 0;
		var v_failCnt = 0;
		var v_rate = 0;
		
		//var postData = $("#form1").serializeArray();
		var postData = {	
				 contextPath : contextPath
				 , pageScale : $("#pageScale").val()
					, curPage : g_page
					, colid : g_colid
					, sorting : g_sorting
					, authGroupnm: $("#auth_Group_nm").val()
					, useyn: $("#use_Yn").val()
					, roleGroup: $("#role_grouop").val()
					
		};
		var formURL = contextPath + "/adm/listAuthGruopJson.do";
		//$("#form1").attr("action");
		
		$.ajax({
			url : formURL,
			type : "POST",
			data : postData,
			dataType : "json", //text, json, html, xml, csv, script, jsonp
			async : true,
			timeout : 2 * 60 * 1000, //2 min,
			beforeSend : function() {
				$.blockUI({ css: {color: '#fff'} });
			},
			complete : function() {
				$.unblockUI();
				$("#span_totCnt").html(v_totCnt);
				$("#span_okCnt").html(v_okCnt);
				$("#span_failCnt").html(v_failCnt);
				$("#span_rate").html(v_rate);
			 
			},
			success : function(result) {		
				var v_chkResult = result.chkResult; //chkLogin 값 받기
				var v_errorMsg = result.errorMsg; //errorMsg 값 받기

				//로그인 처리시
				if (v_chkResult == "OK") {
					
					var v_result = result.rstList;		
					var v_paramCondition = result.paramCondition;					
					var v_str = "";

					var v_pageVO = result.pageVO;			 
					
					pageDiv(result); // 페이지 처리
					
					
					$.each(v_result, function (index, data) {							
						v_str += '<tr>';					
						//v_str += '<td align="center"><input type="checkbox" id="chk_i"></td>';
						v_str += '<td class="text-center">' + data.authGroupid + '</td>';
						v_str += '<td class="text-left">' + data.authGroupnm + '</td>';
						v_str += '<td class="text-left">' + data.authGroupremark + '</td>';
						v_str += '<td class="text-center">' + data.useyn + '</td>';
						v_str += '<td class="text-center">' + data.roleGroup + '</td>';
						v_str += '</tr>';
					});
					
					$("#table_tbody").html(v_str);
				 
					
					// 클릭 이벤트 설정
					$('#table_tbody tr').on('dblclick', function (e) {
						fnInit();
						var selecTr = $(this).index(); 						 
						var authGroupid  =  $("#table_tbody > tr:eq("+selecTr+")>td:eq(0)").html();
					 
					    	var formURL = contextPath + "/adm/selectauthGroup.do";

					    	$.ajax({
					    		url : formURL,
					    		type : "POST",
					    		data : {
					    			      authGroupid : authGroupid		     
					    		       },
					    		dataType : "json", //text, json, html, xml, csv, script, jsonp
					    		async : true,
					    		timeout : 2 * 60 * 1000, //2 min,
					    		beforeSend : function() {			 
					    		//	$.blockUI({ css: { color: '#fff'} });
					    		},
					    		complete : function() {
					    			$.unblockUI();				
					    		},
					    		success : function(result) {		
					    			var v_chkResult = result.chkResult; //chkLogin 값 받기
					    			var v_errorMsg = result.errorMsg; //errorMsg 값 받기
					    			
					    			//로그인 처리시
					    			if (v_chkResult == "OK") {
					    				$("#authGroupid").val(result.authGroupid);
				    	 				$("#authGroupnm").val(result.authGroupnm);
				    	 				$("#authGroupremark").val(result.authGroupremark);
				    	 				$("#useyn").val(result.useyn);
				    	 				$("#roleGroup").val(result.roleGroup);
					    			} else {
					    				alert("에러가 발생하였습니다.");
					    			}
					    			$.unblockUI();
					    		},
					    		error : function(jqXHR, textStatus, errorThrown) {
					    			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
					    			$.unblockUI();
					    		}
					    	});
					    	
					    	fnBtnCtrl('UPDATE');
					    	  
					    $('#groupStatus').modal('show'); //모달 띄우기
					});
				} else {
					alert("에러가 발생하였습니다.")
				}
				$.unblockUI();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("에러가발생하였습니다. 고객센터에 문의하세요.");
				$.unblockUI();
			}
		});
	}

	/***********************
	 * [공통] 권한코드 중복조회
	 ***********************/
	function fn_check() {		
  
		var postData = {	
				authGroupid : $('#authGroupid').val()
		};
		var formURL = contextPath + "/adm/listAuthGruopcheck.do";
		//$("#form1").attr("action");
		
		$.ajax({
			url : formURL,
			type : "POST",
			data : postData,
			dataType : "json", //text, json, html, xml, csv, script, jsonp
			async : true,
			timeout : 2 * 60 * 1000, //2 min,
			beforeSend : function() {
				$.blockUI({ css: {color: '#fff'} });
			},
			complete : function() {
				$.unblockUI();			 
			},
			success : function(result) {		
				var v_chkResult = result.chkResult; //chkLogin 값 받기
				var v_errorMsg = result.errorMsg; //errorMsg 값 받기

				//로그인 처리시
				if (v_chkResult == "OK") {
					 	
					
					if(result.count > 0){
						alert("사용중인 권한 코드가 있습니다.");
						$('#authGroupid').focus();
						check_yn = "N";
					}else{
						alert("사용가능한 권한 코드 입니다.");
						check_yn = "Y";
					}
				 		 
				
				} else {
					alert("에러가 발생하였습니다.")
				}
				$.unblockUI();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("에러가발생하였습니다. 고객센터에 문의하세요.");
				$.unblockUI();
			}
		});
	}
	/***********************
	 * [] 권한코드 저장
	 ***********************/
	function fn_save() {	
		
		if(check_yn == "N"){
			alert("권한코드를 확인해 주세요.");
			$('#authGroupid').focus();
			return;
		}
		if($("#authGroupid").val().length != 3){
			alert("권한코드를 3자리 입력해 주세요.");
			$('#authGroupid').focus();
			return;
		}
		if($('#authGroupnm').val() ==""){
			alert("권한명을 입력해 주세요.");
			$('#authGroupnm').focus();
			return;
		}		
		if($('#authGroupremark').val() == ""){
			alert("권한설명을 입력해 주세요.");
			$('#authGroupremark').focus();
			return;
		}	 
		if($('#useyn').val() == ""){
			alert("사용여부를 체크해 주세요.");
			$('#useyn').focus();
			return;
		}		
		if($('#roleGroup').val() == ""){
			alert("roleGroup을 체크해 주세요.");
			$('#roleGroup').focus();
			return;
		}
		
		var postData = $("#form_authGroup").serializeArray();
		var formURL = contextPath + "/adm/listAuthGruopSave.do";

		if (!confirm("저장하시겠습니까?")) {
			return;
		}
		
		$.ajax({
			url : formURL,
			type : "POST",
			data : postData,
			dataType : "json", //text, json, html, xml, csv, script, jsonp
			async : true,
			timeout : 2 * 60 * 1000, //2 min,
			beforeSend : function() {
			},
			complete : function() {
			},
			success : function(result) {			
				var v_chkResult = result.chkResult; //chkLogin 값 받기
				var v_errorMsg = result.errorMsg; //errorMsg 값 받기

				//로그인 처리시
				if (v_chkResult == "OK") {
					alert("정상처리 되었습니다.");
					fnInit();
					$('#groupStatus').modal('hide');  
					fn_search();
					//fn_UpdateKorean();				
				} else {
					alert("에러가 발생하였습니다.")
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("에러가발생하였습니다. 고객센터에 문의하세요.");
			}
		});
	}


function fn_update() {	
		
		 
		if($('#authGroupnm').val() ==""){
			alert("권한명을 입력해 주세요.");
			$('#authGroupnm').focus();
			return;
		}		
		if($('#authGroupremark').val() == ""){
			alert("권한설명을 입력해 주세요.");
			$('#authGroupremark').focus();
			return;
		}	 
		if($('#useyn').val() == ""){
			alert("사용여부를 체크해 주세요.");
			$('#useyn').focus();
			return;
		}		
		if($('#roleGroup').val() == ""){
			alert("roleGroup을 체크해 주세요.");
			$('#roleGroup').focus();
			return;
		}
		
		var postData = $("#form_authGroup").serializeArray();
		var formURL = contextPath + "/adm/listAuthGruopSave.do";

		if (!confirm("변경하시겠습니까?")) {
			return;
		}
		
		$.ajax({
			url : formURL,
			type : "POST",
			data : postData,
			dataType : "json", //text, json, html, xml, csv, script, jsonp
			async : true,
			timeout : 2 * 60 * 1000, //2 min,
			beforeSend : function() {
			},
			complete : function() {
			},
			success : function(result) {			
				var v_chkResult = result.chkResult; //chkLogin 값 받기
				var v_errorMsg = result.errorMsg; //errorMsg 값 받기

				//로그인 처리시
				if (v_chkResult == "OK") {
					alert("정상처리 되었습니다.");
					fnInit();
					$('#groupStatus').modal('hide');  
					fn_search();
					//fn_UpdateKorean();				
				} else {
					alert("에러가 발생하였습니다.")
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("에러가발생하였습니다. 고객센터에 문의하세요.");
			}
		});
	}


function fn_delete() {			  
		
		var postData = $("#form_authGroup").serializeArray();
		var formURL = contextPath + "/adm/listAuthGruopDelete.do";

		if (!confirm("삭제하시겠습니까?")) {
			return;
		}
		
		$.ajax({
			url : formURL,
			type : "POST",
			data : postData,
			dataType : "json", //text, json, html, xml, csv, script, jsonp
			async : true,
			timeout : 2 * 60 * 1000, //2 min,
			beforeSend : function() {
			},
			complete : function() {
			},
			success : function(result) {			
				var v_chkResult = result.chkResult; //chkLogin 값 받기
				var v_errorMsg = result.errorMsg; //errorMsg 값 받기

				//로그인 처리시
				if (v_chkResult == "OK") {
					alert("정상처리 되었습니다.");
					fnInit();
					$('#groupStatus').modal('hide');  
					fn_search();
					//fn_UpdateKorean();				
				} else {
					alert("에러가 발생하였습니다.")
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("에러가발생하였습니다. 고객센터에 문의하세요.");
			}
		});
	}
	

function getToday() {
	var v_date = new Date();
	
	return getFommattedDate(v_date);
}

function getDayObj(p_str) {
	var v_from = p_str.split("-");
	
	var v_inputDay = new Date(v_from[0], v_from[1] - 1, v_from[2]);
	var v_prevDay = new Date();
	var v_nextDay = new Date();
	
	v_prevDay.setTime(v_inputDay.getTime() - (24 * 60 * 60 * 1000)); // 이전날짜
	v_nextDay.setTime(v_inputDay.getTime() + (24 * 60 * 60 * 1000)); // 이후날짜
	
	var v_obj = new Object();
	
	v_obj.today = getFommattedDate(v_inputDay);
	v_obj.prevDay = getFommattedDate(v_prevDay);
	v_obj.nextDay = getFommattedDate(v_nextDay);
	
	return v_obj;
}

function getFommattedDate(p_date) {
	var v_year = p_date.getFullYear();
	var v_month = p_date.getMonth() + 1; // 0부터 시작하므로 1더함 더함
	var v_day = p_date.getDate();

	if (("" + v_month).length == 1) {
		v_month = "0" + v_month;
	}
	if (("" + v_day).length == 1) {
		v_day = "0" + v_day;
	}
	
	return v_year + "-" + v_month + "-" + v_day;
}

function getColorStatus(p_str) {
	var v_color = "";
	
	switch (p_str) {
		case "FOK" :
			v_color = "#0100FF";
			break;
		case "FWW" :
			v_color = "#FF0000";
			break;
		default :
			v_color = "#8C8C8C";
			break;
	}
	
	return v_color;
}

 
 
function comma(num){
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


function list(p_page) {
	g_page = p_page;
	fn_search();
}

function pageDiv(p_result) {
	var p_obj = p_result.pageVO;
	var p_paramCondition = p_result.paramCondition;
	var p_count = p_result.count;
	
	var html = '';
	html += '<nav aria-label="Page navigation example">';
	html += '<ul class="pagination justify-content-center">';
	html += '<!-- **처음페이지로 이동 : 현재 페이지가 1보다 크면  [처음]하이퍼링크를 화면에 출력-->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'1\')">처음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">처음</a></li>';
	}
	html += '<!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.prevPage + '\')">이전</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">이전</a></li>';
	}
	html += '<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->'
	
	for (var idx=p_obj.blockBegin;idx<=p_obj.blockEnd;idx++) {
		if (p_obj.curPage == idx) {
			html += '<!-- **현재페이지이면 하이퍼링크 제거 -->';
			html += '<li class="page-item"><a class="page-link" style="background-color:#EAEAEA;"href="#"><span style="color:red;">' + idx + '</span></a></li>';
		} else {
			html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + idx + '\')">' + idx + '</a></li>';
		}
	}

	html += '<!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.nextPage + '\')">다음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:list(\'' + p_obj.nextPage + '\')">다음</a></li>';
	}
	
	html += '<!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:list(\'' + p_obj.totPage + '\')">끝</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:list(\'' + p_obj.totPage + '\')">끝</a></li>';
	}
	
	html += '</ul>';
	html += '</nav>';
	
	$("#div_page").html(html);
	$("#div_count").html('<span>Showing ' + p_paramCondition.start + ' to ' + p_paramCondition.end + ' of ' + p_count + ' entries</span>');
}

 
