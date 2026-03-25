package br.gov.caixa.silce.dominio.servico.compra;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representação de uma aposta como enviada pelo silce-compra-processamento.
 */
public class NuvemIntegracaoAposta extends DTONuvem implements IdLegado, IdNuvem {

    public Long idNuvem;

    public Long idLegado;

    public NuvemIntegracaoCompra compra;

    public Integer modalidade;

    public Integer tipoConcurso;

    public Integer indicadorSurpresinha;

    public BigDecimal valor;

    public NuvemIntegracaoApostaComprada comprada;

    public Integer concursoAlvo;

    public Date dataInclusao;

    public Integer subcanal;

    public Boolean apostaOriginalEspelho;

    public Long idNuvemCombo;

    public NuvemIntegracaoReserva reservaCotaBolao;

    public Integer qtdTeimosinhas;

    public String prognostico;

    public String comboAposta;

    public String nuCombo;

    public Boolean indicadorBolao;

    public String camposEspecificosModalidade;

    public NuvemIntegracaoAposta() {
    }

    public Long getIdNuvem() {
        return this.idNuvem;
    }

    public Long getIdLegado() {
        return this.idLegado;
    }
}
