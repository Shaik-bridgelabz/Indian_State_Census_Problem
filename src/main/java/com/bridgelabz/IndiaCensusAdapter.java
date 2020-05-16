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

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws StateCensusException {
        Map<String, CensusDAO> censusStateMap = super.loadCensusData(CSVStateCensus.class, csvFilePath[0]);
        this.loadIndianStateCodeData(censusStateMap, csvFilePath[1]);
        return censusStateMap;
    }

    private Map<String, CensusDAO> loadIndianStateCodeData(Map<String, CensusDAO> censusStateMap, String csvFilePath) throws StateCensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> csvFileIterator = csvBuilder.getCSVfileIterator(reader,CSVStateCode.class);
            Iterable<CSVStateCode> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                         .filter(csvState -> censusStateMap.get(csvState.state) != null)
                         .forEach(csvState -> censusStateMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusStateMap;
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
