package io.training.oschina.producer;

import kafka.producer.KeyedMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * 消息生产者<br>
 *
 * 摘自于http://my.oschina.net/cloudcoder/blog/299215
 *
 * Created by percy on 5/20/16.
 */
public class ProducerDemo {
    public static void main(String[] args) {
        Random rnd = new Random();
        int events=100;

        // 设置配置属性
        Properties props = new Properties();
        props.put("bootstrap.servers","localhost:9092");
        props.put("client.id", "DemoProducer001");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 可选配置，如果不配置，则使用默认的partitioner
        //props.put("partitioner.class", "com.catt.kafka.demo.PartitionerDemo");
        // 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
        // 值为0,1,-1,可以参考
        // http://kafka.apache.org/08/configuration.html
        //props.put("request.required.acks", "1");
//        ProducerConfig config = new ProducerConfig(props);

        // 创建producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        // 产生并发送消息
        long start=System.currentTimeMillis();
        for (long i = 0; i < events; i++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + i;//rnd.nextInt(255);
            String msg = runtime + ",www.example.com," + ip;
            //如果topic不存在，则会自动创建，默认replication-factor为1，partitions为0
//            KeyedMessage<String, String> data = new KeyedMessage<String, String>(
//                    "page_visits", ip, msg);
            ProducerRecord<String, String> data = new ProducerRecord<String, String>("test", ip, msg);

            producer.send(data);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
        // 关闭producer
        producer.close();
    }

}
