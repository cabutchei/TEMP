package br.caixa.loterias.model.apostador;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "_APOSTADOR", schema = "LCE")
public class Apostador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NU_APOSTADOR", nullable = false)
    private Long id;

    /**
     * CPF do apostador.
     */
    @Column(name = "DE_CPF", length = 100)
    private String cpf;

    /**
     * Indica se o apostador foi migrado do legado.
     */
    @Column(name = "IC_MIGRACAO")
    private Boolean indicadorMigracao;

    public Apostador(String cpf) {
        this.cpf = cpf;
    }
}
