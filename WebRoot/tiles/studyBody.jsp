<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%
NoCache.nocache(response);
%>
<jsp:useBean id="studysorter" class="org.nescent.mmdb.util.UtilSort" />
<jsp:setProperty name="studysorter" property="collection" value="${population.mmExperimentStudies}" />
<c:forEach var="exp" items="${studysorter.sortedCollection}">
   <table width="800">
        <tr><th colspan=2>
      		<table width="800">
      		<tr><td class="TbCaption" width="650"><c:out value="${exp.name}" /></td>
      		<td  class="TbCaption"><div style="width:250px;text-align:right;"><span class="TdAction"><a href="editstudy.go?id=<c:out value="${exp.experimentStudyOid}" />">Edit</a>&nbsp;&nbsp;&nbsp;
      		<a href="javascript: deletestudy(<c:out value="${exp.experimentStudyOid}" />)">Delete</a>
      		</span></div>
      		</td>
      		</tr>
      		</table>
      	</th></tr>
      <tr><td class="TdField">Type</td><td class="TdValue"><c:out value="${exp.mmCvTerm.name}" /></td></tr>
      <tr><td class="TdField">Citation</td><td class="TdValue"><c:out value="${exp.mmReferencePart.mmReference.citation}" /></td></tr>
      <tr><td class="TdField">Full Reference</td><td class="TdValue"><c:out value="${exp.mmReferencePart.mmReference.fullReference}" /></td></tr>
      <tr><td class="TdField">Part</td><td class="TdValue"><c:out value="${exp.mmReferencePart.name}" /></td></tr>
      
      <jsp:useBean id="sorter1" class="org.nescent.mmdb.util.UtilSort" />
      <tr><td class="TdField" />
	<th>Attributes (<c:out value="${fn:length(exp.mmExperimentStudyAttrCvtermAssocs)}" />)</th>
	
	</tr>
		<jsp:setProperty name="sorter1" property="collection" value="${exp.mmExperimentStudyAttrCvtermAssocs}" />
      		<c:forEach var="cvAssoc" items="${sorter1.sortedCollection}" varStatus="expAttrStatus">
      			<tr>
      				<td class="TdField"><c:out value="${cvAssoc.mmCvTerm.name}" /></td>
      				<td class="TdValue"><c:out value="${cvAssoc.value}" /></td>
      			</tr>
      		</c:forEach>
     
      		<jsp:useBean id="sorter2" class="org.nescent.mmdb.util.UtilSort" />
      <tr><td class="TdField" />
	<th>Measurements (<c:out value="${fn:length(exp.mmExperimentValues)}" />)</th>
	</tr>
	<jsp:setProperty name="sorter2" property="collection" value="${exp.mmExperimentValues}" />
    		<c:forEach var="valueAssoc" items="${sorter2.sortedCollection}" varStatus="expMeasStatus">
    			<tr>
    				<td class="TdField"><c:out value="${valueAssoc.mmCvTerm.name}" /></td>
    				<td class="TdValue"><c:out value="${valueAssoc.value}" /></td>
    			</tr>
    		</c:forEach>
  </table>
  <br/><br/>
  </c:forEach>