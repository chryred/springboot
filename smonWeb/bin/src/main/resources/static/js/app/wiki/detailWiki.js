$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var titleRef = null;
var workRef = null;
var contentsRef = null;
var shareModalRef = null;
var shareBtnRef = null;
var editBtnRef = null;
var receiversInputRef = null;
var shareTitleInputRef = null;
var sendBlossomPushBtnRef = null;

/*
 * 초기화
 */
function init () {
	titleRef = $("#title");
	workRef = $("#work");
	contentsRef = $("#contents");
	shareModalRef = $("#share_modal").modal({
	    show: false
	});
	shareBtnRef = $("#share");
	editBtnRef = $("#edit");
	receiversInputRef = $("#receivers");
	shareTitleInputRef = $("#share_title");
	sendBlossomPushBtnRef = $("#send_blossom");

	hljs.initHighlightingOnLoad();
	
	loadData(global.seq);
		
	staticBindEvent();
	
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	
	$(window).resize(function() {
		var tableWidth = $(".table-wrapper").css("width");
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", tableWidth);
		$(".ui-jqgrid-hdiv").css("width", tableWidth);
		$(".ui-jqgrid-bdiv").css("width", tableWidth);
		
		$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
	});
	
	editBtnRef.click(function() {
		var isNew = global.isNew == "null" ? 1 : global.isNew;
		location.href = contextPath + "/wiki/edit.do?isNew=" + isNew + "&seq=" + global.seq;
	});
	
	shareBtnRef.click(function() {
		receiversInputRef.val("");
		shareTitleInputRef.val("");
		shareModalRef.modal("show");
	});
	
	sendBlossomPushBtnRef.click(function() {
		$.ajax({
			  url: contextPath + "/wiki/ajax/detail/share.do",
			  type: "POST",
			  data: {
				  receivers: receiversInputRef.val(),
				  shareTitle: shareTitleInputRef.val() == "" ? titleRef.text() : shareTitleInputRef.val(),
				  seq: global.seq
			  },
			  success: function(data) {
				  shareModalRef.modal("hide");
			  },
			  error: function(err) {
			  }
			});
	});
	
}

function loadData(seq) {
	$.ajax({
	  url: contextPath + "/wiki/ajax/edit/select.do",
	  dataType: "json",
	  type: "POST",
	  data: {
		  seq: seq
	  },
	  success: function(data) {
	      titleRef.html(data.TITLE);
	      workRef.html("["+data.WORK_NAME+"]");
	      contentsRef.html(data.CONTENTS_H);
	      
	      
	      if(data.FILE_LIST.length > 0){
	    	  $("#fileList").html("<a href='#' data-toggle='collapse' data-target='#fileDivList' >첨부파일("+ data.FILE_LIST.length +")</a>");

		      $("#fileList").append("<div id='fileDivList' class='collapse'>");
		      $("#fileDivList").append("<br>");
		      $.each(data.FILE_LIST, function(idx, value){
		    	  
		    	  var url = encodeURI(value.PATH + "/" + value.FILEN_NAME);
		    	  //$("#fileList").append("<div class='col-lg-11 col-xs-10'><a href='"+ value.PATH + "/" + value.FILEN_NAME + "'>" + value.FILEN_NAME + "</a></div>");
		    	  //$("#fileDivList").append( idx+1 +". <a href='#' onclick='downloadFile(" + seq + "," +  '"' +  value.FILEN_NAME + '"' +")'>" + value.FILEN_NAME + "</a><br>");
		    	
		    	  $("#fileDivList").append( idx+1 +". <a href='" + contextPath + "/wiki/ajax/detail/callDownload.do?sequence" + "=" + seq + "&fileName=" +  encodeURIComponent(value.FILEN_NAME) +"'>" + value.FILEN_NAME + "</a><br>");
		      })
		      
		      $("#fileList").append("</div>");
	      }
	      
	  },
	  error: function(err) {
	  }
	});
};

/**
function downloadFile(seq, fileName) {
	$.ajax({
	  url: contextPath + "/wiki/ajax/detail/callDownload.do",
	  dataType: "json",
	  type: "POST",
	  data: {
		  sequence : seq,
		  fileName : fileName
	  },
	  success: function(data) {
	      alert("11");
	  },
	  error: function(err) {
		  alert(err);
	  }
	});
};
**/