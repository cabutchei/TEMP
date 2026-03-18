package br.gov.caixa.silce.negocio.aposta.entitydemo;

import java.util.Arrays;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaMegasena;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;
import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAOLocal;
import br.gov.caixa.silce.negocio.apostador.dao.ApostadorDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.CompraDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.ReservaCotaBolaoDAO;
import br.gov.caixa.silce.negocio.aposta.dao.ReservaCotaBolaoDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoCompraDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoReservaCotaBolaoDAOLocal;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;


@Stateless
public class EntityDemoBean implements EntityDemoLocal {

	private static final String SQL_NEXT_ID_RESERVA_COTA_BOLAO =
		"SELECT COALESCE(MAX(NU_RESERVA_COTA_BOLAO), 0) + 1 FROM LCE.LCETB059_RESERVA_COTA_BOLAO";
	private static final String SQL_UM_APOSTADOR_ID =
		"SELECT MIN(NU_APOSTADOR) FROM LCE.LCETB002_APOSTADOR";

	@EJB
	private ApostaDAOLocal apostaDAO;

	@EJB
	private ApostadorDAOLocal apostadorDAO;

	@EJB
	private SituacaoApostaDAOLocal situacaoApostaDAO;

	@EJB
	private CompraDAOLocal compraDAO;

	@EJB
	private SituacaoCompraDAOLocal situacaoCompraDAO;

	@EJB
	private SituacaoReservaCotaBolaoDAOLocal situacaoReservaCotaBolaoDAO;

    @EJB
    private ReservaCotaBolaoDAOLocal reservaCotaBolaoDAO;

    @Override
    public void demo() {
        Data agora = DataUtil.getDataAtual();
        Compra compra = criaNovaCompra(agora);
        Aposta<?> aposta = criarAposta(compra, agora);
        ReservaCotaBolao reserva = criaReservaCotaBolao(compra, aposta, agora);
        reservaCotaBolaoDAO.insert(reserva);
        apostaDAO.insert(aposta);
        reserva.setAposta(aposta);
    }


	private ReservaCotaBolao criaReservaCotaBolao(Compra compra, Aposta<?> aposta, Data agora) {
		Long proximoIdReserva = recupereProximoIdReservaCotaBolao();
		// Integer mesReserva = aposta.getMes().intValue();
		// Integer particaoReserva = aposta.getParticao().intValue();

        // ApostaMegasena aposta = criarAposta(compra, agora);

		ReservaCotaBolao reserva = new ReservaCotaBolao();
		reserva.setId(new ReservaCotaBolaoPK(proximoIdReserva, aposta.getMes().intValue(), aposta.getParticao().intValue()));
		reserva.setAno(agora.getYear());
		reserva.setCodBolao("BOLAO-" + proximoIdReserva);
		reserva.setCodCota("COTA-" + proximoIdReserva);
		reserva.setDataHoraReserva(agora);

		Data expiracao = new Data(agora).add(Calendar.MINUTE, 30);
		reserva.setDataExpiracaoReservaCotaBolao(expiracao);
		reserva.setHoraExpiracaoReservaCotaBolao(new Hora(expiracao));

		reserva.setNumeroCotaReservada(1);
		reserva.setQtdCotaTotal(100);
		reserva.setValorCotaReservada(new Decimal("10,00"));
		reserva.setValorTarifaServico(new Decimal("2,00"));
		reserva.setValorCotaCusteio(Decimal.ZERO);
		reserva.setValorTarifaCusteio(Decimal.ZERO);
		reserva.setNsu(Math.abs(System.nanoTime()));

		SituacaoReservaCotaBolao situacaoReserva = situacaoReservaCotaBolaoDAO.findReferenceById(
			SituacaoReservaCotaBolao.Situacao.RESERVADA.getValue());
		reserva.setSituacao(situacaoReserva);
		reserva.setCompra(compra);
		// reserva.setAposta(aposta);
		return reserva;
	}
    
