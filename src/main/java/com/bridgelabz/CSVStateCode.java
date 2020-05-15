package com.bridgelabz;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCode {

    @CsvBindByName(column = "SrNo", required = true)
    public String srNo;

    @CsvBindByName(column = "StateName", required = true)
    public String state;

    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "CSVStateCode{" +
                "srNo='" + srNo + '\'' +
                ", stateName=" + state +
                ", tin=" + tin +
                ", stateCode=" + stateCode +
                '}';
    }
}