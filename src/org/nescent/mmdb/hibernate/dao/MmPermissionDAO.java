package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmPermission.
 * @see org.nescent.mmdb.hibernate.dao.MmPermission
 * @author MyEclipse - Hibernate Tools
 */
public class MmPermissionDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmPermissionDAO.class);

	//property constants
	public static final String ACCESS = "access";
	public static final String SCOPE = "scope";

    
    public void save(MmPermission transientInstance) {
        log.debug("saving MmPermission instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmPermission persistentInstance) {
        log.debug("deleting MmPermission instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmPermission findById( java.lang.Integer id) {
        log.debug("getting MmPermission instance with id: " + id);
        try {
            MmPermission instance = (MmPermission) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmPermission", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmPermission instance) {
        log.debug("finding MmPermission instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmPermission")
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
      log.debug("finding MmPermission instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmPermission as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByAccess(Object access) {
		return findByProperty(ACCESS, access);
	}
	
	public List findByScope(Object scope) {
		return findByProperty(SCOPE, scope);
	}
	
    public MmPermission merge(MmPermission detachedInstance) {
        log.debug("merging MmPermission instance");
        try {
            MmPermission result = (MmPermission) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmPermission instance) {
        log.debug("attaching dirty MmPermission instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmPermission instance) {
        log.debug("attaching clean MmPermission instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}