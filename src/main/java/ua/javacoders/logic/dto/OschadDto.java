package ua.javacoders.logic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.javacoders.logic.currency.Currency;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OschadDto {
    private Currency currency;
    private BigDecimal rateBuy;
    private BigDecimal rateSell;
}
