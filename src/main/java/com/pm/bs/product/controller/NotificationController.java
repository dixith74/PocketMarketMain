package com.pm.bs.product.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

	@MessageMapping("/notifications")
	@SendTo("/topic/notifications")
	public void getNotifications() {
		
	}
}
