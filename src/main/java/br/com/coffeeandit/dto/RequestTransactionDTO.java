package br.com.coffeeandit.dto;

import java.time.LocalDateTime;


public class RequestTransactionDTO extends TransactionDTO{

    private SituacaoEnum situacaoEnum;

    private LocalDateTime data;

}
