package com.code.file.upload.service;

import com.code.file.upload.controller.FileUploadTemplateController;
import com.code.file.upload.entity.FileUploadDetailTemplate;
import com.code.file.upload.entity.FileUploadHeaderTemplate;
import com.code.file.upload.repository.FileUploadDetailTemplateRepository;
import com.code.file.upload.repository.FileUploadHeaderTemplateRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileUploadTemplateServiceImpl implements FileUploadTemplateService {

    private static final Log logger = LogFactory.getLog(FileUploadTemplateServiceImpl.class);

    @Autowired
    private FileUploadHeaderTemplateRepository fileUploadHeaderTemplateRepository;

    @Autowired
    private FileUploadDetailTemplateRepository fileUploadDetailTemplateRepository;

    public String saveFileUploadHeaderTemplate(FileUploadHeaderTemplate fileUploadHeaderTemplate){
        logger.info(fileUploadHeaderTemplate);
        FileUploadHeaderTemplate newFileUploadHeaderTemplate = fileUploadHeaderTemplateRepository.save(fileUploadHeaderTemplate);
        return "Record has been saved successfully with request id :"+newFileUploadHeaderTemplate+" .!";
    }


    public String saveFileUploadDetailTemplate(FileUploadDetailTemplate fileUploadDetailTemplate){
        logger.info(fileUploadDetailTemplate);
        FileUploadDetailTemplate fileUploadDetailSaved =fileUploadDetailTemplateRepository.save(fileUploadDetailTemplate);
        return "Record has been saved successfully with request id :"+fileUploadDetailSaved;
    }

    @Override
    public List<FileUploadHeaderTemplate> getAllFileUploadTemplates() {
        List<FileUploadHeaderTemplate> templateHeaderList = fileUploadHeaderTemplateRepository.findAll();
        return templateHeaderList;
    }

    @Override
    public FileUploadHeaderTemplate getFileUploadTemplateHeaderByTemplateCode(String templateCode) {
        return fileUploadHeaderTemplateRepository.findByTemplateCode(templateCode);
    }

    @Override
    @Transactional
    public boolean deleteTemplateHeadersByTemplateCode(String templateCode) {
        try {
            fileUploadHeaderTemplateRepository.deleteByTemplateCode(templateCode);
            logger.info("--Deleted successfully!!"+templateCode);
            return true;
        } catch (Exception e) {
            logger.error("--Delete failed for id successfully!!"+templateCode,e);
            return false;
        }
    }


}
