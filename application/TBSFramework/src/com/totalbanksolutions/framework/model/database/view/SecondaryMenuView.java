package com.totalbanksolutions.framework.model.database.view;

public class SecondaryMenuView {

	private String name = "";
	private String url = "";
	private boolean isSelected = false;

	public SecondaryMenuView()
	{
		super();
	}
	
	public SecondaryMenuView(String name, String url)
	{
		this.name = name;
		this.url = url;
	}
	
	public SecondaryMenuView(String name, String url, boolean isSelected)
	{
		this(name, url);
		this.isSelected = isSelected;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
