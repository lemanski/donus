package com.teste.donus.repository;

import com.teste.donus.model.Pessoa;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PessoaRepository extends PagingAndSortingRepository<Pessoa,Long> {

    Pessoa findByCpf(String cpf);
}
