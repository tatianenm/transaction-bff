package br.com.coffeeandit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(of = {"uuid", "situacaoEnum"})
@Schema(description = "Objeto de transporte para o envio de uma promessa de transação")
public class RequestTransactionDTO extends TransactionDTO {

    @JsonIgnore
    private SituacaoEnum situacaoEnum;
    @JsonIgnore
    private LocalDateTime data;

}
