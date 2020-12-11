//package cn._51even.wireless.core.hbase;
//
//import cn._51even.wireless.core.base.bean.enums.SystemEnum;
//import cn._51even.wireless.core.base.exception.BusinessException;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.hadoop.hbase.HColumnDescriptor;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.NamespaceDescriptor;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.List;
//
//@ConditionalOnClass(name = "org.apache.hadoop.hbase.client.Connection")
//@Component
//public class HbaseUtil {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Resource
//    private Connection hBaseConnection;
//    @Resource
//    private Admin hBaseAdmin;
//
//    /**
//     * 创建命名空间
//     * @param nameSpace
//     * @return
//     */
//    public boolean createNameSpace(String nameSpace){
//        boolean result = false;
//        try {
//            NamespaceDescriptor[] namespaceDescriptors = hBaseAdmin.listNamespaceDescriptors();
//            for (NamespaceDescriptor namespaceDescriptor : namespaceDescriptors) {
//                if (namespaceDescriptor.getName().equals(nameSpace)){
//                    logger.error(nameSpace + "already exist");
//                    return false;
//                }
//            }
//            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(nameSpace).build();
//            hBaseAdmin.createNamespace(namespaceDescriptor);
//            result = true;
//        }catch (Exception e){
//            logger.error("HBase createTable 异常,cause:{}",e);
//            throw new BusinessException(SystemEnum.ErrorCode.SERVICE_ERROR);
//        }
//        return result;
//    }
//    /**
//     * 只创建一个列簇的 hBase 表
//     * @param nameSpace
//     * @param tableName
//     * @param familyName
//     * @param maxVersions
//     * @return
//     * @throws IOException
//     */
//    public boolean createTable(String nameSpace,String tableName,String familyName,Integer maxVersions) {
//        boolean result = false;
//        try {
//            TableName tableNameObj = TableName.valueOf(nameSpace+":"+tableName);
//            if(hBaseAdmin.tableExists(tableNameObj)) {
//                logger.error(tableName + "already exist");
//                return false;
//            }
//            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableNameObj);
//            if (StringUtils.isNotBlank(familyName)){
//                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(familyName);
//                if (maxVersions !=null){
//                    hColumnDescriptor.setMaxVersions(maxVersions);
//                }
//                hTableDescriptor.addFamily(hColumnDescriptor);
//            }
//            hBaseAdmin.createTable(hTableDescriptor);
//            hBaseAdmin.close();
//            result = true;
//        }catch (Exception e){
//            logger.error("HBase createTable 异常,cause:{}",e);
//            throw new BusinessException(SystemEnum.ErrorCode.SERVICE_ERROR);
//        }
//        return result;
//    }
//
//    /**
//     * 获取 hBase 表对象
//     * @param nameSpace
//     * @param tableName
//     * @return
//     */
//    public Table getTable(String nameSpace,String tableName){
//        try {
//            Table table = hBaseConnection.getTable(TableName.valueOf(nameSpace+":"+tableName));
//            return table;
//        } catch (IOException e) {
//            logger.error("HBase getTable 异常,cause:{}",e);
//            throw new BusinessException(SystemEnum.ErrorCode.SERVICE_ERROR);
//        }
//    }
//
//    /**
//     * get hBase 结果
//     * @param nameSpace
//     * @param tableName
//     * @param get
//     * @return
//     */
//    public Result getData(String nameSpace,String tableName,Get get){
//        try {
//            return getTable(nameSpace,tableName).get(get);
//        } catch (IOException e) {
//            logger.error("HBase getData 异常,cause:{}",e);
//            throw new BusinessException(SystemEnum.ErrorCode.SERVICE_ERROR);
//        }
//    }
//
//    /**
//     * put hBase 数据
//     * @param nameSpace
//     * @param tableName
//     * @param put
//     * @return
//     */
//    public boolean putData(String nameSpace,String tableName,Put put){
//        boolean result = false;
//        try {
//            Table table = getTable(nameSpace,tableName);
//            table.put(put);
//            result = true;
//        } catch (IOException e) {
//            logger.error("HBase putData 异常,cause:{}",e);
//        }
//        return result;
//    }
//
//    /**
//     * 批量put hBase 数据
//     * @param nameSpace
//     * @param tableName
//     * @param put
//     * @return
//     */
//    public boolean putDataList(String nameSpace, String tableName, List<Put> put){
//        boolean result = false;
//        try {
//            Table table = getTable(nameSpace,tableName);
//            table.put(put);
//            result = true;
//        } catch (IOException e) {
//            logger.error("HBase 批量putData 异常,cause:{}",e);
//        }
//        return result;
//    }
//
//    /**
//     * delete hBase 数据
//     * @param nameSpace
//     * @param tableName
//     * @param delete
//     * @return
//     */
//    public boolean delData(String nameSpace,String tableName,Delete delete){
//        boolean result = false;
//        try {
//            Table table = getTable(nameSpace,tableName);
//            table.delete(delete);
//            result = true;
//        } catch (IOException e) {
//            logger.error("HBase delData 异常,cause:{}",e);
//        }
//        return result;
//    }
//}
