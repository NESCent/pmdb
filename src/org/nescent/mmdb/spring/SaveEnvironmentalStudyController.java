package org.nescent.mmdb.spring;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.*;
import org.nescent.mmdb.hibernate.dao.*;
import org.nescent.mmdb.util.Fields;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;


public class SaveEnvironmentalStudyController implements Controller {
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
        
        
        MmExperimentValueDAO valueDao=new MmExperimentValueDAO();
        
        Session sess=HibernateSessionFactory.getSession();
        Transaction tr=sess.beginTransaction();
        String newValueFieldId=Fields.getFieldId("NewEnvironmentStudyValue");
        if(newValueFieldId==null)
        	throw new Exception("No field defined for NewEnvironmentStudyValue");
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
                	MmExperimentValue v=valueDao.findById(Integer.valueOf(cvid));
                    if(v==null)
                        throw new Exception("No MmExperimentValue found with the id: "+id);
                    
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
                    	if(fname.equals("NewEnvironmentStudyAttribute"))
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
                                    MmExperimentValue  assoc=new MmExperimentValue();
                                    assoc.setMmCvTerm(term);
                                    assoc.setMmExperimentStudy(study);
                                    assoc.setValue(value);
                                    term.getMmExperimentValues().add(assoc);
                                    study.getMmExperimentValues().add(assoc);
                                    sess.save(assoc);
                                }
                            }
                    	}
                    	else if(fname.equals("DevelopStage"))
                    	{
                    		String value=arg0.getParameter(cvid);
                    		if(value!=null)
                    		{
                    			MmDevelopmentalStage stage=study.getMmDevelopmentalStage();
                    			if(stage!=null)
                    			{
                    				stage.setName(value);
                    				sess.update(stage);
                    			}
                    		}
                    	}
                    	else
                    	{
                    		throw new Exception("Not supported field in the Envieonmental Study: "+fname);
                    	}
                    }
                    else
                    	throw new Exception("Unknown field id: "+cvid);
                	
                }
            }
            sess.flush();
            tr.commit();
            retrieveEnvironmentalStudy(study);
            Map model=new HashMap();
            model.put("envstudy",study);
            model.put("tab","study");
            
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