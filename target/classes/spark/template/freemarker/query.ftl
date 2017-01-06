<#assign content>

<h1> Smart Pokemon Team Builder</h1>

<div id="totalChart">
	<div id="pkmnSelectors">
		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
		</div>

		<div class="pkmnSelector">
			<p>Name: </p> <textarea class="autocorrect"></textarea></textarea><div class="autoBox"></div>
			<p>Type1: </p> <textarea class="type1"></textarea>
			<p>Type2: </p> <textarea class="type2"></textarea>
		</div>

		<button id="submit">Submit</button>
	</div>

	<div id="typeChart">
		<p style="margin:0 0; text-align:center;"> Type Chart </p>
		<div class="typeRow">
		</div>
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
