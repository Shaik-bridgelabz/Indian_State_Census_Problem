package com.bridgelabz;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;
import static java.nio.file.Files.newBufferedReader;

public class CensusLoader {

    public <E> Map<String, CensusDAO> loadCensusData(String filepath, Class<E> CensusCsvClass) throws StateCensusException {
        Map<String,CensusDAO> csvFileMap = new HashMap<>();
        try (Reader reader = newBufferedReader(Paths.get(filepath));) {
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
}
