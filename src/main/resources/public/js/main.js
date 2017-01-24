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


	//Do this first to make sure team data is accurate.
	loadImmunity();

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

		$("#chartContent").empty();

		for(var i=0; i<teamChart.length; i++){
			var typeString = "<div class='rowSection whiteText " + teamChart[i][0] +"'>" + teamChart[i][0] + "</div>";
			var multiString1 = "<div class='rowSection content " + multiplierColorCode(1, teamChart[i][1]) + "'>" + teamChart[i][1] + "</div>";
			var multiString2 = "<div class='rowSection content " + multiplierColorCode(2, teamChart[i][2]) + "'>" + teamChart[i][2] + "</div>";
			var multiString3 = "<div class='rowSection content " + multiplierColorCode(3, teamChart[i][3]) + "'>" + teamChart[i][3] + "</div>";
			var multiString4 = "<div class='rowSection content " + multiplierColorCode(4, teamChart[i][4]) + "'>" + teamChart[i][4] + "</div>";
			var multiString5 = "<div class='rowSection rightBorder content " + multiplierColorCode(5, teamChart[i][5]) + "'>" + teamChart[i][5] + "</div>";

			var type = $(typeString);
			var multi1 = $(multiString1);
			var multi2 = $(multiString2);
			var multi3 = $(multiString3);
			var multi4 = $(multiString4);
			var multi5 = $(multiString5);

			$("#chartContent").append(type);
			$("#chartContent").append(multi1);
			$("#chartContent").append(multi2);
			$("#chartContent").append(multi3);
			$("#chartContent").append(multi4);
			$("#chartContent").append(multi5);
		}
	});
}

/*
	Section: 0-5, representing 0, .25, .5, etc...
	Value: # of PKMN weak/resistant to it.
*/
function multiplierColorCode(section, value){
	if(value == 0){
		return ""
	}else if(section < 4){
			return "green"
	}else{
		return "red"
	}
}

function fillTeamWeak(){
	$.post("/teamWeak", function(response) {
		$("#teamWeakContent").empty();
		var teamWeak = JSON.parse(response).result;

		for(var i = 0; i< teamWeak.length; i++){
			var typeString = "<div class='typeSection whiteText " + teamWeak[i] + "'>" + teamWeak[i] + "</div>";
			var type = $(typeString);
			$("#teamWeakContent").append(type);
		}
	});
}

function loadImmunity(){

	//Needs to be this format
	var immune = {};
	immune[3]=parseInt(document.getElementById("lightningRod").innerHTML);	//Electric
	immune[8]=parseInt(document.getElementById("levitate").innerHTML);	//Ground
	immune[2]=parseInt(document.getElementById("stormDrain").innerHTML);	//Water
	immune[1]=parseInt(document.getElementById("flashFire").innerHTML);	//Fire
	immune[4]=parseInt(document.getElementById("sapSipper").innerHTML);	//Grass

	$.post("/immunity", immune, function(response) {
	});
}

function fillRecTypes(){
	$.post("/recTypes", function(response) {
		$("#recTypeContent").empty();
		var recType = JSON.parse(response).result;

		for(var i = 0; i< recType.length; i++){
			var typeString = "<div class='typeSection whiteText " + recType[i] + "'>" + recType[i] + "</div>";
			var type = $(typeString);
			$("#recTypeContent").append(type);
		}
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

		$("#dialogue").html(recPKMNString);
	});
}

////////////////////////////////
// Controlling Ability Window //
////////////////////////////////

$("#immuneButton").click(function() {
	controlWindow();
});

function controlWindow(){

	console.log("Controlling Window");
	var abilWindow = document.getElementById("immuneWindow");
	var abilButton = document.getElementById("immuneButton");

	if(abilButton.classList.contains("closed")){
		abilButton.classList.remove("closed");
		abilWindow.style.transform = "translateX(21em)";
		abilButton.innerHTML = "close";
	}else{
		abilButton.classList.add("closed");
		abilWindow.style.transform = "translateX(0em)";
		abilButton.innerHTML = "Abilities";
	}
}

$(".immuneClick").click(function(){
	var value = $(this).parent().find(".immuneValue");
	var toAdd = value[0].innerHTML;
	value[0].innerHTML = parseInt(toAdd)+1;
});

$(".immuneClear").click(function(){
	var values = document.getElementsByClassName("immuneValue");

	for(var i = 0; i < values.length; i++){
		values[i].innerHTML = 0;
	}
});