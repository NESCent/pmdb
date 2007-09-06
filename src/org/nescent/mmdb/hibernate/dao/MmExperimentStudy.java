package org.nescent.mmdb.hibernate.dao;

import java.util.HashSet;
import java.util.Set;


/**
 * MmExperimentStudy generated by MyEclipse - Hibernate Tools
 */

public class MmExperimentStudy  implements java.io.Serializable {


    // Fields    

     private Integer experimentStudyOid;
     private MmDevelopmentalStage mmDevelopmentalStage;
     private MmReferencePart mmReferencePart;
     private MmPopulationSample mmPopulationSample;
     private String name;
     private Set mmExperimentValues = new HashSet(0);
     private Set mmDataRecords = new HashSet(0);


    // Constructors

    /** default constructor */
    public MmExperimentStudy() {
    }

    
    /** full constructor */
    public MmExperimentStudy(MmDevelopmentalStage mmDevelopmentalStage, MmReferencePart mmReferencePart, MmPopulationSample mmPopulationSample, String name, Set mmExperimentValues, Set mmDataRecords) {
        this.mmDevelopmentalStage = mmDevelopmentalStage;
        this.mmReferencePart = mmReferencePart;
        this.mmPopulationSample = mmPopulationSample;
        this.name = name;
        this.mmExperimentValues = mmExperimentValues;
        this.mmDataRecords = mmDataRecords;
    }

   
    // Property accessors

    public Integer getExperimentStudyOid() {
        return this.experimentStudyOid;
    }
    
    public void setExperimentStudyOid(Integer experimentStudyOid) {
        this.experimentStudyOid = experimentStudyOid;
    }

    public MmDevelopmentalStage getMmDevelopmentalStage() {
        return this.mmDevelopmentalStage;
    }
    
    public void setMmDevelopmentalStage(MmDevelopmentalStage mmDevelopmentalStage) {
        this.mmDevelopmentalStage = mmDevelopmentalStage;
    }

    public MmReferencePart getMmReferencePart() {
        return this.mmReferencePart;
    }
    
    public void setMmReferencePart(MmReferencePart mmReferencePart) {
        this.mmReferencePart = mmReferencePart;
    }

    public MmPopulationSample getMmPopulationSample() {
        return this.mmPopulationSample;
    }
    
    public void setMmPopulationSample(MmPopulationSample mmPopulationSample) {
        this.mmPopulationSample = mmPopulationSample;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Set getMmExperimentValues() {
        return this.mmExperimentValues;
    }
    
    public void setMmExperimentValues(Set mmExperimentValues) {
        this.mmExperimentValues = mmExperimentValues;
    }

    public Set getMmDataRecords() {
        return this.mmDataRecords;
    }
    
    public void setMmDataRecords(Set mmDataRecords) {
        this.mmDataRecords = mmDataRecords;
    }
   








}