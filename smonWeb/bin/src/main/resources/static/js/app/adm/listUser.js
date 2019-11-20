
var g_page = 1;
var g_intervalId;
var g_colid;
var g_sorting;
var sUserList ;


$(document).ready(function() {
 sUserList = "";

 getChecked();
 console.log("xxxxxx");

 fn_start();
 fn_event();
 //fn_search();
selectGroupId();
 fn_search2();
 
 //승인버튼 클릭
 $("#btn_ok").on("click", function() {
 
  fn_update2();

 });
 
 

});

$(".chk-cb").click(function() {
 
 console.log("abc");
 console.log($(this));
 alert($(this).parent().parent().children("td:nth-child(5)").text());
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
 $("#chk_toggle").bootstrapToggle('off');
 
 $("#baseDay").val(getToday());
 
 $("#toggle").qtip({ // Grab some elements to apply the tooltip to
     id: 1,
     content: {
           text: '10초마다 자동갱신'
         , title: '자동갱신여부'    
     }
 });
 
 $("#dataTable thead tr th").addClass("sorting"); 
 
 /*
 $("#dataTable").dataTable({
  responsive : true
 }); 
 
 g_dataTable = $("#dataTable");
 */
   // v_authGroupnm1 = "";
 //selectGroupId();
 //selectGroupId2();
 //v_authGroupnm1 = data.authGroupnm;
 //alert(v_authGroupnm1);
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
    g_colid = "MGR_ID";
    fn_search2();
    break;
   case "1" :
    g_colid = "MGR_NAME";
    fn_search2();
    break;
   case "2" :
    g_colid = "mgr_Grade";
    fn_search2();
    break;    
   case "3" :
    g_colid = "MGR_STATE_CD";
    fn_search2();
    break;
 
   
  }
 
  //alert(g_colid + g_sorting);
  
  //fn_search();
 }); 
 
 $('#chk_toggle').change(function() {
   if ($(this).prop('checked')) {
    g_intervalId = setInterval(function() { 
    // fn_search(); 
    }, 10000);
   } else {
    clearInterval(g_intervalId);
   };
 }); // does not work 
 
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
  fn_search2();
 
 });
 
 // 그리드 체크박스 클릭
 $("#checkbox").on("click", function() {
  fn_search2();
 });
 
 // 
 $("#pageScale").on("change", function() {
  fn_search2();
 });
 
 // 그리드 체크박스 클릭
 $("#chk_no").on("click", function() { 	 
	 if($("#chk_no").prop("checked")) { 
		 $("input[type=checkbox]").prop("checked",true); 
	 }else { 
		 $("input[type=checkbox]").prop("checked",false); 
	 }	 

 });
 
 $("#span_prev").on("click", function() {
  var v_baseDay = $("#baseDay").val();
  
  if (v_baseDay.length != 10) {
   alert("날짜 자리수가 맞지 않습니다.");
   return;
  }
  
  var v_obj = getDayObj(v_baseDay);
  
  $("#baseDay").val(v_obj.prevDay);
 });
 
 $("#span_next").on("click", function() {
  var v_baseDay = $("#baseDay").val();
  
  if (v_baseDay.length != 10) {
   alert("날짜 자리수가 맞지 않습니다.");
   return;
  }
  
  var v_obj = getDayObj(v_baseDay);
  
  $("#baseDay").val(v_obj.nextDay);
 });
 
 
 $(window).resize(function() {
  var tableWidth = $(".table-wrapper").css("width");
  $(".ui-jqgrid").css("width", tableWidth);
  $(".ui-jqgrid-view").css("width", tableWidth);
  $(".ui-jqgrid-hdiv").css("width", tableWidth);
  $(".ui-jqgrid-bdiv").css("width", tableWidth);
  
  //$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
 });
 
 $('#baseDay').datepicker({
  userCurrent: false,
  dateFormat: "yy-mm-dd",
  setDate: ""
 });
}
 
 /***********************
  * [공통] 조회(좌측 트리)
  ***********************/
 function fn_search2() {

//alert(mgrGrade);
  var v_totCnt = 0;
  var v_okCnt  = 0;
  var v_failCnt = 0;
  var v_rate = 0;
  
  //var postData = $("#form1").serializeArray();
  var postData = {
     mgrGrade : $("#mgrGrade").val()
   , mgrId : $("#mgrId").val() 
   , mgrName : $("#mgrName").val() 
   //, mgrName : $("#mgrName").val() 
   , pageScale : $("#pageScale").val()
   , mgrStatecd : $("#useYn").val()
   , contextPath : contextPath
   , curPage : g_page
   , colid : g_colid
   , sorting : g_sorting
  };
  var formURL = contextPath + "/adm/listUserJson.do";
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
    // alert("정상처리 되었습니다.");
     //alert(JSON.stringify(result.rstList));
     
     var v_result = result.rstList;
     var v_pageVO = result.pageVO;
     var v_paramCondition = result.paramCondition;
     
     
     pageDiv(result); // 페이지 처리
     
     var v_str = "";
     
     $.each(v_result, function (index, data) {
     // alert("v_result : " + v_result);
     // alert("index : " + index);
      
      if (index==0) {
      // alert("조회내부함수 !!! ");
       v_totCnt = comma(data.totCnt);
       v_okCnt = comma(data.totOkCnt);
       v_failCnt = comma(data.totCnt - data.totOkCnt);
       v_rate = data.totRate;
      }
      
      
      var adminSelected = data.mgrGrade =="900" ?  "selected":""; //900 시스템
      var userSelected = data.mgrGrade =="100" ?  "selected":""; //100 유저
      var testSelected = data.mgrGrade =="700" ?  "selected":""; //700 관계사테스트
       
      var useY = data.mgrStatecd =="Y" ?  "selected":"";
      var useN= data.mgrStatecd =="N" ?  "selected":"";
      var useR= data.mgrStatecd =="R" ?  "selected":"";
      
      var idm = data.mgrId+"_mgrGrade"
      var ids = data.mgrId+"_mgrStatecd"
   
      v_str += '<tr>';
      v_str += '<td align="center"><input type="checkbox" name="chk_user" id="chk_user" onclick="" value="' + data.mgrId + '"></td>';
      v_str += '<td class="text-center">' + data.mgrId + '</td>';
      v_str += '<td class="text-center">' + data.mgrName + '</td>';
      //v_str += '<td class="text-center"><a href="javascript:aaaa();">' + data.mgrGrade + '</a></td>';
      //v_str += '<td class="text-center"><a href="javascript:bbbb();">' + data.mgrStatecd + '</a></td>';
      v_str += '<td class="text-center">' +
      // '<select id='+idm+' name='+idm+' style="width: 80%">' +
      //  '<option value="admin"'+adminSelected+' >admin</option>' +
      //  '<option value="user" '+userSelected+'>user</option>' +
      '<select id='+idm+' name='+idm+' style="width: 100%">' +
      '<option value=""></option>' +
      '<option value="test"'+testSelected+' >관계사테스트</option>' +
      '<option value="user"'+userSelected+' >일반사용자</option>' +
      '<option value="admin"'+adminSelected+' >시스템관리자</option>' +
         '</select>'
      + '</td>';
      
      v_str += '<td class="text-center">' + 
      '<select id='+ids+' name='+ids+' style="width: 100%">' +
            '<option value="Y" '+useY+' >사용</option>' +
         '<option value="N" '+useN+'>미사용</option>' +
         '<option value="R" '+useR+'>승인대기</option>' + 
         '</select>'
      +'</td>';
      
      v_str += '</tr>';
     });
     
     $("#table_tbody").html(v_str);
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
     
     //var v_authGroupnm = "";
     
     $("#mgrGrade").html("");
     $("#mgrGrade").append('<option value="">전체</option>');
     $.each(v_result, function (index, data) { 
      
      $("#mgrGrade").append('<option value="' + data.authGroupid + '">' + data.authGroupnm + '</option>');
      
      //v_authGroupnm = data.authGroupnm;
      v_authGroupnm1 = data.authGroupnm;
      //alert(v_authGroupnm1);
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

function list(p_page) {
 g_page = p_page;
 fn_search2();
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

function comma(num){
 return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


function chkUserInfo(){
  
  var cnt = 0;
  //alert($("input[name='chk_user']:checked").length);
  $("input[name='chk_user']:checked").each(function(idx,elem){
   //if($(this).is(":checked")){
    if(cnt==0){
     sUserList = $(this).val();
     var aa = $(this).val()+"_mgrGrade";
     var bb = $(this).val()+"_mgrStatecd";
     
     var val1 = $("select[name='"+aa+"'] ").val();
     var val2 = $("select[name='"+bb+"'] ").val();
     
     sUserList = $(this).val()+"@"+val1+"@"+val2;
    }else{
     var aa = $(this).val()+"_mgrGrade";
     var bb = $(this).val()+"_mgrStatecd";
     var val1 = $("select[name='"+aa+"']").val();
     var val2 = $("select[name='"+bb+"']").val();
      
     sUserList= sUserList+","+$(this).val()+"@"+val1+"@"+val2;
    }
    cnt++;
   //}
  });
  
  //alert(sUserList);
 }





///////////////////////////////////////// //////////////////////////////

function fn_update2() {
 chkUserInfo();
 //alert(sUserList);
 if(sUserList==""){
 alert("승인처리 할 대상을 선택 해 주세요.");
 return;
 }
 
 if(!confirm("승인하시겠습니까?")){
  alert(sUserList);
  return;
 }
 
 
 var v_totCnt = 0;
 var v_okCnt  = 0;
 var v_failCnt = 0;
 var v_rate = 0;
 
 //var postData = $("#form1").serializeArray();
 var postData = {
   sUserList :sUserList
  , contextPath : contextPath
  , curPage : g_page
  , colid : g_colid
  , sorting : g_sorting
 };
 var formURL = contextPath + "/adm/listUserUpdateJson.do";
 
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
    //alert("정상처리 되었습니다.");
    //fn_search2();
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