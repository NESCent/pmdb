package org.nescent.mmdb.util;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.nescent.mmdb.aa.Permission;
import org.nescent.mmdb.aa.PermissionManager;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmPermission;
import org.nescent.mmdb.hibernate.dao.MmUserAccount;
import org.nescent.mmdb.hibernate.dao.MmUserAcctRoleAssoc;
import org.nescent.mmdb.spring.SaveDescriptorsController;

public class Login {
    private static Logger logger = null;

    private Logger log() {
	if (logger == null)
	    logger = Logger.getLogger(SaveDescriptorsController.class);

	return logger;
    }

    String userName;
    String password;
    int personOID;

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public int getPersonOID() {
	return personOID;
    }

    public void setPersonOID(int personOID) {
	this.personOID = personOID;
    }

    @SuppressWarnings("unchecked")
    public PermissionManager login() {

	PermissionManager pm = new PermissionManager();
	Session session = HibernateSessionFactory.getSession();
	Transaction tx = session.beginTransaction();
	try {
	    String pword = PasswordService.getInstance().encrypt(this.password);
	    MmUserAccount nua = (MmUserAccount) session.createCriteria(
		    MmUserAccount.class).add(
		    Restrictions.eq("userName", this.userName)).add(
		    Restrictions.eq("password", pword)).uniqueResult();

	    if (nua == null) {
		log().error(
			"failed to log in: " + this.userName + "("
				+ this.password + ").");
		throw new IllegalArgumentException("failed to log in.");
	    }

	    if (nua.getUserAccountOid() == null) {
		personOID = nua.getUserAccountOid().intValue();
	    }
	    Set assocs = nua.getMmUserAcctRoleAssocs();

	    for (Iterator it = assocs.iterator(); it.hasNext();) {
		MmUserAcctRoleAssoc assoc = (MmUserAcctRoleAssoc) it.next();

		Set perms = assoc.getMmRole().getMmPermissions();

		for (Iterator iter = perms.iterator(); iter.hasNext();) {
		    Permission p = new Permission();
		    MmPermission np = (MmPermission) iter.next();
		    p.setRoleName(np.getMmRole().getRoleName());
		    p.setAccess(np.getAccess());
		    p.setScope(np.getScope());
		    p.setResource(np.getMmCvTerm().getName());
		    p.setPersonOID(this.personOID);
		    pm.setPermission(p);
		}
	    }
	    tx.commit();
	    return pm;
	} catch (HibernateException he) {
	    log().error("failed to log in.", he);
	    throw he;
	} finally {
	    if (!tx.wasCommitted()) {
		tx.rollback();
	    }
	}
    }
}
