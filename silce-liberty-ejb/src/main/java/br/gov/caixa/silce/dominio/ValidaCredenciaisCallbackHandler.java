package br.gov.caixa.silce.dominio;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class ValidaCredenciaisCallbackHandler implements CallbackHandler {

	String matricula;
	String senha;

	public ValidaCredenciaisCallbackHandler(String matricula, String senha) {
		this.matricula = matricula;
		this.senha = senha;
	}

	@Override
	public void handle(Callback[] arg0) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < arg0.length; i++) {
			if (arg0[i] instanceof NameCallback) {
				((NameCallback) arg0[i]).setName(this.matricula);
			} else if (arg0[i] instanceof PasswordCallback) {
				((PasswordCallback) arg0[i]).setPassword(this.senha.toCharArray());
			}
		}
	}
}
