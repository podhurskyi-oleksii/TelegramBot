package ua.javacoders.logic.currencyretrieval;

import ua.javacoders.logic.bankdto.CurrencyRateDto;

import java.util.List;

/**
 * The CurrencyRateRetriever interface defines how to retrieve exchange rates from various sources.
 * Implementations of this interface can retrieve rates from web services, files, or other sources.
 */

public interface CurrencyRateRetriever {
    /**
     * Gets a list of exchange rates for different banks.
     * The result is a list of CurrencyRateDto objects that contain information about the bank, currency,
     * buy and sell rates.
     *
     * @return a list of CurrencyRateDto objects with exchange rates
     */
    List<CurrencyRateDto> getCurrencyRates();
}