package br.com.coffeeandit.domain;

import br.com.coffeeandit.dto.RequestTransactionDTO;
import br.com.coffeeandit.dto.TransactionDTO;
import br.com.coffeeandit.exception.UnauthorizedException;
import br.com.coffeeandit.redis.TransactionRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {

    private TransactionRedisRepository transactionRedisRepository;
    private RetryTemplate retryTemplate;
    private ReactiveKafkaProducerTemplate<String, RequestTransactionDTO> reactiveKafkaProducerTemplate;


    public TransactionService(TransactionRedisRepository transactionRedisRepository, RetryTemplate retryTemplate,
                              ReactiveKafkaProducerTemplate<String, RequestTransactionDTO> reactiveKafkaProducerTemplate) {
        this.transactionRedisRepository = transactionRedisRepository;
        this.retryTemplate = retryTemplate;
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    @Value("${app.topic}")
    private String topic;

    @Transactional
    @Retryable(value = QueryTimeoutException.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public Optional<TransactionDTO> save(final RequestTransactionDTO requestTransactionDTO) {
        requestTransactionDTO.setData(LocalDateTime.now());
        log.info("Consultando o redis");
        reactiveKafkaProducerTemplate.send(topic, requestTransactionDTO)
                .doOnSuccess(voidSenderResult -> log.info(voidSenderResult.toString()))
                .subscribe();
        return Optional.of(transactionRedisRepository.save(requestTransactionDTO));
    }

   /* @Retryable(value = QueryTimeoutException.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public Optional<TransactionDTO> findById(final String id) {
        *//*if(id.equals("2")){
            throw new UnauthorizedException("Esta é uma aula de controle de exceções.");
        }*//*
        log.info("Consultando o redis");
        return transactionRedisRepository.findById(id);
    }*/


    public Optional<TransactionDTO> findById(final String id) {
        /*if(id.equals("2")){
            throw new UnauthorizedException("Esta é uma aula de controle de exceções.");
        }*/
        return retryTemplate.execute(ret ->{
            log.info("Consultando o redis");
           return transactionRedisRepository.findById(id);
        });
    }
}
