package com.rservice.businesslogic;

public enum Status {
    SUBMITTED(0, "Submitted"),
    PREPARING(1, "Preparing"),
    READY(2, "Ready"),
    DENIED(3, "Denied"),
    CLOSED(4, "Closed");

    private final int status;
    private final String statusName;

    Status(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public int getStatusId() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }

    /**
     * Get the status which corresponds to the given number as returned by
     * {@link Status#getStatusId()}. If the number is an invalid value,
     * then {@link Status#SUBMITTED} will be returned.
     *
     * @param status The numeric status value.
     * @return The status.
     */
    public static Status valueOf(int status) {
        try {
            return Status.values()[status];
        } catch (ArrayIndexOutOfBoundsException e) {
            return SUBMITTED;
        }
    }

    public static Status valueOfString(String status) {
        switch (status) {
            case "Submitted":
                return SUBMITTED;
            case "Preparing":
                return PREPARING;
            case "Ready":
                return READY;
            case "Denied":
                return DENIED;
            case "Closed":
                return CLOSED;
        }
        return SUBMITTED;
    }
}
