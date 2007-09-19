package org.nescent.mmdb.spring;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.*;
import org.nescent.mmdb.hibernate.dao.*;
import org.nescent.mmdb.util.Fields;
import org.nescent.mmdb.util.RetrieveData;
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
                    			else
                    			{
                    				stage=new MmDevelopmentalStage();
                    				stage.setName(value);
                    				stage.getMmExperimentStudies().add(study);
                    				study.setMmDevelopmentalStage(stage);
                    				sess.save(stage);
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
            RetrieveData.retrieveEnvironmentalStudy(study);
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
    
}
