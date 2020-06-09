package com.example.myapplication;

public class Article {

    String Title,Description,Image,Content ;

    public Article() {
    }

    public Article(String title, String description, String image, String content) {
        Title = title;
        Description = description;
        Image = image;
        Content = content;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content.replace("_b", "_cc");
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
