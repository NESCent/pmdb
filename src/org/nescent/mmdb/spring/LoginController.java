package org.nescent.mmdb.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nescent.mmdb.aa.PermissionManager;
import org.nescent.mmdb.util.Login;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class LoginController extends SimpleFormController {

    protected ModelAndView onSubmit(HttpServletRequest arg0,
	    HttpServletResponse arg1, Object arg2, BindException arg3)
	    throws Exception {
	Login login = (Login) arg2;

	PermissionManager pm = login.login();

	if (pm != null) {
	    arg0.getSession().setAttribute("permission_manager", pm);
	    arg0.getSession().setAttribute("personOID",
		    new Integer(login.getPersonOID()));
	    arg0.getSession().setAttribute("username", login.getUserName());
	    return new ModelAndView("welcome", "username", "");
	} else {
	    return new ModelAndView("login", "message",
		    "Sorry! Failed to log in. Please try again.");
	}
    }

}
