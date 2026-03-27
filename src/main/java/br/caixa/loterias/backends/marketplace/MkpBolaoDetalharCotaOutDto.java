package br.caixa.loterias.backends.marketplace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Output do servico detalharCota")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MkpBolaoDetalharCotaOutDto {

    @Schema(description = "representa o tempo de expiracao em minutos da cota")
    private Integer tempoExpiracao;

    @Schema(description = "representa o idCota")
    private String id;

    @Schema(description = "representa o concurso")
    private Integer concurso;

    @Schema(description = "representa o código da modalidade")
    private Integer codigoModalidade;

    @Schema(description = "representa o numero sequencial da cota no bolao")
    private Integer numeroCota;

    private Integer situacaoCota;
    private Double valorCota; // 175.00
    private Double valorTarifaServico; // 61.25

    private String cpf;
    private Integer nsuTransacaoSilce;
    private String nsbc;

    private String dataHoraReserva;
    private String dataHoraVenda;
}