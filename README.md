# kafka-hdfs-sink

Simple serialization of data from  Kafka to HDFS. Uses direct a direct connection to the brokers instead of zookeeper based tracking.

Creates partitioned files in HDFS.

See http://spark.apache.org/docs/latest/streaming-kafka-integration.html for more information

To compile use:
<code>
sbt package
</code>

To submit this job use:
<code>
/opt/spark-1.6.1-bin-hadoop2.6/bin/spark-submit --name KafkaConsumer --class KafkaConsumer  --packages org.apache.spark:spark-streaming-kafka_2.10:1.6.1 /tmp/kafka-consumer-serializer_2.10-1.0.jar instance-xxx.bigstep.io:9092,instance-xxx.bigstep.io:9092,instance-xxx.bigstep.io:9092 test1
</code>
