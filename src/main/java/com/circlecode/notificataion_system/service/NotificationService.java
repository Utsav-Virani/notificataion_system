package com.circlecode.notificataion_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void sendNotification(String message){
        //cache the message
        redisTemplate.opsForValue().set("latestNotification", message);


        //send the message
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
