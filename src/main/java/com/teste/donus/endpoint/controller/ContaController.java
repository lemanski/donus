package com.teste.donus.endpoint.controller;

import com.teste.donus.endpoint.service.ContaService;
import com.teste.donus.model.Conta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/conta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ContaController {

    private final ContaService contaService;

    @GetMapping("/lista")
    public ResponseEntity<Iterable<Conta>> list(Pageable pageable){
        return new ResponseEntity<>(contaService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/abrirConta")
    public ResponseEntity<?> abrirConta(@RequestBody Conta conta){
        contaService.save(conta);
        return ResponseEntity.ok("Conta aberta com sucesso sucesso!");
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> deposito(Double valor, String cpf){
        contaService.deposito(valor,cpf);
        return ResponseEntity.ok("Depósito realizado com sucesso!");
    }

    @PostMapping("/transferencia")
    public ResponseEntity<?> transferencia(Double valor, String cpfOrigem, String cpfDestino){
        contaService.transferencia(valor,cpfOrigem,cpfDestino);
        return ResponseEntity.ok("Transferencia realizado com sucesso!");
    }

}
