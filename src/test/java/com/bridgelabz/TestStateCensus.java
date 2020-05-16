package com.bridgelabz;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class TestStateCensus {

    CensusAnalyser censusAnalyserIndia =new CensusAnalyser(CensusAnalyser.Country.INDIA);
    CensusAnalyser censusAnalyserUS =new CensusAnalyser(CensusAnalyser.Country.US);

    public static String CSV_CENSUS_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE = "./src/test/resources/StateCensus.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION = "./src/test/resources/StateCensus.jpg";
    public static String CSV_STATES_CODE_FILE_PATH = "./src/test/resources/StateCode.csv";
    public static String CSV_WRONG_STATES_CODE_FILE_PATH = "./src/test/resources/WrongStateCode.csv";
    public static String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenStateCensusCSV_WhenConditionTrue_ReturnNumberOfRecordMatch() {
        try {
            int totalNumberOfRecords = censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            Assert.assertEquals(29, totalNumberOfRecords);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperFileName_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_FILE_PATH_FOR_WRONG_FILE);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperFileExtension_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperDelimiter_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperHeader_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCodeFile_WhenTrue_ReturnNumberOfRecordMatch() {
        try {
            int noOfRecords= censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            Assert.assertEquals(29,noOfRecords);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCode_WhenFalse_ReturnExceptionFileNotFound() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_FILE_PATH_FOR_WRONG_FILE,CSV_FILE_PATH_FOR_WRONG_FILE);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCodeFile_WhenImproperFileExtension_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION,CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCode_WhenImproperDelimiter_Should_ReturnException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCode_WhenImproperHeader_Should_ReturnException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);;
        } catch (StateCensusException e) {
            Assert.assertEquals(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedCensusData = censusAnalyserIndia.getStateWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData,CSVStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnFirstValue_SortedResult() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedStateCodeDataData = censusAnalyserIndia.getStateCodeWiseSortedStateCodeData();
            CSVStateCode[] codeCSV = new Gson().fromJson(sortedStateCodeDataData,CSVStateCode[].class);
            Assert.assertEquals("AD",codeCSV[0].stateCode);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnLastValue_SortedResult() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedStateCodeDataData = censusAnalyserIndia.getStateCodeWiseSortedStateCodeData();
            CSVStateCode[] codeCSV = new Gson().fromJson(sortedStateCodeDataData,CSVStateCode[].class);
            Assert.assertEquals("WB",codeCSV[36].stateCode);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedCensusData = censusAnalyserIndia.getPopulationWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals(199812341,censusCSV[censusCSV.length - 1].population);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturn_NumberOfStatesSortedResult() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedCensusData = censusAnalyserIndia.getPopulationWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals(30,censusCSV.length+1);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturn_MostPopulationDensityState() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedCensusData = censusAnalyserIndia.getDensityWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Bihar",censusCSV[censusCSV.length - 1].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturn_LeastPopulationDensityState() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedCensusData = censusAnalyserIndia.getDensityWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Arunachal Pradesh",censusCSV[0].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturn_LargestStateByArea() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedCensusData = censusAnalyserIndia.getAreaWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Rajasthan",censusCSV[censusCSV.length - 1].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturn_SmallestStateByArea() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedCensusData = censusAnalyserIndia.getAreaWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Goa",censusCSV[0].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSV_WhenConditionTrue_ReturnNumberOfRecordMatch() {
        try {
            int totalNumberOfRecords = censusAnalyserUS.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, totalNumberOfRecords);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            censusAnalyserUS.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyserUS.getStateWiseSortedCensusData();
            CSVUSCensus[] censusCSV = new Gson().fromJson(sortedCensusData,CSVUSCensus[].class);
            Assert.assertEquals("Wyoming",censusCSV[censusCSV.length-1].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            censusAnalyserUS.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyserUS.getPopulationWiseSortedCensusData();
            CSVUSCensus[] censusCSV = new Gson().fromJson(sortedCensusData,CSVUSCensus[].class);
            Assert.assertEquals("California",censusCSV[censusCSV.length-1].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnDensity_ShouldReturnSortedResult() {
        try {
            censusAnalyserUS.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyserUS.getDensityWiseSortedCensusData();
            CSVUSCensus[] censusCSV = new Gson().fromJson(sortedCensusData,CSVUSCensus[].class);
            Assert.assertEquals("District of Columbia",censusCSV[censusCSV.length-1].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        try {
            censusAnalyserUS.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyserUS.getAreaWiseSortedCensusData();
            CSVUSCensus[] censusCSV = new Gson().fromJson(sortedCensusData,CSVUSCensus[].class);
            Assert.assertEquals("Alaska",censusCSV[censusCSV.length-1].state);
        } catch (StateCensusException e) {
            e.printStackTrace();
        }
    }
}