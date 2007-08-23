/**
 * PermissionManager: class to hold all permissions of an user and perform 
 * permission check
 */  
package org.nescent.mmdb.aa;

import java.util.ArrayList;
import java.util.Collections;

import org.nescent.mmdb.hibernate.dao.MmPermission;
import org.nescent.mmdb.hibernate.dao.MmRole;

/**
 * @author xianhua
 *
 */
public class PermissionManager {
	ArrayList permissions;
	
	/**
	 * 
	 */
	public PermissionManager() { 
		permissions=new ArrayList();
	}
	
	public void setPermission(Permission permission)
	{
		if(permissions!=null)
		{
			permissions.add(permission);
			permission.setManager(this);
		}
	}
	
	public boolean getPermission(Permission permission)
	{
		if(permissions==null)return false;
		for(int i=0;i<permissions.size();i++)
		{
			Permission p=(Permission)permissions.get(i);
			boolean perm=p.permit(permission);
			if(perm)return true;
		}
		return false;
	}
	
	public ArrayList getPermissions() {
		try {
		
		 Collections.sort(permissions);
		
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
		 return permissions;
	}
	
	public boolean containsRole(String role)
	{
		for(int i=0;i<permissions.size();i++)
		{
			Permission p=(Permission)permissions.get(i);
			if(p.getRoleName().equals(role))
				return true;
		}
		return false;
	}

}
