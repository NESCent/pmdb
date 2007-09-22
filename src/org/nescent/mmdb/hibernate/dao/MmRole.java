package org.nescent.mmdb.hibernate.dao;

import java.util.HashSet;
import java.util.Set;


/**
 * MmRole generated by MyEclipse - Hibernate Tools
 */

public class MmRole  implements java.io.Serializable {


    // Fields    

     private Integer roleOid;
     private String roleName;
     private Set mmUserAcctRoleAssocs = new HashSet(0);
     private Set mmPermissions = new HashSet(0);


    // Constructors

    /** default constructor */
    public MmRole() {
    }

	/** minimal constructor */
    public MmRole(Integer roleOid, String roleName) {
        this.roleOid = roleOid;
        this.roleName = roleName;
    }
    
    /** full constructor */
    public MmRole(Integer roleOid, String roleName, Set mmUserAcctRoleAssocs, Set mmPermissions) {
        this.roleOid = roleOid;
        this.roleName = roleName;
        this.mmUserAcctRoleAssocs = mmUserAcctRoleAssocs;
        this.mmPermissions = mmPermissions;
    }

   
    // Property accessors

    public Integer getRoleOid() {
        return this.roleOid;
    }
    
    public void setRoleOid(Integer roleOid) {
        this.roleOid = roleOid;
    }

    public String getRoleName() {
        return this.roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set getMmUserAcctRoleAssocs() {
        return this.mmUserAcctRoleAssocs;
    }
    
    public void setMmUserAcctRoleAssocs(Set mmUserAcctRoleAssocs) {
        this.mmUserAcctRoleAssocs = mmUserAcctRoleAssocs;
    }

    public Set getMmPermissions() {
        return this.mmPermissions;
    }
    
    public void setMmPermissions(Set mmPermissions) {
        this.mmPermissions = mmPermissions;
    }
   








}