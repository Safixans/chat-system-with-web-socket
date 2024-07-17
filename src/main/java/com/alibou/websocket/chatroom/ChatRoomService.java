package com.alibou.websocket.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRepository chatRepository;

   public Optional<String> getChatRoomId(String senderId, String receiverId, boolean createRoomIfNotExists) {
        return chatRepository.findBySenderIdAndRecipientId(senderId, receiverId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createRoomIfNotExists) {
                        var chatId = createChatId(senderId, receiverId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(String senderId, String receiverId) {
        var chatId=String.format("%s-%s", senderId, receiverId);
        ChatRoom senderRecipient= ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(receiverId)
                .build();

        ChatRoom recipientSender= ChatRoom.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .recipientId(senderId)
                .build();

        chatRepository.save(senderRecipient);
        chatRepository.save(recipientSender);

        return chatId;
    }
}
