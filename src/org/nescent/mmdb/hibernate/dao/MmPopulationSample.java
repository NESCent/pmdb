package org.nescent.mmdb.hibernate.dao;

import java.util.HashSet;
import java.util.Set;


/**
 * MmPopulationSample generated by MyEclipse - Hibernate Tools
 */

public class MmPopulationSample  implements java.io.Serializable {


    // Fields    

     private Integer populationSampleOid;
     private MmSpecies mmSpecies;
     private String geographicLocation;
     private String population;
     private String year;
     private String comments;
     private Set mmPopSampleAttrCvtermAssocs = new HashSet(0);
     private Set mmExperimentStudies = new HashSet(0);


    // Constructors

    /** default constructor */
    public MmPopulationSample() {
    }

	/** minimal constructor */
    public MmPopulationSample(Integer populationSampleOid) {
        this.populationSampleOid = populationSampleOid;
    }
    
    /** full constructor */
    public MmPopulationSample(Integer populationSampleOid, MmSpecies mmSpecies, String geographicLocation, String population, String year, String comments, Set mmPopSampleAttrCvtermAssocs, Set mmExperimentStudies) {
        this.populationSampleOid = populationSampleOid;
        this.mmSpecies = mmSpecies;
        this.geographicLocation = geographicLocation;
        this.population = population;
        this.year = year;
        this.comments = comments;
        this.mmPopSampleAttrCvtermAssocs = mmPopSampleAttrCvtermAssocs;
        this.mmExperimentStudies = mmExperimentStudies;
    }

   
    // Property accessors

    public Integer getPopulationSampleOid() {
        return this.populationSampleOid;
    }
    
    public void setPopulationSampleOid(Integer populationSampleOid) {
        this.populationSampleOid = populationSampleOid;
    }

    public MmSpecies getMmSpecies() {
        return this.mmSpecies;
    }
    
    public void setMmSpecies(MmSpecies mmSpecies) {
        this.mmSpecies = mmSpecies;
    }

    public String getGeographicLocation() {
        return this.geographicLocation;
    }
    
    public void setGeographicLocation(String geographicLocation) {
        this.geographicLocation = geographicLocation;
    }

    public String getPopulation() {
        return this.population;
    }
    
    public void setPopulation(String population) {
        this.population = population;
    }

    public String getYear() {
        return this.year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }

    public String getComments() {
        return this.comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set getMmPopSampleAttrCvtermAssocs() {
        return this.mmPopSampleAttrCvtermAssocs;
    }
    
    public void setMmPopSampleAttrCvtermAssocs(Set mmPopSampleAttrCvtermAssocs) {
        this.mmPopSampleAttrCvtermAssocs = mmPopSampleAttrCvtermAssocs;
    }

    public Set getMmExperimentStudies() {
        return this.mmExperimentStudies;
    }
    
    public void setMmExperimentStudies(Set mmExperimentStudies) {
        this.mmExperimentStudies = mmExperimentStudies;
    }
   








}