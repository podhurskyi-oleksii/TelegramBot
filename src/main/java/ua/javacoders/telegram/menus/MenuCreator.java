package ua.javacoders.telegram.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ua.javacoders.users.User;

public interface MenuCreator {
    InlineKeyboardMarkup createMenu(User user);
}
