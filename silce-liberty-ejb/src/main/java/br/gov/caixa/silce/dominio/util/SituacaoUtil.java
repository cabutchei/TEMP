package br.gov.caixa.silce.dominio.util;

import java.text.MessageFormat;
import java.util.Arrays;

import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.EtapaFechamento;
import br.gov.caixa.silce.dominio.entidade.Fechamento;
import br.gov.caixa.silce.dominio.entidade.Premio;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.entidade.SituacaoPremio;
import br.gov.caixa.silce.dominio.entidade.SituacaoPremio.Situacao;
import br.gov.caixa.silce.dominio.entidade.SituacaoReservaCotaBolao;

public final class SituacaoUtil {

	// 100% de cobertura
	@SuppressWarnings("unused")
	private static final SituacaoUtil SITUACOES_UTIL = new SituacaoUtil();

	private SituacaoUtil() {
		// classe utilitária
	}

	public static final void validaEtapaFechamento(Fechamento fechamento, EtapaFechamento.Etapa... etapas) {
		for (EtapaFechamento.Etapa etapa : etapas) {
			if (fechamento.getEtapa().isMesmoEnum(etapa)) {
				return;
			}
		}
		String msg = "O Fechamento {0} deveria estar em uma das etapas {1}, mas esta na etapa {2}.";
		String format = MessageFormat.format(msg, fechamento.getId(), Arrays.toString(etapas), fechamento.getEtapa().getEnum());
		throw new IllegalStateException(format);
	}

	public static final void validaSituacaoAposta(ApostaComprada aposta, SituacaoAposta.Situacao... situacaoAposta) {
		for (SituacaoAposta.Situacao situacao : situacaoAposta) {
			if (aposta.getSituacao().isMesmoEnum(situacao)) {
				return;
			}
		}
		String msg = "A Aposta {0} deveria estar em uma das situacoes {1}, mas esta na situacao {2}.";
		String format = MessageFormat.format(msg, aposta.getId(), Arrays.toString(situacaoAposta), aposta.getSituacao().getEnum());
		throw new IllegalStateException(format);
	}

	public static final void validaSituacaoPremio(Premio premio, SituacaoPremio.Situacao... situacaoPremio) {
		for (Situacao situacao : situacaoPremio) {
			if (premio.getSituacao().isMesmoEnum(situacao)) {
				return;
			}
		}
		String msg = "O Premio {0} informado deveria estar em uma das situacoes {1}, mas esta na situacao {2}.";
		String format = MessageFormat.format(msg, premio.getId(), Arrays.toString(situacaoPremio), premio.getSituacao().getEnum());
		throw new IllegalStateException(format);
	}

	public static final void validaSituacaoCompra(Compra compra, SituacaoCompra.Situacao... situacaoCompra) {
		for (SituacaoCompra.Situacao situacao : situacaoCompra) {
			if (compra.getSituacao().isMesmoEnum(situacao)) {
				return;
			}
		}
		String msg = "A Compra {0} informada deveria estar em uma das situacoes {1}, mas esta na situacao {2}.";
		String format = MessageFormat.format(msg, compra.getId(), Arrays.toString(situacaoCompra), compra.getSituacao().getEnum());
		throw new IllegalStateException(format);
	}

	public static final void validaSituacaoReservaCotaBolao(ReservaCotaBolao reserva, SituacaoReservaCotaBolao.Situacao... situacaoReserva) {
		for (SituacaoReservaCotaBolao.Situacao situacao : situacaoReserva) {
			if (reserva.getSituacao().isMesmoEnum(situacao)) {
				return;
			}
		}
		String msg = "A Reserva de cota de bolao {0} informada deveria estar em uma das situacoes {1}, mas esta na situacao {2}.";
		String format = MessageFormat.format(msg, reserva.getId(), Arrays.toString(situacaoReserva), reserva.getSituacao().getEnum());
		throw new IllegalStateException(format);
	}

}
