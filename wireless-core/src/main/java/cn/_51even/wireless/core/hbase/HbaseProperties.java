//package cn._51even.wireless.core.hbase;
//
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.HConstants;
//import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
//@ConditionalOnClass(name = "org.apache.hadoop.hbase.client.Connection")
//@Configuration
//@ConfigurationProperties(prefix = "hbase.zookeeper")
//public class HbaseProperties {
//
//    private String quorum;
//
//    private String port;
//
//    private String znode;
//
//    public String getQuorum() {
//        return quorum;
//    }
//
//    public void setQuorum(String quorum) {
//        this.quorum = quorum;
//    }
//
//    public String getPort() {
//        return port;
//    }
//
//    public void setPort(String port) {
//        this.port = port;
//    }
//
//    public String getZnode() {
//        return znode;
//    }
//
//    public void setZnode(String znode) {
//        this.znode = znode;
//    }
//
//    @Bean("hBaseConnection")
//    public Connection hBaseConnection() {
//        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
//        conf.set(HConstants.ZOOKEEPER_QUORUM,quorum);
//        conf.set(HConstants.ZOOKEEPER_CLIENT_PORT,port);
//        conf.set(HConstants.ZOOKEEPER_ZNODE_PARENT,znode);
//        Connection connection = null;
//        try {
//            connection = ConnectionFactory.createConnection(conf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    @Bean("hBaseAdmin")
//    public Admin hBaseAdmin(){
//        Admin admin = null;
//        try {
//            admin = hBaseConnection().getAdmin();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return admin;
//    }
//}
