package ua.javacoders.logic.currencyretrieval;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currencies.Currency;
import ua.javacoders.logic.bankdto.CurrencyRateDto;
import ua.javacoders.logic.bankdto.PrivatDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The PrivatCurrencyRetrievalService class is responsible for retrieving exchange rates
 * from Privatbank via their API.
 */
public class PrivatCurrencyRetrievalService implements CurrencyRateRetriever {

    public static final Logger LOG = LogManager.getLogger(PrivatCurrencyRetrievalService.class);
    private static final String URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";

    /**
     * Receives exchange rates from the Privatbank API and converts them to a list of CurrencyRateDto objects.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *      <li>Send a request to the Privatbank API and receive a JSON response</li>
     *      <li>Convert the JSON response to a list of PrivatDto objects</li>
     *      <li>Filter the list of privatRates by the required currencies and convert them to a list of CurrencyRateDto objects</li>
     *      <li>If an error occurs, it is logged</li>
     * </ul>
     *
     * @return a list of CurrencyRateDto objects with exchange rates
     */
    @Override
    public List<CurrencyRateDto> getCurrencyRates() {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<PrivatDto> privatRates = convertResponseToList(response);
            return privatRates.stream()
                    .filter(item -> item.getCcy() == Currency.USD || item.getCcy() == Currency.EUR)
                    .map(item -> new CurrencyRateDto(
                            Bank.PRIVAT,
                            item.getCcy(),
                            item.getBuy(),
                            item.getSale()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOG.warn("Error get rates from Privatbank API");
        }
        return new ArrayList<>();
    }

    /**
     * Converts the JSON response from the Privatbank API to a list of PrivatDto objects.
     *
     * @param response JSON string received from the Privatbank API
     * @return list of PrivatDto objects
     */
    private List<PrivatDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, PrivatDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
