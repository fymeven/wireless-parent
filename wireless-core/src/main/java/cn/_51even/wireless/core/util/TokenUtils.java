package cn._51even.wireless.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    public static void saveToken(HttpServletResponse response, String tokenName, String tokenValue){
        CookieUtils.saveCookie(response,tokenName,tokenValue);
    }

    public static String getToken(String tokenName){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String tokenValue = CookieUtils.getCookieByName(request, tokenName);
        if (StringUtils.isBlank(tokenValue)){
            tokenValue = request.getHeader(tokenName);
        }
        return tokenValue;
    }

    public static void removeToken(HttpServletResponse response, String tokenName){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        CookieUtils.clearCookie(request,response,tokenName);
    }
}
