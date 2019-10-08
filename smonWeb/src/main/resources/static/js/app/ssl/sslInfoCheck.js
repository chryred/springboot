$(document).ready(function() {
	fn_selectSslInfo();
});

function fn_selectSslInfo(){
	initSslInfoTable(fn_selectTran(contextPath + '/sslInfoDataTables.do', false, ''));
}


function initSslInfoTable(data) {
	
	$('#sslInfoDataTables').dataTable().fnClearTable();
    $('#sslInfoDataTables').dataTable().fnDestroy();

	$('#sslInfoDataTables').DataTable({
        data : data,
        columns : [ {data : 'SERVICE_NAME', sClass : "left"}, 
                    {data : 'IP_PORT', sClass : "left"}, 
                    {data : 'DOMAIN', sClass : "left",
                    	render:function(data,type,full,meta){
                    		return data.replace(/,/gi,'<br/>');
        	              }	
                    },
                    {data : 'EXPIRATION_DATE', sClass : 'right',
                     render:function(data,type,full,meta){
    	                if(data < 10){
    	                	return "<font color='red' size = '3'><b>" + data +"</b></font>" + " 일";
    	                }else if(data < 30){
    	                	return "<font color='orange' size = '3'><b>" + data +"</b></font>" + " 일";
    	                 }else if(data < 60){
    	                	 return "<font color='blue' size = '3'><b>" + data +"</b></font>" + " 일";
    	                 }else{
    	                	 return "<font color='green' size = '3'><b>" + data +"</b></font>" + " 일";
    	                 }
    	              }	
                    },
                    {data : 'SSL_ENDDATE', sClass : "center"}, 
                    {data : 'SSL_INFO'}
                    ],
        ordering : false,
        paging : false,
        info : false
    });
}