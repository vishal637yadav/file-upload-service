package com.code.file.upload.controller;

import com.code.file.upload.entity.FileUploadHeaderTemplate;
import com.code.file.upload.service.UtilityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/utility")
public class UtilityController {

    private UtilityService utilityService;

    public UtilityController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @PostMapping("/asciiNoToCharacterStringConversion")
    public String asciiNoToCharacterStringConversion(@RequestBody List<Integer> integerList){
        log.info("---::asciiNoToCharacterStringConversion called");
        String createdString = utilityService.integerListToCharacterStringConversion(integerList);
        log.info("createdString---::"+createdString);
        return createdString;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getTemplateHeaders() {
        log.info("---::getTemplateHeaders called----");
        return new ResponseEntity<>("Hello World UtilityController!!", HttpStatus.OK);
    }

    @GetMapping("/welcome/{name}")
    public ResponseEntity<String> welcome(@PathVariable String name) {
        log.info("---::getTemplateHeaders called----");
        return new ResponseEntity<>("Hi "+name+", Welcome the the UtilityController!!", HttpStatus.OK);
    }
}
