package br.com.coffeeandit.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestTransactionDTO extends TransactionDTO{

    private SituacaoEnum situacaoEnum;

    private LocalDateTime data;

}
