package br.gov.caixa.silce.dominio;

import java.io.Serializable;

import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Email;

public class UsuarioSicdu implements Serializable {

	private static final long serialVersionUID = 1L;

	private Email email;
	private CPF cpf;
	private String nome;
	private String nomeMae;
	private Data dataNascimento;

	public UsuarioSicdu() {
		//construtor vazio
	}
	
	public UsuarioSicdu(Email email, CPF cpf, String nome, String nomeMae, Data dataNascimento) {
		this.email = email;
		this.cpf = cpf;
		this.nome = nome;
		this.nomeMae = nomeMae;
		this.dataNascimento = dataNascimento;
	}
	
	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public CPF getCpf() {
		return cpf;
	}

	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Data getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Data dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
}
