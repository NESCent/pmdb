<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%
NoCache.nocache(response);
%>

<table width=800>
<tr><th colspan=2>
	<table width="800">
	<tr><td class="TbCaption" width="650"><c:out value="${population.population}" /></td>
	<td  class="TbCaption"><div style="width:250px;text-align:right;"><span class="TdAction"><a href='editpopulationsample(<c:out value="${population.populationSampleOid}" />)'>Edit</a>&nbsp;&nbsp;&nbsp;
	<a href='javascript:deletePopulation(<c:out value="${population.populationSampleOid}" />)'>Delete</a>
	</span></div>
	</td>
	</tr>
	</table>
</th></tr>
<tr><td class='TdField'>GeoLocation<td class='TdValue'><c:out value="${population.geographicLocation}" /></td></tr>    
<tr><td class='TdField'>Population<td class='TdValue'><c:out value="${population.population}" /></td></tr>    
<tr><td class='TdField'>Year<td class='TdValue'><c:out value="${population.year}" /></td></tr>
<tr><td class='TdField'>Comments<td class='TdValue'><c:out value="${population.comments}" /></td></tr>
<tr><td class='TdField' />
<th>Attributes (<c:out value="${fn:length(population.mmPopSampleAttrCvtermAssocs)}" />)</th>
</tr>
<jsp:useBean id="sorter3" class="org.nescent.mmdb.util.UtilSort" />
<jsp:setProperty name="sorter3" property="collection" value="${population.mmPopSampleAttrCvtermAssocs}" />
<c:forEach var="cvAssoc" items="${sorter3.sortedCollection}" varStatus="popuAttrStatus">
	<tr>
		<td  class='TdField'><c:out value="${cvAssoc.mmCvTerm.name}" /></td>
		<td  class='TdValue'><c:out value="${cvAssoc.value}" /></td>
	</tr>
</c:forEach>
</table>
<br/><br/>
