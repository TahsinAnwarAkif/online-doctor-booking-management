package net.therap.onlinedoctorbookingmanagement.exceptions;

import static net.therap.onlinedoctorbookingmanagement.utilities.enums.OPERATION_RESULTS.BAD_REQUEST_ERROR;

/**
 * @author anwar
 * @since 2/10/18
 */
public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return BAD_REQUEST_ERROR.getMessage();
    }
}