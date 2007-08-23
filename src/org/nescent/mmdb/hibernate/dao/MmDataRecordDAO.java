package org.nescent.mmdb.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class MmDataRecord.
 * @see org.nescent.mmdb.hibernate.dao.MmDataRecord
 * @author MyEclipse - Hibernate Tools
 */
public class MmDataRecordDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(MmDataRecordDAO.class);

	//property constants
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String OUT_CROSSING_VALUE = "outCrossingValue";
	public static final String SELFING_VALUE = "selfingValue";
	public static final String OUT_CROSSING_STD_DEV = "outCrossingStdDev";
	public static final String SELFING_STD_DEV = "selfingStdDev";

    
    public void save(MmDataRecord transientInstance) {
        log.debug("saving MmDataRecord instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MmDataRecord persistentInstance) {
        log.debug("deleting MmDataRecord instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MmDataRecord findById( java.lang.Integer id) {
        log.debug("getting MmDataRecord instance with id: " + id);
        try {
            MmDataRecord instance = (MmDataRecord) getSession()
                    .get("org.nescent.mmdb.hibernate.dao.MmDataRecord", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MmDataRecord instance) {
        log.debug("finding MmDataRecord instance by example");
        try {
            List results = getSession()
                    .createCriteria("org.nescent.mmdb.hibernate.dao.MmDataRecord")
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
      log.debug("finding MmDataRecord instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MmDataRecord as model where model." 
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
	
	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}
	
	public List findByOutCrossingValue(Object outCrossingValue) {
		return findByProperty(OUT_CROSSING_VALUE, outCrossingValue);
	}
	
	public List findBySelfingValue(Object selfingValue) {
		return findByProperty(SELFING_VALUE, selfingValue);
	}
	
	public List findByOutCrossingStdDev(Object outCrossingStdDev) {
		return findByProperty(OUT_CROSSING_STD_DEV, outCrossingStdDev);
	}
	
	public List findBySelfingStdDev(Object selfingStdDev) {
		return findByProperty(SELFING_STD_DEV, selfingStdDev);
	}
	
    public MmDataRecord merge(MmDataRecord detachedInstance) {
        log.debug("merging MmDataRecord instance");
        try {
            MmDataRecord result = (MmDataRecord) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MmDataRecord instance) {
        log.debug("attaching dirty MmDataRecord instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MmDataRecord instance) {
        log.debug("attaching clean MmDataRecord instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}