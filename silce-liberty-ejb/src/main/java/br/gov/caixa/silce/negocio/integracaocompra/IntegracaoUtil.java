package br.gov.caixa.silce.negocio.integracaocompra;

import java.util.Calendar;
import java.util.Date;


import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Hora;

public class IntegracaoUtil {

    public static Data extrairData(Date date) {
        return date != null ? new Data(date) : null;
    }

    public static Hora extrairHora(Date date) {
        return date != null ? new Hora(date) : null;
    }

    public static Integer getParticao(Long nsu) {
        return (int) (nsu % 10);
    }

    public static Integer getMesAtual() {
        return DataUtil.getDataAtual().get(Calendar.MONTH);
    }

    public static Integer getAnoAtual() {
        return DataUtil.getDataAtual().get(Calendar.YEAR);
    }
    
}
