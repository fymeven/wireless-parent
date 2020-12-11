package cn._51even.wireless.core.config;


import cn._51even.wireless.core.base.dal.BaseMapper;

public class ProjectConfig {

    public static String BASE_MAPPER = BaseMapper.class.getName();

    public static String MAPPER_PACKAGE = "cn._51even.**.dal.mapper";

    public static String TYPE_HANDLERS_PACKAGE = "cn._51even.**.dal.typehandler";
                                    
    public static String MODEL_PACKAGE = "cn._51even.**.bean.entity";

    public static String MAPPING_PACKAGE = "classpath*:/cn/_51even/**/dal/mapping/**/*.xml";
}
