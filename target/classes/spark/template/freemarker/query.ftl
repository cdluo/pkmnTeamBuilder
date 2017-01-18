<#assign content>

<!-- Header -->
<div id="header">

<div id="logo"></div>

</div>

<div id="totalChart">
	<div id="pkmnSelectors">
		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
			<button class="selectorClear">Clear</button>
		</div>

		<button id="submit">Submit</button>
	</div>

	<div id="typeChart">
		<p id="typeChartText"></p>
	</div>
</div>

<div id="recPKMN">
	<p style = "position:relative; display:inline-block; margin-left:10px"> Recommended Pokemon: </p>
		<p id="recPKMNText"></p>
</div>

<div id="recTypes">
	<div style="display:block">
		<p style = "position:relative; display:inline-block; margin-left:10px"> Your team is weak to: </p>
		<p id="teamWeak"></p>
	</div>

	<div style = "display:block">
		<p style = "position:relative; display:inline-block; margin-left:10px"> Recommended Types: </p>
		<p id="recTypesText"></p>
	</div>
</div>

</#assign>
<#include "main.ftl">
