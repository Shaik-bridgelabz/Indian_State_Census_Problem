package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class TestStateCensus {

    public static String CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE = "./src/test/resources/StateCensus.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION = "./src/test/resources/StateCensus.jpg";

    @Test
    public void givenStateCensusCSV_WhenConditionTrue_ReturnNumberOfRecordMatch() throws StateCensusException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser(CSV_FILE_PATH);
        int totalNumberOfRecords = stateCensusAnalyser.loadData();
        Assert.assertEquals(29, totalNumberOfRecords);
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperFileName_ReturnsException() throws StateCensusException {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser(CSV_FILE_PATH_FOR_WRONG_FILE);
            stateCensusAnalyser.loadData();
        }
        catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperFileExtension_ReturnsException() throws StateCensusException {
        try {
            StateCensusAnalyser censusAnalyserObject = new StateCensusAnalyser(CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION);
            censusAnalyserObject.loadData();
        }
        catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperDelimiter_ReturnsException() throws StateCensusException
    {
        try
        {
            StateCensusAnalyser censusAnalyserObject = new StateCensusAnalyser(CSV_FILE_PATH);
            censusAnalyserObject.loadData();
        }
        catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperHeader_ReturnsException() throws StateCensusException
    {
        try
        {
            StateCensusAnalyser censusAnalyserObject = new StateCensusAnalyser(CSV_FILE_PATH);
            censusAnalyserObject.loadData();
        }
        catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }
}
