package br.gov.caixa.silce.dominio.servico.marketplace;

import java.math.BigDecimal;

import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Hora;

public class DadosCotaBolao extends AbstractRetornoErro {

	private static final long serialVersionUID = 1L;
	
	public DadosCotaBolao() {
	}

	public DadosCotaBolao(Integer index, String codBolao, Integer qtdCotaDigital, Integer qtdCotaTotal, Integer qtdCotaDisponivel, Long loterica, String nomeFantasia,
		String nomeRazaoSocial, Long numeroUF, Long idMunicipio, Integer concurso, Integer idModalidade, Integer tipoConcurso, String dataSorteio, String horaSorteio,
		BigDecimal vrPremioEstimado, BigDecimal vrCotaSemTarifa, BigDecimal vrCotaComTarifa, BigDecimal vrTarifaServicoCota, BigDecimal vrUltimaCotaSemTarifa,
		BigDecimal vrUltimaCotaComTarifa, BigDecimal vrTarifaServicoUltimaCota, Integer qtdApostas, Integer qtdNumeros, Integer qtdSimplesLoteca, Integer qtdDuplosLoteca,
		Integer qtdTriplosLoteca) {

		this.index = index;
		this.codigoBolao = codBolao;
		this.qtdCotaDigital = qtdCotaDigital;
		this.qtdCotaTotal = qtdCotaTotal;
		this.qtdCotaDisponivel = qtdCotaDisponivel;
		this.loterica = loterica;
		this.nomeFantasia = nomeFantasia;
		this.nomeRazaoSocial = nomeRazaoSocial;
		this.numeroUF = numeroUF;
		this.idMunicipio = idMunicipio;
		this.concurso = concurso;
		this.idModalidade = idModalidade;
		this.setModalidade(Modalidade.getByCodigo(idModalidade));
		this.tipoConcurso = tipoConcurso;
		this.dataSorteio = dataSorteio;
		this.horaSorteio = horaSorteio;
		this.vrPremioEstimado = vrPremioEstimado;
		this.vrCotaSemTarifa = vrCotaSemTarifa;
		this.vrCotaComTarifa = vrCotaComTarifa;
		this.valorTarifaServico = vrTarifaServicoCota;
		this.vrUltimaCotaSemTarifa = vrUltimaCotaSemTarifa;
		this.vrUltimaCotaComTarifa = vrUltimaCotaComTarifa;
		this.vrTarifaServicoUltimaCota = vrTarifaServicoUltimaCota;
		this.qtdApostas = qtdApostas;
		this.qtdNumeros = qtdNumeros;
		this.qtdSimplesLoteca = qtdSimplesLoteca;
		this.qtdDuplosLoteca = qtdDuplosLoteca;
		this.qtdTriplosLoteca = qtdTriplosLoteca;

		if (!vrCotaComTarifa.equals(vrUltimaCotaComTarifa)) {
			this.contemResiduo = true;
		}
	}

	public DadosCotaBolao(Long loterica, String codBolao, Integer concurso, String dataHoraReserva, String codCota, Integer numeroCota, Integer qtdCotasBolao,
		BigDecimal valorCota, BigDecimal valorTarifaServico, Data dataRegistroBolao, Hora horaRegistroBolao, Integer numeroTerminalLoterico, String apostasBolao, Long nsu) {
		
		this.loterica = loterica;
		this.codigoBolao = codBolao;
		this.concurso = concurso;
		this.dataHoraReserva = dataHoraReserva;
		this.codigoCota = codCota;
		this.numeroCota = numeroCota;
		this.qtdCotaTotal = qtdCotasBolao;
		this.valorCota = valorCota;
		this.valorTarifaServico = valorTarifaServico;
		this.dataRegistroBolao = dataRegistroBolao;
		this.horaRegistroBolao = horaRegistroBolao;
		this.numeroTerminalLoterico = numeroTerminalLoterico;
		this.apostasBolao = apostasBolao;
		this.nsu = nsu;
	}

	public DadosCotaBolao(Long loterica, String codBolao, Integer concurso, String dataHoraReserva, String codCota, Integer numeroCota, Integer qtdCotasBolao,
		BigDecimal valorCota, BigDecimal valorTarifaServico, Data dataRegistroBolao, Hora horaRegistroBolao, Integer numeroTerminalLoterico, String cpf, Long nsu,
		SituacaoCota situacaoCota, String apostasBolao, String nsbc) {

		this.loterica = loterica;
		this.codigoBolao = codBolao;
		this.concurso = concurso;
		this.dataHoraReserva = dataHoraReserva;
		this.codigoCota = codCota;
		this.numeroCota = numeroCota;
		this.qtdCotaTotal = qtdCotasBolao;
		this.valorCota = valorCota;
		this.valorTarifaServico = valorTarifaServico;
		this.dataRegistroBolao = dataRegistroBolao;
		this.horaRegistroBolao = horaRegistroBolao;
		this.numeroTerminalLoterico = numeroTerminalLoterico;
		this.cpf = cpf;
		this.nsu = nsu;
		this.situacaoCota = situacaoCota;
		this.apostasBolao = apostasBolao;
		this.nsbc = nsbc;
	}

}
