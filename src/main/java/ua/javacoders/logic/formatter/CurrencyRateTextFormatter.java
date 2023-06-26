package ua.javacoders.logic.formatter;

import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currencies.Currency;
import ua.javacoders.logic.bankdto.CurrencyRateDto;
import ua.javacoders.logic.loader.CurrencyRateLoader;
import ua.javacoders.utils.Emoji;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * The CurrencyRateFormatter class is responsible for formatting the output information about exchange rates in text format.
 * It receives exchange rate data from a JSON file and converts it into a text format suitable for displaying in Telegram.
 */
public class CurrencyRateTextFormatter {

    /**
     * Formats exchange rates in text format for display in Telegram.
     * The method iterates through each bank selected by the user.
     * Depending on the currencies selected by the user, it retrieves the corresponding buying and selling rates.
     * Once retrieved, it generates a text message according to the selected format.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Load the required currency rates using the CurrencyRateLoader</li>
     *     <li>Create a StringJoiner to store the formatted rates</li>
     *     <li>Iterate through the selected banks and retrieve the corresponding currency rates</li>
     *     <li>Format the retrieved currency rates as per the desired output format</li>
     *     <li>Add the formatted rates to the StringJoiner</li>
     * </ul>
     *
     * @param currencies list of currencies for which the exchange rates need to be retrieved
     * @param banks      list of banks for which the exchange rates need to be retrieved
     * @return formatted string with exchange rates for each bank and currency
     */
    public String formatCurrencyRates(List<Currency> currencies, List<Bank> banks) {
        CurrencyRateLoader loader = new CurrencyRateLoader();
        List<CurrencyRateDto> requiredRates = loader.getRequiredRates(currencies, banks);
        StringJoiner formattedRate = new StringJoiner("\n");

        for (Bank bank : banks) {
            List<CurrencyRateDto> bankRates = requiredRates.stream()
                    .filter(rate -> rate.getBank().equals(bank))
                    .collect(Collectors.toList());

            if (!bankRates.isEmpty()) {
                CurrencyRateDto usdRate = currencies.contains(Currency.USD)
                        ? bankRates.stream()
                        .filter(rate -> rate.getCurrency() == Currency.USD)
                        .findFirst()
                        .orElse(null)
                        : null;
                CurrencyRateDto eurRate = currencies.contains(Currency.EUR)
                        ? bankRates.stream()
                        .filter(rate -> rate.getCurrency() == Currency.EUR)
                        .findFirst()
                        .orElse(null)
                        : null;

                // The formatting parameters were chosen by eye
                // One of the ways is to create a method for formatting
                // (determining the optimal value of "spaces" before and after the bank name, in this case it is better to rework enum Bank)
                formattedRate.add(String.format("%s <b>%s</b> %s", Emoji.BANK, bank.getUkrName(), Emoji.BANK));
                formattedRate.add(String.format("<b>%5s</b> %s <b>%11s</b>", "", "BUY↓", "SELL↑"));

                if (usdRate != null) {
                    formattedRate.add(Currency.USD.getSymbol() + String.format("%8.2f %12.2f", usdRate.getBuyRate(), usdRate.getSellRate()));
                }

                if (eurRate != null) {
                    formattedRate.add(Currency.EUR.getSymbol() + String.format("%8.2f %12.2f", eurRate.getBuyRate(), eurRate.getSellRate()));
                }
                formattedRate.add("");
            }
        }
        return formattedRate.toString();
    }
}
