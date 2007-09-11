package org.nescent.mmdb.spring;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmDataRecord;
import org.nescent.mmdb.hibernate.dao.MmDevelopmentalStage;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmExperimentValue;
import org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ViewEnvironmentalStudyController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String id=arg0.getParameter("id");
		if(id==null || id.trim().equals(""))
			throw new Exception("No id specified.");
		
		MmExperimentStudyDAO envStDao=new MmExperimentStudyDAO();
		MmExperimentStudy study = envStDao.findById(Integer.valueOf(id));
		if(study==null)
		{
			throw new Exception("No environmental study ound.");
		}
		
		retrieveEnvironmentalStudy(study);
		HibernateSessionFactory.closeSession();
		return new ModelAndView("envstudy","envstudy",study);
	
	}
	
	public void retrieveEnvironmentalStudy(MmExperimentStudy envStudy) throws Exception
	{
		MmPopulationSample sample=envStudy.getMmPopulationSample();
		if(sample!=null)
		{
			
			MmSpecies species=sample.getMmSpecies();
			species.getFamily();
			species.getGenus();
			species.getSpecies();
			
			sample.getComments();
			sample.getEnvironment();
			sample.getGeographicLocation();
			Set set =sample.getMmPopSampleAttrCvtermAssocs();
			for(Iterator it=set.iterator();it.hasNext();)
			{
				MmPopSampleAttrCvtermAssoc cvAssoc=(MmPopSampleAttrCvtermAssoc)it.next();
				MmCvTerm term=cvAssoc.getMmCvTerm();
				term.getCvtermOid();
				term.getDescription();
				term.getName();
				term.getNamespace();
				term.getValueType();
				cvAssoc.getValue();
			}
			
			sample.getName();
			sample.getPopulation();
			sample.getYear();
			sample.getPopulationSampleOid();
		}
		
		envStudy.getExperimentStudyOid();
		Set set= envStudy.getMmDataRecords();
		for(Iterator it=set.iterator();it.hasNext();)
		{
			MmDataRecord record=(MmDataRecord)it.next();
			record.getName();
			record.getOutCrossingStdDev();
			record.getOutCrossingValue();
			record.getSelfingStdDev();
			record.getSelfingValue();
			record.getType();
		}
		
		MmDevelopmentalStage stage=envStudy.getMmDevelopmentalStage();
		stage.getName();
		
		set= envStudy.getMmExperimentValues();
		for(Iterator it=set.iterator();it.hasNext();)
		{
			MmExperimentValue value=(MmExperimentValue)it.next();
			
			MmCvTerm term=value.getMmCvTerm();
			term.getCvtermOid();
			term.getDescription();
			term.getName();
			term.getNamespace();
			term.getValueType();
			
			value.getValue();
		}
		
		
		
		MmReferencePart refPart=envStudy.getMmReferencePart();
		if(refPart!=null)
		{
			refPart.getName();
			MmReference ref=refPart.getMmReference();
			if(ref!=null)
			{
				ref.getCitation();
				ref.getFullReference();
			}
		}
	}
}
