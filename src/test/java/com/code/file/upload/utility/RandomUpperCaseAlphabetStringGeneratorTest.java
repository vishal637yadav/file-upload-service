package com.code.file.upload.utility;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class RandomUpperCaseAlphabetStringGeneratorTest {

    @Autowired
    RandomUpperCaseAlphabetStringGenerator upperCaseAlphabetStringGenerator;

    @Test
    void basicTest() {
        System.out.println("--basicTest--");
        System.out.println(upperCaseAlphabetStringGenerator);

        String testString = upperCaseAlphabetStringGenerator.ofSize(20).getData();
        System.out.println("testString::-->>" + testString);

    }

    boolean isValidAlphaNumericCheck1(String testString) {
        String REGEX = "^[a-zA-Z0-9]*$";
        return testString.matches(REGEX);
    }

    boolean isValidAlphaNumericCheck2(String testString) {
        String REGEX = "^[a-zA-Z0-9]*$";
        Pattern PATTERN = Pattern.compile(REGEX);
        Matcher matcher = PATTERN.matcher(testString);
        return matcher.matches();
    }

    boolean isValidAlphaNumericCheck3(String testString) {
        boolean result = true;
        for (int i = 0; i < testString.length(); ++i) {
            int codePoint = testString.codePointAt(i);
            if (!isAlphanumeric(codePoint)) {
                result = false;
                break;
            }
        }
        return result;
    }

    boolean isAlphanumeric(final int codePoint) {
        return (codePoint >= 65 && codePoint <= 90) ||
                (codePoint >= 97 && codePoint <= 122) ||
                (codePoint >= 48 && codePoint <= 57);
    }

    boolean isValidAlphaNumericCheck4(String testString) {
        boolean result = true;
        for (int i = 0; i < testString.length(); ++i) {
            final int codePoint = testString.codePointAt(i);
            if (!Character.isAlphabetic(codePoint) || !Character.isDigit(codePoint)) {
                result = false;
                break;
            }
        }
        return result;
    }

}
