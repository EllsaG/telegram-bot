package com.github.EllsaG.telegrambot.service;

import com.github.EllsaG.telegrambot.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    void save(TelegramUser telegramUser);
    List<TelegramUser> retrieveAllActiveUsers();
    Optional<TelegramUser> findByChatId(Long chatId);
    /**
     * Retrieve all inactive {@link TelegramUser}
     *
     * @return the collection of the inactive {@link TelegramUser} objects.
     */
    List<TelegramUser> findAllInActiveUsers();
}
