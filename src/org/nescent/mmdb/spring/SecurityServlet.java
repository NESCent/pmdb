/**
 * SecurityServlet: class to check the permission for each request
 */
package org.nescent.mmdb.spring;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.nescent.mmdb.aa.PermissionManager;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author xianhua
 * 
 */
public class SecurityServlet extends DispatcherServlet {
    private static Logger logger = null;

    private Logger log() {
	if (logger == null)
	    logger = Logger.getLogger(SecurityServlet.class);

	return logger;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.DispatcherServlet#doDispatch(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doDispatch(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	super.doDispatch(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.DispatcherServlet#doService(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doService(HttpServletRequest request,
	    HttpServletResponse response) {

	try {
	    String uri = request.getRequestURI();
	    if (uri.indexOf("login.go") > -1)
		super.doService(request, response);
	    else if (uri.indexOf("register.go") > -1)
		super.doService(request, response);
	    else if (uri.indexOf("cv.go") > -1)
		super.doService(request, response);
	    else if (uri.indexOf("forgotpassword.go") > -1)
		super.doService(request, response);

	    else {

		PermissionManager manager = (PermissionManager) request
			.getSession().getAttribute("permission_manager");
		if (manager == null) {
		    request.getSession().setAttribute("Message",
			    "You have not logged in. Login please!");
		    response.sendRedirect("/pmdb/jsp/login.jsp");
		} else {
		    boolean isAdmin = manager.containsRole("Administrator");
		    if (isAdmin)
			super.doService(request, response);
		    else
			throw new Exception(
				"Sorry! you do not have the authority.");
		}
	    }
	} catch (HibernateException e) {

	    request.getSession().setAttribute("Message",
		    "Error: " + e.getMessage() + "  - Database error occured");
	    System.gc();
	    try {
		response.sendRedirect("/pmdb/jsp/error.jsp");
	    } catch (IOException ioe) {
		log().error("failed to redirect to the error.jsp page.", ioe);
		throw new RuntimeException(
			"failed to redirect to the error.jsp page.", ioe);
	    }
	} catch (Exception e) {
	    log().error("failed to do the service.", e);
	    throw new RuntimeException("failed to do the service.", e);
	}
    }

}
