# training-kafka
练习使用kafka<br>
bin/zookeeper-server-start.sh config/zookeeper.properties <br>
bin/kafka-server-start.sh config/server.properties <br>
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test <br>
bin/kafka-topics.sh --list --zookeeper localhost:2181 <br>
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test <br>
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning <br>



