package ua.javacoders.logic.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currency.Currency;
import ua.javacoders.logic.dto.CurrencyRateDto;
import ua.javacoders.logic.dto.NBUDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NBUCurrencyRetrievalService implements CurrencyRetrievalService {
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Override
    public List<CurrencyRateDto> getRate() {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<NBUDto> nbuRates = convertResponseToList(response);
            return nbuRates.stream()
                    .filter(item -> item.getCc() == Currency.EUR || item.getCc() == Currency.USD)
                    .map(item -> new CurrencyRateDto(
                            Bank.NBU,
                            item.getCc(),
                            item.getRate(),
                            item.getRate()
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error get rates from NBU API");
        }
        return new ArrayList<>();
    }


    private List<NBUDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, NBUDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
