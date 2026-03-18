package br.gov.caixa.silce.negocio.aposta.cascade;

import javax.ejb.Local;

@Local
public interface ApostaCascadeDemoLocal {

	Long criarApostaComCascata();

	Long criarApostaComCascata(Long compraId);
}

