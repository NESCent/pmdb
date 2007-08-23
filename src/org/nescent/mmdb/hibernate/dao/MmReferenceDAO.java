package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmReference.
 * @see org.nescent.mmdb.hibernate.dao.MmReference
 * @author MyEclipse - Hibernate Tools
 */
public class MmReferenceDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmReferenceDAO.class);

	//property constants
	public static final String CITATION = "citation";
	public static final String FULL_REFERENCE = "fullReference";

    
    public void save(MmReference transientInstance) {
        log.debug("saving MmReference instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmReference persistentInstance) {
        log.debug("deleting MmReference instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmReference findById( java.lang.Integer id) {
        log.debug("getting MmReference instance with id: " + id);
        try {
            MmReference instance = (MmReference) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmReference", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmReference instance) {
        log.debug("finding MmReference instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmReference")
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
      log.debug("finding MmReference instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmReference as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByCitation(Object citation) {
		return findByProperty(CITATION, citation);
	}
	
	public List findByFullReference(Object fullReference) {
		return findByProperty(FULL_REFERENCE, fullReference);
	}
	
    public MmReference merge(MmReference detachedInstance) {
        log.debug("merging MmReference instance");
        try {
            MmReference result = (MmReference) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmReference instance) {
        log.debug("attaching dirty MmReference instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmReference instance) {
        log.debug("attaching clean MmReference instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}