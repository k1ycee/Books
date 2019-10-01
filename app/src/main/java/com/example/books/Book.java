package com.example.books;

public class Book {

    public String id;
    public String title;
    public String subtitle;
    public String[] publisher;
    public String authors;
    public String publishdate;

    public Book(String id, String title, String subtitle, String[] publisher, String authors, String publishdate) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.authors = authors;
        this.publishdate = publishdate;
    }
}
