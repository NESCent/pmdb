package org.nescent.mmdb.hibernate.dao;

import java.util.HashSet;
import java.util.Set;


/**
 * MmReferencePart generated by MyEclipse - Hibernate Tools
 */

public class MmReferencePart  implements java.io.Serializable {


    // Fields    

     private Integer referencePartOid;
     private MmReference mmReference;
     private String name;
     private Set mmExperimentStudies = new HashSet(0);
     private Set mmMatingSystemStudies = new HashSet(0);


    // Constructors

    /** default constructor */
    public MmReferencePart() {
    }

	/** minimal constructor */
    public MmReferencePart(Integer referencePartOid, String name) {
        this.referencePartOid = referencePartOid;
        this.name = name;
    }
    
    /** full constructor */
    public MmReferencePart(Integer referencePartOid, MmReference mmReference, String name, Set mmExperimentStudies, Set mmMatingSystemStudies) {
        this.referencePartOid = referencePartOid;
        this.mmReference = mmReference;
        this.name = name;
        this.mmExperimentStudies = mmExperimentStudies;
        this.mmMatingSystemStudies = mmMatingSystemStudies;
    }

   
    // Property accessors

    public Integer getReferencePartOid() {
        return this.referencePartOid;
    }
    
    public void setReferencePartOid(Integer referencePartOid) {
        this.referencePartOid = referencePartOid;
    }

    public MmReference getMmReference() {
        return this.mmReference;
    }
    
    public void setMmReference(MmReference mmReference) {
        this.mmReference = mmReference;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Set getMmExperimentStudies() {
        return this.mmExperimentStudies;
    }
    
    public void setMmExperimentStudies(Set mmExperimentStudies) {
        this.mmExperimentStudies = mmExperimentStudies;
    }

    public Set getMmMatingSystemStudies() {
        return this.mmMatingSystemStudies;
    }
    
    public void setMmMatingSystemStudies(Set mmMatingSystemStudies) {
        this.mmMatingSystemStudies = mmMatingSystemStudies;
    }
   








}