	private Long recupereProximoIdReservaCotaBolao() {
		Number result = apostaDAO.execSingleSelectByNativeQuery(SQL_NEXT_ID_RESERVA_COTA_BOLAO);
		return ((Number) result).longValue();
	}


	private ApostaMegasena criarAposta(Compra compra, Data agora) {
		Hora horaAgora = new Hora(agora);

		ApostaMegasena aposta = new ApostaMegasena();
		aposta.setCompra(compra);
		aposta.setMes(resolveMes(aposta, agora));
		aposta.setParticao(resolveParticao(aposta, compra));
		aposta.setTipoConcurso(TipoConcurso.NORMAL);
		aposta.setIndicadorSurpresinha(IndicadorSurpresinha.NAO_SURPRESINHA);
		aposta.setValor(new Decimal("12,00"));
		aposta.setConcursoAlvo(9999);
		aposta.setDataInclusao(agora);
		aposta.setSubcanalCarrinhoAposta(Subcanal.SILCE);
		aposta.setIndicadorBolao(Boolean.TRUE);
		aposta.setQuantidadeTeimosinhas(1);
		aposta.setPrognosticos(Arrays.asList(1, 2, 3, 4, 5, 6));

		SituacaoAposta situacaoAposta = situacaoApostaDAO.findReferenceById(
			SituacaoAposta.Situacao.NAO_REGISTRADA.getValue());
		aposta.setSituacao(situacaoAposta);
		aposta.setConcursoInicial(9999);
		aposta.setNsuTransacao(Math.abs(System.currentTimeMillis()));
		aposta.setApostaTroca(Boolean.FALSE);
		aposta.setDataInicioApostaComprada(agora);
		aposta.setHoraInicioApostaComprada(horaAgora);
		aposta.setValorComissao(Decimal.ZERO);
		aposta.getApostaComprada().setSubcanalPagamento(Subcanal.SILCE);
		aposta.getApostaComprada().setIndicadorBloqueioDevolucao(Boolean.FALSE);

		// ReservaCotaBolao reservaCotaBolao = criaReservaCotaBolao(aposta, compra, agora);
		// aposta.setReservaCotaBolao(reservaCotaBolao);

		// apostaDAO.insert(aposta);
		// apostaDAO.flush();

		// return aposta.getId();
        return aposta;
	}

	private Compra criaNovaCompra(Data agora) {
		Long apostadorId = recupereUmApostadorId();
		if (apostadorId == null) {
			throw new IllegalStateException("Nenhum apostador encontrado para criar a compra de teste.");
		}

		Compra compra = new Compra();
		compra.setApostador(apostadorDAO.findReferenceById(apostadorId));
		compra.setSituacao(situacaoCompraDAO.findReferenceById(SituacaoCompra.Situacao.CARRINHO.getValue()));
		compra.setNsu(Math.abs(System.currentTimeMillis()));
		compra.setDataInicioCompra(agora);
		compra.setHoraInicioCompra(new Hora(agora));
		compra.setMes(Long.valueOf(agora.getMonth()));
		compra.setParticao(Math.floorMod(compra.getNsu(), 10L));
		compra.setValorTotal(Decimal.ZERO);
		compra.setValorComissao(Decimal.ZERO);
		compra.setSubcanalPagamento(Subcanal.SILCE);
		return compra;
	}

	private Long recupereUmApostadorId() {
		Number result = compraDAO.execSingleSelectByNativeQuery(SQL_UM_APOSTADOR_ID);
		if (result == null) {
			return null;
		}
		return ((Number) result).longValue();
	}
    
	private Long resolveMes(ApostaMegasena aposta, Data agora) {
		if (aposta.getMes() != null) {
			return aposta.getMes();
		}
		return Long.valueOf(agora.getMonth());
	}

	private Long resolveParticao(ApostaMegasena aposta, Compra compra) {
		if (aposta.getParticao() != null) {
			return aposta.getParticao();
		}
		if (compra.getParticao() != null) {
			return compra.getParticao();
		}
		if (compra.getId() == null) {
			return 0L;
		}
		return Math.floorMod(compra.getId(), 10L);
	}
    
}
