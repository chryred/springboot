
$(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajaxSetup({
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		}
	})
});




function fn_selectTran(requstUrl, syncFlag, paramMap){
    
	var returnValue = "";
    
    $.ajax({
        url: requstUrl,
        type:"post",
        dataType:"json", 
        async: syncFlag, 
        data : paramMap,
        success : function(data) {
        	returnValue = data;
        },
        error : function(xhr, status, error) {
            alert("조회 시 에러\n" + xhr + "\n" + status + "\n" + error);
        }
    }); 
    //fn_com_layer_close('layer');
    return returnValue;
} 

var grigHeight = "500";
var menuWidth = $("#page-wrapper").width;
var dataPath = "views/mtr/data/";
var rootPath = "";


function fn_search(gridId, url) {      
    
    $("#" + gridId).clearGridData();  // 이전 데이터 삭제
    
    $("#" + gridId).setGridParam({ async: true, url:url + ".do", postData: {},datatype:"json" }).trigger("reloadGrid");
    
}


function fn_com_layer_open(el){
    
    var temp = $('.' + el);
    
    temp.fadeIn();

    $('.nav-layer').fadeIn();

}

function fn_com_layer_close(el){

    var temp = $('.' + el);

    temp.fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 

}

function fn_com_setMessage(info){
    $("#info").empty();
    $("#info").append(info);
}


function popupOpen(){
    var popUrl = "views/mtr/common/commonMessage.jsp";   //팝업창에 출력될 페이지 URL
    fn_showModalDialog(popUrl, 700, 700, url, false);
}

function fn_getDate(data){
    if(data == '' || data == null){
        return new Date('12/31/9999');
    }
    data = data.replace(/-/g, '/');

    console.log(data);
    return new Date(data);
}

function fn_showModalDialog(url, width, height, msgGubn, closeCallback) {
    
    var modalDiv,
        dialogPrefix = window.showModalDialog ? 'dialog' : '',
        unit = 'px',
        maximized = width === true || height === true,
        w = width || 600,
        h = height || 500,
        border = 5,
        taskbar = 40, // windows taskbar
        header = 20,
        x,
        y;

    if (maximized) {
        x = 0;
        y = 0;
        w = screen.width;
        h = screen.height;
    } else {
        x = window.screenX + (screen.width / 2) - (w / 2) - (border * 2);
        y = window.screenY + (screen.height / 2) - (h / 2) - taskbar - border;
    }

    var features = [
            'toolbar=no',
            'location=no',
            'directories=no',
            'status=no',
            'menubar=no',
            'scrollbars=no',
            'resizable=no',
            'copyhistory=no',
            'center=yes',
            dialogPrefix + 'width=' + w + unit,
            dialogPrefix + 'height=' + h + unit,
            dialogPrefix + 'top=' + y + unit,
            dialogPrefix + 'left=' + x + unit
        ],
        showModal = function (context) {
            if (context) {
                modalDiv = context.document.createElement('div');
                modalDiv.style.cssText = 'top:0;right:0;bottom:0;left:0;position:absolute;z-index:50000;';
                modalDiv.onclick = function () {
                    if (context.focus) {
                        context.focus();
                    }
                    return false;
                }
                window.top.document.body.appendChild(modalDiv);
            }
        },
        removeModal = function () {
            if (modalDiv) {
                modalDiv.onclick = null;
                modalDiv.parentNode.removeChild(modalDiv);
                modalDiv = null;
            }
        };

    // IE
    if (window.showModalDialog) {
        window.showModalDialog(url+"?msgGubn=" +msgGubn, null, features.join(';') + ';');

        if (closeCallback) {
            closeCallback();
        }
    // Other browsers
    } else {
        var win = window.open(url+"?msgGubn=" +msgGubn, "", features.join(','));
        if (maximized) {
            win.moveTo(0, 0);
        }

        // When charging the window.
        var onLoadFn = function () {
                showModal(this);
            },
            // When you close the window.
            unLoadFn = function () {
                window.clearInterval(interval);
                if (closeCallback) {
                    closeCallback();
                }
                removeModal();
            },
            // When you refresh the context that caught the window.
            beforeUnloadAndCloseFn = function () {
                try {
                    unLoadFn();
                }
                finally {
                    win.close();
                }
            };

        if (win) {
            // Create a task to check if the window was closed.
            var interval = window.setInterval(function () {
                try {
                    if (win == null || win.closed) {
                        unLoadFn();
                    }
                } catch (e) { }
            }, 500);

            if (win.addEventListener) {
                win.addEventListener('load', onLoadFn, false);
            } else {
                win.attachEvent('load', onLoadFn);
            }

            window.addEventListener('beforeunload', beforeUnloadAndCloseFn, false);
        }
    }
}


function fn_loadSystemCombo(comboID) {
    
    var paramMap = new Object();
    
    paramMap.team = "003";
    
    var data = fn_selectTran('searchSystemCombo.do', false, paramMap);

    var divHtml = '<SELECT id="' + comboID + '_COMBO" name="' + comboID + '" class="form-control">';

    var index = 0;
    
    $.each(data, function() {
        
        if(index == 0){
            system = this.CODE;
        }
        divHtml += '<option value="'+this.CODE+'">' + this.NAME
                + '</option>';

        index++;
    });

    divHtml += '</SELECT>';

    $("#" + comboID + "_COMBO").remove();

    $("#comboSystem").append(divHtml);

}

function fn_selectStatusOfInterlocking(){
	
	initInterlockingTable(fn_selectTran(contextPath + '/selectStatusOfInterlocking.do', false, ''));
	
    modal = $('#interlockingModal').modal({
        backdrop : true,
        keyboard : true
    });
}


function initInterlockingTable(data) {
	
	//console.log(data);
    $('#interlockingDataTables').dataTable().fnClearTable();
    $('#interlockingDataTables').dataTable().fnDestroy();

    $('#interlockingDataTables').DataTable({
        data : data,
        columns : [ {data : 'ROLE_CODE', 
        	         sClass : "left",
        	         render:function(data,type,full,meta){
        	                    if(data == "002"){
        	                        return "운영정보";
        	                    }else if(data == "003"){
        	                        return "CRM";
        	                    }else{
        	                    	return "사이먼";
        	                    }
        	                }
        	        }, 
                    {data : 'SYSTEM_NAME', sClass : "left"}, 
                    {data : 'DB_SID', sClass : "left"}, 
                    {data : 'INTERLOCKING_STATUS',
                     sClass : "left",
                     render:function(data,type,full,meta){
 	                    if(data == "0"){
 	                        return "<font color='green'><b>연동 중</b></font>";
 	                    }else if(data == "2"){
 	                        return "<font color='red'><b>미 연동</b></font>";
 	                    }else{
 	                    	return "<font color='orange'><b>연결 끊김(" + data + ")</b></font>";
 	                    }
 	                 }
                    }
                    ],
        ordering : false,
        paging : false,
        info : false
    });
}