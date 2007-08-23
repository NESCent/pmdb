package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmUserAcctRoleAssoc.
 * @see org.nescent.mmdb.hibernate.dao.MmUserAcctRoleAssoc
 * @author MyEclipse - Hibernate Tools
 */
public class MmUserAcctRoleAssocDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmUserAcctRoleAssocDAO.class);

	//property constants

    
    public void save(MmUserAcctRoleAssoc transientInstance) {
        log.debug("saving MmUserAcctRoleAssoc instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmUserAcctRoleAssoc persistentInstance) {
        log.debug("deleting MmUserAcctRoleAssoc instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmUserAcctRoleAssoc findById( java.lang.Integer id) {
        log.debug("getting MmUserAcctRoleAssoc instance with id: " + id);
        try {
            MmUserAcctRoleAssoc instance = (MmUserAcctRoleAssoc) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmUserAcctRoleAssoc", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmUserAcctRoleAssoc instance) {
        log.debug("finding MmUserAcctRoleAssoc instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmUserAcctRoleAssoc")
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
      log.debug("finding MmUserAcctRoleAssoc instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmUserAcctRoleAssoc as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

    public MmUserAcctRoleAssoc merge(MmUserAcctRoleAssoc detachedInstance) {
        log.debug("merging MmUserAcctRoleAssoc instance");
        try {
            MmUserAcctRoleAssoc result = (MmUserAcctRoleAssoc) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmUserAcctRoleAssoc instance) {
        log.debug("attaching dirty MmUserAcctRoleAssoc instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmUserAcctRoleAssoc instance) {
        log.debug("attaching clean MmUserAcctRoleAssoc instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}