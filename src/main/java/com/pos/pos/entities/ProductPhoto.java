package com.pos.pos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ProductPhoto {
    private Long id;

    String fileName;
    String fileType;
    byte[] fileContent;
    Product product;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    @OneToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
