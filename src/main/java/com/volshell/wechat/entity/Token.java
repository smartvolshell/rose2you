package com.volshell.wechat.entity;

public class Token {
	private String access_token;
	private Integer expires_in;

	public Token() {
		// TODO Auto-generated constructor stub
	}

	public Token(String access_token, Integer expires_in) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void SetAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	@Override
	public String toString() {
		return "Token [access_token=" + access_token + ", expires_in=" + expires_in + "]";
	}

}
