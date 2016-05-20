package io.training.web;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by percy on 5/20/16.
 */
public class TrainingConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerSerializer");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerSerializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<Integer, String>(props);
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConsumerRecords<Integer, String> records = consumer.poll(20);
            for (ConsumerRecord<Integer, String> record : records)
                System.out.printf("========================offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
        }
    }
}
