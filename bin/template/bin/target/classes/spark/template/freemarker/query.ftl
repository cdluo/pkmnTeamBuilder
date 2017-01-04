<#assign content>

<h1> Query: ${db} </h1>

<div id="NN">
	<p> Nearest Neighbors
		<form method="POST" action="/neighbors">
		  Number:<br>
		  <input type="text" name="Number"><br>
		  Coordinate(x,y,z [or] "name"):<br>
		  <input type="text" name="NNCord">
		  <input type="submit">
		</form>
	</p>
</div>

<div id="rad">
	<p id=> Radius Search
		<form method="POST" action="/radius">
		  Radius:<br>
		  <input name="Rad" type="text"><br>
		  Coordinate(x,y,z [or] "name"):<br>
		  <input type="text" name="RadCord">
		  <input type="submit">
		</form>
	</p>
</div>

<div id="results">
	<p>Results</p>
	<textarea rows="30" cols="30">${result}</textarea>
</div>

</#assign>
<#include "main.ftl">
