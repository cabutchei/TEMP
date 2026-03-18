package br.gov.caixa.silce.dominio.interfaces;

import java.io.Serializable;

public interface Teimosinha extends Serializable {
	public Integer getQuantidadeTeimosinhas();

	public void setQuantidadeTeimosinhas(Integer quantidadeTeimosinhas);

	public boolean contemTeimosinha();

	public int getUltimoConcurso();

	public boolean isConcorrendo(Integer concurso);
}
