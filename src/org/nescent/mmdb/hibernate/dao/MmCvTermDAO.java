package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmCvTerm.
 * @see org.nescent.mmdb.hibernate.dao.MmCvTerm
 * @author MyEclipse - Hibernate Tools
 */
public class MmCvTermDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmCvTermDAO.class);

	//property constants
	public static final String NAMESPACE = "namespace";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String SYNONYM_NAME = "synonymName";
	public static final String VALUE_TYPE = "valueType";
	public static final String IS_VALUE_COMPUTED = "isValueComputed";

    
    public void save(MmCvTerm transientInstance) {
        log.debug("saving MmCvTerm instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmCvTerm persistentInstance) {
        log.debug("deleting MmCvTerm instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmCvTerm findById( java.lang.Integer id) {
        log.debug("getting MmCvTerm instance with id: " + id);
        try {
            MmCvTerm instance = (MmCvTerm) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmCvTerm", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmCvTerm instance) {
        log.debug("finding MmCvTerm instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmCvTerm")
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
      log.debug("finding MmCvTerm instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmCvTerm as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByNamespace(Object namespace) {
		return findByProperty(NAMESPACE, namespace);
	}
	
	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}
	
	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}
	
	public List findBySynonymName(Object synonymName) {
		return findByProperty(SYNONYM_NAME, synonymName);
	}
	
	public List findByValueType(Object valueType) {
		return findByProperty(VALUE_TYPE, valueType);
	}
	
	public List findByIsValueComputed(Object isValueComputed) {
		return findByProperty(IS_VALUE_COMPUTED, isValueComputed);
	}
	
    public MmCvTerm merge(MmCvTerm detachedInstance) {
        log.debug("merging MmCvTerm instance");
        try {
            MmCvTerm result = (MmCvTerm) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmCvTerm instance) {
        log.debug("attaching dirty MmCvTerm instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmCvTerm instance) {
        log.debug("attaching clean MmCvTerm instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}