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

@WebServlet(urlPatterns = "/demo/cascade")
public class ApostaCascadeDemoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String[] JNDI_NAMES = {
        "java:app/silce-liberty-ejb/ApostaCascadeDemoBean!br.gov.caixa.silce.negocio.aposta.cascade.ApostaCascadeDemoLocal",
        "java:global/silce-liberty-ear/silce-liberty-ejb/ApostaCascadeDemoBean!br.gov.caixa.silce.negocio.aposta.cascade.ApostaCascadeDemoLocal",
        "java:global/silce-liberty-ear-1.0-SNAPSHOT/silce-liberty-ejb-1.0-SNAPSHOT/ApostaCascadeDemoBean!br.gov.caixa.silce.negocio.aposta.cascade.ApostaCascadeDemoLocal"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Object bean = lookupBean();
            Long apostaId = invoke(bean, req.getParameter("compraId"));

            out.println("OK");
            out.println("Aposta criada com id=" + apostaId);
            out.println("Use ?compraId=<id> para vincular em compra existente.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("ERRO ao executar demo de cascata");
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

    private Long invoke(Object bean, String compraIdParam)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        if (compraIdParam == null || compraIdParam.trim().isEmpty()) {
            Method method = bean.getClass().getMethod("criarApostaComCascata");
            return (Long) method.invoke(bean);
        }

        Long compraId = Long.valueOf(compraIdParam.trim());
        Method method = bean.getClass().getMethod("criarApostaComCascata", Long.class);
        return (Long) method.invoke(bean, compraId);
    }
}
