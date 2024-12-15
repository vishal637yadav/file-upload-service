package com.code.file.upload.utility;

import org.springframework.stereotype.Component;

@Component
public class RandomAlphaNumericStringGenerator extends AbstractRandomDataGenerator {

    public RandomAlphaNumericStringGenerator() {
        this.startIndex = 0;
        this.endIndex = charArray.length - 1;
    }

    @Override
    public String toString() {
        return "RandomAlphaNumericStringGenerator{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", size=" + size +
                '}';
    }

}
