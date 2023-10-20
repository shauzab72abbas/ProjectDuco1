package nz.co.twg.erpfisuppliers.kafkaservice.config.data;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-data")
public class KafkaConsumerConfigData {

    /*
    *Data used in Kafka configuration
    *
    */
    private String bootstrapServers;
    private String algorithm;
    private String protocol;
    private String mechanism;
    private String jaasConfig;
    private String sourceCredentials;
    private String userInfo;
    private boolean compatibility;
    private boolean latestVersion;
    private int maxAttempts;
    private int maxPollIntervalMs;
    private int maxPollRecords;
    private int retryBackOffMsConfig;
    private int replicationFactor;
    private int reconnectBackoffMs;
    private String keyDeserializer;
    private String valueDeserializer;
    private boolean autoOffset;
    private boolean batchListener;
    private boolean autoStartup;
    private int concurrencyLevel;
    private int sessionTimeOut;
    private int heartbeatIntervalMs;
    private int pollTimeOutMs;
    private int initialIntervalMs;
    private int maxIntervalMs;
    private double multipliar;
    private int sleepTimeMs;
    private String coupaSuppliersTopicName;
    private String coupaSuppliersGroupId;
}
