package org.nescent.mmdb.hibernate.dao;

import org.hibernate.Session;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;


/**
 * Data access object (DAO) for domain model
 * @author MyEclipse - Hibernate Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {
	
	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}
	
}