package org.nescent.mmdb.spring;

import java.util.*;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nescent.mmdb.util.*;
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
		String tab=arg0.getParameter("tab");
		if(id==null || id.trim().equals(""))
			throw new Exception("No id specified.");
		
		MmExperimentStudyDAO envStDao=new MmExperimentStudyDAO();
		MmExperimentStudy study = envStDao.findById(Integer.valueOf(id));
		if(study==null)
		{
			throw new Exception("No environmental study ound.");
		}
		
		RetrieveData.retrieveEnvironmentalStudy(study);
		HibernateSessionFactory.closeSession();
		
		Map model=new HashMap();
        model.put("envstudy",study);
        if(tab!=null)
        	model.put("tab",tab);
        else
        	model.put("tab","study");
            
        return new ModelAndView("envstudy",model);		
	
	}
}
