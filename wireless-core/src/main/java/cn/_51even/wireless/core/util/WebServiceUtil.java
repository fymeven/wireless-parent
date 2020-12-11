package cn._51even.wireless.core.util;

import org.apache.axis.client.Service;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import java.util.Map;

public class WebServiceUtil {

    private static Logger logger = LoggerFactory.getLogger(WebServiceUtil.class);

    private static long timeOut = 60000L;//请求过期时间

    /**
     *  带认证方式
     * @param targetEndpoint   wsdl地址
     * @param targetNamespace  nameSpace地址
     * @param method            调用方法名
     * @param params            参数
     * @param responseClass    返回对象,所有属性需要与服务端出参一致
     * @param userName          认证账号
     * @param password          认证密码
     * @param <T>
     * @return
     */
    public static <T>T axis2(String targetEndpoint, String targetNamespace, String method, Object[] params, Class<T> responseClass, String userName, String password){
        try {
            RPCServiceClient rpcServiceClient = new RPCServiceClient();
            Options options = rpcServiceClient.getOptions();
            options.setTo(new EndpointReference(targetEndpoint));
            options.setTimeOutInMilliSeconds(timeOut);
            HttpTransportPropertiesImpl.Authenticator authenticator = new HttpTransportPropertiesImpl.Authenticator();
            authenticator.setPreemptiveAuthentication(true);
            authenticator.setUsername(userName);
            authenticator.setPassword(password);
            options.setProperty(HTTPConstants.AUTHENTICATE,authenticator);
            options.setAction(targetNamespace+method);
            QName qName = new QName(targetNamespace,method);
            if (params == null){
                params = new Object[]{};
            }
            Object[] objects = rpcServiceClient.invokeBlocking(qName, params, new Class[]{responseClass});
            T response = (T)objects[0];
            return response;
        }catch (Exception e){
            logger.error("webservice请求异常:Cause:{},Message:{}",e.getCause(),e.getMessage());
            return null;
        }
    }

    /**
     *  无认证方式
     * @param targetEndpoint    wsdl地址
     * @param targetNamespace   nameSpace地址
     * @param method             调用方法名
     * @param params             参数
     * @param responseClass     返回对象,所有属性需要与服务端出参一致
     * @param <T>
     * @return
     */
    public static <T>T axis2(String targetEndpoint, String targetNamespace, String method, Object[] params, Class<T> responseClass){
        try {
            RPCServiceClient rpcServiceClient = new RPCServiceClient();
            Options options = rpcServiceClient.getOptions();
            options.setTo(new EndpointReference(targetEndpoint));
            options.setTimeOutInMilliSeconds(timeOut);
            options.setAction(targetNamespace+method);
            QName qName = new QName(targetNamespace,method);
            if (params == null){
                params = new Object[]{};
            }
            Object[] objects = rpcServiceClient.invokeBlocking(qName, params, new Class[]{responseClass});
            T response = (T)objects[0];
            return response;
        }catch (Exception e){
            logger.error("webservice请求异常:Cause:{},Message:{}",e.getCause(),e.getMessage());
            return null;
        }
    }

//    public static <T>T axis1(String targetEndpoint, String targetNamespace, String method, Object[] params, Class<T> responseClass, String userName, String password){
//        try {
//            Service service = new Service();
//            Call call = service.createCall();
//            call.setTargetEndpointAddress(targetEndpoint);
//            QName qName = new QName(targetNamespace,method);
//            call.setOperationName(qName);
//        }catch (Exception e){
//            logger.error("webservice请求异常:Cause:{},Message:{}",e.getCause(),e.getMessage());
//            return null;
//        }
//    }

//    public static <T>T axis1(String targetEndpoint, String targetNamespace, String method, Map<String,Object> paramMap, Class<T> responseClass){
//        try {
//            Service service = new Service();
//            Call call = service.createCall();
//            call.setTargetEndpointAddress(targetEndpoint);
//            QName qName = new QName(targetNamespace,method);
//            call.setOperationName(qName);
//            Object[] params = null;
//            if (paramMap != null && paramMap.size() >0){
//                int i = 0;
//                for (String key : paramMap.keySet()) {
//                    call.addParameter(key,XMLType.XSD_STRING,ParameterMode.IN);
//                    params[i] = paramMap.get(key);
//                    i++;
//                }
//            }else {
//                params = new Object[]{};
//            }
//            call.setReturnType(qName,responseClass);
//            T response = (T)call.invoke(params);
//            return response;
//        }catch (Exception e){
//            logger.error("webservice请求异常:Cause:{},Message:{}",e.getCause(),e.getMessage());
//            return null;
//        }
//    }

    public static <T>T axis1ForArray(String targetEndpoint, String targetNamespace, String method, Map<String,Object> paramMap, Class<T> responseClass,String[] arrayParams){
        try {
            Service service = new Service();
            Call call = service.createCall();
            call.setTargetEndpointAddress(targetEndpoint);
            QName qName = new QName(targetNamespace,method);
            call.setOperationName(qName);
            Object[] params = null;
            if (paramMap != null && paramMap.size() >0){
                int i = 0;
                for (String key : paramMap.keySet()) {
                    params[i] = paramMap.get(key);
                    i++;
                }
            }else {
                params = new Object[]{};
            }
            if (arrayParams !=null){
                for (int j = 0; j < arrayParams.length; j++) {
                    call.addParameter(arrayParams[j],XMLType.SOAP_ARRAY,ParameterMode.IN);
                    paramMap.remove(arrayParams[j]);
                }
            }
            if (paramMap != null && paramMap.size() >0){
                for (String key : paramMap.keySet()) {
                    call.addParameter(key,XMLType.XSD_STRING,ParameterMode.IN);
                }
            }
            call.setReturnType(qName,responseClass);
            T response = (T)call.invoke(params);
            return response;
        }catch (Exception e){
            logger.error("webservice请求异常:Cause:{},Message:{}",e.getCause(),e.getMessage());
            return null;
        }
    }

    /**
     * csf方式调用
     * @param wsdlAddress
     * @param method
     * @param params
     * @return
     */
    public static Object cxf(String wsdlAddress,String method,Object...params){
        try {
            JaxWsDynamicClientFactory dynamicClientFactory = JaxWsDynamicClientFactory.newInstance();
            Client client = dynamicClientFactory.createClient(wsdlAddress);
            if (params == null){
                params = new Object[]{};
            }
            Object[] invoke = client.invoke(method,params);
            logger.info("result=\n"+invoke);
            return invoke[0];
        }catch (Exception e){
            logger.error("webservice请求异常:Cause:{},Message:{}",e.getCause(),e.getMessage());
            return null;
        }
    }
}
