<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>
<%@ page import="org.nescent.mmdb.hibernate.HibernateSessionFactory" %>
<%
NoCache.nocache(response);
%>
<h3>Edit Species Descriptor</h3>
<jsp:include page="messageBody.jsp" />
<script language='javascript'>

var attrs=new Array();
var attrs_desc=new Array();
<c:forEach var="attr" items="${species_attributes}">
attrs[attrs.length]="<c:out value="${attr.name}" />";
attrs_desc[attrs_desc.length]="<c:out value="${attr.description}" />";
</c:forEach>

function attributeNameChanged(id){
    var attrId="attr_"+id;
    var attrValueId="attrValue_"+id;
    
    var elAttr=document.getElementById(attrId);
    var attrName=elAttr.value;
    
    var elAttrValue=document.getElementById(attrValueId);
    var attrValue=elAttrValue.value;
    
    var el=createElement(attrValueId,attrName,attrValue);
    var papa=elAttrValue.parentNode;
    papa.replaceChild(el,elAttrValue)
    
}



</script>
<form action='savedescriptor.go' method='post' name='Edit_Descriptor_Form'>
<input type='hidden' name='id' value='<c:out value="${study.matingSystemStudyOid}" />' />	

<table width="800">
<tr>
	<tr>
		<td class='TdField'>Family</td>
		<td class='TdValue'><input  type="text" name="family" value="<c:out value="${study.mmSpecies.family}" />" /></td>
	</tr>	
        <tr>
        	<td class='TdField'>Genus</td>
        	<td class='TdValue'><input type="text" name="genus" value="<c:out value="${study.mmSpecies.genus}" />" /></td>
        </tr>	
        <tr>
        	<td class='TdField'>Species</td>
        	<td class='TdValue'><input type="text" name="species" value="<c:out value="${study.mmSpecies.species}" />" /></td>
        </tr>	
        <tr>
        	<td class='TdField'>Latitude</td>
        	<td class='TdValue'><input type="text" name="latitude" value="<c:out value="${study.latitude}" />" /></td>
        </tr>
	
      	<tr><td class="TdField">Citation</td><td class='TdValue'><input type="text"  size="65"  name="citation" value="<c:out value="${study.mmReferencePart.mmReference.citation}" />" /></td></tr>
      	<tr><td class="TdField">Full Reference</td><td class='TdValue'><textarea rows=5 cols=65 name="fullReference"><c:out value="${study.mmReferencePart.mmReference.fullReference}" /></textarea></td></tr>
      	<tr><td class="TdField">Reference Part</td><td class='TdValue'><input type="text"  size="45" name="referencePart" value="<c:out value="${study.mmReferencePart.name}" />" /></td></tr>
      	</table>
      	<br/><br/>
      	<table width="800">
      	<tr><th colspan=2>Attribute (<c:out value="${fn:length(study.mmSpeciesAttrCvtermAssocs)}" />)</th></tr>
      	
      	<c:forEach var="cvAssoc" items="${study.mmSpeciesAttrCvtermAssocs}" varStatus="attrStatus">
      	<tr>
      		<td  class="TdField">
      			<select id="attr_<c:out value="${cvAssoc.msacaOid}" />" name="attr_<c:out value="${cvAssoc.msacaOid}" />" onchange="attributeNameChanged(<c:out value="${cvAssoc.msacaOid}" />)">
          			<c:forEach var="attr" items="${species_attributes}">
          				<c:choose>
          					<c:when test="${cvAssoc.mmCvTerm.name==attr.name}">
          						<option selected value="<c:out value="${attr.name}" />"><c:out value="${attr.name}" /></option>
          					</c:when>
          					<c:otherwise>
          						<option value="<c:out value="${attr.name}" />"><c:out value="${attr.name}" /></option>
          					</c:otherwise>
          				</c:choose>	
          			</c:forEach>
      			</select>
      			
      		</td>	
      		<td  class="TdValue">
      		<script language="javascript">
      			var attrValueId="attrValue_<c:out value="${cvAssoc.msacaOid}" />";
      	      	    	var attrName="<c:out value="${cvAssoc.mmCvTerm.name}" />";
      	      	    	var attrValue="<c:out value="${cvAssoc.value}" />";
      	      	    	var values=terms.get(attrName);
        	
                	if(values==null)
                	{
                		if(attrValue.length<30)
                		{
                			document.write("<input name='"+attrValueId+"' id='"+attrValueId+"' type='text' value='"+attrValue+"' />");
                		}
                		else
                		{
            				document.write("<textarea cols='65' rows='5' name='"+attrValueId+"' id='"+attrValueId+"' type='text'>"+attrValue+"</textarea>");
                		}
                		
                	}
                	else
                	{
                		document.write("<select name='"+attrValueId+"' id='"+attrValueId+"'>");
                		
                		for(var i=0;i<values.length;i++)
                		{
                			var selected=false;
                			
                			if(values[i]==attrValue)
                				selected=true;
                			if(selected)	
                			    document.write("<option selected value='"+values[i]+"'>"+ values[i]+"</option>");
                			else
                			    document.write("<option value='"+values[i]+"'>"+ values[i]+"</option>");
                		}
                		document.write("<option value=''></option>");
                		document.write("</select>");
                	}
      	    </script>
      		</td>
	</tr>
	</c:forEach>
	<tr>
		<td class="TdField">
			<select name="attr_newattr" id="attr_newattr" onchange="attributeNameChanged('newattr')">
  			<c:forEach var="attr" items="${species_attributes}">
  				<option value="<c:out value="${attr.name}" />"><c:out value="${attr.name}" /></option>
  			</c:forEach>
			</select>
		</td>	
		<td  class="TdValue"><input type="text" name="attrValue_newattr" id="attrValue_newattr" value="" /> (new)</td>
	</tr>

 </table>
<br/><br/><br/>
<table>
<tr>
	<td><input type="submit" value="save"/></td>
</tr>
</table>
</form>
Note: empty attributes will be deleted if you save the data.

<%
	HibernateSessionFactory.getSession().close();
%>


