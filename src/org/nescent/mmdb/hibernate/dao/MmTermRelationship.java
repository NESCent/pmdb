package org.nescent.mmdb.hibernate.dao;



/**
 * MmTermRelationship generated by MyEclipse - Hibernate Tools
 */

public class MmTermRelationship  implements java.io.Serializable {


    // Fields    

     private Integer termRelationshipOid;
     private MmCvTerm mmCvTermByCvtermSubjectOid;
     private MmCvTerm mmCvTermByCvtermPredicateOid;
     private MmCvTerm mmCvTermByCvtermObjectOid;


    // Constructors

    /** default constructor */
    public MmTermRelationship() {
    }

	/** minimal constructor */
    public MmTermRelationship(Integer termRelationshipOid) {
        this.termRelationshipOid = termRelationshipOid;
    }
    
    /** full constructor */
    public MmTermRelationship(Integer termRelationshipOid, MmCvTerm mmCvTermByCvtermSubjectOid, MmCvTerm mmCvTermByCvtermPredicateOid, MmCvTerm mmCvTermByCvtermObjectOid) {
        this.termRelationshipOid = termRelationshipOid;
        this.mmCvTermByCvtermSubjectOid = mmCvTermByCvtermSubjectOid;
        this.mmCvTermByCvtermPredicateOid = mmCvTermByCvtermPredicateOid;
        this.mmCvTermByCvtermObjectOid = mmCvTermByCvtermObjectOid;
    }

   
    // Property accessors

    public Integer getTermRelationshipOid() {
        return this.termRelationshipOid;
    }
    
    public void setTermRelationshipOid(Integer termRelationshipOid) {
        this.termRelationshipOid = termRelationshipOid;
    }

    public MmCvTerm getMmCvTermByCvtermSubjectOid() {
        return this.mmCvTermByCvtermSubjectOid;
    }
    
    public void setMmCvTermByCvtermSubjectOid(MmCvTerm mmCvTermByCvtermSubjectOid) {
        this.mmCvTermByCvtermSubjectOid = mmCvTermByCvtermSubjectOid;
    }

    public MmCvTerm getMmCvTermByCvtermPredicateOid() {
        return this.mmCvTermByCvtermPredicateOid;
    }
    
    public void setMmCvTermByCvtermPredicateOid(MmCvTerm mmCvTermByCvtermPredicateOid) {
        this.mmCvTermByCvtermPredicateOid = mmCvTermByCvtermPredicateOid;
    }

    public MmCvTerm getMmCvTermByCvtermObjectOid() {
        return this.mmCvTermByCvtermObjectOid;
    }
    
    public void setMmCvTermByCvtermObjectOid(MmCvTerm mmCvTermByCvtermObjectOid) {
        this.mmCvTermByCvtermObjectOid = mmCvTermByCvtermObjectOid;
    }
   








}