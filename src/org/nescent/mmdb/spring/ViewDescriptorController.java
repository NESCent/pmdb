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
		
		retrieveDiscriptor(study);
		HibernateSessionFactory.closeSession();
		return new ModelAndView("descriptor","descriptor",study);
	
	}
	
	public void retrieveDiscriptor(MmMatingSystemStudy mmStudy) throws Exception
	{
		MmSpecies species=mmStudy.getMmSpecies();
		if(species!=null)
		{
			species.getFamily();
			species.getGenus();
			species.getSpecies();
		}
		mmStudy.getLatitude();
		MmReferencePart refPart=mmStudy.getMmReferencePart();
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
		Set mmCvTermsAssocs=mmStudy.getMmSpeciesAttrCvtermAssocs();
		
		for(Iterator it=mmCvTermsAssocs.iterator();it.hasNext();)
		{
			MmSpeciesAttrCvtermAssoc cvAssoc=(MmSpeciesAttrCvtermAssoc)it.next();
			if(cvAssoc!=null)
			{
				cvAssoc.getValue();
				MmCvTerm term=cvAssoc.getMmCvTerm();
				if(term!=null)
				{
					term.getCvtermOid();
					term.getDescription();
					term.getName();
					term.getNamespace();
					term.getValueType();
				}
			}
		}
	}
}
