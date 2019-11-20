$(document).ready(function() {
	init();
});

/*
 * private decalre
 */
var CONTENTS_HEIGHT_MINUS_PIXEL = 300;
var CONTENTS_HEIGHT_MINUS_PIXEL2 = 435;

var tableTemplateHtml = null;
var tableRef1 = null;

var searchBtnRef = null;
var newBtnRef	 = null;
var saveBtnRef 	 = null;
/*var cancelBtnRef = null;*/
var deleteBtnRef = null;

var dbNameComboboxRef = null;
var ownerComboboxRef = null;
var tableNameRef = null;

var inputOwnerRef 		= null;
var inputTableNameRef 	= null;
var inputSQLRef 		= null;
var inputINFORef 		= null;

var flag                =  "I"; //insert 인지 update인지 판단 flag

var SELECTED_TABLE_ROWID = null;
	
/*
 * 초기화
 */
function init () {
	tableRef1 = $("#dataTable1");
	
	searchBtnRef = $(".search-btn");
	newBtnRef	 = $(".new-btn");
	saveBtnRef   = $(".save-btn");
	/*cancelBtnRef = $(".cancel-btn");*/
	deleteBtnRef = $(".delete-btn");
	
	dbNameComboboxRef = $("#dbName");
	customDbNameComboboxRef = $("#customDbNameCombo");
	//customOwnerComboboxRef = $("#customOwnerCombo");
	ownerComboboxRef = $("#owner");
	tableNameRef = $("#tableName");
	
	inputOwnerRef 		= $("#inputOwner");
	inputTableNameRef 	= $("#inputTableName");
	inputSQLRef 		= $("#inputSQL");
	inputINFORef 		= $("#inputINFO");
	inputStndrdNmbrRef	= $("#inputStndrdNmbr");
	inputStndrdCycleRef	= $("#inputStndrdCycle");
	
	staticBindEvent();
	initComboDBName();
	initComboOwner("condition");
	//initComboTbName();
	initTable();
	
	
	initCustomCombo();
	
	$( "#customDbNameCombo" ).combobox();
	//$( "#customOwnerCombo" ).combobox();
	
	
}

function initCustomCombo(){
	 $.widget( "custom.combobox", {
	      _create: function() {
	        this.wrapper = $( "<span>" )
	          .addClass( "custom-combobox" )
	          .insertAfter( this.element );
	 
	        this.element.hide();
	        this._createAutocomplete();
	        this._createShowAllButton();
            
	      },
	 
	      _createAutocomplete: function() {
	        var selected = this.element.children( ":selected" ),
	          value = selected.val() ? selected.text() : "";
	 
	        this.input = $( "<input id='customInput'>" )
	          .appendTo( this.wrapper )
	          .val( value )
	          .attr( "title", "" )
	          .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
	          .autocomplete({
	            delay: 0,
	            minLength: 0,
	            source: $.proxy( this, "_source" )
	          })
	          .tooltip({
	            classes: {
	              "ui-tooltip": "ui-state-highlight"
	            }
	          });
	 
	        this._on( this.input, {
	          autocompleteselect: function( event, ui ) {
	            ui.item.option.selected = true;
	            this._trigger( "select", event, {
	              item: ui.item.option
	            });
	          },
	 
	          autocompletechange: "_removeIfInvalid"
	        });
	      },
	 
	      _createShowAllButton: function() {
	        var input = this.input,
	          wasOpen = false;
	 
	        $( "<a id='autoA'>" )
	          .attr( "tabIndex", -1 )
	          .attr( "title", "전체 보기" )
	          .tooltip()
	          .appendTo( this.wrapper )
	          .button({
	            icons: {
	              primary: "ui-icon-triangle-1-s"
	            },
	            text: false
	          })
	          .removeClass( "ui-corner-all" )
	          .addClass( "custom-combobox-toggle ui-corner-right" )
	          .on( "mousedown", function() {
	            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
	          })
	          .on( "click", function() {
	            input.trigger( "focus" );
	 
	            // Close if already visible
	            if ( wasOpen ) {
	              return;
	            }
	 
	            // Pass empty string as value to search for, displaying all results
	            input.autocomplete( "search", "" );
	          });
	      },
	 
	      _source: function( request, response ) {
	        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
	        response( this.element.children( "option" ).map(function() {
	          var text = $( this ).text();
	          if ( this.value && ( !request.term || matcher.test(text) ) )
	            return {
	              label: text,
	              value: text,
	              option: this
	            };
	        }) );
	      },
	 
	      _removeIfInvalid: function( event, ui ) {
	 
	        // Selected an item, nothing to do
	        if ( ui.item ) {
	          return;
	        }
	 
	        // Search for a match (case-insensitive)
	        var value = this.input.val(),
	          valueLowerCase = value.toLowerCase(),
	          valid = false;
	        this.element.children( "option" ).each(function() {
	          if ( $( this ).text().toLowerCase() === valueLowerCase ) {
	            this.selected = valid = true;
	            return false;
	          }
	        });
	 
	        // Found a match, nothing to do
	        if ( valid ) {
	          return;
	        }
	 
	        // Remove invalid value
	        this.input
	          .val( "" )
	          .attr( "title", "[" + value + "]\n일치하는 데이터가 없습니다." )
	          .tooltip( "open" );
	        this.element.val( "" );
	        this._delay(function() {
	          this.input.tooltip( "close" ).attr( "title", "" );
	        }, 2500 );
	        this.input.autocomplete( "instance" ).term = "";
	      },
	 
	      _destroy: function() {
	        this.wrapper.remove();
	        this.element.show();
	      }
	    });
}

