var billSummaryTable;
var tableConfig = {
	info: false,
	ordering: false,
	paging: false,
	searching: false,
	responsive: true,
	destroy: true,
	data: []
};

$(document).ready(function() {

	$('#CALCULATE').on("click", function() {
		getFormFields($(this).closest("form"));
		return false;
	});
	
	$('#PRINT').on("click", function() {
		getFormFields($(this).closest("form"));
		return true;
	});
		billSummaryTable =
		new DataTable('#billSummary', tableConfig);
});

function getFormFields($form) {
	var data = [];
	$form.serializeArray().forEach(function(field) {
		data.push(field.name + "=" + field.value);
	});
	$.ajax({
		url: "/bill/calculate",
		type: "POST",
		data: data.join("&"),
		contentType: "application/x-www-form-urlencoded",
	}).done(function(data,status) {
		let grandTotal = 0;
		billSummaryTable.clear().destroy();
		tableConfig.data = [];		
        console.log(JSON.stringify(data));     
          
		data.data.billDescription.forEach(function(obj) {			
			var row = [
				obj.ticket+"<span class='ms-1 badge text-dark bg-info'>" + obj.groupName.toUpperCase() + "</span>",
				"<small>" + obj.person+ " x " + obj.perPersonPrice + "</small>",
				obj.totalSum
			];
			grandTotal += obj.totalSum;
			tableConfig.data.push(row);
		});

		if(data.data.parkingBillDescription != null) {
			data.data.parkingBillDescription.forEach(function(obj) {
				var row = [
					obj.desc+"<span class='ms-1 badge text-dark bg-warning'>PARKING</span>",
					"<small>" + obj.count+ " x " + obj.perCharge + "</small>",
					obj.sum
				];
				grandTotal += obj.sum;
				tableConfig.data.push(row);
			});
		}
		billSummaryTable =
			new DataTable('#billSummary', tableConfig);
		$('#grandTotal').text(grandTotal);
		showToast('Message',data.message,data.status);
	}).fail(function(data){
		showToast('Message',data.responseJSON.message,data.responseJSON.status);	
	});	
	;
}

/*
$(document).ready(function() {
	$('.form-container .btn-group').eq(0).on("click", function() {
		$(this).find(".btn-check");
		$(this).find(".btn");
		$('.form-container .btn-group').eq(2).find(".btn-check").each(function(index) {
			if ($(this).prop('checked')) {
				$(this).prop('checked', false);
			}
		});
	});
	$('.form-container .btn-group').eq(1).on("click", function() {
		$(this).find(".btn-check");
		$(this).find(".btn");
		$('.form-container .btn-group').eq(2).find(".btn-check").each(function(index) {
			if ($(this).prop('checked')) {
				$(this).prop('checked', false);
			}
		});
	});
	$('.form-container .btn-group').eq(2).on("click", function() {
		$(this).find(".btn-check");
		$(this).find(".btn");
		$('.form-container .btn-group').eq(0).find(".btn-check").each(function(index) {
			if ($(this).prop('checked')) {
				$(this).prop('checked', false);
			}
		});
		$('.form-container .btn-group').eq(1).find(".btn-check").each(function(index) {
			if ($(this).prop('checked')) {
				$(this).prop('checked', false);
			}
		});
		$('.form-container .counter .input-count').eq(0).val(0);
	});
});
*/
