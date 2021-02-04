package com.example.porkpit;

public class products {

    private String name,price,filepath, category,randomKey;
    public products(){

    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public products(String name, String price, String filepath,String description,String randomKey) {
        this.name = name;
        this.price = price;
        this.filepath = filepath;
        this.randomKey=randomKey;
        this.category =description;
    }
}
