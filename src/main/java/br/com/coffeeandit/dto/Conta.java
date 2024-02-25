package br.com.coffeeandit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
@Getter
@Setter
@ToString
public class Conta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1999195110107880979L;
    @Schema(description = "Código da agência")
    @NotNull(message = "Informar o código da agência")
    private Long codigoAgencia;
    @Schema(description = "Código da conta")
    @NotNull(message = "Informar o código da conta")
    private Long codigoConta;

}
