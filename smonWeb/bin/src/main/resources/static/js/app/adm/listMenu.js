
var g_treeObj;
var g_setting; // zTree 세팅
var g_folderPath="";
var g_rstList;
var g_position = "";
var scales = ["hours", "days"];
var Gubn_chk="";
var check_yn="N";
var settings = {
          source: null,
          itemsPerPage: 7,
          months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
          dow: ["일", "월", "화", "수", "목", "금", "토"],
          startPos: new Date(),
          navigate: "buttons",
          scale: "days",
          useCookie: false,
          maxScale: "months",
          minScale: "hours",
          waitText: "Please wait...",
          onItemClick: function (data) { return; },
          onAddClick: function (data) { return; },
          onRender: function() { return; },
          scrollToToday: true,
          hours : ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"],
          //minutes : ["00", "10", "20", "30", "40", "50"],
          minutes : ["00","30"],
          labelWidthPx : 300,
          heightPx : 25,
          widthPx : 60,
          jobCount : 500
      };

$(document).ready(function() {
	fn_start();
	fn_event();
	fn_search();
	
	$("#menuId").qtip({ // Grab some elements to apply the tooltip to
	    id: 1,
	    content: {	    	 
	         text: 'ex)파일경로명 + 순번 (batch + 1003)'
	        , title: '메뉴번호번호를 입력하세요.'    	        
         },        
         position: {
			  my: 'left top',  // Position my top left...
			  at: 'right top', // at the bottom right of...
		   }	      
	});
	
	$("#menuOrder").qtip({ // Grab some elements to apply the tooltip to
	    id: 1,
	    content: {	    	 
	         text: 'ex)추가 메뉴 위치 선정'
	        , title: '메뉴바에 출력될 메뉴 순번을 입력하세요.'    	        
         },        
         position: {
			  my: 'left top',  // Position my top left...
			  at: 'left bottom', // at the bottom right of...
		   }	      
	});
	
	$("#menuNm").qtip({ // Grab some elements to apply the tooltip to
	    id: 1,
	    content: {	    	 
	         text: 'ex)사용자관리'
	        , title: '메뉴바에 출력될 메뉴명을 입력해주세요.'    	        
         },        
         position: {
			  my: 'left top',  // Position my top left...
			  at: 'right top', // at the bottom right of...
		   }	      
	});
	
	$("#upMenuId").qtip({ // Grab some elements to apply the tooltip to
	    id: 1,
	    content: {	    	 
	         text: 'ex)메뉴가 추가될 상위경로 선택 후 메뉴추가시 자동입력'
	        , title: '싱위 메뉴 번호를 입력해주세요.'    	        
         },        
         position: {
			  my: 'left top',  // Position my top left...
			  at: 'left bottom', // at the bottom right of...
		   }	      
	});
	
	$("#menuImg").qtip({ // Grab some elements to apply the tooltip to
	    id: 1,
	    content: {	    	 
	         text: 'ex) 소메뉴(하위 메뉴)'
	        , title: '메뉴바에 표시될 아이콘을 선택하세요.'    	        
         },        
         position: {
			  my: 'left top',  // Position my top left...
			  at: 'left bottom', // at the bottom right of...
		   }	      
	});
	
	$("#menulink").qtip({ // Grab some elements to apply the tooltip to
	    id: 1,
	    content: {	    	 
	         text: 'ex)/batch/batch.do'
	        , title: '추가된 메뉴의 경로를 입력해주세요.'    	        
         },        
         position: {
			  my: 'left top',  // Position my top left...
			  at: 'left bottom', // at the bottom right of...
		   }	      
	});
	
});

$.ajaxSetup({  
    global: false
}); 


/***********************
 * [공통] 시작
 ***********************/
function fn_start() {
	 
	
	fn_zTreeInit(); // zTree 초기세팅
	//fn_tableInit(); // 테이블 초기세팅
}

/***********************
 * [공통] 이벤트
 ***********************/
function fn_event() {
	// 배치잡 엔터키시
 
	// 조회버튼 클릭
	$("#btn_search").on("click", function() {
		fn_search();
		
	});
	$("#btn_new").on("click", function() {
		// Init();
		$("#menuId").prop("readonly", false);
		$("#upMenuId").val($("#menuId").val());
		$("#menuId").val("");		
		$("#menuNm").val("");
		$("#menuOrder").val("");
		$("#menulink").val("");
		$("#useyn").val("%");
		$("#menuImg").val("%");
		 Gubn_chk = "NEW";
	});
	$("#btn_save").on("click", function() {
		fn_save();
	});
	$("#btn_del").on("click", function() {
		fn_del();
	}); 
	//권한 중복 체크
	$('#menuId').blur(function(){		
		if(Gubn_chk == "NEW")  fn_check();
	});	
 
	
	 
	
	
	$(window).resize(function() {
		var tableWidth = $(".table-wrapper").css("width");
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", tableWidth);
		$(".ui-jqgrid-hdiv").css("width", tableWidth);
		$(".ui-jqgrid-bdiv").css("width", tableWidth);
		
		//$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
	});
	
	 
}

