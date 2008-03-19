<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%
NoCache.nocache(response);
%>

<h1><c:out value='${population.mmSpecies.genus}' /> <c:out value='${population.mmSpecies.species}' /> (<c:out value='${population.mmSpecies.family}' />)</h1>           
<h2>Species Descriptors</h2>
<table width=800>
	<tr><th>Latitude</th><th>Citation</th><th>Reference</th><th>Part</th><th>Action</th></tr>
	<c:forEach var="study" items="${population.mmSpecies.mmMatingSystemStudies}" varStatus="status">
            <c:set var="trclass" value="odd" />
            <c:choose>
            	<c:when test='${status.count%2==0}'>
            		<c:set var="trclass" value="even" />
            	</c:when>
            	<c:otherwise>
            		<c:set var="trclass" value="odd" />
            	</c:otherwise>
            </c:choose>
            <tr class="<c:out value="${trclass}" />">
                   <td><c:out value='${study.latitude}' /></td>
                   <td><c:out value='${study.mmReferencePart.mmReference.citation}' /></td>
                   <td><c:out value='${study.mmReferencePart.mmReference.fullReference}' /></td>
                   <td><c:out value='${study.mmReferencePart.name}' /></td>
                   <td class="TdAction"><a href="editspeciesdescriptor.go?id=<c:out value='${study.matingSystemStudyOid}' />">Edit</a><br/><br/>
                   <a href="javascript: deletespeciesdescriptor(<c:out value='${study.matingSystemStudyOid}' />)">Delete</a></td>
            </tr>
            <!-- start attributes -->
            <tr class="<c:out value="${trclass}" />">
            	<td style="background-color: #FFFFFF;"></td>
            	<td colspan=4><b>Species Attributes</b> (<c:out value="${fn:length(study.mmSpeciesAttrCvtermAssocs)}" />)</td>
            	
            </tr>
            <c:if test="${fn:length(study.mmSpeciesAttrCvtermAssocs)>0}">
            	<tr class="<c:out value="${trclass}" />">
            		<td style="background-color: #FFFFFF;"></td>
            		<td colspan=4>
            			<table>
            			<tr>
            				<th>Attribute</th>
            				<th>Value</th>
	                        </tr>
	                        <c:forEach var="cvAssoc" items="${study.mmSpeciesAttrCvtermAssocs}" varStatus="attrStatus">
	                                <c:choose>
                          			<c:when test='${attrStatus.count%2==0}'>
                          				<tr class="even">
                          			</c:when>
                          			<c:otherwise>
                          				<tr class="odd">
                          			</c:otherwise>
                          		</c:choose>     
	                                <td><c:out value="${cvAssoc.mmCvTerm.name}" /></td>
	                                <td><c:out value="${cvAssoc.value}" /></td>
	                                </tr>
	                        </c:forEach>
	                        </table>
	                </td>
	        </tr>
	     </c:if>
	     <!-- end of attributes -->
       </c:forEach>        
</table>




