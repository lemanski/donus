package com.teste.donus.endpoint.service;

import com.teste.donus.core.CustomHandlerException;
import com.teste.donus.model.Pessoa;
import com.teste.donus.model.validators.PessoaValidator;
import com.teste.donus.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public Pessoa save (Pessoa pessoa){
        new PessoaValidator(pessoa).ValidatorPessoa();
        return pessoaRepository.save(pessoa);
    }

    public Pessoa findByCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf);
    }
}
