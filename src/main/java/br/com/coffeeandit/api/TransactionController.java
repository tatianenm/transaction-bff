package br.com.coffeeandit.api;

import br.com.coffeeandit.dto.RequestTransactionDTO;
import br.com.coffeeandit.dto.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Operation(description = "API para criar uma transação financeira")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Retorno ok com a transação criada."),
                           @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
                           @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
                           @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> enviarTransacao(@RequestBody final RequestTransactionDTO requestTransactionDTO){

        return Mono.empty();
    }
    @Operation(description = "API para buscar as transações persistidas por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno ok da lista de transações"),
                           @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
                           @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
                           @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
                                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> buscarTransacao(@PathVariable("id") final String uuid){

        return Mono.empty();
    }
    @Operation(description = "API para remover as transações persistidas")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Retorno ok da remoção"),
                           @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
                           @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
                           @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
                                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> removerTransacao(@PathVariable("id") final String uuid){

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
    public Mono<TransactionDTO> confirmarTransação(@PathVariable("id") final String uuid){
        return Mono.empty();
    }

}
