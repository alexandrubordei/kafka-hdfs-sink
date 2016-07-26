import kafka.serializer.StringDecoder

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

object KafkaHDFSSink{

  def main(args: Array[String]): Unit = {
   if (args.length != 3) {
      System.err.println(s"""
        |Usage: KafkaHDFSSink <brokers> <topics> <destination-url>
        |  <brokers> is a list of one or more Kafka brokers
        |  <topics> is a list of one or more kafka topics to consume from
        |  <destination-url> is the url prefix (eg:in hdfs) into which to save the fragments. Fragment names will be suffixed with the timestamp. The fragments are directories. 
        """.stripMargin)
      System.exit(1)
    }
   
        //Create SparkContext
    val conf = new SparkConf()
      .setMaster("yarn-client")
      .setAppName("LowLevelKafkaConsumer")
      .set("spark.executor.memory", "5g")
      .set("spark.rdd.compress","true")
      .set("spark.storage.memoryFraction", "1")
      .set("spark.streaming.unpersist", "true")

     val Array(brokers, topics, destinationUrl) = args


    val sparkConf = new SparkConf().setAppName("KafkaConsumer")
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)

    messages.foreachRDD( rdd =>{
		if(!rdd.partitions.isEmpty)
		{
		 	val timestamp: Long = System.currentTimeMillis / 1000
		 	rdd.map(_._2).saveAsTextFile(destinationUrl+timestamp)
		}
    })

    
    ssc.checkpoint(destinationUrl+"__checkpoint")

    ssc.start()
    ssc.awaitTermination()


  }

}
