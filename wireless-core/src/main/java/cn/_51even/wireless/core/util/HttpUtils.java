package cn._51even.wireless.core.util;

import cn._51even.wireless.core.base.bean.response.ResponseResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Administrator on 2018/5/18.
 */

/**
 * http请求
 */
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final String CHARSET="UTF-8";

    private static final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).setConnectionRequestTimeout(60000).build();

    public enum ContentType{
        x_www_form_urlencoded("application/x-www-form-urlencoded","默认方式"),
        multipart_form_data("multipart/form-data","文件上传"),
        application_json("application/json;charset=utf-8","json字符串"),
        text_xml("text/xml;charset=utf-8","xml")
        ;

        private String code;
        private String desc;

        ContentType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static <T>T doPost(String url, JSONObject param, Class<T> beanClass){
        return doPost(url,param,null,null,null,beanClass);
    }

    public static <T>T doPost(String url,JSONObject param,JSONObject header,Class<T> beanClass){
        return doPost(url,param,header,null,null,beanClass);
    }

    public static <T>T doPost(String url,JSONObject param,ContentType contentType,Class<T> beanClass){
        return doPost(url,param,null,null,contentType,beanClass);
    }

    public static <T>T doPost(String url,JSONObject param,JSONObject header,JSONObject file,ContentType contentType,Class<T> beanClass){
        File tmpFile = null;
        if (contentType == null){
            contentType = HttpUtils.ContentType.x_www_form_urlencoded;
        }
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            if (StringUtils.isBlank(url)){
                logger.info("http post请求url为空");
                return null;
            }
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if ((param != null && !param.isEmpty()) || (file != null && !file.isEmpty())){
                switch (contentType){
                    case x_www_form_urlencoded:
                    default:
                        List<NameValuePair> pairs = new ArrayList<>(param.size());
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            if (entry.getValue() != null){
                                pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
                            }
                        }
                        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, "UTF-8");
                        urlEncodedFormEntity.setContentEncoding("UTF-8");
                        urlEncodedFormEntity.setContentType(contentType.x_www_form_urlencoded.getCode());
                        httpPost.setEntity(urlEncodedFormEntity);
                        break;
                    case application_json:
                        StringEntity stringEntity = new StringEntity(param.toJSONString(),CHARSET);
                        stringEntity.setContentEncoding(CHARSET);
                        stringEntity.setContentType(contentType.getCode());
                        httpPost.setEntity(stringEntity);
                        break;
                    case multipart_form_data:
                        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                        multipartEntityBuilder.setCharset(Charset.forName(CHARSET));
                        if (param !=null){
                            for (Map.Entry<String, Object> entry : param.entrySet()) {
                                if (entry.getValue() != null){
                                    multipartEntityBuilder.addPart(entry.getKey(),new StringBody(entry.getValue().toString(), org.apache.http.entity.ContentType.TEXT_PLAIN));
                                }
                            }
                        }
                        if (file != null && !file.isEmpty()){
                            for (Map.Entry<String, Object> entry : file.entrySet()) {
                                Object value = entry.getValue();
                                if (value instanceof InputStream){
                                    InputStream inputStream = (InputStream)value;
                                    multipartEntityBuilder.addBinaryBody(entry.getKey(), inputStream, org.apache.http.entity.ContentType.MULTIPART_FORM_DATA,entry.getKey());
                                }else if(value instanceof Collection) {
                                    Collection<InputStream> fileArray = (Collection) value;
                                    for (InputStream inputStream : fileArray) {
                                        multipartEntityBuilder.addBinaryBody(entry.getKey(), inputStream, org.apache.http.entity.ContentType.MULTIPART_FORM_DATA, entry.getKey());
                                    }
                                }else if (value instanceof String){
                                    String fileFull = value.toString();
                                    String[] fileArray = fileFull.split(",");
                                    for (String filePath : fileArray) {
                                        String fileName = filePath.substring(filePath.lastIndexOf("."));
                                        InputStream inputStream = getStreamFromHttp(filePath);
                                        multipartEntityBuilder.addBinaryBody(entry.getKey(), inputStream, org.apache.http.entity.ContentType.MULTIPART_FORM_DATA,fileName);
                                    }
                                }else if(value instanceof File) {
                                    File fileParam = (File) value;
                                    multipartEntityBuilder.addBinaryBody(entry.getKey(), fileParam);
                                } else if (value instanceof MultipartFile){
                                    MultipartFile multipartFile = (MultipartFile) value;
                                    tmpFile=File.createTempFile("tmp", multipartFile.getOriginalFilename());
                                    multipartFile.transferTo(tmpFile);
                                    multipartEntityBuilder.addBinaryBody(entry.getKey(), tmpFile);
                                }
                            }
                        }
                        HttpEntity httpEntity = multipartEntityBuilder.build();
                        httpPost.setEntity(httpEntity);
                        break;
                }
            }
            if (header != null && !header.isEmpty()){
                for (Map.Entry<String, Object> entry : header.entrySet()) {
                    httpPost.setHeader(entry.getKey(),entry.getValue().toString());
                }
            }
            response = httpClient.execute(httpPost);
            T bean = beanClass.newInstance();
            logger.debug("response:"+response);
            if (bean instanceof CloseableHttpResponse){
                return (T)response;
            }else {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                    String str = EntityUtils.toString(response.getEntity(),CHARSET);
                    if (bean instanceof JSONObject){
                        return (T)JSONObject.parseObject(str);
                    }else if (bean instanceof JSONArray){
                        return (T)JSONArray.parse(str);
                    }else if (bean instanceof ResponseResult){
                        return (T)JSONObject.parseObject(str,ResponseResult.class);
                    }else {
                        return (T)str;
                    }
                }
            }

        }catch (Exception e){
            logger.error("http post请求出错:"+url,e);
        }finally {
            try {
                if (response != null){
                    response.close();
                }
                httpPost.releaseConnection();
                httpClient.close();
                if (tmpFile != null){
                    tmpFile.deleteOnExit();
                }
            } catch (IOException e) {
                logger.error("资源关闭发生错误"+e);
            }
        }
        return null;
    }

    public static <T>T doGet(String url,JSONObject param,Class<T> beanClass){
        return doGet(url,param,null,beanClass);
    }

    public static <T>T  doGet(String url,JSONObject param,JSONObject header,Class<T> beanClass){
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            if (StringUtils.isBlank(url)){
                logger.info("http get请求url为空");
                return null;
            }
            httpClient = HttpClients.createDefault();
            if (param != null && !param.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<>(param.size());
                for (Map.Entry<String, Object> entry : param.entrySet()) {
                    if (entry.getValue() != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
                    }
                }
                url +="?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs,"UTF-8"));
            }
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            if (header != null && !header.isEmpty()){
                for (Map.Entry<String, Object> entry : header.entrySet()) {
                    httpGet.setHeader(entry.getKey(),entry.getValue().toString());
                }
            }
            response = httpClient.execute(httpGet);
            T bean = beanClass.newInstance();
            if (bean instanceof CloseableHttpResponse){
                return (T)response;
            }else {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String str = EntityUtils.toString(response.getEntity(), CHARSET);
                    if (bean instanceof JSONObject){
                        return (T)JSONObject.parseObject(str);
                    }else if (bean instanceof JSONArray){
                        return (T)JSONArray.parse(str);
                    }else {
                        return (T)str;
                    }
                }
            }
        }catch (Exception e){
            logger.error("http get请求出错:"+url,e);
        }finally {
            try {
                if (response!=null){
                    response.close();
                }
                httpGet.releaseConnection();
                httpClient.close();
            } catch (IOException e) {
                logger.error("资源关闭发生错误"+e);
            }
        }
        return null;
    }

    public static InputStream getStreamFromHttp(String httpUrl){
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5*1000);
            InputStream inputStream = connection.getInputStream();
            return inputStream;
        }catch (Exception e){
            logger.error("获取文件流异常:{}",e);
        }
        return null;
    }

    public static CloseableHttpResponse responsePost(String url, JSONObject param){
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            if (StringUtils.isBlank(url)){
                logger.info("http post请求url为空");
                return null;
            }
            httpClient = HttpClients.createDefault();
            List<NameValuePair> pairs= null;
            if (param != null && !param.isEmpty()){
                pairs = new ArrayList<>(param.size());
                for (Map.Entry<String, Object> entry : param.entrySet()) {
                    if (entry.getValue() != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
                    }
                }
            }
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if (pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
            }
            response = httpClient.execute(httpPost);
            return response;
        }catch (Exception e){
            logger.error("http post请求出错:"+url,e);
        }finally {
            try {
                if (response!=null){
                    response.close();
                }
                httpPost.releaseConnection();
                httpClient.close();
            } catch (IOException e) {
                logger.error("资源关闭发生错误"+e);
            }
        }
        return null;
    }

}
