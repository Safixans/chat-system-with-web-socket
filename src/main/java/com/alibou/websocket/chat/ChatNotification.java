package com.alibou.websocket.chat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {

    private String chatId;
    private String senderId;
    private String recipientId;
    private String content;

}
