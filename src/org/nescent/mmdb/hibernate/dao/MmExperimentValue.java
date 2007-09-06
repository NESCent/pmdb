package org.nescent.mmdb.hibernate.dao;



/**
 * MmExperimentValue generated by MyEclipse - Hibernate Tools
 */

public class MmExperimentValue  implements java.io.Serializable {


    // Fields    

     private Integer experimentValueOid;
     private MmCvTerm mmCvTerm;
     private MmExperimentStudy mmExperimentStudy;
     private String value;


    // Constructors

    /** default constructor */
    public MmExperimentValue() {
    }

    
    /** full constructor */
    public MmExperimentValue(MmCvTerm mmCvTerm, MmExperimentStudy mmExperimentStudy, String value) {
        this.mmCvTerm = mmCvTerm;
        this.mmExperimentStudy = mmExperimentStudy;
        this.value = value;
    }

   
    // Property accessors

    public Integer getExperimentValueOid() {
        return this.experimentValueOid;
    }
    
    public void setExperimentValueOid(Integer experimentValueOid) {
        this.experimentValueOid = experimentValueOid;
    }

    public MmCvTerm getMmCvTerm() {
        return this.mmCvTerm;
    }
    
    public void setMmCvTerm(MmCvTerm mmCvTerm) {
        this.mmCvTerm = mmCvTerm;
    }

    public MmExperimentStudy getMmExperimentStudy() {
        return this.mmExperimentStudy;
    }
    
    public void setMmExperimentStudy(MmExperimentStudy mmExperimentStudy) {
        this.mmExperimentStudy = mmExperimentStudy;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
   








}