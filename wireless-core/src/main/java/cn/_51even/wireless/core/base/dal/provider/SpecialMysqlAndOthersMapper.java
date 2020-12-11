package cn._51even.wireless.core.base.dal.provider;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface SpecialMysqlAndOthersMapper<T> {
    @InsertProvider(
            type = SpecialMysqlAndOthersProvider.class,
            method = "dynamicSQL"
    )
    int insertMysqlAndOthersList(List<T> var1);
}
