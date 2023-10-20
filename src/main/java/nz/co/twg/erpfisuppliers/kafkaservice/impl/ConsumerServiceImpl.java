package nz.co.twg.erpfisuppliers.kafkaservice.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Setter
@Slf4j
public class ConsumerServiceImpl implements IConsumerService {

    @Autowired(required = true)
    private IKafkaConsumerDataReader paymentReader;

    /* Reading message from kafka based on topic */

    @KafkaListener(
            topics = "${kafka-consumer-data.coupaSuppliersTopicName}",
            groupId = "${kafka-consumer-data.coupaSuppliersGroupId}")
    public void receive(@Payload String data, @Headers MessageHeaders messageHeaders) {
        log.info("Reading payload" +data);
        log.info("Reading message from kafka on given topic started");
        paymentReader.dataReader(data);
        log.info("Reading message from kafka on given topic ended");
    }
}
