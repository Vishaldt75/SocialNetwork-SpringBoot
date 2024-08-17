package com.social.Network.utils;


import javax.servlet.http.HttpServletRequest;
import com.social.Network.DTO.UserDTO;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.social.Network.Constants.Constants.PROFILE_IMAGES;

public class ServerUtils {

    public static UserDTO getUserFromSession(HttpServletRequest request){
        return (UserDTO) request.getSession().getAttribute("user");
    }

    public static Path getProfileImagesPath() {
        return Paths.get(".").resolve(PROFILE_IMAGES);
    }
}
