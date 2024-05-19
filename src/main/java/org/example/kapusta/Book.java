package org.example.kapusta;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Book {
    @Id
    @JsonProperty("id")
    private ObjectId id;
    @JsonProperty("author")
    public String authorName;

    @JsonProperty("title")
    public String title;

    @JsonProperty("year")
    public int year;

    @JsonProperty("quantity")
    public int quantity;

    @JsonProperty("imageUrl")
    public String imageUrl;

    public Book(){

    }
    public Book(String authorName, String title, int year, int quantity) {
        this.authorName = authorName;
        this.title = title;
        this.year = year;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getAuthorName() {
        return authorName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }
    public ObjectId getId() {
        return id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Author: " + authorName + ", Title: " + title + ", Year: " + year + ", Quantity: " + quantity;
    }
}
