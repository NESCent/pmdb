package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmSpeciesAttrCvtermAssoc.
 * @see org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssoc
 * @author MyEclipse - Hibernate Tools
 */
public class MmSpeciesAttrCvtermAssocDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmSpeciesAttrCvtermAssocDAO.class);

	//property constants
	public static final String VALUE = "value";

    
    public void save(MmSpeciesAttrCvtermAssoc transientInstance) {
        log.debug("saving MmSpeciesAttrCvtermAssoc instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmSpeciesAttrCvtermAssoc persistentInstance) {
        log.debug("deleting MmSpeciesAttrCvtermAssoc instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmSpeciesAttrCvtermAssoc findById( java.lang.Integer id) {
        log.debug("getting MmSpeciesAttrCvtermAssoc instance with id: " + id);
        try {
            MmSpeciesAttrCvtermAssoc instance = (MmSpeciesAttrCvtermAssoc) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssoc", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmSpeciesAttrCvtermAssoc instance) {
        log.debug("finding MmSpeciesAttrCvtermAssoc instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmSpeciesAttrCvtermAssoc")
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
      log.debug("finding MmSpeciesAttrCvtermAssoc instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmSpeciesAttrCvtermAssoc as model where model." 
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
	
    public MmSpeciesAttrCvtermAssoc merge(MmSpeciesAttrCvtermAssoc detachedInstance) {
        log.debug("merging MmSpeciesAttrCvtermAssoc instance");
        try {
            MmSpeciesAttrCvtermAssoc result = (MmSpeciesAttrCvtermAssoc) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmSpeciesAttrCvtermAssoc instance) {
        log.debug("attaching dirty MmSpeciesAttrCvtermAssoc instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmSpeciesAttrCvtermAssoc instance) {
        log.debug("attaching clean MmSpeciesAttrCvtermAssoc instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}