/*
 * 정적DOM 이벤트 바인딩
 */
function staticBindEvent() {
	//조회버튼 클릭
	searchBtnRef.on("click", function() {
		searchTable();
		flag = "I";
	});
	
	//신규버튼 클릭
	newBtnRef.on("click", function(){
		
		$("#customInput").val("");
		$("#inputOwner").val("");
		$("#inputTableName").val("");
		$("#inputSQL").val("");
		$("#inputStndrdNmbr").val("");
		$("#inputStndrdCycle").val("");
		$("#inputINFO").val("");
		
		
		readOnlySet("F");
		
		flag = "I";
	});
	
	//저장버튼 클릭
	saveBtnRef.on("click", function() {
		btn_save(flag);
	});
	
	
	//삭제버튼 클릭
	deleteBtnRef.on("click", function() {
		btn_delete();
	});
	
	
	dbNameComboboxRef.on("change", function() {
		initComboOwner();
	});
	
	
	
	/*
	customDbNameComboboxRef.autocomplete({
	  
	  change: function( event, ui ) {initComboOwner("input");}
	});*/
	
	
	
	$(window).resize(function() {
		var tableWidth = $(".table-wrapper").css("width");
		$(".ui-jqgrid").css("width", tableWidth);
		$(".ui-jqgrid-view").css("width", tableWidth);
		$(".ui-jqgrid-hdiv").css("width", tableWidth);
		$(".ui-jqgrid-bdiv").css("width", tableWidth);
		
		$(".ui-jqgrid-bdiv").css("height", ($(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL) + "px");
	});
}

function readOnlySet(value){
	if(value == "T"){
		$("#customInput").attr("readOnly",true);
		$("#inputOwner").attr("readOnly",true);
		$("#inputTableName").attr("readOnly",true);
		$("#autoA").css("visibility", "hidden")

	}else if(value == "F"){
		$("#customInput").attr("readOnly",false);
		$("#inputOwner").attr("readOnly",false);
		$("#inputTableName").attr("readOnly",false);
		$("#autoA").css("visibility", "");
	}
}

/*
 * DB 명 콤보박스 옵션 초기화
 */
function initComboDBName() {
	$.ajax({
		url: contextPath + "/personalInfo/destroyManage/search/ajax/list/dbName.do",
		type: "POST",
		dataType: "json",
		success: function(data) {
			dbNameComboboxRef.html("");
			dbNameComboboxRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				dbNameComboboxRef.append('<option value="' + option.DB_NAME + '">' + option.DB_KOR_NAME + '</option>');
				customDbNameComboboxRef.append('<option value="' + option.DB_NAME + '">' + option.DB_NAME + '</option>');
			});
		},
		error: function() {
			alert("에러가 발생했습니다.");
		}
	});
}

/*
 * 소유자 콤보박스 옵션 초기화
 */
function initComboOwner(gubn) {
	console.log(gubn);
	$.ajax({
		url: contextPath + "/personalInfo/destroyManage/search/ajax/list/owner.do",
		type: "POST",
		dataType: "json",
		data: {
			dbName: dbNameComboboxRef.val()
		},
		success: function(data) {
			ownerComboboxRef.html("");
			ownerComboboxRef.append('<option value="">전체</option>');
			_.each(data, function(option, idx) {
				ownerComboboxRef.append('<option value="' + option.OWNER + '">' + option.OWNER + '</option>');
			});
			
		},
		error: function(err) {
			alert("에러가 발생했습니다.");
		}
	});
}

function inputData(params){
	
	$("#customInput").val(params.DB_NAME);
	inputOwnerRef.val(params.OWNER);
	$("#inputTableName").val(params.TABLE_NAME);
	$("#inputSQL").val(params.SQL);
	$("#inputStndrdNmbr").val(params.DSTRCTN_NMBR);
	$("#inputStndrdCycle").val(params.DSTRCTN_CYCL);
	$("#inputINFO").val(params.INFO);
	
	readOnlySet("T");
	
	flag = "U";
	
}

