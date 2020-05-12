package com.bridgelabz;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;
import static java.nio.file.Files.newBufferedReader;

public class CensusAnalyser<E> {

    List<CSVStateCensusDAO> censusList = null;

    public CensusAnalyser() {
        this.censusList=new ArrayList<CSVStateCensusDAO>();
    }

    public int loadIndianStateCensusData(String filepath) throws StateCensusException {
        int totalNumberOfRecords=0;
        try (Reader reader = newBufferedReader(Paths.get(filepath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVfileIterator(reader,CSVStateCensus.class);
            while (csvFileIterator.hasNext()) {
                this.censusList.add(new CSVStateCensusDAO(csvFileIterator.next()));
            }
            return censusList.size();
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
        if (censusList==null || censusList.size()==0) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_CENSUS_DATA,"NO Data Found");
        }
        Comparator<CSVStateCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
            this.sort(censusComparator);
            String sortedStateCensusJson = new Gson().toJson(this.censusList);
            return sortedStateCensusJson;
    }

    private void sort(Comparator<CSVStateCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size()-1; i++) {
            for(int j = 0; j < censusList.size()-i-1; j++) {
                CSVStateCensusDAO census1 = censusList.get(j);
                CSVStateCensusDAO census2= censusList.get(j+1);
                if(censusComparator.compare(census1,census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
    }

}
