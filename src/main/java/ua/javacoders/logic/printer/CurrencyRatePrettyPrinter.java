package ua.javacoders.logic.printer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currency.Currency;
import ua.javacoders.logic.dto.CurrencyRateDto;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyRatePrettyPrinter {

    public String printPrettyRates(List<Currency> currencies, List<Bank> banks) {
        List<CurrencyRateDto> requiredRates = getRequiredRates(currencies, banks);
        StringBuilder prettyRates = new StringBuilder();
        for (CurrencyRateDto requiredRate : requiredRates) {
            prettyRates.append("Банк: ")
                    .append(requiredRate.getBank().getUkrName()).append("\n")
                    .append("Валюта: ").append(requiredRate.getCurrency()).append("\n")
                    .append("Ціна купівлі: ").append(requiredRate.getBuyRate()).append("\n")
                    .append("Ціна продажу: ").append(requiredRate.getSellRate()).append("\n")
                    .append("\n");
        }
        return prettyRates.toString();
    }

    private List<CurrencyRateDto> getRequiredRates(List<Currency> currencies, List<Bank> banks) {
        Gson gson = new Gson();
        try {
            List<CurrencyRateDto> requiredRates = gson
                    .fromJson(new FileReader("currencyRates.json")
                            , new TypeToken<List<CurrencyRateDto>>() {
                            }.getType());
            return requiredRates.stream()
                    .filter(item -> banks.contains(item.getBank()))
                    .filter(item -> currencies.contains(item.getCurrency()))
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.err.println("Error in getRequiredRates");
        }
        return new ArrayList<>();
    }
}
