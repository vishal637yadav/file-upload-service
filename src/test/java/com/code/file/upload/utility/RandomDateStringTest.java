package com.code.file.upload.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RandomDataGenerator.class)
class RandomDateStringTest {

    RandomDateString randomDateString ;

    @BeforeEach
    void setUp() {
        randomDateString = new RandomDateString(); // Assuming your class name
    }

    @Test
    @DisplayName("Should return date within last 50 days")
    void testGetDataReturnsDateWithinRange() {
        // Given
        LocalDate today = LocalDate.now();
        LocalDate fiftyDaysAgo = today.minusDays(50);

        // When
        String result = randomDateString.getData();
        LocalDate resultDate = LocalDate.parse(result);

        // Then
        assertTrue(resultDate.isAfter(fiftyDaysAgo.minusDays(1)) || resultDate.isEqual(fiftyDaysAgo),
                "Date should be after or equal to 50 days ago");
        assertTrue(resultDate.isBefore(today.plusDays(1)) || resultDate.isEqual(today),
                "Date should be before or equal to today");
    }
}