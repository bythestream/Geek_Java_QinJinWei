package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xyu.greeter")
public class WebProperties {
	private int id = 999;
	private String name = "ABC";
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}

