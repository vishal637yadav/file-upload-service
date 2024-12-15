package com.code.file.upload.utility;

import java.util.Random;

public abstract class AbstractRandomDataGenerator implements RandomDataGenerator {

    protected final Random random = new Random();
    protected final char[] charArray = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    protected int startIndex;
    protected int endIndex;
    protected int size;

    public AbstractRandomDataGenerator ofSize(int size) {
        this.size = size;
        return this;
    }

    @Override
    public String getData() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            sb.append(charArray[random.nextInt(startIndex, endIndex)]);
        }
        return sb.toString();
    }

}
