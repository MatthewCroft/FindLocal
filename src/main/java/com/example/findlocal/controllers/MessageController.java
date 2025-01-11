package com.example.findlocal.controllers;

import com.example.findlocal.entity.Message;
import com.example.findlocal.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/conversation/{conversationId}/message")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@PathVariable Long userId, @PathVariable Long conversationId, @RequestBody Message message) {
        if (userId == null || conversationId == null || message.getContent() == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Message createdMessage = messageService.addMessage(conversationId, userId, message);

        return ResponseEntity
                .ok(createdMessage);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getConversationMessages(@PathVariable Long conversationId) {
        if (conversationId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        List<Message> messages = messageService.getMessagesByConversationId(conversationId);

        return ResponseEntity
                .ok(messages);
    }
}
