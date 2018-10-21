document.onsubmit = onSubmit;

$("#employeeName").focusin(function () {
    var val = $(this).val();
    if (val === "Например, Иванов") {
        $(this).val("");
    }
});

$("#employeeName").focusout(function () {
    var val = $(this).val();
    if (val === "") {
        $(this).val("Например, Иванов");
    }
});

function onSubmit() {
    var fieldValue = document.getElementById("employeeNumber").value;
    if (isNaN(parseInt(fieldValue))) {
        document.getElementById("employeeNumber").style.backgroundColor = "#FF0000";
        alert("Need to enter the number, not a random string or be empty");
    } else {
        var value = document.getElementById("employeeName").value;
        var url = "submiting.html";
        var new_window = window.open(url + "?" + value);
    }
}

function onShow() {
    var isShow = document.getElementById("EmpTable").style.display;
    if (isShow !== "none") {
        document.getElementById("EmpTable").style.display = "none";
    } else {
        document.getElementById("EmpTable").style.display = "flex";
    }
}

$('.deleteRef').click(function () {
    var calling = $(this);
    $("#Modal").dialog({
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
