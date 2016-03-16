package com.jpm.stockmarket.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by vaisakh on 15/03/2016.
 */
@SuppressWarnings("serial")
public class StockServiceException extends RuntimeException {

    private HttpStatus code = null;

    /**
     * Constructor.
     */
    public StockServiceException() {
        super();
    }

    /**
     * Constructor.
     */
    public StockServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     */
    public StockServiceException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     */
    public StockServiceException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     */
    public StockServiceException(String message, HttpStatus code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Constructor.
     */
    public StockServiceException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

    /**
     * @return Returns the code.
     */
    public HttpStatus getCode() {
        return code;
    }

    /**
     * @param code
     *            The code to set.
     */
    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
