package ua.javacoders.logic.currencyretrieval;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currencies.Currency;
import ua.javacoders.logic.bankdto.CurrencyRateDto;
import ua.javacoders.logic.bankdto.NBUDto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The NBUCurrencyRetrievalService class is responsible for retrieving exchange rates
 * from the National Bank of Ukraine (NBU) through their API.
 */
public class NBUCurrencyRetrievalService implements CurrencyRateRetriever {
    public static final Logger LOG = LogManager.getLogger(NBUCurrencyRetrievalService.class);
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    /**
     * Receives exchange rates from the NBU API and converts them to a list of CurrencyRateDto objects.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *      <li>Send a request to the NBU API and receive a JSON response</li>
     *      <li>Convert the JSON response to a list of NBUDto objects</li>
     *      <li>Filter the list of nbuRates by the required currencies and convert them to a list of CurrencyRateDto objects</li>
     *      <li>If an error occurs, it is logged</li>
     * </ul>
     *
     * @return a list of CurrencyRateDto objects with exchange rates
     */
    @Override
    public List<CurrencyRateDto> getCurrencyRates() {
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
            LOG.warn("Error get rates from NBU API");
        }
        return new ArrayList<>();
    }


    /**
     * Converts the JSON response from the NBU API to a list of NBUDto objects.
     *
     * @param response JSON string received from the NBU API
     * @return list of NBUDto objects
     */
    private List<NBUDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, NBUDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}
