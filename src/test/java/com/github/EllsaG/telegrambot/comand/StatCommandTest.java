package com.github.EllsaG.telegrambot.comand;

import com.github.EllsaG.telegrambot.command.Command;
import com.github.EllsaG.telegrambot.command.CommandName;
import com.github.EllsaG.telegrambot.command.StatCommand;
import com.github.EllsaG.telegrambot.javarushclient.dto.GroupStatDTO;
import com.github.EllsaG.telegrambot.javarushclient.dto.StatisticDTO;
import com.github.EllsaG.telegrambot.service.SendBotMessageService;
import com.github.EllsaG.telegrambot.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.EllsaG.telegrambot.comand.AbstractCommandTest.prepareUpdate;
import static com.github.EllsaG.telegrambot.command.StatCommand.STAT_MESSAGE;
import static java.lang.String.format;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest {

    private SendBotMessageService sendBotMessageService;
    private StatisticsService statisticsService;
    private Command statCommand;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        statisticsService = Mockito.mock(StatisticsService.class);
        statCommand = new StatCommand(sendBotMessageService, statisticsService);
    }

    @Test
    public void shouldProperlySendMessage() {
        //given
        Long chatId = 1234567L;
        GroupStatDTO groupDto = new GroupStatDTO(1, "group", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1, Collections.singletonList(groupDto), 2.5);
        Mockito.when(statisticsService.countBotStatistic())
                .thenReturn(statisticDTO);

        //when
        statCommand.execute(prepareUpdate(chatId, CommandName.STAT.getCommandName()));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                format("%s (id = %s) - %s подписчиков", groupDto.getTitle(), groupDto.getId(), groupDto.getActiveUserCount())));
    }

}
