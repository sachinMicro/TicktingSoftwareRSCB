$(document).ready(function () {
	$('#form').on("submit", function () {
		getFormFields($(this));
		return false;
	});
});

function getFormFields($form){
	// $form.find('input[type=checkbox]').each(function() {
	//	 if (this.checked) {
	//		 console.log('Checked checkbox:', $(this).attr('name') + ": " + $(this).val());
	//	 } else {
	//		 console.log('Unchecked checkbox:', $(this).attr('name') + ": " + $(this).val());
	//	 }
	// });
	var data = [];
	$form.serializeArray().forEach(function(field) {
		// console.log(field.name + ": " + field.value);
		data.push(field.name + "=" + field.value);
	});

	$.ajax({
		url: "/bill/calculate",
		type: "POST",
		data: data.join("&"),
		contentType: "application/x-www-form-urlencoded",
		success: updateSummary,
		error: function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + ": Bill updation failed");
			console.error("AJAX Error: ", textStatus, errorThrown);
		}
	});
}

// function updateSummary(responseData){
// 	alert("Bill update request sent: " + JSON.stringify(responseData) + ".");
// }

$(document).ready(function () {
	$('.form-container .btn-group').eq(0).on("click", function() {
		const inputElements = $(this).find(".btn-check");
		const labelElements = $(this).find(".btn");
		$('.form-container .btn-group').eq(2).find(".btn-check").each(function(index){
			if ($(this).prop('checked')) {
				$(this).prop('checked', false);
			}
		});
	});
	$('.form-container .btn-group').eq(1).on("click", function() {
		const inputElements = $(this).find(".btn-check");
		const labelElements = $(this).find(".btn");
		$('.form-container .btn-group').eq(2).find(".btn-check").each(function(index){
			if ($(this).prop('checked')) {
				$(this).prop('checked', false);
			}
		});
	});
	$('.form-container .btn-group').eq(2).on("click", function() {
		const inputElements = $(this).find(".btn-check");
		const labelElements = $(this).find(".btn");
		$('.form-container .btn-group').eq(0).find(".btn-check").each(function(index){
			if ($(this).prop('checked')) {
				$(this).prop('checked', false);
			}
		});
		$('.form-container .btn-group').eq(1).find(".btn-check").each(function(index){
			if ($(this).prop('checked')) {
				$(this).prop('checked', false);
			}
		});
		$('.form-container .counter .input-count').eq(0).val(0);
	});
});
