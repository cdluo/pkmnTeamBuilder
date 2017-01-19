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
		<!-- Header Row-->
		<div class = "rowSection">Type</div><div class = "rowSection">0.0x</div><div class = "rowSection">0.25x</div><div class = "rowSection">0.5x</div><div class = "rowSection">2.0x</div><div class = "rowSection rightBorder">4.0x</div>

		<div id="chartContent">
			
		</div>
	</div>
</div>

<!-- Team Weaknesses -->
<div class="col-sm-12" id="recTypes">
	<p style = "position:relative; display:inline-block; margin-left:10px"> Your team is weak to: </p>
	<p id="teamWeak"></p>

	<br>

	<p style = "position:relative; display:inline-block; margin-left:10px"> Recommended Types: </p>
	<p id="recTypesText"></p>
</div>

<!-- Recommended Pokemon -->
<div class="col-sm-12" id="recPKMN">
	<p style = "position:relative; display:inline-block; margin-left:10px"> Recommended Pokemon: </p>
	<p id="recPKMNText"></p>
</div>

</#assign>
<#include "main.ftl">
