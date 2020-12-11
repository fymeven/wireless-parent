package cn._51even.wireless.core.elasticsearch;

import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@ConditionalOnClass(name = "io.searchbox.client.JestClient")
@Configuration
@ConfigurationProperties(prefix = "elasticsearch.jest")
public class ElasticsearchProperties {

    private String uri;

    private boolean auth;

    private String userName;

    private String passWord;

    private int connTimeout;

    private int readTimeout;

    private int maxResultWindow=10000;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getMaxResultWindow() {
        return maxResultWindow;
    }

    public void setMaxResultWindow(int maxResultWindow) {
        this.maxResultWindow = maxResultWindow;
    }

    @ConditionalOnExpression("#{!''.equals(environment['elasticsearch.jest.uri'])}")
    @Bean(name = "jestClient")
    public JestClient jestClient(){
        JestClientFactory factory = new JestClientFactory();
        HttpClientConfig.Builder builder = new HttpClientConfig.Builder(Arrays.asList(uri));
        if (auth){
            BasicCredentialsProvider customCredentialsProvider = new BasicCredentialsProvider();
            customCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, passWord));
            builder.credentialsProvider(customCredentialsProvider);
        }
        builder.connTimeout(connTimeout);
        builder.readTimeout(readTimeout);
        builder.multiThreaded(true);
        factory.setHttpClientConfig(builder.build());
        JestClient jestClient = factory.getObject();
        return jestClient;
    }

}
