package org.nescent.mmdb.util;


public class Species {
	Integer id;
	String family;
	String genus;
	String species;
	int populationNum;
	int inbreedNum;
	int pollinationNum;
	int outcrossingNum;
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getGenus() {
		return genus;
	}
	public void setGenus(String genus) {
		this.genus = genus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getInbreedNum() {
		return inbreedNum;
	}
	public void setInbreedNum(int inbreedNum) {
		this.inbreedNum = inbreedNum;
	}
	public int getOutcrossingNum() {
		return outcrossingNum;
	}
	public void setOutcrossingNum(int outcrossingNum) {
		this.outcrossingNum = outcrossingNum;
	}
	public int getPollinationNum() {
		return pollinationNum;
	}
	public void setPollinationNum(int pollinationNum) {
		this.pollinationNum = pollinationNum;
	}
	public int getPopulationNum() {
		return populationNum;
	}
	public void setPopulationNum(int populationNum) {
		this.populationNum = populationNum;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	
	
	
}
