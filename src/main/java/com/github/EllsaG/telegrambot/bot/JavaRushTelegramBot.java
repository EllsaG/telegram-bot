package com.github.EllsaG.telegrambot.bot;

import com.github.EllsaG.telegrambot.command.CommandContainer;
import com.github.EllsaG.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.EllsaG.telegrambot.service.GroupSubService;
import com.github.EllsaG.telegrambot.service.SendBotMessageServiceImpl;
import com.github.EllsaG.telegrambot.service.StatisticsService;
import com.github.EllsaG.telegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.EllsaG.telegrambot.command.CommandName.NO;

@Component
public class JavaRushTelegramBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;


    private final CommandContainer commandContainer;

    @Autowired
    public JavaRushTelegramBot(TelegramUserService telegramUserService,JavaRushGroupClient groupClient,
                               GroupSubService groupSubService, @Value("#{'${bot.admins}'.split(',')}") List<String> admins,
                               StatisticsService statisticsService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),
                telegramUserService,groupClient,groupSubService, admins, statisticsService);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String username = update.getMessage().getFrom().getFirstName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier, username).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), username).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }


}
