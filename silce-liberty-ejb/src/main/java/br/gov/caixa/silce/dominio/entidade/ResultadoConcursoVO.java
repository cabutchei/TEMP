package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.gov.caixa.silce.dominio.jogos.Concurso;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesLoteca;
import br.gov.caixa.silce.dominio.jogos.PalpitesLotogol;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.silce.dominio.servico.apimanager.MunicipioUFGanhador;
import br.gov.caixa.silce.dominio.servico.apimanager.RateioPremio;
import br.gov.caixa.silce.dominio.servico.apimanager.RetornoResultadoConcursoAPIManager;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.StringUtil;

public class ResultadoConcursoVO implements Serializable {
	private static final int FIM_PRIMEIRO_SORTEIO = 6 * 3;
	private static final long serialVersionUID = 1L;
	private static final String MARCO = "Marco";
	private String municipioSorteio;
	private String ufSorteio;
	private String aviso = "";
	private Decimal arrecadacaoTotal;
	private Decimal acumuladoParaProximoConcurso;
	private Boolean acumulou = false;
	private List<?> numerosSorteadosPrimeiroSorteio = new ArrayList<Integer>();
	private List<Integer> numerosSorteadosSegundoSorteio = new ArrayList<Integer>();
	private List<Integer> trevosSorteadosPrimeiroSorteio = new ArrayList<Integer>();
	private PalpitesLotogol partidasLotogol = null;
	private PalpitesLoteca partidasLoteca = null;
	private Integer tipoPublicacao;
	private Concurso concurso;
	private Concurso proximoConcurso;
	private Concurso proximoConcursoFinalZero = null;
	private Concurso proximoConcursoFinalCinco = null;
	private Concurso proximoConcursoEspecial;
	private String[] premiacoesPrimeiroSorteio;
	private String premiacaoTimeDoCoracao = null;
	private String premiacaoMesDeSorte = null;
	private String[] ganhadoresPrimeiroSorteio;
	private String ufTimeCoracao;

	public static ResultadoConcursoVO fromEntidade(ResultadoConcurso res) {
		return new ResultadoConcursoVO(res);
	}

