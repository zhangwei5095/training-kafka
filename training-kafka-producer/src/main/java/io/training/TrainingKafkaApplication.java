package io.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序
 *
 * Created by percy on 16-5-18.
 */
@SpringBootApplication
public class TrainingKafkaApplication {

    public static void main(String[] args) {
        final boolean isAsync = args.length > 0 ? !args[0].trim().toLowerCase().equals("sync") : true;
        Producer producerThread = new Producer(KafkaProperties.topic, isAsync);
        producerThread.start();
        SpringApplication.run(TrainingKafkaApplication.class, args);
    }
}
