package com.circlecode.notificataion_system;

import com.circlecode.notificataion_system.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class NotificationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationService notificationService;

    @Container
    static GenericContainer<?> mongo = new GenericContainer<>("mongo:latest")
            .withExposedPorts(27017);

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379);

    @Container
    static GenericContainer<?> rabbitmq = new GenericContainer<>("rabbitmq:management")
            .withExposedPorts(5672, 15672);

    @BeforeEach
    void setUp() {
        System.setProperty("spring.data.mongodb.uri", "mongodb://" + mongo.getHost() + ":" + mongo.getMappedPort(27017) + "/notificationdb");
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
        System.setProperty("spring.rabbitmq.host", rabbitmq.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitmq.getMappedPort(5672).toString());
    }

    @Test
    void testNotify() throws Exception {
        String message = "Integration test notification";

        mockMvc.perform(post("/notify")
                        .content(message)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the message was cached in Redis
//        String cachedMessage = notificationService.getLatestNotification();
//        assertThat(cachedMessage).isEqualTo(message);
    }
}
