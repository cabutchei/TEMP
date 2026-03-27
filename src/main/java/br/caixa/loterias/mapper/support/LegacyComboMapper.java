package br.caixa.loterias.mapper.support;

import br.caixa.loterias.model.combo.Combo;
import br.caixa.loterias.model.legado.ComboApostaLegado;
import br.caixa.loterias.model.legado.TipoComboLegado;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class LegacyComboMapper {

    @Named("toComboApostaLegado")
    public ComboApostaLegado toComboApostaLegado(Combo combo) {
        if (combo == null) {
            return null;
        }

        TipoComboLegado tipoComboLegado = toTipoComboLegado(combo);
        ComboApostaLegado comboLegado = new ComboApostaLegado(tipoComboLegado);
        comboLegado.setId(combo.getId());
        comboLegado.setMes(combo.getMes());
        comboLegado.setDataInclusao(combo.getDataInclusao() != null ? combo.getDataInclusao().toString() : null);
        comboLegado.setTipoCombo(tipoComboLegado);
        return comboLegado;
    }

    @Named("toTipoComboLegado")
    public TipoComboLegado toTipoComboLegado(Combo combo) {
        if (combo == null || combo.getTipoCombo() == null) {
            return null;
        }

        TipoComboLegado tipoComboLegado = new TipoComboLegado();
        tipoComboLegado.setId(combo.getTipoCombo().getCodigo());
        tipoComboLegado.setNome(combo.getTipoCombo().getNome());
        tipoComboLegado.setDescricao(combo.getTipoCombo().getDescricao());
        return tipoComboLegado;
    }
}
