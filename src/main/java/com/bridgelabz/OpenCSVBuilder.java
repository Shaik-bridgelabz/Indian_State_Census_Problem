package com.bridgelabz;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder <E> implements ICSVBuilder {
    public Iterator <E>  getCSVfileIterator(Reader reader, Class csvClass) throws StateCensusException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (RuntimeException e) {
            throw new StateCensusException(StateCensusException.TypeOfException.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or delimiter not proper.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}