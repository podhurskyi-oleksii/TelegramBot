package ua.javacoders.logic.bankdto;

import lombok.Data;
import lombok.AllArgsConstructor;
import ua.javacoders.logic.currencies.Currency;
import ua.javacoders.logic.banks.Bank;

import java.math.BigDecimal;

/**
 * The CurrencyRateDto class represents a general object that contains information
 * about the bank, currencies, and the buying and selling rate of these currencies.
 */
@Data
@AllArgsConstructor
public class CurrencyRateDto {
    /**
     * bank - Bank name
     * currency - Currency name
     * buyRate - The price at which the bank buys the currency
     * sellRate - The price at which the bank sells the currency
     */
    private Bank bank;
    private Currency currency;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
}
