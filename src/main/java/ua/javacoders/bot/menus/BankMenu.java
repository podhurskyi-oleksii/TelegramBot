package ua.javacoders.bot.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.user.User;

import java.util.ArrayList;
import java.util.List;

import static ua.javacoders.utils.Emoji.*;

/**
 * Implements the MenuCreator interface to create a bank selection menu.
 * This menu displays a list of banks with checkboxes indicating whether they are selected or not.
 * It also provides navigation buttons to go back or return to the main menu.
 */
public class BankMenu implements MenuCreator {

    /**
     * Creates an InlineKeyboardMarkup object representing the bank selection menu.
     * The menu contains buttons for banks and navigation buttons.
     *
     * @param user the user for whom the menu is being created
     * @return InlineKeyboardMarkup object representing the bank selection menu
     */
    @Override
    public InlineKeyboardMarkup createMenu(User user) {
        InlineKeyboardMarkup bankMenu = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(firstRow(user));
        rowList.add(secondRow(user));
        rowList.add(thirdRow());
        rowList.add(fourthRow());

        bankMenu.setKeyboard(rowList);
        return bankMenu;
    }

    private InlineKeyboardButton addMonoButton(User user) {
        InlineKeyboardButton monoButton = new InlineKeyboardButton();
        String emoji = user.getBanks().contains(Bank.MONO) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        monoButton.setText("Моно" + " " + emoji);
        monoButton.setCallbackData("MONO");
        return monoButton;
    }

    private InlineKeyboardButton addPrivateButton(User user) {
        InlineKeyboardButton privatButton = new InlineKeyboardButton();
        String emoji = user.getBanks().contains(Bank.PRIVAT) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        privatButton.setText("Приват" + " " + emoji);
        privatButton.setCallbackData("PRIVAT");
        return privatButton;
    }

    private InlineKeyboardButton addNbuButton(User user) {
        InlineKeyboardButton nbuButton = new InlineKeyboardButton();
        String emoji = user.getBanks().contains(Bank.NBU) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        nbuButton.setText("НБУ" + " " + emoji);
        nbuButton.setCallbackData("NBU");
        return nbuButton;
    }

    private InlineKeyboardButton addOschadButton(User user) {
        InlineKeyboardButton oschadButton = new InlineKeyboardButton();
        String emoji = user.getBanks().contains(Bank.OSCHAD) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        oschadButton.setText("Ощад" + " " + emoji);
        oschadButton.setCallbackData("OSCHAD");
        return oschadButton;
    }

    private InlineKeyboardButton addPumbButton(User user) {
        InlineKeyboardButton pumbButton = new InlineKeyboardButton();
        String emoji = user.getBanks().contains(Bank.PUMB) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        pumbButton.setText("ПУМБ" + " " + emoji);
        pumbButton.setCallbackData("PUMB");
        return pumbButton;
    }

    private InlineKeyboardButton addBackButton() {
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        String emoji = LEFTWARDS_ARROW_WITH_HOOK.toString();
        backButton.setText("Назад" + " " + emoji);
        backButton.setCallbackData("BANK_BACK");
        return backButton;
    }

    private InlineKeyboardButton addBackToMainMenuButton() {
        InlineKeyboardButton backToMainMenuButton = new InlineKeyboardButton();
        String emoji = HOUSE_BUILDING.toString();
        backToMainMenuButton.setText("До головного меню" + " " + emoji);
        backToMainMenuButton.setCallbackData("BACK_TO_MAIN_MENU");
        return backToMainMenuButton;
    }

    private List<InlineKeyboardButton> firstRow(User user) {
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(addMonoButton(user));
        firstRow.add(addPrivateButton(user));
        firstRow.add(addNbuButton(user));
        return firstRow;
    }

    private List<InlineKeyboardButton> secondRow(User user) {
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(addOschadButton(user));
        secondRow.add(addPumbButton(user));
        return secondRow;
    }

    private List<InlineKeyboardButton> thirdRow() {
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(addBackButton());
        return thirdRow;
    }

    private List<InlineKeyboardButton> fourthRow() {
        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        fourthRow.add(addBackToMainMenuButton());
        return fourthRow;
    }
}
