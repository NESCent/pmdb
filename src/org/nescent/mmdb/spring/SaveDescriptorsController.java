package org.nescent.mmdb.spring;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmCvTermDAO;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmPopulationSampleDAO;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssocDAO;
import org.nescent.mmdb.hibernate.dao.MmSpeciesDAO;
import org.nescent.mmdb.util.Fields;
import org.nescent.mmdb.util.RetrieveData;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SaveDescriptorsController implements Controller {

    private static Logger logger=null;
    
    private Logger log() {
        if(logger==null)
            logger=Logger.getLogger(SaveDescriptorsController.class);
        
        return logger;
    }
    public ModelAndView handleRequest(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception{
        
        
        
        String id=arg0.getParameter("id");
        if(id==null)
        {
            log().error("No population id specified");
            throw new RuntimeException("No population id specified.");
        }
        MmPopulationSampleDAO sampleDao=new MmPopulationSampleDAO();    
        MmPopulationSample sample=sampleDao.findById(Integer.valueOf(id));
        
        if(sample==null)
        {
            log().error("Unable to find the species: "+id);
            throw new RuntimeException("No population found: " +id);
        }
        
        
        
        String species_id=arg0.getParameter("species_id");
        if(species_id==null)
        {
            log().error("No species id specified");
            throw new RuntimeException("No species id specified.");
        }
            
        MmSpeciesDAO mmSpDao=new MmSpeciesDAO();
        MmSpecies sp = mmSpDao.findById(Integer.valueOf(species_id));
        if(sp==null)
        {
            log().error("Unable to find the species: "+species_id);
            throw new RuntimeException("No species found.");
        }
        
        Session sess=HibernateSessionFactory.getSession();
        Transaction tr=sess.beginTransaction();
        
        try
        {
            saveSpecies(sp,arg0,sess);
            saveStudies(arg0,sess);
            sess.flush();
            tr.commit();
            
            return new ModelAndView("population","population",sample);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            if(tr!=null)
            {
                tr.rollback();
            }
            sess.close();
        }
    }
    
    public void saveStudies(HttpServletRequest arg0, Session sess) throws Exception
    {
        ArrayList<String> descriptor_ids=new ArrayList<String>();
        for(Enumeration en=arg0.getParameterNames();en.hasMoreElements();)
        {
            String cvid=(String)en.nextElement();
            if(cvid.indexOf("descriptor_id")!=-1)
                descriptor_ids.add(arg0.getParameter(cvid));
        }
        
        for(int i=0;i<descriptor_ids.size();i++)
        {
            String descriptor_id=(String)descriptor_ids.get(i);
            MmMatingSystemStudyDAO mmStDao=new MmMatingSystemStudyDAO();
            MmMatingSystemStudy study=mmStDao.findById(Integer.valueOf(descriptor_id));
            if(study==null)
            {
                log().error("Unable to find the mating system study : "+descriptor_id);
                throw new Exception("Unable to find the mating system study : "+descriptor_id);
            }
            saveStudy(study,arg0,sess);
        }
        
       
    
    }
    
    public void saveStudy(MmMatingSystemStudy study,HttpServletRequest arg0, Session sess) throws Exception{
        
        MmSpeciesAttrCvtermAssocDAO assocDao=new MmSpeciesAttrCvtermAssocDAO();
        Integer id=study.getMatingSystemStudyOid();
        String citation=arg0.getParameter(id+"."+Fields.getFieldId("citation"));
        String full=arg0.getParameter(id+"."+Fields.getFieldId("fullreference"));
        String part=arg0.getParameter(id+"."+Fields.getFieldId("part"));
        String latitude=arg0.getParameter(id+"."+Fields.getFieldId("latitude"));
        
        if(latitude!=null)
            study.setLatitude(latitude);
        
        MmReferencePart mpart=study.getMmReferencePart();
        if(mpart!=null)
        {
            if(part!=null)
                mpart.setName(part);
            MmReference ref=mpart.getMmReference();
            if(ref!=null)
            {
                if(citation!=null)
                    ref.setCitation(citation);
                if(full!=null)  
                    ref.setFullReference(full);
            }
        }
        
        sess.update(study);
        
        String newFid=id+"."+Fields.getFieldId("NewDescriptorAttribute");
        String newVid=id+"."+Fields.getFieldId("NewDescriptorValue");
        String prefix=id+".";
        
        
        for(Enumeration en=arg0.getParameterNames();en.hasMoreElements();)
        {
            String cvid=(String)en.nextElement();
            
            
            if(cvid.equals(newFid))
            {
                String field=arg0.getParameter(newFid);
                String value=arg0.getParameter(newVid);
                if(field!=null && ! field.trim().equals("") && value!=null && !value.trim().equals(""))
                {
                    MmCvTermDAO termDao=new MmCvTermDAO();
                    List terms=termDao.findByName(field);
                    if(terms.size()>0)
                    {
                        MmCvTerm term=(MmCvTerm)terms.toArray()[0];
                        MmSpeciesAttrCvtermAssoc assoc=new MmSpeciesAttrCvtermAssoc();
                        assoc.setMmCvTerm(term);
                        assoc.setMmMatingSystemStudy(study);
                        assoc.setValue(value);
                        term.getMmSpeciesAttrCvtermAssocs().add(assoc);
                        study.getMmSpeciesAttrCvtermAssocs().add(assoc);
                        sess.save(assoc);
                    }
                }
            }
            else if(cvid.indexOf(prefix)!=-1)
            {
                String ss[]=cvid.split(".");
                if(ss.length>2)
                {
                    MmSpeciesAttrCvtermAssoc assoc=assocDao.findById(Integer.valueOf(ss[1]));
                    if(assoc==null)
                        throw new Exception("No MmSpeciesAttrCvtermAssoc found with the id: "+id);
                
                    String value=arg0.getParameter(cvid);
                    if(value==null || value.equals(""))
                    {
                        sess.delete(assoc);
                    }
                    else
                    {
                        assoc.setValue(value);
                        sess.update(assoc);
                    }
                }
            }
        }
    }
    public void saveSpecies(MmSpecies sp,HttpServletRequest arg0, Session sess) throws Exception
    {
        String family=arg0.getParameter(Fields.getFieldId("Family"));
        String genus=arg0.getParameter(Fields.getFieldId("genus"));
        String species=arg0.getParameter(Fields.getFieldId("species"));
        
        if(genus!=null && genus.trim().equals(""))
            throw new  Exception("Empty genus.");
        if(species!=null && species.trim().equals(""))
            throw new  Exception("Empty species.");
        
        if(family!=null)
            sp.setFamily(family);
        if(genus!=null) 
            sp.setGenus(genus);
        if(species!=null)
            sp.setSpecies(species);
        
        
                
        sess.update(sp);
    }

}
