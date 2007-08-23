package org.nescent.mmdb.hibernate.dao;



/**
 * MmSpeciesAttrCvtermAssoc generated by MyEclipse - Hibernate Tools
 */

public class MmSpeciesAttrCvtermAssoc  implements java.io.Serializable {


    // Fields    

     private Integer msacaOid;
     private MmMatingSystemStudy mmMatingSystemStudy;
     private MmCvTerm mmCvTerm;
     private String value;


    // Constructors

    /** default constructor */
    public MmSpeciesAttrCvtermAssoc() {
    }

	/** minimal constructor */
    public MmSpeciesAttrCvtermAssoc(String value) {
        this.value = value;
    }
    
    /** full constructor */
    public MmSpeciesAttrCvtermAssoc(MmMatingSystemStudy mmMatingSystemStudy, MmCvTerm mmCvTerm, String value) {
        this.mmMatingSystemStudy = mmMatingSystemStudy;
        this.mmCvTerm = mmCvTerm;
        this.value = value;
    }

   
    // Property accessors

    public Integer getMsacaOid() {
        return this.msacaOid;
    }
    
    public void setMsacaOid(Integer msacaOid) {
        this.msacaOid = msacaOid;
    }

    public MmMatingSystemStudy getMmMatingSystemStudy() {
        return this.mmMatingSystemStudy;
    }
    
    public void setMmMatingSystemStudy(MmMatingSystemStudy mmMatingSystemStudy) {
        this.mmMatingSystemStudy = mmMatingSystemStudy;
    }

    public MmCvTerm getMmCvTerm() {
        return this.mmCvTerm;
    }
    
    public void setMmCvTerm(MmCvTerm mmCvTerm) {
        this.mmCvTerm = mmCvTerm;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
   








}