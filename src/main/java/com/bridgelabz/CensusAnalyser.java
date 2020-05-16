package com.bridgelabz;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;

public class CensusAnalyser<E> {

    List<CensusDAO> censusList = null;
    List<CSVStateCode> codeCSVList=null;

    Map<String, CensusDAO> csvFileMap=null;
    public enum Country{ INDIA, US }
    private Country country;

    public CensusAnalyser(Country country) {
        this.country=country;
    }

    public int loadCensusData(String... csvFilePath) throws StateCensusException {
        csvFileMap=new CensusAdapterFactory().censusFactory(country,csvFilePath);
        censusList=csvFileMap.values().stream().collect(Collectors.toList());
        return csvFileMap.size();
    }

    public String getStateWiseSortedCensusData() throws StateCensusException {
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        return this.sortCensusData(censusComparator,country);
    }

    public String getStateCodeWiseSortedStateCodeData() throws StateCensusException {
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        return this.sortCensusData(censusComparator,country);
    }

    public String getPopulationWiseSortedCensusData() throws StateCensusException {
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        return this.sortCensusData(censusComparator,country);
    }

    public String getDensityWiseSortedCensusData() throws StateCensusException {
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        return this.sortCensusData(censusComparator,country);
    }

    public String getAreaWiseSortedCensusData() throws StateCensusException {
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        return this.sortCensusData(censusComparator,country);
    }

    private String sortCensusData(Comparator<CensusDAO> censusComparator, Country country) throws StateCensusException {
        if (csvFileMap==null || csvFileMap.size()==0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA,"NO Data Found");
        }
        ArrayList censusDTOS = csvFileMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }
}