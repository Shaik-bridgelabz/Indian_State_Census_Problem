package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class TestStateCensus {

    public static String CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE = "./src/test/resources/StateCensus.csv";

    @Test
    public void givenStateCensusCSV_WhenConditionTrue_ReturnNumberOfRecordMatch() throws StateCensusException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser(CSV_FILE_PATH);
        int totalNumberOfRecords = stateCensusAnalyser.loadData();
        Assert.assertEquals(29, totalNumberOfRecords);
    }

    @Test
    public void givenStateCensusAnalyserFile_WhenImproperFileName_ReturnsException() throws StateCensusException {
        try
        {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser(CSV_FILE_PATH_FOR_WRONG_FILE);
            stateCensusAnalyser.loadData();
        }
        catch (StateCensusException e)
        {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }
}
