package ua.javacoders.logic.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currency.Currency;
import ua.javacoders.logic.dto.CurrencyRateDto;
import ua.javacoders.logic.dto.MonoDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonoCurrencyRetrievalService implements CurrencyRetrievalService {
    private static final String URL = "https://api.monobank.ua/bank/currency";
    private static final Map<Integer, Currency> codeCurr = Map.of(
            Currency.USD.getCode(), Currency.USD,
            Currency.EUR.getCode(), Currency.EUR
    );

    @Override
    public List<CurrencyRateDto> getRate() {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<MonoDto> monoRates = convertResponseToList(response);
            return monoRates.stream()
                    .filter(item -> codeCurr.containsKey(item.getCurrencyCodeA())
                            && item.getCurrencyCodeB().equals(Currency.UAH.getCode()))
                    .map(item -> new CurrencyRateDto(
                            Bank.MONO,
                            codeCurr.get(item.getCurrencyCodeA()),
                            item.getRateBuy(),
                            item.getRateSell()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error get rates from Monobank API");
        }
        return new ArrayList<>();
    }

    private List<MonoDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, MonoDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
