package com.teste.donus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.donus.model.Conta;
import com.teste.donus.model.Pessoa;
import com.teste.donus.repository.ContaRepository;
import com.teste.donus.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class DonusApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testAbrirConta() throws Exception{
		Pessoa correntista = new Pessoa();
		correntista.setCpf("020202020202");
		correntista.setNomeCompleto("Correntista teste");
		Conta conta = new Conta();
		conta.setSaldo(1000);
		conta.setCorrentista(correntista);

		mockMvc.perform(post("/v1/conta/abrirConta")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(conta)))
				.andExpect(status().isOk());
	}

	@Test
	void testDepositar() throws Exception{
		String cpf = "032032115484";
		String nomeCompleto = "Teste Deposito";
		Double saldo = 0D;
		criaConta(nomeCompleto, cpf, saldo);
		mockMvc.perform(post("/v1/conta/deposito")
				.param("valor", "100")
				.param("cpf", cpf)).andExpect(status().isOk());
	}

	Conta criaConta(String nomeCompleto, String cpf, Double saldo) {
		Pessoa correntista = criaCorrentista(nomeCompleto,cpf);
		Conta conta = new Conta();
		conta.setCorrentista(correntista);
		conta.setSaldo(saldo);
		return contaRepository.save(conta);
	}

	Pessoa criaCorrentista(String nomeCompleto, String cpf){
		Pessoa pessoa = new Pessoa();
		pessoa.setNomeCompleto(nomeCompleto);
		pessoa.setCpf(cpf);
		return pessoaRepository.save(pessoa);
	}
}
