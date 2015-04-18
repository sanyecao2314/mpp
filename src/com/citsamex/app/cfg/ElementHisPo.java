package com.citsamex.app.cfg;

import java.util.Calendar;

import com.citsamex.service.db.MppBasePo;

public class ElementHisPo extends MppBasePo {


    private static final long serialVersionUID = 4302113727398097863L;
    private long uuid;
    private long id;
    private String name;
    private String path;
    private String path2;
    private String text;
    private String color;
    private String bgColor;
    private String left;
    private String top;
    private String company;
    private String seq;

    private String description;
    private String createusername;
    private Calendar createdatetime;
    private String updateusername;
    private Calendar updatedatetime;
    
    public long getUuid() {
        return uuid;
    }
    public void setUuid(long uuid) {
        this.uuid = uuid;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getPath2() {
        return path2;
    }
    public void setPath2(String path2) {
        this.path2 = path2;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getBgColor() {
        return bgColor;
    }
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
    public String getLeft() {
        return left;
    }
    public void setLeft(String left) {
        this.left = left;
    }
    public String getTop() {
        return top;
    }
    public void setTop(String top) {
        this.top = top;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCreateusername() {
        return createusername;
    }
    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }
    public Calendar getCreatedatetime() {
        return createdatetime;
    }
    public void setCreatedatetime(Calendar createdatetime) {
        this.createdatetime = createdatetime;
    }
    public String getUpdateusername() {
        return updateusername;
    }
    public void setUpdateusername(String updateusername) {
        this.updateusername = updateusername;
    }
    public Calendar getUpdatedatetime() {
        return updatedatetime;
    }
    public void setUpdatedatetime(Calendar updatedatetime) {
        this.updatedatetime = updatedatetime;
    }
    
    
    

}
