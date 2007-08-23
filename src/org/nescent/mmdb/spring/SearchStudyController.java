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

public class SearchStudyController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String speciesName=arg0.getParameter("species");
		
		if(speciesName==null || speciesName.trim().equals(""))
			throw new Exception("No species specified.");
		
		MmSpeciesDAO spDao=new MmSpeciesDAO();
		List specieses = spDao.findByName(speciesName);
		if(specieses.size()!=1)
		{
			HibernateSessionFactory.closeSession();
			return new ModelAndView("speciesList","list",specieses);
		}
		else
		{
			MmSpecies species=(MmSpecies)specieses.toArray()[0];
			retrieveSpecies(species);
			HibernateSessionFactory.closeSession();
			return new ModelAndView("studies","species",species);
		}
		
		
	}
	
	private void retrieveSpecies(MmSpecies species)
	{
		
		Set mmStudies=species.getMmMatingSystemStudies();
		for(Iterator it=mmStudies.iterator();it.hasNext();)
		{
			MmMatingSystemStudy mmStudy=(MmMatingSystemStudy)it.next();
			mmStudy.getLatitude();
			MmReferencePart refPart=mmStudy.getMmReferencePart();
			refPart.getName();
			MmReference ref=refPart.getMmReference();
			ref.getCitation();
			ref.getFullReference();
		}
		
		Set mmSamples=species.getMmPopulationSamples();
		for(Iterator it=mmSamples.iterator();it.hasNext();)
		{
			MmPopulationSample mmSample=(MmPopulationSample)it.next();
			mmSample.getGeographicLocation();
			mmSample.getEnvironment();
			mmSample.getName();
			mmSample.getPopulation();
			mmSample.getYear();
			Set mmEnvStudies=mmSample.getMmExperimentStudies();
			for(Iterator it1=mmSamples.iterator();it1.hasNext();)
			{
				MmExperimentStudy mmEnvStudy=(MmExperimentStudy)it.next();
				mmEnvStudy.getName();
				MmReferencePart refPart=mmEnvStudy.getMmReferencePart();
				refPart.getName();
				MmReference ref=refPart.getMmReference();
				ref.getCitation();
				ref.getFullReference();
			}
		}
		
		
		
		
	}

}
