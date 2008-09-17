package org.nescent.mmdb.spring;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmSpeciesDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ViewDescriptorController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String id=arg0.getParameter("id");
		if(id==null || id.trim().equals(""))
			throw new Exception("No id specified.");
		
		MmMatingSystemStudyDAO mmStDao=new MmMatingSystemStudyDAO();
		MmMatingSystemStudy study = mmStDao.findById(Integer.valueOf(id));
		if(study==null)
		{
			throw new Exception("No species discriptor found.");
		}
		
		return new ModelAndView("descriptor","descriptor",study);
	
	}
	

}
