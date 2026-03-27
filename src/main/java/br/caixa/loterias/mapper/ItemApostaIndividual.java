package br.caixa.loterias.mapper;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import br.caixa.loterias.model.combo.Combo;
import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.enums.situacaoapostaindividual.SituacaoApostaIndividualEnum;
import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.model.matrizprognostico.MatrizPrognostico;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Essa classe é uma projeção que enriquece uma {@link ApostaIndividual} com o {@link ItemCompravel} associado
 */
public final class ItemApostaIndividual {

    private final ItemCompravel item;

    public ItemApostaIndividual(ItemCompravel item) {
        this.item = Objects.requireNonNull(item, "item não pode ser null");

        if (!item.isAposta()) {
            throw new IllegalArgumentException("Item não é uma aposta individual");
        }
        if (item.getAposta() == null) {
            throw new IllegalArgumentException("Item não contém aposta");
        }
    }

    public ItemCompravel getItemCompravel() {
        return item;
    }

    public Long getItemCompravelId() {
        return item.getId();
    }

    public Compra getCompra() {
        return item.getCompra();
    }

    public Long getIdLegado() {
        return item.getApostaLegado() != null
                ? item.getApostaLegado()
                : aposta().getIdLegado();
    }

    public Long getId() {
        return aposta().getId();
    }

    public Boolean getIndicadorCombo() {
        return aposta().getIndicadorCombo();
    }

    public Integer getTeimosinhaQtd() {
        return aposta().getTeimosinhaQtd();
    }

    public Integer getTeimosinhaConcursoInicial() {
        return aposta().getTeimosinhaConcursoInicial();
    }

    public Boolean getTeimosinhaBilheteTroca() {
        return aposta().getTeimosinhaBilheteTroca();
    }

    public Boolean getEspelho() {
        return aposta().getEspelho();
    }

    public Boolean getGeraEspelho() {
        return aposta().getGeraEspelho();
    }

    public Long getApostaVinculoEspelho() {
        return aposta().getApostaVinculoEspelho();
    }

    public BigDecimal getValor() {
        return aposta().getValor();
    }

    public boolean isSurpresinha() {
        return aposta().isSurpresinha();
    }

    public Integer getConcursoAlvo() {
        return aposta().getConcursoAlvo();
    }

    public SituacaoApostaIndividualEnum getSituacao() {
        return aposta().getSituacao();
    }

    public Long getTipoConcurso() {
        return aposta().getTipoConcurso();
    }

    public Long getModalidade() {
        return aposta().getModalidade();
    }

    public String getSerieBilhete() {
        return aposta().getSerieBilhete();
    }

    public Long getNsuAposta() {
        return aposta().getNsuAposta();
    }

    public LocalDateTime getTimestampInclusao() {
        return aposta().getTimestampInclusao();
    }

    public LocalDateTime getTimestampEnvioSispl() {
        return aposta().getTimestampEnvioSispl();
    }

    public LocalDateTime getTimestampEfetivacaoSispl() {
        return aposta().getTimestampEfetivacaoSispl();
    }

    public LocalDateTime getTimestampFinalizacaoProcessamento() {
        return aposta().getTimestampFinalizacaoProcessamento();
    }

    public LocalDateTime getTimestampAlteracaoSituacao() {
        return aposta().getTimestampAlteracaoSituacao();
    }

    public Combo getCombo() {
        return aposta().getCombo();
    }

    public List<MatrizPrognostico> getMatrizesPrognostico() {
        return aposta().getMatrizesPrognostico();
    }

    private ApostaIndividual aposta() {
        return item.getAposta();
    }
}
