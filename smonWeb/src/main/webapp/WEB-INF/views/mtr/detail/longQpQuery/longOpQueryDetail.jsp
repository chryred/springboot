<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!-- link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" -->
		<link rel="stylesheet" href="//fonts.googleapis.com/earlyaccess/nanumgothic.css">
		<script type="text/javascript">
			var longDataTable;

			var commMap = {'last_load_ymd':'${long_map.last_load_ymd}', 'db_name':'${long_map.db_name}', 'sql_id':'${long_map.sql_id}', 'last_load_time':'${long_map.last_load_time}', 'search_type':'1'};
			var dayOptions = {
			        //data : fn_selectTran(contextPath + '/selectLongQueryDetailList.do', false, commMap),
			        "ajax": {
			                     url : "selectLongQueryDetailList.do",
			                     type : 'POST',
			                     dataSrc: function(json) {
			                     	return json;
			                     },
			                     data : commMap
			                 },
			        columnDefs : [
									  {"targets" : [0], "width" : "10%", "searchable": false},
					                  {"targets" : [1], "visible" : false, "searchable": false},
					                  {"targets" : [2], "visible" : false, "searchable": false},
					                  {"targets" : [3], "width" : "13%"},
					                  {"targets" : [4], "visible" : false, "searchable": false},
					                  {"targets" : [5], "width" : "20", "searchable": false},
					                  {"targets" : [6], "width" : "20%", "searchable": false},
					                  {"targets" : [7], "width" : "20%", "searchable": false},
					                  {"targets" : [8], "width" : "17%", "searchable": false},
					                ],
			        columns : [
			                      {data : 'RNUM', sClass : "center"},
					                      {data : 'LAST_LOAD_YMD', sClass : "left hidden"},
			                      {data : 'DB_NAME', sClass : "left hidden"},
			                      {data : 'SQL_ID', sClass : "center"},
			                      {data : 'LAST_LOAD_TIME', sClass : "left hidden"},
			                      {data : 'ELAPSED_TIME', sClass : "center"},
			                      {data : 'EXEC_DT', sClass : "center",
			                       render:function(data, type, full, meta){
			 	                   	return data.substring(0, 4) + "/" + data.substring(4, 6) + "/" + data.substring(6, 8) + " "
			 	                   	      + data.substring(8, 10) + ":" + data.substring(10, 12) + ":" + data.substring(12, 14);
			 	                    }
			                      },
			                      {data : 'EXECUTIONS', sClass : "center"},
			                      {data : 'ROWS_PROCESSED', sClass : "center"}
			                    ],
			        paging: true,
			        serverSide: true,
					info: false,
					lengthMenu: [[10, 20, 30], ['10', '20', '30']],
					ordering: false,
					"destroy": true,
					"iDisplayLength": 10,
					//autoWidth: true,
					 "language" : {
			                                 "lengthMenu" : "건수&nbsp;: _MENU_ ",
			                                 "search" : "SQL ID 검색:",
			                                 "zeroRecords" : "조회내역이 없습니다",
			                                 "info": "<b>총 _TOTAL_건</b>",
			                                 /* "aria" : {
			                                     "sortAscending" : ": activate to sort column ascending",
			                                     "sortDescending" : ": activate to sort column descending"
			                                 }, */
			                                 "paginate" : {
			                                     "first" : "맨 처음",
			                                     "last" : "맨 마지막",
			                                     "next" : ">",
			                                     "previous" : "<"
			                                 },
			                             },
					"drawCallback" : function(settings) {

					}
			    };

			var monthOptions =  {
			        //data : fn_selectTran(contextPath + '/selectLongQueryDetailList.do', false, paramMap),
			        //{"LAST_LOAD_YMD":"201803","RNUM":1,"SQL_ID":"59y0wj4dm82w7","EXECUTIONS":6,"DB_NAME":"SIC","ELAPSED_TIME":32.81,"ROWS_PROCESSED":3267.67}]
			        "ajax": {
			                     url : "selectLongQueryDetailMonList.do",
			                     type : 'POST',
			                     dataSrc: function(json) {
			                     	return json;
			                     },
			                     data :  commMap
			                 },
			        columnDefs : [
									  {"targets" : [0], "width" : "10%", "searchable": false},
					                  {"targets" : [1], "width" : "13%",
					                       render:function(data, type, full, meta){
						 	                   	return data.substring(0, 4) + "/" + data.substring(4, 6);
					 	                    }},
					                  {"targets" : [2], "visible" : false, "searchable": false},
					                  {"targets" : [3], "width" : "20", "searchable": false},
					                  {"targets" : [4], "width" : "20%", "searchable": false},
					                  {"targets" : [5], "width" : "20%", "searchable": false},
					                  {"targets" : [6], "width" : "17%", "searchable": false},
					                ],
			        columns : [
			                      {data : 'RNUM', sClass : "center"},
					              {data : 'LAST_LOAD_YMD', sClass : "center"},
			                      {data : 'DB_NAME', sClass : "left hidden"},
			                      {data : 'SQL_ID', sClass : "center"},
			                      {data : 'ELAPSED_TIME', sClass : "center"},
			                      {data : 'EXECUTIONS', sClass : "center"},
			                      {data : 'ROWS_PROCESSED', sClass : "center"}
			                    ],
			        paging: true,
			        serverSide: true,
					info: false,
					lengthMenu: [[10, 20, 30], ['10', '20', '30']],
					ordering: false,
					"destroy": true,
					"iDisplayLength": 10,
					//autoWidth: true,
					 "language" : {
			                                 "lengthMenu" : "건수&nbsp;: _MENU_ ",
			                                 "search" : "SQL ID 검색:",
			                                 "zeroRecords" : "조회내역이 없습니다",
			                                 "info": "<b>총 _TOTAL_건</b>",
			                                 /* "aria" : {
			                                     "sortAscending" : ": activate to sort column ascending",
			                                     "sortDescending" : ": activate to sort column descending"
			                                 }, */
			                                 "paginate" : {
			                                     "first" : "맨 처음",
			                                     "last" : "맨 마지막",
			                                     "next" : ">",
			                                     "previous" : "<"
			                                 },
			                             },
					"drawCallback" : function(settings) {

					}
			    };

			$(document).ready(function() {
				interLongQueryDataTables(dayOptions);
				$('#longQueryDetails').hide();
				// 이벤트 설정
				initailizeInterEvent();

				$('#interLongQueryDataTables tbody').on( 'dblclick', 'td', function () {
					var rowData = longDataTable.row( $(this).parent() ).data();
					var cellIdx = $(this).parent().children().index($(this));

					// sql_id의 경우는 더블클릭 허용
					if(cellIdx == 1) {
						return false;
					}

					$(this).parent().toggleClass('selected');

				  	$('#interLongQueryDataTables_wrapper').fadeOut(500);
				  	$('#longQueryDetails').fadeIn();
				    var data = fn_selectTran('selectLongQueryDetailListScnd.do', false, rowData);
				    //console.log(data[0]);
				    $('#longQueryDetails tbody tr').remove();
				    $('#longQueryDetails tbody').append(
					    '<tr>' +
						'	<td class="center">BUFFER_GETS</td>' +
						'	<td>' + data[0].BUFFER_GETS + '</td>' +
						'</tr>' +
						'<tr>' +
						'	<td class="center">DISK_READS</td>' +
						'	<td>' + data[0].DISK_READS + '</td>' +
						'</tr>' +
						'<tr>' +
						'	<td class="center">' + ($('#search_type').val() == '1' ? '실행 횟수' : '전체 실행횟수' ) + '</td>' +
						'	<td>' + rowData.EXECUTIONS +'</td>' +
						'</tr>' +
						'<tr>' +
						'	<td class="center">' + ($('#search_type').val() == '1' ? '실행 시간' : '평균 실행시간' ) + '</td>' +
						'	<td>' + rowData.ELAPSED_TIME + '</td>' +
						'</tr>' +
						'<tr>' +
						'	<td class="center">실행 쿼리</td>' +
						'	<td style="height:300px">' +
						'		<textarea style="width:100%;height:100%">' + data[0].SQL_FULLTEXT + '</textarea>' +
						'	</td>' +
						'</tr>'
						//data[0].SQL_FULLTEXT.replace(/\t/gi, '&nbsp;').replace(/\n/gi, '<br/>')
					);
				});


				$('#long_query_checkbox').on('change', function(e) {
					if($(this).is(":checked")) {
						setTimeout(
						  function() {
						  	$('#interLongQueryDataTables_wrapper').fadeIn(500);
					  		$('#longQueryDetails').fadeOut();
					  		$('#long_query_checkbox').prop("checked", false);
						  }, 500);
					}
				});
			});


			function interLongQueryDataTables(options) {
			    longDataTable = $('#interLongQueryDataTables').DataTable(options);
			}

			function dataTablesClear() {
				$('#interLongQueryDataTables').dataTable().fnDestroy();
				$('#interLongQueryDataTables').dataTable().fnClearTable();
			}

			function initailizeInterEvent() {
				// 검색 이벤트 설정
				var oSearch = $('#interLongQueryDataTables_filter input[type=search]');
				oSearch.unbind();
				oSearch.on('keyup', function(e){
					if(e.keyCode != 13) {
						return;
					}
					longDataTable.search($(this).val()).draw();
				});

				// select 이벤트 설정
				$('#search_type').remove();
				$('#interLongQueryDataTables_length label').append('<select id="search_type" class="form-control input-sm"><option value="1">일</option><option value="2">월</option></select>');
				// select 이벤트 설정
				$('#search_type').on('change', function(e){
			 		var selectedVal = $(this).val();

			 		commMap.search_type = selectedVal;
					// 일로 설정
					if(selectedVal == 1) {
						dataTablesClear();
						$('#interLongQueryDataTables thead tr th').remove();
						$('#interLongQueryDataTables thead tr').append("<th>순번</th><th>최종 실행일자(YMD)</th><th>DB명</th><th>SQL ID</th><th>최종 실행시간(HMS)</th>	<th>실행 시간(sec)</th><th>최종 실행일자</th>	<th>실행 횟수</th><th>실행 Row수</th>");
						interLongQueryDataTables(dayOptions);
						initailizeInterEvent();
					}
					// 월로 설정
					else {
						dataTablesClear();
						$('#interLongQueryDataTables thead tr th').remove();
						$('#interLongQueryDataTables thead tr').append("<th>순번</th><th>년/월</th><th>DB명</th><th>SQL ID</th><th>평균 실행시간(sec)</th><th>전체 실행횟수</th><th>전체 실행Row수</th>");
						interLongQueryDataTables(monthOptions);
						initailizeInterEvent();
					}
			 	 });
				$('#search_type').val(commMap.search_type);
				$('#interLongQueryDataTables_length select[name=interLongQueryDataTables_length]').on('change', function(e) {
					$('#search_type').val(commMap.search_type);
				});

			}

		</script>
		<style type="text/css">
			.table-solid {
				border-right: 1px solid #111;
				border-bottom: 1px solid #111;
			}
			.table-bordered > thead > tr > th, table.dataTable.no-footer {
			  	border-bottom: 1px solid #111;
			}
			table.dataTable thead th, table.dataTable tfoot th {
				font-weight: bold;
			}
			.dataTables_empty {
				text-align:center;
			}
			table.dataTable.hover tbody tr:hover, table.dataTable.display tbody tr:hover {
				background-color:#F6F6F6;
			}
			table.dataTable.hover tbody tr.selected, table.dataTable.display tbody tr.selected {
				background-color:#BFBFBF
			}


			input[type='checkbox']{ height: 0; width: 0; }

			input[type='checkbox'] + label{
			  position: relative;
			  display: flex;
			  margin: .6em 0;
			  align-items: center;
			  color: #F27E72;
			  transition: color 250ms cubic-bezier(.4,.0,.23,1);
			  float:right;
			}
			input[type='checkbox'] + label > ins{
			  position: absolute;
			  display: block;
			  bottom: 0;
			  height: 0;
			  width: 100%;
			  overflow: hidden;
			  text-decoration: none;
			  transition: height 300ms cubic-bezier(.4,.0,.23,1);
			}
			input[type='checkbox'] + label > ins > i{
			  position: absolute;
			  bottom: 0;
			  font-style: normal;
			  color: #4FC3F7;
			}
			input[type='checkbox'] + label > span{
			  display: flex;
			  justify-content: center;
			  align-items: center;
			  margin-right: 0.5em;
			  width: 1em;
			  height: 1em;
			  background: transparent;
			  border: 2px solid #9E9E9E;
			  border-radius: 2px;
			  cursor: pointer;
			  transition: all 250ms cubic-bezier(.4,.0,.23,1);
			}

			input[type='checkbox'] + label:hover, input[type='checkbox']:focus + label{
			  color: #D12513;
			}
			input[type='checkbox'] + label:hover > span, input[type='checkbox']:focus + label > span{
			  background: rgba(255,255,255,.1);
			}
			input[type='checkbox']:checked + label > ins{ height: 100%; }

			input[type='checkbox']:checked + label > span{
			  border: .5em solid #FFEB3B;
			  animation: shrink-bounce 200ms cubic-bezier(.4,.0,.23,1);
			}
			input[type='checkbox']:checked + label > span:before{
			  content: "";
			  position: absolute;
			  top: .6em;
			  left: .2em;
			  border-right: 3px solid transparent;
			  border-bottom: 3px solid transparent;
			  transform: rotate(45deg);
			  transform-origin: 0% 100%;
			  animation: checkbox-check 125ms 250ms cubic-bezier(.4,.0,.23,1) forwards;
			}

			@keyframes shrink-bounce{
			  0%{
			    transform: scale(1);
			  }
			  33%{
			    transform: scale(.85);
			  }
			  100%{
			    transform: scale(1);
			  }
			}
			@keyframes checkbox-check{
			  0%{
			    width: 0;
			    height: 0;
			    border-color: #212121;
			    transform: translate3d(0,0,0) rotate(45deg);
			  }
			  33%{
			    width: .2em;
			    height: 0;
			    transform: translate3d(0,0,0) rotate(45deg);
			  }
			  100%{
			    width: .2em;
			    height: .5em;
			    border-color: #212121;
			    transform: translate3d(0,-.5em,0) rotate(45deg);
			  }
			}


			#move-left-right {
			  animation-duration: 2s;
			  animation-name: slidein;
			}

			@keyframes slidein {
			  from {
			    margin-left: 100%;
			    width: 300%
			  }

			  to {
			    margin-left: 0%;
			    width: 100%;
			  }
			}

		</style>
	</head>
	<body style="font:Nanum Gothic">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<h3 class="modal-title2">
				<b></b>
			</h3>
			<!-- 아이콘 영역 -->
			<h4 class="modal-title4"></h4>
		</div>
		<div class="modal-body">
			<div class="panel-body">
				<div class="display compact">
					<h4 style="font-weight:bold;color:#727272;margin-bottom:15px;" id="move-left-right"><i class="fa fa-clock-o fa-wa"></i>&nbsp;Long Query</h4>
					<table class="table table-bordered hover" style="width:100%" id="interLongQueryDataTables">
						<thead>
							<tr>
								<th>순번</th>
								<th>최종 실행일자(YMD)</th>
								<th>DB명</th>
								<th>SQL ID</th>
								<th>최종 실행시간(HMS)</th>
								<th>실행 시간(sec)</th>
								<th>최종 실행일자</th>
								<th>실행 횟수</th>
								<th>실행 Row수</th>
							</tr>
						</thead>
						<tbody id="longQueryGrid">
						</tbody>
					</table>
					<div style="margin-right:100px;" id="longQueryDetails">
						<table class="table table-bordered table-striped">
							<colgroup>
							    <col style="width: 20%;" />
							    <col style="width: 70%;"/>
						  	</colgroup>
							<tbody class="table-solid">
							</tbody>
						</table>
						<input id='long_query_checkbox' type='checkbox' />
						<label for="long_query_checkbox" id="move-left-right">
							<span></span>
								Back
							<ins><i>Back</i></ins>
						</label>
					</div>
				</div>
			</div>
		</div>

		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		</div>
	</body>
</html>