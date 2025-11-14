package com.code.file.upload.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityServiceImpl implements UtilityService {

    private static final Log logger = LogFactory.getLog(UtilityServiceImpl.class);

    @Override
    public String integerListToCharacterStringConversion(List<Integer> integerList) {
        StringBuilder sb = new StringBuilder();

        for (Integer number : integerList) {
            if (number < 160) {
                char curChar = (char) number.intValue();
                sb.append(curChar);
            }
        }
        logger.info("----->>::" + sb.toString());
        return sb.toString();
    }

}
