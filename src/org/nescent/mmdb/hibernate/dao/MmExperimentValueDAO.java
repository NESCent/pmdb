package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmExperimentValue.
 * @see org.nescent.mmdb.hibernate.dao.MmExperimentValue
 * @author MyEclipse - Hibernate Tools
 */
public class MmExperimentValueDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmExperimentValueDAO.class);

	//property constants
	public static final String VALUE = "value";

    
    public void save(MmExperimentValue transientInstance) {
        log.debug("saving MmExperimentValue instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmExperimentValue persistentInstance) {
        log.debug("deleting MmExperimentValue instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmExperimentValue findById( java.lang.Integer id) {
        log.debug("getting MmExperimentValue instance with id: " + id);
        try {
            MmExperimentValue instance = (MmExperimentValue) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmExperimentValue", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmExperimentValue instance) {
        log.debug("finding MmExperimentValue instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmExperimentValue")
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
      log.debug("finding MmExperimentValue instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmExperimentValue as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByValue(Object value) {
		return findByProperty(VALUE, value);
	}
	
    public MmExperimentValue merge(MmExperimentValue detachedInstance) {
        log.debug("merging MmExperimentValue instance");
        try {
            MmExperimentValue result = (MmExperimentValue) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmExperimentValue instance) {
        log.debug("attaching dirty MmExperimentValue instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmExperimentValue instance) {
        log.debug("attaching clean MmExperimentValue instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}