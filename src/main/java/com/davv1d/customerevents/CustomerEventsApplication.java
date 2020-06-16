package com.davv1d.customerevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.config.EnablePublisher;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
@EnablePublisher
public class CustomerEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerEventsApplication.class, args);
	}

}
