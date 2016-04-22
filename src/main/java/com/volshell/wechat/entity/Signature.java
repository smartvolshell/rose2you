package com.volshell.wechat.entity;

/**
 * @author volshell
 * @version 1.0
 *
 *          服务器验证实体类
 */
public class Signature {
	/**
	 * 随机数
	 */
	String nonce;
	/**
	 * 微信加密签名
	 */
	String signature;;
	/**
	 * 时间戳
	 */
	String timestamp;
	/**
	 * 随机字符串（可选）
	 */
	String echostr;

	public Signature(String nonce, String signature, String timestamp, String echostr) {
		super();
		this.nonce = nonce;
		this.signature = signature;
		this.timestamp = timestamp;
		this.echostr = echostr;
	}

	public Signature(String signature, String timestamp, String nonce) {
		super();
		this.signature = signature;
		this.timestamp = timestamp;
		this.nonce = nonce;
	}

	public String getNonce() {
		return nonce;
	}

	public String getSignature() {
		return signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
