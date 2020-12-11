package cn._51even.wireless.core.base.dal;

import cn._51even.wireless.core.base.dal.provider.SpecialMysqlAndOthersMapper;
import tk.mybatis.mapper.common.ConditionMapper;

public interface BaseMapper<T> extends
        tk.mybatis.mapper.common.Mapper<T>
        ,ConditionMapper<T>,
        SpecialMysqlAndOthersMapper<T>
{

}
