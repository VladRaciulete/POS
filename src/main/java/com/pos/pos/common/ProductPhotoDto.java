package com.pos.pos.common;

public class ProductPhotoDto {
    private Long id;
    private String fileName;
    private String fileType;
    private byte[] fileContent;

    public ProductPhotoDto(Long id, String fileName, String fileType, byte[] fileContent) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileContent = fileContent;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }
}
