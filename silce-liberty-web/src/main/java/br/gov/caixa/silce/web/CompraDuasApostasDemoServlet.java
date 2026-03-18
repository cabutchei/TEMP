package br.gov.caixa.silce.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/demo/compra-duas-apostas")
public class CompraDuasApostasDemoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String[] JNDI_NAMES = {
        "java:app/silce-liberty-ejb-1.0-SNAPSHOT/CompraDuasApostasDemoBean!br.gov.caixa.silce.negocio.aposta.compraupdate.CompraDuasApostasDemoLocal",
        "java:global/silce-liberty-ear-1.0-SNAPSHOT/silce-liberty-ejb-1.0-SNAPSHOT/CompraDuasApostasDemoBean!br.gov.caixa.silce.negocio.aposta.compraupdate.CompraDuasApostasDemoLocal"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Object bean = lookupBean();
            Long compraId = invoke(bean);

            out.println("OK");
            out.println("Compra criada com id=" + compraId);
            out.println("Duas apostas foram gravadas sem reserva de cota.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("ERRO ao executar demo de compra com duas apostas");
            out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private Object lookupBean() throws NamingException {
        InitialContext context = new InitialContext();
        NamingException last = null;

        for (String jndiName : JNDI_NAMES) {
            try {
                return context.lookup(jndiName);
            } catch (NamingException ex) {
                last = ex;
            }
        }

        throw last;
    }

    private Long invoke(Object bean)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Method method = bean.getClass().getMethod("criarCompraComDuasApostas");
        return (Long) method.invoke(bean);
    }
}
