package com.forged.exceptions;

/**
 * Created by visitor15 on 7/5/14.
 */
public abstract class BaseException extends Exception {

    private String _errorMessage;

    protected BaseException(final String message) {
        _errorMessage = "";
    }

    public String getErrorMessage() {
        return _errorMessage;
    }

    protected void logError() {
        //TODO: write errors to file(s).
    }
}
