package org.shark.boot04.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@AutoConfiguration
public class MessageConfig {

	@Bean
	MessageSource MessageSourece() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("application");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
