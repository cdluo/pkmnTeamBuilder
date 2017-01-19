var width = document.documentElement.clientWidth;
resizeBody(width);

function resizeBody(width){

	var juniper = document.getElementById("placeHolderImg");
	var dialogue = document.getElementById("dialogue");

	if(width < 768){
		juniper.style.height = "140px";
		juniper.style.left = ".2em";
		dialogue.style.marginLeft = "80px"
		dialogue.style.width = "75%"
	}else{
		juniper.style.height = "220px";
		juniper.style.left = "1em";
		dialogue.style.marginLeft = "160px"
		dialogue.style.width = "80%"
	}
}

$(window).resize(function(){
	var width = document.documentElement.clientWidth;
	resizeBody(width);
});