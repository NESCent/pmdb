package org.nescent.mmdb.spring;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.hibernate.dao.MmSpeciesDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.nescent.mmdb.util.RetrieveData;

public class SpeciesController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String id=arg0.getParameter("id");
		
		if(id==null || id.trim().equals(""))
			throw new Exception("No species id specified.");
		
		MmSpeciesDAO spDao=new MmSpeciesDAO();
		MmSpecies species = spDao.findById(Integer.valueOf(id));
		if(species==null)
			throw new Exception("No species found.");
		
		RetrieveData.retrieveSpecies(species);
		HibernateSessionFactory.closeSession();
		return new ModelAndView("studies","species",species);
	}
}
