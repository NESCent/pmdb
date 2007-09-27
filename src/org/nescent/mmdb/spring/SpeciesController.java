package org.nescent.mmdb.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.nescent.mmdb.util.Population;
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
		
		Map models=new HashMap();
		List pops=new ArrayList();
		Set samples=species.getMmPopulationSamples();
		for(Iterator it=samples.iterator();it.hasNext();)
		{
			MmPopulationSample sample=(MmPopulationSample)it.next();
			pops.add(getPopulation1(sample));
		}
		models.put("species", species);
		models.put("populations", pops);
		HibernateSessionFactory.closeSession();
		return new ModelAndView("studies",models);
	}
	
	public Population getPopulation(MmPopulationSample sample){
		
		
		Population pop=new Population();
		pop.setId(sample.getPopulationSampleOid());
		pop.setGeographicLocation(sample.getGeographicLocation());
		pop.setPopulation(sample.getPopulation());
		pop.setYear(sample.getYear());
		
		int inbreedNum=0;
		int outNum=0;
		int pollNum=0;
		
		
		Session sess=HibernateSessionFactory.getSession();
		String hql="SELECT  ";
		hql+= "count(study),study.mmCvTerm.name FROM MmExperimentStudy study ";
		hql+=" INNER JOIN study.mmPopulationSample sample";
		hql+=" WHERE sample.populationSampleOid=:population_id";
		hql+=" GROUP BY study.mmCvTerm.name ";
		//hql+=" having species.species='latifolia'";
			
		Query q=sess.createQuery(hql);
		q.setInteger("population_id", sample.getPopulationSampleOid().intValue());
		List list=q.list();
		for(int i=0;i<list.size();i++)
		{
			
			Object [] objs=(Object [])list.get(i);
			
			String studyName=(String)objs[1];
			if(studyName.indexOf("inbreed")!=-1)
				inbreedNum=((Integer)objs[0]).intValue();
			else if(studyName.indexOf("outcross")!=-1)
				outNum=((Integer)objs[0]).intValue();
			else if(studyName.indexOf("pollination")!=-1)
				pollNum=((Integer)objs[0]).intValue();
			
		}
		
		pop.setInbreedNum(inbreedNum);
		pop.setOutcrossingNum(outNum);
		pop.setPollinationNum(pollNum);
		
		return pop;
	}
	
	public Population getPopulation1(MmPopulationSample sample){
	
		Population pop=new Population();
		pop.setId(sample.getPopulationSampleOid());
		pop.setGeographicLocation(sample.getGeographicLocation());
		pop.setPopulation(sample.getPopulation());
		pop.setYear(sample.getYear());
		
		int inbreedNum=0;
		int outNum=0;
		int pollNum=0;
		
		Set sts=sample.getMmExperimentStudies();
		for(Iterator it1=sts.iterator();it1.hasNext();)
		{
			MmExperimentStudy st=(MmExperimentStudy)it1.next();
			MmCvTerm term=st.getMmCvTerm();
			if(term!=null && term.getName()!=null)
			{
				if(term.getName().indexOf("inbreeding")!=-1)
					inbreedNum++;
				else if(term.getName().indexOf("pollination")!=-1)
					pollNum++;
				else if(term.getName().indexOf("outcrossing")!=-1)
					outNum++;
			}
				
		}
		pop.setInbreedNum(inbreedNum);
		pop.setOutcrossingNum(outNum);
		pop.setPollinationNum(pollNum);
		
		return pop;
	}
}
