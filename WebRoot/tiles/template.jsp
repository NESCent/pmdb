<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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

<center>
<table  border="0" cellpadding="0" cellspacing="0" width="800" bordercolor="#000000" >
<tr>
<td colspan="2" width="100%" valign="top"><div class="topWindow"><tiles:insert attribute="header"/></div></td>
</tr>
<tr>
<td width="160" valign="top" align="left"><div class="menuWindow"><tiles:insert attribute="menu"/></div></td>
<td width="640" valign="top" align="left"><div class="mainWindow"><tiles:insert attribute="body"/></div></td>
</tr>
<tr>
<td colspan="2" width="100%"  valign="top"><div class="bottomWindow"><tiles:insert attribute="bottom"/></div></td>
</tr>
</table>
</center>

</body>

</html>