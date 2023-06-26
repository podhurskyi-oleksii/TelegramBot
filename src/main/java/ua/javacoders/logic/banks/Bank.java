package ua.javacoders.logic.banks;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enums with names of banks
 * Necessary for obtaining courses from the bank and correct text formatting
 */
@AllArgsConstructor
@Getter
public enum Bank {
    MONO("   МОНО   ", "MonoBank"),
    PRIVAT("  ПРВАТ  ", "PrivatBank"),
    OSCHAD(" ОЩАДБАНК ", "OschadBank"),
    PUMB("   ПУМБ   ", "Pumb"),
    NBU("    НБУ    ", "NBU");

    private final String ukrName;
    private final String engName;
}
