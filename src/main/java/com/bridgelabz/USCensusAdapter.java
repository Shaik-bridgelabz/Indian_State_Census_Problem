package com.bridgelabz;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws StateCensusException {
        return super.loadCensusData(CSVUSCensus.class, csvFilePath[0]);
    }
}
