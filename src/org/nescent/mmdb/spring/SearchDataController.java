package org.nescent.mmdb.spring;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.nescent.mmdb.util.RetrieveData;
import org.nescent.mmdb.util.Species;

public class SearchDataController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		//species
		String species=arg0.getParameter("species");
		String family=arg0.getParameter("family");
		String genus=arg0.getParameter("genus");
		String study_type=arg0.getParameter("study_type");
		
		/*
		//reference
		String citation=arg0.getParameter("citation");
		String full=arg0.getParameter("fullreference");
		String part=arg0.getParameter("part");
		
		//populaiton
		
		String populationName=arg0.getParameter("populationName");
		String geographicLocation=arg0.getParameter("geographicLocation");
		String environment=arg0.getParameter("environment");
		String population=arg0.getParameter("population");
		String comments=arg0.getParameter("comments");
		String year=arg0.getParameter("year");
		*/
		//species attribute
		String species_attribute=arg0.getParameter("species_attribute");
		String species_attribute_opt=arg0.getParameter("species_attribute_opt");
		String species_attribute_value=arg0.getParameter("species_attribute_value");
		
		//measurement attribute
		String measurement_attribute=arg0.getParameter("measurement_attribute");
		String measurement_attribute_opt=arg0.getParameter("measurement_attribute_opt");
		String measurement_attribute_value=arg0.getParameter("measurement_attribute_value");
		List list=new ArrayList();
		
		if((study_type==null || study_type.trim().equals("")))
		{
			list = getSpeciesBasedOnSpeciesAttributes(family,genus,species, species_attribute, species_attribute_opt, species_attribute_value);
		}
		else
		{
			List list1=getSpeciesBasedOnSpeciesAttributes(family,genus,species, species_attribute, species_attribute_opt, species_attribute_value);
		//get species based on reference
		//List list2=getSpeciesBasedOnSpeciesReference(family,genus,species,citation,full,part);
		//get species based on population attributes
			List list3=null;
		if(study_type.indexOf("inbreed")!=-1)
			list3=getSpeciesBasedOnPopulationAttributes(family,genus,species, measurement_attribute, measurement_attribute_opt, measurement_attribute_value);
		else if(study_type.indexOf("pollination")!=-1)
			list3=getSpeciesBasedOnPopulationValues(family,genus,species, measurement_attribute, measurement_attribute_opt, measurement_attribute_value);
		//get species based on population reference
		//List list4=getSpeciesBasedOnPopulationReference(family,genus,species,popcitation,popfull,poppart);
		Map map=new HashMap();
		Map map1=new HashMap();
		
		if(list1!=null && list3==null)
		{
			list=list1;
		}
		else if(list1==null && list3!=null)	
		{
			list=list3;
		}
			
		else if(list1!=null && list3!=null)
		{
			for(int i=0;i<list1.size();i++)
			{
				MmSpecies sp=(MmSpecies)list1.get(i);
				map.put(sp.getSpeciesOid(),sp);
			}
			for(int i=0;i<list3.size();i++)
			{
				MmSpecies sp=(MmSpecies)list3.get(i);
				map1.put(sp.getSpeciesOid(),sp);
			}
			for(Iterator it=map.keySet().iterator();it.hasNext();)
			{
				Object key=it.next();
				if(map1.containsKey(key))
				{
					MmSpecies sp=(MmSpecies)map.get(key);
					list.add(sp);
				}
					
			}
		}	
		}
		List sps=new ArrayList();
		if(list==null)list=new ArrayList();
		for(int i=0;i<list.size();i++)
		{
			MmSpecies sp=(MmSpecies)list.get(i);
			sps.add(getSpecies(sp));
		}
		HibernateSessionFactory.closeSession();

		return new ModelAndView("speciesList","list",sps);
	}
	
	public static void main(String [] agrs)
	{
		SearchDataController t=new SearchDataController();
		t.test();
	}
	public void test()
	{
		try{
		//species
		String species="";//"se";
		String family="";//"Asteraceae";
		String genus="";
		
		//reference
		/*
		String citation="citation";
		String full="fullreference";
		String part="part";
		*/
		//reference
		/*
		String popcitation="popcitation";
		String popfull="popfullreference";
		String poppart="poppart";
		*/
		//populaiton
		/*
		String populationName="populationName";
		String geographicLocation="geographicLocation";
		String environment="environment";
		String population="population";
		String comments="comments";
		String year="year";
		*/
		//species attribute
		String species_attribute="";//growth_form";
		String species_attribute_opt="";
		String species_attribute_value="";//"herb";
		
		//measurement attribute
		String measurement_attribute="(Fci/Fio)";//"Fci-S/F";
		String measurement_attribute_opt="like";
		String measurement_attribute_value="0.971";
		
	
		List list1=getSpeciesBasedOnSpeciesAttributes(family,genus,species, species_attribute, species_attribute_opt, species_attribute_value);
		System.out.println(list1==null?"Empty list1":list1.size());
		//get species based on reference
		//List list2=getSpeciesBasedOnSpeciesReference(family,genus,species,citation,full,part);
		//get species based on population attributes
		List list3=getSpeciesBasedOnPopulationAttributes(family,genus,species, measurement_attribute, measurement_attribute_opt, measurement_attribute_value);
		System.out.println(list3==null?"Empty list3":list3.size());
		//get species based on population reference
		//List list4=getSpeciesBasedOnPopulationReference(family,genus,species,popcitation,popfull,poppart);
		Map map=new HashMap();
		Map map1=new HashMap();
		List list=new ArrayList();
		if(list1!=null && list3==null)
		{
			
			for(int i=0;i<list1.size();i++)
			{
				MmSpecies sp=(MmSpecies)list1.get(i);
				list.add(sp.getSpeciesOid());
			}
			
		}
		else if(list1==null && list3!=null)	
		{
			for(int i=0;i<list3.size();i++)
			{
				MmSpecies sp=(MmSpecies)list3.get(i);
				list.add(sp.getSpeciesOid());
			}
		}
			
		else if(list1!=null && list3!=null)
		{
			for(int i=0;i<list1.size();i++)
			{
				MmSpecies sp=(MmSpecies)list1.get(i);
				map.put(sp.getSpeciesOid(),sp);
			}
			for(int i=0;i<list3.size();i++)
			{
				MmSpecies sp=(MmSpecies)list3.get(i);
				map1.put(sp.getSpeciesOid(),sp);
			}
			for(Iterator it=map.keySet().iterator();it.hasNext();)
			{
				Object key=it.next();
				if(map1.containsKey(key))
				{
					MmSpecies sp=(MmSpecies)map.get(key);
					list.add(sp.getSpeciesOid());
				}
					
			}
		}	
		
		Session sess=HibernateSessionFactory.getSession();	
		for(int i=0;i<list.size();i++)
		{
			Integer id=(Integer)list.get(i);
			
			
			System.out.println("retrieving species: " + id);
			MmSpecies sp=RetrieveData.retrieveSpecies(id);
			
			System.out.println(sp.getGenus()+" "+sp.getSpecies()+" ("+sp.getFamily()+")");
			
		}
		sess.close();
		}catch(Exception e){
			System.out.println(e);
		}
			
	}
	
	public List getSpeciesBasedOnSpeciesAttributes(String family, String genus, String species, 
		String species_attribute, String species_attribute_opt, String species_attribute_value) throws Exception
	{
		if(
			(family==null || family.trim().equals("")) && 
			(genus==null || genus.trim().equals("")) &&
			(species==null || species.trim().equals("")) &&
			(species_attribute==null || species_attribute.trim().equals(""))
			)
			return null;	
		if(species_attribute_value==null)
			species_attribute_value="";
		
		String sql="SELECT DISTINCT species FROM MmSpecies species "+
					"INNER JOIN species.mmMatingSystemStudies.mmSpeciesAttrCvtermAssocs assoc INNER JOIN assoc.mmCvTerm term";
		String with="";
		boolean first=true;
		if(family!=null && !family.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.family like :family ";
			first=false;
		}
			 
		if(genus!=null && !genus.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.genus like :genus ";
			first=false;
		}
		
		if(species!=null && !species.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.species like :species ";
			first=false;
		}
		
		if(species_attribute!=null && !species_attribute.trim().equals(""))
		{
			if(species_attribute_value!=null)
			{
				if(!first)
					with+=" AND ";	
				
				if(species_attribute_opt==null || species_attribute_opt.trim().equals(""))
					species_attribute_opt="like";
					
				with+=" term.name like :species_attribute AND assoc.value " + species_attribute_opt +" :species_attribute_value";
				
				first=false;
			}
		}
		if(with!="")
			sql+=" WHERE "+with;
		
		Session sess=HibernateSessionFactory.getSession();
		
		Query queryObject = sess.createQuery(sql);
	    if(family!=null && !family.trim().equals(""))
	    {
	      	queryObject.setString("family", '%'+family+'%');
    	}
    	if(genus!=null && !genus.trim().equals(""))
    	{
    		queryObject.setString("genus", '%'+genus+'%');
    	}
    	if(species!=null && !species.trim().equals(""))
    	{
    		queryObject.setString("species", '%'+species+'%');
    	}
	    if(species_attribute!=null && !species_attribute.trim().equals(""))
		{
			if(species_attribute_value!=null)
			{
				queryObject.setString("species_attribute", species_attribute);	
				if(species_attribute_opt!=null && species_attribute_opt.trim().equals("like"))
					queryObject.setString("species_attribute_value", '%'+species_attribute_value+'%');	
				else
					queryObject.setString("species_attribute_value", species_attribute_value);	
			}
		}    	
		
		List specieses = queryObject.list();
		
				
		return specieses;
	}
	
	public List getSpeciesBasedOnSpeciesReference(String family, String genus, String species, 
		String citation, String full, String part) throws Exception
	{
		if(
			(family==null || family.trim().equals("")) && 
			(genus==null || genus.trim().equals("")) &&
			(species==null || species.trim().equals("")) &&
			(citation==null || citation.trim().equals("")) &&
			(full==null || full.trim().equals("")) && 
			(part==null || part.trim().equals(""))
			)
			return null;	
		
		
		String sql="SELECT DISTINCT species FROM MmSpecies species "+
					"INNER JOIN species.mmMatingSystemStudies.mmReferencePart part INNER JOIN part.mmReference ref";
		String with="";
		boolean first=true;
		if(family!=null && !family.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.family like :family ";
			first=false;
		}
			 
		if(genus!=null && !genus.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.genus like :genus ";
			first=false;
		}
		
		if(species!=null && !species.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.species like :species ";
			first=false;
		}
		
		if(citation!=null && !citation.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
				
			with+=" ref.citation like :citation ";
			first=false;
			
		}
		if(full!=null && !full.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
				
			with+=" ref.fullReference like :full ";
			first=false;
			
		}
		if(part!=null && !part.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
				
			with+=" part.name like :part ";
			first=false;
			
		}
		if(with!="")
			sql+=" WHERE "+with;
			
		Session sess=HibernateSessionFactory.getSession();
		
		Query queryObject = sess.createQuery(sql);
	    if(family!=null && !family.trim().equals(""))
	    {
	      	queryObject.setString("family", '%'+family+'%');
    	}
    	if(genus!=null && !genus.trim().equals(""))
    	{
    		queryObject.setString("genus", '%'+genus+'%');
    	}
    	if(species!=null && !species.trim().equals(""))
    	{
    		queryObject.setString("species", '%'+species+'%');
    	}
	    if(citation!=null && !citation.trim().equals(""))
		{
			queryObject.setString("citation", '%'+citation+'%');
			
		}
		if(full!=null && !full.trim().equals(""))
		{
			queryObject.setString("full", '%'+full+'%');
		}
		if(part!=null && !part.trim().equals(""))
		{
			queryObject.setString("part", '%'+part+'%');
		}	
			
		List specieses = queryObject.list();
		sess.close();
		return specieses;
	}
	
	public List getSpeciesBasedOnPopulationAttributes(String family, String genus, String species, 
		String attribute, String attribute_opt, String attribute_value) throws Exception
	{
		
		
		String sql="SELECT DISTINCT species FROM MmSpecies species "+
					"INNER JOIN species.mmPopulationSamples.mmExperimentStudies study INNER JOIN study.mmExperimentStudyAttrCvtermAssocs value INNER JOIN value.mmCvTerm term";
		
		String with="";
		boolean first=true;
		if(family!=null && !family.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.family like :family ";
			first=false;
		}
			 
		if(genus!=null && !genus.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.genus like :genus ";
			first=false;
		}
		
		if(species!=null && !species.trim().equals(""))
		{
			if(!first)
				with+=" AND ";	
			with+=" species.species like :species ";
			first=false;
		}
		
		if(attribute!=null && !attribute.trim().equals(""))
		{
			if(attribute_value!=null && !attribute_value.trim().equals(""))
			{
				if(!first)
					with+=" AND ";	
				if(attribute_opt==null || attribute_opt.trim().equals(""))
					attribute_opt="like";
				
				with+=" term.name = :attribute AND value.value " + attribute_opt +" :attribute_value";
				
				first=false;
			}
		}
		if(with=="")
			with+=" size(study.mmExperimentStudyAttrCvtermAssocs)>0";
		else
			with+=" AND size(study.mmExperimentStudyAttrCvtermAssocs)>0";
		if(with!="")
			sql+=" WHERE "+with;
		
		Session sess=HibernateSessionFactory.getSession();
		
		Query queryObject = sess.createQuery(sql);
	    if(family!=null && !family.trim().equals(""))
	    {
	      	queryObject.setString("family", '%'+family+'%');
    	}
    	if(genus!=null && !genus.trim().equals(""))
    	{
    		queryObject.setString("genus", '%'+genus+'%');
    	}
    	if(species!=null && !species.trim().equals(""))
    	{
    		queryObject.setString("species", '%'+species+'%');
    	}
	    
	    if(attribute!=null && !attribute.trim().equals(""))
		{
			if(attribute_value!=null && !attribute_value.trim().equals(""))
			{
				queryObject.setString("attribute", attribute);	
				if(attribute_opt!=null && attribute_opt.trim().equals("like"))
					queryObject.setString("attribute_value", "%"+attribute_value+"%");	
				else
					queryObject.setString("attribute_value", attribute_value);
				
			}
		}    	
			
		List specieses = queryObject.list();
		
		return specieses;
	}
	public List getSpeciesBasedOnPopulationValues(String family, String genus, String species, 
			String attribute, String attribute_opt, String attribute_value) throws Exception
		{
			
			
			String sql="SELECT DISTINCT species FROM MmSpecies species "+
						"INNER JOIN species.mmPopulationSamples.mmExperimentStudies study INNER JOIN study.mmExperimentValues value INNER JOIN value.mmCvTerm term";
			String with="";
			boolean first=true;
			if(family!=null && !family.trim().equals(""))
			{
				if(!first)
					with+=" AND ";	
				with+=" species.family like :family ";
				first=false;
			}
				 
			if(genus!=null && !genus.trim().equals(""))
			{
				if(!first)
					with+=" AND ";	
				with+=" species.genus like :genus ";
				first=false;
			}
			
			if(species!=null && !species.trim().equals(""))
			{
				if(!first)
					with+=" AND ";	
				with+=" species.species like :species ";
				first=false;
			}
			
			if(attribute!=null && !attribute.trim().equals(""))
			{
				if(attribute_value!=null && !attribute_value.trim().equals(""))
				{
					if(!first)
						with+=" AND ";	
					if(attribute_opt==null || attribute_opt.trim().equals(""))
						attribute_opt="like";
					
					with+=" term.name = :attribute AND value.value " + attribute_opt +" :attribute_value";
					
					first=false;
				}
			}
			if(with=="")
				with+=" size(study.mmExperimentValues)>0";
			else
				with+=" AND size(study.mmExperimentValues)>0";
			if(with!="")
				sql+=" WHERE "+with;
			
			Session sess=HibernateSessionFactory.getSession();
			
			Query queryObject = sess.createQuery(sql);
		    if(family!=null && !family.trim().equals(""))
		    {
		      	queryObject.setString("family", '%'+family+'%');
	    	}
	    	if(genus!=null && !genus.trim().equals(""))
	    	{
	    		queryObject.setString("genus", '%'+genus+'%');
	    	}
	    	if(species!=null && !species.trim().equals(""))
	    	{
	    		queryObject.setString("species", '%'+species+'%');
	    	}
		    
		    if(attribute!=null && !attribute.trim().equals(""))
			{
				if(attribute_value!=null && !attribute_value.trim().equals(""))
				{
					queryObject.setString("attribute", attribute);	
					if(attribute_opt!=null && attribute_opt.trim().equals("like"))
						queryObject.setString("attribute_value", "%"+attribute_value+"%");	
					else
						queryObject.setString("attribute_value", attribute_value);
					
				}
			}    	
				
			List specieses = queryObject.list();
			
			return specieses;
		}
	public Species getSpecies(MmSpecies species){
		Species sp=new Species();
		sp.setFamily(species.getFamily());
		sp.setGenus(species.getGenus());
		sp.setSpecies(species.getSpecies());
		sp.setId(species.getSpeciesOid());
		int inbreedNum=0;
		int outNum=0;
		int pollNum=0;
		sp.setPopulationNum(species.getMmPopulationSamples().size());
		
		Session sess=HibernateSessionFactory.getSession();
		String hql="SELECT  ";
		hql+= "count(study),study.mmCvTerm.name FROM MmExperimentStudy study ";
		hql+=" INNER JOIN study.mmPopulationSample.mmSpecies species";
		hql+=" WHERE species.species='"+sp.getSpecies()+"'";
		hql+=" AND species.genus='"+ sp.getGenus()+"'";
		hql+=" AND species.family='"+sp.getFamily()+"'";
		hql+=" GROUP BY study.mmCvTerm.name ";
		//hql+=" having species.species='latifolia'";
			
		Query q=sess.createQuery(hql);
		List list=q.list();
		for(int i=0;i<list.size();i++)
		{
			
			Object [] objs=(Object [])list.get(i);
			
			for(int j=0;j<objs.length;j++)
			{
				String studyName=(String)objs[1];
				if(studyName.indexOf("inbreed")!=-1)
					inbreedNum=((Integer)objs[0]).intValue();
				if(studyName.indexOf("outcross")!=-1)
					outNum=((Integer)objs[0]).intValue();
				if(studyName.indexOf("pollination")!=-1)
					pollNum=((Integer)objs[0]).intValue();
			}
		}
		
		sp.setInbreedNum(inbreedNum);
		sp.setOutcrossingNum(outNum);
		sp.setPollinationNum(pollNum);
		
		return sp;
	}
}