	public ResultadoConcursoVO(ResultadoConcurso res) {
		Modalidade modAtual = res.getModalidade();
		// Verificar se concurso é dupla sena e se houve segundo sorteio
		if (modAtual.isDuplaSena()) {
			String primeiro = res.getNumerosSorteadosSorteio().substring(0, FIM_PRIMEIRO_SORTEIO);
			numerosSorteadosPrimeiroSorteio = ApostaUtil.getIntListPrognosticos(primeiro);
			String segundo = res.getNumerosSorteadosSorteio().substring(FIM_PRIMEIRO_SORTEIO, res.getNumerosSorteadosSorteio().length());
			numerosSorteadosSegundoSorteio = ApostaUtil.getIntListPrognosticos(segundo);
		} else if (modAtual.isSuperSete()) {
			numerosSorteadosPrimeiroSorteio = montaMatrizSuperSete(res);
		} else if (modAtual.isMaisMilionaria()) {
			montaMaisMilionaria(res);
		} else {
			numerosSorteadosPrimeiroSorteio = ApostaUtil.getIntListPrognosticos(res.getNumerosSorteadosSorteio());
		}
		String[] obj = res.getMunicipiosSorteados().split("/");
		ganhadoresPrimeiroSorteio = Arrays.copyOfRange(obj, 1, obj.length);
		obj = res.getFaixas().split("/");
		premiacoesPrimeiroSorteio = Arrays.copyOfRange(obj, 1, obj.length);
		if (res.getMunicipioSorteio() != null && res.getMunicipioSorteio().contains("/")) {
			municipioSorteio = res.getMunicipioSorteio().split("/")[0].trim();
			ufSorteio = res.getMunicipioSorteio().split("/")[1].trim();
		} else {
			municipioSorteio = res.getMunicipioSorteio() != null ? res.getMunicipioSorteio() : "";
			ufSorteio = "";
		}
		ufSorteio = res.getMunicipioSorteio().split("/")[1].trim();
		arrecadacaoTotal = res.getArrecadacaoTotal();
		acumuladoParaProximoConcurso = res.getAcumuladoParaProximoConcurso();
		if (Integer.parseInt(res.getIcConcurso()) != 2) {
			Integer ganhadores = Integer.parseInt(premiacoesPrimeiroSorteio[0].split(";")[1]);
			acumulou = ganhadores == 0;
		}

		tipoPublicacao = getTipoPublicacao();

		concurso = new Concurso(res.getModalidade(), TipoConcurso.getByCodigo(res.getIcConcurso().toCharArray()[0]), res.getConcurso(), null, null, res.getDataApuracao(), null,
			null, null, null, null, null);


		proximoConcurso = new Concurso(res.getModalidade(), null, null, null, null, null, null, null, null, null, res.getAcumuladoParaProximoConcurso(), null);
		// Verificar se o proximo concurso possui final 0 ou 5
		Integer numero = res.getConcurso() + (5 - (res.getConcurso() % 5));
		if (res.getConcurso() % 10 > 4) {
			proximoConcursoFinalZero = new Concurso(res.getModalidade(), null, numero, null, null, null, null, null, null, null, res.getAcumuladoParaProximoFinalZeroOuCinco(),
				null);
		} else {
			proximoConcursoFinalCinco = new Concurso(res.getModalidade(), null, numero, null, null, null, null, null, null, null, res.getAcumuladoParaProximoFinalZeroOuCinco(),
				null);
		}
		proximoConcursoEspecial = new Concurso(res.getModalidade(), null, null, null, null, null, null, null, null, null, res.getAcumuladoParaConcursoEspecial(), null);
		if (Modalidade.TIMEMANIA.equals(modAtual)) {
			premiacaoTimeDoCoracao = res.getTimeCoracaoOuMesSorte();
			ufTimeCoracao = res.getUfTimeCoracao();
		} else if (Modalidade.DIA_DE_SORTE.equals(modAtual)) {
			premiacaoMesDeSorte = res.getTimeCoracaoOuMesSorte();
			premiacaoMesDeSorte = MARCO.equals(premiacaoMesDeSorte.trim()) ? premiacaoMesDeSorte.replace("c", "ç") : premiacaoMesDeSorte;
		} else if (Modalidade.LOTECA.equals(modAtual)) {
			numerosSorteadosPrimeiroSorteio = Collections.emptyList();
			partidasLoteca = new PalpitesLoteca(res.getNumerosSorteadosSorteio());
		} else if (Modalidade.LOTOGOL.equals(modAtual)) {
			numerosSorteadosPrimeiroSorteio = Collections.emptyList();
			partidasLotogol = new PalpitesLotogol(res.getNumerosSorteadosSorteio());
		}
	}

