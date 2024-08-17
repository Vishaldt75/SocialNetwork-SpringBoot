package com.social.Network.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.social.Network.DTO.MessageDTO;
import com.social.Network.DTO.UserDTO;
import com.social.Network.Services.MessagesService;
import com.social.Network.Services.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static info.cheremisin.social.network.utils.ServerUtils.getUserFromSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MessagesController {

    private final MessagesService messagesService = null;
    private final UserService userService = null;

    private void addConversationToModel(Long companionId, HttpServletRequest request, Model model) {
        UserDTO user = getUserFromSession(request);
        UserDTO companion = userService.getUserById(companionId);
        List<MessageDTO> messages = messagesService.findConversation(user.getId(), companionId);
        model.addAttribute("messages", messages);
        model.addAttribute("companion", companion);
    }

    @GetMapping("/messages")
    public String getMessages(HttpServletRequest request, Model model) {
        UserDTO user = getUserFromSession(request);
        Collection<MessageDTO> recentMessages = messagesService.findAllRecentMessages(user.getId());
        model.addAttribute("recentMessages", recentMessages);
        return "messages";
    }

    @GetMapping("/conversation/{companionId}")
    public String getConversation(@PathVariable Long companionId, HttpServletRequest request, Model model) {
        addConversationToModel(companionId, request, model);
        model.addAttribute("newMessage", new MessageDTO());
        return "conversation";
    }

    @PostMapping("/conversation/{companionId}")
    public String postMessage(@PathVariable Long companionId,
                              @Valid @ModelAttribute("newMessage") MessageDTO messageDTO, BindingResult bindingResult,
                              HttpServletRequest request, Model model) {
        if(bindingResult.hasErrors()) {
            addConversationToModel(companionId, request, model);
            return "conversation";
        }
        UserDTO user = getUserFromSession(request);
        UserDTO companion = userService.getUserById(companionId);
        messageDTO.setSender(user);
        messageDTO.setReceiver(companion);
        messageDTO.setTime(LocalDateTime.now());
        messagesService.postMessage(messageDTO);
        return "redirect:/user/conversation/" + messageDTO.getReceiver().getId();
    }

}
