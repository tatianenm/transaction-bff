package br.com.coffeeandit.config;

import br.com.coffeeandit.dto.RequestTransactionDTO;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;
import java.util.Objects;

@Configuration
public class ReactiveKafkaProducerConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, RequestTransactionDTO> reactiveKafkaProducerTemplate(
            final KafkaProperties kafkaProperties){
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<String, RequestTransactionDTO>(SenderOptions.create(properties));
    }
}
