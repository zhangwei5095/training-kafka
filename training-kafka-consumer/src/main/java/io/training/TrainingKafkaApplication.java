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

        Consumer consumer = new Consumer("test");
        consumer.doWork();
        SpringApplication.run(TrainingKafkaApplication.class, args);
    }
}
