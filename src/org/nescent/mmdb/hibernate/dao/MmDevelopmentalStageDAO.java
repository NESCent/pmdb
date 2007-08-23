package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmDevelopmentalStage.
 * @see org.nescent.mmdb.hibernate.dao.MmDevelopmentalStage
 * @author MyEclipse - Hibernate Tools
 */
public class MmDevelopmentalStageDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmDevelopmentalStageDAO.class);

	//property constants
	public static final String NAME = "name";

    
    public void save(MmDevelopmentalStage transientInstance) {
        log.debug("saving MmDevelopmentalStage instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmDevelopmentalStage persistentInstance) {
        log.debug("deleting MmDevelopmentalStage instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmDevelopmentalStage findById( java.lang.Integer id) {
        log.debug("getting MmDevelopmentalStage instance with id: " + id);
        try {
            MmDevelopmentalStage instance = (MmDevelopmentalStage) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmDevelopmentalStage", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmDevelopmentalStage instance) {
        log.debug("finding MmDevelopmentalStage instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmDevelopmentalStage")
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
      log.debug("finding MmDevelopmentalStage instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmDevelopmentalStage as model where model." 
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
	
    public MmDevelopmentalStage merge(MmDevelopmentalStage detachedInstance) {
        log.debug("merging MmDevelopmentalStage instance");
        try {
            MmDevelopmentalStage result = (MmDevelopmentalStage) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmDevelopmentalStage instance) {
        log.debug("attaching dirty MmDevelopmentalStage instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmDevelopmentalStage instance) {
        log.debug("attaching clean MmDevelopmentalStage instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}