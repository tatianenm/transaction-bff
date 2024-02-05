package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class BeneficiarioDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2468000536336261482L;
    @Schema(description = "CPF do beneficiário")
    @NotNull(message = "Informar o cpf")
    private Long cpf;
    @Schema(description = "Código do banco destino")
    @NotNull(message = "Informar o código do banco de destino")
    private Long codigoBanco;
    @Schema(description = "Agência de destino")
    @NotNull(message = "Informar a agência de destino")
    private String agencia;
    @Schema(description = "Conta de destino")
    @NotNull(message = "Informar conta de destino")
    private String conta;
    @Schema(description = "Nome do favorecido")
    @NotNull(message = "Informar o nome do favorecido")
    private  String nomeFavorecido;
}
