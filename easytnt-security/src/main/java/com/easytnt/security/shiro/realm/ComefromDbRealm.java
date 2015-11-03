/**
 * 
 * 
 **/

package com.easytnt.security.shiro.realm;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.easytnt.security.UserDetails;

/**
 * <pre>
 * 通过数据库查询的Realm
 * </pre>
 * 
 * @author 李贵庆2015年10月27日
 * @version 1.0
 **/
public class ComefromDbRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(ComefromDbRealm.class);

	private String sql = "select user_name,user_pwd,enabled from tb_user where user_name+?";

	private String userNameField = "user_name";

	private String passwordField = "user_pwd";

	private String enabledField = "enabled";

	private JdbcTemplate jdbcTemplate;

	private int enabledValue = 1;

	private ResultSetExtractor<UserDetails> userExctractor;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.debug("{}", principals);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		if (username == null)
			throw new UnknownAccountException();

		if (this.userExctractor == null)
			useDefautlExctractor();
		logger.debug("Query user with {}", this.sql);
		UserDetails user = jdbcTemplate.query(sql, new Object[] { username }, this.userExctractor);

		if (user == null)
			throw new UnknownAccountException();

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getCredentials(),
				ByteSource.Util.bytes(user.getUserName()), getName());
		logger.debug("{}", authenticationInfo);
		return authenticationInfo;
	}

	private void useDefautlExctractor() {
		this.userExctractor = new ResultSetExtractor<UserDetails>() {

			@Override
			public UserDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (!rs.next())
					return null;
				if (rs.getInt(enabledField) != enabledValue)
					throw new LockedAccountException();

				final String _username = rs.getString(userNameField);
				final String _password = rs.getString(passwordField);

				UserDetails user = new UserDetails() {
					private String userName = _username;

					private String credentials = _password;

					@Override
					public String getUserName() {

						return this.userName;
					}

					@Override
					public String getAlias() {
						return this.userName;
					}

					@Override
					public String getRealName() {
						return this.userName;
					}

					@Override
					public Object getCredentials() {
						return this.credentials;
					}

					@Override
					public <T> T getSource() {
						return null;
					}

					@Override
					public <T> boolean sourceOf(T t) {
						return true;
					}

					@Override
					public String toString() {
						return this.userName;
					}

				};

				return user;
			}

		};

	}

	/**
	 * 查询结果必须含有：user_name,user_pwd,enabled 三个字段，查询参数有且只一个
	 * 
	 * @param sql
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setUserNameField(String userNameField) {
		this.userNameField = userNameField;
	}

	public void setPasswordField(String passwordField) {
		this.passwordField = passwordField;
	}

	public void setEnabledField(String enabledField) {
		this.enabledField = enabledField;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setEnabledValue(int enabledValue) {
		this.enabledValue = enabledValue;
	}

	public void setUserExctractor(ResultSetExtractor<UserDetails> userExctractor) {
		this.userExctractor = userExctractor;
	}
}
