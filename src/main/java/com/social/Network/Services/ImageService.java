package com.social.Network.Services;


import org.springframework.web.multipart.MultipartFile;
import com.social.Network.DTO.UserDTO;
import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {

    String updateProfileImage(UserDTO user, MultipartFile multipartFile) throws IOException;

    Path getProfileImagesPath();
}
