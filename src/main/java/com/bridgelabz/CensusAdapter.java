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

public abstract class CensusAdapter {

    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws StateCensusException;

    public <E> Map<String,CensusDAO> loadCensusData(Class<E> censusCsvClass, String csvFilePath) throws StateCensusException {
        Map<String,CensusDAO> censusStateMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVfileIterator(reader,censusCsvClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if(censusCsvClass.getName().contains("CSVStateCensus")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(CSVStateCensus.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCsvClass.getName().contains("CSVUSCensus")){
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(CSVUSCensus.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusStateMap;
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
}