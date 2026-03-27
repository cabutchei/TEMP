package br.caixa.loterias.model.matrizprognostico;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "LCE", name = "_MATRIZ_PROGNOSTICO")
public class MatrizPrognostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_MATRIZ", nullable = false)
    private Long id;

    /**
     * Valor preenchido na matriz (números, prognósticos, etc.).
     */
    @Column(name = "DE_VALOR", nullable = false)
    private String valor;

    /**
     * Aposta individual à qual esta matriz está associada.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "NU_APOSTA", referencedColumnName = "NU_APOSTA")
    private ApostaIndividual apostaAssociada;

    /**
     * Tipo da matriz.
     */
    @Column(name = "NU_TIPO_MATRIZ", nullable = false)
    @Convert(converter = TipoMatrizPrognosticoEnumConverter.class)
    private TipoMatrizPrognosticoEnum tipoMatriz;

    @Column(name = "NU_QTD_MARCACOES", nullable = true)
    private Long quantidadeMarcacoes;
}