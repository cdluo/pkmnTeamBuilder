/////////////////
// JQuery/AJAX //
/////////////////

/*
	Gets rid of the auto suggestion box when anywhere else is clicked.
*/
$(document).click(function() {
    $(".autoBox").css("visibility","hidden");
});

/*
	Fills in team selector when suggestion is clicked.
*/
$(document).on("click", ".autoSuggestion", function(e){

	//Why do you have to do it this way? Read on() documentation.
	//Dynamically generated elements...

	var selected = $(this)[0].innerHTML;	//Gets inner HTML (Suggested PKMN's name)
	var data = {input:selected};

	var selector = $(this).parent().parent();
	var nameBox = selector.find(".autocorrect");
	var type1Box = selector.find(".type1");
	var type2Box = selector.find(".type2");

	$.post("/findPkmn", data, function(response) {
		var pkmn = JSON.parse(response).result;

		//Set to DB query.
		nameBox.val(pkmn.name);
		type1Box.val(pkmn.type1);
		type2Box.val(pkmn.type2);
	})
});

/*
	Gets auto correct suggestions as user types
*/
$(".autocorrect").on("keyup", function(e){
	var userInput = $(this).val();
	var data = {input:userInput};
	var autoBox = $(this).parent().find(".autoBox");
	var suggestions;

	$.post("/autocorrect", data, function(response) {
		suggestions = JSON.parse(response).result;
		autoBox.empty();

		for(var i = 0; i< suggestions.length; i++){
			// Add suggestion rows to autoBox
			var toAdd = document.createElement("p");
			var text = suggestions[i];
			var textNode = document.createTextNode(text);
			toAdd.appendChild(textNode);
			toAdd.className = "autoSuggestion";
			autoBox.append(toAdd);
		}

		autoBox.css("visibility","visible");
	})
});

/*
	Submits the team form and calls all the "fill" functions.
*/
$("#submit").click(function() {
	var selectors = $(".pkmnSelector");

	var data = {};

	for(var i = 0; i < 6; i++){
		var name = selectors[i].getElementsByClassName("autocorrect")[0];
		var t1 = selectors[i].getElementsByClassName("type1")[0];
		var t2 = selectors[i].getElementsByClassName("type2")[0];

		data['p' + i + 'Name'] = name.value;
		data['p' + i + 't1'] = t1.value;
		data['p' + i + 't2'] = t2.value;
	}

	$.post("/loadTeam", data, function(response) {
		fillTeamChart();
		fillTeamWeak();
		fillRecTypes();
		fillRecPKMM();
	});
});

/*
	Clears each respective entry in the team form when clicked.
*/
$(".selectorClear").click(function() {
	var name = $(this).parent().find(".autocorrect");
	var type1 = $(this).parent().find(".type1");
	var type2 = $(this).parent().find(".type2");

	name.val("");
	type1.val("");
	type2.val("");

});


////////////////////
// Fill Functions //
////////////////////

/*
	Fill functions populate their respective windows.
*/
function fillTeamChart(){
	$.post("/teamChart", function(response) {
		var teamChart = JSON.parse(response).result;
		var teamChartBreak = teamChart.replace(/\n/g, '<br/>');

		//teamChart is currently just a string.

		$("#typeChartText").html(teamChartBreak);
	});
}

function fillTeamWeak(){
	$.post("/teamWeak", function(response) {
		var teamWeak = JSON.parse(response).result;
		var weakString = "";
		for(var i = 0; i< teamWeak.length; i++){
			weakString += teamWeak[i];
			weakString += ", ";
		}

		$("#teamWeak").html(weakString);
	});
}

function fillRecTypes(){
	$.post("/recTypes", function(response) {
		var recType = JSON.parse(response).result;
		var recTypeString = "";
		for(var i = 0; i< recType.length; i++){
			recTypeString += recType[i];
			recTypeString += ", ";
		}

		$("#recTypesText").html(recTypeString);
	});
}

function fillRecPKMM(){
	$.post("/recPKMN", function(response) {
		var recPKMN = JSON.parse(response).result;
		var recPKMNString = "";
		for(var i = 0; i< recPKMN.length; i++){
			recPKMNString += recPKMN[i];
			recPKMNString += ", ";
		}

		$("#recPKMNText").html(recPKMNString);
	});
}