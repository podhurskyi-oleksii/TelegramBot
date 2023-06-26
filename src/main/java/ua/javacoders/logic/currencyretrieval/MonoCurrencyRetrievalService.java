package ua.javacoders.logic.currencyretrieval;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currencies.Currency;
import ua.javacoders.logic.bankdto.CurrencyRateDto;
import ua.javacoders.logic.bankdto.MonoDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The MonoCurrencyRetrievalService class is responsible for retrieving exchange rates
 * from MonoBank via their API.
 */
public class MonoCurrencyRetrievalService implements CurrencyRateRetriever {
    public static final Logger LOG = LogManager.getLogger(MonoCurrencyRetrievalService.class);
    private static final String URL = "https://api.monobank.ua/bank/currency";
    private static final Map<Integer, Currency> codeCurr = Map.of(
            Currency.USD.getCode(), Currency.USD,
            Currency.EUR.getCode(), Currency.EUR
    );

    /**
     * Receives exchange rates from the MonoBank API and converts them to a list of CurrencyRateDto objects.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *      <li>Send a request to the MonoBank API and receive a JSON response</li>
     *      <li>Convert the JSON response to a list of MonoDto objects</li>
     *      <li>Filter the list of monoRates by the required currencies and convert them to a list of CurrencyRateDto objects</li>
     *      <li>If an error occurs, it is logged</li>
     * </ul>
     *
     * @return a list of CurrencyRateDto objects with exchange rates
     */
    @Override
    public List<CurrencyRateDto> getCurrencyRates() {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<MonoDto> monoRates = convertResponseToList(response);
            // Filter the list of monoRates by the required currencies and convert them to a list of CurrencyRateDto objects
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
            LOG.warn("Error get rates from Monobank API");
        }
        return new ArrayList<>();
    }

    /**
     * Converts the JSON response from the MonoBank API to a list of MonoDto objects.
     *
     * @param response JSON string received from the MonoBank API
     * @return list of MonoDto objects
     */
    private List<MonoDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, MonoDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
