package ua.javacoders.logic.bankdto;

import lombok.Data;
import ua.javacoders.logic.currencies.Currency;

import java.math.BigDecimal;

/**
 * The PUMBDto class represents the exchange rate object from the <a href="https://about.pumb.ua/info/currency_converter">PUMB</a>,
 * which contains information about currency codes and buy and sell rates.
 */
@Data
public class PUMBDto {
    /**
     * currency - Currency name
     * buyRate - The price at which the bank buys the currency
     * sellRate - The price at which the bank sells the currency
     */
    private Currency currency;
    private BigDecimal rateBuy;
    private BigDecimal rateSell;
}
