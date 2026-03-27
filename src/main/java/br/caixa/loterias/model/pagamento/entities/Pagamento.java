package br.caixa.loterias.model.pagamento.entities;

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
@Table(name = "_PAGAMENTO", schema = "LCE")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_PAGAMENTO", nullable = false)
    private Long id;
}
