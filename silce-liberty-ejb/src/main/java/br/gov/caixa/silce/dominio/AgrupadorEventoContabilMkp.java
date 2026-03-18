package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class AgrupadorEventoContabilMkp extends AgrupadorEventoContabil implements Serializable {

	private static final long serialVersionUID = 1L;


	private BigDecimal vendaCotaSIDEC;
	private BigDecimal tarifaCotaSIDEC;
	private BigDecimal vendaCotaNSGD;
	private BigDecimal tarifaCotaNSGD;
	private BigDecimal vendaCotaSIDECPix;
	private BigDecimal tarifaCotaSIDECPix;
	private BigDecimal vendaCotaNSGDPix;
	private BigDecimal tarifaCotaNSGDPix;
	private BigDecimal vendaCusteioSIDEC;
	private BigDecimal tarifaCusteioSIDEC;
	private BigDecimal vendaCusteioNSGD;
	private BigDecimal tarifaCusteioNSGD;
	private BigDecimal totalCotasMKP;
	private BigDecimal totalCusteioMP;
	private Integer totalRegistros;

	public AgrupadorEventoContabilMkp() {
		super();
	}

	public AgrupadorEventoContabilMkp(String dataFechamento, Integer produto, Integer concurso, Integer unidadeLoterica, BigDecimal vendaCotaSIDEC,
		BigDecimal tarifaCotaSIDEC, BigDecimal vendaCotaNSGD, BigDecimal tarifaCotaNSGD, BigDecimal vendaCotaSIDECPix, BigDecimal tarifaCotaSIDECPix,
		BigDecimal vendaCotaNSGDPix, BigDecimal tarifaCotaNSGDPix, BigDecimal totalCotasMKP, BigDecimal vendaCusteioSIDEC, BigDecimal tarifaCusteioSIDEC,
		BigDecimal vendaCusteioNSGD, BigDecimal tarifaCusteioNSGD, BigDecimal totalCusteioMP, Integer dvLoterica, Integer poloLoterica, String nomeFantasiaLoterica) {
		super(dataFechamento, produto, concurso, unidadeLoterica, dvLoterica, poloLoterica, nomeFantasiaLoterica);
		this.vendaCotaSIDEC = vendaCotaSIDEC;
		this.tarifaCotaSIDEC = tarifaCotaSIDEC;
		this.vendaCotaNSGD = vendaCotaNSGD;
		this.tarifaCotaNSGD = tarifaCotaNSGD;
		this.vendaCotaSIDECPix = vendaCotaSIDECPix;
		this.tarifaCotaSIDECPix = tarifaCotaSIDECPix;
		this.vendaCotaNSGDPix = vendaCotaNSGDPix;
		this.tarifaCotaNSGDPix = tarifaCotaNSGDPix;
		this.totalCotasMKP = totalCotasMKP;
		this.vendaCusteioSIDEC = vendaCusteioSIDEC;
		this.tarifaCusteioSIDEC = tarifaCusteioSIDEC;
		this.vendaCusteioNSGD = vendaCusteioNSGD;
		this.tarifaCusteioNSGD = tarifaCusteioNSGD;
		this.totalCusteioMP = totalCusteioMP;
	}

	public BigDecimal getVendaCotaSIDEC() {
		return vendaCotaSIDEC;
	}

	public void setVendaCotaSIDEC(BigDecimal vendaCotaSIDEC) {
		this.vendaCotaSIDEC = vendaCotaSIDEC;
	}

	public BigDecimal getTarifaCotaSIDEC() {
		return tarifaCotaSIDEC;
	}

	public void setTarifaCotaSIDEC(BigDecimal tarifaCotaSIDEC) {
		this.tarifaCotaSIDEC = tarifaCotaSIDEC;
	}

	public BigDecimal getVendaCotaNSGD() {
		return vendaCotaNSGD;
	}

	public void setVendaCotaNSGD(BigDecimal vendaCotaNSGD) {
		this.vendaCotaNSGD = vendaCotaNSGD;
	}

	public BigDecimal getTarifaCotaNSGD() {
		return tarifaCotaNSGD;
	}

	public void setTarifaCotaNSGD(BigDecimal tarifaCotaNSGD) {
		this.tarifaCotaNSGD = tarifaCotaNSGD;
	}

	public BigDecimal getVendaCotaSIDECPix() {
		return vendaCotaSIDECPix;
	}

	public void setVendaCotaSIDECPix(BigDecimal vendaCotaSIDECPix) {
		this.vendaCotaSIDECPix = vendaCotaSIDECPix;
	}

	public BigDecimal getTarifaCotaSIDECPix() {
		return tarifaCotaSIDECPix;
	}

	public void setTarifaCotaSIDECPix(BigDecimal tarifaCotaSIDECPix) {
		this.tarifaCotaSIDECPix = tarifaCotaSIDECPix;
	}

	public BigDecimal getVendaCotaNSGDPix() {
		return vendaCotaNSGDPix;
	}

	public void setVendaCotaNSGDPix(BigDecimal vendaCotaNSGDPix) {
		this.vendaCotaNSGDPix = vendaCotaNSGDPix;
	}

	public BigDecimal getTarifaCotaNSGDPix() {
		return tarifaCotaNSGDPix;
	}

	public void setTarifaCotaNSGDPix(BigDecimal tarifaCotaNSGDPix) {
		this.tarifaCotaNSGDPix = tarifaCotaNSGDPix;
	}

	public BigDecimal getVendaCusteioSIDEC() {
		return vendaCusteioSIDEC;
	}

	public void setVendaCusteioSIDEC(BigDecimal vendaCusteioSIDEC) {
		this.vendaCusteioSIDEC = vendaCusteioSIDEC;
	}

	public BigDecimal getTarifaCusteioSIDEC() {
		return tarifaCusteioSIDEC;
	}

	public void setTarifaCusteioSIDEC(BigDecimal tarifaCusteioSIDEC) {
		this.tarifaCusteioSIDEC = tarifaCusteioSIDEC;
	}

	public BigDecimal getVendaCusteioNSGD() {
		return vendaCusteioNSGD;
	}

	public void setVendaCusteioNSGD(BigDecimal vendaCusteioNSGD) {
		this.vendaCusteioNSGD = vendaCusteioNSGD;
	}

	public BigDecimal getTarifaCusteioNSGD() {
		return tarifaCusteioNSGD;
	}

	public void setTarifaCusteioNSGD(BigDecimal tarifaCusteioNSGD) {
		this.tarifaCusteioNSGD = tarifaCusteioNSGD;
	}

	public BigDecimal getTotalCotasMKP() {
		return totalCotasMKP;
	}

	public void setTotalCotasMKP(BigDecimal totalCotasMKP) {
		this.totalCotasMKP = totalCotasMKP;
	}

	public BigDecimal getTotalCusteioMP() {
		return totalCusteioMP;
	}

	public void setTotalCusteioMP(BigDecimal totalCusteioMP) {
		this.totalCusteioMP = totalCusteioMP;
	}

	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	private String getTotalCusteioMPString() {
		return (getTotalCusteioMP() == null) ? "0,0" : getTotalCusteioMP().toString().replace(".", ",");
	}

	private String getTarifaCusteioNSGDString() {
		return (getTarifaCusteioNSGD() == null) ? "0,0" : getTarifaCusteioNSGD().toString().replace(".", ",");
	}

	private String getVendaCusteioNSGDString() {
		return (getVendaCusteioNSGD() == null) ? "0,0" : getVendaCusteioNSGD().toString().replace(".", ",");
	}

	private String getTarifaCusteioSIDECString() {
		return (getTarifaCusteioSIDEC() == null) ? "0,0" : getTarifaCusteioSIDEC().toString().replace(".", ",");
	}

	private String getVendaCusteioSIDECString() {
		return (getVendaCusteioSIDEC() == null) ? "0,0" : getVendaCusteioSIDEC().toString().replace(".", ",");
	}

	private String getTotalCotasMkpString() {
		return (getTotalCotasMKP() == null) ? "0,0" : getTotalCotasMKP().toString().replace(".", ",");
	}

	private String getTarifaCotaNSGDString() {
		return (getTarifaCotaNSGD() == null) ? "0,0" : getTarifaCotaNSGD().toString().replace(".", ",");
	}

	private String getVendaCotaNSGDString() {
		return (getVendaCotaNSGD() == null) ? "0,0" : getVendaCotaNSGD().toString().replace(".", ",");
	}

	private String getTarifaCotaSIDECString() {
		return (getTarifaCotaSIDEC() == null) ? "0,0" : getTarifaCotaSIDEC().toString().replace(".", ",");
	}

	private String getVendaCotaSIDECString() {
		return (getVendaCotaSIDEC() == null) ? "0,0" : getVendaCotaSIDEC().toString().replace(".", ",");
	}

	private String getVendaCotaSIDECPixString() {
		return (getVendaCotaSIDECPix() == null) ? "0,0" : getVendaCotaSIDECPix().toString().replace(".", ",");
	}

	private String getTarifaCotaSIDECPixString() {
		return (getTarifaCotaSIDECPix() == null) ? "0,0" : getTarifaCotaSIDECPix().toString().replace(".", ",");
	}

	private String getVendaCotaNSGDPixString() {
		return (getVendaCotaNSGDPix() == null) ? "0,0" : getVendaCotaNSGDPix().toString().replace(".", ",");
	}

	private String getTarifaCotaNSGDPixString() {
		return (getTarifaCotaNSGDPix() == null) ? "0,0" : getTarifaCotaNSGDPix().toString().replace(".", ",");
	}

	@Override
	public String toString() {
		StringBuilder dataBuilder = new StringBuilder();
		dataBuilder.append(getDataFechamentoFormatada() + ";");
		dataBuilder.append(getProdutoString() + ";");
		dataBuilder.append(getConcursoString() + ";");
		dataBuilder.append(getUnidadeLotericaString() + ";");
		dataBuilder.append(getVendaCotaSIDECString() + ";");
		dataBuilder.append(getTarifaCotaSIDECString() + ";");
		dataBuilder.append(getVendaCotaNSGDString() + ";");
		dataBuilder.append(getTarifaCotaNSGDString() + ";");
		dataBuilder.append(getVendaCotaSIDECPixString() + ";");
		dataBuilder.append(getTarifaCotaSIDECPixString() + ";");
		dataBuilder.append(getVendaCotaNSGDPixString() + ";");
		dataBuilder.append(getTarifaCotaNSGDPixString() + ";");
		dataBuilder.append(getTotalCotasMkpString() + ";");
		dataBuilder.append(getVendaCusteioSIDECString() + ";");
		dataBuilder.append(getTarifaCusteioSIDECString() + ";");
		dataBuilder.append(getVendaCusteioNSGDString() + ";");
		dataBuilder.append(getTarifaCusteioNSGDString() + ";");
		dataBuilder.append(getTotalCusteioMPString() + "\n");
		return dataBuilder.toString();
	}

	public static String getColunmNames() {
		StringBuilder dataBuilder = new StringBuilder();
		dataBuilder.append(getTituloCsvFile("UL"));
		dataBuilder.append("DATA;");
		dataBuilder.append("PRODUTO;");
		dataBuilder.append("CONCURSO;");
		dataBuilder.append("UNIDADE LOTERICA;");
		dataBuilder.append("SIDEC VENDA-COTAS-DIGITAIS (39000-3);");
		dataBuilder.append("SIDEC VENDA-TARIFA-SERVICO (39001-1);");
		dataBuilder.append("NSGD VENDA-COTAS-DIGITAIS (39032-1);");
		dataBuilder.append("NSGD VENDA-TARIFA-SERVICO (39035-6);");
		dataBuilder.append("SIDEC VENDA COTAS PIX (39129-8);");
		dataBuilder.append("SIDEC VENDA TARIFA PIX (39130-1);");
		dataBuilder.append("NSGD VENDA COTAS PIX (39131-0);");
		dataBuilder.append("NSGD VENDA TARIFA PIX (39132-8);");
		dataBuilder.append("TOTAL VENDAS MARKETPLACE REPASSADO AO LOTERICO;");
		dataBuilder.append("SIDEC TAR-USO-MP-COTA-DIGITAIS (39031-3);");
		dataBuilder.append("SIDEC TAR-USO-MP-TARIFA-SERVICO (39031-3);");
		dataBuilder.append("NSGD TAR-USO-MP-COTA-DIGITAIS (39037-2);");
		dataBuilder.append("NSGD TAR-USO-MP-TARIFA-SERVICO (39037-2);");
		dataBuilder.append("TOTAL DE TARIFA DE USO DO MEIO DE PAGAMENTO RECEBIDO (39030-5)\n");
		return dataBuilder.toString();
	}

	public static String getSubTotal(List<AgrupadorEventoContabilMkp> eventos) {
		BigDecimal vendaCotaSIDECtotal = new BigDecimal(0.0);
		BigDecimal tarifaCotaSIDECtotal = new BigDecimal(0.0);
		BigDecimal vendaCotaNSGDtotal = new BigDecimal(0.0);
		BigDecimal tarifaCotaNSGDtotal = new BigDecimal(0.0);
		BigDecimal vendaCotaSIDECPixtotal = new BigDecimal(0.0);
		BigDecimal tarifaCotaSIDECPixtotal = new BigDecimal(0.0);
		BigDecimal vendaCotaNSGDPixtotal = new BigDecimal(0.0);
		BigDecimal tarifaCotaNSGDPixtotal = new BigDecimal(0.0);
		BigDecimal totalCotasMKPtotal = new BigDecimal(0.0);
		BigDecimal vendaCusteioSIDECtotal = new BigDecimal(0.0);
		BigDecimal tarifaCusteioSIDECtotal = new BigDecimal(0.0);
		BigDecimal vendaCusteioNSGDtotal = new BigDecimal(0.0);
		BigDecimal tarifaCusteioNSGDtotal = new BigDecimal(0.0);
		BigDecimal totalCusteioMPtotal = new BigDecimal(0.0);

		if (!eventos.isEmpty()) {
			for (AgrupadorEventoContabilMkp evento : eventos) {
				vendaCotaSIDECtotal = vendaCotaSIDECtotal.add(plusValue(evento.getVendaCotaSIDEC()));
				tarifaCotaSIDECtotal = tarifaCotaSIDECtotal.add(plusValue(evento.getTarifaCotaSIDEC()));
				vendaCotaNSGDtotal = vendaCotaNSGDtotal.add(plusValue(evento.getVendaCotaNSGD()));
				tarifaCotaNSGDtotal = tarifaCotaNSGDtotal.add(plusValue(evento.getTarifaCotaNSGD()));
				vendaCotaSIDECPixtotal = vendaCotaSIDECPixtotal.add(plusValue(evento.getVendaCotaSIDECPix()));
				tarifaCotaSIDECPixtotal = tarifaCotaSIDECPixtotal.add(plusValue(evento.getTarifaCotaSIDECPix()));
				vendaCotaNSGDPixtotal = vendaCotaNSGDPixtotal.add(plusValue(evento.getVendaCotaNSGDPix()));
				tarifaCotaNSGDPixtotal = tarifaCotaNSGDPixtotal.add(plusValue(evento.getTarifaCotaNSGDPix()));
				totalCotasMKPtotal = totalCotasMKPtotal.add(plusValue(evento.getTotalCotasMKP()));
				vendaCusteioSIDECtotal = vendaCusteioSIDECtotal.add(plusValue(evento.getVendaCusteioSIDEC()));
				tarifaCusteioSIDECtotal = tarifaCusteioSIDECtotal.add(plusValue(evento.getTarifaCusteioSIDEC()));
				vendaCusteioNSGDtotal = vendaCusteioNSGDtotal.add(plusValue(evento.getVendaCusteioNSGD()));
				tarifaCusteioNSGDtotal = tarifaCusteioNSGDtotal.add(plusValue(evento.getTarifaCusteioNSGD()));
				totalCusteioMPtotal = totalCusteioMPtotal.add(plusValue(evento.getTotalCusteioMP()));
			}
		}

		return "SUBTOTAL;;;;"
			+ vendaCotaSIDECtotal.toString().replace(".", ",") + ";"
			+ tarifaCotaSIDECtotal.toString().replace(".", ",") + ";"
			+ vendaCotaNSGDtotal.toString().replace(".", ",") + ";"
			+ tarifaCotaNSGDtotal.toString().replace(".", ",") + ";"
			+ vendaCotaSIDECPixtotal.toString().replace(".", ",") + ";"
			+ tarifaCotaSIDECPixtotal.toString().replace(".", ",") + ";"
			+ vendaCotaNSGDPixtotal.toString().replace(".", ",") + ";"
			+ tarifaCotaNSGDPixtotal.toString().replace(".", ",") + ";"
			+ totalCotasMKPtotal.toString().replace(".", ",") + ";"
			+ vendaCusteioSIDECtotal.toString().replace(".", ",") + ";"
			+ tarifaCusteioSIDECtotal.toString().replace(".", ",") + ";"
			+ vendaCusteioNSGDtotal.toString().replace(".", ",") + ";"
			+ tarifaCusteioNSGDtotal.toString().replace(".", ",") + ";"
			+ totalCusteioMPtotal.toString().replace(".", ",") + "\n";
	}

}