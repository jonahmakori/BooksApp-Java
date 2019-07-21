package com.hayssamsoussi.whattoread.database.model;

public class FavoriteBook {

    private String id;
    private String name;
    private String author;
    private String thumb;
    private String publisher;

    public FavoriteBook(String id, String name, String author, String thumb, String publisher) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.thumb = thumb;
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
