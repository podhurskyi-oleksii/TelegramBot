package ua.javacoders.logic.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currency.Currency;
import ua.javacoders.logic.dto.CurrencyRateDto;
import ua.javacoders.logic.dto.PUMBDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PUMBCurrencyRetrievalService implements CurrencyRetrievalService {
    private static final String URL = "https://about.pumb.ua/info/currency_converter";

    @Override
    public List<CurrencyRateDto> getRate() {
        try {
            Document document = Jsoup.connect(URL).get();
            Element currencyRateTable = document.selectFirst("table");
            Elements tableRows = currencyRateTable.select("tr");

            return tableRows.stream()
                    .filter(element -> element.select("td:nth-child(1)").text().equals("USD")
                            || element.select("td:nth-child(1)").text().equals("EUR"))
                    .map(item -> {
                        PUMBDto pumb = new PUMBDto();
                        pumb.setCurrency(Currency.valueOf(item.select("td:nth-child(1)").text()));
                        pumb.setRateBuy(new BigDecimal(
                                item.select("td:nth-child(2)").text().replaceFirst(",", ".")));
                        pumb.setRateSell(new BigDecimal(
                                item.select("td:nth-child(3)").text().replaceFirst(",", ".")));
                        return pumb;
                    })
                    .map(item -> new CurrencyRateDto(Bank.PUMB, item.getCurrency(), item.getRateBuy(), item.getRateSell()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error get rates from PUMB.ua");
        }
        return new ArrayList<>();
    }
}
