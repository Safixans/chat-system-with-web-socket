package com.alibou.websocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void processMessage(
      @Payload ChatMessage chatMessage
    ){
        ChatMessage saved = chatMessageService.save(chatMessage);
        //john/queue/messages
        template.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                ChatNotification.builder()
                        .chatId(saved.getChatId())
                        .senderId(saved.getSenderId())
                        .recipientId(saved.getRecipientId())
                        .content(saved.getContent())
                        .build()
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId ){
    return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

}
