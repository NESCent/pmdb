<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%
NoCache.nocache(response);
%>
<jsp:useBean id="speciessorter" scope="page" class="org.nescent.mmdb.util.UtilSort" />
<jsp:setProperty name="speciessorter" property="collection" value="${population.mmSpecies.mmMatingSystemStudies}" />
<h1><c:out value='${population.mmSpecies.genus}' /> <c:out value='${population.mmSpecies.species}' /> (<c:out value='${population.mmSpecies.family}' />)</h1>           
<h2>Species Descriptors</h2>
<c:forEach var="study" items="${speciessorter.sortedCollection}" varStatus="status">
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
      	<jsp:useBean id="sorter" scope="page" class="org.nescent.mmdb.util.UtilSort" />
      	<jsp:setProperty name="sorter" property="collection" value="${study.mmSpeciesAttrCvtermAssocs}" />

      	<c:forEach var="cvAssoc" items="${sorter.sortedCollection}" varStatus="attrStatus">
      	<tr>
      		<td  class="TdField"><c:out value="${cvAssoc.mmCvTerm.name}" /></td>
      		<td  class="TdValue"><c:out value="${cvAssoc.value}" /></td>
	
      		</tr>
	</c:forEach>
	
	    <!-- end of attributes -->
	     </table>
	    <br/><br/> 
 </c:forEach>





