package com.bridgelabz;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;
import static java.nio.file.Files.newBufferedReader;

public class StateCodeAnalyser {

    private static String CSV_STATE_CODE_FILE_PATH = "./src/test/resources/StateCode.csv";
    int totalNumberOfRecords = 0;

    public StateCodeAnalyser(String path)
    {
        CSV_STATE_CODE_FILE_PATH = path;
    }

    public int loadStateCodeData() throws IOException {
        try (Reader reader = newBufferedReader(Paths.get(CSV_STATE_CODE_FILE_PATH)))
        {
            CsvToBean<CSVStateCode> csvStateCodeBean = new CsvToBeanBuilder(reader)
                    .withType(CSVStateCode.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVStateCode> csvStateCodeIterator = csvStateCodeBean.iterator();
            while (csvStateCodeIterator.hasNext()) {
                CSVStateCode csvStateCode = csvStateCodeIterator.next();
                System.out.println("SrNo :" +csvStateCode.getSrNo());
                System.out.println("StateName :" +csvStateCode.getStateName());
                System.out.println("TIN :" +csvStateCode.getTIN());
                System.out.println("StateCode :" +csvStateCode.getStateCode());
                totalNumberOfRecords++;
            }
        }catch (IOException e) {
            System.out.println("Error");
        }
        return totalNumberOfRecords;
    }
}
