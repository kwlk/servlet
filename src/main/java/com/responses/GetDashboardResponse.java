package com.responses;

import com.beans.Book;

import java.util.List;

public class GetDashboardResponse {
    private List<Book> books;
    private int status;

    public GetDashboardResponse(List<Book> books, int status) {
        this.books = books;
        this.status = status;
    }
}
