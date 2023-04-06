package ua.javacoders.logic.banks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Bank {
    MONO("Монобанк", "MonoBank"),
    PRIVAT("Приватбанк", "PrivatBank"),
    OSCHAD("ОщадБанк", "OschadBank"),
    PUMB("ПУМБ", "Pumb"),
    NBU("НБУ", "NBU");

    private final String ukrName;
    private final String engName;
}
