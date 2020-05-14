package com.bridgelabz;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;
import static java.nio.file.Files.newBufferedReader;

public class CensusLoader {

    public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws StateCensusException {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return this.loadCensusData(CSVStateCensus.class,csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
                return this.loadCensusData(CSVUSCensus.class,csvFilePath);
        else throw new StateCensusException(StateCensusException.TypeOfException.INVALID_COUNTRY,"Invalid Country");
    }

    private  <E> Map<String, CensusDAO> loadCensusData(Class<E> CensusCsvClass, String... csvFilePath) throws StateCensusException {
        Map<String,CensusDAO> csvFileMap = new HashMap<>();
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVfileIterator(reader,CensusCsvClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if(CensusCsvClass.getName().equals("com.bridgelabz.CSVStateCensus")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                             .map(CSVStateCensus.class::cast)
                             .forEach(censusCSV -> csvFileMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (CensusCsvClass.getName().equals("com.bridgelabz.CSVUSCensus")){
                StreamSupport.stream(csvIterable.spliterator(), false)
                             .map(CSVUSCensus.class::cast)
                             .forEach(censusCSV -> csvFileMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if(csvFilePath.length == 1) return csvFileMap;
            this.loadIndianStateCodeData(csvFileMap, csvFilePath[1]);
            return csvFileMap;
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

    private Integer loadIndianStateCodeData(Map<String, CensusDAO> csvFileMap, String csvFilePath) throws StateCensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> csvFileIterator = csvBuilder.getCSVfileIterator(reader,CSVStateCode.class);
            Iterable<CSVStateCode> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                         .filter(csvState -> csvFileMap.get(csvState.stateName) != null)
                         .forEach(censusCSV -> csvFileMap.get(censusCSV.stateName).state = censusCSV.stateCode);
            return csvFileMap.size();
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

}
