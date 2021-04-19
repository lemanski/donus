package com.teste.donus.endpoint.service;

import com.teste.donus.core.CustomHandlerException;
import com.teste.donus.model.Conta;
import com.teste.donus.model.Pessoa;
import com.teste.donus.repository.ContaRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Service
public class ContaService {

	private final ContaRepository contaRepository;

	private final PessoaService pessoaService;
	
	public Iterable<Conta> findAll (Pageable pageable){
		log.info("Buscando todas as contas!");
		return contaRepository.findAll( pageable);
	}

	public Conta findByCpf(String cpf){
		log.info("Conta service: Buscando por cpf " + cpf);
		if(!cpf.isEmpty()){
			pessoaService.findByCpf(cpf);
		}
		return null;
	}

	public Conta save (Conta conta ) {
		log.info("Conta service: save");
		if(null != conta && null == conta.getId()){
			if(null != conta.getCorrentista() && null == conta.getCorrentista().getId()){
				if(null == findByCpf(conta.getCorrentista().getCpf())){
					pessoaService.save(conta.getCorrentista());
					return contaRepository.save(conta);
				}
				throw new CustomHandlerException("Não foi possível abrir a conta! Cpf Já cadastrado no sistema, somente é possível uma conta por cpf!");
			}
		}
		throw new CustomHandlerException("Não foi possível abrir a conta! Entrar em contato com suporte Técnico!");
	}

	@Scope("singleton")
	public void deposito(Double valor, String cpf) {
		if(valor < 0){
			throw new CustomHandlerException("Não foi possível realizar o deposito! Não é possível depositar valores negativos!");
		}
		if(!cpf.isEmpty()){
			Pessoa correntista = pessoaService.findByCpf(cpf);
			if(null == correntista) {
				throw new CustomHandlerException("Não foi possível realizar o deposito! Cpf informado não encontrado verifique!");
			}
			Conta conta = contaRepository.findByCorrentista(correntista);
			double saldo = conta.getSaldo();
			conta.setSaldo(saldo + valor);
			contaRepository.save(conta);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void transferencia(Double valor, String cpfOrigem, String cpfDestino) {
		if(valor < 0){
			throw new CustomHandlerException("Não foi possível realizar a transferencia! Não é possível fazer transferencia de valores negativos!");
		}
		if(valor > 2000){
			throw new CustomHandlerException("Não foi possível realizar a transferencia! Valor maximo de transferencia é 2000.00!");
		}
		if(cpfOrigem.isEmpty() || cpfDestino.isEmpty()){
			throw new CustomHandlerException("Não foi possível realizar a transferencia! É necessario informar o cpf destino e o de cpf origem!");
		}
		if(cpfDestino.equalsIgnoreCase(cpfOrigem)){
			log.info("Transferencia da mesma conta para a mesma conta :/");
		}

		retiraSaldo(valor, cpfOrigem);
		deposito(valor,cpfDestino);
	}

	private void retiraSaldo(Double valor, String cpfOrigem) {
		Pessoa correntista = pessoaService.findByCpf(cpfOrigem);
		Conta conta = contaRepository.findByCorrentista(correntista);
		double saldo = conta.getSaldo();
		double saldoAtualizado = saldo - valor;
		if(saldoAtualizado < 0){
			throw new CustomHandlerException("Não foi possível realizar a transferencia! Cpf origem não tem saldo suficiente!");
		}
		conta.setSaldo(saldoAtualizado);
		contaRepository.save(conta);
	}
}
