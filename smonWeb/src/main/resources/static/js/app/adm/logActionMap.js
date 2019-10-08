var g_setting;
var cur_tab = "reg";

var URL
var actionGubunCd = "R";
var actionName;
var personalYn = "Y";

function setPageVO(curPage, colid, sorting, url) {
	var ret = {
			      url		: url
				, curPage	: curPage
				, colid		: colid
				, sorting 	: sorting
				, pageScale : 10
			  };
	
	if(!ret.curPage) {
		ret.curPage = "1";
	}
	if(!ret.colid) {
		ret.colid = "url"
	}
	if(!ret.sorting) {
		ret.sorting = "ASC"
	}
	if(!ret.url) {
		ret.url = "%"
	}
	return ret;
}

function getUrlStatistics(curPage, colid, sorting, url) {
	var PAGESCALE = 10;


	var pageVO = setPageVO(curPage, colid, sorting, url);

	var formURL = contextPath + "/adm/getURLStatistics.do";

	$.ajax({
		url : formURL,
		type : "GET",
		data : pageVO,
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
				
				var v_result = result.data;
				
				var v_pageVO = result.pageVO;
				var v_paramCondition = result.paramCondition;
				
				pageDiv(result); // 페이지 처리
				
				var v_str = "";
				
				$.each(v_result, function (index, data) {

					v_str += '<tr>';
					v_str += '<td class="text-left">' + data.url + '</td>';
					v_str += '<td class="text-center">' + data.personalYN + '</td>';
					v_str += '<td class="text-center">' + data.avgExecTime + '</td>';
					v_str += '<td class="text-center">' + data.recExecTime + '</td>';
					v_str += '<td class="text-center">' + data.reqCnt + '</td>';
					v_str += '</tr>';
				});
				
				$("#dataTable #table_tbody").html(v_str);
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




function init(urls) {
	console.log(urls);
	
	fn_zTreeInit();
	var nodes = [];
	
	for(var i=0; i<urls.length; i++) {
		var node = {};
		var children = [];
		
		var name = urls[i].title;
		node.name = name;
		children.push({"name": urls[i].content, "urlInfo": urls[i].url, "modId": urls[i].modId});
		try {
			while(urls[++i].title == name) {
				children.push({"name": urls[i].content, "urlInfo": urls[i].url,  "modId": urls[i].modId});
			}
		} catch(e) {}
		node.children = children;
		nodes.push(node);

		i--;
	}
	$.fn.zTree.init($("#treeDemo"), g_setting, nodes);
	
}

function setTreeFontColor() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	console.log(zTree);
	nodes = zTree.getNodes();

	for(var i=0; i<nodes.length; i++) {
		var isEmpty = false;

		var children = nodes[i].children;
		
		for(var j=0; j<children.length; j++) {
			console.log(children[j].url);
			var curNode = nodes = zTree.getNodeByParam("url", children[j].url, null);
			zTree.setting.view.fontCss = {};

			if(!children[j].modId) {
				isEmpty = true;
				zTree.setting.view.fontCss["color"] = "#d9534f";
				zTree.updateNode(curNode);

				console.log(curNode);
				
			}
		}	
		console.log(nodes[i]);
	}
}

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
		if(treeNode.level != 0) {
			url = treeNode.urlInfo;
			getURLInfoDetail(url);
		}
	}
}

function getURLInfoDetail(url) {
	$.ajax({
		url : contextPath + "/adm/getURLInfo.do",
		type : "GET",
		data : {"URL": url},
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
			if(result.chkResult == "OK") {
				setDetail(url, result.data);
			}
			
			$(".panel-body .content-container").css("display", "block");
			$.unblockUI();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$.unblockUI();
		}
	});
}

function setDetail(url, data) {
	$("#title-url").text(url);
	$("#action-name").val(data.actionName);
	$("#action-gubun").val(data.actionGubunCd);
	$("#personal-yn").val(data.personalYN);
}

