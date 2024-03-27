package br.com.coffeeandit.api;

import br.com.coffeeandit.domain.TransactionService;
import br.com.coffeeandit.dto.RequestTransactionDTO;
import br.com.coffeeandit.dto.TransactionDTO;
import br.com.coffeeandit.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@Tag(name = "/transaction", description = "Grupo de API's para manipulação de transações financeiras")
public class TransactionController {
   /* @Value("${transacoes.duration}")
    private Long duration; config cloud

    @Value("${transacoes.events}")
    private String events;*/
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(description = "API para criar uma transação financeira")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Retorno ok com a transação criada."),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RequestTransactionDTO> enviarTransacao(@RequestBody final RequestTransactionDTO requestTransactionDTO) {
        return transactionService.save(requestTransactionDTO);

    }

    @Operation(description = "API para buscar as transações persistidas por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno ok da lista de transações"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> buscarTransacao(@PathVariable("id") final String uuid) {
        final Optional<TransactionDTO> transactionDTO = transactionService.findById(uuid);
        System.out.println("************************ESTOU AQUI");
        if (transactionDTO.isPresent()) {
            return Mono.just(transactionDTO.get());
        }
        throw new NotFoundException("Unable to find resource");
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
    @PatchMapping(value = "/{id}/confirmar")
    public Mono<TransactionDTO> confirmarTransação(@PathVariable("id") final String uuid) {
        return Mono.empty();
    }


    @GetMapping(value = "/{agencia}/{conta}")
    public Flux<List<TransactionDTO>> buscarTransações(@PathVariable("agencia") final Long agencia,
                                                       @PathVariable("conta") final Long conta) {
        return transactionService.findByAgenciaAndContaFlux(agencia, conta);

    }

    @GetMapping(value = "/sse/{agencia}/{conta}")
    public Flux<ServerSentEvent<List<TransactionDTO>>> buscarTransaçõesSSE(@PathVariable("agencia") final Long agencia,
                                                                           @PathVariable("conta") final Long conta) {
        return Flux.interval(Duration.ofSeconds(2))
       // return Flux.interval(Duration.ofSeconds(duration)) alteracao feita p/config server cloud
                .map(sequence -> ServerSentEvent.<List<TransactionDTO>>builder()
                        .id(String.valueOf(sequence))
                        .event("transações")
                       // .event(events)
                        .data(transactionService.findByAgenciaAndConta(agencia, conta))
                        .retry(Duration.ofSeconds(1))
                        .build());

    }


}
