package com.kot.horizon.localization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import com.kot.horizon.common.TestsDetails;
import com.kot.horizon.common.localization.LocalizationService;
import com.kot.horizon.user.model.Language;
import com.kot.horizon.user.model.UserEntity;

@DirtiesContext
class LocalizationServiceTest extends TestsDetails {

	@Autowired
	private LocalizationService localizationService;

	@Test
	void getUaResourceTest() {
		assertEquals("Українська",
				LocalizationService.translate("language", "uk"));
	}

	@Test
	void getEnResourceTest() {
		assertEquals("English", LocalizationService.translate("language", "en"));
	}

	@Test
	void messageNotFound() {
		assertNull(LocalizationService.translate("randomtextt", "en"));
	}

	@Test
	void getDefaultLanguageTest() {
		loginUser(userBuilder.buildNewOne());
		UserEntity user = currentUserService.getCurrentUser();
		user.setLanguage(Language.UK);
		assertEquals("Українська",
				localizationService.translate("language"));
	}
}