	public ResultadoConcursoVO(RetornoResultadoConcursoAPIManager res) {
		Modalidade modalidade = res.getModalidade();
		if (modalidade.isDuplaSena()) {
			numerosSorteadosPrimeiroSorteio = res.getListaDezenas();
			numerosSorteadosSegundoSorteio = res.getListaDezenasSegundoSorteio();
		} else if (modalidade.isSuperSete()) {
			numerosSorteadosPrimeiroSorteio = montaMatrizSuperSete(res);
		} else if (modalidade.isMaisMilionaria()) {
			montaMaisMilionariaAPIManager(res);
		} else {
			numerosSorteadosPrimeiroSorteio = res.getListaDezenas();
		}
		ganhadoresPrimeiroSorteio = formataPremiacoesPrimeiroSorteio(res.getListaMunicipioUFGanhadores());
		premiacoesPrimeiroSorteio = formataFaixas(res.getListaRateioPremio());
		municipioSorteio = res.getNomeMunicipioUFSorteio().substring(0, res.getNomeMunicipioUFSorteio().indexOf(','));
		ufSorteio = res.getNomeMunicipioUFSorteio().substring(res.getNomeMunicipioUFSorteio().indexOf(',') + 1).trim();
		arrecadacaoTotal = res.getValorArrecadado();
		acumuladoParaProximoConcurso = res.getValorEstimadoProximoConcurso();
		acumulou = res.getAcumulado();
		tipoPublicacao = res.getTipoPublicacao();

		concurso = new Concurso(res.getModalidade(), TipoConcurso.getByCodigo(res.getIndicadorConcursoEspecial().toString().toCharArray()[0]), res.getNumeroConcurso(), null, null,
			new Data(res.getDataApuracao(), DataUtil.DD_MM_YYYY), null, null, null, null, null, null);

		proximoConcurso = new Concurso(res.getModalidade(), null, null, null, null, null, null, null, null, null, res.getValorEstimadoProximoConcurso(), null);

		Integer numero = res.getNumeroConcurso() + (5 - (res.getNumeroConcurso() % 5));
		if (res.getNumeroConcurso() % 10 > 4) {
			proximoConcursoFinalZero = new Concurso(res.getModalidade(), null, numero, null, null, null, null, null, null, null, res.getValorAcumuladoConcurso_0_5(), null);
		} else {
			proximoConcursoFinalCinco = new Concurso(res.getModalidade(), null, numero, null, null, null, null, null, null, null, res.getValorAcumuladoConcurso_0_5(), null);
		}

		proximoConcursoEspecial = new Concurso(res.getModalidade(), null, null, null, null, null, null, null, null, null, res.getValorAcumuladoConcursoEspecial(), null);
		if (modalidade.isTimemania()) {
			premiacaoTimeDoCoracao = res.getNomeTimeCoracaoMesSorte().substring(0, res.getNomeTimeCoracaoMesSorte().indexOf('/') - 1).trim();
			ufTimeCoracao = res.getNomeTimeCoracaoMesSorte().substring(res.getNomeTimeCoracaoMesSorte().indexOf('/') + 1, res.getNomeTimeCoracaoMesSorte().length()).trim();
		} else if (modalidade.isDiaDeSorte()) {
			premiacaoMesDeSorte = res.getNomeTimeCoracaoMesSorte();
			premiacaoMesDeSorte = MARCO.equals(premiacaoMesDeSorte.trim()) ? premiacaoMesDeSorte.replace("c", "ç") : premiacaoMesDeSorte;
		} else if (modalidade.isLoteca()) {
			numerosSorteadosPrimeiroSorteio = Collections.emptyList();
			List<Integer> resultadoResumo = res.getListaResultadoResumoLoteca();
			StringBuilder builder = new StringBuilder();
			int i = 0;
			for (Integer inteiro : resultadoResumo) {
				int x = inteiro;
				if (inteiro == 2) {
					x = 3;
				} else if (inteiro == 0) {
					x = 2;
				}
				builder.append(StringUtil.completeAEsquerda(Integer.valueOf(3 * i + x).toString(), 3, '0'));
				i++;
			}
			partidasLoteca = new PalpitesLoteca(builder.toString());
		} else if (modalidade.isLotogol()) {
			numerosSorteadosPrimeiroSorteio = Collections.emptyList();
			partidasLotogol = new PalpitesLotogol(new String());
		}
	}

	private String[] formataFaixas(List<RateioPremio> listaPremio) {
		if (listaPremio == null || listaPremio.isEmpty()) {
			return null;
		} else {
			String[] retorno = new String[listaPremio.size()];
			for (int i = 0; i < listaPremio.size(); i++) {
				RateioPremio obj = listaPremio.get(i);
				StringBuilder format = new StringBuilder();
				String faixa = StringUtil.completeAEsquerda(obj.getFaixa().toString(), 4, '0');
				String numeroGanhadores = obj.getNumeroGanhadores().toString();
				String valor = obj.getValorPremio().getValorSemSeparador();
				format.append(faixa).append(";").append(numeroGanhadores).append(";").append(valor);
				retorno[i] = format.toString();
			}
			return retorno;
		}
	}

