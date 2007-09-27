
<%@page import="java.io.File" %>
<%@page import="java.io.FileInputStream" %>
<%@page import="org.nescent.mmdb.util.MmdbConfiguration" %>
<%
String version = "";
String built="";

MmdbConfiguration mmdbConfig=(MmdbConfiguration)request.getSession().getServletContext().getAttribute("mmdb_config");
if(mmdbConfig==null)
{
	mmdbConfig=new MmdbConfiguration();
	String configFile="config/mmdb.conf";
	try
	{
		String file=getServletContext().getRealPath(configFile);
			
		FileInputStream in=new FileInputStream(new File(file));
		mmdbConfig.load(in);
		this.getServletContext().setAttribute("mmdb_config", mmdbConfig);
	}
	catch(Exception e)
	{
		out.write("Error: "+e);
	}
}		
if(mmdbConfig!=null)
{
	if(mmdbConfig.getProperty("Version")!=null)
		version=mmdbConfig.getProperty("Version");
	if(mmdbConfig.getProperty("Deploy_Date")!=null)
		built=mmdbConfig.getProperty("Deploy_Date");
}
%>
<table border="0" cellpadding="0" cellspacing="0" width=100%>
	<tr>
		<td><img src="/pmdb/images/logo.jpg" /></td>
		<td>Version: <%=version %><br/>Built: <%=built %></td>
	</tr>
</table>
