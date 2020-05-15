package com.bridgelabz;

public class StateCensusException extends Exception {

    public enum TypeOfException {
        NO_FILE_FOUND,
        INCORRECT_DELIMITER_EXCEPTION,
        INCORRECT_DELIMITER_HEADER_EXCEPTION,
        NO_CENSUS_DATA,
        INVALID_COUNTRY;
    }

    public TypeOfException type;

    public StateCensusException(TypeOfException type,String message) {
        super(message);
        this.type=type;
    }

    public StateCensusException(String message,String name) {
        super(message);
        this.type=TypeOfException.valueOf(name);
    }

}
