package ua.javacoders.logic.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ua.javacoders.logic.currency.Currency;
import ua.javacoders.logic.banks.Bank;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateDto {
    private Bank bank;
    private Currency currency;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
}
