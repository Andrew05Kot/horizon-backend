package com.kot.horizon.mail;

import java.io.IOException;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Service
public class EmailService {

	@Value("${spring.mail.username}")
	private String username;

	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;

	@Autowired
	private JavaMailSender emailSender;

	public void sendMessageUsingFreemarkerTemplate(String template, String to, String subject, Map<String, Object> templateModel)
			throws IOException, TemplateException, MessagingException {

		Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate(template);
		String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

		sendHtmlMessage(to, subject, htmlBody);
	}

	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom("Horizon");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		emailSender.send(message);
	}
}
