package br.caixa.loterias.model.combo;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import br.caixa.loterias.model.enums.tipocomboenum.TipoComboEnum;
import br.caixa.loterias.model.enums.tipocomboenum.TipoComboEnumConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "LCE", name = "COMBO")
@Getter
@Setter
public class Combo implements Serializable {

    /**
     * Identificador do combo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_COMBO")
    private Long id;

    /**
     * Tipo do combo.
     *
     * <p>
     * Exemplo: {@code 1} (Especial)
     */
    @Column(name = "NU_TIPO_COMBO")
    @Convert(converter = TipoComboEnumConverter.class)
    private TipoComboEnum tipoCombo;

    /**
     * Número do mês associado ao combo.
     */
    @Column(name = "NU_MES")
    private Long mes;

    /**
     * Timestamp da inclusão do combo.
     */
    @Column(name = "TS_INCLUSAO")
    private LocalDateTime dataInclusao;

    /**
     * Lista de apostas associadas a este combo.
     */
    @OneToMany(mappedBy = "combo")
    private List<ApostaIndividual> apostas;
}
