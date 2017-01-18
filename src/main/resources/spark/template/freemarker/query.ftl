<#assign content>

<!-- Header -->
<div id="header">
	<div id="logo"></div>
</div>

<!-- Form -->
<div class="col-sm-6" style="position:relative; top:2.5em">
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
<div class="col-sm-6" style="position:relative; top:2em">
	<div id="typeChart">
		<p id="typeChartText"></p>
	</div>
</div>

<div class="col-sm-12" id="recTypes">
	<p style = "position:relative; display:inline-block; margin-left:10px"> Your team is weak to: </p>
	<p id="teamWeak"></p>

	<br>

	<p style = "position:relative; display:inline-block; margin-left:10px"> Recommended Types: </p>
	<p id="recTypesText"></p>
</div>

<div class="col-sm-12" id="recPKMN">
	<p style = "position:relative; display:inline-block; margin-left:10px"> Recommended Pokemon: </p>
	<p id="recPKMNText"></p>
</div>

</#assign>
<#include "main.ftl">