function Init() {
	$("#menuId").prop("readonly", false);
	$("#menuId").val("");
	$("#upMenuId").val("");
	$("#menuNm").val("");
	$("#menuOrder").val("");
	$("#menulink").val("");
	$("#useyn").val("%");
	$("#menuImg").val("%");
}

/***********************
 * [공통] 조회(좌측 트리)
 ***********************/
function fn_search() {
	//var postData = $("#form1").serializeArray();
	var postData = {
		   contextPath : contextPath
	};
	 
	var formURL = contextPath + "/adm/listMenuJson.do";
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
			if (g_folderPath != "") {
				fn_searchDetail(g_folderPath); // 기존 폴더가 있을경우 검색
				var v_node = g_treeObj.getNodeByParam("id", g_folderPath);
				g_treeObj.selectNode(v_node);	
			}
		
		},
		success : function(result) {		
			var v_chkResult = result.chkResult; //chkLogin 값 받기
			var v_errorMsg = result.errorMsg; //errorMsg 값 받기
			
			
			//로그인 처리시
			if (v_chkResult == "OK") {			
				g_treeObj = $.fn.zTree.init($("#treeDemo"), g_setting, result.rstList);							
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
 * [공통] zTree 초기화 
 ***********************/
function fn_zTreeInit() {
	g_setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};
	/** 클릭 이전 이벤트 */
	function beforeClick(treeId, treeNode, clickFlag) {
		
	}
	/** 클릭 이벤트 */
	function onClick(event, treeId, treeNode, clickFlag) {
		g_folderPath = treeNode.id; // 폴더 path
		var v_lev = treeNode.lev;
		
		if (v_lev == 1) {
			
		}
		
		fn_searchDetail(g_folderPath);
	}
}

/***********************
 * [조회] 테이블 및 차트 그리기
 ***********************/
function fn_searchDetail(p_folderPath, p_index) {
	//var postData = $("#form1").serializeArray();
	//$("#form1").attr("action");
 
	var formURL = contextPath + "/adm/MenuDetailJson.do";

	$.ajax({
		url : formURL,
		type : "POST",
		data : {
			      folderPath : p_folderPath			     
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
				//alert(result.name);
				form1.menuId.value = result.id;
				form1.upMenuId.value = result.pId1;
				form1.menuNm.value = result.name;
				form1.menuOrder.value = result.menuorder;
				form1.menulink.value = result.menulink;
				form1.useyn.value = result.useyn;
				form1.menuImg.value = result.menuImg;
			//	$("#useYn").val(result.useyn);
			} else {
				alert("에러가 발생하였습니다.");
			}
			$.unblockUI();
			$("#menuId").prop("readonly", true);
			 Gubn_chk = "";
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
			$.unblockUI();
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

function fn_save() {	
	
	 
	if($('#menuId').val() == ""){
		alert("메뉴번호를 입력해주세요.");
		$('#menuId').focus();
		return;
	}
	if($('#menuNm').val() == ""){
		alert("메뉴명을 입력해주세요.");
		$('#menuNm').focus();
		return;
	}
	if($('#upMenuId').val() == ""){
		alert("상위메뉴번호를 입력해주세요.");
		$('#upMenuId').focus();
		return;
	}
	if($('#useyn').val() == "%"){
		alert("사용여부를 선택해주세요.");
		$('#useyn').focus();
		return;
	}
	
	if (!confirm("저장하시겠습니까?")) {
		return;
	}
	
	var postData = $("#form1").serializeArray();
	var formURL = contextPath + "/adm/updateMenuJson.do";
 
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
				Init();			
				fn_search();			  	
			} else {
				alert("에러가 발생하였습니다.")
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
		}
	});
}



function fn_del() {	
 
	var postData = $("#form1").serializeArray();
	var formURL = contextPath + "/adm/deleteMenuJson.do";
	

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
				alert("삭제 되었습니다.");
				Init();		
				g_folderPath="";
				fn_search();
			} else {
				alert("에러가 발생하였습니다.")
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러가발생하였습니다. 고객센터에 문의하세요.");
		}
	});
}


/***********************
 * [공통] 메뉴번호 중복조회
 ***********************/
function fn_check() {		

	var postData = {	
			id : $('#menuId').val()
	};
	var formURL = contextPath + "/adm/listMenuIdcheck.do";
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
				 	
				
				if(result.count > 0 && $('#menuId').val() != ""){
					alert("사용중인 메뉴번호가 있습니다.");
					$('#menuId').focus();
					check_yn = "N";
				}else{
				//	alert("사용가능한 메뉴번호 입니다.");
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
