package com.bridgelabz;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public class CensusAnalyser<E> {

    List<CSVStateCensus> censusCSVList = null;

    public int loadIndianStateCensusData(String filepath) throws StateCensusException {
        int totalNumberOfRecords=0;
        try (Reader reader = newBufferedReader(Paths.get(filepath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVfileList(reader,CSVStateCensus.class);
            return censusCSVList.size();
        } catch (NoSuchFileException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_FILE_FOUND,"File Not Found in Path");
        }
        catch (RuntimeException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (CSVBuilderException e) {
            throw new StateCensusException(e.getMessage(),e.type.name());
        }
        return totalNumberOfRecords;
    }

    public Integer loadIndianStateCodeData (String csvFilePath) throws StateCensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCode> codeCSVList = csvBuilder.getCSVfileList(reader,CSVStateCode.class);
            return codeCSVList.size();
        } catch (NoSuchFileException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_FILE_FOUND, "File Not Found in Path");
        } catch (RuntimeException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (CSVBuilderException e) {
        throw new StateCensusException(e.getMessage(),e.type.name());
    }
        return (null);
    }

    private <E> int getCount(Iterator<E> iterator) {
    Iterable<E> csvIterable = () -> iterator;
    int numOfRecords = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    return numOfRecords;
    }

    public String getStateWiseSortedCensusData() throws StateCensusException {
        if (censusCSVList==null || censusCSVList.size()==0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA,"NO Data Found");
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.getState());
            this.sort(censusComparator);
            String sortedStateCensusJson = new Gson().toJson(censusCSVList);
            return sortedStateCensusJson;
    }

    private void sort(Comparator<CSVStateCensus> censusComparator) {
        for (int i = 0; i < censusCSVList.size()-1; i++) {
            for(int j = 0; j < censusCSVList.size()-i-1; j++) {
                CSVStateCensus census1 = censusCSVList.get(j);
                CSVStateCensus census2= censusCSVList.get(j+1);
                if(censusComparator.compare(census1,census2) > 0) {
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1, census1);
                }
            }
        }
    }

}
