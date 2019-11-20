$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var titleInputRef = null;

var uploaderModalRef = null;
var uploaderOpenBtnRef = null;
var uploaderTabRef = null;
var uploaderFormRef = null;
var uploaderFileRef = null;
var uploaderUrlRef = null;
var uploaderConfirmBtnRef = null;
var saveBtnRef = null;
var workCodeComboRef = null;

var newSeq = null;
var seq = null;
var imageGubn = null;

/*
 * 초기화
 */
function init () {
	titleInputRef = $("#title");
	
	uploaderOpenBtnRef = $('<i id="add_picture" class="fa fa-picture-o" aria-hidden="true"></i>');
	//이미지 업로드 모달 초기화
	uploaderModalRef = $("#uploader_modal").modal({
	    show: false
	});
	uploaderTabRef = $(".uploader_tab");
	uploaderUrlRef = $("#upload_url");
	uploaderFormRef = $("#upload_form");
	uploaderFileRef = $("#uploadfile");
	uploaderConfirmBtnRef = $("button#uploader_confirm");
	uploaderSelectBtnRef = $("button#uploader_select");
	
	seq = $("#seq");
    imageGubn = $("#imageGubn");

	saveBtnRef = $("#save");
	
	workCodeComboRef = $("#workCode");
	
	initCombo('work', workCodeComboRef);
		
	hljs.initHighlightingOnLoad();
	
	CKEDITOR.replace("editor");
	
	CKEDITOR.editor.prototype.readOnly = true;
	
	CKEDITOR.on("instanceReady", function() {
		$(".cke_toolbox").append(uploaderOpenBtnRef);
		CKEDITOR.instances.editor.resize('100%', $(window).height() - 380);
		$(".container").show();
	
		if(global.isNew == 0) {
			loadData(global.seq);
			seq.val(global.seq);
		}else{
			selectSequence();
			
		}
		
		
	});
	
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
	
	//본문 이미지 삽입 업로더 모달 열기
	uploaderOpenBtnRef.click(function() {
	    uploaderModalReset();
	    uploaderModalRef.modal("show");
	    imageGubn.val("1");   //이미지
	});
	
	//파일첨부 업로더 모달 열기
	uploaderSelectBtnRef.click(function() {
	    uploaderModalReset();
	    uploaderModalRef.modal("show");
	    imageGubn.val("2");    //파일
	});
	
	saveBtnRef.click(function() {
		$.ajax({
			url: contextPath + "/wiki/ajax/edit/save.do",
			type: "POST",
			dataType: "text",
			beforeSubmit: function(data, frm, opt) {
				if(workCodeComboRef.val() == null){
					alert("업무명을 선택해 주세요");
					
					return false;
				}
			},
			data: {
				isNew: global.isNew == "null" ? 1 : global.isNew,
				title: titleInputRef.val(),
				workCode: workCodeComboRef.val(),
				contentsH: CKEDITOR.instances.editor.getData(),
				contentsS: CKEDITOR.instances.editor.getData().replace(/<\/?[^>]+(>|$)/g, ""),
				seq: global.isNew == "null" ? newSeq : global.seq
			},
			success: function(result) {
				alert("저장되었습니다.");
				if(global.isNew == 1) {
					window.history.back();
				}				
			},
			error: function() {
				alert("실패했습니다.");
			}
		})
	});
	
	//선택된 이미지 에디터에 추가
	uploaderConfirmBtnRef.click(function() {
		var tabId = uploaderTabRef.find(".active").attr("id");
		var valid = true;
		
		uploaderFormRef.ajaxSubmit({
			url: contextPath + "/wiki/ajax/edit/upload.do",
			type: "POST",
			dataType: "text",
			beforeSubmit: function(data, frm, opt) {
                
				if( uploaderFileRef.val() != "" ){
					    var ext = uploaderFileRef.val().split('.').pop().toLowerCase();
					   
					    if(imageGubn.val() == "1"){
					    	if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
					    		alert('jpg,png,jpef,gif 파일만 업로드 할수 있습니다.');
						        valid = false;
						    }
					    }else if(imageGubn.val() == "2"){
					    	if(!($.inArray(ext, ['gif','png','jpg','jpeg']) != -1 || $.inArray(ext, ['xlsx','ppt','pptx','xls','doc','docx', 'txt']) != -1)) {
                                alert('jpg,png,jpef,gif 및 문서 파일만 업로드 할수 있습니다.');
						        valid = false;
						    }
					    }
			     }else{
			    	 valid = false;
			     }
				return valid;
			},
			success: function(result) {
				if(imageGubn.val() == "1"){
					var imgTagBoxRef = $('<div><img></div>');
					imgTagBoxRef.find("img").attr("src", result);
					CKEDITOR.instances.editor.insertHtml(imgTagBoxRef.html());
				}else{
					
					var arr=uploaderFileRef.val().split("\\");
					
					$("#fileList").append("<div class='col-lg-11 col-xs-10'><a href='"+ result + "'>" + arr[arr.length-1] + "</a></div>");
				}
          	},
          	error: function(err) {
          		console.error(err);
          		uploaderModalRef.modal("hide");
          	},
          	complete: function() {
          		uploaderModalRef.modal("hide");
          	}
		});

	});
}

function uploaderModalReset() {
	uploaderUrlRef.val("http://");
	uploaderFileRef.val("");
};

function loadData(seq) {
	$.ajax({
	  url: contextPath + "/wiki/ajax/edit/select.do",
	  type: "POST",
	  data: {
		  seq: seq
	  },
	  success: function(data) {
	      titleInputRef.val(data.TITLE);
	      workCodeComboRef.val(data.WORK_CODE);
	      CKEDITOR.instances.editor.insertHtml(data.CONTENTS_H);
	     
	      $("#fileList").append("<div id='fileDivList'>");
	      $("#fileDivList").append("<br>");
	      $.each(data.FILE_LIST, function(idx, value){
	    	  
	    	  var url = encodeURI(value.PATH + "/" + value.FILEN_NAME);
	    	  
	    	  //$("#fileList").append("<div class='col-lg-11 col-xs-10'><a href='"+ value.PATH + "/" + value.FILEN_NAME + "'>" + value.FILEN_NAME + "</a></div>");
	    	  $("#fileDivList").append( idx+1 +". <a href='" + contextPath + "/wiki/ajax/detail/callDownload.do?sequence" + "=" + seq + "&fileName=" +  encodeURIComponent(value.FILEN_NAME) +"'>" + value.FILEN_NAME + "</a><br>");
	    	  //$("#fileDivList").append( idx+1 +". <a href='" + contextPath + "/wiki/ajax/detail/callDownload.do?sequence" + "=" + seq + "&fileName=" +  value.FILEN_NAME +"'>" + value.FILEN_NAME + "</a><br>");
	      })
	      
	      $("#fileList").append("</div>");
	      
	  },
	  error: function(err) {
	  }
	});
};

function selectSequence() {
	$.ajax({
	  url: contextPath + "/wiki/ajax/detail/selectSequence.do",
	  type: "POST",
	  success: function(data) {
		  newSeq = data;
		  seq.val(newSeq);
	  },
	  error: function(err) {
		  alert("시퀀스 생성 중 오류 발생!")
	  }
	});
};


