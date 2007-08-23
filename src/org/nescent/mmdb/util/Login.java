package org.nescent.mmdb.util;
import java.util.*;

import org.hibernate.*;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.*;
import org.hibernate.Query;
import org.nescent.mmdb.aa.*;

  

public class Login{
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
	
	public boolean validateLogin() throws Exception  {
	
		Session session = HibernateSessionFactory.getSession();
		
    	session.beginTransaction();
   
		try
		{
			
		String pword = PasswordService.getInstance().encrypt(this.password);
    	Query q = session.createQuery("FROM org.nescent.mmdb.hibernate.dao.MmUserAccount where userName = :name and password = :password");
       	q.setParameter("name", this.userName);
      	q.setParameter("password", pword);
         	    	
    	List result = q.list(); 
    	
    	Iterator iter = result.iterator();
		   if (!iter.hasNext())
	       {
			   session.close();
	           return false;
	       }
		   else
		   {
			   MmUserAccount nua = (MmUserAccount) iter.next();

			   this.personOID = nua.getUserAccountOid().intValue();
			   session.close();
				   return true;
		   }
		}
		catch (Exception e)
		{
			throw e;
		}

    	
    			
	}
	
	public Set getPermissions () throws Exception
	{
		//List list= new ArrayList();
		Session session = HibernateSessionFactory.getSession();
		
    	session.beginTransaction();

    	
    	String pword = PasswordService.getInstance().encrypt(this.password);
       	String sql = "FROM org.nescent.mmdb.hibernate.dao.MmUserAccount  where userName = :name and password =:password";
        
    	//return session.createSQLQuery(sql)
    	Query q = session.createQuery(sql);
    	 q.setParameter("name", this.userName);
    	 q.setParameter("password", pword);
    	 
    	 List li = q.list();
    		Iterator iter = li.iterator();
    		
    		   if (!iter.hasNext())
    	       {
    			   System.out.println("does not have" + this.userName);
    	           return null;
    	       }

    	
    	 MmUserAccount n = (MmUserAccount)q.list().get(0);
      	 
    	 Set assocs=n.getMmUserAcctRoleAssocs();
    	 MmRole role = null;
    	 String rolename = null;
    	 Set permissions=null;
    	 
    	 for(Iterator it=assocs.iterator();it.hasNext();)
    	 { 
    		 MmUserAcctRoleAssoc assoc=(MmUserAcctRoleAssoc)it.next();
   		 
    	     Set perms = assoc.getMmRole().getMmPermissions();
    	     if(permissions==null)
    	    	 permissions=perms;
    	     else
    	    	 permissions.addAll(perms);
    	     
    	     role = assoc.getMmRole();
    	     rolename = role.getRoleName();
    	     
    		 
    	 }
    	 if(permissions==null)
			 throw new Exception("No permission found");
    		 
		 return permissions; 
 
	}
	
	
	public PermissionManager setPermissions() throws Exception{


	Set li = getPermissions();
	
	if (li == null) throw new Exception("Permissions not found");

	Iterator iter = li.iterator();
	
	   if (!iter.hasNext())
       {
            
           return null;
       }
	   
	   PermissionManager pm = new PermissionManager();
	   
	   
	      while (iter.hasNext())
	        {
	    	 
	    	  Permission p = new Permission();
	    	  MmPermission np = (MmPermission) iter.next();    	   
	    	  p.setRoleName(np.getMmRole().getRoleName());
	    	  p.setAccess(np.getAccess());
	    	  p.setScope(np.getScope());
	    	  p.setResource(np.getMmCvTerm().getName());
	    	  p.setPersonOID(this.personOID);
	    	  
	    	  

	    	  pm.setPermission(p);
	        }
	      
	      
	      return pm;
	
	} 


}

	

	
	


