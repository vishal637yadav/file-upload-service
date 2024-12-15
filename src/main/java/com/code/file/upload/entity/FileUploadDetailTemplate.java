package com.code.file.upload.entity;

import jakarta.persistence.*;

@Entity
public class FileUploadDetailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myFileDtlSeqGen")
    @SequenceGenerator(name = "myFileDtlSeqGen", sequenceName = "FileDetailTemplate_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String templateCode;

    private String columnName;
    private String columnAlias;
    private String columnDatatype;
    private int columnSequence;
    private String columnSpecification;
    private String isMandatory;

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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnAlias() {
        return columnAlias;
    }

    public void setColumnAlias(String columnAlias) {
        this.columnAlias = columnAlias;
    }

    public String getColumnDatatype() {
        return columnDatatype;
    }

    public void setColumnDatatype(String columnDatatype) {
        this.columnDatatype = columnDatatype;
    }

    public int getColumnSequence() {
        return columnSequence;
    }

    public void setColumnSequence(int columnSequence) {
        this.columnSequence = columnSequence;
    }

    public String getColumnSpecification() {
        return columnSpecification;
    }

    public void setColumnSpecification(String columnSpecification) {
        this.columnSpecification = columnSpecification;
    }

    public String getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(String isMandatory) {
        this.isMandatory = isMandatory;
    }

    @Override
    public String toString() {
        return "FileUploadDetailTemplate{" +
                "id=" + id +
                ", templateCode='" + templateCode + '\'' +
                ", columnName='" + columnName + '\'' +
                ", columnAlias='" + columnAlias + '\'' +
                ", columnDatatype='" + columnDatatype + '\'' +
                ", columnSequence=" + columnSequence +
                ", columnSpecification='" + columnSpecification + '\'' +
                ", isMandatory='" + isMandatory + '\'' +
                '}';
    }
}
