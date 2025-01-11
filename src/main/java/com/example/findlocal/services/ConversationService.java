package com.example.findlocal.services;

import com.example.findlocal.entity.Conversation;
import com.example.findlocal.entity.User;
import com.example.findlocal.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConversationService {
    private UserService userService;
    private ConversationRepository conversationRepository;

    @Autowired
    public ConversationService(UserService userService, ConversationRepository conversationRepository) {
        this.userService = userService;
        this.conversationRepository = conversationRepository;
    }

    //todo: understand how to start a conversation and add inital users to it
    public Conversation createConversation(Long userId, Conversation conversation) {
        User user = userService.getUser(userId);
        if (conversation.getUsers() == null) {
            conversation.setUsers(new HashSet<>(Set.of(user)));
        }

        return conversationRepository.save(conversation);
    }

    public Conversation addUserToConversation(Long userId, Long conversationId) {
        User user = userService.getUser(userId);
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException(String.format("conversation not found for %d", conversationId)));
        conversation.getUsers().add(user);
        return conversation;
    }

    public Conversation getConversationByConversationId(Long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException(String.format("Conversation not found for %d", conversationId)));
    }

    public List<Conversation> getConversationsByUserId(Long userId) {
        return conversationRepository.findAllByUsersId(userId);
    }
}
