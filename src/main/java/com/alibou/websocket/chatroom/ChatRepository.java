package com.alibou.websocket.chatroom;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRepository extends MongoRepository<ChatRoom,String> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String receiverId);
}
