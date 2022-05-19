package com.kot.horizon.image.config;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageDirectoryInitializator {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageDirectoryInitializator.class);

	@Bean
	public void imagesFolderInitialization() {
		String rootDirectory = System.getProperty("user.home", ".");
		String folderWithImages = "/images/";
		File directory = new File(rootDirectory + folderWithImages);
		if ( !directory.exists() ) {
			directory.mkdir();
			LOGGER.info("created new directory {}", directory.getAbsolutePath());
		}
	}
}
