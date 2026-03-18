package br.gov.caixa.silce.dominio;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Validate;

public class SumarizadorBatimento implements Serializable {

	private static final long serialVersionUID = 1L;

	private Data data;
	private Decimal valorBruto;
	private Decimal valorComissao;
	private Decimal valorComissaoAjustada;
	private Decimal valorCota;
	private Decimal valorTarifa;
	private Decimal valorCotaCusteio;
	private Decimal valorTarifaCusteio;

	public void setMes(Integer mes) {
		Validate.notNull(mes, "mes");

		Calendar instance = getData();

		// No calendar o mes comecao com 0, enquanto
		// no db2 comece com 1
		instance.set(Calendar.MONTH, mes - 1);
		this.data = new Data(instance);
	}

	public Integer getId() {
		return getMes().hashCode();
	}

	public Integer getAno() {
		if (this.data == null) {
			return null;
		}
		String pattern = "yyyy";
		String date = getDataFormatada(pattern);
		return Integer.valueOf(date);
	}

	public void setAno(Integer ano) {
		if (ano == null) {
			setAnoByData(null);
		} else {
			Calendar instance = getData();
			instance.set(Calendar.YEAR, ano);
			this.data = new Data(instance);
		}
	}

	private void setAnoByData(Data dataEnviada) {
		this.data = dataEnviada;
	}

	private Calendar getData() {
		Calendar instance;
		if (this.data == null) {
			instance = Calendar.getInstance();
		} else {
			instance = this.data.getCalendar();
		}
		instance.set(Calendar.DAY_OF_MONTH, 1);
		return instance;
	}

	public Integer getMesNum() {
		String date = getDataFormatada("MM");
		return Integer.valueOf(date);
	}

	public String getMes() {
		return getDataFormatada("MMMM/yyyy");
	}

	public String getDataFormatada(String pattern) {
		DateFormat formater = new SimpleDateFormat(pattern, Locale.getDefault());
		return formater.format(this.data.getTime());
	}

	public Decimal getValorBruto() {
		return convertSafe(valorBruto);
	}

	public void setValorBruto(Decimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Decimal getValorComissaoAjustada() {
		return convertSafe(valorComissaoAjustada);
	}

	public void setValorComissaoAjustada(Decimal valorComissaoAjustada) {
		this.valorComissaoAjustada = valorComissaoAjustada;
	}

	public Decimal getValorComissao() {
		return convertSafe(this.valorComissao);
	}

	public void setValorComissao(Decimal valor) {
		this.valorComissao = valor;
	}

	public Decimal getValorCota() {
		return convertSafe(valorCota);
	}

	public void setValorCota(Decimal valorCota) {
		this.valorCota = valorCota;
	}

	public Decimal getValorTarifa() {
		return convertSafe(valorTarifa);
	}

	public void setValorTarifa(Decimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public Decimal getValorCotaCusteio() {
		return convertSafe(valorCotaCusteio);
	}

	public void setValorCotaCusteio(Decimal valorCotaCusteio) {
		this.valorCotaCusteio = valorCotaCusteio;
	}

	public Decimal getValorTarifaCusteio() {
		return convertSafe(valorTarifaCusteio);
	}

	public void setValorTarifaCusteio(Decimal valorTarifaCusteio) {
		this.valorTarifaCusteio = valorTarifaCusteio;
	}

	private Decimal convertSafe(Decimal db) {
		if (db == null) {
			return Decimal.ZERO;
		}
		return new Decimal(db);
	}

}
