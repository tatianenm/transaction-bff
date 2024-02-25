package br.com.coffeeandit.domain;

import br.com.coffeeandit.dto.LimiteDiario;
import br.com.coffeeandit.feign.LimiteClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public LimiteDiario buscarLimiteDiario(final Long agencia, final Long conta){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        var limiteDiarioSup = fallback(agencia, conta);
        //countCircuitBreaker.decorateSupplier(() -> limiteClient.buscarLimiteDiario(agencia, conta));

        return limiteDiarioSup.get();
    }

    private Supplier<LimiteDiario> fallback(final Long agencia, final Long conta){
        var limiteDiarioSup = countCircuitBreaker.decorateSupplier(() -> limiteClient.buscarLimiteDiario(agencia, conta));
        
        return Decorators
                .ofSupplier(limiteDiarioSup)
                .withCircuitBreaker(countCircuitBreaker)
                .withFallback(Arrays.asList(CallNotPermittedException.class),
                        e -> this.getStaticLimit())
                .decorate();

    }

    private LimiteDiario getStaticLimit() {
        LimiteDiario limiteDiario = new LimiteDiario();
        limiteDiario.setValor(BigDecimal.ZERO);
        return limiteDiario;
    }

}
