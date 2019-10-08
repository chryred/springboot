/*$(document).ready(function() {

	$('#btn-add-row').click(function() {
			var table_code = '<tr>';
			table_code = table_code +  '<td><textarea rows="1" cols="4">11</textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4">22</textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '<td><textarea rows="1" cols="4"></textarea></td>';
			table_code = table_code +  '</tr>';
			$('#mytable > tbody:last').append(table_code);
		});
		$('#btn-delete-row').click(function() {
		  $('#mytable > tbody:last > tr:last').remove();
		});
});
*/

    $(document).ready(function () {
        $("#grid").shieldGrid({
            dataSource: {
                data: gridData,
                schema: {
                    fields: {
                        id: { path: "id", type: Number },
                        age: { path: "age", type: Number },
                        name: { path: "name", type: String },
                        company: { path: "company", type: String },
                        month: { path: "month", type: Date },
                        isActive: { path: "isActive", type: Boolean },
                        email: { path: "email", type: String },
                        transport: { path: "transport", type: String }
                    }
                }
            },
            sorting: {
                multiple: true
            },
            rowHover: false,
            columns: [
                { field: "name", title: "Person Name", width: "120px" },
                { field: "age", title: "Age", width: "80px" },
                { field: "company", title: "Company Name" },
                { field: "month", title: "Date of Birth", format: "{0:MM/dd/yyyy}", width: "120px" },
                { field: "isActive", title: "Active" },
                { field: "email", title: "Email Address", width: "250px" },
                { field: "transport", title: "Custom Editor", width: "120px", editor: myCustomEditor },
                {
                    width: "104px",
                    title: "Delete Column",
                    buttons: [
                        { cls: "deleteButton", commandName: "delete", caption: "<img src='http://www.prepbootstrap.com/Content/images/template/BootstrapEditableGrid/delete.png' /><span>Delete</span>" }
                    ]
                }
            ],
            editing: {
                enabled: true,
                event: "click",
                type: "cell",
                confirmation: {
                    "delete": {
                        enabled: true,
                        template: function (item) {
                            return "Delete row with ID = " + item.id
                        }
                    }
                }
            },
            events:
            {
                getCustomEditorValue: function (e) {
                    e.value = $("#dropdown").swidget().value();
                    $("#dropdown").swidget().destroy();
                }
            }
        });

        function myCustomEditor(cell, item) {
            $('<div id="dropdown"/>')
                .appendTo(cell)
                .shieldDropDown({
                    dataSource: {
                        data: ["motorbike", "car", "truck"]
                    },
                    value: !item["transport"] ? null : item["transport"].toString()
                }).swidget().focus();
        }
    });
