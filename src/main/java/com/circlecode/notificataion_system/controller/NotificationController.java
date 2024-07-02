package com.circlecode.notificataion_system.controller;

import com.circlecode.notificataion_system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notify")
    public void notify(@RequestBody String message){
        notificationService.sendNotification(message);
    }

}
