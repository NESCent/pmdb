package org.nescent.mmdb.spring;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nescent.mmdb.hibernate.dao.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.nescent.mmdb.util.RetrieveData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.*;

public class AddSpeciesDescriptorController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {

		String family = arg0.getParameter("family");
		String genus = arg0.getParameter("genus");
		String species = arg0.getParameter("species");
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

		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();

		MmMatingSystemStudy study = null;

		// get or create species
		MmSpecies sp = null;

		MmSpeciesDAO spDao = new MmSpeciesDAO();
		List sps = spDao.findByName(family, genus, species);
		if (sps.size() > 0)
			sp = (MmSpecies) sps.get(0);
		else {
			sp = new MmSpecies();
			sp.setFamily(family);
			sp.setGenus(genus);
			sp.setSpecies(species);

		}

		study = new MmMatingSystemStudy();
		study.setMmSpecies(sp);
		study.setLatitude(latitude);
		sp.getMmMatingSystemStudies().add(study);

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
		mpart.getMmMatingSystemStudies().add(study);

		sess.save(study);
		sess.flush();
		tx.commit();
		return new ModelAndView("descriptor", "descriptor", study);
	}
}
