package com.code.file.upload.service;

import com.code.file.upload.entity.FileUploadDetailTemplate;
import com.code.file.upload.entity.FileUploadHeaderTemplate;

import java.util.List;

public interface FileUploadTemplateService {

    String saveFileUploadHeaderTemplate(FileUploadHeaderTemplate fileUploadHeaderTemplate);
    String saveFileUploadDetailTemplate(FileUploadDetailTemplate fileUploadDetailTemplate);

    List<FileUploadHeaderTemplate> getAllFileUploadTemplates();

    FileUploadHeaderTemplate getFileUploadTemplateHeaderByTemplateCode(String templateCode);

    boolean deleteTemplateHeadersByTemplateCode(String templateCode);

}
