package com.myportfolio.domain;

/**
 * Represents a single item in the final, optimized portfolio result.
 * It connects a Stock object with the number of shares to be purchased.
 */
public class PortfolioItem {
    private final Stock stock;
    private final int shares;

    public PortfolioItem(Stock stock, int shares) {
        this.stock = stock;
        this.shares = shares;
    }

    // --- Getters ---

    public Stock getStock() {
        return stock;
    }

    public int getShares() {
        return shares;
    }
}

