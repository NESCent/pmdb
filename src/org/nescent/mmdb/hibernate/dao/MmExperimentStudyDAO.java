package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmExperimentStudy.
 * @see org.nescent.mmdb.hibernate.dao.MmExperimentStudy
 * @author MyEclipse - Hibernate Tools
 */
public class MmExperimentStudyDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmExperimentStudyDAO.class);

	//property constants
	public static final String NAME = "name";

    
    public void save(MmExperimentStudy transientInstance) {
        log.debug("saving MmExperimentStudy instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmExperimentStudy persistentInstance) {
        log.debug("deleting MmExperimentStudy instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmExperimentStudy findById( java.lang.Integer id) {
        log.debug("getting MmExperimentStudy instance with id: " + id);
        try {
            MmExperimentStudy instance = (MmExperimentStudy) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmExperimentStudy", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmExperimentStudy instance) {
        log.debug("finding MmExperimentStudy instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmExperimentStudy")
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
      log.debug("finding MmExperimentStudy instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmExperimentStudy as model where model." 
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
	
    public MmExperimentStudy merge(MmExperimentStudy detachedInstance) {
        log.debug("merging MmExperimentStudy instance");
        try {
            MmExperimentStudy result = (MmExperimentStudy) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmExperimentStudy instance) {
        log.debug("attaching dirty MmExperimentStudy instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmExperimentStudy instance) {
        log.debug("attaching clean MmExperimentStudy instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}