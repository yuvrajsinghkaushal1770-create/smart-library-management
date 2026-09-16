package com.library.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator implements Runnable {

    private static final double FINE_PER_DAY = 5.0;

    private final LocalDate dueDate;
    private final LocalDate returnDate;
    private double fine;

    public FineCalculator(LocalDate dueDate, LocalDate returnDate) {
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    @Override
    public void run() {
        this.fine = calculate();
    }

    public double calculate() {
        if (returnDate == null || !returnDate.isAfter(dueDate)) {
            return 0.0;
        }
        long lateDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        return lateDays * FINE_PER_DAY;
    }

    public double getFine() {
        return fine;
    }
}