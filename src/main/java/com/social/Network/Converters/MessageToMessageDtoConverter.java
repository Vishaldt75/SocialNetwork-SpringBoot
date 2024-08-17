package com.social.Network.Converters;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.social.Network.DTO.MessageDTO;
import com.social.Network.Entities.Message;

@Component
@RequiredArgsConstructor
public class MessageToMessageDtoConverter {

    private final UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();

    public MessageDTO convert(Message message, Long id) {
        if(message == null) {
            return null;
        }
        return MessageDTO.builder()
                         .time(message.getTime())
                         .message(message.getMessage())
                         .receiver(userToUserDtoConverter.convert(message.getReceiver()))
                         .sender(userToUserDtoConverter.convert(message.getSender()))
                         .companionId(id.equals(message.getSender().getId()) ?
                                      message.getReceiver().getId() : message.getSender().getId())
                         .build();
    }
}
