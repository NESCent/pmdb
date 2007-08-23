package org.nescent.mmdb.hibernate.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmUserAccount.
 * @see org.nescent.mmdb.hibernate.dao.MmUserAccount
 * @author MyEclipse - Hibernate Tools
 */
public class MmUserAccountDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmUserAccountDAO.class);

	//property constants
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String ENABLE_DISABLE_STATUS = "enableDisableStatus";

    
    public void save(MmUserAccount transientInstance) {
        log.debug("saving MmUserAccount instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmUserAccount persistentInstance) {
        log.debug("deleting MmUserAccount instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmUserAccount findById( java.lang.Integer id) {
        log.debug("getting MmUserAccount instance with id: " + id);
        try {
            MmUserAccount instance = (MmUserAccount) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmUserAccount", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmUserAccount instance) {
        log.debug("finding MmUserAccount instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmUserAccount")
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
      log.debug("finding MmUserAccount instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmUserAccount as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}
	
	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}
	
	public List findByEnableDisableStatus(Object enableDisableStatus) {
		return findByProperty(ENABLE_DISABLE_STATUS, enableDisableStatus);
	}
	
    public MmUserAccount merge(MmUserAccount detachedInstance) {
        log.debug("merging MmUserAccount instance");
        try {
            MmUserAccount result = (MmUserAccount) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmUserAccount instance) {
        log.debug("attaching dirty MmUserAccount instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmUserAccount instance) {
        log.debug("attaching clean MmUserAccount instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}