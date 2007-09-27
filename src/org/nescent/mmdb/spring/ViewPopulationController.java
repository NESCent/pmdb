package org.nescent.mmdb.spring;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmPopulationSample;
import org.nescent.mmdb.hibernate.dao.MmPopulationSampleDAO;
import org.nescent.mmdb.util.RetrieveData;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ViewPopulationController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		String id=arg0.getParameter("id");
		String tab=arg0.getParameter("tab");
		if(id==null || id.trim().equals(""))
			throw new Exception("No id specified.");
		
		MmPopulationSampleDAO popDao=new MmPopulationSampleDAO();
		MmPopulationSample pop = popDao.findById(Integer.valueOf(id));
		if(pop==null)
		{
			throw new Exception("No population found: "+id);
		}
		
		RetrieveData.retrievePopulation(pop);
		HibernateSessionFactory.closeSession();
		
		Map model=new HashMap();
        model.put("population",pop);
        if(tab!=null)
        	model.put("tab",tab);
        else
        	model.put("tab","study");
            
        return new ModelAndView("population",model);		
	}

}
