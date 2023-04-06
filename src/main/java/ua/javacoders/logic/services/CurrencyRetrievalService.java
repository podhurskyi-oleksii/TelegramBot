package ua.javacoders.logic.services;

import ua.javacoders.logic.dto.CurrencyRateDto;

import java.util.List;

public interface CurrencyRetrievalService {
    public List<CurrencyRateDto> getRate();
}