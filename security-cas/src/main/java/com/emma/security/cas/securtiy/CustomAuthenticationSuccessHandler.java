package com.emma.security.cas.securtiy;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.emma.security.cas.util.IpUtils;

/**
 * 自定义认证成功句柄
 * @author liuyang
 */
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

//	@Autowired
//	private AccountService accountService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		logger.info("customAuthenticationSuccessHandler");
		super.onAuthenticationSuccess(request, response, authentication);
		if (authentication instanceof AbstractAuthenticationToken) {
			setRemoteAddress(request, (AbstractAuthenticationToken) authentication);
		}
		if (authentication instanceof CasAuthenticationToken) {
			//doCasLog((CasAuthenticationToken) authentication);
		}
	}

	/**
	 * 设置真实的ip地址
	 * @param request 请求
	 * @param authentication
	 */
	private void setRemoteAddress(HttpServletRequest request, AbstractAuthenticationToken authentication) {
		String ip = IpUtils.getIpAddr(request);
		Object details = authentication.getDetails();
		logger.info("abstractAuthenticationToken.details:{}", details.getClass());
		try {
			//暴力反射，因为remoteAddress是私有的成员变量，并且无setter方法
			Field fieldx = details.getClass().getDeclaredField("remoteAddress");
			fieldx.setAccessible(true);
			fieldx.set(details, ip);
		} catch (Exception e) {
			logger.warn("abstractAuthenticationToken.details:{}", details.getClass());
			logger.warn("get real ip failed:{}", e);
		}
		authentication.setDetails(details);
	}

//	/**
//	 * 做cas登录日志
//	 * @param authentication
//	 */
//	private void doCasLog(CasAuthenticationToken authentication) {
//		logger.info("do cas log");
//		Assertion assertion = authentication.getAssertion();
//		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
//		Map<String, Object> attributes = assertion.getPrincipal().getAttributes();
//		//学信登录返回的key有mobilePhone、xm、supportedBy、usruid、email、authtype
//		for (Entry<String, Object> map : attributes.entrySet()) {
//			logger.info("attrs : " + map.getKey() + "----------" + map.getValue() + userInfo.toString());
//		}
//		String userId = (String) attributes.get("usruid");
//		String mobilePhone = (String) attributes.get("mobilePhone");
//		logger.info("mobilePhone: " + mobilePhone);
//		AuthType authType = AuthType.cas_basic;
//		String chsiAuthtype = (String) attributes.get("authtype");
//		if (StringUtils.hasText(chsiAuthtype)) {
//			if (chsiAuthtype.contains("cert")) {
//				authType = AuthType.cas_cert;
//			} else if (chsiAuthtype.contains("sms")) {
//				authType = AuthType.cas_sms;
//			} else {
//				authType = AuthType.cas_basic;
//			}
//		}
//		logger.info(userId + " : " + userInfo.getUsername());
//		accountService.updateLastLogin(userId, new DateTime().toDate(), authType, mobilePhone);
//	}

}
