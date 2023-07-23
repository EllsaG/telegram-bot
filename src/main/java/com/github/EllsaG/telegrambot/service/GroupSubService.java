package com.github.EllsaG.telegrambot.service;

import com.github.EllsaG.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.EllsaG.telegrambot.repository.entity.GroupSub;

import java.util.List;
import java.util.Optional;

public interface GroupSubService {
    GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo);

    GroupSub save(GroupSub groupSub);

    Optional<GroupSub> findById(Integer id);

    List<GroupSub> findAll();

}
