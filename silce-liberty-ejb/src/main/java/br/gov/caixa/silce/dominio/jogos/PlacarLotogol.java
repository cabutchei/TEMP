package br.gov.caixa.silce.dominio.jogos;

public enum PlacarLotogol {
	ZERO(0, "0", 0),
	UM(1, "1", 1),
	DOIS(2, "2", 2),
	TRES(3, "3", 3),
	MAIS(4, "+", 4);
	
	private final Integer coluna;
	private final String placar;
	private final Integer valorPlacar;
	
	private PlacarLotogol(Integer coluna, String placar, Integer valorPlacar){
		this.coluna = coluna;
		this.placar = placar;
		this.valorPlacar = valorPlacar;
	}
	
	public Integer getColuna(){
		return coluna;
	}

	/**
	 * @return "0", "1", "2", "3" ou "+"
	 */
	public String getPlacar() {
		return placar;
	}

	/**
	 * @return 0, 1, 2, 3 ou 4
	 */
	public Integer getValorPlacar() {
		return valorPlacar;
	}
	
	public static Integer getColunaPlacar(String placar){
		
		for (PlacarLotogol placarLotogol : values()){
			if(placarLotogol.getPlacar().equals(placar)) {
				return placarLotogol.getColuna();
			}
		}
		
		return null;
	}
	
	public static PlacarLotogol getByValue(int value) {
		for (PlacarLotogol placarLotogol : PlacarLotogol.values()) {
			if(placarLotogol.getColuna().equals(value)) {
				return placarLotogol;
			}
		}
		return null;
	}

}
