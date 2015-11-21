/**
 * <p><b>© 2015-2015 </b></p>
 * 
 **/

package com.easytnt.commons.web.view;
/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/
public class Responser {
	public final static String ModelName = "status";
	
	private boolean success;
	
	private String code;
	
	private String msg;
	
	public static class Builder{
		private Responser responser;
		
		public Builder() {
			this.responser = new Responser();
		}
		
		public Builder success() {
			this.responser.success = Boolean.TRUE;
			return this;
		}
		
		public Builder failure() {
			this.responser.success = Boolean.FALSE;
			return this;
		}
		
		public Builder msg(String msg) {
			this.responser.msg = msg;
			return this;
		}
		
		public Builder code(String code) {
			this.responser.code = code;
			return this;
		}
		
		public Responser create() {
			return this.responser;
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}

