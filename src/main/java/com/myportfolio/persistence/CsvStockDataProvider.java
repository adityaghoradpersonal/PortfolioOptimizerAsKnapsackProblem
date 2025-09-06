package com.myportfolio.persistence;

import com.myportfolio.domain.Stock;
import com.myportfolio.service.StockDataProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of StockDataProvider that reads stock data from a CSV file
 * located in the project's resources.
 */
public class CsvStockDataProvider implements StockDataProvider {

    private static final String CSV_FILE_PATH = "/sp500_cleaned.csv"; // Note the leading slash

    @Override
    public List<Stock> getStocks() throws IOException {
        List<Stock> stocks = new ArrayList<>();

        // This is the standard way to read a file from the 'resources' folder
        try (InputStream is = getClass().getResourceAsStream(CSV_FILE_PATH);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {

            if (is == null) {
                throw new IOException("Cannot find resource file: " + CSV_FILE_PATH);
            }

            String line;
            // Skip the header row
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 5) {
                    try {
                        String name = columns[0];
                        double latestPrice = Double.parseDouble(columns[1]);
                        int priceInCents = Integer.parseInt(columns[2]);
                        double expectedReturnAnnual = Double.parseDouble(columns[3]);
                        double expectedValuePerShare = Double.parseDouble(columns[4]);

                        stocks.add(new Stock(name, latestPrice, priceInCents, expectedReturnAnnual, expectedValuePerShare));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed row: " + line);
                    }
                }
            }
        }

        return stocks;
    }
}