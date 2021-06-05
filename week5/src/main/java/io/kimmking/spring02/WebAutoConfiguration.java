package io.kimmking.spring02;

import org.springframework.beans.factory.annotation.Autowired;
import io.kimmking.spring02.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.kimmking.spring01.Student;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@Import(WebConfiguration.class)
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration {

	@Autowired
	WebProperties properties;

	@Autowired
	WebConfiguration configuration;

	@Bean
	public Student createStudent() {
		return new Student(properties.getA(), properties.getName());
	}
}
