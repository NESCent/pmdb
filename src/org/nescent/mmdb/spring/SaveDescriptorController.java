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
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy;
import org.nescent.mmdb.hibernate.dao.MmMatingSystemStudyDAO;
import org.nescent.mmdb.hibernate.dao.MmReference;
import org.nescent.mmdb.hibernate.dao.MmReferencePart;
import org.nescent.mmdb.hibernate.dao.MmSpecies;
import org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssoc;
import org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssocDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SaveDescriptorController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String id=arg0.getParameter("id");
		if(id==null)
			throw new Exception("No id specified.");
		MmMatingSystemStudyDAO mmStDao=new MmMatingSystemStudyDAO();
		MmMatingSystemStudy study = mmStDao.findById(Integer.valueOf(id));
		if(study==null)
		{
			throw new Exception("No species discriptor found.");
		}
		
		MmSpeciesAttrCvtermAssocDAO assocDao=new MmSpeciesAttrCvtermAssocDAO();
		
		Session sess=HibernateSessionFactory.getSession();
		Transaction tr=sess.beginTransaction();
		
		try
		{
			for(Enumeration en=arg0.getParameterNames();en.hasMoreElements();)
			{
				String cvid=(String)en.nextElement();
				if(cvid.equals("id") ||cvid.equals("-2") ) continue;
				else if(cvid.equals("-1"))
				{
					String field=arg0.getParameter("-1");
					String value=arg0.getParameter("-2");
					if(field!=null && value!=null)
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
				else
				{
					MmSpeciesAttrCvtermAssoc assoc=assocDao.findById(Integer.valueOf(cvid));
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
			sess.flush();
			tr.commit();
			retrieveDiscriptor(study);
			return new ModelAndView("descriptor","descriptor",study);
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
	
	public void retrieveDiscriptor(MmMatingSystemStudy mmStudy) throws Exception
	{
		MmSpecies species=mmStudy.getMmSpecies();
		species.getFamily();
		species.getGenus();
		species.getSpecies();
		mmStudy.getLatitude();
		MmReferencePart refPart=mmStudy.getMmReferencePart();
		refPart.getName();
		MmReference ref=refPart.getMmReference();
		ref.getCitation();
		ref.getFullReference();
		
		Set mmCvTermsAssocs=mmStudy.getMmSpeciesAttrCvtermAssocs();
		
		for(Iterator it=mmCvTermsAssocs.iterator();it.hasNext();)
		{
			MmSpeciesAttrCvtermAssoc cvAssoc=(MmSpeciesAttrCvtermAssoc)it.next();
			MmCvTerm term=cvAssoc.getMmCvTerm();
			term.getCvtermOid();
			term.getDescription();
			term.getName();
			term.getNamespace();
			term.getValueType();
			cvAssoc.getValue();
		}
	}

}
