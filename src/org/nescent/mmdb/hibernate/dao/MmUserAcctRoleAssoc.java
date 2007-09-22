package org.nescent.mmdb.hibernate.dao;



/**
 * MmUserAcctRoleAssoc generated by MyEclipse - Hibernate Tools
 */

public class MmUserAcctRoleAssoc  implements java.io.Serializable {


    // Fields    

     private Integer uaraOid;
     private MmRole mmRole;
     private MmUserAccount mmUserAccount;


    // Constructors

    /** default constructor */
    public MmUserAcctRoleAssoc() {
    }

    
    /** full constructor */
    public MmUserAcctRoleAssoc(Integer uaraOid, MmRole mmRole, MmUserAccount mmUserAccount) {
        this.uaraOid = uaraOid;
        this.mmRole = mmRole;
        this.mmUserAccount = mmUserAccount;
    }

   
    // Property accessors

    public Integer getUaraOid() {
        return this.uaraOid;
    }
    
    public void setUaraOid(Integer uaraOid) {
        this.uaraOid = uaraOid;
    }

    public MmRole getMmRole() {
        return this.mmRole;
    }
    
    public void setMmRole(MmRole mmRole) {
        this.mmRole = mmRole;
    }

    public MmUserAccount getMmUserAccount() {
        return this.mmUserAccount;
    }
    
    public void setMmUserAccount(MmUserAccount mmUserAccount) {
        this.mmUserAccount = mmUserAccount;
    }
   








}