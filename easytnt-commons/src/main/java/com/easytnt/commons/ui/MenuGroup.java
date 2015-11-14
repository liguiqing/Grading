/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.ui;

import java.util.ArrayList;
import java.util.List;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月8日
 * @version 1.0
 **/
public class MenuGroup {

	private List<Menu> menus;
	
	public static MenuGroup createMenuGroup() {
		MenuGroup group = new MenuGroup();
		group.menus = new ArrayList<>();
		return group;
	}
	
	public void appendMenu(Menu menu) {
		this.menus.add(menu);
	}
	
	public void activedMenuByIndex(int index) {
		if(this.menus.get(index) != null) {
			for(Menu m:this.menus) {
				m.inactived();
			}
			this.menus.get(index).active();
		}	
	}
	
	@Override
	public MenuGroup clone() {
		MenuGroup mg = new MenuGroup();
		mg.menus = new ArrayList<>(this.menus);
		return mg;
	}
	
	public void activedMenuByUrl(String url) {
		
	}
	
	public List<Menu> getMenus(){
		return this.menus;
	}
}


