package br.caixa.loterias.model.registroenviointegracao;

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
@Table(name = "_REGISTRO_ENVIO_INTEGRACAO", schema = "LCE")
public class RegistroEnvioIntegracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_REGISTRO_ENVIO_INTEGRACAO", nullable = false)
    private Long id;
}
