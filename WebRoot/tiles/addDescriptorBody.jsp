<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>
<script language="javascript">
function checkData()
{
	var msg="";
	
	var form=document.Add_Descriptor_Form;

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
	
	if(form.citation.value==null ||form.citation.value.replace(/^\s+|\s+$/g, '')=="")
	{
		alert("Citation can not be empty.")
		return false;
	}	
	
	return true;
}

</script>
<h3>Add Species Descriptor</h3>
<form action="addspeciesdescriptor.go" method="post" name="Add_Descriptor_Form" onSubmit="return checkData();">
<%
NoCache.nocache(response);
%>
<table>
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
<th colspan=2>Latitude</th>
</tr>	
<tr>
	<td class="TdField">Latitude</td>
	<td class="TdValue"><input name="latitude" type="text" size="50"/></td>
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
	<td class="TdField"></td>
	<td class="TdValue"><input type="submit" value="Add"  /></td>
</tr>
</table>
</form>
	