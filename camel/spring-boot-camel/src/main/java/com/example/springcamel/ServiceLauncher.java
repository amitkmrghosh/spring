package com.example.springcamel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.camel.builder.RouteBuilder;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(value = "com.example.springcamel")
public class ServiceLauncher {

	@Autowired
	CamelContext camelContext;

	public static void main(String[] args) {
		SpringApplication.run(ServiceLauncher.class, args);
	}


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

       return args -> {

            camelContext.addRoutes(new RouteBuilder() {
 
             public void configure() {
               from("file:/home/frank/test/in?noop=true").to("file:/home/frank/test/out");
            } 
 
         });

        };


    }
}