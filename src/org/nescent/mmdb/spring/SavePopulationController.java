package org.nescent.mmdb.spring;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmCvTermDAO;
import org.nescent.mmdb.hibernate.dao.MmDataRecord;
import org.nescent.mmdb.hibernate.dao.MmDevelopmentalStage;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmExperimentValue;
import org.nescent.mmdb.hibernate.dao.MmExperimentValueDAO;
import org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssocDAO;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmPopulationSampleDAO;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.util.Fields;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SavePopulationController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String id=arg0.getParameter("id");
        if(id==null)
            throw new Exception("No id specified.");
        MmExperimentStudyDAO mmStDao=new MmExperimentStudyDAO();
        MmExperimentStudy study = mmStDao.findById(Integer.valueOf(id));
        if(study==null)
        {
            throw new Exception("No experiment study found.");
        }
        
        MmPopulationSample sample = study.getMmPopulationSample();
        
        if(sample==null)
        {
            throw new Exception("No population sample found by id: "+id);
        }
        
        
        MmPopSampleAttrCvtermAssocDAO valueDao=new MmPopSampleAttrCvtermAssocDAO();
        
        Session sess=HibernateSessionFactory.getSession();
        Transaction tr=sess.beginTransaction();
        String newValueFieldId=Fields.getFieldId("NewPopulationSampleValue");
        if(newValueFieldId==null)
        	throw new Exception("No field defined for NewPopulationSampleValue");
        try
        {
            for(Enumeration en=arg0.getParameterNames();en.hasMoreElements();)
            {
                String cvid=(String)en.nextElement();
                
                if(cvid.equals("id") || cvid.equals(newValueFieldId)) continue;
                
                int intId=0;
                try{
                	intId=Integer.valueOf(cvid).intValue();
                }catch(Exception e)
                {
                	
                }
                if(intId>0)
                {
                	MmPopSampleAttrCvtermAssoc v=valueDao.findById(Integer.valueOf(cvid));
                    if(v==null)
                        throw new Exception("No MmPopSampleAttrCvtermAssoc found with the id: "+id);
                    
                    String value=arg0.getParameter(cvid);
                    if(value==null || value.trim().equals(""))
                    {
                        sess.delete(v);
                    }
                    else
                    {
                        v.setValue(value);
                        sess.update(v);
                    }
                }
                else
                {
                    String fname=Fields.getField(cvid);
                    if(fname!=null)
                    {
                    	if(fname.equals("NewPopulationSampleAttribute"))
                    	{
                    		String field=arg0.getParameter(cvid);
                            String value=arg0.getParameter(newValueFieldId);
                            if(field!=null && value!=null)
                            {
                                MmCvTermDAO termDao=new MmCvTermDAO();
                                List terms=termDao.findByName(field);
                                if(terms.size()>0)
                                {
                                    MmCvTerm term=(MmCvTerm)terms.toArray()[0];
                                    MmPopSampleAttrCvtermAssoc  assoc=new MmPopSampleAttrCvtermAssoc();
                                    assoc.setMmCvTerm(term);
                                    assoc.setMmPopulationSample(sample);
                                    assoc.setValue(value);
                                    term.getMmPopSampleAttrCvtermAssocs().add(assoc);
                                    sample.getMmPopSampleAttrCvtermAssocs().add(assoc);
                                    sess.save(assoc);
                                }
                            }
                    	}
                    	else
                    	{
                    		String value=arg0.getParameter(cvid);
                    		if(value!=null)
                    		{
                    			if(fname.equals("GeoLocation"))
                    				sample.setGeographicLocation(value);
                    			else if(fname.equals("PopulationName"))
                    				sample.setName(value);
                    			else if(fname.equals("Population"))
                    				sample.setPopulation(value);
                    			else if(fname.equals("PopulationYear"))
                    				sample.setYear(value);
                    			else if(fname.equals("PopulationComments"))
                    				sample.setComments(value);
                    			else if(fname.equals("Environment"))
                    				sample.setEnvironment(value);
                    			else
                            		throw new Exception("Not supported field in the Envieonmental Study: "+fname);
                    		}
                    		
                    	}
                    	
                    }
                    else
                    	throw new Exception("Unknown field id: "+cvid);
                	
                }
            }
            sess.update(sample);
            sess.flush();
            tr.commit();
            retrieveEnvironmentalStudy(study);
            Map model=new HashMap();
	        model.put("envstudy",study);
	        model.put("tab","population");
	            
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
