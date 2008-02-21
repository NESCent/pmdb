<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%
NoCache.nocache(response);
String message=(String)request.getSession().getAttribute("Message");
%>
<h2>Population: <c:out value='${population.population}' /></h2>
<div class="TabView" id="TabView">
	<div class="Tabs">
		<a>Species</a>
		<a>Population</a>
		<a>Study</a>
	</div>
	<div class="Pages">	
		<div class='Page'>
			<div class='Pad'>	
				<jsp:include page="speciesDescriptorBoxBody.jsp" />	
			</div>
		</div>
		<div class='Page'>
			<div class='Pad'>	
				<jsp:include page="populationSampleBody.jsp" />	
			</div>
		</div>
		<div class='Page'>
			<div class='Pad'>	
				<jsp:include page="studyBody.jsp" />	
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	tabview_initialize('TabView');
</script>	





