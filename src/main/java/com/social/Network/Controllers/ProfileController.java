package com.social.Network.Controllers;


import com.social.Network.DTO.MessageDTO;
import com.social.Network.DTO.UserDTO;
import com.social.Network.Services.FriendsService;
import com.social.Network.Services.MessagesService;
import com.social.Network.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService = null;
    private final FriendsService friendsService = null;
    private final MessagesService messagesService = null;

    @GetMapping("/profile")
    public String getProfilePage(Model model, HttpServletRequest request) {
        UserDTO user = getUserFromSession(request);
        model.addAttribute("user", user);
        MessageDTO recentMessage = messagesService.getRecentMessage(user.getId());
        model.addAttribute("recentMessage", recentMessage);
        Set<UserDTO> friends = friendsService.getAcceptedFriendshipUsers(user.getId());
        model.addAttribute("friends", friends);
        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String getUserPage(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDTO sessionUser = getUserFromSession(request);
        if(sessionUser.getId().equals(id)) {
            return "redirect:/user/profile";
        }
        UserDTO user = userService.getUserById(id);
        Set<UserDTO> friends = friendsService.getAcceptedFriendshipUsers(id);
        Boolean friendship = friendsService.checkFriendship(sessionUser, user);
        model.addAttribute("user", user);
        model.addAttribute("usersHaveFriendship", friendship);
        model.addAttribute("friends", friends);
        return "profile";
    }
}