function do_check(index){
	
	if($("#customInput").val() == "" || $("#inputOwner").val() == "" || $("#inputTableName").val() == "" || $("#inputSQL").val() == ""){
		alert("비고를 제외한 항목은 반드시 입력해야 합니다.");
		return false;
	}
	
	/*switch(index){
		case 'I' :
			if($("#inputDBName").val() == "" || $("#inputOwner").val() == "" || $("#inputTableName").val() == "" || $("#inputSQL").val() == ""){
				alert("INFO를 제외한 항목은 반드시 입력해야 합니다.");
				return false;
			}
			
			break;
			
		case 'U' : 
			if($("#inputDBName").val() == "" || $("#inputOwner").val() == "" || $("#inputTableName").val() == "" || $("#inputSQL").val() == ""){
				alert("INFO를 제외한 항목은 반드시 입력해야 합니다.");
				return false;
			}
			
			break;
			
		case 'DELETE' :
			if($("#inputDBName").val() == "" || $("#inputOwner").val() == "" || $("#inputTableName").val() == "" || $("#inputSQL").val() == ""){
				alert("INFO를 제외한 항목은 반드시 입력해야 합니다.");
				return false;
			}
			
			break;
		default : break;
		
		
	}*/
	return true;
}


//Insert 또는 Update
function btn_save(flag){
	
	if (!do_check(flag)) return;
	
	
	$.ajax({
		url: contextPath + "/personalInfo/destroyManage/search/ajax/list/save.do",
		type: "POST",
		dataType: "json",
		data: {
			dbName: $("#customInput").val(),
			owner: inputOwnerRef.val(),
			tableName: inputTableNameRef.val(),
			sqlData : inputSQLRef.val(),
			info : inputINFORef.val(),
			flag : flag,
			stndrdNmbr : inputStndrdNmbrRef.val(),
			stndrdCycle : inputStndrdCycleRef.val()
		},
		success: function(data){
			searchTable();
			alert("성공하였습니다.");
			
		},
		error: function(err){
			alert("에러가 발생했습니다." + err);
		}
		
	});
	
}

//Delete
function btn_delete(){
	
	if (!do_check(flag)) return;
	
	$.ajax({
		url: contextPath + "/personalInfo/destroyManage/search/ajax/list/delete.do",
		type: "POST",
		dataType: "json",
		data: {
			dbName: $("#customInput").val(),
			owner: inputOwnerRef.val(),
			tableName: inputTableNameRef.val(),
			sqlData : inputSQLRef.val()
		},
		success: function(data){
			searchTable();
			alert("성공하였습니다.");
		},
		error: function(err){
			alert("에러가 발생했습니다." + err);
		}
		
	});
}

/*
 * 그리드 조회
 */
function searchTable () {
	var temp = null;
	
	$("#inputDBName").val("");
	$("#inputOwner").val("");
	$("#inputTableName").val("");
	$("#inputSQL").val("");
	$("#inputStndrdNmbr").val("");
	$("#inputStndrdCycle").val("");
	$("#inputINFO").val("");
	flag = "I";
	
	tableRef1.setGridParam({
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			tableName: tableNameRef.val() != "" ? tableNameRef.val() : null
		}
	}).trigger("reloadGrid");
}

/*
 * 그리드 초기화
 */
function initTable () {
	var temp = null;
	tableRef1.jqGrid({
		url: contextPath + "/personalInfo/destroyManage/search/ajax/list.do",
		datatype: "json",
		mtype: "POST",
		postData: {
			dbName: dbNameComboboxRef.val() != "" ? dbNameComboboxRef.val() : null,
			owner: ownerComboboxRef.val() != "" ? ownerComboboxRef.val() : null,
			tableName: tableNameRef.val() != "" ? tableNameRef.val() : null
		},
		colNames: [ "DB명", "소유자", "테이블명", "SQL", "파기기준", "", "", "비고" ],
		colModel : [{
			width: "100px",
			id: "DB_NAME",
			name: "DB_NAME",
			index: "DB_NAME"
		}, {
			width: "100px",
			id:"OWNER",
			name: "OWNER",
			index: "OWNER"
		}, {
			width: "150px",
			id:"TABLE_NAME",
			name: "TABLE_NAME",
			index: "TABLE_NAME"
		}, {
			width: "400px",
			id:"SQL",
			name: "SQL",
			index: "SQL",
			hidden : true
		}, {
			width: "100px",
			id:"DSTRCTN_STNDRD",
			name: "DSTRCTN_STNDRD",
			index: "DSTRCTN_STNDRD",
			align: 'center'
		}, {
			width: "200px",
			id:"DSTRCTN_NMBR",
			name: "DSTRCTN_NMBR",
			index: "DSTRCTN_NMBR",
			hidden: true
		}, {
			width: "200px",
			id:"DSTRCTN_CYCL",
			name: "DSTRCTN_CYCL",
			index: "DSTRCTN_CYCL",
			hidden: true
		}, {
			width: "300px",
			id:"INFO",
			name: "INFO",
			index: "INFO"
		}],
		autowidth: false,
		shrinkToFit: false,
		width: $('.table-wrapper').width(),
		height: $(window).height() - CONTENTS_HEIGHT_MINUS_PIXEL,
		rowNum:50,
        rowList:[25,50,100],
        pager : '#pager',
		loadonce: false,
		multiselect: false,
		onSelectRow: function(rowid, status, e) {
			SELECTED_TABLE_ROWID = rowid;
			inputData(tableRef1.getRowData(rowid));
		}
	});

}

