package org.nescent.mmdb.hibernate.dao;

import java.util.HashSet;
import java.util.Set;

/**
 * MmMatingSystemStudy generated by MyEclipse - Hibernate Tools
 */

public class MmMatingSystemStudy implements java.io.Serializable,
	Comparable<MmMatingSystemStudy> {

    // Fields

    private Integer matingSystemStudyOid;
    private MmReferencePart mmReferencePart;
    private MmSpecies mmSpecies;
    private String latitude;
    private Set mmSpeciesAttrCvtermAssocs = new HashSet(0);

    // Constructors

    /** default constructor */
    public MmMatingSystemStudy() {
    }

    /** minimal constructor */
    public MmMatingSystemStudy(Integer matingSystemStudyOid) {
	this.matingSystemStudyOid = matingSystemStudyOid;
    }

    /** full constructor */
    public MmMatingSystemStudy(Integer matingSystemStudyOid,
	    MmReferencePart mmReferencePart, MmSpecies mmSpecies,
	    String latitude, Set mmSpeciesAttrCvtermAssocs) {
	this.matingSystemStudyOid = matingSystemStudyOid;
	this.mmReferencePart = mmReferencePart;
	this.mmSpecies = mmSpecies;
	this.latitude = latitude;
	this.mmSpeciesAttrCvtermAssocs = mmSpeciesAttrCvtermAssocs;
    }

    // Property accessors

    public Integer getMatingSystemStudyOid() {
	return this.matingSystemStudyOid;
    }

    public void setMatingSystemStudyOid(Integer matingSystemStudyOid) {
	this.matingSystemStudyOid = matingSystemStudyOid;
    }

    public MmReferencePart getMmReferencePart() {
	return this.mmReferencePart;
    }

    public void setMmReferencePart(MmReferencePart mmReferencePart) {
	this.mmReferencePart = mmReferencePart;
    }

    public MmSpecies getMmSpecies() {
	return this.mmSpecies;
    }

    public void setMmSpecies(MmSpecies mmSpecies) {
	this.mmSpecies = mmSpecies;
    }

    public String getLatitude() {
	return this.latitude;
    }

    public void setLatitude(String latitude) {
	this.latitude = latitude;
    }

    public Set getMmSpeciesAttrCvtermAssocs() {
	return this.mmSpeciesAttrCvtermAssocs;
    }

    public void setMmSpeciesAttrCvtermAssocs(Set mmSpeciesAttrCvtermAssocs) {
	this.mmSpeciesAttrCvtermAssocs = mmSpeciesAttrCvtermAssocs;
    }

    public int compareTo(MmMatingSystemStudy o) {
	if (this.getMmReferencePart() == null)
	    return -1;
	if (this.getMmReferencePart().getMmReference() == null)
	    return -1;
	if (this.getMmReferencePart().getMmReference().getFullReference() == null)
	    return -1;

	return this.getMmReferencePart().getMmReference().getFullReference()
		.compareTo(
			o.getMmReferencePart().getMmReference()
				.getFullReference());
    }

}