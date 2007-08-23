package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmMatingSystemStudy.
 * @see org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy
 * @author MyEclipse - Hibernate Tools
 */
public class MmMatingSystemStudyDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmMatingSystemStudyDAO.class);

	//property constants
	public static final String LATITUDE = "latitude";

    
    public void save(MmMatingSystemStudy transientInstance) {
        log.debug("saving MmMatingSystemStudy instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmMatingSystemStudy persistentInstance) {
        log.debug("deleting MmMatingSystemStudy instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmMatingSystemStudy findById( java.lang.Integer id) {
        log.debug("getting MmMatingSystemStudy instance with id: " + id);
        try {
            MmMatingSystemStudy instance = (MmMatingSystemStudy) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmMatingSystemStudy instance) {
        log.debug("finding MmMatingSystemStudy instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmMatingSystemStudy")
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
      log.debug("finding MmMatingSystemStudy instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmMatingSystemStudy as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}
	
    public MmMatingSystemStudy merge(MmMatingSystemStudy detachedInstance) {
        log.debug("merging MmMatingSystemStudy instance");
        try {
            MmMatingSystemStudy result = (MmMatingSystemStudy) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmMatingSystemStudy instance) {
        log.debug("attaching dirty MmMatingSystemStudy instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmMatingSystemStudy instance) {
        log.debug("attaching clean MmMatingSystemStudy instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}