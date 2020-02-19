package fr.polytech.al.tfc.rollinghistory.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    private static final String app = "kafka-account";
    @Bean
    public KafkaProducer<String,String> producer(@Value("${kafkabroker}") String kafkaBrokers){
        Map<String, Object> config = new HashMap<>();
        config.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);

        StringSerializer deserializer = new StringSerializer();

        return new KafkaProducer<>(config, deserializer, deserializer);
    }
}
