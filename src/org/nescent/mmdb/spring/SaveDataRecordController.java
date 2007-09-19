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
import org.nescent.mmdb.util.RetrieveData;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmCvTermDAO;
import org.nescent.mmdb.hibernate.dao.MmDataRecord;
import org.nescent.mmdb.hibernate.dao.MmDataRecordDAO;
import org.nescent.mmdb.hibernate.dao.MmDevelopmentalStage;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmExperimentValue;
import org.nescent.mmdb.hibernate.dao.MmExperimentValueDAO;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.util.Fields;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SaveDataRecordController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		MmDataRecord newRecord=null;
		String id=arg0.getParameter("id");
        if(id==null)
            throw new Exception("No id specified.");
        MmExperimentStudyDAO mmStDao=new MmExperimentStudyDAO();
        MmExperimentStudy study = mmStDao.findById(Integer.valueOf(id));
        if(study==null)
        {
            throw new Exception("No experiment study found.");
        }
		
        MmDataRecordDAO rDao=new MmDataRecordDAO();
        
        Session sess=HibernateSessionFactory.getSession();
        Transaction tr=sess.beginTransaction();
        
        
        try
        {
            for(Enumeration en=arg0.getParameterNames();en.hasMoreElements();)
            {
                String cvid=(String)en.nextElement();
                if(cvid==null || cvid.equals("id") || cvid.indexOf("record_id")!=-1) continue;
                String value=arg0.getParameter(cvid);
                Double doubleValue=null;
                try{
                	doubleValue=Double.valueOf(value);
    			}catch(Exception e){
    				
    			}
                int intId=0;
                try{
                	intId=Integer.valueOf(cvid).intValue();
                }catch(Exception e)
                {
                	throw new Exception("Invalid id: "+cvid);
                }
                if(intId<0)
                {
                	intId=-intId;
                	int record_id=intId/10000;
                	String rid="record_id_"+record_id;
                	String did=arg0.getParameter(rid);
                	if(did==null) //new record
                	{
                		if(newRecord==null)
                		{
                			newRecord=new MmDataRecord();
                			newRecord.setMmExperimentStudy(study);
                			study.getMmDataRecords().add(newRecord);
                		}
                		
                		int fid=intId%10000;
                		
                		if(fid==1) //rname
                			newRecord.setName(value);
                		else if(fid==2) //type
                			newRecord.setType(value);
                		else if(fid==3) //outsd
                		{
                			newRecord.setOutCrossingStdDev(doubleValue);
                		}
                		else if(fid==4) //outv
                			newRecord.setOutCrossingValue(doubleValue);
                		else if(fid==5) //selfsd
                			newRecord.setSelfingStdDev(doubleValue);
                		else if(fid==6) //selfv
                			newRecord.setSelfingValue(doubleValue);
                		else 
                			throw new Exception("Not supported field id in the Envieonmental Study: "+fid);
                		
                		
                	}
                	else
                	{
                		MmDataRecord rcd=rDao.findById(Integer.valueOf(did));
                		if(rcd==null)
                			throw new Exception("No Data Record found for the id: "+did);                	
                		int fid=intId%10000;
                		
                		if(fid==1) //rname
                			rcd.setName(value);
                		else if(fid==2) //type
                			rcd.setType(value);
                		else if(fid==3) //outsd
                			rcd.setOutCrossingStdDev(doubleValue);
                		else if(fid==4) //outv
                			rcd.setOutCrossingValue(doubleValue);
                		else if(fid==5) //selfsd
                			rcd.setSelfingStdDev(doubleValue);
                		else if(fid==6) //selfv
                			rcd.setSelfingValue(doubleValue);
                		else 
                			throw new Exception("Not supported field id in the Envieonmental Study: "+fid);
                		
                		sess.update(rcd);
                    }
                }
                else
                  	throw new Exception("Invalid field id: "+intId);
            }
            
            if(newRecord!=null)
            	sess.save(newRecord);
            sess.flush();
            tr.commit();
            RetrieveData.retrieveEnvironmentalStudy(study);
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

}
