package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.openjpa.persistence.jdbc.Strategy;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.config.DatabaseConfig;
import br.gov.caixa.silce.dominio.openjpa.DataValueHandler;
import br.gov.caixa.silce.dominio.openjpa.DecimalValueHandler;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;

@Entity
@Table(name = "LCETB019_COMISSAO", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
	@NamedQuery(name=Comissao.NQ_SELECT_BY_MEIO_PAGAMENTO, 
		query="Select c from Comissao c where c.meioPagamento.id=?1 order by c.dataInicio asc"),
	@NamedQuery(name=Comissao.NQ_SELECT_BY_MEIO_PAGAMENTO_DATA_MAXIMA, 
		query="Select c from Comissao c where c.meioPagamento.id=?1 and c.dataInicio <= ?2 order by c.dataInicio desc"),
	@NamedQuery(name = Comissao.NQ_SELECT_BY_MEIO_PAGAMENTO_DATA, query = "Select c from Comissao c where c.meioPagamento.id=?1 and c.dataInicio = ?2")
})
public class Comissao extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO = "Comissao.findByMeioPagamento";
	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO_DATA_MAXIMA = "Comissao.findByMeioPagamentoDataMax";
	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO_DATA = "Comissao.findByMeioPagamentoData";
	
	private static final long serialVersionUID = 989041624807244370L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NU_COMISSAO")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;
	
	@Column(name="PC_COMISSAO")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal percentual;
	
	@Column(name = "DT_INICIO_COMISSAO")
	@Strategy(DataValueHandler.STRATEGY_NAME)
	@Temporal(TemporalType.DATE)
	private Data dataInicio;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public Decimal getPercentual() {
		return percentual;
	}

	public void setPercentual(Decimal percentual) {
		this.percentual = percentual;
	}

	public Data getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Data dataInicio) {
		this.dataInicio = dataInicio;
	}


}
