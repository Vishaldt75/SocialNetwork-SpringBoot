package com.social.Network.Services;


import java.util.Collection;
import java.util.List;

import com.social.Network.DTO.MessageDTO;

public interface MessagesService {
    Collection<MessageDTO> findAllRecentMessages(Long id);

    List<MessageDTO> findConversation(Long userId, Long companionId);

    MessageDTO getRecentMessage(Long id);

    void postMessage(MessageDTO messageDTO);
}
