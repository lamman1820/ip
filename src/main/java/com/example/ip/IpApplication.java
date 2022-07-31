package com.example.ip;

import com.example.ip.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@EnableScheduling
public class IpApplication {
	@Autowired
	IpService ipService;

	public static void main(String[] args) {
		SpringApplication.run(IpApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	void test() throws ExecutionException, InterruptedException {
		ipService.check();;
	}
}
