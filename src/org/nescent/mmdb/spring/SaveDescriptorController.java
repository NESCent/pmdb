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
import org.nescent.mmdb.util.Fields;
import org.nescent.mmdb.util.RetrieveData;

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
		
		Session sess=HibernateSessionFactory.getSession();
		Transaction tr=sess.beginTransaction();
		
		try
		{
			saveSpeciesReference(study,arg0,sess);
			saveAttributes(study,arg0,sess);
			sess.flush();
			tr.commit();
			RetrieveData.retrieveDiscriptor(study);
			return new ModelAndView("descriptor","descriptor",study);
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
	
	public void saveAttributes(MmMatingSystemStudy study, HttpServletRequest arg0, Session sess) throws Exception
	{
		String id=arg0.getParameter("id");
		MmSpeciesAttrCvtermAssocDAO assocDao=new MmSpeciesAttrCvtermAssocDAO();
		
		
		for(Enumeration en=arg0.getParameterNames();en.hasMoreElements();)
		{
			String cvid=(String)en.nextElement();
			
			if(cvid.equals("id") || cvid.equals("-2")|| (cvid.indexOf("-")!=-1 && ! cvid.equals("-1")) ) continue;
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
	
	}
	public void saveSpeciesReference(MmMatingSystemStudy study,HttpServletRequest arg0, Session sess) throws Exception
	{
		String family=arg0.getParameter(Fields.getFieldId("Family"));
		String genus=arg0.getParameter(Fields.getFieldId("genus"));
		String species=arg0.getParameter(Fields.getFieldId("species"));
		String citation=arg0.getParameter(Fields.getFieldId("citation"));
		String full=arg0.getParameter(Fields.getFieldId("fullreference"));
		String part=arg0.getParameter(Fields.getFieldId("part"));
		String latitude=arg0.getParameter(Fields.getFieldId("latitude"));
		
		if(genus!=null && genus.trim().equals(""))
			throw new  Exception("Empty genus.");
		if(species!=null && species.trim().equals(""))
			throw new  Exception("Empty species.");
		if(citation!=null && citation.trim().equals(""))
			throw new  Exception("Empty citation.");	
		
		
		
		//get or create species
		MmSpecies sp=study.getMmSpecies();
		
		if(sp==null)
			throw new Exception("No species found for the mating system study.");
		
		if(family!=null)
			sp.setFamily(family);
		if(genus!=null)	
			sp.setGenus(genus);
		if(species!=null)
		sp.setSpecies(species);
		
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
	}
}
