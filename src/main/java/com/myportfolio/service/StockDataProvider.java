package com.myportfolio.service;

import com.myportfolio.domain.Stock;
import java.io.IOException;
import java.util.List;

/**
 * An interface that defines the contract for any class that provides stock data.
 * This decouples our application from the specific data source (e.g., CSV, database, API).
 */
public interface StockDataProvider {
    List<Stock> getStocks() throws IOException;
}