package com.bridgelabz;

import com.google.gson.Gson;

import java.util.*;

import static java.nio.file.Files.newBufferedReader;

public class CensusAnalyser<E> {

    List<CSVStateCensus> censusList = null;
    List<CSVStateCode> codeCSVList=null;

    Map<String, CensusDAO> csvFileMap = null;
    public enum Country{ INDIA, US }

    public int loadCensusData(Country country,String... csvFilePath) throws StateCensusException {
        csvFileMap=new CensusAdapterFactory().censusFactory(country,csvFilePath);
        return csvFileMap.size();
    }

    public String getStateWiseSortedCensusData() throws StateCensusException {
        if (censusList==null || censusList.size()==0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA,"NO Data Found");
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.state);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedStateCodeData() throws StateCensusException {
        if (codeCSVList==null || codeCSVList.size()==0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA,"NO Data Found");
        }
        Comparator<CSVStateCode> stateCodeComparator = Comparator.comparing(census -> census.stateCode);
        this.sortStateCode(stateCodeComparator);
        String sortedStateCensusJson = new Gson().toJson(this.codeCSVList);
        return sortedStateCensusJson;
    }

    private void sortStateCode(Comparator<CSVStateCode> stateCodeComparator) {
        for (int i = 0; i < codeCSVList.size()-1; i++) {
            for(int j = 0; j < codeCSVList.size()-i-1; j++) {
                CSVStateCode census1 = codeCSVList.get(j);
                CSVStateCode census2= codeCSVList.get(j+1);
                if(stateCodeComparator.compare(census1,census2) > 0) {
                    codeCSVList.set(j, census2);
                    codeCSVList.set(j+1, census1);
                }
            }
        }
    }

    private void sortCensusData(Comparator<CSVStateCensus> censusComparator) {
        for (int i = 0; i < censusList.size()-1; i++) {
            for(int j = 0; j < censusList.size()-i-1; j++) {
                CSVStateCensus census1 = censusList.get(j);
                CSVStateCensus census2= censusList.get(j+1);
                if(censusComparator.compare(census1,census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
    }

    public String getPopulationWiseSortedCensusData(String csvFilePath) throws StateCensusException {
        loadCensusData(Country.INDIA,csvFilePath,csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.population);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getDensityWiseSortedCensusData(String csvFilePath) throws StateCensusException {
        loadCensusData(Country.INDIA,csvFilePath,csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getAreaWiseSortedCensusData(String csvFilePath) throws StateCensusException {
        loadCensusData(Country.INDIA,csvFilePath,csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }
}