package br.caixa.loterias.model.itemcompravel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompravelCountDTO {
    private Long totalItensCompraveis;
    private Long totalApostasIndividuais;
    private Long totalCotasBolao;
}
