package com.bridgelabz;

public class CensusDAO {

    public String stateCode;
    public double populationDensity;
    public double totalArea;
    public double population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;

    public CensusDAO(CSVStateCensus indiaCensus) {
        state=indiaCensus.state;
        areaInSqKm=indiaCensus.areaInSqKm;
        densityPerSqKm=indiaCensus.densityPerSqKm;
        population= indiaCensus.population;
    }

    public CensusDAO(CSVUSCensus USCensus) {
        state = USCensus.state;
        population = USCensus.population;
        totalArea = USCensus.totalArea;
        populationDensity = USCensus.populationDensity;
        stateCode = USCensus.stateId;
    }

    public CSVStateCensus getCSVStateCensus() {
        return new CSVStateCensus(state, (int) population, (int) populationDensity, (int) totalArea);
    }

    public Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.US))
            return new CSVUSCensus(state, stateCode, population, populationDensity, totalArea);
        return new CSVStateCensus(state, (int) population, (int) populationDensity, (int) totalArea);
    }

}
