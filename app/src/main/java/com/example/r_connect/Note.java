package com.example.r_connect;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Note {
    String name,branch,year,image;
    List<String> areaofin;

    @PropertyName("Name")
    public String getName() {
        return name;
    }
    @PropertyName("Name")
    public void setName(String name) {
        this.name = name;
    }
    @PropertyName("Branch")
    public void setBranch(String branch) {
        this.branch = branch;
    }
    @PropertyName("Graduation Year")
    public void setYear(String year) {
        this.year = year;
    }
    @PropertyName("Imageuri")
    public void setImage(String image) {
        this.image = image;
    }
    @PropertyName("Area of Interest")
    public void setAreaofin(List<String> areaofin) {
        this.areaofin = areaofin;
    }
    @PropertyName("Branch")
    public String getBranch() {
        return branch;
    }
    @PropertyName("Area of Interest")
    public String getAreaofin() {
        String s="";
        for(int i=0;i<areaofin.size();i++)
            s=s+areaofin.get(i)+" ";

        return s;
    }
    @PropertyName("Graduation Year")
    public String getYear() {
        return year;
    }
    @PropertyName("Imageuri")
    public String getImageuri() {
        return image;
    }

    public Note() {
        //empty constructor needed
    }
    public Note(String name, String branch, List<String> areaofin, String year, String image)
    {
        this.name=name;
        this.branch=branch;
        this.areaofin=areaofin;
        this.year=year;
        this.image=image;
    }
}
