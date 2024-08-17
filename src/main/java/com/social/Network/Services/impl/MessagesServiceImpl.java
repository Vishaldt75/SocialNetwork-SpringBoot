package com.social.Network.Services.impl;


import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.Network.Converters.MessageDtoToMessageConverter;
import com.social.Network.Converters.MessageToMessageDtoConverter;
import com.social.Network.DTO.MessageDTO;
import com.social.Network.Entities.User;
import com.social.Network.Repositories.MessageRepository;
import com.social.Network.Services.MessagesService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessagesServiceImpl implements MessagesService {

    private final MessageRepository messageRepository = null;
    private final MessageToMessageDtoConverter messageToMessageDtoConverter = new MessageToMessageDtoConverter();
    private final MessageDtoToMessageConverter messageDtoToMessageConverter = new MessageDtoToMessageConverter();

    @Override
    @Transactional(readOnly = true)
    public Collection<MessageDTO> findAllRecentMessages(Long id) {
        Iterable<com.social.Network.Entities.Message> all = messageRepository.findAllRecentMessages(id);
        Map<User, MessageDTO> map = new HashMap<>();
        all.forEach(m -> {
            MessageDTO messageDTO = messageToMessageDtoConverter.convert(m, id);
            User user = m.getSender().getId().equals(id) ? m.getReceiver() : m.getSender();
            map.put(user, messageDTO);
        });
        return map.values();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDTO> findConversation(Long userId, Long companionId) {
        List<com.social.Network.Entities.Message> all = messageRepository.findConversation(userId, companionId);
        List<MessageDTO> messages = new LinkedList<>();
        all.forEach(m -> messages.add(messageToMessageDtoConverter.convert(m, userId)));
        return messages;
    }

    @Override
    @Transactional(readOnly = true)
    public MessageDTO getRecentMessage(Long id) {
        Message message = messageRepository.findFirstBySenderIdOrReceiverIdOrderByIdDesc(id, id);
        MessageDTO messageDTO = messageToMessageDtoConverter.convert(message, id);
        return messageDTO;
    }

    @Override
    @Transactional
    public void postMessage(MessageDTO messageDTO) {
        Message message = messageDtoToMessageConverter.convert(messageDTO);
        messageRepository.saveAll(message);
    }


}
