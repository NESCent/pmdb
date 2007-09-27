<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>
<script language="javascript">
function checkData()
{
	var form=document.Add_ExperilemntalStudy_Form;
	if(form.studyName.value==null || form.studyName.value.replace(/^\s+|\s+$/g, '')=="")
	{
		alert("Study name can not be empty.")
		return false;
	}
	if(form.genus.value==null ||form.genus.value.replace(/^\s+|\s+$/g, '')=="")
	{
		alert("Genus can not be empty.")
		return false;
	}
	if(form.species.value==null ||form.species.value.replace(/^\s+|\s+$/g, '')=="")
	{
		alert("Species can not be empty.")
		return false;
	}
	if(form.population.value==null ||form.population.value.replace(/^\s+|\s+$/g, '')=="")
	{
		alert("Population can not be empty.")
		return false;
	}
	if(form.citation.value==null ||form.citation.value.replace(/^\s+|\s+$/g, '')=="")
	{
		alert("Citation can not be empty.")
		return false;
	}
	return true;
}
</script>
<h3>Add Experimental Study</h3>
<form action="addexperimentalstudy.go" method="post" name="Add_ExperilemntalStudy_Form" onSubmit="return checkData();">
<%
NoCache.nocache(response);
%>
<table>
<tr>
	<td class="TdField required">Study Name</td>
	<td class="TdValue"><input name="studyName" type="text"  size="50"/></td>
</tr>	
<tr>	
<th colspan=2>Taxon</th>
</tr>
<tr>
	<td class="TdField">Family</td>
	<td class="TdValue"><input name="family" type="text"  size="50"/></td>
</tr>	
<tr>
	<td class="TdField required">Genus</td>
	<td class="TdValue"><input name="genus" type="text"  size="50"/></td>
</tr>	
<tr>	
	<td class="TdField required">Species</td>
	<td class="TdValue"><input name="species" type="text" size="50" /></td>
</tr>
<tr>	
<th colspan=2>Population</th>
</tr>	

<tr>
	<td class="TdField">Geographic Location</td>
	<td class="TdValue"><input name="geographicLocation" type="text" size="50"/></td>
</tr>	

<tr>
	<td class="TdField required">Population</td>
	<td class="TdValue"><input name="population" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Year</td>
	<td class="TdValue"><input name="year" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Comments</td>
	<td class="TdValue"><input name="comments" type="text" size="50"/></td>
</tr>	
<tr>	
<th colspan=2>Reference</th>
</tr>
<tr>
	<td class="TdField required">Citation</td>
	<td class="TdValue"><input name="citation" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Full Reference</td>
	<td class="TdValue"><textarea name="fullreference" cols="50" rows="3"></textarea></td>
</tr>	
<tr>
	<td class="TdField">Part</td>
	<td class="TdValue"><input name="part" type="text"  size="50"/></td>
</tr>	

<tr>
	<td class="TdField required">Required field</td>
	<td class="TdValue"><input type="submit" value="Add"  /></td>
</tr>
</table>
</form>
	