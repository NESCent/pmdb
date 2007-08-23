package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmRole.
 * @see org.nescent.mmdb.hibernate.dao.MmRole
 * @author MyEclipse - Hibernate Tools
 */
public class MmRoleDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmRoleDAO.class);

	//property constants
	public static final String ROLE_NAME = "roleName";

    
    public void save(MmRole transientInstance) {
        log.debug("saving MmRole instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmRole persistentInstance) {
        log.debug("deleting MmRole instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmRole findById( java.lang.Integer id) {
        log.debug("getting MmRole instance with id: " + id);
        try {
            MmRole instance = (MmRole) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmRole", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmRole instance) {
        log.debug("finding MmRole instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmRole")
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
      log.debug("finding MmRole instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmRole as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByRoleName(Object roleName) {
		return findByProperty(ROLE_NAME, roleName);
	}
	
    public MmRole merge(MmRole detachedInstance) {
        log.debug("merging MmRole instance");
        try {
            MmRole result = (MmRole) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmRole instance) {
        log.debug("attaching dirty MmRole instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmRole instance) {
        log.debug("attaching clean MmRole instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}