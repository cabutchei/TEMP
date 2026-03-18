package br.gov.caixa.silce.dominio.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.dominio.exception.NegocioException;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.AbstractParametroJogo;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroDiaDeSorte;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroEquipe;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroJogoNumerico;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroLoteca;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroLotogol;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroMaisMilionaria;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroPartida;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroTimemania;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroValorApostaLoteca;
import br.gov.caixa.silce.dominio.broker.parametrosjogos.ParametroValorApostaLotogol;
import br.gov.caixa.silce.dominio.entidade.AbstractApostaCarrinhoFavorito;
import br.gov.caixa.silce.dominio.entidade.AbstractApostaFavorita;
import br.gov.caixa.silce.dominio.entidade.AbstractApostaNumerica;
import br.gov.caixa.silce.dominio.entidade.AbstractApostaVO;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoDiaDeSorte;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoDuplasena;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoLotofacil;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoLotomania;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoMaisMilionaria;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoMegaSena;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoQuina;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoSuperSete;
import br.gov.caixa.silce.dominio.entidade.ApostaCarrinhoFavoritoTimemania;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.entidade.ApostaDiaDeSorte;
import br.gov.caixa.silce.dominio.entidade.ApostaDiaDeSorteVO;
import br.gov.caixa.silce.dominio.entidade.ApostaDuplasena;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaDiaDeSorte;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaDuplasena;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaLotofacil;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaLotomania;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaMaisMilionaria;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaMegaSena;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaQuina;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaSuperSete;
import br.gov.caixa.silce.dominio.entidade.ApostaFavoritaTimemania;
import br.gov.caixa.silce.dominio.entidade.ApostaLoteca;
import br.gov.caixa.silce.dominio.entidade.ApostaLotecaVO;
import br.gov.caixa.silce.dominio.entidade.ApostaLotofacil;
import br.gov.caixa.silce.dominio.entidade.ApostaLotogol;
import br.gov.caixa.silce.dominio.entidade.ApostaLotogolVO;
import br.gov.caixa.silce.dominio.entidade.ApostaLotomania;
import br.gov.caixa.silce.dominio.entidade.ApostaLotomaniaVO;
import br.gov.caixa.silce.dominio.entidade.ApostaMaisMilionaria;
import br.gov.caixa.silce.dominio.entidade.ApostaMaisMilionariaVO;
import br.gov.caixa.silce.dominio.entidade.ApostaMegasena;
import br.gov.caixa.silce.dominio.entidade.ApostaNumericaVO;
import br.gov.caixa.silce.dominio.entidade.ApostaQuina;
import br.gov.caixa.silce.dominio.entidade.ApostaSuperSete;
import br.gov.caixa.silce.dominio.entidade.ApostaSuperSeteVO;
import br.gov.caixa.silce.dominio.entidade.ApostaTimemania;
import br.gov.caixa.silce.dominio.entidade.ApostaTimemaniaVO;
import br.gov.caixa.silce.dominio.entidade.EquipeEsportiva;
import br.gov.caixa.silce.dominio.entidade.HistoricoAposta;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaDiaDeSorte;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaDuplasena;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaLoteca;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaLotofacil;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaLotogol;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaLotomania;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaMaisMilionaria;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaMegasena;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaQuina;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaSuperSete;
import br.gov.caixa.silce.dominio.entidade.HistoricoApostaTimemania;
import br.gov.caixa.silce.dominio.exception.CodigoErro;
import br.gov.caixa.silce.dominio.interfaces.Favorita;
import br.gov.caixa.silce.dominio.interfaces.Teimosinha;
import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSuperSete;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.StringUtil;
import br.gov.caixa.util.Validate;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 * @author c127237
 */
public final class ApostaUtil {

	private static final int QTD_COLUNAS_SUPER_7 = 7;

	private static final int TAMANHO_PROGNOSTICO = 3;

	private static final String FORMATO_PROGNOSTICO = "%0" + TAMANHO_PROGNOSTICO + "d";

	private static final Integer ZERO = 0;

	private static final Map<Integer, String> DEZENAS_FORMATADAS_TELA = new HashMap<Integer, String>();

	private static int numeroEquipe = 0;

	private static final Map<Integer, String> prognoticosFormatados = new HashMap<Integer, String>(100);

	private static final Logger LOG = LogManager.getLogger(ApostaUtil.class, new MessageFormatMessageFactory());

	// Colocado somente para dar 100% no emma
	@SuppressWarnings("unused")
	private static ApostaUtil instancia = new ApostaUtil();

	private ApostaUtil() {
	}

