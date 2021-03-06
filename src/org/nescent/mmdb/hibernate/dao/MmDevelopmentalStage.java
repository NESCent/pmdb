package org.nescent.mmdb.hibernate.dao;

import java.util.HashSet;
import java.util.Set;


/**
 * MmDevelopmentalStage generated by MyEclipse - Hibernate Tools
 */

public class MmDevelopmentalStage  implements java.io.Serializable {


    // Fields    

     private Integer developmentalStageOid;
     private String name;
     private Set mmExperimentStudies = new HashSet(0);


    // Constructors

    /** default constructor */
    public MmDevelopmentalStage() {
    }

	/** minimal constructor */
    public MmDevelopmentalStage(String name) {
        this.name = name;
    }
    
    /** full constructor */
    public MmDevelopmentalStage(String name, Set mmExperimentStudies) {
        this.name = name;
        this.mmExperimentStudies = mmExperimentStudies;
    }

   
    // Property accessors

    public Integer getDevelopmentalStageOid() {
        return this.developmentalStageOid;
    }
    
    public void setDevelopmentalStageOid(Integer developmentalStageOid) {
        this.developmentalStageOid = developmentalStageOid;
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
   








}