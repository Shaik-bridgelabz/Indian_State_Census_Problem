package com.bridgelabz;

import java.util.Map;

public class CensusAdapterFactory {

    public Map<String, CensusDAO> censusFactory(CensusAnalyser.Country country, String... csvFilePath) throws StateCensusException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        throw new StateCensusException(StateCensusException.TypeOfException.INVALID_COUNTRY,"Invalid Country");
    }
}