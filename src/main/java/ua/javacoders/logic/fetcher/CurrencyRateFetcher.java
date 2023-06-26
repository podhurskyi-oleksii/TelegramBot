package ua.javacoders.logic.fetcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.javacoders.logic.bankdto.CurrencyRateDto;
import ua.javacoders.logic.currencyretrieval.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.javacoders.utils.FilesPath.CURRENCIES_RATES;

/**
 * The CurrencyRateFetcher class is responsible for fetching and saving currency rates
 * from multiple sources. It uses different implementations of CurrencyRateRetriever to fetch
 * currency rates from different banks and then saves the fetched rates to a JSON file.
 */
public class CurrencyRateFetcher {
    public static final Logger LOG = LogManager.getLogger(CurrencyRateFetcher.class);

    // List of CurrencyRateRetriever services to fetch rates from different banks
    List<CurrencyRateRetriever> retrievalServices = List.of(
            new MonoCurrencyRetrievalService(),
            new PrivatCurrencyRetrievalService(),
            new NBUCurrencyRetrievalService(),
            new OschadCurrencyRetrievalService(),
            new PUMBCurrencyRetrievalService()
    );

    /**
     * Fetches the currency rates from multiple sources using the CurrencyRateRetrievers
     * and saves the fetched rates to a JSON file.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Iterate through the list of CurrencyRateRetrievers and fetch the currency rates</li>
     *     <li>Combine the fetched currency rates into a single list</li>
     *     <li>Convert the combined list of currency rates into a JSON string</li>
     *     <li>Save the JSON string to a file</li>
     *     <li>If an error occurs, it is logged</li>
     * </ul>
     */
    public void saveRatesToFile() {
        List<CurrencyRateDto> rateList = new ArrayList<>();
        for (CurrencyRateRetriever service : retrievalServices) {
            rateList.addAll(service.getCurrencyRates());
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter writer = new FileWriter(CURRENCIES_RATES.getFilePath());
            writer.write(gson.toJson(rateList));
            writer.close();
        } catch (IOException e) {
            LOG.warn("Error occurred during file operations (open, write, or close): ", e);
        }
    }
}
