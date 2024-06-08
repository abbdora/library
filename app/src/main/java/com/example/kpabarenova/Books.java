package com.example.kpabarenova;

public class Books {
    private int id;
    private String information;
    private String nameBook;
    private String photo;

    public Books(int id, String information, String nameBook, String photo){
        this.id = id;
        this.information = information;
        this.nameBook = nameBook;
        this.photo = photo;
    }
    public int geId() {
        return id;
    }
    public void seId(int id) {
        this.id = id;
    }
    public String getInformation(){
        return information;
    }
    public void setInformation(String information){
        this.information = information;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
