package com.example.kpabarenova;

public class Read {
    private int id;
    private String nameBook;

    public Read(int id, String nameBook){
        this.id = id;
        this.nameBook = nameBook;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return nameBook;
    }
    public void setName(String nameBook) {
        this.nameBook = nameBook;
    }
}
