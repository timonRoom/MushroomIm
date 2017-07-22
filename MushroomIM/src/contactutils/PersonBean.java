package contactutils;

public class PersonBean {
	private String userId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String Name;
	private String PinYin;
	private String FirstPinYin;
	private String PortraitUri;

	public String getPortraitUri() {
		return PortraitUri;
	}

	public void setPortraitUri(String portraitUri) {
		PortraitUri = portraitUri;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPinYin() {
		return PinYin;
	}

	public void setPinYin(String pinYin) {
		PinYin = pinYin;
	}

	public String getFirstPinYin() {
		return FirstPinYin;
	}

	public void setFirstPinYin(String firstPinYin) {
		FirstPinYin = firstPinYin;
	}

	@Override
	public String toString() {
		return "PersonBean [userId=" + userId + ", Name=" + Name + ", PinYin=" + PinYin + ", FirstPinYin=" + FirstPinYin
				+ ", PortraitUri=" + PortraitUri + "]";
	}

	

	

}
