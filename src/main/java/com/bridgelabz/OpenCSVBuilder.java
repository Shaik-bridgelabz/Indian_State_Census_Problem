package com.bridgelabz;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder <E> implements ICSVBuilder {

    @Override
    public Iterator <E>  getCSVfileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader,csvClass).iterator();
    }

    @Override
    public List getCSVfileList(Reader reader, Class csvClass) throws CSVBuilderException {
       return this.getCSVBean(reader,csvClass).parse();
    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBeanBuilder.build();
        } catch (RuntimeException e) {
            throw new CSVBuilderException("Header or delimiter not proper.", CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (Exception e) {
            throw new CSVBuilderException("File not proper",CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

}