package com.ecom.ExpressEcom.constants;

public final class AppConstants {

    // General messages
    public static final String RESOURCE_NOT_FOUND = "Requested resource not found";
    public static final String SUCCESS = "Operation completed successfully";
    public static final String VALIDATION_FAILED = "Validation failed";

    // User related messages
    public static final String USER_NOT_FOUND = "User not found with id: %d";
    public static final String USER_EMAIL_NOT_FOUND = "User not found with email: %s";
    public static final String USER_SAVED = "User saved successfully";
    public static final String USER_UPDATED = "User updated successfully";
    public static final String USER_DELETED = "User deleted successfully";
    public static final String EMAIL_ALREADY_EXISTS = "User with email %s already exists";
    public static final String EMAIL_REQUIRED="Email is Not Provided";

    // API Response keys
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATA = "data";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ERRORS = "errors";
    public static final String KEY_PATH = "path";

    // Response status messages
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";

    private AppConstants() {
        // Private constructor to prevent instantiation
    }
}
