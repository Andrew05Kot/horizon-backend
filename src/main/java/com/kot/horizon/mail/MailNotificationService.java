package com.kot.horizon.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.notification.NotificationEntity;
import com.kot.horizon.notification.NotificationService;
import com.kot.horizon.tour.TourEntity;
import com.kot.horizon.user.model.UserEntity;

@Service
public class MailNotificationService {

	private final Logger LOGGER = LogManager.getLogger(MailNotificationService.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private NotificationService notificationService;

	private static final String SUBJECT = "Horizon notification";

	public void createAndSendNotification(UserEntity recipient, TourEntity tour, String status) {
		Map<String, Object> model = buildTemplateModel(recipient.getEmail(), tour.getName(), status);
		try {
			emailService.sendMessageUsingFreemarkerTemplate("notification-template.ftl", recipient.getEmail(), SUBJECT, model);
			notificationService.create(buildNotificationEntity(recipient, status));
		} catch (IOException | TemplateException | MessagingException ex) {
			LOGGER.error(ex.getMessage(), ex);
			notificationService.create(buildNotificationEntity(recipient, "NOT_SEND"));
		}
	}

	private NotificationEntity buildNotificationEntity(UserEntity recipient, String status) {
		NotificationEntity entity = new NotificationEntity();
		entity.setRecipient(recipient);
		entity.setStatus(status);
		return entity;
	}

	private Map<String, Object> buildTemplateModel(String recipientName, String tourName, String status) {
		Map<String, Object> model = new HashMap<>();
		model.put("recipientName", recipientName);
		model.put("tourName", tourName);
		model.put("status", status);
		return model;
	}

}
