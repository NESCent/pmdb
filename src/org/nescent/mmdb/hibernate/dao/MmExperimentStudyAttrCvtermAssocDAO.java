package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmExperimentStudyAttrCvtermAssoc.
 * @see org.nescent.mmdb.hibernate.dao.MmExperimentStudyAttrCvtermAssoc
 * @author MyEclipse - Hibernate Tools
 */
public class MmExperimentStudyAttrCvtermAssocDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmExperimentStudyAttrCvtermAssocDAO.class);

	//property constants
	public static final String VALUE = "value";

    
    public void save(MmExperimentStudyAttrCvtermAssoc transientInstance) {
        log.debug("saving MmExperimentStudyAttrCvtermAssoc instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmExperimentStudyAttrCvtermAssoc persistentInstance) {
        log.debug("deleting MmExperimentStudyAttrCvtermAssoc instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmExperimentStudyAttrCvtermAssoc findById( java.lang.Integer id) {
        log.debug("getting MmExperimentStudyAttrCvtermAssoc instance with id: " + id);
        try {
            MmExperimentStudyAttrCvtermAssoc instance = (MmExperimentStudyAttrCvtermAssoc) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmExperimentStudyAttrCvtermAssoc", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmExperimentStudyAttrCvtermAssoc instance) {
        log.debug("finding MmExperimentStudyAttrCvtermAssoc instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmExperimentStudyAttrCvtermAssoc")
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
      log.debug("finding MmExperimentStudyAttrCvtermAssoc instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmExperimentStudyAttrCvtermAssoc as model where model." 
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
	
    public MmExperimentStudyAttrCvtermAssoc merge(MmExperimentStudyAttrCvtermAssoc detachedInstance) {
        log.debug("merging MmExperimentStudyAttrCvtermAssoc instance");
        try {
            MmExperimentStudyAttrCvtermAssoc result = (MmExperimentStudyAttrCvtermAssoc) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmExperimentStudyAttrCvtermAssoc instance) {
        log.debug("attaching dirty MmExperimentStudyAttrCvtermAssoc instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmExperimentStudyAttrCvtermAssoc instance) {
        log.debug("attaching clean MmExperimentStudyAttrCvtermAssoc instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}