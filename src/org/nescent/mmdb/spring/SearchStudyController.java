package org.nescent.mmdb.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
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
import org.nescent.mmdb.util.Species;

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
		List sps=new ArrayList();
		
		for(int i=0;i<specieses.size();i++)
		{
			MmSpecies species=(MmSpecies)specieses.get(i);
			sps.add(getSpecies(species));
		}
		
		return new ModelAndView("speciesList","list",sps);

	}
	
	public Species getSpecies(MmSpecies species){
		Species sp=new Species();
		sp.setFamily(species.getFamily());
		sp.setGenus(species.getGenus());
		sp.setSpecies(species.getSpecies());
		sp.setId(species.getSpeciesOid());
		int inbreedNum=0;
		int outNum=0;
		int pollNum=0;
		sp.setPopulationNum(species.getMmPopulationSamples().size());
		
		Session sess=HibernateSessionFactory.getSession();
		String hql="SELECT  ";
		hql+= "count(study),study.mmCvTerm.name FROM MmExperimentStudy study ";
		hql+=" INNER JOIN study.mmPopulationSample sample INNER JOIN sample.mmSpecies species";
		hql+=" WHERE species.species='"+sp.getSpecies()+"'";
		hql+=" AND species.genus='"+ sp.getGenus()+"'";
		hql+=" AND species.family='"+sp.getFamily()+"'";
		hql+=" GROUP BY study.mmCvTerm.name ";
		//hql+=" having species.species='latifolia'";
			
		Query q=sess.createQuery(hql);
		List list=q.list();
		for(int i=0;i<list.size();i++)
		{
			
			Object [] objs=(Object [])list.get(i);
			
			String studyName=(String)objs[1];
			if(studyName.indexOf("inbreed")!=-1)
				inbreedNum=((Long)objs[0]).intValue();
			else if(studyName.indexOf("outcross")!=-1)
				outNum=((Long)objs[0]).intValue();
			else if(studyName.indexOf("pollination")!=-1)
				pollNum=((Long)objs[0]).intValue();
			
		}
		
		sp.setInbreedNum(inbreedNum);
		sp.setOutcrossingNum(outNum);
		sp.setPollinationNum(pollNum);
		
		return sp;
	}
}
