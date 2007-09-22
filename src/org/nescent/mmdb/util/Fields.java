package org.nescent.mmdb.util;


public class Fields {
	static String [][] fields={
		{"NewDescriptorAttribute","-1"},		
		{"NewDescriptorValue","-2"},
		{"NewDescriptorDesc","-3"},		
		{"NewEnvironmentStudyAttribute","-4"},		
		{"NewEnvironmentStudyValue","-5"},
		{"NewEnvironmentStudyDesc","-6"},
		{"NewPopulationSampleAttribute","-7"},		
		{"NewPopulationSampleValue","-8"},	
		{"NewPopulationSampleDesc","-9"},					
		{"DevelopStage","-100"},
		{"Family","-101"},
		{"Genus","-102"},
		{"Species","-103"},
		{"GeoLocation","-104"},
		{"PopulationName","-105"},
		{"Population","-106"},
		{"PopulationYear","-107"},
		{"PopulationComments","-108"},
		{"Environment","-109"},
		{"Latitude","-110"},
		{"Citation","-111"},
		{"FullReference","-112"},
		{"Part","-113"}
		
		
		
	};
	
	public static String getFieldId(String field)
	{
		for(int i=0;i<fields.length;i++)
		{
			if(fields[i][0].toUpperCase().equals(field.toUpperCase()))
				return fields[i][1];
		}
		return null;
	}
	
	public static String getField(String id)
	{
		for(int i=0;i<fields.length;i++)
		{
			if(fields[i][1].equals(id))
				return fields[i][0];
		}
		return null;
	}
}
