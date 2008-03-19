package org.nescent.mmdb.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EditExperimentStudyController implements Controller {

    private static Logger log;

    private static Logger log() {
	if (log == null) {
	    log = Logger.getLogger(EditExperimentStudyController.class);
	}
	return log;
    }

    @SuppressWarnings("unchecked")
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
	    MmExperimentStudy study = (MmExperimentStudy) session.get(
		    MmExperimentStudy.class, Integer.valueOf(id));
	    if (study == null) {
		log().error(
			"failed to retrieve the experiment study with id: "
				+ id);
		throw new IllegalArgumentException(
			"failed to retrieve the experiment study with id: "
				+ id);
	    }

	    String sql = "FROM MmCvTerm term where term.namespace='inbreed_exp_attribute' order by term.name";
	    Query q = session.createQuery(sql);

	    List inbreedingAttrs = q.list();

	    sql = "FROM MmCvTerm term where term.namespace='pollination_attribute' order by term.name";
	    q = session.createQuery(sql);
	    List pollinationAttrs = q.list();

	    sql = "FROM MmCvTerm term where term.namespace='outcrossing_attribute' order by term.name";
	    q = session.createQuery(sql);
	    List outcrossingAttrs = q.list();

	    sql = "FROM MmCvTerm term where term.namespace='study type ontology' and term.name<>'species attributes study' order by term.name";
	    q = session.createQuery(sql);
	    List studyTypes = q.list();

	    sql = "FROM MmCvTerm term where term.namespace='stage_attribute' order by term.name";
	    q = session.createQuery(sql);
	    List stages = q.list();
	    Map<String, Object> model = new HashMap<String, Object>();
	    if (study.getMmCvTerm().getName().equals(
		    "inbreeding depression study")) {
		model.put("study_attributes", inbreedingAttrs);
	    } else if (study.getMmCvTerm().getName().equals(
		    "experimental pollination study")) {
		model.put("study_attributes", pollinationAttrs);
	    } else if (study.getMmCvTerm().getName().equals(
		    "outcrossing database study")) {
		model.put("study_attributes", outcrossingAttrs);
	    }

	    model.put("measurement_attributes", pollinationAttrs);
	    model.put("stages", stages);
	    model.put("study_types", studyTypes);
	    model.put("study", study);

	    tx.commit();
	    return new ModelAndView("editStudy", model);
	} catch (NumberFormatException nfe) {
	    log().error("invalid number: " + id, nfe);
	    throw new IllegalArgumentException("invalid number: " + id, nfe);
	} catch (HibernateException he) {
	    log().error("failed to edit the experiment study with id: " + id,
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
