package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmTermRelationship.
 * @see org.nescent.mmdb.hibernate.dao.MmTermRelationship
 * @author MyEclipse - Hibernate Tools
 */
public class MmTermRelationshipDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmTermRelationshipDAO.class);

	//property constants

    
    public void save(MmTermRelationship transientInstance) {
        log.debug("saving MmTermRelationship instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmTermRelationship persistentInstance) {
        log.debug("deleting MmTermRelationship instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmTermRelationship findById( java.lang.Integer id) {
        log.debug("getting MmTermRelationship instance with id: " + id);
        try {
            MmTermRelationship instance = (MmTermRelationship) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmTermRelationship", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmTermRelationship instance) {
        log.debug("finding MmTermRelationship instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmTermRelationship")
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
      log.debug("finding MmTermRelationship instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmTermRelationship as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

    public MmTermRelationship merge(MmTermRelationship detachedInstance) {
        log.debug("merging MmTermRelationship instance");
        try {
            MmTermRelationship result = (MmTermRelationship) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmTermRelationship instance) {
        log.debug("attaching dirty MmTermRelationship instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmTermRelationship instance) {
        log.debug("attaching clean MmTermRelationship instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}