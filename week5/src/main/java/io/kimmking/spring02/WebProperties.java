package io.kimmking.spring02;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "web")
public class WebProperties {
	private int a = 123;
	private String name = "ABC";
	public int getA() {
		return a;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setA(int a) {
		this.a = a;
	}
}
