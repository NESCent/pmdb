package org.nescent.mmdb.spring;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferenceDAO;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.hibernate.dao.MmSpeciesDAO;
import org.nescent.mmdb.util.RetrieveData;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class AddExperimentalStudyController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		String studyName = arg0.getParameter("studyName");
		String family = arg0.getParameter("family");
		String genus = arg0.getParameter("genus");
		String species = arg0.getParameter("species");

		String geographicLocation = arg0.getParameter("geographicLocation");
		String population = arg0.getParameter("population");
		String comments = arg0.getParameter("comments");
		String year = arg0.getParameter("year");

		String citation = arg0.getParameter("citation");
		String full = arg0.getParameter("fullreference");
		String part = arg0.getParameter("part");
		String latitude = arg0.getParameter("latitude");

		if (genus == null || genus.trim().equals(""))
			throw new Exception("Empty genus.");
		if (species == null || species.trim().equals(""))
			throw new Exception("Empty species.");
		if (citation == null || citation.trim().equals(""))
			throw new Exception("Empty citation.");
		if (studyName == null || studyName.trim().equals(""))
			throw new Exception("Empty study name.");
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();

		MmExperimentStudy study = null;

		// get or create species and population
		MmSpecies sp = null;
		MmPopulationSample pSample = null;

		MmSpeciesDAO spDao = new MmSpeciesDAO();
		List sps = spDao.findByName(family, genus, species);
		if (sps.size() > 0) {
			sp = (MmSpecies) sps.get(0);
			Set samples = sp.getMmPopulationSamples();
			for (Iterator it = samples.iterator(); it.hasNext();) {
				MmPopulationSample smp = (MmPopulationSample) it.next();
				if (isEqual(smp.getGeographicLocation(), geographicLocation)
						&& isEqual(smp.getPopulation(), population)
						&& isEqual(smp.getYear(), year)) {
					pSample = smp;
					break;
				}
			}
			if (pSample == null) {
				pSample = new MmPopulationSample();
				pSample.setGeographicLocation(geographicLocation);
				pSample.setYear(year);
				pSample.setComments(comments);
				pSample.setMmSpecies(sp);
				sp.getMmPopulationSamples().add(pSample);
			}
		} else {
			sp = new MmSpecies();
			sp.setFamily(family);
			sp.setGenus(genus);
			sp.setSpecies(species);

			pSample = new MmPopulationSample();
			pSample.setGeographicLocation(geographicLocation);
			pSample.setYear(year);
			pSample.setComments(comments);
			pSample.setMmSpecies(sp);
			sp.getMmPopulationSamples().add(pSample);
		}

		study = new MmExperimentStudy();
		study.setMmPopulationSample(pSample);
		pSample.getMmExperimentStudies().add(study);

		// get or create reference
		MmReference ref = null;
		MmReferencePart mpart = null;
		MmReferenceDAO refDao = new MmReferenceDAO();
		List refs = refDao.findByCitation(citation);

		if (refs.size() > 0) {
			ref = (MmReference) refs.get(0);
			Set parts = ref.getMmReferenceParts();

			for (Iterator it = parts.iterator(); it.hasNext();) {
				MmReferencePart p = (MmReferencePart) it.next();
				String pname = p.getName();

				if (pname != null && part != null
						&& pname.trim().equals(part.trim())) {
					mpart = p;
					break;
				} else if (pname == null && part == null) {
					mpart = p;
					break;
				}
			}
			if (mpart == null) {
				mpart = new MmReferencePart();
				mpart.setName(part);
				mpart.setMmReference(ref);
				ref.getMmReferenceParts().add(mpart);
			}
		} else {
			ref = new MmReference();
			ref.setCitation(citation);
			ref.setFullReference(full);
			mpart = new MmReferencePart();
			mpart.setName(part);
			mpart.setMmReference(ref);
			ref.getMmReferenceParts().add(mpart);
		}

		study.setMmReferencePart(mpart);
		mpart.getMmExperimentStudies().add(study);
		study.setName(studyName);

		sess.save(study);
		sess.flush();
		tx.commit();

		Map model = new HashMap();
		model.put("envstudy", study);
		model.put("tab", "study");

		return new ModelAndView("envstudy", model);

	}

	public boolean isEqual(String o1, String o2) {
		if (o1 == null || o1.trim().equals("")) {
			if (o2 == null || o2.trim().equals(""))
				return true;
		} else if (o2 == null || o2.trim().equals(""))
			return false;
		else
			return o1.equals(o2);

		return false;
	}
}
