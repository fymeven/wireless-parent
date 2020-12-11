package cn._51even.wireless.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtils {

	private static final Logger logger= LoggerFactory.getLogger(CookieUtils.class);

    /**
     * 保存cookie
     * @param response
     * @param cookieName
     * @param cookieValue
     */
	public static void saveCookie(HttpServletResponse response, String cookieName, String cookieValue) {
		Cookie cookie = null;
		try {
            cookie = new Cookie(cookieName, URLEncoder.encode(cookieValue,"UTF-8"));
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(-1);
            logger.debug("获取cookie:"+cookieName+"="+cookieValue);
		} catch (Exception e) {
            logger.error("保存cookie时发生错误",e);
		}
		response.addCookie(cookie);
	}

    /**
     * 获取cookie
     * @param request
     * @param cookieName
     * @return
     */
	public static String getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())){
                    String value = cookie.getValue();
                    try {
                        String cookieValue = URLDecoder.decode(value,"UTF-8");
                        logger.debug("获取cookie:"+cookieName+"="+cookieValue);
                        return cookieValue;
                    } catch (Exception e) {
                        logger.error("获取cookie时发生错误",e);
                    }
                }
            }
        }
		return null;
	}

    /**
     * 移除cookie
     * @param request
     * @param response
     * @param cookieName
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())){
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    logger.debug("清除cookie:"+cookieName);
                }
            }
        }
    }

    public static void clearAll(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            logger.debug("清除所有cookie");
        }
    }
}
