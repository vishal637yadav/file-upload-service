package com.code.file.upload.controller;

import com.code.file.upload.entity.FileUploadDetailTemplate;
import com.code.file.upload.entity.FileUploadHeaderTemplate;
import com.code.file.upload.service.FileUploadTemplateService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/fileUploadTemplate")
@RestController
public class FileUploadTemplateController {

    private static final Log logger = LogFactory.getLog(FileUploadTemplateController.class);

    private final FileUploadTemplateService fileUploadTemplateService;

    @Autowired
    public FileUploadTemplateController(FileUploadTemplateService fileUploadTemplateService) {
        this.fileUploadTemplateService = fileUploadTemplateService;
    }

    @PostMapping("/header")
    public ResponseEntity<String> uploadFileHeaderTemplate(@RequestBody FileUploadHeaderTemplate fileUploadHeaderTemplate) {
        logger.info("----::>>--::--<<::----FileUploadTemplateController.uploadFileHeaderTemplate Called!!");

        if (logger.isDebugEnabled()) {
            logger.debug("Loading source " +this);
        }
        String status = fileUploadTemplateService.saveFileUploadHeaderTemplate(fileUploadHeaderTemplate);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/detail")
    public ResponseEntity<String> uploadFileDetailsTemplate(@RequestBody FileUploadDetailTemplate fileUploadDetailTemplate) {
        logger.info("----::>>--::--<<::----FileUploadTemplateController.uploadFileDetailsTemplate Called!!");

        if (logger.isDebugEnabled()) {
            logger.debug("Loading source " +this);
        }
        String status = fileUploadTemplateService.saveFileUploadDetailTemplate(fileUploadDetailTemplate);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

}
