package cn._51even.wireless.core.util;

import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

public class PageHelperUtil {

    public static PageInfo buildPageInfo(List list, long total, Integer pageNum, Integer pageSize){
        com.github.pagehelper.Page gitPage = new com.github.pagehelper.Page();
        for (int i = 0; i < list.size(); i++) {
            gitPage.add(i,list.get(i));
        }
        gitPage.setTotal(total);
        BigDecimal total_decimal = BigDecimal.valueOf(total);
        BigDecimal pageSize_decimal = BigDecimal.valueOf((long) pageSize);
        int pages = total_decimal.divide(pageSize_decimal, BigDecimal.ROUND_FLOOR).intValue();
        gitPage.setPages(pages);
        gitPage.setPageNum(pageNum);
        gitPage.setPageSize(pageSize);
        gitPage.setStartRow((pageNum-1) * pageSize);
        PageInfo pageInfo = new PageInfo(gitPage);
        return pageInfo;
    }
}
