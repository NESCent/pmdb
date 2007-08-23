package org.nescent.mmdb.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.nescent.mmdb.aa.PermissionManager;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.util.Login;

import java.util.List;


import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


public class LoginController extends SimpleFormController {

	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, BindException arg3) throws Exception {
		// TODO Auto-generated method stub
		
		Login login=(Login)arg2;
					  
		
//		Validate Login information
		boolean success = login.validateLogin();

		
		if (success)
		{

			PermissionManager pm = 	login.setPermissions();
			
			if (pm != null)
			{
				arg0.getSession().setAttribute("permission_manager",pm);
				arg0.getSession().setAttribute("personOID",new Integer(login.getPersonOID()));
				arg0.getSession().setAttribute("username",login.getUserName());
				
				HibernateSessionFactory.closeSession();
			
				return new ModelAndView("welcome", "username", "");
							
			}
			else 
			{
				return new ModelAndView("login", "essage", " Sorry - No Permissions to access mmdb - Contact System Administrator!");
			}
		}
	
		else
		{
				return new ModelAndView("login","message", "Sorry! Failed to log in. Please try again.");
	
		}

		
	}

	

}
