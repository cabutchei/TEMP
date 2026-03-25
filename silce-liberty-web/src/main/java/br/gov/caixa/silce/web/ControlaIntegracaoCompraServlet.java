package br.gov.caixa.silce.web;

import java.io.BufferedReader;
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

@WebServlet(urlPatterns = "/integracao/compra")
public class ControlaIntegracaoCompraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String[] JNDI_NAMES = {
        "java:app/silce-liberty-ejb-1.0-SNAPSHOT/ControlaIntegracaoCompraBean!br.gov.caixa.silce.negocio.interfaces.local.ControlaIntegracaoCompraLocal",
        "java:global/silce-liberty-ear-1.0-SNAPSHOT/silce-liberty-ejb-1.0-SNAPSHOT/ControlaIntegracaoCompraBean!br.gov.caixa.silce.negocio.interfaces.local.ControlaIntegracaoCompraLocal"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("Use POST " + req.getRequestURI());
        out.println("Envie o JSON da MsgNuvemIntegracaoCompraSolicitacao no corpo da requisicao.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            String jsonSolicitacao = readBody(req);

            if (jsonSolicitacao.trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("ERRO");
                out.println("O corpo da requisicao esta vazio.");
                return;
            }

            Object bean = lookupBean();
            invoke(bean, jsonSolicitacao);

            out.println("OK");
            out.println("Solicitacao encaminhada para ControlaIntegracaoCompraBean.");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("ERRO");
            out.println(e.getMessage());
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;

            if (cause instanceof IllegalArgumentException) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            out.println("ERRO");
            out.println(cause.getClass().getName() + ": " + cause.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("ERRO");
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

    private void invoke(Object bean, String jsonSolicitacao)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Method method = bean.getClass().getMethod("trateNotificacaoCompraNuvemJson", String.class);
        method.invoke(bean, jsonSolicitacao);
    }

    private String readBody(HttpServletRequest req) throws IOException {
        StringBuilder json = new StringBuilder();

        try (BufferedReader reader = req.getReader()) {
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line).append('\n');
            }
        }

        return json.toString();
    }
}
