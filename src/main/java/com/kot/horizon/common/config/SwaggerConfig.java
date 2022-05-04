package com.kot.horizon.common.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*
 * If you would like to have different versions available in the single
 * generated API specification you should declare different Docket @Beans – one per single version.
 */

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@Profile("!( prod | ( test & !swagger )")
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String AUTHORIZATION_KEY_NAME = "JWT";
	public static final String DEFAULT_INCLUDE_PATTERN = "/.*";

	@Bean
	public Docket swaggerApi10() {
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title(SwaggerInfo.TITLE)
				.description(SwaggerInfo.DESCRIPTION)
				.version(SwaggerInfo.API_VERSION_V1).build();
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName(SwaggerInfo.GROUP_NAME + SwaggerInfo.API_VERSION_V1)
				.useDefaultResponseMessages(false)
				.apiInfo(apiInfo)
				.select().paths(regex(SwaggerInfo.PATH_V1))
				.build()
				.produces(SwaggerInfo.CONSUMES_AND_PRODUCES)
				.consumes(SwaggerInfo.CONSUMES_AND_PRODUCES)
				.tags(new Tag(SwaggerInfo.USER_API, SwaggerInfo.USER_CONTROLLER_INFO))
				.securityContexts(securityContexts())
				.securitySchemes(securitySchemes());
	}

	@Bean
	public UiConfiguration uiConfiguration() {
		return UiConfigurationBuilder.builder()
				.deepLinking(true)
				.validatorUrl(null)
				.build();
	}
	
	private List< ? extends SecurityScheme > securitySchemes() {
		return Arrays.asList( new ApiKey(AUTHORIZATION_KEY_NAME, AUTHORIZATION_HEADER, "header") );
	}

	private List< SecurityContext > securityContexts() {
		return Arrays.asList( 
				SecurityContext.builder()
					.securityReferences(defaultAuth())
					.forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
					.build() );
		}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList( 
				new SecurityReference(AUTHORIZATION_KEY_NAME, authorizationScopes));
	}
}
