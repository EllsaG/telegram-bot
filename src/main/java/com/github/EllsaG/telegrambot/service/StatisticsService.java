package com.github.EllsaG.telegrambot.service;

import com.github.EllsaG.telegrambot.javarushclient.dto.StatisticDTO;

/**
 * Service for getting bot statistics.
 */
public interface StatisticsService {
    StatisticDTO countBotStatistic();
}