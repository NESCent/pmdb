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
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssoc;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SaveDescriptorController implements Controller {
    private static Logger log;

    private static Logger log() {
	if (log == null) {
	    log = Logger.getLogger(SaveDescriptorController.class);
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
	    saveSpeciesReference(study, request, session);
	    saveAttributes(study, request, session);

	    session.flush();
	    tx.commit();

	    String sql = "FROM MmCvTerm term where term.namespace='species_attribute' order by term.name";
	    Query q = session.createQuery(sql);
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("study", study);
	    model.put("species_attributes", q.list());
	    model.put("tab", "species");
	    model.put("message", "Seccessfully updated.");
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

    @SuppressWarnings("unchecked")
    public void saveAttributes(MmMatingSystemStudy study,
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
			    MmSpeciesAttrCvtermAssoc assoc = new MmSpeciesAttrCvtermAssoc();
			    assoc.setMmCvTerm(term);
			    assoc.setMmMatingSystemStudy(study);
			    assoc.setValue(attrValue);
			    term.getMmSpeciesAttrCvtermAssocs().add(assoc);
			    study.getMmSpeciesAttrCvtermAssocs().add(assoc);
			    sess.save(assoc);
			} else {
			    log().error(
				    "failed to find the cvterm for species attribute: "
					    + attr);
			    throw new IllegalArgumentException(
				    "failed to find the cvterm for species attribute: "
					    + attr);
			}
		    }
		} else {

		    MmSpeciesAttrCvtermAssoc assoc = null;
		    for (Object o : study.getMmSpeciesAttrCvtermAssocs()) {
			MmSpeciesAttrCvtermAssoc as = (MmSpeciesAttrCvtermAssoc) o;
			if (as.getMsacaOid().equals(Integer.valueOf(aid))) {
			    assoc = as;
			    break;
			}
		    }
		    if (assoc == null) {
			log().error(
				"failed to find the MmSpeciesAttrCvtermAssoc with the id: "
					+ aid);
			throw new IllegalArgumentException(
				"failed to find the MmSpeciesAttrCvtermAssoc with the id: "
					+ aid);
		    }
		    if (attrValue == null) {
			sess.delete(assoc);
			study.getMmSpeciesAttrCvtermAssocs().remove(assoc);
		    } else {
			assoc.setValue(attrValue);
			sess.update(assoc);
		    }
		}

	    }

	}

    }

    public void saveSpeciesReference(MmMatingSystemStudy study,
	    HttpServletRequest request, Session sess) {
	String family = nullIfEmpty(request.getParameter("family"));
	String genus = nullIfEmpty(request.getParameter("genus"));
	String species = nullIfEmpty(request.getParameter("species"));
	String citation = nullIfEmpty(request.getParameter("citation"));
	String full = nullIfEmpty(request.getParameter("fullReference"));
	String part = nullIfEmpty(request.getParameter("referencePart"));
	String latitude = nullIfEmpty(request.getParameter("latitude"));

	if (genus == null) {
	    log().error("no genus specified");
	    throw new IllegalArgumentException("no genus specified");
	}
	if (species == null) {
	    log().error("no species specified");
	    throw new IllegalArgumentException("no species specified");
	}
	if (citation == null) {
	    log().error("no citation specified");
	    throw new IllegalArgumentException("no citation specified");
	}

	// get or create species
	MmSpecies sp = study.getMmSpecies();

	if (sp == null) {
	    log().error(
		    "no species found for the mating system study: "
			    + study.getMatingSystemStudyOid());
	    throw new IllegalArgumentException(
		    "no species found for the mating system study: "
			    + study.getMatingSystemStudyOid());
	}

	sp.setFamily(family);
	sp.setGenus(genus);
	sp.setSpecies(species);
	study.setLatitude(latitude);

	MmReferencePart mpart = study.getMmReferencePart();
	if (mpart != null) {
	    mpart.setName(part);
	    MmReference ref = mpart.getMmReference();
	    if (ref != null) {
		ref.setCitation(citation);
		ref.setFullReference(full);
	    }
	}

	sess.update(study);
    }
}
