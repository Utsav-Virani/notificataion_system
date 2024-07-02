package com.circlecode.notificataion_system;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.circlecode.notificataion_system.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class NotificationServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testSendNotification() {
        String message = "Test notification";

        notificationService.sendNotification(message);

        // Verify that the message was cached in Redis
        verify(valueOperations).set("latestNotification", message);

        // Verify that the message was sent via WebSocket
        verify(messagingTemplate).convertAndSend("/topic/notifications", message);
    }
}
