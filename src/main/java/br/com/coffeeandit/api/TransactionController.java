package br.com.coffeeandit.api;

import br.com.coffeeandit.domain.TransactionService;
import br.com.coffeeandit.dto.RequestTransactionDTO;
import br.com.coffeeandit.dto.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@Tag(name = "/transaction", description = "Grupo de API's para manipulação de transações financeiras")
public class TransactionController {

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    private TransactionService transactionService;



    @Operation(description = "API para criar uma transação financeira")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Retorno ok com a transação criada."),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> enviarTransacao(@RequestBody final RequestTransactionDTO requestTransactionDTO) {
        final Optional<TransactionDTO> transactionDTO = transactionService.save(requestTransactionDTO);
        if (transactionDTO.isPresent()) {
            return Mono.just(transactionDTO.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
    }

    @Operation(description = "API para buscar as transações persistidas por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno ok da lista de transações"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> buscarTransacao(@PathVariable("id") final String uuid) {
        final Optional<TransactionDTO> transactionDTO = transactionService.findById(uuid);
        System.out.println("************************ESTOU AQUI");
        if (transactionDTO.isPresent()) {
            return Mono.just(transactionDTO.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
    }

    @Operation(description = "API para remover as transações persistidas")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Retorno ok da remoção"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> removerTransacao(@PathVariable("id") final String uuid) {
        return Mono.empty();
    }

    @Operation(description = "API para autorizar a transação financeira")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno ok da confirmação"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @PatchMapping(value = "/{id}/confirmar", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> confirmarTransação(@PathVariable("id") final String uuid) {
        return Mono.empty();
    }

}
