//package cn._51even.wireless.web;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import cn._51even.wireless.core.hbase.HbaseUtil;
//import cn._51even.wireless.core.util.DateUtils;
//import cn._51even.wireless.core.util.UUIDUtil;
//import org.apache.hadoop.hbase.client.Put;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class HBaseUtilTest {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Resource
//    private HbaseUtil hbaseUtil;
//
//    String NAMESPACE = "ssbk_streaming";
//
//    String FAMILY_NAME = "basic";
//
//    String TABLE_TASK = "task";//任务表
//
//    String TABLE_TARGET = "target";//目标表
//
//    String TABLE_TAG = "tag";//标签表
//
//    String TABLE_RYZT = "ryzt";//专题表
//
//    String TABLE_HISTORY = "history";//历史轨迹表(只保留最近一条轨迹)
//
//    String TABLE_gather = "gather";//聚集表
//
//    @Test
//    public void createNameSpace(){
//        boolean r1 = hbaseUtil.createNameSpace(NAMESPACE);
//        logger.debug("result:{}",r1);
//    }
//    @Test
//    public void createTable(){
////        boolean r1 = hbaseUtil.createTable(NAMESPACE,TABLE_TASK, FAMILY_NAME, 1);
////        boolean r2 = hbaseUtil.createTable(NAMESPACE,TABLE_TARGET, FAMILY_NAME, Integer.MAX_VALUE);
////        boolean r3 = hbaseUtil.createTable(NAMESPACE,TABLE_TAG, FAMILY_NAME, Integer.MAX_VALUE);
////        boolean r4 = hbaseUtil.createTable(NAMESPACE,TABLE_RYZT, FAMILY_NAME, Integer.MAX_VALUE);
////        boolean r5 = hbaseUtil.createTable(NAMESPACE,TABLE_HISTORY, FAMILY_NAME, 1);
////        boolean r6 = hbaseUtil.createTable(NAMESPACE,TABLE_gather, FAMILY_NAME, Integer.MAX_VALUE);
////        logger.debug("result:{},{},{},{},{},{}",r1,r2,r3,r4,r5,r6);
//    }
//
//    @Test
//    public void put(){
//        putTask();
//        putTarget();
//        putTag();
//    }
//
//    @Test
//    public void putTask(){
//        //普通布控
//        SsbkTask ssbkTask = new SsbkTask();
//        ssbkTask.setId(UUIDUtil.getUUID());
//        ssbkTask.setTaskName("标签任务1");
//        ssbkTask.setTaskType("2");
//        ssbkTask.setTaskDesc("标签任务模拟测试");
//        ssbkTask.setBusinessType("zdff");
//        ssbkTask.setStartTime(DateUtils.parseDateTime("2020-07-14 12:00:00"));
//        ssbkTask.setEndTime(DateUtils.parseDateTime("2020-09-14 12:00:00"));
//        ssbkTask.setTaskPermission("1");
//        ssbkTask.setAreaType("1");
//        ssbkTask.setAreaStr("330100");
////        ssbkTask.setAreaStr("{\"radius\":\"5000\",\"location\":{\"lat\":\"30.27317\",\"lon\":\"120.159456\"}}");
//        ssbkTask.setTaskStatus("1");
////        ssbkTask.setApproveStatus("1");
//        ssbkTask.setCreateTime(DateUtils.parseDateTime("2020-07-14 12:00:00"));
//        ssbkTask.setCreateUserId("CD3B23792718675382AFB2C59670AC9F");
//        ssbkTask.setCreateUserIdCard("341021199503234215");
//        ssbkTask.setCreateUserName("even");
//        ssbkTask.setCreateUserDeptCode("3301040112");
//        ssbkTask.setCreateUserDeptName("下沙派出所");
////        ssbkTask.setDeleteFlag("0");
//
//        String rowKey = ssbkTask.getId();
//        Put put = new Put(rowKey.getBytes());
//
//        put.addColumn(FAMILY_NAME.getBytes(),"id".getBytes(),ssbkTask.getId().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskName".getBytes(),ssbkTask.getTaskName().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskType".getBytes(),ssbkTask.getTaskType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskDesc".getBytes(),ssbkTask.getTaskDesc().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"businessType".getBytes(),ssbkTask.getBusinessType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"startTime".getBytes(),DateUtils.formatDateTime(ssbkTask.getStartTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"endTime".getBytes(),DateUtils.formatDateTime(ssbkTask.getEndTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskPermission".getBytes(),ssbkTask.getTaskPermission().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"areaType".getBytes(),ssbkTask.getAreaType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"areaStr".getBytes(),ssbkTask.getAreaStr().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskStatus".getBytes(),ssbkTask.getTaskStatus().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createTime".getBytes(),DateUtils.formatDateTime(ssbkTask.getCreateTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserId".getBytes(),ssbkTask.getCreateUserId().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserIdCard".getBytes(),ssbkTask.getCreateUserIdCard().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserName".getBytes(),ssbkTask.getCreateUserName().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserDeptCode".getBytes(),ssbkTask.getCreateUserDeptCode().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserDeptName".getBytes(),ssbkTask.getCreateUserDeptName().getBytes());
//
//
//        JSONArray tagArray = new JSONArray();
//
////        SsbkTaskTag nativeTag = new SsbkTaskTag();
////        nativeTag.setId(UUIDUtil.getUUID());
////        nativeTag.setTagCode("native");
////        nativeTag.setTagName("户籍地");
////        nativeTag.setTagType("1");
////        nativeTag.setTagGroup("1");
////        nativeTag.setTagValue("330100");
////        nativeTag.setHasMultipart("0");
////        nativeTag.setBkType("person");
////        nativeTag.setTaskId(ssbkTask.getId());
////        nativeTag.setTaskType(ssbkTask.getTaskType());
////        nativeTag.setTagStatus("1");
////        nativeTag.setCreateTime(ssbkTask.getCreateTime());
////        nativeTag.setCreateUserId(ssbkTask.getCreateUserId());
////        nativeTag.setCreateUserIdCard(ssbkTask.getCreateUserIdCard());
////        nativeTag.setCreateUserName(ssbkTask.getCreateUserName());
////        nativeTag.setCreateUserDeptCode(ssbkTask.getCreateUserDeptCode());
////        nativeTag.setCreateUserDeptName(ssbkTask.getCreateUserDeptName());
////        tagArray.add(nativeTag);
////
////        SsbkTaskTag ageRange = new SsbkTaskTag();
////        ageRange.setId(UUIDUtil.getUUID());
////        ageRange.setTagCode("ageRange");
////        ageRange.setTagName("年龄范围");
////        ageRange.setTagType("1");
////        ageRange.setTagGroup("1");
////        ageRange.setTagValueStart("18");
////        ageRange.setTagValueEnd("24");
////        ageRange.setHasMultipart("1");
////        ageRange.setBkType("person");
////        ageRange.setTaskId(ssbkTask.getId());
////        ageRange.setTaskType(ssbkTask.getTaskType());
////        ageRange.setTagStatus("1");
////        ageRange.setCreateTime(ssbkTask.getCreateTime());
////        ageRange.setCreateUserId(ssbkTask.getCreateUserId());
////        ageRange.setCreateUserIdCard(ssbkTask.getCreateUserIdCard());
////        ageRange.setCreateUserName(ssbkTask.getCreateUserName());
////        ageRange.setCreateUserDeptCode(ssbkTask.getCreateUserDeptCode());
////        ageRange.setCreateUserDeptName(ssbkTask.getCreateUserDeptName());
////        tagArray.add(ageRange);
////
////        SsbkTaskTag birthDay = new SsbkTaskTag();
////        birthDay.setId(UUIDUtil.getUUID());
////        birthDay.setTagCode("birthDay");
////        birthDay.setTagName("出生日期");
////        birthDay.setTagType("1");
////        birthDay.setTagGroup("1");
////        birthDay.setTagValue("04-29");
////        birthDay.setHasMultipart("0");
////        birthDay.setBkType("person");
////        birthDay.setTaskId(ssbkTask.getId());
////        birthDay.setTaskType(ssbkTask.getTaskType());
////        birthDay.setTagStatus("1");
////        birthDay.setCreateTime(ssbkTask.getCreateTime());
////        birthDay.setCreateUserId(ssbkTask.getCreateUserId());
////        birthDay.setCreateUserIdCard(ssbkTask.getCreateUserIdCard());
////        birthDay.setCreateUserName(ssbkTask.getCreateUserName());
////        birthDay.setCreateUserDeptCode(ssbkTask.getCreateUserDeptCode());
////        birthDay.setCreateUserDeptName(ssbkTask.getCreateUserDeptName());
////        tagArray.add(birthDay);
////
////        SsbkTaskTag sex = new SsbkTaskTag();
////        sex.setId(UUIDUtil.getUUID());
////        sex.setTagCode("sex");
////        sex.setTagName("性别");
////        sex.setTagType("1");
////        sex.setTagGroup("1");
////        sex.setTagValue("2");
////        sex.setHasMultipart("0");
////        sex.setBkType("person");
////        sex.setTaskId(ssbkTask.getId());
////        sex.setTaskType(ssbkTask.getTaskType());
////        sex.setTagStatus("1");
////        sex.setCreateTime(ssbkTask.getCreateTime());
////        sex.setCreateUserId(ssbkTask.getCreateUserId());
////        sex.setCreateUserIdCard(ssbkTask.getCreateUserIdCard());
////        sex.setCreateUserName(ssbkTask.getCreateUserName());
////        sex.setCreateUserDeptCode(ssbkTask.getCreateUserDeptCode());
////        sex.setCreateUserDeptName(ssbkTask.getCreateUserDeptName());
////        tagArray.add(sex);
////
////        SsbkTaskTag nameLength = new SsbkTaskTag();
////        nameLength.setId(UUIDUtil.getUUID());
////        nameLength.setTagCode("nameLength");
////        nameLength.setTagName("姓名长度");
////        nameLength.setTagType("1");
////        nameLength.setTagGroup("1");
////        nameLength.setTagValue("3");
////        nameLength.setHasMultipart("0");
////        nameLength.setBkType("person");
////        nameLength.setTaskId(ssbkTask.getId());
////        nameLength.setTaskType(ssbkTask.getTaskType());
////        nameLength.setTagStatus("1");
////        nameLength.setCreateTime(ssbkTask.getCreateTime());
////        nameLength.setCreateUserId(ssbkTask.getCreateUserId());
////        nameLength.setCreateUserIdCard(ssbkTask.getCreateUserIdCard());
////        nameLength.setCreateUserName(ssbkTask.getCreateUserName());
////        nameLength.setCreateUserDeptCode(ssbkTask.getCreateUserDeptCode());
////        nameLength.setCreateUserDeptName(ssbkTask.getCreateUserDeptName());
////        tagArray.add(nameLength);
//
//        SsbkTaskTag nameLength = new SsbkTaskTag();
//        nameLength.setId(UUIDUtil.getUUID());
//        nameLength.setTagCode("nameLength");
//        nameLength.setTagName("姓名长度");
//        nameLength.setTagType("1");
//        nameLength.setTagGroup("1");
//        nameLength.setTagValue("3");
//        nameLength.setHasMultipart("0");
//        nameLength.setBkType("person");
//        nameLength.setTaskId(ssbkTask.getId());
//        nameLength.setTaskType(ssbkTask.getTaskType());
//        nameLength.setTagStatus("1");
//        nameLength.setCreateTime(ssbkTask.getCreateTime());
//        nameLength.setCreateUserId(ssbkTask.getCreateUserId());
//        nameLength.setCreateUserIdCard(ssbkTask.getCreateUserIdCard());
//        nameLength.setCreateUserName(ssbkTask.getCreateUserName());
//        nameLength.setCreateUserDeptCode(ssbkTask.getCreateUserDeptCode());
//        nameLength.setCreateUserDeptName(ssbkTask.getCreateUserDeptName());
//        tagArray.add(nameLength);
//
//        put.addColumn(FAMILY_NAME.getBytes(),"tag".getBytes(), JSON.toJSONString(tagArray).getBytes());
//        boolean result = hbaseUtil.putData(NAMESPACE,TABLE_TASK, put);
//        logger.debug("result:{}",result);
//
//        for (Object o : tagArray) {
//            SsbkTaskTag ssbkTaskTag = (SsbkTaskTag) o;
//            String tagRowKey = ssbkTaskTag.getTagCode();
//            if (!ssbkTaskTag.getTagCode().equals("ageRange")){
//                tagRowKey = tagRowKey + ":" + ssbkTaskTag.getTagValue();
//            }
//            Put tagPut = new Put(tagRowKey.getBytes());
//
//            if (ssbkTaskTag.getId() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"id".getBytes(),ssbkTaskTag.getId().getBytes());
//            if (ssbkTaskTag.getTagCode() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"tagCode".getBytes(),ssbkTaskTag.getTagCode().getBytes());
//            if (ssbkTaskTag.getTagName() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"tagName".getBytes(),ssbkTaskTag.getTagName().getBytes());
//            if (ssbkTaskTag.getTagType() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"tagType".getBytes(),ssbkTaskTag.getTagType().getBytes());
//            if (ssbkTaskTag.getTagGroup() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"tagGroup".getBytes(),ssbkTaskTag.getTagGroup().getBytes());
//            if (ssbkTaskTag.getTagValue() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"tagValue".getBytes(),ssbkTaskTag.getTagValue().getBytes());
//            if (ssbkTaskTag.getHasMultipart() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"hasMultipart".getBytes(),ssbkTaskTag.getHasMultipart().getBytes());
//            if (ssbkTaskTag.getShowType() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"showType".getBytes(),ssbkTaskTag.getShowType().getBytes());
//            if (ssbkTaskTag.getBkType() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"bkType".getBytes(),ssbkTaskTag.getBkType().getBytes());
//            if (ssbkTaskTag.getTaskId() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"taskId".getBytes(),ssbkTaskTag.getTaskId().getBytes());
//            if (ssbkTaskTag.getTaskType() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"taskType".getBytes(),ssbkTaskTag.getTaskType().getBytes());
//            if (ssbkTaskTag.getStartTime() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"startTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTag.getStartTime()).getBytes());
//            if (ssbkTaskTag.getEndTime() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"endTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTag.getEndTime()).getBytes());
//            if (ssbkTaskTag.getThreshold() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"threshold".getBytes(),ssbkTaskTag.getThreshold().getBytes());
//            if (ssbkTaskTag.getTagStatus() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"targetStatus".getBytes(),ssbkTaskTag.getTagStatus().getBytes());
//            if (ssbkTaskTag.getCreateTime() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"createTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTag.getCreateTime()).getBytes());
//            if (ssbkTaskTag.getCreateUserId() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"createUserId".getBytes(),ssbkTaskTag.getCreateUserId().getBytes());
//            if (ssbkTaskTag.getCreateUserIdCard() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"createUserIdCard".getBytes(),ssbkTaskTag.getCreateUserIdCard().getBytes());
//            if (ssbkTaskTag.getCreateUserName() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"createUserName".getBytes(),ssbkTaskTag.getCreateUserName().getBytes());
//            if (ssbkTaskTag.getCreateUserDeptCode() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"createUserDeptCode".getBytes(),ssbkTaskTag.getCreateUserDeptCode().getBytes());
//            if (ssbkTaskTag.getCreateUserDeptName() !=null)
//            tagPut.addColumn(FAMILY_NAME.getBytes(),"createUserDeptName".getBytes(),ssbkTaskTag.getCreateUserDeptName().getBytes());
//
//            boolean tagResult = hbaseUtil.putData(NAMESPACE,TABLE_TAG, tagPut);
//            logger.debug("tagResult:{}",tagResult);
//        }
//    }
//
//    @Test
//    public void putTarget(){
////        SsbkTaskTarget ssbkTaskTarget = new SsbkTaskTarget();
////        ssbkTaskTarget.setId(UUIDUtil.getUUID());
////        ssbkTaskTarget.setBkKey("341021199503234215");
////        ssbkTaskTarget.setBkType("person");
////        ssbkTaskTarget.setBkSjy("idCard");
////        ssbkTaskTarget.setBkDesc("萧山人员测试");
////        ssbkTaskTarget.setBkLevel("1");
////        ssbkTaskTarget.setTaskId("80b824d33ed449f48c2d9d7a5c05ca9d");
////        ssbkTaskTarget.setTaskType("1");
////        ssbkTaskTarget.setIdCard("341021199503234215");
////        ssbkTaskTarget.setName("even");
////        ssbkTaskTarget.setHasCorrelation("1");
////        ssbkTaskTarget.setCorrelationKey("浙A88888");
////        ssbkTaskTarget.setCorrelationType("2");
////        ssbkTaskTarget.setCorrelationSjy("car");
////        ssbkTaskTarget.setStartTime(DateUtils.parseDateTime("2020-06-30 10:00:00"));
////        ssbkTaskTarget.setEndTime(DateUtils.parseDateTime("2020-07-23 10:00:00"));
////        ssbkTaskTarget.setThreshold("1");
////        ssbkTaskTarget.setTargetStatus("1");
////        ssbkTaskTarget.setCreateTime(DateUtils.parseDateTime("2020-06-30 12:00:00"));
////        ssbkTaskTarget.setCreateUserId("CD3B23792718675382AFB2C59670AC9F");
////        ssbkTaskTarget.setCreateUserIdCard("341021199503234215");
////        ssbkTaskTarget.setCreateUserName("even");
////        ssbkTaskTarget.setCreateUserDeptCode("3301040112");
////        ssbkTaskTarget.setCreateUserDeptName("下沙派出所");
//
//        SsbkTaskTarget ssbkTaskTarget = new SsbkTaskTarget();
//        ssbkTaskTarget.setId(UUIDUtil.getUUID());
//        ssbkTaskTarget.setBkKey("浙A88888");
//        ssbkTaskTarget.setBkType("car");
//        ssbkTaskTarget.setTargetType("car");
//        ssbkTaskTarget.setBkDesc("");
//        ssbkTaskTarget.setBkLevel("2");
//        ssbkTaskTarget.setTaskId("80b824d33ed449f48c2d9d7a5c05ca9d");
//        ssbkTaskTarget.setTaskType("1");
//        ssbkTaskTarget.setIdCard("");
//        ssbkTaskTarget.setName("");
//        ssbkTaskTarget.setHasCorrelation("0");
//        ssbkTaskTarget.setStartTime(DateUtils.parseDateTime("2020-06-30 12:00:00"));
//        ssbkTaskTarget.setEndTime(DateUtils.parseDateTime("2021-06-30 12:00:00"));
//        ssbkTaskTarget.setThreshold("1");
//        ssbkTaskTarget.setTargetStatus("1");
//        ssbkTaskTarget.setCreateTime(DateUtils.parseDateTime("2020-06-30 10:00:00"));
//        ssbkTaskTarget.setCreateUserId("CD3B23792718675382AFB2C59670AC9F");
//        ssbkTaskTarget.setCreateUserIdCard("341021199503234215");
//        ssbkTaskTarget.setCreateUserName("even");
//        ssbkTaskTarget.setCreateUserDeptCode("3301040112");
//        ssbkTaskTarget.setCreateUserDeptName("下沙派出所");
//
//        //目标
//        String rowKey = ssbkTaskTarget.getBkKey();
//        Put put = new Put(rowKey.getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"id".getBytes(),ssbkTaskTarget.getId().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"bkKey".getBytes(),ssbkTaskTarget.getBkKey().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"bkType".getBytes(),ssbkTaskTarget.getBkType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"targetType".getBytes(),ssbkTaskTarget.getTargetType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"bkDesc".getBytes(),ssbkTaskTarget.getBkDesc().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"bkLevel".getBytes(),ssbkTaskTarget.getBkLevel().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskId".getBytes(),ssbkTaskTarget.getTaskId().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskType".getBytes(),ssbkTaskTarget.getTaskType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"idCard".getBytes(),ssbkTaskTarget.getIdCard().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"name".getBytes(),ssbkTaskTarget.getName().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"hasCorrelation".getBytes(),ssbkTaskTarget.getHasCorrelation().getBytes());
////        put.addColumn(FAMILY_NAME.getBytes(),"correlationKey".getBytes(),ssbkTaskTarget.getCorrelationKey().getBytes());
////        put.addColumn(FAMILY_NAME.getBytes(),"correlationType".getBytes(),ssbkTaskTarget.getCorrelationType().getBytes());
////        put.addColumn(FAMILY_NAME.getBytes(),"correlationSjy".getBytes(),ssbkTaskTarget.getCorrelationSjy().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"startTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTarget.getStartTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"endTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTarget.getEndTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"threshold".getBytes(),ssbkTaskTarget.getThreshold().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"targetStatus".getBytes(),ssbkTaskTarget.getTargetStatus().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTarget.getCreateTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserId".getBytes(),ssbkTaskTarget.getCreateUserId().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserIdCard".getBytes(),ssbkTaskTarget.getCreateUserIdCard().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserName".getBytes(),ssbkTaskTarget.getCreateUserName().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserDeptCode".getBytes(),ssbkTaskTarget.getCreateUserDeptCode().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserDeptName".getBytes(),ssbkTaskTarget.getCreateUserDeptName().getBytes());
//
//        boolean result = hbaseUtil.putData(NAMESPACE,TABLE_TARGET, put);
//        logger.debug("result:{}",result);
//    }
//
//    @Test
//    public void putTag(){
//        //策略布控
//        SsbkTaskTag ssbkTaskTag = new SsbkTaskTag();
//        ssbkTaskTag.setId("tag_id_1");
//        ssbkTaskTag.setTagCode("midNightActive");
//        ssbkTaskTag.setTagName("后半夜活动");
//        ssbkTaskTag.setTagType("1");
//        ssbkTaskTag.setTagValueStart("01:00:00");
//        ssbkTaskTag.setTagValueEnd("06:00:00");
//        ssbkTaskTag.setHasMultipart("1");
//        ssbkTaskTag.setShowType("1");
//        ssbkTaskTag.setBkType("person");
//        ssbkTaskTag.setTaskId("task_id_1");
//        ssbkTaskTag.setTaskType("1");
//        ssbkTaskTag.setStartTime(DateUtils.parseDateTime("2020-06-10 12:00:00"));
//        ssbkTaskTag.setEndTime(DateUtils.parseDateTime("2020-07-10 12:00:00"));
//        ssbkTaskTag.setThreshold("1");
//        ssbkTaskTag.setTagStatus("1");
//        ssbkTaskTag.setCreateTime(DateUtils.parseDateTime("2020-06-10 12:00:00"));
//        ssbkTaskTag.setCreateUserId("user1");
//        ssbkTaskTag.setCreateUserIdCard("341021199503234215");
//        ssbkTaskTag.setCreateUserName("even");
//        ssbkTaskTag.setCreateUserDeptCode("330100000000");
//        ssbkTaskTag.setCreateUserDeptName("杭州市公安局");
//
//        String rowKey = ssbkTaskTag.getTagCode();
//        Put put = new Put(rowKey.getBytes());
//
//        put.addColumn(FAMILY_NAME.getBytes(),"id".getBytes(),ssbkTaskTag.getId().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"tagCode".getBytes(),ssbkTaskTag.getTagCode().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"tagName".getBytes(),ssbkTaskTag.getTagName().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"tagType".getBytes(),ssbkTaskTag.getTagType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"tagValue".getBytes(),ssbkTaskTag.getTagValue().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"hasMultipart".getBytes(),ssbkTaskTag.getHasMultipart().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"showType".getBytes(),ssbkTaskTag.getShowType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"bkType".getBytes(),ssbkTaskTag.getBkType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskId".getBytes(),ssbkTaskTag.getTaskId().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"taskType".getBytes(),ssbkTaskTag.getTaskType().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"startTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTag.getStartTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"endTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTag.getEndTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"threshold".getBytes(),ssbkTaskTag.getThreshold().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"targetStatus".getBytes(),ssbkTaskTag.getTagStatus().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createTime".getBytes(),DateUtils.formatDateTime(ssbkTaskTag.getCreateTime()).getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserId".getBytes(),ssbkTaskTag.getCreateUserId().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserIdCard".getBytes(),ssbkTaskTag.getCreateUserIdCard().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserName".getBytes(),ssbkTaskTag.getCreateUserName().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserDeptCode".getBytes(),ssbkTaskTag.getCreateUserDeptCode().getBytes());
//        put.addColumn(FAMILY_NAME.getBytes(),"createUserDeptName".getBytes(),ssbkTaskTag.getCreateUserDeptName().getBytes());
//
//        boolean result = hbaseUtil.putData(NAMESPACE,TABLE_TAG, put);
//        logger.debug("result:{}",result);
//    }
//}