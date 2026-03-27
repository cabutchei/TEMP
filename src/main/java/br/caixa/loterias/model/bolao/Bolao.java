package br.caixa.loterias.model.bolao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "_BOLAO", schema = "LCE")
public class Bolao {

    @Id
    @Column(name = "NU_BOLAO", nullable = false)
    private Long id;

    /**
     * Quantidade de cotas disponíveis neste bolão.
     */
    @Column(name = "NU_QTD_COTAS")
    private Integer qtdCotas;

    /**
     * Código do terminal lotérico onde o bolão foi registrado.
     */
    @Column(name = "NU_TERMINAL_LOTERICO")
    private Integer terminalLoterico;

    /**
     * Data e hora do registro do bolão.
     */
    @Column(name = "TS_REGISTRO")
    private LocalDateTime timestampRegistro;

    /**
     * Identificador da lotérica onde o bolão foi registrado.
     */
    @Column(name = "NU_LOTERICA")
    private Long lotericaId;

    /**
     * Data e hora da última atualização do bolão.
     */
    @Column(name = "TS_ATUALIZACAO")
    private LocalDateTime timestampAtualizacao;

    /**
     * Identificador da UF (Unidade Federativa) da lotérica.
     */
    @Column(name = "NU_UF_LOTERICA")
    private Long ufLotericaId;

    /**
     * Identificador do município da lotérica.
     */
    @Column(name = "NU_MUNICIPIO_LOTERICA")
    private Long municipioLotericaId;

    /**
     * Identificador do bolão no Marketplace em base 64
     */
    @Column(name = "DE_ID_BOLAO_MKP")
    private String idBolaoMarketplace;
}
