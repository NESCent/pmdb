package org.nescent.mmdb.spring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmDataRecord;
import org.nescent.mmdb.hibernate.dao.MmDataRecordDAO;
import org.nescent.mmdb.hibernate.dao.MmDevelopmentalStage;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmExperimentValue;
import org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class DeleteDataRecordController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		String id=arg0.getParameter("id");
        if(id==null)
            throw new Exception("No id specified.");
        MmDataRecordDAO rDao=new MmDataRecordDAO();
        MmDataRecord record = rDao.findById(Integer.valueOf(id));
        if(record==null)
        {
            throw new Exception("No data record found for id: "+id);
        }
		
        MmExperimentStudy study =record.getMmExperimentStudy();
        if(study==null)
        	throw new Exception("No environmental study froun for the data record: "+id);
        
        Session sess=HibernateSessionFactory.getSession();
        Transaction tr=sess.beginTransaction();
        
        
        try
        {
            sess.delete(record);
            sess.flush();
            tr.commit();
            retrieveEnvironmentalStudy(study);
            Map model=new HashMap();
            model.put("envstudy",study);
            model.put("tab","staticstics");
            
            return new ModelAndView("envstudy",model);
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            tr.rollback();
            sess.close();
        }
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
            MmCvTerm t=record.getMmCvTerm();
            if(t!=null)
            {
            	t.getCvtermOid();
                t.getDescription();
                t.getName();
                t.getNamespace();
                t.getValueType();
            }
        }
        
        
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
