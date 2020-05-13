package com.bridgelabz;

public class CSVStateCensusDAO {

    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;

    public CSVStateCensusDAO(CSVStateCensus indiaCensus) {
        state=indiaCensus.state;
        areaInSqKm=indiaCensus.areaInSqKm;
        densityPerSqKm=indiaCensus.densityPerSqKm;
        population= indiaCensus.population;
    }
}
