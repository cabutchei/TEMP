package br.gov.caixa.silce.dominio.servico.apimanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Decimal;

public final class RetornoResultadoConcursoAPIManager extends SaidaHttpAPIManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer numeroConcurso;
	private Modalidade modalidade;
	private String nomeMunicipioUFSorteio;
	private String dataApuracao;
	private Decimal valorArrecadado;
	private Decimal valorEstimadoProximoConcurso;
	private Decimal valorAcumuladoProximoConcurso;
	private Decimal valorAcumuladoConcursoEspecial;
	private Decimal valorAcumuladoConcurso_0_5;
	private Boolean acumulado;
	private Integer indicadorConcursoEspecial;
	private List<Integer> dezenasSorteadasOrdemSorteio = new ArrayList<Integer>();
	private List<EquipeEsportiva> listaResultadoEquipeEsportiva = new ArrayList<EquipeEsportiva>();
	private List<Integer> listaResultadoResumoLoteca = new ArrayList<Integer>();
	private Integer numeroJogo;
	private String nomeTimeCoracaoMesSorte;
	private Integer tipoPublicacao;
	private String observacao;
	private String localSorteio;
	private String dataProximoConcurso;
	private Integer numeroConcursoAnterior;
	private Integer numeroConcursoProximo;
	private Integer valorTotalPremioFaixaUm;
	private Integer numeroConcursoFinal_0_5;
	private Integer numeroAcertoGanhador;
	private List<MunicipioUFGanhador> listaMunicipioUFGanhadores = new ArrayList<MunicipioUFGanhador>();;
	private List<RateioPremio> listaRateioPremio = new ArrayList<RateioPremio>();
	private List<Integer> listaDezenas = new ArrayList<Integer>();
	private List<Integer> listaDezenasSegundoSorteio = new ArrayList<Integer>();

	public Integer getNumeroConcurso() {
		return numeroConcurso;
	}
	public void setNumeroConcurso(Integer numeroConcurso) {
		this.numeroConcurso = numeroConcurso;
	}
	public Modalidade getModalidade() {
		return modalidade;
	}
	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public String getNomeMunicipioUFSorteio() {
		return nomeMunicipioUFSorteio;
	}

	public void setNomeMunicipioUFSorteio(String nomeMunicipioUFSorteio) {
		this.nomeMunicipioUFSorteio = nomeMunicipioUFSorteio;
	}

	public String getDataApuracao() {
		return dataApuracao;
	}

	public void setDataApuracao(String dataApuracao) {
		this.dataApuracao = dataApuracao;
	}

	public Decimal getValorArrecadado() {
		return valorArrecadado;
	}

	public void setValorArrecadado(Decimal valorArrecadado) {
		this.valorArrecadado = valorArrecadado;
	}

	public Decimal getValorEstimadoProximoConcurso() {
		return valorEstimadoProximoConcurso;
	}

	public void setValorEstimadoProximoConcurso(Decimal valorEstimadoProximoConcurso) {
		this.valorEstimadoProximoConcurso = valorEstimadoProximoConcurso;
	}

	public Decimal getValorAcumuladoProximoConcurso() {
		return valorAcumuladoProximoConcurso;
	}

	public void setValorAcumuladoProximoConcurso(Decimal valorAcumuladoProximoConcurso) {
		this.valorAcumuladoProximoConcurso = valorAcumuladoProximoConcurso;
	}

	public Decimal getValorAcumuladoConcursoEspecial() {
		return valorAcumuladoConcursoEspecial;
	}

	public void setValorAcumuladoConcursoEspecial(Decimal valorAcumuladoConcursoEspecial) {
		this.valorAcumuladoConcursoEspecial = valorAcumuladoConcursoEspecial;
	}

	public Decimal getValorAcumuladoConcurso_0_5() {
		return valorAcumuladoConcurso_0_5;
	}

	public void setValorAcumuladoConcurso_0_5(Decimal valorAcumuladoConcurso_0_5) {
		this.valorAcumuladoConcurso_0_5 = valorAcumuladoConcurso_0_5;
	}

	public Boolean getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(Boolean acumulado) {
		this.acumulado = acumulado;
	}

	public Integer getIndicadorConcursoEspecial() {
		return indicadorConcursoEspecial;
	}

	public void setIndicadorConcursoEspecial(Integer indicadorConcursoEspecial) {
		this.indicadorConcursoEspecial = indicadorConcursoEspecial;
	}

	public Integer getNumeroJogo() {
		return numeroJogo;
	}

	public void setNumeroJogo(Integer numeroJogo) {
		this.numeroJogo = numeroJogo;
	}

	public String getNomeTimeCoracaoMesSorte() {
		return nomeTimeCoracaoMesSorte;
	}

	public void setNomeTimeCoracaoMesSorte(String nomeTimeCoracaoMesSorte) {
		this.nomeTimeCoracaoMesSorte = nomeTimeCoracaoMesSorte;
	}

	public Integer getTipoPublicacao() {
		return tipoPublicacao;
	}

	public void setTipoPublicacao(Integer tipoPublicacao) {
		this.tipoPublicacao = tipoPublicacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getLocalSorteio() {
		return localSorteio;
	}

	public void setLocalSorteio(String localSorteio) {
		this.localSorteio = localSorteio;
	}

	public String getDataProximoConcurso() {
		return dataProximoConcurso;
	}

	public void setDataProximoConcurso(String dataProximoConcurso) {
		this.dataProximoConcurso = dataProximoConcurso;
	}

	public Integer getNumeroConcursoAnterior() {
		return numeroConcursoAnterior;
	}

	public void setNumeroConcursoAnterior(Integer numeroConcursoAnterior) {
		this.numeroConcursoAnterior = numeroConcursoAnterior;
	}

	public Integer getNumeroConcursoProximo() {
		return numeroConcursoProximo;
	}

	public void setNumeroConcursoProximo(Integer numeroConcursoProximo) {
		this.numeroConcursoProximo = numeroConcursoProximo;
	}

	public Integer getValorTotalPremioFaixaUm() {
		return valorTotalPremioFaixaUm;
	}

	public void setValorTotalPremioFaixaUm(Integer valorTotalPremioFaixaUm) {
		this.valorTotalPremioFaixaUm = valorTotalPremioFaixaUm;
	}

	public Integer getNumeroConcursoFinal_0_5() {
		return numeroConcursoFinal_0_5;
	}

	public void setNumeroConcursoFinal_0_5(Integer numeroConcursoFinal_0_5) {
		this.numeroConcursoFinal_0_5 = numeroConcursoFinal_0_5;
	}

	public Integer getNumeroAcertoGanhador() {
		return numeroAcertoGanhador;
	}

	public void setNumeroAcertoGanhador(Integer numeroAcertoGanhador) {
		this.numeroAcertoGanhador = numeroAcertoGanhador;
	}

	public List<Integer> getDezenasSorteadasOrdemSorteio() {
		return dezenasSorteadasOrdemSorteio;
	}

	public void setDezenasSorteadasOrdemSorteio(List<Integer> dezenasSorteadasOrdemSorteio) {
		this.dezenasSorteadasOrdemSorteio = dezenasSorteadasOrdemSorteio;
	}

	public List<Integer> getListaResultadoResumoLoteca() {
		return listaResultadoResumoLoteca;
	}

	public void setListaResultadoResumoLoteca(List<Integer> listaResultadoResumoLoteca) {
		this.listaResultadoResumoLoteca = listaResultadoResumoLoteca;
	}

	public List<Integer> getListaDezenas() {
		return listaDezenas;
	}

	public void setListaDezenas(List<Integer> listaDezenas) {
		this.listaDezenas = listaDezenas;
	}

	public List<Integer> getListaDezenasSegundoSorteio() {
		return listaDezenasSegundoSorteio;
	}

	public void setListaDezenasSegundoSorteio(List<Integer> listaDezenasSegundoSorteio) {
		this.listaDezenasSegundoSorteio = listaDezenasSegundoSorteio;
	}

	public List<EquipeEsportiva> getListaResultadoEquipeEsportiva() {
		return listaResultadoEquipeEsportiva;
	}

	public void setListaResultadoEquipeEsportiva(List<EquipeEsportiva> listaResultadoEquipeEsportiva) {
		this.listaResultadoEquipeEsportiva = listaResultadoEquipeEsportiva;
	}

	public List<MunicipioUFGanhador> getListaMunicipioUFGanhadores() {
		return listaMunicipioUFGanhadores;
	}

	public void setListaMunicipioUFGanhadores(List<MunicipioUFGanhador> listaMunicipioUFGanhadores) {
		this.listaMunicipioUFGanhadores = listaMunicipioUFGanhadores;
	}

	public List<RateioPremio> getListaRateioPremio() {
		return listaRateioPremio;
	}

	public void setListaRateioPremio(List<RateioPremio> listaRateioPremio) {
		this.listaRateioPremio = listaRateioPremio;
	}

}
