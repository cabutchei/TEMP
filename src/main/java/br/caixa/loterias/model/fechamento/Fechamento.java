package br.caixa.loterias.model.fechamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Dummy implementation created to satisfy missing dependency references.
 */
@Getter
@Setter
@Entity
@Table(name = "_FECHAMENTO", schema = "LCE")
public class Fechamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_FECHAMENTO", nullable = false)
    private Long id;

    @Column(name = "NU_CONCURSO")
    private Long idConcurso;

    @Column(name = "NU_MODALIDADE")
    private Long idModalidade;
}
