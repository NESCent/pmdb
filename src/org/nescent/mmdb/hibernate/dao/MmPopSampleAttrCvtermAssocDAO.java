package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmPopSampleAttrCvtermAssoc.
 * @see org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssoc
 * @author MyEclipse - Hibernate Tools
 */
public class MmPopSampleAttrCvtermAssocDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmPopSampleAttrCvtermAssocDAO.class);

	//property constants
	public static final String VALUE = "value";

    
    public void save(MmPopSampleAttrCvtermAssoc transientInstance) {
        log.debug("saving MmPopSampleAttrCvtermAssoc instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmPopSampleAttrCvtermAssoc persistentInstance) {
        log.debug("deleting MmPopSampleAttrCvtermAssoc instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmPopSampleAttrCvtermAssoc findById( java.lang.Integer id) {
        log.debug("getting MmPopSampleAttrCvtermAssoc instance with id: " + id);
        try {
            MmPopSampleAttrCvtermAssoc instance = (MmPopSampleAttrCvtermAssoc) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssoc", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmPopSampleAttrCvtermAssoc instance) {
        log.debug("finding MmPopSampleAttrCvtermAssoc instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmPopSampleAttrCvtermAssoc")
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
      log.debug("finding MmPopSampleAttrCvtermAssoc instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmPopSampleAttrCvtermAssoc as model where model." 
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
	
    public MmPopSampleAttrCvtermAssoc merge(MmPopSampleAttrCvtermAssoc detachedInstance) {
        log.debug("merging MmPopSampleAttrCvtermAssoc instance");
        try {
            MmPopSampleAttrCvtermAssoc result = (MmPopSampleAttrCvtermAssoc) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmPopSampleAttrCvtermAssoc instance) {
        log.debug("attaching dirty MmPopSampleAttrCvtermAssoc instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmPopSampleAttrCvtermAssoc instance) {
        log.debug("attaching clean MmPopSampleAttrCvtermAssoc instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}