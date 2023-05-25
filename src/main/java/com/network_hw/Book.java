package com.network_hw;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private Integer id;
    private String name;
    private String description;
    private String author;
    private List<Review> reviews;
    private Integer soldAmount;

    public Book(Integer id, String name, String description, String author, List<Review> reviews, Integer soldAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.reviews = reviews;
        this.soldAmount = soldAmount;
    }


    BookSearchView getSearchView() {
        return new BookSearchView(id, name, description.substring(0, 20), author);
    }

    BookFullView getFullView() {
        List<ReviewPreview> revs = new ArrayList<>();
        for(Review r : reviews) {
            if(r.status == Review.Status.APPROVED || r.status == Review.Status.BY_MODERATOR) {
                revs.add(r.getPreview());
            }

        }
        return new BookFullView(id, name, description, author, revs);
    }

    List<Review> getReviews() {
        return reviews;
    }


    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
class BookSearchView {
    private Integer id;
    private String name;
    private String shortDescription;
    private String author;

    public BookSearchView() {

    }

    public BookSearchView(Integer id, String name, String shortDescription, String author) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
};
class BookFullView {
    private Integer id;
    private String name;
    private String description;
    private String author;
    private List<ReviewPreview> reviews;

    BookFullView(){};

    public BookFullView(Integer id, String name, String description, String author, List<ReviewPreview> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.reviews = reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<ReviewPreview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewPreview> reviews) {
        this.reviews = reviews;
    }


};
