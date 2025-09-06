package com.myportfolio.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The core algorithm engine. Solves the Bounded Knapsack Problem using Dynamic Programming.
 * This class is pure logic and has no knowledge of UI or data sources.
 */
public class PortfolioOptimizer {

    public List<PortfolioItem> solve(List<Stock> stocks, int budgetInCents, Map<Stock, Integer> bounds) {
        int numStocks = stocks.size();

        // DP table: dp[i][w] = max value for first 'i' stocks with budget 'w' cents.
        double[][] dp = new double[numStocks + 1][budgetInCents + 1];

        // --- PART 1: Fill the DP table ---
        for (int i = 1; i <= numStocks; i++) {
            Stock currentStock = stocks.get(i - 1);
            int priceInCents = currentStock.getPriceInCents();
            double valuePerShare = currentStock.getExpectedValuePerShare();
            int maxShares = bounds.get(currentStock); // Use the calculated bound

            for (int w = 0; w <= budgetInCents; w++) {
                // Option 1: Don't take the current stock at all.
                dp[i][w] = dp[i - 1][w];

                // Option 2: Try taking k shares of the current stock.
                for (int k = 1; k <= maxShares; k++) {
                    if (w >= k * priceInCents) {
                        dp[i][w] = Math.max(
                                dp[i][w],
                                (k * valuePerShare) + dp[i - 1][w - (k * priceInCents)]
                        );
                    } else {
                        break; // Cannot afford more shares.
                    }
                }
            }
        }

        // --- PART 2: Backtrack to find which stocks and shares were chosen ---
        List<PortfolioItem> optimalPortfolio = new ArrayList<>();
        int w = budgetInCents;
        for (int i = numStocks; i > 0; i--) {
            Stock currentStock = stocks.get(i - 1);
            int priceInCents = currentStock.getPriceInCents();
            double valuePerShare = currentStock.getExpectedValuePerShare();
            int maxShares = bounds.get(currentStock);

            // Find how many shares (k) of this stock were included.
            for (int k = maxShares; k >= 0; k--) {
                if (w >= k * priceInCents) {
                    double valueFromThisChoice = (k * valuePerShare) + dp[i - 1][w - (k * priceInCents)];
                    // If this choice matches the optimal value, it was the one we made.
                    if (dp[i][w] == valueFromThisChoice) {
                        if (k > 0) {
                            optimalPortfolio.add(new PortfolioItem(currentStock, k));
                        }
                        w -= (k * priceInCents); // Reduce budget and move to the next stock.
                        break;
                    }
                }
            }
        }
        return optimalPortfolio;
    }
}
