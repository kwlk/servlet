package com.responses;

import com.beans.Book;

public class BookResponse {
    private Book book;
    private int status;

    public BookResponse(Book book, int status) {
        this.book = book;
        this.status = status;
    }
}
