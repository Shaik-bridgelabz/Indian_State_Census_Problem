package com.bridgelabz;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder <E> {
    public Iterator<E> getCSVfileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException;
}
