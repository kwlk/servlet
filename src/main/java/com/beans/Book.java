package com.beans;

import java.util.*;

public class Book {
    protected static int counter = 0;
    protected String title;
    protected String author;
    protected int year;
    protected int id;

    public Book(String title, String author, int year) {
        this.author = author;
        this.title = title;
        this.year = year;
        counter += 1;
        this.id = counter;
    }

    public Book(int i){
        List<String> authors = new ArrayList<>(
                Arrays.asList("Stephen King", "J. K. Rowling", "Ernest Hemingway", "William Shakespeare", "J. R. R. Tolkien",
                        "Paulo Coelho", "F. Scott Fitzgerald", "George Orwell", "Jane Austen", "Emily Bronte"));
        ArrayList<String> titles = new ArrayList<>(
                Arrays.asList("The Shining", "Harry Potter and the Philosopher's Stone", "The Old Man and the sea",
                        "Romeo And Juliet", "Hobbit", "The Alchemist", "The Great Gatsby", "1984", "Pride and Prejudice",
                        "Wuthering Heights"));
        int yearSeed = 1980;
        int range = 42;
        Random generator = new Random(i);

        this.author = authors.get( i % authors.size());
        this.title = titles.get(i % titles.size());
        this.year = yearSeed + generator.nextInt(range);
        counter += 1;
        this.id = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id || year == book.year && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year, id);
    }
}
