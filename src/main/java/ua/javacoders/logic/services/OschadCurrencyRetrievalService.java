package ua.javacoders.logic.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currency.Currency;
import ua.javacoders.logic.dto.CurrencyRateDto;
import ua.javacoders.logic.dto.OschadDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OschadCurrencyRetrievalService implements CurrencyRetrievalService {
    private static final String URL = "https://www.oschadbank.ua/currency-rate";

    @Override
    public List<CurrencyRateDto> getRate() {
        try {
            Document document = Jsoup.connect(URL).get();
            Element currencyRateTable = document.select("tbody").get(1);
            Elements tableRows = currencyRateTable.select("tr");

            return tableRows.stream()
                    .filter(element -> element.select("td:nth-child(2)").text().equals("USD")
                            || element.select("td:nth-child(2)").text().equals("EUR"))
                    .map(item -> {
                        OschadDto dto = new OschadDto();
                        dto.setCurrency(Currency.valueOf(item.select("td:nth-child(2)").text()));
                        dto.setRateBuy(new BigDecimal(
                                item.select("td:nth-child(4)").text().replaceFirst(",", ".")));
                        dto.setRateSell(new BigDecimal(
                                item.select("td:nth-child(5)").text().replaceFirst(",", ".")));
                        return dto;
                    })
                    .map(item -> new CurrencyRateDto(Bank.OSCHAD, item.getCurrency(), item.getRateBuy(), item.getRateSell()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error get rates from OschadBank.ua");
        }
        return null;
    }
}
