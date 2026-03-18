package br.gov.caixa.dominio;

import java.io.Serializable;

public class ProxyConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String user;
	private final String password;
	private final String userDomain;
	private final String host;
	private final String port;
	private final String authScheme;
	
	public ProxyConfig(String user, String password, String userDomain, String host, String port, String authScheme) {
		super();
		this.user = user;
		this.password = password;
		this.userDomain = userDomain;
		this.host = host;
		this.port = port;
		this.authScheme = authScheme;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getAuthScheme() {
		return authScheme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authScheme == null) ? 0 : authScheme.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userDomain == null) ? 0 : userDomain.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProxyConfig)) {
			return false;
		}
		ProxyConfig other = (ProxyConfig) obj;
		if (authScheme == null) {
			if (other.authScheme != null) {
				return false;
			}
		} else if (!authScheme.equals(other.authScheme)) {
			return false;
		}
		if (host == null) {
			if (other.host != null) {
				return false;
			}
		} else if (!host.equals(other.host)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (port == null) {
			if (other.port != null) {
				return false;
			}
		} else if (!port.equals(other.port)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		if (userDomain == null) {
			if (other.userDomain != null) {
				return false;
			}
		} else if (!userDomain.equals(other.userDomain)) {
			return false;
		}
		return true;
	}

}
