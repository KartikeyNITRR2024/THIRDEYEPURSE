package com.thirdeye.purse.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.thirdeye.purse.annotation.AdminRequired;
import com.thirdeye.purse.pojos.Token;
import com.thirdeye.purse.security.TokenGeneraterAndParser;
import com.thirdeye.purse.services.UserInfoService;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    
	@Autowired
	TokenGeneraterAndParser tokenGeneraterAndParser;
    
	@Value("${maximumlifeoftoken}")
    private Long maximumLifeOfToken;
	
	@Autowired
	UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenString = request.getHeader("token");

        if (tokenString == null || tokenString.isEmpty()) {
            throw new Exception("Token Not Found");
        }

        Token token = null;
        try {
            token = tokenGeneraterAndParser.tokenParser(tokenString);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        
        if (!userInfoService.isUserActive(token.getUserId())) {
            throw new Exception("Inactive user");
        }
        
        long loginTimeInMillis = token.getLoginTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        
        if (loginTimeInMillis + (maximumLifeOfToken * 1000) < currentTimeInMillis) {
            throw new Exception("Token Expired. Please login again");
        }
        
        boolean isAdminApiAllowed = token.getHasAdminRights().equals(1) && userInfoService.isUserAdmin(token.getUserId());
        
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(AdminRequired.class) != null) {
                if (!isAdminApiAllowed) {
                    throw new Exception("Access Denied: Admin rights required.");
                }
            }
        }
        
        return true;
    }

}
