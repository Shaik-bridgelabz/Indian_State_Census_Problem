package com.bridgelabz;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser <E> {

    public int readFile(String filepath) throws StateCensusException {
        int totalNumberOfRecords=0;
        try (Reader reader = newBufferedReader(Paths.get(filepath));) {
            Iterator<CSVStateCensus> stateCSVIterator = this.getCSVfileIterator(reader, CSVStateCensus.class);
            Iterable<CSVStateCensus> csvIterable = ()->stateCSVIterator;
            int numOfRecords = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();

            return numOfRecords;

        } catch (NoSuchFileException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_FILE_FOUND,"File Not Found in Path");
        }
        catch (RuntimeException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return totalNumberOfRecords;
    }
    public Integer loadIndianStateCodeData (String csvFilePath) throws StateCensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<CSVStateCode> csvStateCodeIterator = this.getCSVfileIterator(reader, CSVStateCode.class);
            Iterable<CSVStateCode> csvStateCodeIterable = () -> csvStateCodeIterator;
            int countRecord = (int) StreamSupport.stream(csvStateCodeIterable.spliterator(),false).count();
            return countRecord;
        } catch (NoSuchFileException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_FILE_FOUND, "File Not Found in Path");
        } catch (RuntimeException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (null);
    }

    private < E > Iterator < E > getCSVfileIterator(Reader reader, Class < E > csvClass) throws StateCensusException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (RuntimeException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or delimiter not proper.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
