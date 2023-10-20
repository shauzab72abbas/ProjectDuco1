package nz.co.twg.erpfisuppliers.kafkaservice.impl;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

public interface IConsumerService {

    public void receive(@Payload String data, @Headers MessageHeaders messageHeaders);
}
