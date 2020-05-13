package com.bridgelabz;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class TestStateCensus {

    CensusAnalyser censusAnalyser =new CensusAnalyser();

    public static String CSV_CENSUS_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE = "./src/test/resources/StateCensus.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION = "./src/test/resources/StateCensus.jpg";
    public static String CSV_STATES_CODE_FILE_PATH = "./src/test/resources/StateCode.csv";
    public static String CSV_WRONG_STATES_CODE_FILE_PATH = "./src/test/resources/WrongStateCode.csv";

    @Test
    public void givenStateCensusCSV_WhenConditionTrue_ReturnNumberOfRecordMatch() {
        int totalNumberOfRecords = 0;
        try {
            totalNumberOfRecords = censusAnalyser.loadIndianStateCensusData(CSV_CENSUS_FILE_PATH);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(29, totalNumberOfRecords);
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperFileName_ReturnsException() {
        try {
            censusAnalyser.loadIndianStateCensusData(CSV_FILE_PATH_FOR_WRONG_FILE);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperFileExtension_ReturnsException() {
        try {
            censusAnalyser.loadIndianStateCensusData(CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperDelimiter_ReturnsException() {
        try {
            censusAnalyser.loadIndianStateCensusData(CSV_CENSUS_FILE_PATH);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperHeader_ReturnsException() {
        try {
            censusAnalyser.loadIndianStateCensusData(CSV_CENSUS_FILE_PATH);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCodeFile_WhenTrue_ReturnNumberOfRecordMatch() {
        Integer noOfRecords= null;
        try {
            noOfRecords = censusAnalyser.loadIndianStateCodeData(CSV_STATES_CODE_FILE_PATH);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
        Assert.assertEquals((Integer)37, noOfRecords);
    }

    @Test
    public void givenStateCode_WhenFalse_ReturnExceptionFileNotFound() {
        try {
            censusAnalyser.loadIndianStateCodeData(CSV_WRONG_STATES_CODE_FILE_PATH);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCodeFile_WhenImproperFileExtension_ReturnsException() {
        try {
            censusAnalyser.loadIndianStateCodeData(CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCode_WhenImproperDelimiter_Should_ReturnException() {
        try {
            censusAnalyser.loadIndianStateCodeData(CSV_STATES_CODE_FILE_PATH);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCode_WhenImproperHeader_Should_ReturnException() {
        try {
            censusAnalyser.loadIndianStateCodeData(CSV_STATES_CODE_FILE_PATH);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {

        try {
            censusAnalyser.loadIndianStateCensusData(CSV_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData,CSVStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].getState());
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnFirstValue_SortedResult() {

        try {
            censusAnalyser.loadIndianStateCodeData(CSV_STATES_CODE_FILE_PATH);
            String sortedStateCodeDataData = censusAnalyser.getStateCodeWiseSortedStateCodeData();
            CSVStateCode[] codeCSV = new Gson().fromJson(sortedStateCodeDataData,CSVStateCode[].class);
            Assert.assertEquals("AD",codeCSV[0].StateCode);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnLastValue_SortedResult() {

        try {
            censusAnalyser.loadIndianStateCodeData(CSV_STATES_CODE_FILE_PATH);
            String sortedStateCodeDataData = censusAnalyser.getStateCodeWiseSortedStateCodeData();
            CSVStateCode[] codeCSV = new Gson().fromJson(sortedStateCodeDataData,CSVStateCode[].class);
            Assert.assertEquals("WB",codeCSV[36].StateCode);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }
}