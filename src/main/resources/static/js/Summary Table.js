function updateSummary(responseData){
	alert("Bill update request sent: " + JSON.stringify(responseData) + ".");

	const $table = $("#billSummary");

	$table.find("tbody").remove();
	$table.find("tfoot").remove();

	const $tbody = $("<tbody></tbody>");
	const $tfoot = $("<tfoot></tfoot>");
	$table.append($tbody);
	$table.append($tfoot);

/*
	const table = document.getElementById("billSummary");
	for (let i = 0; i < table.getElementsByTagName("tbody").length; ++i) {
		table.getElementsByTagName("tbody")[i].remove();
	}
	for (let i = 0; i < table.getElementsByTagName("tfoot").length; ++i) {
		table.getElementsByTagName("tfoot")[i].remove();
	}
	const tbody = document.createElement("tbody");
	const tfoot = document.createElement("tfoot");
	table.appendChild(tbody);
	table.appendChild(tfoot);
*/
}
