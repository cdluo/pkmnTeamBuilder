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
	<button id="immuneButton" class="closed"> Abilities </div>
	<div id="immuneWindow">
		<div class="immuneRow"><div class="immuneClick Electric" style="margin-top:.5em"> Lightning Rod </div> <p class= "immuneValue" id="lightningRod">0</p></div>

		<div class="immuneRow"><div class="immuneClick Ground"> Levitate </div> <p class= "immuneValue" id="levitate">0</p></div>
		<div class="immuneRow"><div class="immuneClick Water"> Storm Drain </div> <p class= "immuneValue" id="stormDrain">0</p></div>
		<div class="immuneRow"><div class="immuneClick Fire"> Flash Fire </div> <p class= "immuneValue" id="flashFire">0</p></div>
		<div class="immuneRow"><div class="immuneClick Grass" style="margin-bottom:.5em"> Sap Sipper </div> <p class= "immuneValue" id="sapSipper">0</p></div>
		<button class="immuneClear">Clear</button>
	</div>
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
