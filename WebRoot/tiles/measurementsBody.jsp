<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%
NoCache.nocache(response);
%>
<script language='javascript'>
var measurements=new Array();
var measurements_desc=new Array();
<c:forEach var="attr" items="${measurement_attributes}">
measurements[measurements.length]="<c:out value="${attr.name}" />";
measurements_desc[measurements_desc.length]="<c:out value="${attr.description}" />";
</c:forEach>

function measurementNameChanged(id){
    var attrId="attrm_"+id;
    var attrValueId="attrValuem_"+id;
    
    var elAttr=document.getElementById(attrId);
    var attrName=elAttr.value;
    
    var elAttrValue=document.getElementById(attrValueId);
    var attrValue=elAttrValue.value;
    
    var el=createElement(attrValueId,attrName,attrValue);
    var papa=elAttrValue.parentNode;
    papa.replaceChild(el,elAttrValue)
    
}

</script>

<table width="800">
      	<tr><th colspan=2>Measurements (<c:out value="${fn:length(study.mmExperimentValues)}" />)</th></tr>
      	<c:forEach var="cvAssoc" items="${study.mmExperimentValues}" varStatus="attrStatus">
      	<tr>
      		<td  class="TdField">
      			<select id="attrm_<c:out value="${cvAssoc.experimentValueOid}" />" name="attrm_<c:out value="${cvAssoc.experimentValueOid}" />" onchange="measurementNameChanged(<c:out value="${cvAssoc.experimentValueOid}" />)">
          			<c:forEach var="attr" items="${measurement_attributes}">
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
      			var attrValueId="attrValuem_<c:out value="${cvAssoc.experimentValueOid}" />";
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
			<select name="attrm_newattr" id="attrm_newattr" onchange="measurementNameChanged('newattr')">
  			<c:forEach var="attr" items="${measurement_attributes}">
  				<option value="<c:out value="${attr.name}" />"><c:out value="${attr.name}" /></option>
  			</c:forEach>
			</select>
		</td>	
		<td  class="TdValue"><input type="text" name="attrValuem_newattr" id="attrValuem_newattr" value="" /> (new)</td>
	</tr>

 </table>