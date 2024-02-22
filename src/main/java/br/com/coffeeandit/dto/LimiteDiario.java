package br.com.coffeeandit.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class LimiteDiario {

    private Long id;

    private Long agencia;

    private Long conta;

    private BigDecimal valor;

    private LocalDate data;

}
