package com.bridgelabz;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;

import static java.nio.file.Files.newBufferedReader;

public class StateCensusAnalyser {

    public static String CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";

    public StateCensusAnalyser(String path) {
        CSV_FILE_PATH = path;
    }

    int totalNumberOfRecords=0;

    public int loadData() throws StateCensusException {
        try (Reader reader = newBufferedReader(Paths.get(CSV_FILE_PATH));)
        {
            CsvToBean<CSVStateCensus> csvStateCensusBeanObj = new CsvToBeanBuilder(reader)
                    .withType(CSVStateCensus.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVStateCensus> stateCensusCSVIterator = csvStateCensusBeanObj.iterator();
            while (stateCensusCSVIterator.hasNext())
            {
                CSVStateCensus stateCensusCSV = stateCensusCSVIterator.next();
                System.out.println("State: " +stateCensusCSV.getState());
                System.out.println("Population: " +stateCensusCSV.getPopulation());
                System.out.println("Area: " +stateCensusCSV.getAreaInSqKm());
                System.out.println("Density: " +stateCensusCSV.getDensityPerSqKm());
                totalNumberOfRecords++;
            }
        } catch (IOException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.NO_FILE_FOUND,"File Not Found in Path");
        }
        return totalNumberOfRecords;
    }
}
