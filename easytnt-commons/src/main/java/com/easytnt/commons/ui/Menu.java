

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
	
	private boolean actived = Boolean.FALSE;
	
	public Menu(String name,String url) {
		this.name = name;
		this.url = url;
	}
	
	public Menu(String name,String url,boolean actived) {
		this.name = name;
		this.url = url;
		this.actived = actived;
	}
	
	public void active() {
		this.actived = Boolean.TRUE;
	}
	
	public void inactived() {
		this.actived = Boolean.FALSE;
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

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}
}

