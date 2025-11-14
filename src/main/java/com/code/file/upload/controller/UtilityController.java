package com.code.file.upload.controller;

import com.code.file.upload.service.UtilityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utility")
public class UtilityController {

    private static final Log logger = LogFactory.getLog(UtilityController.class);

    private UtilityService utilityService;

    public UtilityController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @PostMapping("/asciiNoToCharacterStringConversion")
    public String asciiNoToCharacterStringConversion(@RequestBody List<Integer> integerList){
        logger.info("---::asciiNoToCharacterStringConversion called");
        String createdString = utilityService.integerListToCharacterStringConversion(integerList);
        logger.info("createdString---::"+createdString);
        return createdString;
    }

}
