package nz.co.twg.erpfisuppliers.kafkaservice.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nz.co.twg.erpfisuppliers.kafkaservice.config.data.KafkaConsumerConfigData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.record.Record;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import static org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter.CONTEXT_RECORD;

@EnableKafka
@Configuration
@Setter
@Slf4j
public class KafkaConsumerConfig<K extends Serializable, V extends Record> {
    @Autowired(required = true)
    private KafkaConsumerConfigData kafkaConsumerConfigData;

    /* Kafka Configuration */
    @Bean
    public Map<String, Object> consumerConfigs() {
        log.info("kafka configuration started" + kafkaConsumerConfigData);
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerConfigData.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "${kafka-consumer-data.coupaSuppliersGroupId}");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
            kafkaConsumerConfigData.getMaxPollIntervalMs());
        props.put("security.protocol", kafkaConsumerConfigData.getProtocol());
        props.put("ssl.endpoint.identification.algorithm",
            kafkaConsumerConfigData.getAlgorithm());
        props.put("sasl.mechanism", kafkaConsumerConfigData.getMechanism());
        props.put("sasl.jaas.config", kafkaConsumerConfigData.getJaasConfig());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        log.info("kafka configuration ended");
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
            kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setRetryTemplate(retryTemplate());
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.setRecoveryCallback(context -> {
            ConsumerRecord consumerRecord = (ConsumerRecord) context.getAttribute(CONTEXT_RECORD);
            ProducerRecord<String, String> record = new ProducerRecord<>(kafkaConsumerConfigData.getCoupaSuppliersTopicName(), consumerRecord.value().toString());
            kafkaTemplate().send(record);
            return Optional.empty();
        });
        return factory;
    }

    private RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(getSimpleRetryPolicy());
        return retryTemplate;
    }

    private SimpleRetryPolicy getSimpleRetryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
        exceptionMap.put(IllegalArgumentException.class, false);
        exceptionMap.put(TimeoutException.class, true);
        return new SimpleRetryPolicy(kafkaConsumerConfigData.getMaxAttempts(), exceptionMap, true);
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }
}
