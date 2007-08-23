package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmPopulationSample.
 * @see org.nescent.mmdb.hibernate.dao.MmPopulationSample
 * @author MyEclipse - Hibernate Tools
 */
public class MmPopulationSampleDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmPopulationSampleDAO.class);

	//property constants
	public static final String NAME = "name";
	public static final String GEOGRAPHIC_LOCATION = "geographicLocation";
	public static final String ENVIRONMENT = "environment";
	public static final String POPULATION = "population";
	public static final String YEAR = "year";
	public static final String COMMENTS = "comments";

    
    public void save(MmPopulationSample transientInstance) {
        log.debug("saving MmPopulationSample instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmPopulationSample persistentInstance) {
        log.debug("deleting MmPopulationSample instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmPopulationSample findById( java.lang.Integer id) {
        log.debug("getting MmPopulationSample instance with id: " + id);
        try {
            MmPopulationSample instance = (MmPopulationSample) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmPopulationSample", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmPopulationSample instance) {
        log.debug("finding MmPopulationSample instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmPopulationSample")
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
      log.debug("finding MmPopulationSample instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmPopulationSample as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}
	
	public List findByGeographicLocation(Object geographicLocation) {
		return findByProperty(GEOGRAPHIC_LOCATION, geographicLocation);
	}
	
	public List findByEnvironment(Object environment) {
		return findByProperty(ENVIRONMENT, environment);
	}
	
	public List findByPopulation(Object population) {
		return findByProperty(POPULATION, population);
	}
	
	public List findByYear(Object year) {
		return findByProperty(YEAR, year);
	}
	
	public List findByComments(Object comments) {
		return findByProperty(COMMENTS, comments);
	}
	
    public MmPopulationSample merge(MmPopulationSample detachedInstance) {
        log.debug("merging MmPopulationSample instance");
        try {
            MmPopulationSample result = (MmPopulationSample) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmPopulationSample instance) {
        log.debug("attaching dirty MmPopulationSample instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmPopulationSample instance) {
        log.debug("attaching clean MmPopulationSample instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}