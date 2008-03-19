package org.nescent.mmdb.spring;

import java.util.Enumeration;
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
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmCvTermDAO;
import org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SavePopulationSampleController implements Controller {
    private static Logger log;

    private static Logger log() {
	if (log == null) {
	    log = Logger.getLogger(SavePopulationSampleController.class);
	}
	return log;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
	    HttpServletResponse response) {

	String id = request.getParameter("id");
	if (id == null) {
	    log().error("no id specified");
	    throw new IllegalArgumentException("no id specified");
	}

	Session session = HibernateSessionFactory.getSession();
	Transaction tx = session.beginTransaction();
	try {
	    MmPopulationSample sample = (MmPopulationSample) session.get(
		    MmPopulationSample.class, Integer.valueOf(id));
	    if (sample == null) {
		log().error(
			"failed to retrieve the population sample with id: "
				+ id);
		throw new IllegalArgumentException(
			"failed to retrieve the population sample with id: "
				+ id);
	    }

	    savePopulationReference(sample, request, session);
	    saveAttributes(sample, request, session);

	    session.flush();
	    tx.commit();

	    String sql = "FROM MmCvTerm term where term.namespace='exp_pop_attribute' order by term.name";
	    Query q = session.createQuery(sql);
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("population", sample);
	    model.put("population_attributes", q.list());
	    model.put("message", "Seccessfully updated.");

	    return new ModelAndView("editPopulationSample", model);
	} catch (NumberFormatException nfe) {
	    log().error("invalid number: " + id, nfe);
	    throw new IllegalArgumentException("invalid number: " + id, nfe);
	} catch (HibernateException he) {
	    log().error("failed to save the population sample with id: " + id,
		    he);
	    throw he;

	} finally {
	    if (!tx.wasCommitted())
		tx.rollback();
	}
    }

    @SuppressWarnings("unchecked")
    public void saveAttributes(MmPopulationSample sample,
	    HttpServletRequest request, Session sess) {

	for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
	    String cvid = (String) en.nextElement();

	    if (cvid.indexOf("attr_") != -1) {
		String ss[] = cvid.split("_");
		if (ss.length != 2) {
		    log().error("invalid attribute id: " + cvid);
		}
		String aid = ss[1];
		String attr = nullIfEmpty(request.getParameter(cvid));
		String attrValue = nullIfEmpty(request
			.getParameter("attrValue_" + aid));

		if (aid.equals("newattr")) {
		    if (attrValue != null && attr != null) {
			MmCvTermDAO termDao = new MmCvTermDAO();
			List terms = termDao.findByName(attr);
			if (terms.size() > 0) {
			    MmCvTerm term = (MmCvTerm) terms.toArray()[0];
			    MmPopSampleAttrCvtermAssoc assoc = new MmPopSampleAttrCvtermAssoc();
			    assoc.setMmCvTerm(term);
			    assoc.setMmPopulationSample(sample);
			    assoc.setValue(attrValue);
			    term.getMmPopSampleAttrCvtermAssocs().add(assoc);
			    sample.getMmPopSampleAttrCvtermAssocs().add(assoc);
			    sess.save(assoc);
			} else {
			    log().error(
				    "failed to find the cvterm for population sample attribute: "
					    + attr);
			    throw new IllegalArgumentException(
				    "failed to find the cvterm for population sample attribute: "
					    + attr);
			}
		    }
		} else {

		    MmPopSampleAttrCvtermAssoc assoc = null;
		    for (Object o : sample.getMmPopSampleAttrCvtermAssocs()) {
			MmPopSampleAttrCvtermAssoc as = (MmPopSampleAttrCvtermAssoc) o;
			if (as.getMpsacaOid().equals(Integer.valueOf(aid))) {
			    assoc = as;
			    break;
			}
		    }
		    if (assoc == null) {
			log().error(
				"failed to find the MmPopSampleAttrCvtermAssoc with the id: "
					+ aid);
			throw new IllegalArgumentException(
				"failed to find the MmPopSampleAttrCvtermAssoc with the id: "
					+ aid);
		    }
		    if (attrValue == null) {
			sess.delete(assoc);
			sample.getMmPopSampleAttrCvtermAssocs().remove(assoc);
		    } else {
			assoc.setValue(attrValue);
			sess.update(assoc);
		    }
		}

	    }

	}

    }

    public void savePopulationReference(MmPopulationSample sample,
	    HttpServletRequest request, Session sess) {
	String geoLocation = nullIfEmpty(request.getParameter("geoLocation"));
	String population = nullIfEmpty(request.getParameter("population"));
	String year = nullIfEmpty(request.getParameter("year"));
	String comments = nullIfEmpty(request.getParameter("comments"));

	sample.setComments(comments);
	sample.setGeographicLocation(geoLocation);
	sample.setPopulation(population);
	sample.setYear(year);

	sess.update(sample);
    }

    private String nullIfEmpty(String s) {
	return (s == null || s.trim().equals("")) ? null : s;
    }
}
