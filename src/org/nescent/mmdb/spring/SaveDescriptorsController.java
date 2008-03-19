package org.nescent.mmdb.spring;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmCvTermDAO;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmPopulationSampleDAO;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssocDAO;
import org.nescent.mmdb.hibernate.dao.MmSpeciesDAO;
import org.nescent.mmdb.util.Fields;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SaveDescriptorsController implements Controller {

    private static Logger logger = null;

    private Logger log() {
	if (logger == null)
	    logger = Logger.getLogger(SaveDescriptorsController.class);

	return logger;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
	    HttpServletResponse response) {

	String id = request.getParameter("id");
	if (id == null) {
	    log().error("No population id specified");
	    throw new RuntimeException("No population id specified.");
	}
	MmPopulationSampleDAO sampleDao = new MmPopulationSampleDAO();
	MmPopulationSample sample = sampleDao.findById(Integer.valueOf(id));

	if (sample == null) {
	    log().error("Unable to find the species: " + id);
	    throw new RuntimeException("No population found: " + id);
	}

	String species_id = request.getParameter("species_id");
	if (species_id == null) {
	    log().error("No species id specified");
	    throw new RuntimeException("No species id specified.");
	}

	MmSpeciesDAO mmSpDao = new MmSpeciesDAO();
	MmSpecies sp = mmSpDao.findById(Integer.valueOf(species_id));
	if (sp == null) {
	    log().error("Unable to find the species: " + species_id);
	    throw new RuntimeException("No species found.");
	}

	Session sess = HibernateSessionFactory.getSession();
	Transaction tr = sess.beginTransaction();

	try {
	    saveSpecies(sp, request, sess);
	    saveStudies(request, sess);
	    sess.flush();
	    tr.commit();

	    return new ModelAndView("population", "population", sample);
	} catch (HibernateException e) {
	    log().error("failed to save the species descriptors.", e);
	    throw e;
	} finally {
	    if (!tr.wasCommitted()) {
		tr.rollback();
	    }
	}
    }

    @SuppressWarnings("unchecked")
    public void saveStudies(HttpServletRequest request, Session sess) {
	ArrayList<String> descriptor_ids = new ArrayList<String>();
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
	    String cvid = (String) en.nextElement();
	    if (cvid.indexOf("descriptor_id") != -1)
		descriptor_ids.add(request.getParameter(cvid));
	}

	for (int i = 0; i < descriptor_ids.size(); i++) {
	    String descriptor_id = (String) descriptor_ids.get(i);
	    MmMatingSystemStudyDAO mmStDao = new MmMatingSystemStudyDAO();
	    MmMatingSystemStudy study = mmStDao.findById(Integer
		    .valueOf(descriptor_id));
	    if (study == null) {
		log().error(
			"Unable to find the mating system study : "
				+ descriptor_id);
		throw new IllegalArgumentException(
			"Unable to find the mating system study : "
				+ descriptor_id);
	    }
	    saveStudy(study, request, sess);
	}

    }

    @SuppressWarnings("unchecked")
    public void saveStudy(MmMatingSystemStudy study,
	    HttpServletRequest request, Session sess) {

	MmSpeciesAttrCvtermAssocDAO assocDao = new MmSpeciesAttrCvtermAssocDAO();
	Integer id = study.getMatingSystemStudyOid();
	String citation = request.getParameter(id + "."
		+ Fields.getFieldId("citation"));
	String full = request.getParameter(id + "."
		+ Fields.getFieldId("fullreference"));
	String part = request
		.getParameter(id + "." + Fields.getFieldId("part"));
	String latitude = request.getParameter(id + "."
		+ Fields.getFieldId("latitude"));

	if (latitude != null)
	    study.setLatitude(latitude);

	MmReferencePart mpart = study.getMmReferencePart();
	if (mpart != null) {
	    if (part != null)
		mpart.setName(part);
	    MmReference ref = mpart.getMmReference();
	    if (ref != null) {
		if (citation != null)
		    ref.setCitation(citation);
		if (full != null)
		    ref.setFullReference(full);
	    }
	}

	sess.update(study);

	String newFid = id + "." + Fields.getFieldId("NewDescriptorAttribute");
	String newVid = id + "." + Fields.getFieldId("NewDescriptorValue");
	String prefix = id + ".";

	for (Enumeration en = request.getParameterNames(); en.hasMoreElements();) {
	    String cvid = (String) en.nextElement();

	    if (cvid.equals(newFid)) {
		String field = request.getParameter(newFid);
		String value = request.getParameter(newVid);
		if (field != null && !field.trim().equals("") && value != null
			&& !value.trim().equals("")) {
		    MmCvTermDAO termDao = new MmCvTermDAO();
		    List terms = termDao.findByName(field);
		    if (terms.size() > 0) {
			MmCvTerm term = (MmCvTerm) terms.toArray()[0];
			MmSpeciesAttrCvtermAssoc assoc = new MmSpeciesAttrCvtermAssoc();
			assoc.setMmCvTerm(term);
			assoc.setMmMatingSystemStudy(study);
			assoc.setValue(value);
			term.getMmSpeciesAttrCvtermAssocs().add(assoc);
			study.getMmSpeciesAttrCvtermAssocs().add(assoc);
			sess.save(assoc);
		    }
		}
	    } else if (cvid.indexOf(prefix) != -1) {
		String ss[] = cvid.split(".");
		if (ss.length > 2) {
		    MmSpeciesAttrCvtermAssoc assoc = assocDao.findById(Integer
			    .valueOf(ss[1]));
		    if (assoc == null) {
			log().error(
				"No MmSpeciesAttrCvtermAssoc found with the id: "
					+ id);
			throw new IllegalArgumentException(
				"No MmSpeciesAttrCvtermAssoc found with the id: "
					+ id);
		    }
		    String value = request.getParameter(cvid);
		    if (value == null || value.equals("")) {
			sess.delete(assoc);
		    } else {
			assoc.setValue(value);
			sess.update(assoc);
		    }
		}
	    }
	}
    }

    public void saveSpecies(MmSpecies sp, HttpServletRequest request,
	    Session sess) {
	String family = request.getParameter(Fields.getFieldId("Family"));
	String genus = request.getParameter(Fields.getFieldId("genus"));
	String species = request.getParameter(Fields.getFieldId("species"));

	if (genus != null && genus.trim().equals("")) {
	    log().error("no genus specified.");
	    throw new IllegalArgumentException("no genus specified.");
	}
	if (species != null && species.trim().equals("")) {
	    log().error("no species specified.");
	    throw new IllegalArgumentException("no species specified.");
	}

	if (family != null)
	    sp.setFamily(family);
	if (genus != null)
	    sp.setGenus(genus);
	if (species != null)
	    sp.setSpecies(species);

	sess.update(sp);
    }

}
