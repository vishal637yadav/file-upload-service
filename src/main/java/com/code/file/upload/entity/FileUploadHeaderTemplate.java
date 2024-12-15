package com.code.file.upload.entity;

import jakarta.persistence.*;

@Entity
public class FileUploadHeaderTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myFileHdrSeqGen")
    @SequenceGenerator(name = "myFileHdrSeqGen", sequenceName = "FileHeaderTemplate_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique=true)
    private String templateCode;
    @Column(nullable = false)
    private String fileNamingPattern;
    @Column(length = 12)
    private String fileFormat;
    private String recordDelimiter;
    private String uploadType;
    private String fileHeaders;
    private String fileContentValidation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getFileNamingPattern() {
        return fileNamingPattern;
    }

    public void setFileNamingPattern(String fileNamingPattern) {
        this.fileNamingPattern = fileNamingPattern;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getRecordDelimiter() {
        return recordDelimiter;
    }

    public void setRecordDelimiter(String recordDelimiter) {
        this.recordDelimiter = recordDelimiter;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getFileHeaders() {
        return fileHeaders;
    }

    public void setFileHeaders(String fileHeaders) {
        this.fileHeaders = fileHeaders;
    }

    public String getFileContentValidation() {
        return fileContentValidation;
    }

    public void setFileContentValidation(String fileContentValidation) {
        this.fileContentValidation = fileContentValidation;
    }

    @Override
    public String toString() {
        return "FileUploadHeaderTemplate{" +
                "id=" + id +
                ", templateCode='" + templateCode + '\'' +
                ", fileNamingPattern='" + fileNamingPattern + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", recordDelimiter='" + recordDelimiter + '\'' +
                ", uploadType='" + uploadType + '\'' +
                ", fileHeaders='" + fileHeaders + '\'' +
                ", fileContentValidation='" + fileContentValidation + '\'' +
                '}';
    }
}
