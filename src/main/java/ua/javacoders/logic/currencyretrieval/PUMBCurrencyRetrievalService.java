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
import ua.javacoders.logic.bankdto.PUMBDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The PUMBCurrencyRetrievalService class is responsible for retrieving exchange rates
 * from PUMB Bank via their <a href="https://about.pumb.ua/info/currency_converter">website</a>.
 */
public class PUMBCurrencyRetrievalService implements CurrencyRateRetriever {
    public static final Logger LOG = LogManager.getLogger(PUMBCurrencyRetrievalService.class);
    private static final String URL = "https://about.pumb.ua/info/currency_converter";

    /**
     * Gets exchange rates from the PUMB Bank web page and converts them to a list of CurrencyRateDto objects.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Connect to the PUMB Bank URL using Jsoup</li>
     *     <li>Select the table with exchange rates</li>
     *     <li>Select all rows of the table</li>
     *     <li>Filter the table rows by the required currencies, convert them to PUMBDto,
     *     and then to CurrencyRateDto, and collect them in a list</li>
     *     <li>If an error occurs, it is logged</li>
     * </ul>
     *
     * @return a list of CurrencyRateDto objects with exchange rates
     */
    @Override
    public List<CurrencyRateDto> getCurrencyRates() {
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
            LOG.warn("Error get rates from PUMB website");
        }
        return new ArrayList<>();
    }
}
