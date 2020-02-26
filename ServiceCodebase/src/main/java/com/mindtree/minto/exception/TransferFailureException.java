/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * AuthenticationFailureException.java
 * Created On: Feb 22, 2020 Created By: M1026329
 */
@ResponseStatus(code=HttpStatus.PRECONDITION_FAILED)
public class TransferFailureException extends Exception {
    
    /**
     * 
     */
    public TransferFailureException(String msg) {
        super(msg);
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2306613846490237418L;

}
