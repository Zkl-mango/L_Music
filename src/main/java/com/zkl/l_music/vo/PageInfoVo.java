package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(value = {"handler"})
public class PageInfoVo<T> implements Serializable {
    /**
     * 当前页码
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long size;
    /**
     * 总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 列表数据
     */
    private List list;

}