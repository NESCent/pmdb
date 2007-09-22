package org.nescent.mmdb.util;

import java.util.*;

import org.nescent.mmdb.hibernate.dao.*;

public class RetrieveData {
	public static void retrieveEnvironmentalStudy(MmExperimentStudy envStudy) throws Exception
	{
		envStudy.getName();
		MmPopulationSample sample=envStudy.getMmPopulationSample();
		if(sample!=null)
		{
			
			MmSpecies species=sample.getMmSpecies();
			if(species!=null)
			{
				species.getFamily();
				species.getGenus();
				species.getSpecies();
			}
			sample.getComments();
			sample.getEnvironment();
			sample.getGeographicLocation();
			Set set =sample.getMmPopSampleAttrCvtermAssocs();
			for(Iterator it=set.iterator();it.hasNext();)
			{
				MmPopSampleAttrCvtermAssoc cvAssoc=(MmPopSampleAttrCvtermAssoc)it.next();
				MmCvTerm term=cvAssoc.getMmCvTerm();
				if(term!=null)
				{
					term.getCvtermOid();
					term.getDescription();
					term.getName();
					term.getNamespace();
					term.getValueType();
				}
				cvAssoc.getValue();
			}
			
			sample.getName();
			sample.getPopulation();
			sample.getYear();
			sample.getPopulationSampleOid();
		}
		
		envStudy.getExperimentStudyOid();
		Set set= envStudy.getMmDataRecords();
		for(Iterator it=set.iterator();it.hasNext();)
		{
			MmDataRecord record=(MmDataRecord)it.next();
			if(record!=null)
			{
				record.getName();
				record.getOutCrossingStdDev();
				record.getOutCrossingValue();
				record.getSelfingStdDev();
				record.getSelfingValue();
				record.getType();
			}
		}
		
		MmDevelopmentalStage stage=envStudy.getMmDevelopmentalStage();
		if(stage!=null)
			stage.getName();
		
		set= envStudy.getMmExperimentValues();
		
		for(Iterator it=set.iterator();it.hasNext();)
		{
			MmExperimentValue value=(MmExperimentValue)it.next();
			
			MmCvTerm term=value.getMmCvTerm();
			if(term!=null)
			{
				term.getCvtermOid();
				term.getDescription();
				term.getName();
				term.getNamespace();
				term.getValueType();
			}
			
			value.getValue();
		}
		
		
		
		MmReferencePart refPart=envStudy.getMmReferencePart();
		if(refPart!=null)
		{
			refPart.getName();
			MmReference ref=refPart.getMmReference();
			if(ref!=null)
			{
				ref.getCitation();
				ref.getFullReference();
			}
		}
	}
	
