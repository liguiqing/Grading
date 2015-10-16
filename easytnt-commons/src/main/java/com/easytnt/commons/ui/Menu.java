

package com.easytnt.commons.ui;
/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年9月21日
 * @version 1.0
 **/
public class Menu {
	private String name;
	
	private String url;
	
	public Menu(String name,String url) {
		this.name = name;
		this.url = url;
	}
	
	public Menu() {
		
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
	
}

