package br.gov.caixa.silce.dominio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@Table(name = "LCETB061_CUSTEIO_UNIDADE_LTRCA", schema = DatabaseConfig.SCHEMA)
@NamedQueries({
		@NamedQuery(name = Custeio.NQ_SELECT_BY_MEIO_PAGAMENTO, query = "Select c from Custeio c join fetch c.meioPagamento where c.meioPagamento.id=?1 order by c.dataInicio desc"),
		@NamedQuery(name = Custeio.NQ_SELECT_BY_MEIO_PAGAMENTO_DATA, query = "Select c from Custeio c where c.meioPagamento.id=?1 and c.dataInicio = ?2"),
		@NamedQuery(name = Custeio.NQ_SELECT_ALL, query = "Select c from Custeio c"),
		@NamedQuery(name = Custeio.NQ_SELECT_TODOS, query = "Select custeio from Custeio custeio join fetch custeio.meioPagamento order by custeio.dataInicio desc")
})

@NamedNativeQueries({
		@NamedNativeQuery(name = Custeio.NQ_SELECT_ALL_CUSTEIOS, query = "SELECT M.DE_MEIO_PAGAMENTO_PREMIO, C.PC_CUSTEIO_UNIDADE_LOTERICA, C.DT_INICIO_CUSTEIO "
			+ "FROM LCETB061_CUSTEIO_UNIDADE_LTRCA C"
			+ "JOIN LCETB018_MEIO_PAGAMENTO M"
			+ "ON C.NU_MEIO_PAGAMENTO = M.NU_MEIO_PAGAMENTO")
})

public class Custeio extends AbstractEntidade<Long> {

	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO = "Custeio.findByMeioPagamento";
	public static final String NQ_SELECT_BY_MEIO_PAGAMENTO_DATA = "Custeio.findByMeioPagamentoData";
	public static final String NQ_SELECT_ALL = "Custeio.consulteAllCusteios";
	public static final String NQ_SELECT_ALL_CUSTEIOS = "Custeio.findTodos";
	public static final String NQ_SELECT_TODOS = "Custeio.findTudo";
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_CUSTEIO_UNIDADE_LOTERICA")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NU_MEIO_PAGAMENTO", referencedColumnName = "NU_MEIO_PAGAMENTO")
	private MeioPagamento meioPagamento;
	
	@Column(name = "PC_CUSTEIO_UNIDADE_LOTERICA")
	@Strategy(DecimalValueHandler.STRATEGY_NAME)
	private Decimal percentual;
	
	@Column(name = "DT_INICIO_CUSTEIO")
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
