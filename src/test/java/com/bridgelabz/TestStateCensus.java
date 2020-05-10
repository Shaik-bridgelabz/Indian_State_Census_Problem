package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TestStateCensus {

    @Test
    public void givenStateCensusCSV_WhenConditionTrue_ReturnNumberOfRecordMatch() throws IOException {
        StateCensusAnalyser censusAnalyserObject = new StateCensusAnalyser();
        int totalNumberOfRecords = censusAnalyserObject.loadData();
        Assert.assertEquals(29, totalNumberOfRecords);
    }
}
