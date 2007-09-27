package org.nescent.mmdb.util;

public class Population {
	Integer id;
	int populationNum;
	int inbreedNum;
	int pollinationNum;
	int outcrossingNum;
	String geographicLocation;
	String population;
	String year;
	
	
	public String getGeographicLocation() {
		return geographicLocation;
	}
	public void setGeographicLocation(String geographicLocation) {
		this.geographicLocation = geographicLocation;
	}
	public String getPopulation() {
		return population;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	
}
