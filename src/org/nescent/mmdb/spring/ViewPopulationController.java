package org.nescent.mmdb.spring;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmPopulationSampleDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ViewPopulationController implements Controller {
    private static Logger log;

    private static Logger log() {
	if (log == null) {
	    log = Logger.getLogger(ViewPopulationController.class);
	}
	return log;
    }

    @SuppressWarnings("unchecked")
    public ModelAndView handleRequest(HttpServletRequest request,
	    HttpServletResponse response) {
	String id = request.getParameter("id");
	String tab = request.getParameter("tab");
	if (id == null || id.trim().equals("")) {
	    log().error("no id specified.");
	    throw new IllegalArgumentException("no id specified.");
	}
	Session session = HibernateSessionFactory.getSession();
	Transaction tx = session.beginTransaction();

	try {
	    MmPopulationSampleDAO popDao = new MmPopulationSampleDAO();
	    MmPopulationSample pop = popDao.findById(Integer.valueOf(id));
	    if (pop == null) {
		log().error("failed to retrieve the population with id: " + id);
		throw new IllegalArgumentException(
			"failed to retrieve the population with id: " + id);
	    }

	    Map model = new HashMap();
	    model.put("population", pop);
	    if (tab != null)
		model.put("tab", tab);
	    else
		model.put("tab", "study");

	    tx.commit();
	    return new ModelAndView("population", model);
	} catch (HibernateException he) {
	    log().error("failed to view the population.", he);
	    throw he;
	}

    }

}
