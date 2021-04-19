package com.teste.donus.model.validators;

import com.teste.donus.core.CustomHandlerException;
import com.teste.donus.model.Pessoa;

public class PessoaValidator {

    private Pessoa pessoa;

    public PessoaValidator(Pessoa pessoa){
        this.pessoa = pessoa;
    }

    public void ValidatorPessoa(){
        if(pessoa.getCpf().isEmpty()){
            throw new CustomHandlerException("Não é possível salvar o registro! Campo CPF não pode ser Vazio!");
        }
        if(pessoa.getNomeCompleto().isEmpty()){
            throw new CustomHandlerException("Não é possível salvar o registro! parametro nome completo está vazio! ");
        }
        if(pessoa.getCpf().isEmpty()){
            throw new CustomHandlerException("Não é possível salvar o registro! parametro cpf está vazio! ");

        }
    }
}
