/**
 * Permission class to hold permission parameters and validate against 
 * another Permission object 
 */
package org.nescent.mmdb.aa;

import org.nescent.mmdb.util.Record;

import java.io.Serializable;


/**
 * @author xianhua
 * @author Surya Dhullipalla
 */
public class Permission extends Record implements Serializable{
	public static String ACCESS_TYPE_SEARCH= "search";
	public static String ACCESS_TYPE_READ =	"view";
	public static String ACCESS_TYPE_INSERT= 	"insert";
	public static String ACCESS_TYPE_UPDATE= "edit";
	public static String ACCESS_TYPE_DELETE= "delete";
	public static String ACCESS_TYPE_ALL=	"all";
	
	public static int INT_ACCESS_TYPE_SEARCH= 0;
	public static int INT_ACCESS_TYPE_READ = 1;
	public static int INT_ACCESS_TYPE_INSERT= 2;
	public static int INT_ACCESS_TYPE_UPDATE= 3;
	public static int INT_ACCESS_TYPE_DELETE= 4;
	public static int INT_ACCESS_TYPE_ALL=	5;
	
	public static String SCOPE_ALL = "all";
	public static String SCOPE_OWN = "own";
	
	public static String OBJECT_ALL = "all";
	
	String access;
	String scope;
	String resource;
	String roleName;
	int personOID;
	
	PermissionManager manager;
	  
	/**
	 * 
	 */
	public Permission() {
		super();
	}
	/**
	 * @param access
	 * @param scope
	 * @param resource
	 * @param personOID
	 * @param roleName
	 */
	public Permission(String access, String scope, String resource, int personOID, String roleName) {
		super();
		this.access = access;
		this.scope = scope;
		this.resource = resource;
		this.personOID = personOID;
		this.roleName = roleName;
	}
	
	public PermissionManager getManager() {
		return manager;
	}
	public void setManager(PermissionManager manager) {
		this.manager = manager;
	}
	/* (non-Javadoc)
	 * @see org.nescent.nead.util.Record#getId()
	 */
	public int getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	/* (non-Javadoc)
	 * @see org.nescent.nead.util.Record#setId(java.lang.String)
	 */
	public void setId(int id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}
	
	/**
	 * @return the objectId
	 */
	public String getAccess() {
		return this.access;
	}
	/**
	 * @param objectId the objectId to set
	 */
	public void setAccess(String access) {
		this.access = access;
	}
	/**
	 * @return the userId
	 */
	public int getPersonOID() {
		return this.personOID;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setPersonOID(int personOID) {
		this.personOID = personOID;
	}
	/**
	 * @return the scope
	 */
	public String getScope() {
		return this.scope;
	}
	/**
	 * @param action the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	/**
	 * @return the resource
	 */
	public String getResource() {
		return this.resource;
	}
	/**
	 * @param object the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return this.roleName;
	}
	/**
	 * @param roleId the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	
	public boolean permit(Permission permission)
	{
		if(resource.equals(Permission.OBJECT_ALL))
		{
			if(access.equals(Permission.ACCESS_TYPE_ALL))
			{
				if(scope.equals(Permission.SCOPE_ALL))
					return true;
				else
				{
					if (resource.equals("person") && permission.getId() == permission.getPersonOID())
					{
						return true;
					}
											
					return false;
					
					
				}
			}
			else if(access.equals(permission.access))
			{
				if(scope.equals(Permission.SCOPE_ALL))
					return true;
				else
				{
					if (resource.equals("person") && permission.getId() == permission.getPersonOID())
					{
						return true;
					}
					
					
						
					return false;
				}
			}
			else
				return false;
		}
		else if(resource.equals(permission.getResource()))
		{
			if(access.equals(Permission.ACCESS_TYPE_ALL))
			{
				if(scope.equals(Permission.SCOPE_ALL))
					return true;
				else
				{
					if (resource.equals("person") && permission.getId() == permission.getPersonOID())
					{
						return true;
					}
					
								
					return false;
					

				}
			}
			else if(access.equals(permission.getAccess()))
			{
				if(scope.equals(Permission.SCOPE_ALL))
					return true;
				else
				{
					if (access.equals("add"))
					{
						return false;
					}
					if (resource.equals("person") && permission.getId() == permission.getPersonOID())
					{
						return true;
					}
					
						
					return false;
				}
			}
			else
				return false;
		}
		else
			return false;
		
		
	}
	

	
	static public int getAccessType(String access)
	{
		if(access.equals("search"))
			return Permission.INT_ACCESS_TYPE_SEARCH;
		if(access.equals("view"))
			return Permission.INT_ACCESS_TYPE_READ;
		if(access.equals("insert"))
			return Permission.INT_ACCESS_TYPE_INSERT;
		if(access.equals("edit"))
			return Permission.INT_ACCESS_TYPE_UPDATE;
		if(access.equals("delete"))
			return Permission.INT_ACCESS_TYPE_DELETE;
		if(access.equals("all"))
			return Permission.INT_ACCESS_TYPE_ALL;
		
		return -1;
	}
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Permission)
		{
			Permission p=(Permission)obj;
			return resource.equals(p.getResource());
		}
		else
			return super.equals(obj);
			
	}
	public int compareTo(Object o)

	{
		Permission p = (Permission)o;
	    return resource.compareTo(p.resource);

	}


	

}
