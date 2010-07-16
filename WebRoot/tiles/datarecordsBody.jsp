<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%
NoCache.nocache(response);
%>

<table width="800">
      	<tr><th colspan=7>Data Record (<c:out value="${fn:length(study.mmDataRecords)}" />)</th></tr>
      	<tr><th>Stage</th><th>Name</th><th>Outcrossing</th><th>Outcrossing SD</th><th>Selfing</th><th>Selfing SD</th><th></th></tr>
      	<jsp:useBean id="sorter" scope="page" class="org.nescent.mmdb.util.UtilSort" />
      	<jsp:setProperty name="sorter" property="collection" value="${study.mmDataRecords}" />
      	
      	<c:forEach var="cvAssoc" items="${sorter.sortedCollection}" varStatus="attrStatus">
      	<tr>
      		<td class="TdField">
      			<select name="stage_<c:out value="${cvAssoc.dataRecordOid}" />">
          			<c:forEach var="attr" items="${stages}">
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
      		<td class="TdValue"><input name="name_<c:out value="${cvAssoc.dataRecordOid}" />" value="<c:out value='${cvAssoc.name}' />" /></td>
      		<td class="TdValue"><input name="outcrossing_<c:out value="${cvAssoc.dataRecordOid}" />" value="<c:out value='${cvAssoc.outCrossingValue}' />" /></td>
      		<td class="TdValue"><input name="outcrossingsd_<c:out value="${cvAssoc.dataRecordOid}" />" value="<c:out value='${cvAssoc.outCrossingStdDev}' />" /></td>
      		<td class="TdValue"><input name="selfing_<c:out value="${cvAssoc.dataRecordOid}" />" value="<c:out value='${cvAssoc.selfingValue}' />" /></td>
      		<td class="TdValue"><input name="selfingsd_<c:out value="${cvAssoc.dataRecordOid}" />" value="<c:out value='${cvAssoc.selfingStdDev}' />" /></td>
      		<td class="TdValue"></td>
	</tr>
	</c:forEach>
	<tr>
	<td  class="TdField">
		<select name="stage_new">
			<c:forEach var="attr" items="${stages}">
				<option value="<c:out value="${attr.name}" />"><c:out value="${attr.name}" /></option>
			</c:forEach>
		</select>
		
	</td>	
	<td  class="TdValue"><input name="name_new" value="<c:out value='${cvAssoc.name}' />" /></td>
	<td class="TdValue"><input name="outcrossing_new" value="<c:out value='${cvAssoc.outCrossingValue}' />" /></td>
	<td class="TdValue"><input name="outcrossingsd_new" value="<c:out value='${cvAssoc.outCrossingStdDev}' />" /></td>
	<td class="TdValue"><input name="selfing_new" value="<c:out value='${cvAssoc.selfingValue}' />" /></td>
	<td class="TdValue"><input name="selfingsd_new" value="<c:out value='${cvAssoc.selfingStdDev}' />" /></td>
	<td class="TdValue">(new)</td>
	</tr>

 </table>