	public static  void retrieveSpecies(MmSpecies species) throws Exception
	{
		Integer id=species.getSpeciesOid();
		MmSpeciesDAO spDao=new MmSpeciesDAO();
		species= spDao.findById(id);
		species.getFamily();
		species.getGenus();
		species.getSpecies();
		
		Set mmStudies=species.getMmMatingSystemStudies();
		for(Iterator it=mmStudies.iterator();it.hasNext();)
		{
			MmMatingSystemStudy mmStudy=(MmMatingSystemStudy)it.next();
			mmStudy.getLatitude();
			MmReferencePart refPart=mmStudy.getMmReferencePart();
			if(refPart!=null)
			{
				refPart.getName();
				MmReference ref=refPart.getMmReference();
				if(ref!=null)
				{
					ref.getCitation();
					ref.getFullReference();
				}
			}
		}
		
		Set mmSamples=species.getMmPopulationSamples();
		for(Iterator it=mmSamples.iterator();it.hasNext();)
		{
			MmPopulationSample mmSample=(MmPopulationSample)it.next();
			mmSample.getGeographicLocation();
			mmSample.getEnvironment();
			mmSample.getName();
			mmSample.getPopulation();
			mmSample.getYear();
			Set mmEnvStudies=mmSample.getMmExperimentStudies();
			for(Iterator it1=mmEnvStudies.iterator();it1.hasNext();)
			{
				MmExperimentStudy mmEnvStudy=(MmExperimentStudy)it1.next();
				retrieveEnvironmentalStudy(mmEnvStudy);
			}
		}
	}
	public static MmSpecies retrieveSpecies(Integer id) throws Exception
	{
		
		MmSpeciesDAO spDao=new MmSpeciesDAO();
		MmSpecies species= spDao.findById(id);
		species.getFamily();
		species.getGenus();
		species.getSpecies();
		
		Set mmStudies=species.getMmMatingSystemStudies();
		for(Iterator it=mmStudies.iterator();it.hasNext();)
		{
			MmMatingSystemStudy mmStudy=(MmMatingSystemStudy)it.next();
			mmStudy.getLatitude();
			MmReferencePart refPart=mmStudy.getMmReferencePart();
			if(refPart!=null)
			{
				refPart.getName();
				MmReference ref=refPart.getMmReference();
				if(ref!=null)
				{
					ref.getCitation();
					ref.getFullReference();
				}
			}
		}
		
		Set mmSamples=species.getMmPopulationSamples();
		for(Iterator it=mmSamples.iterator();it.hasNext();)
		{
			MmPopulationSample mmSample=(MmPopulationSample)it.next();
			mmSample.getGeographicLocation();
			mmSample.getEnvironment();
			mmSample.getName();
			mmSample.getPopulation();
			mmSample.getYear();
			Set set =mmSample.getMmPopSampleAttrCvtermAssocs();
			for(Iterator it1=set.iterator();it1.hasNext();)
			{
				MmPopSampleAttrCvtermAssoc cvAssoc=(MmPopSampleAttrCvtermAssoc)it1.next();
				MmCvTerm term=cvAssoc.getMmCvTerm();
				if(term!=null)
				{
					term.getCvtermOid();
					term.getDescription();
					term.getName();
					term.getNamespace();
					term.getValueType();
				}
				cvAssoc.getValue();
			}
			Set mmEnvStudies=mmSample.getMmExperimentStudies();
			for(Iterator it1=mmEnvStudies.iterator();it1.hasNext();)
			{
				MmExperimentStudy envStudy=(MmExperimentStudy)it1.next();
				envStudy.getName();
				envStudy.getExperimentStudyOid();
				set= envStudy.getMmDataRecords();
				for(Iterator it2=set.iterator();it2.hasNext();)
				{
					MmDataRecord record=(MmDataRecord)it2.next();
					if(record!=null)
					{
						record.getName();
						record.getOutCrossingStdDev();
						record.getOutCrossingValue();
						record.getSelfingStdDev();
						record.getSelfingValue();
						record.getType();
					}
				}
				
				MmDevelopmentalStage stage=envStudy.getMmDevelopmentalStage();
				if(stage!=null)
					stage.getName();
				
				set= envStudy.getMmExperimentValues();
				
				for(Iterator it2=set.iterator();it2.hasNext();)
				{
					MmExperimentValue value=(MmExperimentValue)it2.next();
					
					MmCvTerm term=value.getMmCvTerm();
					if(term!=null)
					{
						term.getCvtermOid();
						term.getDescription();
						term.getName();
						term.getNamespace();
						term.getValueType();
					}
					
					value.getValue();
				}
				
				
				
				MmReferencePart refPart=envStudy.getMmReferencePart();
				if(refPart!=null)
				{
					refPart.getName();
					MmReference ref=refPart.getMmReference();
					if(ref!=null)
					{
						ref.getCitation();
						ref.getFullReference();
					}
				}
			}
		}
		
		return species;
	}
	public static void retrieveDiscriptor(MmMatingSystemStudy mmStudy) throws Exception
	{
		MmSpecies species=mmStudy.getMmSpecies();
		if(species!=null)
		{
			species.getFamily();
			species.getGenus();
			species.getSpecies();
		}
		mmStudy.getLatitude();
		MmReferencePart refPart=mmStudy.getMmReferencePart();
		if(refPart!=null)
		{
			refPart.getName();
			MmReference ref=refPart.getMmReference();
			if(ref!=null)
			{
				ref.getCitation();
				ref.getFullReference();
			}
		}
		Set mmCvTermsAssocs=mmStudy.getMmSpeciesAttrCvtermAssocs();
		
		for(Iterator it=mmCvTermsAssocs.iterator();it.hasNext();)
		{
			MmSpeciesAttrCvtermAssoc cvAssoc=(MmSpeciesAttrCvtermAssoc)it.next();
			if(cvAssoc!=null)
			{
				cvAssoc.getValue();
				MmCvTerm term=cvAssoc.getMmCvTerm();
				if(term!=null)
				{
					term.getCvtermOid();
					term.getDescription();
					term.getName();
					term.getNamespace();
					term.getValueType();
				}
			}
		}
	}
}
