package com.code.file.upload.service;

import com.code.file.upload.entity.FileUploadDetailTemplate;
import com.code.file.upload.entity.FileUploadHeaderTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface FileUploadTemplateService {

    public String saveFileUploadHeaderTemplate(FileUploadHeaderTemplate fileUploadHeaderTemplate);
    public String saveFileUploadDetailTemplate(FileUploadDetailTemplate fileUploadDetailTemplate);


}
