package com.code.file.upload.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/meta-data")
@RestController
public class MetaDataConfigurationController {

    @GetMapping("/test")
    public String testApi(){
        return "testApi";
    }

    @PostMapping("/save-data")
    public String save(){
        return "save-api..";
    }


}
