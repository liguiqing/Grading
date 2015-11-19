/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.security.sso;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月19日
 * @version 1.0
 **/
public class SsoUserDetails  {
	
	private String ssoAccount;
	
	private String ssoPassword;
	
	private Boolean enabled;
	
	public SsoUserDetails(String ssoAccount, String ssoPassword) {
		this.ssoAccount = ssoAccount;
		this.ssoPassword = ssoPassword;
		this.enabled = Boolean.TRUE;
	}
	
	public SsoUserDetails(String ssoAccount, String ssoPassword, Boolean enabled) {
		super();
		this.ssoAccount = ssoAccount;
		this.ssoPassword = ssoPassword;
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.ssoAccount).toHashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SsoUserDetails))
			return false;
		SsoUserDetails other = (SsoUserDetails) o;

		return new EqualsBuilder().append(this.ssoAccount, other.ssoAccount).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.ssoAccount).append(this.ssoAccount).build();
	}

	
	public SsoUserDetails() {
		
	}
	
	private Long ssoUserId;

	public Long getSsoUserId() {
		return ssoUserId;
	}

	public void setSsoUserId(Long ssoUserId) {
		this.ssoUserId = ssoUserId;
	}

	public String getSsoAccount() {
		return ssoAccount;
	}

	public void setSsoAccount(String ssoAccount) {
		this.ssoAccount = ssoAccount;
	}

	public String getSsoPassword() {
		return ssoPassword;
	}

	public void setSsoPassword(String ssoPassword) {
		this.ssoPassword = ssoPassword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}


