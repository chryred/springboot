
var g_page = 1;
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
 
/***********************
 * [공통] 시작
 ***********************/
function fn_start() {
	selectGroupId();	 
}


/***********************
 * [공통] 이벤트
 ***********************/
function fn_event() {
  
	
	
	
	// 배치잡 엔터키시
	$("#jobName").keypress(function(e){
        if (e.which == 13) {
        	//fn_search();
        }
    });
	
	// 배치잡 엔터키시
	$("#folderPath").keypress(function(e){
        if (e.which == 13) {
        	//fn_search();
        }
    });
	
	// 조회버튼 클릭
	$("#btn_search").on("click", function() {
		fn_search();
	}); 
	
	$("#mgrGrade").on("change", function() {
		fn_search(); 
	});
	
	/*$("#btn_save").on("click", function() {
		fn_save(); 
	});
	*/
}

/***********************
 * [공통] 조회
 ***********************/
function fn_search() {

	var v_totCnt = 0;
	var v_okCnt  = 0;
	var v_failCnt = 0;
	var v_rate = 0;
	//alert($('#mgrGrade').val());
	var mgr_grade = $('#mgrGrade').val();
 
	if(mgr_grade == null){
		mgr_grade ="900";
	}else{
		mgr_grade = $('#mgrGrade').val();
	}
 
	var postData = {	
			authGroupid : mgr_grade		
	};
 
	var formURL = contextPath + "/adm/listMenuGruopJson.do";
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
				
				var v_result = result.rstList;		
				var v_paramCondition = result.paramCondition;			
				var v_str = "";
			 
				$.each(v_result, function (index, data) {							
					v_str += '<tr>';
					//v_str += '<td class="text-center">' + data.rownum + '</td>';	
					if(data.level == '1'){
						v_str += '<td class="text-left"> ▶&nbsp;  ' + data.name + '</td>';	
					}else{
						v_str += '<td class="text-left"> &nbsp;&nbsp;&nbsp;&nbsp;  -  '+ data.name + '</td>';	
					}
									 
					v_str += '<td class="text-center">';
					v_str += '<input type="hidden" id="menuId" name="menuId" value="'+data.id+'" >';
					v_str += '<input type="radio" id="authCd'+index+'" name="authCd'+index+'" value="XX"  style="width:17px;height:17px;vertical-align:middle;" ' + (data.authCd =="XX" ? "checked": "")+ ' >없음  '  
					v_str += '<input type="radio" id="authCd'+index+'" name="authCd'+index+'" value="VW"  style="width:17px;height:17px;vertical-align:middle;" ' + (data.authCd =="VW" ? "checked": "")+ '>조회  '
					v_str += '<input type="radio" id="authCd'+index+'" name="authCd'+index+'" value="IU"  style="width:17px;height:17px;vertical-align:middle;" ' + (data.authCd =="IU" ? "checked": "")+ '>실행 '  
					v_str += '</td>' 
					v_str += '<td class="text-center">' + data.useyn + '</td>';
					v_str += '<td class="text-left">' + data.menulink + '</td>';
					v_str += '</tr>';
				
				});						 				
				$("#table_tbody").html(v_str);
				$("#table_tbody").html(v_str);
				// ids.push($("#menugroup>table>tbody>tr:eq("+i+")>td>#menuId").val());
				// authCds.push($("#menugroup>table>tbody>tr:eq("+i+")>td>input[name=authCd"+i+"]:checked").val());
				// 클릭 이벤트 설정

				$('#menugroup>table>tbody>tr ').on('click', function (e) {
					 
					var selecTr = $(this).index(); 						 
					 
					var id = $("#menugroup>table>tbody>tr:eq("+selecTr+")>td>#menuId").val();
					var authCd = $("#menugroup>table>tbody>tr:eq("+selecTr+")>td>input[name=authCd"+selecTr+"]:checked").val();
					 
				    	var formURL = contextPath + "/adm/listMenuGruopSave.do";

				    	$.ajax({
				    		url : formURL,
				    		type : "POST",
				    		data : {
				    			id : id		   ,
				    			authCd : authCd,
				    			authGroupid :  $('#mgrGrade').val()	
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
 


function fn_save(){ 

	// alert($("#menugroup>table>tbody>tr>td>#menuId").val());

	 var postDatas = new Array();
	 var ids = new Array(), authCds= new Array();
	 
	 for(var i = 0; i <$("#menugroup>table>tbody>tr").length; i ++){
		 ids.push($("#menugroup>table>tbody>tr:eq("+i+")>td>#menuId").val());
		 authCds.push($("#menugroup>table>tbody>tr:eq("+i+")>td>input[name=authCd"+i+"]:checked").val());
		 var postData = {
				 "id":$("#menugroup>table>tbody>tr:eq("+i+")>td>#menuId").val(),
				 "authCd":$("#menugroup>table>tbody>tr:eq("+i+")>td>input[name=authCd"+i+"]:checked").val()
			};
		 
		 postDatas.push(postData);
	 }
	
	console.log(postDatas);
	 
	// return;
	//postDatas = postDatas.serializeArray();
		var formURL = contextPath + "/adm/updateMenugroup.do";
	 
		$.ajax({
			url : formURL,
			type : "POST",
			data : {data : postDatas},
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
				} else {
					alert("에러가 발생하였습니다.")
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("에러가발생하였습니다. 고객센터에 문의하세요.");
			}
		});
		
	 
}
function selectGroupId() { 

	
	var v_totCnt = 0;
	var v_okCnt  = 0;
	var v_failCnt = 0;
	var v_rate = 0;
	
	//var postData = $("#form1").serializeArray();
	var postData = {	
			 contextPath : contextPath
	};
	var formURL = contextPath +  "/adm/selectGroupId.do";
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
				
				var v_result = result.rstList;		
				var v_paramCondition = result.paramCondition;					
				var v_str = "";
				$("#mgrGrade").html("");
				$.each(v_result, function (index, data) {	
					
					$("#mgrGrade").append('<option value="' + data.authGroupid + '">' + data.authGroupnm + '</option>');
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

function getToday() {
	var v_date = new Date();
	
	return getFommattedDate(v_date);
}
 
 
 
 
 

function comma(num){
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

