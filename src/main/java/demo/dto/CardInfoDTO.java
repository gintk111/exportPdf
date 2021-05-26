package demo.dto;

import java.io.File;

public class CardInfoDTO {
	private String companyName;
	private String fullName;
	private File avatar;
	private File logo;
	private File background;
	private String orderId;
	private String orderNumber;
	private String orderDate;
	private Integer cardSide;
	private File dataJson;
	private String urlFont;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public File getAvatar() {
		return avatar;
	}

	public void setAvatar(File avatar) {
		this.avatar = avatar;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public File getBackground() {
		return background;
	}

	public void setBackground(File background) {
		this.background = background;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getCardSide() {
		return cardSide;
	}

	public void setCardSide(Integer cardSide) {
		this.cardSide = cardSide;
	}

	public File getDataJson() {
		return dataJson;
	}

	public void setDataJson(File dataJson) {
		this.dataJson = dataJson;
	}

	public String getUrlFont() {
		return urlFont;
	}

	public void setUrlFont(String urlFont) {
		this.urlFont = urlFont;
	}

	public CardInfoDTO(String companyName, String fullName, String orderId, String orderNumber, String orderDate) {
		super();
		this.companyName = companyName;
		this.fullName = fullName;
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
	}

	public CardInfoDTO(Integer cardSide) {
		this.cardSide = cardSide;
	}

	public CardInfoDTO() {
		super();
	}

}
