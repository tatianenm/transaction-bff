package br.com.coffeeandit.redis;

import br.com.coffeeandit.dto.TransactionDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRedisRepository extends CrudRepository<TransactionDTO, String> {
}
