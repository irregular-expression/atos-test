package ru.irregularexpression.atostest.meetingrooms.model.web;

/**
 * Base class for handling api responses.
 * Here will be all methods and values which are common for each api response.
 */
public class ServerResponse {
    private int error;
    private boolean success;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
