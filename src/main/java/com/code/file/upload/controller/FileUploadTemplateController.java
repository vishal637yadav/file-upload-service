package com.code.file.upload.controller;

import com.code.file.upload.entity.FileUploadDetailTemplate;
import com.code.file.upload.entity.FileUploadHeaderTemplate;
import com.code.file.upload.service.FileUploadTemplateService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/fileUploadTemplate")
@RestController
public class FileUploadTemplateController {

    private static final Log logger = LogFactory.getLog(FileUploadTemplateController.class);

    private final FileUploadTemplateService fileUploadTemplateService;

    public FileUploadTemplateController(FileUploadTemplateService fileUploadTemplateService) {
        this.fileUploadTemplateService = fileUploadTemplateService;
    }

    @PostMapping("/header")
    public ResponseEntity<String> uploadFileHeaderTemplate(@RequestBody FileUploadHeaderTemplate fileUploadHeaderTemplate) {
        logger.info("----::>>--::--<<::----FileUploadTemplateController.uploadFileHeaderTemplate Called!!");

        if (logger.isDebugEnabled()) {
            logger.debug("Loading source " + this);
        }
        String status = fileUploadTemplateService.saveFileUploadHeaderTemplate(fileUploadHeaderTemplate);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("/header")
    public ResponseEntity<List<FileUploadHeaderTemplate>> getTemplateHeaders() {
        List<FileUploadHeaderTemplate> headerTemplateList = fileUploadTemplateService.getAllFileUploadTemplates();
        if (logger.isDebugEnabled())
            logger.debug(headerTemplateList);
        return new ResponseEntity<>(headerTemplateList, HttpStatus.OK);
    }

    @GetMapping("/header/{templateCode}")
    public ResponseEntity<FileUploadHeaderTemplate> getTemplateHeadersByTemplateCode(@PathVariable String templateCode) {
        FileUploadHeaderTemplate headerTemplate = fileUploadTemplateService.getFileUploadTemplateHeaderByTemplateCode(templateCode);
        if (logger.isDebugEnabled())
            logger.debug(headerTemplate);
        return new ResponseEntity<>(headerTemplate, HttpStatus.OK);
    }

    @DeleteMapping("/header/{templateCode}")
    public ResponseEntity<String> deleteTemplateHeadersByTemplateCode(@PathVariable String templateCode) {

        boolean updated = fileUploadTemplateService.deleteTemplateHeadersByTemplateCode(templateCode);
        if (updated)
            return new ResponseEntity<>("TemplateHeaders have templateCode='"+templateCode+"' Deleted Successfully!", HttpStatus.OK);
        return new ResponseEntity<>("Input Job Id Not Found in the System", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/detail")
    public ResponseEntity<String> uploadFileDetailsTemplate(@RequestBody FileUploadDetailTemplate fileUploadDetailTemplate) {
        logger.info("----::>>--::--<<::----FileUploadTemplateController.uploadFileDetailsTemplate Called!!");

        if (logger.isDebugEnabled()) {
            logger.debug("Loading source " + this);
        }
        String status = fileUploadTemplateService.saveFileUploadDetailTemplate(fileUploadDetailTemplate);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

}
