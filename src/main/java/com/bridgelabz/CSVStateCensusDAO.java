package com.bridgelabz;

public class CSVStateCensusDAO {

    public String population;
    public String densityPerSqKm;
    public String areaInSqKm;
    public String state;

    public CSVStateCensusDAO(CSVStateCensus indiaCensus) {
        state=indiaCensus.state;
        areaInSqKm=indiaCensus.AreaInSqKm;
        densityPerSqKm=indiaCensus.DensityPerSqKm;
        population= indiaCensus.population;
    }
}
