package com.github.EllsaG.telegrambot.command;

import com.github.EllsaG.telegrambot.command.annotation.AdminCommand;
import com.github.EllsaG.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.EllsaG.telegrambot.service.GroupSubService;
import com.github.EllsaG.telegrambot.service.SendBotMessageService;
import com.github.EllsaG.telegrambot.service.StatisticsService;
import com.github.EllsaG.telegrambot.service.TelegramUserService;

import java.util.List;
import java.util.Map;

import static com.github.EllsaG.telegrambot.command.CommandName.*;
import static java.util.Objects.nonNull;

public class CommandContainer {
    private final Map<String, Command> commandMap;
    private final Command unknownCommand;
    private final List<String> admins;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService, List<String> admins,
                            StatisticsService statisticsService) {
        this.admins = admins;

        commandMap = Map.ofEntries(Map.entry(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService)),
                        Map.entry(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService)),
                        Map.entry(HELP.getCommandName(), new HelpCommand(sendBotMessageService)),
                        Map.entry(NO.getCommandName(), new NoCommand(sendBotMessageService)),
                        Map.entry(STAT.getCommandName(), new StatCommand(sendBotMessageService, statisticsService)),
                        Map.entry(ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(sendBotMessageService, javaRushGroupClient,groupSubService)),
                        Map.entry(LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(sendBotMessageService, telegramUserService)),
                        Map.entry(DELETE_GROUP_SUB.getCommandName(), new DeleteGroupSubCommand(sendBotMessageService,groupSubService, telegramUserService)),
                        Map.entry(ADMIN_HELP.getCommandName(), new AdminHelpCommand(sendBotMessageService)));

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier, String username) {
        Command orDefault = commandMap.getOrDefault(commandIdentifier, unknownCommand);
        if (isAdminCommand(orDefault)) {
            if (admins.contains(username)) {
                return orDefault;
            } else {
                return unknownCommand;
            }
        }
        return orDefault;
    }

    private boolean isAdminCommand(Command command) {
        return nonNull(command.getClass().getAnnotation(AdminCommand.class));
    }


}
