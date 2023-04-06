package ua.javacoders.logic.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.javacoders.logic.dto.CurrencyRateDto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRateSaver {
    List<CurrencyRetrievalService> retrievalServices = List.of(
            new MonoCurrencyRetrievalService(),
            new PrivatCurrencyRetrievalService(),
            new NBUCurrencyRetrievalService(),
            new OschadCurrencyRetrievalService(),
            new PUMBCurrencyRetrievalService()
    );

    public void saveRatesToFile() {
        List<CurrencyRateDto> rateList = new ArrayList<>();
        for (CurrencyRetrievalService service : retrievalServices) {
            rateList.addAll(service.getRate());
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter writer = new FileWriter("currencyRates.json");
            writer.write(gson.toJson(rateList));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
