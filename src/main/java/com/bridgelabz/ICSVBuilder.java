package com.bridgelabz;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder <E> {
    public Iterator<E> getCSVfileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException;
    public List <E> getCSVfileList(Reader reader, Class<E> csvClass) throws CSVBuilderException;
}
