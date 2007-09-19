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

public class SearchStudyController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String speciesName=arg0.getParameter("species");
		String familyName=arg0.getParameter("family");
		String genusName=arg0.getParameter("genus");
		
		if((speciesName==null || speciesName.trim().equals("")) && 
				(familyName==null || familyName.trim().equals("")) &&
				(genusName==null || genusName.trim().equals("")))
			throw new Exception("No names specified.");
		
		MmSpeciesDAO spDao=new MmSpeciesDAO();
		List specieses = spDao.findByName(familyName,genusName,speciesName);
		if(specieses.size()!=1)
		{
			HibernateSessionFactory.closeSession();
			return new ModelAndView("speciesList","list",specieses);
		}
		else
		{
			MmSpecies species=(MmSpecies)specieses.get(0);
			RetrieveData.retrieveSpecies(species);
			HibernateSessionFactory.closeSession();
			return new ModelAndView("studies","species",species);
		}
		
		
	}
}
