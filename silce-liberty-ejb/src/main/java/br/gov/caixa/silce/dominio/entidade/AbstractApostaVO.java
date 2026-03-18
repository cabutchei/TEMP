package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;

import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.entidade.TipoCombo.Tipo;
import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.Palpites;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.silce.dominio.servico.marketplace.Bolao;
import br.gov.caixa.silce.dominio.servico.marketplace.DadosCotaBolao;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

/**
 * @author c101482 - Vinicius Ferraz Campos Florentino
 * @author c127237 - Rinaldo Pitzer Junior
 */

public abstract class AbstractApostaVO<P extends Palpites<?>> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Modalidade modalidade;

	private TipoConcurso tipoConcurso;

	private IndicadorSurpresinha indicadorSurpresinha = IndicadorSurpresinha.NAO_SURPRESINHA;

	private Decimal valor;
	
	private Integer concursoAlvo;

	private Data dataInclusao;

	private Subcanal subcanalCarrinhoAposta;

	private Boolean indicadorBolao = Boolean.FALSE;

	private String codBolao;

	private Integer qtdCotasBolao;

	private Data dataExpiracaoReservaCotaBolao;

	private String dadosBolao;

	private Decimal valorTarifaServico;

	private Long idReservaCotaBolao;

	private Integer mesReservaCotaBolao;

	private Integer nuParticaoReservaCotaBolao;

	private DadosCotaBolao dadosCotaBolao;

	private Boolean indicadorCombo = Boolean.FALSE;
	
	private Tipo tipoCombo;


	/**
	 * vazio devido ao jpa
	 */
	protected AbstractApostaVO() {
		this(null);
		// vazio devido ao jpa
	}

	public AbstractApostaVO(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public String getDescModalidade() {
		return getModalidade().getDescricao();
	}

	public TipoConcurso getTipoConcurso() {
		return tipoConcurso;
	}

	public void setTipoConcurso(TipoConcurso tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}

	public abstract P getPalpites();

	public Decimal getValor() {
		return valor;
	}

	public void setValor(Decimal valor) {
		this.valor = valor;
	}

	public IndicadorSurpresinha getIndicadorSurpresinha() {
		return indicadorSurpresinha;
	}

	public void setIndicadorSurpresinha(IndicadorSurpresinha indicadorSurpresinha) {
		this.indicadorSurpresinha = indicadorSurpresinha;
	}
	
	public boolean isSurpresinha() {
		return indicadorSurpresinha.isSurpresinha();
	}

	public final Aposta<?> createAposta() {
		Aposta<?> aposta = ApostaUtil.createAposta(modalidade);
		aposta.setId(getId());
		if (dataInclusao == null) {
			aposta.setDataInclusao(new Data(Calendar.getInstance()));
		} else {
			aposta.setDataInclusao(new Data(dataInclusao));
		}
		aposta.setTipoConcurso(tipoConcurso);
		aposta.setIndicadorSurpresinha(indicadorSurpresinha);
		aposta.setIndicadorBolao(indicadorBolao);
		aposta.setSubcanalCarrinhoAposta(subcanalCarrinhoAposta);

		if (aposta.getIndicadorBolao() != null && aposta.getIndicadorBolao()) {
			Decimal valorCota = valor;
			if (qtdCotasBolao != null) {
				valorCota = valor.divide(new Decimal(qtdCotasBolao));
			}

			if (valorTarifaServico != null) {
				valorCota = valorCota.add(valorTarifaServico);
			}

			ReservaCotaBolao reserva = new ReservaCotaBolao();
			reserva.setCodBolao(codBolao);
			aposta.setValor(valorCota);
			aposta.setReservaCotaBolao(reserva);

		} else {
			aposta.setIndicadorBolao(false);
			aposta.setValor(valor);
		}

		aposta.setConcursoAlvo(concursoAlvo);
		populeAposta(aposta);
		return aposta;
	}
	
	public void populeDadosCotaBolao(Bolao o) {
		this.dadosCotaBolao = new DadosCotaBolao();
		this.dadosCotaBolao.setTempoExpiracao(o.getTempoExpiracao());
		this.dadosCotaBolao.setCodigoBolao(o.getCodigoBolao());
		this.dadosCotaBolao.setConcurso(o.getConcurso());
		this.dadosCotaBolao.setIdModalidade(o.getIdModalidade());
		this.dadosCotaBolao.setLoterica(o.getLoterica());
		this.dadosCotaBolao.setNumeroTerminalLoterico(o.getNumeroTerminalLoterico());
		this.dadosCotaBolao.setQtdCotaTotal(o.getQtdCotaTotal());
		this.dadosCotaBolao.setDataRegistroBolao(o.getDataRegistroBolao());
		this.dadosCotaBolao.setHoraRegistroBolao(o.getHoraRegistroBolao());

		List<DadosCotaBolao> cotas = new ArrayList<DadosCotaBolao>();
		for (DadosCotaBolao c : o.getCotas()) {
			this.dadosCotaBolao.setDataHoraReserva(c.getDataHoraReserva());
			this.dadosCotaBolao.setNumeroCota(c.getNumeroCota());
			this.dadosCotaBolao.setValorCota(c.getValorCota());
			this.dadosCotaBolao.setValorTarifaServico(c.getValorTarifaServico());
			this.dadosCotaBolao.setCodigoCota(c.getCodigoCota());

			cotas.add(c);
		}
		this.dadosCotaBolao.setCotas(cotas);
		this.dadosCotaBolao.setApostasBolao("{\"apostas\" :" + new Gson().toJson(o.getApostas()) + "}");

	}
	protected abstract void populeAposta(Aposta<?> aposta);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((indicadorSurpresinha == null) ? 0 : indicadorSurpresinha.hashCode());
		result = prime * result + ((modalidade == null) ? 0 : modalidade.hashCode());
		result = prime * result + ((tipoConcurso == null) ? 0 : tipoConcurso.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractApostaVO)) {
			return false;
		}
		AbstractApostaVO<?> other = (AbstractApostaVO<?>) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (indicadorSurpresinha != other.indicadorSurpresinha) {
			return false;
		}
		if (modalidade != other.modalidade) {
			return false;
		}
		if (tipoConcurso != other.tipoConcurso) {
			return false;
		}
		if (valor == null) {
			if (other.valor != null) {
				return false;
			}
		} else if (!valor.equals(other.valor)) {
			return false;
		}
		return true;
	} 
	
	public boolean isPrognosticosEquals(AbstractApostaVO<?> vo) {
		return getPalpites().equals(vo.getPalpites());
	}

	public Integer getConcursoAlvo() {
		return concursoAlvo;
	}

	public void setConcursoAlvo(Integer concursoAlvo) {
		this.concursoAlvo = concursoAlvo;
	}

	public Data getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Data dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Subcanal getSubcanalCarrinhoAposta() {
		return subcanalCarrinhoAposta;
	}

	public void setSubcanalCarrinhoAposta(Subcanal subcanalCarrinhoAposta) {
		this.subcanalCarrinhoAposta = subcanalCarrinhoAposta;
	}

	public Boolean getIndicadorBolao() {
		return indicadorBolao;
	}

	public void setIndicadorBolao(Boolean indicadorBolao) {
		this.indicadorBolao = indicadorBolao;
	}

	public Integer getQtdCotasBolao() {
		return qtdCotasBolao;
	}

	public void setQtdCotasBolao(Integer qtdCotasBolao) {
		this.qtdCotasBolao = qtdCotasBolao;
	}

	public String getCodBolao() {
		return codBolao;
	}

	public void setCodBolao(String codBolao) {
		this.codBolao = codBolao;
	}

	public Data getDataExpiracaoReservaCotaBolao() {
		return dataExpiracaoReservaCotaBolao;
	}

	public void setDataExpiracaoReservaCotaBolao(Data dataExpiracaoReservaCotaBolao) {
		this.dataExpiracaoReservaCotaBolao = dataExpiracaoReservaCotaBolao;
	}

	public String getDadosBolao() {
		return dadosBolao;
	}

	public void setDadosBolao(String dadosBolao) {
		this.dadosBolao = dadosBolao;
	}

	public Decimal getValorTarifaServico() {
		return valorTarifaServico;
	}

	public void setValorTarifaServico(Decimal valorTarifaServico) {
		this.valorTarifaServico = valorTarifaServico;
	}

	public Long getIdReservaCotaBolao() {
		return idReservaCotaBolao;
	}

	public void setIdReservaCotaBolao(Long idReservaCotaBolao) {
		this.idReservaCotaBolao = idReservaCotaBolao;
	}

	public Integer getMesReservaCotaBolao() {
		return mesReservaCotaBolao;
	}

	public void setMesReservaCotaBolao(Integer mesReservaCotaBolao) {
		this.mesReservaCotaBolao = mesReservaCotaBolao;
	}

	public Integer getNuParticaoReservaCotaBolao() {
		return nuParticaoReservaCotaBolao;
	}

	public void setNuParticaoReservaCotaBolao(Integer nuParticaoReservaCotaBolao) {
		this.nuParticaoReservaCotaBolao = nuParticaoReservaCotaBolao;
	}

	public DadosCotaBolao getDadosCotaBolao() {
		return this.dadosCotaBolao; // TODO a implementar
	}

	public void setDadosCotaBolao(DadosCotaBolao dadosCotaBolao) {
		this.dadosCotaBolao = dadosCotaBolao;
	}

	public Boolean getIndicadorCombo() {
		return indicadorCombo;
	}

	public void setIndicadorCombo(Boolean indicadorCombo) {
		this.indicadorCombo = indicadorCombo;
	}

	public Tipo getTipoCombo() {
		return tipoCombo;
	}

	public void setTipoCombo(Tipo tipoCombo) {
		this.tipoCombo = tipoCombo;
	}


	@Override
	public String toString() {
		return "AbstractApostaVO [id=" + id + ", modalidade=" + modalidade + ", tipoConcurso=" + tipoConcurso + ", indicadorSurpresinha=" + indicadorSurpresinha + ", valor="
			+ valor + ", concursoAlvo=" + concursoAlvo + ", dataInclusao=" + dataInclusao + ", subcanalCarrinhoAposta=" + subcanalCarrinhoAposta + ", indicadorBolao="
			+ indicadorBolao + ", codBolao=" + codBolao + ", qtdCotasBolao=" + qtdCotasBolao + ", dataExpiracaoReservaCotaBolao=" + dataExpiracaoReservaCotaBolao + ", dadosBolao="
			+ dadosBolao + ", valorTarifaServico=" + valorTarifaServico + ", idReservaCotaBolao=" + idReservaCotaBolao + ", mesReservaCotaBolao=" + mesReservaCotaBolao
			+ ", nuParticaoReservaCotaBolao=" + nuParticaoReservaCotaBolao + ", dadosCotaBolao=" + dadosCotaBolao + ", indicadorCombo=" + indicadorCombo + ", tipoCombo=" + tipoCombo + "]";
	}

}
