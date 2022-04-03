package com.requests;

public class AddBookRequest {
    private final String title;
    private final String author;
    private final int year;

    public AddBookRequest(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
