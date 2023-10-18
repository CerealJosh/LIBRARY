package com.myproject.library.Models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int Id;
    public String Title;
    public String ISBN;
    public int RevisionNumber;
    public Date PublicationDate;
    public String Publisher;
    public String Authors;
    public Date DateAdded;
    public String Genre;
    public boolean Availability;
    
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }
    public int getRevisionNumber() {
        return RevisionNumber;
    }
    public void setRevisionNumber(int revisionNumber) {
        RevisionNumber = revisionNumber;
    }
    public Date getPublicationDate() {
        return PublicationDate;
    }
    public void setPublicationDate(Date publicationDate) {
        PublicationDate = publicationDate;
    }
    public String getPublisher() {
        return Publisher;
    }
    public void setPublisher(String publisher) {
        Publisher = publisher;
    }
    public String getAuthors() {
        return Authors;
    }
    public void setAuthors(String authors) {
        Authors = authors;
    }
    public Date getDateAdded() {
        return DateAdded;
    }
    public void setDateAdded(Date dateAdded) {
        DateAdded = dateAdded;
    }
    public String getGenre() {
        return Genre;
    }
    public void setGenre(String genre) {
        Genre = genre;
    }
    public boolean isAvailability() {
        return Availability;
    }
    public void setAvailability(boolean availability) {
        Availability = availability;
    }
}
