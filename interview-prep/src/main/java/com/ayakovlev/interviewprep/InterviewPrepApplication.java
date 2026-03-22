package com.ayakovlev.interviewprep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application entry point.
 * EnableJpaAuditing activates automatic filling of audit fields
 * (dcre, dmod, userCre, userMod) in all Entity classes
 * that use AuditingEntityListener.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class InterviewPrepApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewPrepApplication.class, args);
	}

}
