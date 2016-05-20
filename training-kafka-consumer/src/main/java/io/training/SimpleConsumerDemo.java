/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.training;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.javaapi.FetchResponse;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;
import org.apache.kafka.clients.producer.Producer;

import java.util.HashMap;
import java.util.Map;

public class SimpleConsumerDemo {

    private static void printMessages(ByteBufferMessageSet messageSet) throws UnsupportedEncodingException {
        for (MessageAndOffset messageAndOffset : messageSet) {
            ByteBuffer payload = messageAndOffset.message().payload();
            byte[] bytes = new byte[payload.limit()];
            payload.get(bytes);
            System.out.println(new String(bytes, "UTF-8"));
        }
    }

//    private static void generateData() {
//        Producer producer2 = new Producer(KafkaProperties.topic2, false);
//        producer2.start();
//        Producer producer3 = new Producer(KafkaProperties.topic3, false);
//        producer3.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws Exception {
        //generateData();

        SimpleConsumer simpleConsumer = new SimpleConsumer(KafkaProperties.kafkaServerURL,
                KafkaProperties.kafkaServerPort,
                KafkaProperties.connectionTimeOut,
                KafkaProperties.kafkaProducerBufferSize,
                KafkaProperties.clientId);

        System.out.println("Testing single fetch");
        FetchRequest req = new FetchRequestBuilder()
                .clientId(KafkaProperties.clientId)
                .addFetch(KafkaProperties.topic, 0, 0L, 100)
                .build();
        FetchResponse fetchResponse = simpleConsumer.fetch(req);
        printMessages(fetchResponse.messageSet(KafkaProperties.topic, 0));

        System.out.println("Testing single multi-fetch");
        Map<String, List<Integer>> topicMap = new HashMap<String, List<Integer>>();
        topicMap.put(KafkaProperties.topic, Collections.singletonList(0));
        req = new FetchRequestBuilder()
                .clientId(KafkaProperties.clientId)
                .addFetch(KafkaProperties.topic, 0, 0L, 100)
                .build();
        fetchResponse = simpleConsumer.fetch(req);
        int fetchReq = 0;
        for (Map.Entry<String, List<Integer>> entry : topicMap.entrySet()) {
            String topic = entry.getKey();
            for (Integer offset : entry.getValue()) {
                System.out.println("Response from fetch request no: " + ++fetchReq);
                printMessages(fetchResponse.messageSet(topic, offset));
            }
        }
    }
}
