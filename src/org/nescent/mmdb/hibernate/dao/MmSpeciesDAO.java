package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmSpecies.
 * @see org.nescent.mmdb.hibernate.dao.MmSpecies
 * @author MyEclipse - Hibernate Tools
 */
public class MmSpeciesDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmSpeciesDAO.class);

	//property constants
	public static final String FAMILY = "family";
	public static final String GENUS = "genus";
	public static final String SPECIES = "species";

    
    public void save(MmSpecies transientInstance) {
        log.debug("saving MmSpecies instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmSpecies persistentInstance) {
        log.debug("deleting MmSpecies instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmSpecies findById( java.lang.Integer id) {
        log.debug("getting MmSpecies instance with id: " + id);
        try {
            MmSpecies instance = (MmSpecies) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmSpecies", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmSpecies instance) {
        log.debug("finding MmSpecies instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmSpecies")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding MmSpecies instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmSpecies as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByFamily(Object family) {
		return findByProperty(FAMILY, family);
	}
	
	public List findByGenus(Object genus) {
		return findByProperty(GENUS, genus);
	}
	
	public List findBySpecies(Object species) {
		return findByProperty(SPECIES, species);
	}
	 public List findByName(String family, String genus, String species) {
	        log.debug("finding MmSpecies instance with name.");
	        try {
	            boolean first=true;
	        	String queryString = "from MmSpecies as model where " ;
	        	if(family!=null && !family.trim().equals(""))
	        	{
	        		queryString+=" model.family like :family ";
	        		first=false;
	        	}
	        	if(genus!=null && !genus.trim().equals(""))
	        	{
	        		if(!first)
	        			queryString+=" OR ";	
	        		queryString+=" model.genus like :genus ";
	        		first=false;
	        	}
	        	if(species!=null && !species.trim().equals(""))
	        	{
	        		if(!first)
	        			queryString+=" OR ";	
	        		queryString+=" model.species like :species ";
	        		first=false;
	        	}
	        	queryString+=" order by model.family,model.genus, model.species";
	        	Query queryObject = getSession().createQuery(queryString);
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
	        	
	        	
	        	return queryObject.list();
	        } catch (RuntimeException re) {
	           log.error("find by name failed", re);
	           throw re;
	        }
	  	}
    public MmSpecies merge(MmSpecies detachedInstance) {
        log.debug("merging MmSpecies instance");
        try {
            MmSpecies result = (MmSpecies) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmSpecies instance) {
        log.debug("attaching dirty MmSpecies instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmSpecies instance) {
        log.debug("attaching clean MmSpecies instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}