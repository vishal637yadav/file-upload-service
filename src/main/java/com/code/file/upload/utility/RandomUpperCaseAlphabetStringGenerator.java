package com.code.file.upload.utility;

import org.springframework.stereotype.Component;

@Component
public class RandomUpperCaseAlphabetStringGenerator extends AbstractRandomDataGenerator {

    public RandomUpperCaseAlphabetStringGenerator() {
        this.startIndex = 36;
        this.endIndex = charArray.length - 1;
    }

    @Override
    public String toString() {
        return "RandomUpperCaseAlphabetStringGenerator{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", size=" + size +
                '}';
    }

}
