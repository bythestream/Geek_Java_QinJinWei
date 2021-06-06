package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@ConditionalOnClass(Student.class)
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration {

	@Autowired
	WebProperties greeterProperties;

	@Autowired
	WebConfiguration configuration;

	@Bean
	public Student createStudent() {
		return new Student(greeterProperties.getId(), greeterProperties.getName());
	}
	
	
	@Bean
    @ConditionalOnMissingBean
    public WebConfiguration greeterConfig() {

        String userName = greeterProperties.getName() == null
          ? System.getProperty("user.name") 
          : greeterProperties.getName();
        
        // ..

        WebConfiguration greetingConfig = new WebConfiguration();
        greetingConfig.put("user.name", userName);
        // ...
        return greetingConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public Student greeter(WebConfiguration greetingConfig) {
        return new Student(greetingConfig);
    }
}
