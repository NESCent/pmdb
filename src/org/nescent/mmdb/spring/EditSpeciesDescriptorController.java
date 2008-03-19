package org.nescent.mmdb.spring;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EditSpeciesDescriptorController implements Controller {
    private static Logger log;

    private static Logger log() {
	if (log == null) {
	    log = Logger.getLogger(EditSpeciesDescriptorController.class);
	}
	return log;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
	    HttpServletResponse response) {
	String id = nullIfEmpty(request.getParameter("id"));
	if (id == null) {
	    log().error("no id specified");
	    throw new IllegalArgumentException("no id specified");
	}

	Session session = HibernateSessionFactory.getSession();
	Transaction tx = session.beginTransaction();

	try {
	    MmMatingSystemStudy study = (MmMatingSystemStudy) session.get(
		    MmMatingSystemStudy.class, Integer.valueOf(id));
	    if (study == null) {
		log().error(
			"failed to retrieve the species descriptor with id: "
				+ id);
		throw new IllegalArgumentException(
			"failed to retrieve the species descriptor with id: "
				+ id);
	    }
	    String sql = "FROM MmCvTerm term where term.namespace='species_attribute' order by term.name";
	    Query q = session.createQuery(sql);
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("study", study);
	    model.put("species_attributes", q.list());
	    tx.commit();
	    return new ModelAndView("editSpeciesDescriptor", model);
	} catch (NumberFormatException nfe) {
	    log().error("invalid number: " + id, nfe);
	    throw new IllegalArgumentException("invalid number: " + id, nfe);
	} catch (HibernateException he) {
	    log().error("failed to edit the species descriptor with id: " + id,
		    he);
	    throw he;

	} finally {
	    if (!tx.wasCommitted())
		tx.rollback();
	}
    }

    private String nullIfEmpty(String s) {
	return (s == null || s.trim().equals("")) ? null : s;
    }
}
