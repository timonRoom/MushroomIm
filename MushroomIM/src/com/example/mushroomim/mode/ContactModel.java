package com.example.mushroomim.mode;

public class ContactModel {
	private String portraitUri;
	private String nikename;
	private String userId;
	public String getPortraitUri() {
		return portraitUri;
	}
	public void setPortraitUri(String portraitUri) {
		this.portraitUri = portraitUri;
	}
	public String getNikename() {
		return nikename;
	}
	public void setNikename(String nikename) {
		this.nikename = nikename;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ContactModel [portraitUri=" + portraitUri + ", nikename=" + nikename + ", userId=" + userId + "]";
	}
}
