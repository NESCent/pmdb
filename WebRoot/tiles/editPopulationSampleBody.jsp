<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>
<%@ page import="org.nescent.mmdb.hibernate.HibernateSessionFactory" %>
<h3>Edit Population Sample</h3>
<jsp:include page="messageBody.jsp" />
<Form name="View_Population_Form" method="post" action="savepopulationsample.go">
<input type="hidden" name="id" value="<c:out value="${population.populationSampleOid}" />" />
<table width=800>
<tr><td class='TdField'>GeoLocation<td class='TdValue'>
	<input type="text" name="geoLocation" value="<c:out value='${population.geographicLocation}' />"  />
</td></tr>	
<tr><td class='TdField'>Population<td class='TdValue'>
	<input type="text" name="population" value="<c:out value='${population.population}' />" />
</td></tr>	

<tr><td class='TdField'>Year<td class='TdValue'>
	<input type="text" name="year" value="<c:out value='${population.year}' />" />
</td></tr>	
<tr><td class='TdField'>Comments<td class='TdValue'>
	<input type="text" name="comments" value="<c:out value='${population.comments}' />" />
</td></tr>
<table width="800">
	<tr><th colspan=2>Attribute (<c:out value="${fn:length(population.mmPopSampleAttrCvtermAssocs)}" />)</th></tr>
	
	<c:forEach var="cvAssoc" items="${population.mmPopSampleAttrCvtermAssocs}" varStatus="attrStatus">
	<tr>
		<td  class="TdField">
			<select id="attr_<c:out value="${cvAssoc.mpsacaOid}" />" name="attr_<c:out value="${cvAssoc.mpsacaOid}" />" onchange="attributeNameChanged(<c:out value="${cvAssoc.mpsacaOid}" />)">
  			<c:forEach var="attr" items="${population_attributes}">
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
			var attrValueId="attrValue_<c:out value="${cvAssoc.mpsacaOid}" />";
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
			<c:forEach var="attr" items="${population_attributes}">
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
