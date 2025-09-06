package com.myportfolio.service;

import com.myportfolio.domain.PortfolioItem;
import com.myportfolio.domain.PortfolioOptimizer;
import com.myportfolio.domain.Stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The service layer that orchestrates the main business logic.
 * It coordinates data fetching and the optimization algorithm.
 */
public class PortfolioService {

    private final StockDataProvider stockDataProvider;
    private final PortfolioOptimizer optimizer;

    // We use "Dependency Injection" here: the service depends on abstractions (interfaces),
    // not on concrete classes. This makes our code flexible and testable.
    public PortfolioService(StockDataProvider stockDataProvider) {
        this.stockDataProvider = stockDataProvider;
        this.optimizer = new PortfolioOptimizer(); // Instantiate the algorithm engine
    }

    /**
     * The main method that the UI will call. It takes user inputs, applies business rules,
     * and returns the final portfolio.
     */
    public List<PortfolioItem> calculateOptimalPortfolio(double budget, double diversificationPercent, List<Stock> availableStocks) {
        if (budget <= 0 || diversificationPercent <= 0) {
            return List.of(); // Return an empty list if inputs are invalid
        }

        // --- Business Logic: Apply the diversification constraint ---
        Map<Stock, Integer> bounds = new HashMap<>();
        for (Stock stock : availableStocks) {
            // Calculate the max number of shares based on the diversification rule
            double maxInvestmentPerStock = budget * (diversificationPercent / 100.0);
            int maxShares = (int) Math.floor(maxInvestmentPerStock / stock.getLatestPrice());
            bounds.put(stock, maxShares);
        }

        int budgetInCents = (int) (budget * 100);

        // --- Delegate to the optimizer ---
        return optimizer.solve(availableStocks, budgetInCents, bounds);
    }

    /**
     * Gets the initial list of stocks from the data provider.
     */
    public List<Stock> getAllStocks() throws Exception {
        return stockDataProvider.getStocks();
    }
}
