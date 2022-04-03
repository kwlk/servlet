package com.beans;

public class Constants {
    public static String ERROR_RESPONSE_NO_USER = "No user found.";
    public static String ERROR_USER_NOT_ADMIN = "User does not have admin permissions.";
    public static String ERROR_BOOK_IN_DATABASE = "This book is already in the database.";
    public static String ERROR_BOOK_NOT_IN_DATABASE = "This book does not exist in the database.";
    public static String ERROR_FIELD_EMPTY = "None of the fields can be empty.";

    public static String MESSAGE_USER_LOGGED (String username) {
        return String.format("User %s logged successfully.", username);
    }
}
