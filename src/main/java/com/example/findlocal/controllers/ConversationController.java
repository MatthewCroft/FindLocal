package com.example.findlocal.controllers;

import com.example.findlocal.entity.Conversation;
import com.example.findlocal.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/conversation")
public class ConversationController {
    private ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@PathVariable Long userId, @RequestBody Conversation conversation) {
        if (userId == null || conversation.getName() == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Conversation createdConversation = conversationService.createConversation(userId, conversation);

        return ResponseEntity
                .ok(createdConversation);
    }

    @GetMapping
    public ResponseEntity<List<Conversation>> getAllUserConversations(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        List<Conversation> conversations = conversationService.getConversationsByUserId(userId);

        return ResponseEntity
                .ok(conversations);
    }
}