	private String[] formataPremiacoesPrimeiroSorteio(List<MunicipioUFGanhador> listaMunicipiosUF) {
		if (listaMunicipiosUF == null || listaMunicipiosUF.isEmpty()) {
			return null;
		} else {
			String[] retorno = new String[listaMunicipiosUF.size()];
			for (int i = 0; i < listaMunicipiosUF.size(); i++) {
				MunicipioUFGanhador obj = listaMunicipiosUF.get(i);
				StringBuilder format = new StringBuilder();
				String ganhadores = obj.getGanhadores().toString();
				String municipio = obj.getMunicipio();
				String uf = obj.getUF();
				format.append(ganhadores).append(";").append(municipio).append(";").append(uf);
				retorno[i] = format.toString();
			}
			return retorno;
		}
	}

	private List<List<Integer>> montaMatrizSuperSete(ResultadoConcurso res) {
		List<List<Integer>> listaLocal = new ArrayList<List<Integer>>();
		List<Integer> intListPrognosticos = ApostaUtil.getIntListPrognosticos(res.getNumerosSorteadosSorteio());
		for (Integer integer : intListPrognosticos) {
			ArrayList<Integer> coluna = new ArrayList<Integer>();
			coluna.add(integer);
			listaLocal.add(coluna);
		}
		return listaLocal;
	}

	private List<List<Integer>> montaMatrizSuperSete(RetornoResultadoConcursoAPIManager res) {
		List<List<Integer>> listaLocal = new ArrayList<List<Integer>>();
		for (Integer integer : res.getListaDezenas()) {
			ArrayList<Integer> coluna = new ArrayList<Integer>();
			coluna.add(integer);
			listaLocal.add(coluna);
		}
		return listaLocal;
	}

	private void montaMaisMilionaria(ResultadoConcurso res) {
		if (res.getNumerosSorteadosSorteio().length() == 24) {
			this.setNumerosSorteadosPrimeiroSorteio(ApostaUtil.getIntListPrognosticos(res.getNumerosSorteadosSorteio().substring(0, 18)));
			this.setTrevosSorteadosPrimeiroSorteio(ApostaUtil.getIntListPrognosticos(res.getNumerosSorteadosSorteio().substring(18, 24)));
		}
	}

	private void montaMaisMilionariaAPIManager(RetornoResultadoConcursoAPIManager res) {
		List<Integer> prognosticos = res.getListaDezenas();
		if (prognosticos != null && !prognosticos.isEmpty() && prognosticos.size() == 8) {

			List<Integer> listaPrognosticos = new ArrayList<Integer>();
			List<Integer> listaTrevos = new ArrayList<Integer>();

			for (int i = 0; i < prognosticos.size(); i++) {
				if (i <= 5) {
					listaPrognosticos.add(prognosticos.get(i));
				}

				if (i >= 6 && i <= 7) {
					listaTrevos.add(prognosticos.get(i));
				}
			}
			this.setNumerosSorteadosPrimeiroSorteio(listaPrognosticos);
			this.setTrevosSorteadosPrimeiroSorteio(listaTrevos);
		}
	}

	public String getMunicipioSorteio() {
		return municipioSorteio;
	}

	public void setMunicipioSorteio(String municipioSorteio) {
		this.municipioSorteio = municipioSorteio;
	}

	public String getUfSorteio() {
		return ufSorteio;
	}

