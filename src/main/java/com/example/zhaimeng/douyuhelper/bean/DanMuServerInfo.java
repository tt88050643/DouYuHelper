package com.example.zhaimeng.douyuhelper.bean;

public class DanMuServerInfo {
	private String ip;
	private String port;

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void clear() {
		setIp(null);
		setPort(null);
	}
	
}
