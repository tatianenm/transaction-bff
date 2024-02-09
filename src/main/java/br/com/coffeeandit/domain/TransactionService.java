package br.com.coffeeandit.domain;

import br.com.coffeeandit.dto.RequestTransactionDTO;
import br.com.coffeeandit.dto.TransactionDTO;
import br.com.coffeeandit.redis.TransactionRedisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    private TransactionRedisRepository transactionRedisRepository;


    public TransactionService(TransactionRedisRepository transactionRedisRepository) {
        this.transactionRedisRepository = transactionRedisRepository;
    }

    @Transactional
    public Optional<TransactionDTO> save(final RequestTransactionDTO requestTransactionDTO) {
        requestTransactionDTO.setData(LocalDateTime.now());
        return Optional.of(transactionRedisRepository.save(requestTransactionDTO));
    }

    public Optional<TransactionDTO> findById(final String id) {
        return transactionRedisRepository.findById(id);
    }
}
