package com.bridgelabz;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;

public class CensusAnalyser<E> {

    List<CSVStateCensus> censusList = null;
    List<CSVStateCode> codeCSVList=null;

    Map<String, CensusDAO> csvFileMap;
    public enum Country{ INDIA, US }
    private Country country;

    public CensusAnalyser() {
        this.csvFileMap=new HashMap<String, CensusDAO>();
    }

    public CensusAnalyser(Country country) {
        this.country=country;
    }

    public int loadCensusData(String... csvFilePath) throws StateCensusException {
        csvFileMap=new CensusAdapterFactory().censusFactory(country,csvFilePath);
        return csvFileMap.size();
    }

    public String getStateWiseSortedCensusData() throws StateCensusException {
        if (csvFileMap==null || csvFileMap.size()==0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA,"NO Data Found");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        ArrayList censusDTOS = csvFileMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedStateCodeData() throws StateCensusException {
        if (csvFileMap==null || csvFileMap.size()==0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA,"NO Data Found");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        ArrayList stateDTOS = csvFileMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCodeJson = new Gson().toJson(stateDTOS);
        return sortedStateCodeJson;
    }

    public String getPopulationWiseSortedCensusData(String csvFilePath) throws StateCensusException {
        loadCensusData(csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        ArrayList stateDTOS = csvFileMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getDensityWiseSortedCensusData(String csvFilePath) throws StateCensusException {
        loadCensusData(csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        ArrayList stateDTOS = csvFileMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getAreaWiseSortedCensusData(String csvFilePath) throws StateCensusException {
        loadCensusData(csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        ArrayList stateDTOS = csvFileMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }
}