	public void setUfSorteio(String ufSorteio) {
		this.ufSorteio = ufSorteio;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public Decimal getArrecadacaoTotal() {
		return arrecadacaoTotal;
	}

	public void setArrecadacaoTotal(Decimal arrecadacaoTotal) {
		this.arrecadacaoTotal = arrecadacaoTotal;
	}

	public Decimal getAcumuladoParaProximoConcurso() {
		return acumuladoParaProximoConcurso;
	}

	public void setAcumuladoParaProximoConcurso(Decimal acumuladoParaProximoConcurso) {
		this.acumuladoParaProximoConcurso = acumuladoParaProximoConcurso;
	}

	public Boolean getAcumulou() {
		return acumulou;
	}

	public void setAcumulou(Boolean acumulou) {
		this.acumulou = acumulou;
	}

	public List<?> getNumerosSorteadosPrimeiroSorteio() {
		return numerosSorteadosPrimeiroSorteio;
	}

	public void setNumerosSorteadosPrimeiroSorteio(
		List<Integer> numerosSorteadosPrimeiroSorteio) {
		this.numerosSorteadosPrimeiroSorteio = numerosSorteadosPrimeiroSorteio;
	}

	public List<Integer> getNumerosSorteadosSegundoSorteio() {
		return numerosSorteadosSegundoSorteio;
	}

	public void setNumerosSorteadosSegundoSorteio(
		List<Integer> numerosSorteadosSegundoSorteio) {
		this.numerosSorteadosSegundoSorteio = numerosSorteadosSegundoSorteio;
	}

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public Concurso getProximoConcurso() {
		return proximoConcurso;
	}

	public void setProximoConcurso(Concurso proximoConcurso) {
		this.proximoConcurso = proximoConcurso;
	}

	public Concurso getProximoConcursoFinalZero() {
		if (proximoConcursoFinalZero != null) {
			return proximoConcursoFinalZero;
		}
		return null;
	}

	public void setProximoConcursoFinalZero(Concurso proximoConcursoFinalZero) {
		this.proximoConcursoFinalZero = proximoConcursoFinalZero;
	}

	public Concurso getProximoConcursoFinalCinco() {
		if (proximoConcursoFinalCinco != null) {
			return proximoConcursoFinalCinco;
		}
		return null;
	}

	public void setProximoConcursoFinalCinco(Concurso proximoConcursoFinalCinco) {
		this.proximoConcursoFinalCinco = proximoConcursoFinalCinco;
	}

	public Concurso getProximoConcursoEspecial() {
		return proximoConcursoEspecial;
	}

	public void setProximoConcursoEspecial(Concurso proximoConcursoEspecial) {
		this.proximoConcursoEspecial = proximoConcursoEspecial;
	}

	public PalpitesLotogol getPartidasLotogol() {
		return partidasLotogol;
	}

	public void setPartidasLotogol(PalpitesLotogol partidasLotogol) {
		this.partidasLotogol = partidasLotogol;
	}

	public PalpitesLoteca getPartidasLoteca() {
		return partidasLoteca;
	}

	public void setPartidasLoteca(PalpitesLoteca partidasLoteca) {
		this.partidasLoteca = partidasLoteca;
	}

	public String[] getPremiacoesPrimeiroSorteio() {
		return premiacoesPrimeiroSorteio;
	}

	public void setPremiacoesPrimeiroSorteio(String[] premiacoesPrimeiroSorteio) {
		this.premiacoesPrimeiroSorteio = premiacoesPrimeiroSorteio;
	}

	public String getPremiacaoTimeDoCoracao() {
		return premiacaoTimeDoCoracao;
	}

	public void setPremiacaoTimeDoCoracao(String premiacaoTimeDoCoracao) {
		this.premiacaoTimeDoCoracao = premiacaoTimeDoCoracao;
	}

	public String getPremiacaoMesDeSorte() {
		return premiacaoMesDeSorte;
	}

	public void setPremiacaoMesDeSorte(String premiacaoMesDeSorte) {
		this.premiacaoMesDeSorte = premiacaoMesDeSorte;
	}

	public String[] getGanhadoresPrimeiroSorteio() {
		return ganhadoresPrimeiroSorteio;
	}

	public void setGanhadoresPrimeiroSorteio(String[] ganhadoresPrimeiroSorteio) {
		this.ganhadoresPrimeiroSorteio = ganhadoresPrimeiroSorteio;
	}

	public List<Integer> getTrevosSorteadosPrimeiroSorteio() {
		return trevosSorteadosPrimeiroSorteio;
	}

	public void setTrevosSorteadosPrimeiroSorteio(List<Integer> trevosSorteadosPrimeiroSorteio) {
		this.trevosSorteadosPrimeiroSorteio = trevosSorteadosPrimeiroSorteio;

	}

	public String getUfTimeCoracao() {
		return ufTimeCoracao;
	}

	public void setUfTimeCoracao(String ufTimeCoracao) {
		this.ufTimeCoracao = ufTimeCoracao;
	}

	public Integer getTipoPublicacao() {
		return tipoPublicacao;
	}

	public void setTipoPublicacao(Integer tipoPublicacao) {
		this.tipoPublicacao = tipoPublicacao;
	}

}
