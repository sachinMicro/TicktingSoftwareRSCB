var ticketSchema = {
	seperateTickets: {
		types: [],
		groupType: "",
		personCount: 0
	},
	familyGroup: {
		type: ""
	},
	vehicle: {
		bikes: 0,
		threeFourWheeler: 0
	}
}

$(document).ready(function(){
	$('.form-container .btn-group').eq(0).on("click", function() {
		const inputElements = $(this).find(".btn-check");
		const labelElements = $(this).find(".btn");
		ticketSchema.seperateTickets.types = [];
		inputElements.each(function (index) {
			// ticketSchema.seperateTickets.types[labelElements.eq(index).text()] = $(this).prop('checked');
			if ($(this).prop('checked')){
				ticketSchema.seperateTickets.types.push(labelElements.eq(index).text())
			}
		});
	});

	$('.form-container .btn-group').eq(1).on("click", function() {
		const inputElements = $(this).find(".btn-check");
		const labelElements = $(this).find(".btn");
		inputElements.each(function (index) {
			if ($(this).prop('checked')) {
				ticketSchema.seperateTickets.groupType = labelElements.eq(index).text();
				ticketSchema.familyGroup.type = "";
			}
		});
	});

	$('.form-container .btn-group').eq(2).on("click", function() {
		const inputElements = $(this).find(".btn-check");
		const labelElements = $(this).find(".btn");
		inputElements.each(function (index) {
			if ($(this).prop('checked')) {
				ticketSchema.familyGroup.type = labelElements.eq(index).text();
				ticketSchema.seperateTickets.groupType = "";
			}
		});
	});

	$('.form-container .counter .input-count').eq(0).on("input", function() {
		ticketSchema.seperateTickets.personCount = $(this).val();
	});
	$('.form-container .counter .input-count').eq(1).on("input", function() {
		ticketSchema.vehicle.bikes = $(this).val();
	});
	$('.form-container .counter .input-count').eq(2).on("input", function() {
		ticketSchema.vehicle.threeFourWheeler = $(this).val();
	});

	$('.form-container .mt-3 .btn-group .btn').on("click", function() {
		const inputElement = $(this).parent().parent().parent().find(".counter .input-count");
		inputElement.val($(this).html());
		ticketSchema.seperateTickets.personCount = inputElement.val();
	});
	$('.form-container .my-3').eq(0).find(".btn-group .btn").on("click", function() {
		const inputElement = $(this).parent().parent().parent().find(".counter .input-count").eq(0);
		inputElement.val($(this).html());
		ticketSchema.vehicle.bikes = inputElement.val();
	});
	$('.form-container .my-3').eq(1).find(".btn-group .btn").on("click", function() {
		const inputElement = $(this).parent().parent().parent().find(".counter .input-count").eq(1);
		inputElement.val($(this).html());
		ticketSchema.vehicle.threeFourWheeler = inputElement.val();
	});

	const calculateButton = $('<button class=\"btn btn-success p-3\" type=\"button\"></button>').text('Update bill');
	$('.form-container .btn').eq($('.form-container .btn').length - 1).parent().append(calculateButton);
	calculateButton.on("click", function() {
		$(calculateButton).on("click", function() {
			$.ajax({
				url: "/bill/update",
				type: "POST",
				data: {"ticketSchema": JSON.stringify(ticketSchema)},
                contentType: "application/json; charset=utf-8",
				// dataType: "json",
				success: function(response) {

					alert("Bill update request sent: " + response + ".");

				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert(textStatus + ": Bill updation failed");
					console.error("AJAX Error: ", textStatus, errorThrown);
				}
			});
		});
	});
});
