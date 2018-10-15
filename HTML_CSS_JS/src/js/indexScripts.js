document.onsubmit = onSubmit;

function onFocusField() {
    var node = document.getElementById('employeeName');
    if (node.value === "") {
        node.value = "Например, Иванов";
        $('#employeeName').select();
    }
}

function onSubmit() {
    var fieldValue = document.getElementById("employeeNumber").value;
    if (isNaN(parseInt(fieldValue))) {
        document.getElementById("employeeNumber").style.backgroundColor = "#FF0000";
        alert("Need to enter the number, not a random string or be empty");
    } else {
        var value = document.getElementById("employeeName").value;
        var url = "submiting.html";
        var new_window = window.open(url + "?" + value);
        new_window.document.getElementById("employeeName").value = value;
    }
}

function onShow() {
    var isShow = document.getElementById("empTable").style.display;
    if (isShow !== "none") {
        document.getElementById("empTable").style.display = "none";
    } else {
        document.getElementById("empTable").style.display = "flex";
    }
}

$('.deleteRef').click(function () {
    var calling = $(this);
    $("#modal").dialog({
        dialogClass: "dialog",
        modal: true,
        width: 200,
        height: 200,
        resizable: false,
        buttons: [{
            text: "Delete", "class": "deleteDialogBtn", click: function () {
                $(calling).parents("tr").css("display", "none");
                $(this).dialog("close");
            }
        },
            {
                text: "Cancel", "class": "cancelDialogBtn", click: function () {
                    $(this).dialog("close");
                }
            }]
    });
    $(".ui-dialog-titlebar").hide();
});
