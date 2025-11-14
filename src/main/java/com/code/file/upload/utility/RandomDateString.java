package com.code.file.upload.utility;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateString implements RandomDataGenerator{

    /**
     * Generate random date between two dates
     */
    private LocalDate randomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomDay);
    }

    /**
     * Generate random date within last N days
     */
    private LocalDate randomDateWithinDays(int days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);
        return randomDate(startDate, endDate);
    }


    @Override
    public String getData() {
        //Random Date within last Fifty Days
        LocalDate date = randomDateWithinDays(50);
        return date.toString();
    }
}
