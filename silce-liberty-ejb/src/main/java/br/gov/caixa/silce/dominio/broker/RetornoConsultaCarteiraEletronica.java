package br.gov.caixa.silce.dominio.broker;

import br.gov.caixa.dominio.SaidaBroker;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;

/**
 * Representa os dados da carteira eletrônica do apostador.
 *
 */
public class RetornoConsultaCarteiraEletronica extends SaidaBroker {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long nsu;
	
	private Data dataTransacao;
	
	private String status;
	
	private CPF cpf;	
	
	private Data dataNascimento;
	
	private String nome;

	public Long getNsu() {
		return nsu;
	}

	public void setNsu(Long nsu) {
		this.nsu = nsu;
	}

	public Data getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Data dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CPF getCpf() {
		return cpf;
	}

	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	public Data getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Data dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
