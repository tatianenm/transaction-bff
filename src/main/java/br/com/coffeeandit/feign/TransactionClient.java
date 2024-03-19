package br.com.coffeeandit.feign;

import br.com.coffeeandit.dto.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "transaction", url = "${transaction.url}")
public interface TransactionClient {
    @RequestMapping(path = "/transaction/{agencia}/{conta}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    List<TransactionDTO> buscarTransacoes(@PathVariable("agencia") final Long agencia,
                                          @PathVariable("conta") final Long conta);
}
