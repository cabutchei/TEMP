package br.caixa.loterias.model.legado;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class ApostaLegado {

    private Long idLegado;

    private Long idNuvem;

    private CompraLegado compra;

    private ModalidadeLegado modalidade;

    private TipoConcursoLegado tipoConcurso;

    private IndicadorSurpresinhaLegado indicadorSurpresinha;

    private BigDecimal valor;

    private ApostaCompradaLegado comprada;

    private Integer concursoAlvo;

    private Date dataInclusao;

    private SubCanalLegado subcanal;

    private ApostaLotomaniaLegado apostaOriginalEspelho;

    private ComboApostaLegado comboAposta;

    private Long nuCombo;

    private Boolean indicadorBolao;

    private ReservaCotaBolaoLegado reservaCotaBolao;

    private Integer qtdTeimosinhas;

    private String prognostico;

    private CamposEspecificosModalidade camposEspecificosModalidade;
}
