package com.github.EllsaG.telegrambot.comand;

import com.github.EllsaG.telegrambot.command.Command;
import com.github.EllsaG.telegrambot.command.CommandContainer;
import com.github.EllsaG.telegrambot.command.CommandName;
import com.github.EllsaG.telegrambot.command.UnknownCommand;
import com.github.EllsaG.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.EllsaG.telegrambot.service.GroupSubService;
import com.github.EllsaG.telegrambot.service.SendBotMessageService;
import com.github.EllsaG.telegrambot.service.StatisticsService;
import com.github.EllsaG.telegrambot.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static java.util.Collections.singletonList;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient groupClient = Mockito.mock(JavaRushGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        StatisticsService statisticsService = Mockito.mock(StatisticsService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService,
                groupClient, groupSubService, singletonList("username"),statisticsService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName(), "username");
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/fgjhdfgdfg";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand, "username");

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}
