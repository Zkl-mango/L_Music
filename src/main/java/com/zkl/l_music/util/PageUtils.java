package com.zkl.l_music.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zkl.l_music.vo.PageInfoVo;

public class PageUtils {

    /**
     * 获取自定义分页类
     * @param page PageHelper查出开的分页数据
     * @return 自定义分页
     */
    public static PageInfoVo generatePageVo(IPage page){
        return PageInfoVo.builder()
                .totalPage(page.getPages())
                .page(page.getCurrent())
                .total(page.getTotal())
                .size(page.getSize())
                .list(page.getRecords())
                .build();
    }
/*

    */
/**
     * 生成
     * @param page PageHelper查出开的分页数据
     * @param voList 重新分页Vo列表
     * @return
     *//*

    public static PageInfoVo generatePageVo(Page page, List voList){
        return PageInfoVo.builder()
                .pageNo(page.getPageNum())
                .total(page.getTotal())
                .pageSize(page.getPageSize())
                .pageTotal(page.getPages())
                .pageData(voList)
                .build();
    }

    */
/**
     * 生成空页
     * @param pageSize
     * @param pageNo
     * @return
     *//*

    public static PageInfoVo emptyPageVo(int pageSize,int pageNo){
        return PageInfoVo.builder()
                .pageNo(pageNo)
                .total((long) 0)
                .pageSize(pageSize)
                .pageTotal(0)
                .pageData(Lists.newArrayList())
                .build();
    }

    public static PageInfoVo generatePageVo(List list, int pageSize,int pageNo){
        return PageInfoVo.builder()
                .pageNo(pageNo)
                .total((long) 1000)
                .pageSize(pageSize)
                .pageTotal(new BigDecimal(1000).divide(new BigDecimal(pageSize), 0, BigDecimal.ROUND_UP).intValue())
                .pageData(list)
                .build();

    }
*/

}
