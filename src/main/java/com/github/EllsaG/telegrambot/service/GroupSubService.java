package com.github.EllsaG.telegrambot.service;

import com.github.EllsaG.telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.EllsaG.telegrambot.repository.entity.GroupSub;

public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
