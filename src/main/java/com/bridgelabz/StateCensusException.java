package com.bridgelabz;

public class StateCensusException extends Throwable {

    public enum TypeOfException {
        NO_FILE_FOUND
    }

    public TypeOfException type;

    public StateCensusException(TypeOfException type,String message) {
        super(message);
        this.type=type;
    }
}