	public static String convertToString(int[] prognosticos) {
		if (prognosticos == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int prognostico : prognosticos) {
			builder.append(formataPrognostico(prognostico));
		}

		return builder.toString();
	}

	public static String convertToString(Integer[] prognosticos) {
		if (prognosticos == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Integer prognostico : prognosticos) {
			builder.append(formataPrognostico(prognostico));
		}

		return builder.toString();
	}

	public static <T> String convertToString(Collection<T> prognosticos) {
		if (prognosticos == null || prognosticos.isEmpty()) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (T prognostico : prognosticos) {
			Integer valor = null;
			if (prognostico instanceof String) {
				valor = Integer.parseInt((String) prognostico);
			} else if (prognostico instanceof Integer) {
				valor = (Integer) prognostico;
			}
			builder.append(formataPrognostico(valor));
		}
		return builder.toString();
	}

	/**
	 * Converte o array de String em int, fazendo simplesmente o valueof de cada elemento do array e formatando
	 * 
	 * @param prognosticos
	 * @return
	 */
	public static String convertToString(String[] prognosticos) {
		if (prognosticos == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (String prognostico : prognosticos) {
			builder.append(formataPrognostico(Integer.valueOf(prognostico)));
		}

		return builder.toString();
	}

	public static String convertToString(List<boolean[]> prognosticos) {
		if (prognosticos == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < prognosticos.size(); i++) {
			for (int j = 0; j < prognosticos.get(i).length; j++) {
				if (prognosticos.get(i)[j]) {
					int valor = (prognosticos.get(i).length * i) + j + 1;
					builder.append(formataPrognostico(valor));
				}
			}

		}
		return builder.toString();
	}

	public static String convertToString(boolean[][] prognosticos) {
		if (prognosticos == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < prognosticos.length; i++) {
			for (int j = 0; j < prognosticos[i].length; j++) {
				if (prognosticos[i][j]) {
					int valor = (prognosticos[i].length * i) + j + 1;
					builder.append(formataPrognostico(valor));
				}
			}

		}
		return builder.toString();
	}

	public static String[] getArrayPrognosticos(String prognosticos) {
		if (prognosticos == null) {
			return null;
		}
		return StringUtil.splitDigitos(prognosticos, TAMANHO_PROGNOSTICO);
	}

	public static Integer[] getIntArrayPrognosticos(String prognosticos) {
		if (prognosticos == null) {
			return null;
		}
		String[] splitDigitos = StringUtil.splitDigitos(prognosticos, TAMANHO_PROGNOSTICO);
		Integer[] prognosticosInt = new Integer[splitDigitos.length];
		for (int i = 0; i < splitDigitos.length; i++) {
			prognosticosInt[i] = Integer.valueOf(splitDigitos[i]);
		}
		return prognosticosInt;
	}

	public static List<String> getListPrognosticos(String prognosticos) {
		String[] arrayPrognosticos = getArrayPrognosticos(prognosticos);
		if (arrayPrognosticos == null) {
			return null;
		}
		return Arrays.asList(arrayPrognosticos);
	}

	public static List<List<Integer>> getListListIntPrognosticos(int[] prognosticos) {
		List<List<Integer>> progs = new ArrayList<List<Integer>>();
		for (int i = 0; i < QTD_COLUNAS_SUPER_7; i++) {
			progs.add(new ArrayList<Integer>());
		}
		if (prognosticos != null) {
			for (int i = 0; i < prognosticos.length; i++) {
				Integer linha = (prognosticos[i] - 1) / QTD_COLUNAS_SUPER_7;
				Integer coluna = (prognosticos[i] - 1) % QTD_COLUNAS_SUPER_7;
				progs.get(coluna).add(linha);
			}
		}
		return progs;
	}

	public static List<Integer> getIntListPrognosticos(String prognosticos) {
		Integer[] arrayPrognosticos = getIntArrayPrognosticos(prognosticos);
		if (arrayPrognosticos == null) {
			return null;
		}
		return Arrays.asList(arrayPrognosticos);
	}

	public static List<Integer> getIntListPrognosticos(int[] prognosticos) {
		if (prognosticos == null) {
			return null;
		}
		Integer[] arrayPrognosticos = new Integer[prognosticos.length];
		for (int i = 0; i < prognosticos.length; i++) {
			arrayPrognosticos[i] = prognosticos[i];
		}
		return Arrays.asList(arrayPrognosticos);
	}

	public static String formataPrognostico(int prognostico) {
		String prognosticoFormatado = prognoticosFormatados.get(prognostico);
		if (prognosticoFormatado == null) {
			prognosticoFormatado = String.format(FORMATO_PROGNOSTICO, prognostico);
			prognoticosFormatados.put(prognostico, prognosticoFormatado);
		}
		return prognosticoFormatado;
	}

	public static String formataPrognoticos(List<Integer> dezenas) {
		StringBuilder prognosticosFormatados = new StringBuilder();
		for (Integer d : dezenas) {
			prognosticosFormatados.append(formataPrognostico(d));
		}
		return prognosticosFormatados.toString();
	}

	public static ParametroEquipe recuperaTime(EquipeEsportiva equipeEsportiva) {
		if (equipeEsportiva != null) {
			ParametroEquipe parametroEquipe = new ParametroEquipe();
			parametroEquipe.setDescricaoCurta(equipeEsportiva.getNomeReduzido());
			parametroEquipe.setDescricaoLonga(equipeEsportiva.getNome());
			parametroEquipe.setIndicadorSelecao(equipeEsportiva.getSelecao());
			parametroEquipe.setNome(equipeEsportiva.getNome());
			parametroEquipe.setNumero(Integer.valueOf(equipeEsportiva.getId().intValue()));
			parametroEquipe.setNumeroPais(Integer.valueOf(equipeEsportiva.getPais().intValue()));
			parametroEquipe.setUf(equipeEsportiva.getUf());
			return parametroEquipe;
		}
		return null;
	}

	@Deprecated
	public static String formataPrognosticoTela(Integer prognostico) {
		String value = DEZENAS_FORMATADAS_TELA.get(prognostico);
		if (value == null) {
			Integer resto = prognostico % 100;
			if (resto < 10) {
				value = "0" + resto;
			} else {
				value = String.valueOf(prognostico);
			}
			DEZENAS_FORMATADAS_TELA.put(prognostico, value);
		}
		return value;
	}

	public static Decimal calculeValor(ParametroJogoNumerico parametroJogoNumerico, Integer qtApostas, Integer qtPrognosticosJogo,
		Integer quantidadeTeimosinhas) {

		Decimal valorPorAposta = parametroJogoNumerico.getValor(qtPrognosticosJogo);
		Decimal valorFinal = Decimal.ZERO;
		if (valorPorAposta != null) {
			if (qtApostas != null && !ZERO.equals(qtApostas)) {
				valorFinal = valorFinal.add(valorPorAposta.multiply(new Decimal(qtApostas)));
			}

			if (quantidadeTeimosinhas != null && !ZERO.equals(quantidadeTeimosinhas)) {
				valorFinal = valorFinal.multiply(new Decimal(quantidadeTeimosinhas));
			}
			return valorFinal;
		}

		return null;
	}

	public static Decimal calculeValorMaisMilionaria(ParametroMaisMilionaria parametroJogoMaisMilionaria, Integer qtApostas, Integer qtPrognosticosJogo,
		Integer qtPrognosticosJogoTrevo, Integer quantidadeTeimosinhas) {

		List<Integer> qtPrognosticos = new ArrayList<Integer>();
		qtPrognosticos.add(qtPrognosticosJogo);
		qtPrognosticos.add(qtPrognosticosJogoTrevo);
		Decimal valorPorAposta = parametroJogoMaisMilionaria.getValorMaisMilionaria(qtPrognosticos);
		Decimal valorFinal = Decimal.ZERO;
		if (valorPorAposta != null) {
			if (qtApostas != null && !ZERO.equals(qtApostas)) {
				valorFinal = valorFinal.add(valorPorAposta.multiply(new Decimal(qtApostas)));
			}

			if (quantidadeTeimosinhas != null && !ZERO.equals(quantidadeTeimosinhas)) {
				valorFinal = valorFinal.multiply(new Decimal(quantidadeTeimosinhas));
			}
			return valorFinal;
		}

		return null;
	}

	public static Decimal calculeValor(ParametroJogoNumerico parametroJogoNumerico, AbstractApostaNumerica apostaNumerica) {
		return calculeValor(parametroJogoNumerico, 1, apostaNumerica.getPrognosticos().size(),
			apostaNumerica.getQuantidadeTeimosinhas());
	}

	public static Decimal calculeValor(ParametroJogoNumerico parametroJogoNumerico, ApostaSuperSete apostaSuperSete) {
		return calculeValor(parametroJogoNumerico, 1, apostaSuperSete.getPalpites().size(), apostaSuperSete.getQuantidadeTeimosinhas());
	}

	public static Decimal calculeValor(ParametroMaisMilionaria parametroJogoMaisMilionaria, ApostaMaisMilionaria apostaMaisMilionaria) {
		return calculeValorMaisMilionaria(parametroJogoMaisMilionaria, 1, apostaMaisMilionaria.getPalpites().size(), apostaMaisMilionaria.getPalpitesTrevo().size(),
			apostaMaisMilionaria.getQuantidadeTeimosinhas());
	}

	public static Decimal calculeValor(ParametroLoteca parametroLoteca, Integer quantidadeDuplos, Integer quantidadeTriplos) {
		List<ParametroValorApostaLoteca> valores = parametroLoteca.getValoresAposta();
		Decimal valorPorJogo = null;

		for (ParametroValorApostaLoteca param : valores) {
			if (param.getQuantidadeDuplos().equals(quantidadeDuplos) && param.getQuantidadeTriplos().equals(quantidadeTriplos)) {
				valorPorJogo = param.getValor();
			}
		}
		return valorPorJogo;
	}

	public static Decimal calculeValor(ParametroLoteca parametroLoteca, ApostaLoteca apostaLoteca) {
		return calculeValor(parametroLoteca, apostaLoteca.getPalpites().getQuantidadeDuplos(), apostaLoteca.getPalpites()
			.getQuantidadeTriplos());
	}

	public static Decimal calculeValor(ParametroLotogol parametroLotogol, Integer quantidadeApostas) {
		List<ParametroValorApostaLotogol> valores = parametroLotogol.getValoresAposta();
		Decimal valorPorJogo = null;

		for (ParametroValorApostaLotogol param : valores) {
			if (param.getNumeroApostas().equals(quantidadeApostas)) {
				valorPorJogo = param.getValor();
			}
		}
		return valorPorJogo;
	}

	public static Decimal calculeValor(ParametroLotogol parametroLotogol, ApostaLotogol apostaLotogol) {
		return calculeValor(parametroLotogol, apostaLotogol.getQuantidadeApostas());
	}

	public static Aposta<?> createAposta(Modalidade modalidade) {
		switch (modalidade) {
			case SUPER_7:
				return new ApostaSuperSete();
			case DUPLA_SENA:
				return new ApostaDuplasena();
			case LOTECA:
				return new ApostaLoteca();
			case LOTOFACIL:
				return new ApostaLotofacil();
			case LOTOGOL:
				return new ApostaLotogol();
			case LOTOMANIA:
				return new ApostaLotomania();
			case MEGA_SENA:
				return new ApostaMegasena();
			case QUINA:
				return new ApostaQuina();
			case TIMEMANIA:
				return new ApostaTimemania();
			case DIA_DE_SORTE:
				return new ApostaDiaDeSorte();
			case MAIS_MILIONARIA:
				return new ApostaMaisMilionaria();
		}
		return null;
	}

	public static HistoricoAposta<?> createHistoricoAposta(Modalidade modalidade) {
		switch (modalidade) {
			case DUPLA_SENA:
				return new HistoricoApostaDuplasena();
			case LOTECA:
				return new HistoricoApostaLoteca();
			case LOTOFACIL:
				return new HistoricoApostaLotofacil();
			case LOTOGOL:
				return new HistoricoApostaLotogol();
			case LOTOMANIA:
				return new HistoricoApostaLotomania();
			case MEGA_SENA:
				return new HistoricoApostaMegasena();
			case QUINA:
				return new HistoricoApostaQuina();
			case TIMEMANIA:
				return new HistoricoApostaTimemania();
			case DIA_DE_SORTE:
				return new HistoricoApostaDiaDeSorte();
			case SUPER_7:
				return new HistoricoApostaSuperSete();
			case MAIS_MILIONARIA:
				return new HistoricoApostaMaisMilionaria();
		}
		return null;
	}

	public static AbstractApostaVO<?> createApostaVO(Modalidade modalidade) {
		switch (modalidade) {
			case DUPLA_SENA:
			case LOTOFACIL:
			case MEGA_SENA:
			case QUINA:
				return new ApostaNumericaVO(modalidade);
			case LOTOMANIA:
				return new ApostaLotomaniaVO();
			case LOTECA:
				return new ApostaLotecaVO();
			case LOTOGOL:
				return new ApostaLotogolVO();
			case TIMEMANIA:
				return new ApostaTimemaniaVO();
			case DIA_DE_SORTE:
				return new ApostaDiaDeSorteVO();
			case SUPER_7:
				return new ApostaSuperSeteVO();
			case MAIS_MILIONARIA:
				return new ApostaMaisMilionariaVO();
		}
		return null;
	}

	public static List<ParametroPartida> gerarPrognosticosParametroPartidaFicticios(int quantidadePrognosticos, String nomePadraoEquipes) {

		List<ParametroPartida> listaPartidas = new ArrayList<ParametroPartida>();

		ParametroPartida partida = null;
		int numeroPartida = 1;

		for (int i = 0; i < quantidadePrognosticos; i++) {
			partida = new ParametroPartida();
			partida.setNumero(numeroPartida);

			/* Equipe 1 */
			partida.setEquipe1(criarEquipe(nomePadraoEquipes));

			/* Equipe 2 */
			partida.setEquipe2(criarEquipe(nomePadraoEquipes));

			listaPartidas.add(partida);
			numeroPartida++;
		}

		numeroEquipe = 0;
		return listaPartidas;
	}

	private static ParametroEquipe criarEquipe(String nomePadraoEquipes) {
		ParametroEquipe equipe = new ParametroEquipe();
		numeroEquipe++;
		equipe.setNome(nomePadraoEquipes + numeroEquipe);
		return equipe;
	}

	@SuppressWarnings("unchecked")
	public static final AbstractApostaVO<?> gereAposta(Modalidade modalidade, List<?> prognosticos, ParametroJogoNumerico parametroJogoNumerico, Integer qtdTeimosinhas) {
		AbstractApostaVO<?> apostaCarrinho = (AbstractApostaVO<?>) ApostaUtil.createApostaVO(modalidade);
		Validate.notNull(apostaCarrinho, "ApostaVO Gerada");

		if (apostaCarrinho instanceof ApostaSuperSeteVO) {
			((ApostaSuperSeteVO) apostaCarrinho).setPrognosticos((List<List<Integer>>) prognosticos);
		} else {
			((ApostaNumericaVO) apostaCarrinho).setPrognosticos((List<Integer>) prognosticos);
		}

		Decimal valor;

		valor = calculeValor(parametroJogoNumerico, 1, prognosticos.size(), qtdTeimosinhas);

		apostaCarrinho.setValor(valor);
		apostaCarrinho.setTipoConcurso(parametroJogoNumerico.getConcurso().getTipoConcurso());
		apostaCarrinho.setConcursoAlvo(parametroJogoNumerico.getConcurso().getNumero());

		apostaCarrinho.setIndicadorSurpresinha(IndicadorSurpresinha.NAO_SURPRESINHA);
		if (apostaCarrinho instanceof Teimosinha) {
			((Teimosinha) apostaCarrinho).setQuantidadeTeimosinhas(qtdTeimosinhas);
		}
		if (modalidade.equals(Modalidade.TIMEMANIA) && parametroJogoNumerico instanceof ParametroTimemania) {
			ParametroTimemania parametroTimemania = (ParametroTimemania) parametroJogoNumerico;
			List<ParametroEquipe> equipes = parametroTimemania.getEquipes();
			Random random = new SecureRandom();
			int nextInt = random.nextInt(equipes.size());
			ParametroEquipe parametroEquipe = equipes.get(nextInt);

			ApostaTimemaniaVO vo = (ApostaTimemaniaVO) apostaCarrinho;
			vo.setTimeDoCoracao(parametroEquipe.getNumero());
		} else if (modalidade.equals(Modalidade.DIA_DE_SORTE) && parametroJogoNumerico instanceof ParametroDiaDeSorte) {
			ParametroDiaDeSorte meses = (ParametroDiaDeSorte) parametroJogoNumerico;
			Random random = new SecureRandom();
			int quantidadeMeses = meses.getMeses().size();
			int nextInt = random.nextInt(quantidadeMeses);

			ApostaDiaDeSorteVO vo = (ApostaDiaDeSorteVO) apostaCarrinho;
			vo.setMesDeSorte(meses.getMeses().get(nextInt).getNumero());
		}

		return apostaCarrinho;
	}

	@SuppressWarnings("unchecked")
	public static final AbstractApostaVO<?> gereAposta(Modalidade modalidade, List<?> prognosticos, List<Integer> trevos,
		ParametroMaisMilionaria parametroMaisMilionaria, Integer qtdTeimosinhas) {
		AbstractApostaVO<?> apostaCarrinho = (AbstractApostaVO<?>) ApostaUtil.createApostaVO(modalidade);
		Validate.notNull(apostaCarrinho, "ApostaVO Gerada");

		((ApostaMaisMilionariaVO) apostaCarrinho).setPrognosticos((List<Integer>) prognosticos);
		((ApostaMaisMilionariaVO) apostaCarrinho).setPrognosticosTrevo((List<Integer>) trevos);

		Decimal valor = calculeValorMaisMilionaria(parametroMaisMilionaria, 1, prognosticos.size(), trevos.size(), qtdTeimosinhas);

		apostaCarrinho.setValor(valor);
		apostaCarrinho.setTipoConcurso(parametroMaisMilionaria.getConcurso().getTipoConcurso());
		apostaCarrinho.setConcursoAlvo(parametroMaisMilionaria.getConcurso().getNumero());

		apostaCarrinho.setIndicadorSurpresinha(IndicadorSurpresinha.NAO_SURPRESINHA);
		if (apostaCarrinho instanceof Teimosinha) {
			((Teimosinha) apostaCarrinho).setQuantidadeTeimosinhas(qtdTeimosinhas);
		}

		return apostaCarrinho;
	}

	public static final AbstractApostaVO<?> gereApostaFavorita(AbstractApostaFavorita apostaFavorita, ParametroJogoNumerico parametroJogoNumerico, Integer qtdTeimosinhas) {
		Modalidade modalidade = apostaFavorita.getModalidade();
		AbstractApostaVO<?> apostaVO = null;

		if (Modalidade.MAIS_MILIONARIA.equals(modalidade)) {
			apostaVO = gereAposta(modalidade, ((Favorita) apostaFavorita).getPrognosticos(), ((ApostaFavoritaMaisMilionaria) apostaFavorita).getTrevos().getPrognosticos(),
				(ParametroMaisMilionaria) parametroJogoNumerico, qtdTeimosinhas);
		} else {
			apostaVO = gereAposta(modalidade, ((Favorita) apostaFavorita).getPrognosticos(), parametroJogoNumerico, qtdTeimosinhas);
		}
		
		if (Modalidade.TIMEMANIA.equals(modalidade)) {
			ApostaTimemaniaVO apostaTimemaniaVO = (ApostaTimemaniaVO) apostaVO;
			apostaTimemaniaVO.setTimeDoCoracao(((ApostaFavoritaTimemania) apostaFavorita).getTimeDoCoracao());
		} else if (Modalidade.DIA_DE_SORTE.equals(modalidade)) {
			ApostaDiaDeSorteVO apostaDiaDeSorteVO = (ApostaDiaDeSorteVO) apostaVO;
			apostaDiaDeSorteVO.setMesDeSorte(((ApostaFavoritaDiaDeSorte) apostaFavorita).getMesDeSorte());
		}
		return apostaVO;
	}

	public static final AbstractApostaVO<?> gereApostaFavorita(AbstractApostaCarrinhoFavorito apostaFavorita, ParametroJogoNumerico parametroJogoNumerico, Integer qtdTeimosinhas) {
		Modalidade modalidade = apostaFavorita.getModalidade();

		AbstractApostaVO<?> apostaVO = gereAposta(modalidade, ((Favorita) apostaFavorita).getPrognosticos(), parametroJogoNumerico, qtdTeimosinhas);
		if (Modalidade.TIMEMANIA.equals(modalidade)) {
			ApostaTimemaniaVO apostaTimemaniaVO = (ApostaTimemaniaVO) apostaVO;
			apostaTimemaniaVO.setTimeDoCoracao(((ApostaCarrinhoFavoritoTimemania) apostaFavorita).getTimeDoCoracao());
		} else if (Modalidade.DIA_DE_SORTE.equals(modalidade)) {
			ApostaDiaDeSorteVO apostaDiaDeSorteVO = (ApostaDiaDeSorteVO) apostaVO;
			apostaDiaDeSorteVO.setMesDeSorte(((ApostaCarrinhoFavoritoDiaDeSorte) apostaFavorita).getMesDeSorte());
		}
		return apostaVO;
	}

	public static final ApostaLotomaniaVO gereApostaEspelho(ApostaLotomaniaVO apostaLotomaniaVO, ParametroJogoNumerico parametroLotomania) {
		Validate.notNull(apostaLotomaniaVO, "apostaLotomaniaVO");
		Validate.notNull(parametroLotomania, "parametroLotomania");
		Validate.isTrue(Modalidade.LOTOMANIA.equals(parametroLotomania.getConcurso().getModalidade()), "Molidade do parâmetro deve ser Lotomania");

		ApostaLotomaniaVO apostaEspelho = new ApostaLotomaniaVO();
		apostaEspelho.setTipoConcurso(apostaLotomaniaVO.getTipoConcurso());
		apostaEspelho.setConcursoAlvo(apostaLotomaniaVO.getConcursoAlvo());
		apostaEspelho.setValor(apostaLotomaniaVO.getValor());
		apostaEspelho.setQuantidadeTeimosinhas(apostaLotomaniaVO.getQuantidadeTeimosinhas());
		apostaEspelho.setPrognosticos(gerePrognosticoEspelho(apostaLotomaniaVO.getPrognosticos(), parametroLotomania));
		apostaEspelho.setIndicadorSurpresinha(apostaLotomaniaVO.getIndicadorSurpresinha());
		apostaEspelho.setEspelho(true);
		return apostaEspelho;
	}

	public static List<Integer> gerePrognosticoEspelho(Collection<Integer> prognosticos, ParametroJogoNumerico parametroLotomania) {
		List<Integer> dezenasEspelho = new ArrayList<Integer>();
		dezenasEspelho.addAll(getDezenasVolanteLotomania(parametroLotomania));
		dezenasEspelho.removeAll(prognosticos);

		return dezenasEspelho;
	}

	private static List<Integer> getDezenasVolanteLotomania(ParametroJogoNumerico parametroLotomania) {
		ArrayList<Integer> dezenasVolante = new ArrayList<Integer>();
		int prognosticoMaximo = parametroLotomania.getPrognosticoMaximo();
		for (int i = 1; i <= prognosticoMaximo; i++) {
			dezenasVolante.add(i);
		}
		return dezenasVolante;
	}

	public static final void calculeEAtualizeValor(Aposta<?> aposta, AbstractParametroJogo parametroJogo) {
		if (parametroJogo == null) {
			throw new IllegalStateException("Parametro para modalidade " + aposta.getModalidade() + " - " + aposta.getTipoConcurso() + " não disponível");
		}
		Decimal valor = null;
		if (aposta.getModalidade().isLoteca()) {
			valor = calculeValor((ParametroLoteca) parametroJogo, (ApostaLoteca) aposta);
		} else if (aposta.getModalidade().isLotogol()) {
			valor = calculeValor((ParametroLotogol) parametroJogo, (ApostaLotogol) aposta);
		} else if (aposta.getModalidade().isSuperSete()) {
			valor = calculeValor((ParametroJogoNumerico) parametroJogo, (ApostaSuperSete) aposta);
		} else if (aposta.getModalidade().isMaisMilionaria()) {
			valor = calculeValor((ParametroMaisMilionaria) parametroJogo, (ApostaMaisMilionaria) aposta);
		} else if (aposta.getModalidade().isNumerico()) {
			valor = calculeValor((ParametroJogoNumerico) parametroJogo, (AbstractApostaNumerica) aposta);
		} else {
			throw new IllegalStateException("Modalidade não prevista");
		}
		if (valor == null) {
			throw new IllegalStateException("Erro no cálculo do valor da aposta"
				+ ": ->" + aposta.getId() + ", modalidade->"
				+ aposta.getDescModalidade() + ", quantidade de palpites->"
				+ aposta.getPalpites().getPrognosticos().size());
		}
		aposta.setValor(valor);
	}

	public static final AbstractApostaFavorita gereApostaFavorita(Modalidade modalidade) {
		AbstractApostaFavorita apostaFavorita = null;

		switch (modalidade) {
			case MEGA_SENA:
				apostaFavorita = new ApostaFavoritaMegaSena();
				break;
			case QUINA:
				apostaFavorita = new ApostaFavoritaQuina();
				break;
			case DUPLA_SENA:
				apostaFavorita = new ApostaFavoritaDuplasena();
				break;
			case LOTOFACIL:
				apostaFavorita = new ApostaFavoritaLotofacil();
				break;
			case LOTOMANIA:
				apostaFavorita = new ApostaFavoritaLotomania();
				break;
			case DIA_DE_SORTE:
				apostaFavorita = new ApostaFavoritaDiaDeSorte();
				break;
			case TIMEMANIA:
				apostaFavorita = new ApostaFavoritaTimemania();
				break;
			case SUPER_7:
				apostaFavorita = new ApostaFavoritaSuperSete();
				break;
			case MAIS_MILIONARIA:
				apostaFavorita = new ApostaFavoritaMaisMilionaria();
				break;
			default:
				// FIXME: LANCAR UMA EXCECAO AQUI
				break;
		}
		if (apostaFavorita != null) {
			apostaFavorita.setModalidade(modalidade);
		}
		return apostaFavorita;
	}

	public static final AbstractApostaCarrinhoFavorito gereApostaCarrinhoFavorito(Modalidade modalidade) {
		AbstractApostaCarrinhoFavorito apostaCarrinhoFavorito = null;

		switch (modalidade) {
			case MEGA_SENA:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoMegaSena();
				break;
			case QUINA:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoQuina();
				break;
			case DUPLA_SENA:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoDuplasena();
				break;
			case LOTOFACIL:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoLotofacil();
				break;
			case LOTOMANIA:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoLotomania();
				break;
			case DIA_DE_SORTE:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoDiaDeSorte();
				break;
			case TIMEMANIA:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoTimemania();
				break;
			case SUPER_7:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoSuperSete();
				break;
			case MAIS_MILIONARIA:
				apostaCarrinhoFavorito = new ApostaCarrinhoFavoritoMaisMilionaria();
				break;
			default:
				// FIXME: LANCAR UMA EXCECAO AQUI
				break;
		}

		return apostaCarrinhoFavorito;
	}

	@Deprecated
	public static final PalpitesSuperSete formataPalpitesSuperSete(List<Integer> numerosSelecionados) {
		// FIXME Arrumar quando virar LIST<LIST<INTEGER>>
		StringBuilder sb = new StringBuilder();
		for (Integer integer : numerosSelecionados) {
			sb.append(StringUtil.completeAEsquerda(integer.toString(), 3, '0'));
		}
		return new PalpitesSuperSete(sb.toString());
	}

	public static final List<Integer> formataPalpitesSuperSeteParaTela(String numerosSelecionados) {
		List<Integer> numeros = new ArrayList<Integer>();
		for (int i = 0; i < numerosSelecionados.length(); i += 3) {
			numeros.add(Integer.valueOf(numerosSelecionados.substring(i, i + 3)));
		}
		return numeros;
	}

	// @Deprecated
	// public static final PalpitesMaisMilionaria formataPalpitesMaisMilionaria(List<Integer> numerosSelecionados) {
	// // FIXME Arrumar quando virar LIST<LIST<INTEGER>>
	// StringBuilder sb = new StringBuilder();
	// for (Integer integer : numerosSelecionados) {
	// sb.append(StringUtil.completeAEsquerda(integer.toString(), 3, '0'));
	// }
	// return new PalpitesMaisMilionaria(sb.toString());
	// }

	public static final List<Integer> formataPalpitesMaisMilionariaParaTela(String numerosSelecionados) {
		List<Integer> numeros = new ArrayList<Integer>();
		for (int i = 0; i < numerosSelecionados.length(); i += 3) {
			numeros.add(Integer.valueOf(numerosSelecionados.substring(i, i + 3)));
		}
		return numeros;
	}

	public static final void verificaApostaBloqueadaParaConsulta(Aposta<?> aposta, Long idApostador) throws NegocioException {
		// FIXME CÓDIGO PROVISÓRIO PARA BLOQUEAR A CONSULTA DE ALGUMAS APOSTAS LOTOFÁCIL E QUINA
		// REMOVER APÓS CORREÇÃO EM PRD
		// ----- INÍCIO ------
		List<Long> idsParaBloquear = Arrays.asList(143090795L, 143406579L);

		Long idApostaComprada = aposta.getApostaComprada().getId();
		ApostaComprada apostaOriginal = aposta.getApostaOriginal();
		if (idsParaBloquear.contains(idApostaComprada) || (apostaOriginal != null && idsParaBloquear.contains(apostaOriginal.getId()))) {
			LOG.error("Apostador tentou conferir uma aposta bloqueada. Apostador: {0}. Aposta: {1}. ApostaComprada: {2}.", idApostador, aposta.getId(), idApostaComprada);
			throw new NegocioException(CodigoErro.PAGAMENTO_PREMIO_CONSULTA_BLOQUEADA);
		}

		// QUINA BLOQUEADA
		List<Long> idsParaBloquearQuina = Arrays.asList(345016478L, 346623511L, 349974178L, 350735834L, 350808283L, 353020134L, 353561276L, 353658330L, 354057863L, 345016478L);

		if (idsParaBloquearQuina.contains(idApostaComprada) || (apostaOriginal != null && idsParaBloquearQuina.contains(apostaOriginal.getId()))) {
			LOG.error("Apostador tentou conferir uma aposta bloqueada. Apostador: {0}. Aposta: {1}. ApostaComprada: {2}.", idApostador, aposta.getId(), idApostaComprada);
			throw new NegocioException(CodigoErro.PAGAMENTO_PREMIO_CONSULTA_BLOQUEADA_QUINA);
		}

		// ----- FIM ------
	}
}