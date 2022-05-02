package com.kot.horizon.localization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.model.user.Language;
import com.kot.horizon.service.user.CurrentUserService;

@Service
public class LocalizationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalizationService.class);
	public static final Language DEFAULT_LANGUAGE = Language.UK;

	@Autowired
	private CurrentUserService currentUserService;

	public String translate(String message) {
		return getString(message, getCurrentLocale());
	}
	
	public Locale getCurrentLocale() {
		if(currentUserService.getCurrentUser() != null){
			return currentUserService.getCurrentUser().getLanguage().getLocale();
		}
		return DEFAULT_LANGUAGE.getLocale();
	}

	public static String translate(String message, String language) {
		Locale locale = new Locale(language);
		return getString(message, locale);
	}

	public static String translate(String message, Language language) {
		return getString(message, language.getLocale());
	}

	private static String getString(String message, Locale locale) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
			return bundle.getString(message);
		} catch (MissingResourceException e1) {
			LOGGER.info("Locale not found: " + e1.getMessage());
			try {
				ResourceBundle bundle = ResourceBundle.getBundle("messages", DEFAULT_LANGUAGE.getLocale());
				return bundle.getString(message);
			} catch (MissingResourceException e2) {
				LOGGER.info("Message key not found: " + e2.getMessage());
			}
		}
		return null;
	}

}