package com.tcs.hackkerrank;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.tcs.hackkerrank.rabbitmq.RabbitMessageConsumer;
@EnableScheduling
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Springboot_sprint3_Inventory {
	
	    
	public static void main(String[] args) {
		ConfigurableApplicationContext  ctx = SpringApplication.run(Springboot_sprint3_Inventory.class, args);
		RabbitMessageConsumer consumer = (RabbitMessageConsumer) ctx.getBean("rabbitMessageConsumer");
		try {
			//consumer = new RabbitMessageConsumer("queue");
			Thread consumerThread = new Thread(consumer);
			consumerThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 @Autowired
	    JobLauncher jobLauncher;
	     
	    @Autowired
	    Job job;
	@Scheduled(fixedRate = 300000)
    public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
    }
}

