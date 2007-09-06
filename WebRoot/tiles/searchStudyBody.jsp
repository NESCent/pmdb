<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<h3>Search Studies by Species</h3>
<form action="searchStudy.go" method="post" name="Search_Study_Form">
<table>
<tr>
	<td class="TdField">Family</td>
	<td class="TdValue"><input name="family" />
</tr>
<tr>	
	<td class="TdField">Genus</td>
	<td class="TdValue"><input name="genus" />
</tr>
<tr>
	<td class="TdField">Species</td>
	<td class="TdValue"><input name="species" />
</tr>
<tr>
	<td></td>
	<td><input type="submit" value="Search"/></td>
</table>
</form>