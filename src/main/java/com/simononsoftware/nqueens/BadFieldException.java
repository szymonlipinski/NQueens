package com.simononsoftware.nqueens;

/**
 * Exception used when accessing wrong field.
 */
public class BadFieldException extends RuntimeException {

    public BadFieldException(Board board, Field field) {
        super(String.format("The field %s doesn't fit on the board with size %d", field, board.getSize()));
    }

    public BadFieldException(String msg) {
        super(msg);
    }
}
