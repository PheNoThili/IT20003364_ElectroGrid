$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateNoticeForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	} else {
		$("#alertSuccess").text("Successfully Saved");
		$("#alertSuccess").show();
	}
	// If valid------------------------
	var type = ($("#hidNoticeIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
	{
	url : "NoticesAPI",
	type : type,
	data : $("#formNotice").serialize(),
	dataType : "text",
	complete : function(response, status)
	{
	onNoticeSaveComplete(response.responseText, status);
	}
	});
	});
	function onNoticeSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divNoticesGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidNoticeIDSave").val("");
	$("#formNotice")[0].reset();
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidNoticeIDSave").val($(this).data("noticeid"));
	$("#userId").val($(this).closest("tr").find('td:eq(0)').text());
	$("#noticeSubject").val($(this).closest("tr").find('td:eq(1)').text());
	$("#noticeBody").val($(this).closest("tr").find('td:eq(2)').text());
	// $("#date").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "NoticesAPI",
		type : "DELETE",
		data : "NoticeId=" + $(this).data("noticeid"),
		dataType : "text",
		complete : function(response, status) {
			onNoticeDeleteComplete(response.responseText, status);
		}
	});
});

function onNoticeDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divNoticesGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL================================================================
function validateNoticeForm() {
	// ID
	if ($("#userId").val().trim() == "") {
		return "Insert Admin ID.";
	}
	// Subject
	if ($("#noticeSubject").val().trim() == "") {
		return "Insert Notice Subject.";
	}
	// Body------------------------
	if ($("#noticeBody").val().trim() == "") {

		return "Insert Notice Body.";
	}
	return true;
}