package com.example.findlocal.services;

import com.example.findlocal.entity.Conversation;
import com.example.findlocal.entity.Message;
import com.example.findlocal.entity.User;
import com.example.findlocal.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private ConversationService conversationService;
    private UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, ConversationService conversationService, UserService userService) {
        this.conversationService = conversationService;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public List<Message> getMessagesByConversationId(Long conversationId) {
        return messageRepository.findAllByConversationId(conversationId);
    }

    public Message addMessage(Long conversationId, Long userId, Message message) {
        Conversation conversation = conversationService.getConversationByConversationId(conversationId);
        User user = userService.getUser(userId);

        message.setConversation(conversation);
        message.setUser(user);

        return messageRepository.save(message);
    }
}
