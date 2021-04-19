package com.teste.donus.repository;

import com.teste.donus.model.Conta;
import com.teste.donus.model.Pessoa;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContaRepository extends PagingAndSortingRepository<Conta, Long>{

    Conta findByCorrentista(Pessoa pessoa);
}
