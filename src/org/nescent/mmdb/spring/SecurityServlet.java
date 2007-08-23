/**
 * SecurityServlet: class to check the permission for each request
 */
package org.nescent.mmdb.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.nescent.mmdb.aa.PermissionManager;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author xianhua
 *
 */
public class SecurityServlet extends DispatcherServlet{

	
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.DispatcherServlet#doDispatch(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doDispatch(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		
		super.doDispatch(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.DispatcherServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doService(HttpServletRequest arg0, HttpServletResponse arg1)  throws Exception {
		
		
		
		
		try
		{
			String uri=arg0.getRequestURI();
			if(uri.indexOf("login.go")>-1)
				super.doService(arg0, arg1);
			else if(uri.indexOf("register.go")>-1)
				super.doService(arg0, arg1);
			else if(uri.indexOf("cv.go")>-1)
				super.doService(arg0, arg1);
			else if(uri.indexOf("forgotpassword.go")>-1)
				super.doService(arg0, arg1);
			
				

			else
			{
				
				PermissionManager manager=(PermissionManager)arg0.getSession().getAttribute("permission_manager");
				if(manager==null)
				{
					arg0.getSession().setAttribute("Message","You have not logged in. Login please!");
					arg1.sendRedirect("/mmdb/jsp/login.jsp");
				}
				else
				{
					boolean isAdmin=manager.containsRole("Administrator");
					if(isAdmin)
						super.doService(arg0, arg1);
					else
						throw new Exception("Sorry! you do not have the authority.");
				}
			}
		}
		catch(HibernateException e)
		{
			
			
			arg0.getSession().setAttribute("Message", "Error: "+ e.getMessage() + "  - Database error occured");
			System.gc();
			
			arg1.sendRedirect("/mmdb/jsp/error.jsp");
		}
		catch(Exception e)
		{
			
			arg0.getSession().setAttribute("Message", "Error: "+e.getMessage());
			arg1.sendRedirect("/mmdb/jsp/error.jsp");
			
			
		}
		finally
		{

		}
	}


	
}
		
		
					
