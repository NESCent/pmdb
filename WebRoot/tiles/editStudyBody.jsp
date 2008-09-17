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
<h3>Edit Experiment Study</h3>
<jsp:include page="messageBody.jsp" />
<form name="Edit_Study_Form" action"saveexperimentstudy.go?id=<c:out value='${study.experimentStudyOid}' />" method="post" >
<table width=800>
<tr><td class="TdField">Type</td><td class="TdValue"><c:out value='${study.mmCvTerm.name }' />
<tr><th class="TdField">Study</td><td class="TdValue"><input type="text" name="studyName" value="<c:out value='${study.name }' />" /></td></tr>
<!--	
<select name="studyType">
	<c:forEach var="studyType" items="${study_types}">
		<c:choose>
			<c:when test="${studyType.name==study.mmCvTerm.name}">
				<option selected value="<c:out value="${studyType.name}" />"><c:out value="${studyType.name}" /></option>
			</c:when>
			<c:otherwise>
				<option value="<c:out value="${studyType.name}" />"><c:out value="${studyType.name}" /></option>
			</c:otherwise>
		</c:choose>	
	</c:forEach>
	</select> -->
	</td></tr>
<tr><td class="TdField">Citation</td><td class="TdValue"><input type="text" name="citation" value="<c:out value='${study.mmReferencePart.mmReference.citation }' />" /></td></tr>
<tr><td class="TdField">Full Reference</td><td class="TdValue"><input type="text" name="fullReference" value="<c:out value='${study.mmReferencePart.mmReference.fullReference}' />" /></td></tr>
<tr><td class="TdField">Part</td><td class="TdValue"><input type="text" name="referencePart" value="<c:out value='${study.mmReferencePart.name }' />" /></td></tr>
<br/><br/>
<jsp:include page="experimentStudyAttributesBody.jsp" />
<br/><br/>
<jsp:include page="measurementsBody.jsp" />
<br/><br/>
<jsp:include page="datarecordsBody.jsp" />
<br/><br/>
<table>
<tr>
<td><input type="submit" value="save"/></td>
</tr>
</table>
</form>
Note: empty attributes will be deleted if you save the data.



