package cn._51even.wireless.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ClientUtil {

    public static String getUserIp(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return getUserIp(request);
    }

    public static String getUserIp(HttpServletRequest request){
        String returnIp="";
        String ip = request.getHeader("X-Forwarded-For");
        if(ip!=null&&!"".equals(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理会有多个IP,第一个才是真实IP
            int index = ip.indexOf(",");
            if(index != -1){
                returnIp = ip.substring(0,index);
            }else {
                returnIp = ip;
            }
        }else {
            ip = request.getHeader("X-Real-IP");
            if(ip!=null&&!"".equals(ip) && !"unKnown".equalsIgnoreCase(ip)){
                returnIp = ip;
            }else {
                returnIp = request.getRemoteAddr();
            }
        }
        return returnIp;
    }
}
