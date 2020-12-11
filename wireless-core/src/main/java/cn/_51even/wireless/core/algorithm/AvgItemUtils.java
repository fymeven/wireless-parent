package cn._51even.wireless.core.algorithm;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AvgItemUtils {

    private static Logger logger = LoggerFactory.getLogger(AvgItemUtils.class);
    /**
     *
     * @param total 分配总数
     * @param num 分配个数
     * @param start 第一个元素下标起始值，默认1
     * @return
     */
    public static Map<Integer, List<Integer>> getAvgItemListMap(Integer total,Integer num,Integer start){
        if (total == null || num == null){
            return null;
        }
        if (start == null){
            start = 1;
        }
        //平均分配算法,先将能整除的平均分配,不能整除的取模按顺序追加到每个元素中
        Map<Integer,List<Integer>> listMap = new LinkedHashMap<>();
        //取平均值
        int div = BigDecimal.valueOf((long) total).divide(BigDecimal.valueOf((long) num), BigDecimal.ROUND_FLOOR).intValue();
        int count = 0;
        for (int i = 0; i < num; i++) {
            List<Integer> itemList = new ArrayList<>();
            for (int j = count * div; j < (count + 1) * div; j++) {
                itemList.add(j+start);
            }
            listMap.put(i,itemList);
            count++;
        }
        //取模
        int mod = 10 % 3;
        int member = start;
        for (Map.Entry<Integer, List<Integer>> entry : listMap.entrySet()) {
            if (member < mod+start){
                entry.getValue().add(div * num + member);
            }
            member ++;
        }
        logger.debug(JSONObject.toJSONString(listMap));
        return listMap;
    }

}
