<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<title><tiles:getAsString name="title" ignore="true"/></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	 
<link rel="stylesheet" type="text/css" href="/mmdb/css/mmdb.css" />

<script type="text/javascript" src="/mmdb/jsp/tabview.js"></script>
<script type="text/javascript" src="/mmdb/jsp/fields_def.js"></script>
<script type="text/javascript" src="/mmdb/jsp/editnow.js"></script>

</head>

<body>

<table border="0" cellpadding="0" cellspacing="0" class="outerTable" >
	<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0" class="innerTable">
				<tr>
					<td colspan=3 class="tdHead" ><tiles:insert attribute="header"/></td>
				</tr>
				<tr>
					<td class="tdMenu"><tiles:insert attribute="menu" /></td>
					<td class="tdCorner"><img src="/mmdb/images/menucorner.jpg" /></td>
					<td class="tdMain"><tiles:insert attribute="body"/>
				</tr>	
			</table>
		</td>
	</tr>	
</table>
</body>

</html>