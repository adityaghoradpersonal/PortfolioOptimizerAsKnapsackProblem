package com.myportfolio.domain;

/**
 * Represents a single stock with all its pre-calculated data.
 * This is a pure data class (POJO) that mirrors a row in the sp500_cleaned.csv file.
 */
public class Stock {
    private final String name;
    private final double latestPrice;
    private final int priceInCents;
    private final double expectedReturnAnnual;
    private final double expectedValuePerShare;

    public Stock(String name, double latestPrice, int priceInCents, double expectedReturnAnnual, double expectedValuePerShare) {
        this.name = name;
        this.latestPrice = latestPrice;
        this.priceInCents = priceInCents;
        this.expectedReturnAnnual = expectedReturnAnnual;
        this.expectedValuePerShare = expectedValuePerShare;
    }

    // --- Getters for all fields ---

    public String getName() {
        return name;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    public double getExpectedReturnAnnual() {
        return expectedReturnAnnual;
    }

    public double getExpectedValuePerShare() {
        return expectedValuePerShare;
    }
}

