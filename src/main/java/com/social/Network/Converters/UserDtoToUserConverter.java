package com.social.Network.Converters;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.social.Network.DTO.UserDTO;
import com.social.Network.Entities.User;

@Component
@RequiredArgsConstructor
public class UserDtoToUserConverter implements Converter<UserDTO, User> {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User convert(UserDTO userDTO) {
        User.UserBuilder builder = User.builder()
                                       .id(userDTO.getId())
                                       .email(userDTO.getEmail())
                                       .firstName(userDTO.getFirstName())
                                       .lastName(userDTO.getLastName())
                                       .phone(userDTO.getPhone())
                                       .sex(Gender.getGenderByName(userDTO.getSex()))
                                       .image(userDTO.getImage())
                                       .dob(userDTO.getDob());

        if (userDTO.getPassword() != null)
            builder.password(passwordEncoder.encode(userDTO.getPassword()));

        return builder.build();
    }
}
