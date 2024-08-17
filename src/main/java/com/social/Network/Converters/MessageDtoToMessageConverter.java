package com.social.Network.Converters;


import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.social.Network.DTO.MessageDTO;
import com.social.Network.Entities.Message;

@Component
@RequiredArgsConstructor
public class MessageDtoToMessageConverter implements Converter<MessageDTO, Message> {

    private final UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();

    @Override
    public Message convert(MessageDTO messageDTO) {
        if(messageDTO == null) {
            return null;
        }

        return Message.builder()
                      .time(messageDTO.getTime())
                      .message(messageDTO.getMessage())
                      .sender(userDtoToUserConverter.convert(messageDTO.getSender()))
                      .receiver(userDtoToUserConverter.convert(messageDTO.getReceiver()))
                      .build();
    }
}