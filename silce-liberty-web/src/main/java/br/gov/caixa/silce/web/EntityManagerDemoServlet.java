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

@WebServlet(urlPatterns = "/demo/entity")
public class EntityManagerDemoServlet  extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String[] JNDI_NAMES = {
        "java:app/silce-liberty-ejb-1.0-SNAPSHOT/EntityDemoBean!br.gov.caixa.silce.negocio.aposta.entitydemo.EntityDemoLocal",
        "java:global/silce-liberty-ear-1.0-SNAPSHOT/silce-liberty-ejb-1.0-SNAPSHOT/EntityDemoBean!br.gov.caixa.silce.negocio.aposta.entitydemo.EntityDemoLocal"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Object bean = lookupBean();
            invoke(bean);
            out.println("OK");
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

    private void invoke(Object bean)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

            Method method = bean.getClass().getMethod("demo");
            method.invoke(bean);
    }
}
