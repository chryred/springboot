/*jQuery.fn.dataTableExt.oApi.fnFakeRowspan = function ( oSettings, iColumn, bCaseSensitive ) {

    var oldData;
    var rowspan = 0;
    
    for(var i in oSettings.aoData){
    
    
    var pageSize = oSettings._iDisplayLength;
    var oData = oSettings.aoData[i];
    var cellsToRemove = [];
    
    if(i == 0){
        oldData = oData.nTr.childNodes[-1];
    }
    
    var newData = oData.nTr.childNodes[0];
    
    console.log($(oldData).text());
    
    if($(oldData).text() != $(newData).text()){
        if(i != 0){
            oldData.rowSpan = rowspan;
            rowspan = 1;
        }
        oldData = newData;
    }else{
        rowspan ++;
        oData.nTr.removeChild(newData);  
    }
    
    
    for (var iColumn = 0; iColumn < oData.nTr.childNodes.length; iColumn++) {
      
      var cell = oData.nTr.childNodes[iColumn];
      var rowspan = $(cell).data('rowspan');

      var hide = $(cell).data('hide');

      if (hide) {
        cellsToRemove.push(cell);
      } else if (rowspan > 1) {
        cell.rowSpan = rowspan;
      }
    }
    for(var j in cellsToRemove){
      var cell = cellsToRemove[j];
        oData.nTr.removeChild(cell);      
    }
  }
    
  //oSettings.aoDrawCallback.push({ "sName": "fnFakeRowspan" });
  return this;
};*/


jQuery.fn.dataTableExt.oApi.fnFakeRowspan = function ( oSettings, iColumn, bCaseSensitive ) {
    /* Fail silently on missing/errorenous parameter data. */
    if (isNaN(iColumn)) {
        return false;
    }
 
    if (iColumn < 0 || iColumn > oSettings.aoColumns.length-1) {
        alert ('Invalid column number choosen, must be between 0 and ' + (oSettings.aoColumns.length-1));
        return false;
    }
 
    bCaseSensitive = (typeof(bCaseSensitive) != 'boolean' ? true : bCaseSensitive);
 
    function fakeRowspan () {
        var firstOccurance = null,
            value = null,
            rowspan = 0;
        
        jQuery.each(oSettings.aoData, function (i, oData) {

            var cell = oData.nTr.childNodes[iColumn],
                val = $(cell).text();
           
            
            /* Use lowercase comparison if not case-sensitive. */
            if (!bCaseSensitive) {
                val = val.toLowerCase();
            }
            
            /* Reset values on new cell data. */
            /*if (cell != value) {
                value = cell;
                firstOccurance = cell;
                rowspan = 0;
            }
 
            if (cell == value) {
                rowspan++;
                console.log(rowspan);
            }
 
            if (firstOccurance !== null && cell == value && rowspan > 1) {
                oData.nTr.removeChild(cell);
                firstOccurance.rowSpan = rowspan;
            }*/
            if (val != value) {
                value = val;
                firstOccurance = cell;
                rowspan = 0;
            }
 
            if (val == value) {
                rowspan++;
            }
 
            if (firstOccurance !== null && val == value && rowspan > 1) {
                oData.nTr.removeChild(cell);
                firstOccurance.rowSpan = rowspan;
            }
        });
    }
 
    oSettings.aoDrawCallback.push({ "fn": fakeRowspan, "sName": "fnFakeRowspan" });
 
    return this;
};