package com.code.file.upload.service;

import com.code.file.upload.entity.FileUploadDetailTemplate;
import com.code.file.upload.entity.FileUploadHeaderTemplate;
import com.code.file.upload.repository.FileUploadDetailTemplateRepository;
import com.code.file.upload.repository.FileUploadHeaderTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadTemplateServiceImpl implements FileUploadTemplateService {

    @Autowired
    private FileUploadHeaderTemplateRepository fileUploadHeaderTemplateRepository;

    @Autowired
    private FileUploadDetailTemplateRepository fileUploadDetailTemplateRepository;

    public String saveFileUploadHeaderTemplate(FileUploadHeaderTemplate fileUploadHeaderTemplate){

        System.out.println(fileUploadHeaderTemplate);
        Long id = fileUploadHeaderTemplateRepository.save(fileUploadHeaderTemplate).getId();

        return "Record has been saved successfully with request id :"+id;
    }


    public String saveFileUploadDetailTemplate(FileUploadDetailTemplate fileUploadDetailTemplate){

        System.out.println(fileUploadDetailTemplate);
        FileUploadDetailTemplate fileUploadDetailSaved =fileUploadDetailTemplateRepository.save(fileUploadDetailTemplate);

        return "Record has been saved successfully with request id :"+fileUploadDetailSaved;

    }


}
