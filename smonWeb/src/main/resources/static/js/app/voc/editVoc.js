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
	uploaderFileRef = $("#upload_file");
	uploaderConfirmBtnRef = $("button#uploader_confirm");
	
	saveBtnRef = $("#save");
		
	CKEDITOR.replace("editor");
	
	CKEDITOR.on("instanceReady", function() {
		$(".cke_toolbox").append(uploaderOpenBtnRef);
		CKEDITOR.instances.editor.resize('100%', $(window).height() - 380);
		$(".container").show();
		
		/*
		if(global.isNew == 0) {
			loadData(global.seq);
		}
		*/
		
	});
	
	staticBindEvent();
	
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	
	$(window).resize(function() {
		var tableWidth = $(".table-wrapper").css("width");
		/*
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", tableWidth);
		$(".ui-jqgrid-hdiv").css("width", tableWidth);
		$(".ui-jqgrid-bdiv").css("width", tableWidth);
		
		$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
		*/
	});
	
	//이미지 업로더 모달 열기
	uploaderOpenBtnRef.click(function() {
	    uploaderModalReset();
	    uploaderModalRef.modal("show");
	});
	
	saveBtnRef.click(function() {
		$.ajax({
			url: contextPath + "/wiki/ajax/edit/save.do",
			type: "POST",
			dataType: "text",
			data: {
				isNew: global.isNew == "null" ? 1 : global.isNew,
				title: titleInputRef.val(),
				contents: CKEDITOR.instances.editor.getData(),
				seq: global.seq
			},
			success: function(result) {
				alert("저장되었습니다.");
				history.back();
			},
			error: function() {
				alert("실패했습니다.");
			}
		})
	});
	
	//선택된 이미지 에디터에 추가
	uploaderConfirmBtnRef.click(function() {
		var tabId = uploaderTabRef.find(".active").attr("id");
		if(tabId == 'uploadWithUrl') {
			var valid = true;
			valid = valid && uploaderUrlRef.val() != "";
	
			if(valid) {
				var imgTagBoxRef = $('<div><img></div>');
				imgTagBoxRef.find("img").attr("src", uploaderUrlRef.val());
				CKEDITOR.instances.editor.insertHtml(imgTagBoxRef.html());
				uploaderModalRef.modal("hide");
			}
		} else {
			uploaderFormRef.ajaxSubmit({
				url: contextPath + "/wiki/ajax/edit/upload.do",
				type: "POST",
				dataType: "text",
				beforeSubmit: function(data, frm, opt) {
					var valid = true;
					valid = valid && uploaderFileRef.val() != "";
					return valid;
				},
				success: function(result) {
					var imgTagBoxRef = $('<div><img></div>');
					imgTagBoxRef.find("img").attr("src", result);
					CKEDITOR.instances.editor.insertHtml(imgTagBoxRef.html());
              	},
              	error: function(err) {
              		console.error(err);
              	},
              	complete: function() {
              		uploaderModalRef.modal("hide");
              	}
			});
		}
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
	      CKEDITOR.instances.editor.insertHtml(data.CONTENTS);
	      console.log(data.CONTENTS);
	  },
	  error: function(err) {
	  }
	});
};
