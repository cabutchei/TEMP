package br.gov.caixa.silce.dominio.servico.pix;

import br.gov.caixa.dominio.EntradaHttp;

public class EntradaCriaCobranca implements EntradaHttp<RetornoCobranca> {

	private static final long serialVersionUID = 1L;

	private String chave;
	private Valor valor;
	private Calendario calendario;
	private Devedor devedor;

	public EntradaCriaCobranca(String chave, Valor valor, Calendario calendario, Devedor devedor) {
		super();
		this.chave = chave;
		this.valor = valor;
		this.calendario = calendario;
		this.devedor = devedor;
	}

	public static class Valor {

		private String original;
		private Integer modalidadeAlteracao;

		public Valor(String original, Integer modalidadeAlteracao) {
			super();
			this.original = original;
			this.modalidadeAlteracao = modalidadeAlteracao;
		}

		public String getOriginal() {
			return original;
		}

		public void setOriginal(String original) {
			this.original = original;
		}

		public Integer getModalidadeAlteracao() {
			return modalidadeAlteracao;
		}

		public void setModalidadeAlteracao(Integer modalidadeAlteracao) {
			this.modalidadeAlteracao = modalidadeAlteracao;
		}
	}
	
	public static class Calendario {
		private Long expiracao;

		public Calendario(Long expiracao) {
			super();
			this.expiracao = expiracao;
		}

		public Long getExpiracao() {
			return expiracao;
		}

		public void setExpiracao(Long expiracao) {
			this.expiracao = expiracao;
		}
	}
	
	public static class Devedor {
		private String cpf;
		private String nome;

		public Devedor(String cpf, String nome) {
			super();
			this.cpf = cpf;
			this.nome = nome;
		}

		public String getCpf() {
			return cpf;
		}

		public void setCpf(String cpf) {
			this.cpf = cpf;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}
		
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Valor getValor() {
		return valor;
	}

	public void setValor(Valor valor) {
		this.valor = valor;
	}

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	public Devedor getDevedor() {
		return devedor;
	}

	public void setDevedor(Devedor devedor) {
		this.devedor = devedor;
	}
}
