package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmReferencePart.
 * @see org.nescent.mmdb.hibernate.dao.MmReferencePart
 * @author MyEclipse - Hibernate Tools
 */
public class MmReferencePartDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmReferencePartDAO.class);

	//property constants
	public static final String NAME = "name";

    
    public void save(MmReferencePart transientInstance) {
        log.debug("saving MmReferencePart instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmReferencePart persistentInstance) {
        log.debug("deleting MmReferencePart instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmReferencePart findById( java.lang.Integer id) {
        log.debug("getting MmReferencePart instance with id: " + id);
        try {
            MmReferencePart instance = (MmReferencePart) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmReferencePart", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmReferencePart instance) {
        log.debug("finding MmReferencePart instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmReferencePart")
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
      log.debug("finding MmReferencePart instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmReferencePart as model where model." 
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
	
    public MmReferencePart merge(MmReferencePart detachedInstance) {
        log.debug("merging MmReferencePart instance");
        try {
            MmReferencePart result = (MmReferencePart) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmReferencePart instance) {
        log.debug("attaching dirty MmReferencePart instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmReferencePart instance) {
        log.debug("attaching clean MmReferencePart instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}