package com.code.file.upload.utility;

import org.springframework.stereotype.Component;

@Component
public class RandomNumericStringGenerator extends AbstractRandomDataGenerator {

    public RandomNumericStringGenerator() {
        this.startIndex = 0;
        this.endIndex = 9;
    }

    @Override
    public String toString() {
        return "RandomNumericStringGenerator{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", size=" + size +
                '}';
    }

}
