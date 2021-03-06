package org.nescent.mmdb.hibernate.dao;

/**
 * MmSpeciesAttrCvtermAssoc generated by MyEclipse - Hibernate Tools
 */

public class MmSpeciesAttrCvtermAssoc implements java.io.Serializable,
	Comparable<MmSpeciesAttrCvtermAssoc> {

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
    public MmSpeciesAttrCvtermAssoc(Integer msacaOid, String value) {
	this.msacaOid = msacaOid;
	this.value = value;
    }

    /** full constructor */
    public MmSpeciesAttrCvtermAssoc(Integer msacaOid,
	    MmMatingSystemStudy mmMatingSystemStudy, MmCvTerm mmCvTerm,
	    String value) {
	this.msacaOid = msacaOid;
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

    public int compareTo(MmSpeciesAttrCvtermAssoc assoc) {
	MmCvTerm term = assoc.getMmCvTerm();
	int r = this.getMmCvTerm().getName().compareTo(term.getName());
	if (r == 0) {
	    return this.getMsacaOid().compareTo(assoc.getMsacaOid());
	}
	return r;

    }

}