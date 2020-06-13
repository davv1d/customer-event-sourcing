package com.davv1d.customerevents;

import com.davv1d.customerevents.domain.Customer;
import com.davv1d.customerevents.service.EventSourcedCustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.config.EnablePublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
@EnablePublisher
public class CustomerEventsApplication {

	private final EventSourcedCustomerService eventSourcedCustomerService;

	public CustomerEventsApplication(EventSourcedCustomerService eventSourcedCustomerService) {
		this.eventSourcedCustomerService = eventSourcedCustomerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerEventsApplication.class, args);
	}

//	@Scheduled(fixedDelay = 2000)
//	public void send() {
//		UUID uuid = UUID.randomUUID();
//		Customer customer = new Customer(uuid);
//		customer.activate();
//		customer.changeNameTo("Gowno");
//		eventSourcedCustomerService.save(customer);
//	}
}
