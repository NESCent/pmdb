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
import org.nescent.mmdb.hibernate.dao.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.nescent.mmdb.util.Fields;
import org.nescent.mmdb.util.RetrieveData;

public class SaveExperimentalController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String id=arg0.getParameter("id");
		if(id==null)
			throw new Exception("No id specified.");
		MmExperimentStudyDAO stDao=new MmExperimentStudyDAO();
		MmExperimentStudy study = stDao.findById(Integer.valueOf(id));
		if(study==null)
		{
			throw new Exception("No experiment study found.");
		}
		
		String family=arg0.getParameter(Fields.getFieldId("Family"));
		String genus=arg0.getParameter(Fields.getFieldId("genus"));
		String species=arg0.getParameter(Fields.getFieldId("species"));
		String citation=arg0.getParameter(Fields.getFieldId("citation"));
		String full=arg0.getParameter(Fields.getFieldId("fullreference"));
		String part=arg0.getParameter(Fields.getFieldId("part"));
				
		if(genus!=null && genus.trim().equals(""))
			throw new  Exception("Empty genus.");
		if(species!=null && species.trim().equals(""))
			throw new  Exception("Empty species.");
		if(citation!=null && citation.trim().equals(""))
			throw new  Exception("Empty citation.");	
		
		Session sess=HibernateSessionFactory.getSession();
		Transaction tx=sess.beginTransaction();
		
	
		
		
		//get or create species
		MmPopulationSample sample=study.getMmPopulationSample();
		if(sample==null)
			throw new Exception("No Population Sample found.");
			
		MmSpecies sp=sample.getMmSpecies();
				
		if(sp==null)
			throw new Exception("No species found for the mating system study.");
		
		if(family!=null)
			sp.setFamily(family);
		if(genus!=null)	
			sp.setGenus(genus);
		if(species!=null)
			sp.setSpecies(species);
				
		
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
		sess.flush();
		tx.commit();
		
		RetrieveData.retrieveEnvironmentalStudy(study);
		
		sess.close();
		Map model=new HashMap();
        model.put("envstudy",study);
        model.put("tab","study");
            
        return new ModelAndView("envstudy",model);
	}
}
