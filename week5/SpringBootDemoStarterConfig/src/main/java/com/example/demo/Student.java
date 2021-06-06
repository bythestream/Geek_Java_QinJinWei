package com.example.demo;


//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;


public class Student implements Serializable{//, ApplicationContextAware {


    private int id;
    private String name;
    
    public Student(int id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
    private WebConfiguration greetingConfig;

    public Student(WebConfiguration greetingConfig) {
        this.greetingConfig = greetingConfig;
    }
    
    public void setBeanName(String name) {
    	this.name = name;
    }

    public String getName() {
    	return this.name;
    }
    
    //private String beanName;
    //private ApplicationContext applicationContext;
    
    public void init(){
        System.out.println("hello...........");
    }
    
//    public Student create(){
//        return new Student(101,"KK101");
//    }

    public String greet() {
       return ("name=" + this.name + ", ID=" + this.id);
    }


}
