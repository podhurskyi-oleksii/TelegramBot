package ua.javacoders.logic.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currency.Currency;
import ua.javacoders.logic.dto.CurrencyRateDto;
import ua.javacoders.logic.dto.PrivatDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PrivatCurrencyRetrievalService implements CurrencyRetrievalService {

    private static final String URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";

    @Override
    public List<CurrencyRateDto> getRate() {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<PrivatDto> privatRates = convertResponseToList(response);
            return privatRates.stream()
                    .filter(item -> item.getCcy() == Currency.EUR || item.getCcy() == Currency.USD)
                    .map(item -> new CurrencyRateDto(
                            Bank.PRIVAT,
                            item.getCcy(),
                            item.getBuy(),
                            item.getSale()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error get rates from Privatbank API");
        }
        return new ArrayList<>();
    }

    private List<PrivatDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, PrivatDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
