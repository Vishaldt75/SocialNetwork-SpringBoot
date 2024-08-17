package com.social.Network.Converters;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.social.Network.DTO.PageDTO;
import com.social.Network.DTO.UserDTO;
import com.social.Network.Entities.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dmitrii on 03.10.2019.
 */

@Component
@RequiredArgsConstructor
public class PageToPageDtoUserConverter implements Converter<Page<User>, PageDTO<UserDTO>> {

    private final UserToUserDtoConverter userToUserDtoConverter;

    @Override
    public PageDTO<UserDTO> convert(Page<User> page) {
        List<UserDTO> list = page.getContent().stream()
                .map(e -> userToUserDtoConverter.convert(e))
                .collect(Collectors.toList());

        return PageDTO.<UserDTO>builder()
                .content(list)
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }

}
