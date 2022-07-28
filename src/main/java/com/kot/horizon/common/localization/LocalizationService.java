package com.kot.horizon.common.localization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.user.model.Language;
import com.kot.horizon.user.service.CurrentUserService;

@Service
public class LocalizationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationService.class);

	public static final Language DEFAULT_LANGUAGE = Language.EN;

	@Autowired
	private CurrentUserService currentUserService;

	public String translate(String message) {
		return getMessageString(message, getCurrentLocale());
	}

	public Locale getCurrentLocale() {
		if (currentUserService.getCurrentUser() != null) {
			return currentUserService.getCurrentUser().getLanguage().getLocale();
		}
		return DEFAULT_LANGUAGE.getLocale();
	}

	public static String translate(String message, String language) {
		Locale locale = new Locale(language);
		return getMessageString(message, locale);
	}

	public static String translate(String message, Language language) {
		return getMessageString(message, language.getLocale());
	}

	private static String getMessageString(String message, Locale locale) {
		try {
			return getMessageBundle(message, locale);
		} catch (MissingResourceException lnf) {
			LOGGER.info("Locale not found {}", lnf.getMessage());
			try {
				return getMessageBundle(message, DEFAULT_LANGUAGE.getLocale());
			} catch (MissingResourceException mknf) {
				LOGGER.info("Message key not found {}", mknf.getMessage());
			}
		}
		return null;
	}

	private static String getMessageBundle(String message, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		return bundle.getString(message);
	}
}
