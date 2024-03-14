package br.com.coffeeandit.domain;

import br.com.coffeeandit.dto.LimiteDiario;
import br.com.coffeeandit.feign.LimiteClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Supplier;

@Service
public class LimiteService {

    LimiteClient limiteClient;
    @Autowired
    private CircuitBreaker countCircuitBreaker;

    public LimiteService(LimiteClient limiteClient) {
        this.limiteClient = limiteClient;
    }

    public Mono<Supplier<LimiteDiario>>  buscarLimiteDiario(final Long agencia, final Long conta){

       /* try {para testar circuit breaker
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
       // var limiteDiarioSup = fallback(agencia, conta);
        //countCircuitBreaker.decorateSupplier(() -> limiteClient.buscarLimiteDiario(agencia, conta));
        //return limiteDiarioSup.get();quando temos um get é bloqueante

        return buscarLimiteDiarioSupplier(agencia, conta);


    }

    private Mono<Supplier<LimiteDiario>> buscarLimiteDiarioSupplier(final Long agencia, final Long conta){
        var limiteDiarioSup = countCircuitBreaker.decorateSupplier(() ->
                limiteClient.buscarLimiteDiario(agencia, conta));
        
        return Mono.fromSupplier(() -> {
            return
                Decorators
                        .ofSupplier(limiteDiarioSup)
                        .withCircuitBreaker(countCircuitBreaker)
                        .withFallback(Arrays.asList(CallNotPermittedException.class),
                                e -> this.getStaticLimit())
                        .decorate();
        });


    }

    private LimiteDiario getStaticLimit() {
        LimiteDiario limiteDiario = new LimiteDiario();
        limiteDiario.setValor(BigDecimal.ZERO);
        return limiteDiario;
    }

}
