package nz.co.twg.erpfisuppliers.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public Logger Slf4j() {
        Logger log = LoggerFactory.getLogger("KafkaConsumerConfig");
        return log;
    }
}
