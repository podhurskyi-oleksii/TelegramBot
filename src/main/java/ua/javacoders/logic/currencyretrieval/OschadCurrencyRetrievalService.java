package ua.javacoders.logic.currencyretrieval;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currencies.Currency;
import ua.javacoders.logic.bankdto.CurrencyRateDto;
import ua.javacoders.logic.bankdto.OschadDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The OschadCurrencyRetrievalService class is responsible for retrieving exchange rates
 * from Oschadbank via their <a href="https://www.oschadbank.ua/currency-rate">website</a>.
 */
public class OschadCurrencyRetrievalService implements CurrencyRateRetriever {
    public static final Logger LOG = LogManager.getLogger(OschadCurrencyRetrievalService.class);
    private static final String URL = "https://www.oschadbank.ua/currency-rate";

    /**
     * Gets exchange rates from the Oschadbank web page and converts them to a list of CurrencyRateDto objects.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Loading a web page with exchange rates using Jsoup</li>
     *     <li>Select the table with exchange rates on the page (we need the second one, because there are rates for cards)</li>
     *     <li>Select all rows of the table</li>
     *     <li>Filter the table rows by the required currencies, convert them to OschadDto,
     *     and then to CurrencyRateDto, and collect them in a list</li>
     *     <li>If an error occurs, it is logged</li>
     * </ul>
     *
     * @return list of CurrencyRateDto objects with exchange rates
     */
    @Override
    public List<CurrencyRateDto> getCurrencyRates() {
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
            LOG.warn("Error get rates from Oschadbank website");
        }
        return new ArrayList<>();
    }
}
