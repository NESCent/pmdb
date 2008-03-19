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
import org.nescent.mmdb.hibernate.dao.MmDataRecord;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudyAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmExperimentValue;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SaveExperimentStudyController implements Controller {
    private static Logger log;

    private static Logger log() {
	if (log == null) {
	    log = Logger.getLogger(SaveExperimentStudyController.class);
	}
	return log;
    }

    private String nullIfEmpty(String s) {
	return (s == null || s.trim().equals("")) ? null : s;
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
	    saveAttributes(study, request, session);
	    saveMeasurements(study, request, session);
	    saveDatarecords(study, request, session);

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

    @SuppressWarnings("unchecked")
    public void saveMeasurements(MmExperimentStudy study,
	    HttpServletRequest request, Session sess) {
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
	    String cvid = (String) en.nextElement();

	    if (cvid.indexOf("attrm_") != -1) {
		String ss[] = cvid.split("_");
		if (ss.length != 2) {
		    log().error("invalid attribute id: " + cvid);
		}
		String aid = ss[1];
		String attr = nullIfEmpty(request.getParameter(cvid));
		String attrValue = nullIfEmpty(request
			.getParameter("attrValuem_" + aid));
		MmCvTermDAO termDao = new MmCvTermDAO();
		List terms = termDao.findByName(attr);
		MmCvTerm term = null;
		if (terms.size() > 0) {
		    term = (MmCvTerm) terms.toArray()[0];
		} else {
		    log().error(
			    "failed to find the cvterm for experiment value: "
				    + attr);
		    throw new IllegalArgumentException(
			    "failed to find the cvterm for experiment value: "
				    + attr);
		}
		if (aid.equals("newattr")) {
		    if (attrValue != null && attr != null) {
			MmExperimentValue assoc = new MmExperimentValue();
			assoc.setMmCvTerm(term);
			assoc.setMmExperimentStudy(study);
			assoc.setValue(attrValue);
			term.getMmExperimentValues().add(assoc);
			study.getMmExperimentValues().add(assoc);
			sess.save(assoc);

		    }
		} else {

		    MmExperimentValue assoc = null;
		    for (Object o : study.getMmExperimentValues()) {
			MmExperimentValue as = (MmExperimentValue) o;
			if (as.getExperimentValueOid().equals(
				Integer.valueOf(aid))) {
			    assoc = as;
			    break;
			}
		    }
		    if (assoc == null) {
			log().error(
				"failed to find the MmExperimentValue with the id: "
					+ aid);
			throw new IllegalArgumentException(
				"failed to find the MmExperimentValue with the id: "
					+ aid);
		    }
		    if (attrValue == null) {
			sess.delete(assoc);
			study.getMmExperimentValues().remove(assoc);
		    } else {
			if (!term.getName().equals(
				assoc.getMmCvTerm().getName())) {
			    assoc.setMmCvTerm(term);
			    term.getMmExperimentStudyAttrCvtermAssocs().add(
				    assoc);
			}
			assoc.setValue(attrValue);
			sess.update(assoc);
		    }
		}

	    }

	}

    }

    @SuppressWarnings("unchecked")
    public void saveDatarecords(MmExperimentStudy study,
	    HttpServletRequest request, Session sess) {
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
	    String cvid = (String) en.nextElement();

	    if (cvid.indexOf("stage_") != -1) {
		String ss[] = cvid.split("_");
		if (ss.length != 2) {
		    log().error("invalid attribute id: " + cvid);
		}
		String aid = ss[1];
		String stage = nullIfEmpty(request.getParameter(cvid));
		String name = nullIfEmpty(request.getParameter("name_" + aid));
		String outcrossing = nullIfEmpty(request
			.getParameter("outcrossing_" + aid));
		String outcrossingsd = nullIfEmpty(request
			.getParameter("outcrossingsd_" + aid));
		String selfing = nullIfEmpty(request.getParameter("selfing_"
			+ aid));
		String selfingsd = nullIfEmpty(request
			.getParameter("selfingsd_" + aid));

		String attrValue = nullIfEmpty(request
			.getParameter("attrValue_" + aid));
		MmCvTermDAO termDao = new MmCvTermDAO();
		List terms = termDao.findByName(stage);
		MmCvTerm term = null;
		if (terms.size() > 0) {
		    term = (MmCvTerm) terms.toArray()[0];
		} else {
		    log()
			    .error(
				    "failed to find the cvterm for stage: "
					    + stage);
		    throw new IllegalArgumentException(
			    "failed to find the cvterm for stage: " + stage);
		}
		if (aid.equals("new")) {
		    if (stage != null || name != null || outcrossing != null
			    || outcrossingsd != null || selfing != null
			    || selfingsd != null) {

			MmDataRecord assoc = new MmDataRecord();
			assoc.setMmCvTerm(term);
			assoc.setMmExperimentStudy(study);
			assoc.setName(name);
			assoc.setOutCrossingStdDev(Double.valueOf(outcrossing));
			assoc.setOutCrossingStdDev(Double
				.valueOf(outcrossingsd));
			assoc.setSelfingValue(Double.valueOf(selfing));
			assoc.setSelfingStdDev(Double.valueOf(selfingsd));

			term.getMmDataRecords().add(assoc);
			study.getMmDataRecords().add(assoc);
			sess.save(assoc);

		    }
		} else {

		    MmDataRecord assoc = null;
		    for (Object o : study.getMmDataRecords()) {
			MmDataRecord as = (MmDataRecord) o;
			if (as.getDataRecordOid().equals(Integer.valueOf(aid))) {
			    assoc = as;
			    break;
			}
		    }
		    if (assoc == null) {
			log().error(
				"failed to find the MmDataRecord with the id: "
					+ aid);
			throw new IllegalArgumentException(
				"failed to find the MmDataRecord with the id: "
					+ aid);
		    }
		    if (attrValue == null) {
			sess.delete(assoc);
			study.getMmDataRecords().remove(assoc);
		    } else {
			if (!term.getName().equals(
				assoc.getMmCvTerm().getName())) {
			    assoc.setMmCvTerm(term);
			    term.getMmExperimentStudyAttrCvtermAssocs().add(
				    assoc);
			}
			assoc.setMmExperimentStudy(study);
			assoc.setName(name);
			assoc.setOutCrossingStdDev(Double.valueOf(outcrossing));
			assoc.setOutCrossingStdDev(Double
				.valueOf(outcrossingsd));
			assoc.setSelfingValue(Double.valueOf(selfing));
			assoc.setSelfingStdDev(Double.valueOf(selfingsd));

			sess.update(assoc);
		    }
		}

	    }

	}

    }

    @SuppressWarnings("unchecked")
    public void saveAttributes(MmExperimentStudy study,
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
		MmCvTermDAO termDao = new MmCvTermDAO();
		List terms = termDao.findByName(attr);
		MmCvTerm term = null;
		if (terms.size() > 0) {
		    term = (MmCvTerm) terms.toArray()[0];
		} else {
		    log().error(
			    "failed to find the cvterm for experiment study attribute: "
				    + attr);
		    throw new IllegalArgumentException(
			    "failed to find the cvterm for experiment study attribute: "
				    + attr);
		}
		if (aid.equals("newattr")) {
		    if (attrValue != null && attr != null) {
			MmExperimentStudyAttrCvtermAssoc assoc = new MmExperimentStudyAttrCvtermAssoc();
			assoc.setMmCvTerm(term);
			assoc.setMmExperimentStudy(study);
			assoc.setValue(attrValue);
			term.getMmExperimentStudyAttrCvtermAssocs().add(assoc);
			study.getMmExperimentStudyAttrCvtermAssocs().add(assoc);
			sess.save(assoc);
		    }
		} else {

		    MmExperimentStudyAttrCvtermAssoc assoc = null;
		    for (Object o : study
			    .getMmExperimentStudyAttrCvtermAssocs()) {
			MmExperimentStudyAttrCvtermAssoc as = (MmExperimentStudyAttrCvtermAssoc) o;
			if (as.getMmexpOid().equals(Integer.valueOf(aid))) {
			    assoc = as;
			    break;
			}
		    }
		    if (assoc == null) {
			log().error(
				"failed to find the MmExperimentStudyAttrCvtermAssoc with the id: "
					+ aid);
			throw new IllegalArgumentException(
				"failed to find the MmExperimentStudyAttrCvtermAssoc with the id: "
					+ aid);
		    }
		    if (attrValue == null) {
			sess.delete(assoc);
			study.getMmExperimentStudyAttrCvtermAssocs().remove(
				assoc);
		    } else {
			if (!term.getName().equals(
				assoc.getMmCvTerm().getName())) {
			    assoc.setMmCvTerm(term);
			    term.getMmExperimentStudyAttrCvtermAssocs().add(
				    assoc);
			}

			assoc.setValue(attrValue);
			sess.update(assoc);
		    }
		}

	    }

	}

    }

}
