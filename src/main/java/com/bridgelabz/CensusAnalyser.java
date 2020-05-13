package com.bridgelabz;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static java.nio.file.Files.newBufferedReader;

public class CensusAnalyser<E> {

    List<CSVStateCensus> censusList = null;
    List<CSVStateCode> codeCSVList=null;

    Map <String, CSVStateCensus> StateCensusCSVMap = null;
    Map <String, CSVStateCode> StateCodeCSVMap = null;

    public CensusAnalyser() {
        this.StateCensusCSVMap=new HashMap<>();
        this.StateCodeCSVMap=new HashMap<>();
    }

    public int loadIndianStateCensusData(String filepath) throws StateCensusException {
        try (Reader reader = newBufferedReader(Paths.get(filepath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVfileIterator(reader,CSVStateCensus.class);
            while (csvFileIterator.hasNext()) {
                CSVStateCensus csvStateCensus = csvFileIterator.next();
                this.StateCensusCSVMap.put(csvStateCensus.state,csvStateCensus);
                censusList=StateCensusCSVMap.values().stream().collect(Collectors.toList());
            }
            int numberOfRecords=StateCensusCSVMap.size();
            return numberOfRecords;
        } catch (NoSuchFileException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_FILE_FOUND,"File Not Found in Path");
        } catch (RuntimeException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        } catch (IOException e){
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_EXCEPTION,"File Not Proper");
        } catch (CSVBuilderException e) {
            throw new StateCensusException(e.getMessage(),e.type.name());
        }
    }

    public Integer loadIndianStateCodeData (String csvFilePath) throws StateCensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> csvFileIterator = csvBuilder.getCSVfileIterator(reader,CSVStateCode.class);
            while (csvFileIterator.hasNext()) {
                CSVStateCode csvStateCode = csvFileIterator.next();
                this.StateCodeCSVMap.put(csvStateCode.StateCode,csvStateCode);
                codeCSVList=StateCodeCSVMap.values().stream().collect(Collectors.toList());
            }
            int numberOfRecords=StateCodeCSVMap.size();
            return numberOfRecords;
        } catch (NoSuchFileException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_FILE_FOUND, "File Not Found in Path");
        } catch (RuntimeException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        } catch (CSVBuilderException e) {
            throw new StateCensusException(e.getMessage(),e.type.name());
        } catch (IOException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_EXCEPTION,"File Not Proper");
        }
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
        Comparator<CSVStateCode> stateCodeComparator = Comparator.comparing(census -> census.StateCode);
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
        loadIndianStateCensusData(csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.population);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

}
