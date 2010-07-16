<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%
NoCache.nocache(response);
%>
<script languge="javascript">
function deletespeciesdescriptor(id){
    var message="Do you really want to delete the species descriptor?";
    var message1="Please confirm again. Do you really want to delete the species descriptor?";
    if(confirm(message)){
	if(confirm(message1)){
	    var url="deletespeciesdescriptor.go?id"+id;
	    window.location.href=url;
	}
    }
}
</script>
<h1><c:out value='${population.mmSpecies.genus}' /> <c:out value='${population.mmSpecies.species}' /> (<c:out value='${population.mmSpecies.family}' />)</h1>           
<h2>Species Descriptors</h2>
<c:forEach var="study" items="${population.mmSpecies.mmMatingSystemStudies}" varStatus="status">
	<table width=800>
        <tr><th colspan=2>
      		<table width="100%"><tr><td class="TbCaption" width="650"><c:out value="${study.mmReferencePart.mmReference.citation}" /></td>
      		<td><div style="width:100%;text-align:right;"><span class="TdAction"><a href="editspeciesdescriptor.go?id=<c:out value='${study.matingSystemStudyOid}' />">Edit</a>&nbsp;&nbsp;&nbsp;
                <a href="javascript: deletespeciesdescriptor(<c:out value='${study.matingSystemStudyOid}' />)">Delete</a>
      		</span></div>
      		</td>
      		</tr>
      		</table>
      	</th></tr>
      	
      	<tr><td class="TdField">Latitude</td><td class="TdValue"><c:out value="${study.latitude}" /></td></tr>
      	<tr><td class="TdField">Citation</td><td class="TdValue"><c:out value="${study.mmReferencePart.mmReference.citation}" /></td></tr>
      	<tr><td class="TdField">Full Reference</td><td class="TdValue"><c:out value="${study.mmReferencePart.mmReference.fullReference}" /></td></tr>
      	<tr><td class="TdField">Reference Part</td><td class="TdValue"><c:out value="${study.mmReferencePart.name}" /></td></tr>
      	<tr><td class="TdField" /><th>Attribute (<c:out value="${fn:length(study.mmSpeciesAttrCvtermAssocs)}" />)</th></tr>
      	
      	<c:forEach var="cvAssoc" items="${study.mmSpeciesAttrCvtermAssocs}" varStatus="attrStatus">
    	<c:if test="${cvAssoc.mmCvTerm!=null}">  	
      	<tr>
      		<td  class="TdField"><c:out value="${cvAssoc.mmCvTerm.name}" /></td>
      		<td  class="TdValue"><c:out value="${cvAssoc.value}" /></td>
		</tr>
		</c:if>
	</c:forEach>
	
	    <!-- end of attributes -->
	     </table>
	    <br/><br/> 
 </c:forEach>