function setURLInfoDetail() {
	actionName = $("#action-name").val();
	$.ajax({
		url : contextPath + "/adm/setURLInfo.do",
		type : "POST",
		data : {
				"URL": url,
				"actionName": actionName,
				"actionGubunCd": actionGubunCd,
				"personalYN": personalYn
			   },
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
			if(result.chkResult != "OK") {
				alert("오류발생");
			}
			$(".panel-body .content-container").css("display", "block");
			$.unblockUI();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$.unblockUI();
		}
	});
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
		html += '<li class="page-item"><a class="page-link" href="javascript:getUrlStatistics(1,\'' + p_paramCondition.colid + '\',\'' + p_paramCondition.sorting + ',\'' + p_paramCondition.url + '\')">처음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">처음</a></li>';
	}
	html += '<!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->';
	if (p_obj.curBlock > 1) {
		html += '<li class="page-item"><a class="page-link" href="javascript:getUrlStatistics(\'' + p_paramCondition.prevPage + '\',\'' + p_paramCondition.colid + '\',\'' + p_paramCondition.sorting + '\',\'' + p_paramCondition.url + '\')">이전</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="#">이전</a></li>';
	}
	html += '<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->'
	
	for (var idx=p_obj.blockBegin;idx<=p_obj.blockEnd;idx++) {
		if (p_obj.curPage == idx) {
			html += '<!-- **현재페이지이면 하이퍼링크 제거 -->';
			html += '<li class="page-item"><a class="page-link" style="background-color:#EAEAEA;"href="#"><span style="color:red;">' + idx + '</span></a></li>';
		} else {
			html += '<li class="page-item"><a class="page-link" href="javascript:getUrlStatistics(' + idx + ',\'' + p_paramCondition.colid + '\',\'' + p_paramCondition.sorting + '\',\'' + p_paramCondition.url + '\')">' + idx + '</a></li>';
		}
	}

	html += '<!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:getUrlStatistics(' + p_paramCondition.nextPage + ',\'' + p_paramCondition.colid + '\',\'' + p_paramCondition.sorting + '\',\'' + p_paramCondition.url + '\')">다음</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:getUrlStatistics(' + p_paramCondition.nextPage + ',\'' + p_paramCondition.colid + '\',\'' + p_paramCondition.sorting + '\',\'' + p_paramCondition.url + '\')">다음</a></li>';
	}
	
	html += '<!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->';
	
	if (p_obj.curBlock <= p_obj.totBlock) {
		html += '<li class="page-item"><a class="page-link" href="javascript:getUrlStatistics(' + p_paramCondition.totPage + ',\'' + p_paramCondition.colid + '\',\'' + p_paramCondition.sorting + '\',\'' + p_paramCondition.url + '\')">끝</a></li>';
	} else {
		html += '<li class="page-item disabled"><a class="page-link" href="javascript:getUrlStatistics(' + p_paramCondition.totPage + ',\'' + p_paramCondition.colid + '\',\'' + p_paramCondition.sorting + '\',\'' + p_paramCondition.url + '\')">끝</a></li>';
	}
	
	html += '</ul>';
	html += '</nav>';
	
	$("#div_page").html(html);
	$("#div_count").html('<span>Showing ' + p_paramCondition.start + ' to ' + p_paramCondition.end + ' of ' + p_count + ' entries</span>');
}


$(".selectpicker").on("change", function() {
	if(this.id == "action-gubun") {
		actionGubunCd = this.value;
	} else if (this.id == "personal-yn") {
		personalYn = this.value;
	}
});

$(".btn#save-detail").on("click", function() {
	setURLInfoDetail();
});

$(".btn#search").on("click", function() {
	searchUrl();
});

function searchUrl() {
	var url = $("#url-name").val();
	getUrlStatistics(1, "url", "ASC", url);
}

$("#url-name").on("keyup", function(e) {
	if(e.keyCode == 13) {
		searchUrl();
	}
});


function getColnameByColidx(v_colidx) {
	switch (v_colidx) {
		case "0" :
			g_colid = "url";
			break;
		case "1" :
			g_colid = "personal_yn";
			break;
		case "2" :
			g_colid = "avg_time";
			break;				
		case "3" :
			g_colid = "rec_time";
			break;
		case "4" :
			g_colid = "cnt";
			break;	
	}
	return g_colid;
}

$("#dataTable thead tr th").on("click", function() {
	$("#dataTable thead tr th").not(this).removeClass("sorting");
	$("#dataTable thead tr th").not(this).removeClass("sorting_asc");
	$("#dataTable thead tr th").not(this).removeClass("sorting_desc");
	$("#dataTable thead tr th").not(this).addClass("sorting"); 
	
	var $this = $(this);
	
	var colid = getColnameByColidx($this.attr("colidx"));
	var sorting;
	
	if($this.hasClass("sorting")) {
		$this.removeClass("sorting");
		$this.addClass("sorting_asc");
		sorting = "ASC";
	} else if ($this.hasClass("sorting_desc")) {
		$this.removeClass("sorting_desc");
		$this.addClass("sorting_asc");
		sorting = "ASC";
	} else if($this.hasClass("sorting_asc")) {
		$this.removeClass("sorting_asc");
		$this.addClass("sorting_desc");
		sorting = "DESC";
	} else {
		$this.addClass("sorting_asc");
		sorting = "ASC";
	}
	
	getUrlStatistics(1, colid, sorting, "%");
});

$('.nav-tabs a').click(function(){
    $(this).tab('show');
    if(cur_tab == "reg") {
    	cur_tab = "stat";
    	
        getUrlStatistics(1, "url", "ASC", "%");
    } else {
    	cur_tab = "reg";
    }
});



