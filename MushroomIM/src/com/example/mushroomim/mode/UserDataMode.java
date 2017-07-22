package com.example.mushroomim.mode;

public class UserDataMode {
	public static final String table = "data";
	private String userid;
	private  String  token;
	private  String password;
	private String portraitUri;
	public String getPortraitUri() {
		return portraitUri;
	}
	public void setPortraitUri(String portraitUri) {
		this.portraitUri = portraitUri;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String usernikename;
	public String getUsernikename() {
		return usernikename;
	}
	public void setUsernikename(String usernikename) {
		this.usernikename = usernikename;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "UserDataMode [userid=" + userid + ", token=" + token + ", password=" + password + ", portraitUri="
				+ portraitUri + ", usernikename=" + usernikename + "]";
	}
	
	
}
