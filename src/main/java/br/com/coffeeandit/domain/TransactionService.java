package br.com.coffeeandit.domain;

import br.com.coffeeandit.dto.RequestTransactionDTO;
import br.com.coffeeandit.dto.TransactionDTO;
import br.com.coffeeandit.exception.UnauthorizedException;
import br.com.coffeeandit.redis.TransactionRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
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


    public TransactionService(TransactionRedisRepository transactionRedisRepository, RetryTemplate retryTemplate) {
        this.transactionRedisRepository = transactionRedisRepository;
        this.retryTemplate = retryTemplate;
    }

    @Transactional
    @Retryable(value = QueryTimeoutException.class, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public Optional<TransactionDTO> save(final RequestTransactionDTO requestTransactionDTO) {
        requestTransactionDTO.setData(LocalDateTime.now());
        log.info("Consultando o redis");
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
