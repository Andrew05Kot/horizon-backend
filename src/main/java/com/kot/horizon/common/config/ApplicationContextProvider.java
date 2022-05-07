package com.kot.horizon.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Autowired
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public static <T> T getBean(final Class clazz) {
		return (T) applicationContext.getBean(clazz);
	}

	public static ApplicationContext getContext() {
		return applicationContext;
	}

}

