package com.asq.security.cas.config;

import javax.servlet.http.HttpSessionEvent;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.asq.security.cas.service.UserDetailsServiceImpl;

/**
 * @author liujian
 * @date 2019/5/28
 */
@Configuration
public class CasSecurityConfig {

	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		//本机服务，访问/login/cas时进行校验登录
		serviceProperties.setService("http://localhost:8080/login/cas");
		serviceProperties.setSendRenew(false);
		return serviceProperties;
	}

	@Bean
	@Primary
	public AuthenticationEntryPoint authenticationEntryPoint(
			ServiceProperties sP) {

		CasAuthenticationEntryPoint entryPoint
				= new CasAuthenticationEntryPoint();
		//cas登录服务
		entryPoint.setLoginUrl("https://account.chdi.com.cn/passport/login");
		entryPoint.setServiceProperties(sP);
		return entryPoint;
	}

	@Bean
	public TicketValidator ticketValidator() {
		//指定cas校验器
		return new Cas30ServiceTicketValidator(
				"https://account.chdi.com.cn/passport");
	}

	//cas认证
	@Bean
	public CasAuthenticationProvider casAuthenticationProvider() {

		CasAuthenticationProvider provider = new CasAuthenticationProvider();
		provider.setServiceProperties(serviceProperties());
		provider.setTicketValidator(ticketValidator());
		//固定响应用户，在生产环境中需要额外设置用户映射
		provider.setUserDetailsService(new UserDetailsServiceImpl());
		provider.setKey("CAS_PROVIDER_LOCALHOST_8123");
		return provider;
	}


	@Bean
	public SecurityContextLogoutHandler securityContextLogoutHandler() {
		return new SecurityContextLogoutHandler();
	}

	@Bean
	public LogoutFilter logoutFilter() {
		//退出后转发路径
		LogoutFilter logoutFilter = new LogoutFilter(
				"https://account.chdi.com.cn/passport/logout",
				securityContextLogoutHandler());
		//cas退出
		logoutFilter.setFilterProcessesUrl("/logout/cas");
		return logoutFilter;
	}

	@Bean
	public SingleSignOutFilter singleSignOutFilter() {
		//单点退出
		SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
		singleSignOutFilter.setCasServerUrlPrefix("https://account.chdi.com.cn/passport");
		singleSignOutFilter.setIgnoreInitConfiguration(true);
		return singleSignOutFilter;
	}

	//设置退出监听
	@EventListener
	public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener(
			HttpSessionEvent event) {
		return new SingleSignOutHttpSessionListener();
	}
}