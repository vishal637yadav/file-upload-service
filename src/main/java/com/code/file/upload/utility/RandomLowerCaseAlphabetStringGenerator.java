package com.code.file.upload.utility;

import org.springframework.stereotype.Component;

@Component
public class RandomLowerCaseAlphabetStringGenerator extends AbstractRandomDataGenerator {

    public RandomLowerCaseAlphabetStringGenerator() {
        this.startIndex = 10;
        this.endIndex = 35;
    }

    @Override
    public String toString() {
        return "RandomLowerCaseAlphabetStringGenerator{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", size=" + size +
                '}';
    }

}
