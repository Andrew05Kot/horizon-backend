package com.kot.horizon.common.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "postgresql")
class CustomFlywayConfiguration extends FlywayAutoConfiguration.FlywayConfiguration {

	@Primary
	@Bean(name = "flywayInitializer")
	@DependsOn("applicationContextProvider")
	public FlywayMigrationInitializer flywayInitializer(Flyway flyway){
		return super.flywayInitializer(flyway, null);
	}
}
