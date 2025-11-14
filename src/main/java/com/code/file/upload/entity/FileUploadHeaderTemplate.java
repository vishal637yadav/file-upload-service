package com.code.file.upload.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class FileUploadHeaderTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myFileHdrSeqGen")
    @SequenceGenerator(name = "myFileHdrSeqGen", sequenceName = "FileHeaderTemplate_seq", allocationSize = 1)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String templateCode;

    @Column(length = 20, nullable = false)
    private String fileNamingPattern;

    @Column(length = 8)
    private String fileFormat;

    @Column(length = 4)
    private String recordDelimiter;

    // Copy('C') or Insert('I')
    @Column(length = 1)
    private String uploadType;

    @Column(length = 2000)
    private String fileHeaders;

    @Column(length = 1, nullable = true)
    private String fileContentValidation;

}
