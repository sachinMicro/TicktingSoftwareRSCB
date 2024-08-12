$(document).ready(function () {
	// Set event on Tickets select
	$('#form').find('input[type=checkbox][name=tickets]').on("click", function(){
		$('#form').find('input[type=radio][name=familyGroup][value=0]').prop("checked", true);
	});

	// Set event on Groups select
	$('#form').find('input[type=radio][name=group]').on("click", function(){
		$('#form').find('input[type=radio][name=familyGroup][value=0]').prop("checked", true);
	});

	// Set event on Family Groups select
	$('#form').find('input[type=radio][name=familyGroup]').on("click", function(){
		$('#form').find('input[type=checkbox][name=tickets]').each(function(){
			$(this).prop("checked", false)
		});
		$('#form').find('input[type=radio][name=group]').each(function(){
			$(this).prop("checked", false)
		});

		$('#form').find('input[type=number][name=persons]').attr({'min': 1});
	});

	// Set minimum 25 limit on Persons otherwise 0 limit with min attributes
	$('#form').find('input[type=radio][name=group]').each(function(){
		if ($(this).val() == "2"){
			$(this).on("change", function(){
				const $element = $('#form').find('input[type=number][name=persons]');
				$element.val(25);
				$element.attr({'min': 25});
			});
		}
		else {
			$(this).on("change", function(){
				$('#form').find('input[type=number][name=persons]').attr({'min': 1});
			});
		}
	});

	// Same minimum 25 limit but adjust for up down button
	$('#form').find('input[type=number][name=persons]').parent().find('.minus').on("click", function(){
		const $numb = $('#form').find('input[type=number][name=persons]');
		if (parseInt($numb.val()) <= parseInt($numb.attr('min'))) {
			$numb.val(parseInt($numb.val()) + 1);
		}
	});

	// Setting input field min limit
	$('#form').find('input[type=number][name=persons]').on("focusout", function(){
		if (parseInt($(this).val()) < parseInt($(this).attr('min'))){
			$(this).val($(this).attr('min'));
		}
	});
});
