package com.kot.horizon.mail;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import com.kot.horizon.common.TestsDetails;

@DirtiesContext
@ExtendWith(MockitoExtension.class)
public class EmailServiceTest extends TestsDetails {

	@Autowired
	private EmailService emailService;

	@MockBean
	private JavaMailSender javaMailSender;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Mock
	private MimeMessage mimeMessage;

	@Test
	public void sendToJmsServiceTest() throws MessagingException, IOException, TemplateException {
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		emailService.sendMessageUsingFreemarkerTemplate("email-template.ftl", "kot@gmail.com", "Token", new HashMap<>());
		verify(javaMailSender).send(mimeMessage);
	}
}
