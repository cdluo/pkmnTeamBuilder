<#assign content>

<!-- Header -->
<div id="header">
	<div id="logo"></div>
</div>

<!-- Form -->
<div class="col-sm-6" style="position:relative; margin-top:2.75em; padding: 0 0;">
	<div id="pkmnSelectors">
		<div class="pkmnSelector">
			<textarea class="autocorrect" placeholder="Pokemon"></textarea><div class="autoBox"></div>
			<textarea class="type1" placeholder="type1"></textarea>
			<textarea class="type2" placeholder="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<textarea class="autocorrect" placeholder="Pokemon"></textarea><div class="autoBox"></div>
			<textarea class="type1" placeholder="type1"></textarea>
			<textarea class="type2" placeholder="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<textarea class="autocorrect" placeholder="Pokemon"></textarea><div class="autoBox"></div>
			<textarea class="type1" placeholder="type1"></textarea>
			<textarea class="type2" placeholder="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<textarea class="autocorrect" placeholder="Pokemon"></textarea><div class="autoBox"></div>
			<textarea class="type1" placeholder="type1"></textarea>
			<textarea class="type2" placeholder="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<textarea class="autocorrect" placeholder="Pokemon"></textarea><div class="autoBox"></div>
			<textarea class="type1" placeholder="type1"></textarea>
			<textarea class="type2" placeholder="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector" style="margin-bottom: 15px">
			<textarea class="autocorrect" placeholder="Pokemon"></textarea><div class="autoBox"></div>
			<textarea class="type1" placeholder="type1"></textarea>
			<textarea class="type2" placeholder="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>
	</div>
	<button id="submit">Submit</button>
</div>

<!-- Chart -->
<div class="col-sm-6" style="position:relative; margin-top:1em; padding: 0 0;">
	<div id="typeChart">
		<div style="height:1em"></div>
		<!-- Header Row-->
		<div class = "rowSection header">Type</div><div class = "rowSection header">0.0x</div><div class = "rowSection header">0.25x</div><div class = "rowSection header">0.5x</div><div class = "rowSection header">2.0x</div><div class = "rowSection header rightBorder">4.0x</div>

		<div id="chartContent">
		</div>
		<div style="height:1em"></div>
	</div>
</div>

<div class="row" style="margin: 0 0">
	<div class="col-sm-6" style="padding: 0 0">
		<div class="typeBox" id="teamWeakBox">
			<p class="typeLabel header">Team Weaknesses</p>
			<div style="margin-left:.5em" id="teamWeakContent"></div>
		</div>
	</div>

	<div class="col-sm-6" style="padding: 0 0">
		<div class="typeBox" id="recTypes">
			<p class="typeLabel header">Recommended Types</p>
			<div style="margin-left:.5em" id="recTypeContent"></div>
		</div>
	</div>
</div>

<!-- Recommended Pokemon -->
<div class="col-sm-12" style="margin: 0 0; padding: 0 0">
	<div id="recPKMN">
		<img id="placeHolderImg" src="../images/binaPlaceHolder.png">
		<div id="dialogue">
		</div>
	</div>
</div>

</#assign>
<#include "main.ftl">
