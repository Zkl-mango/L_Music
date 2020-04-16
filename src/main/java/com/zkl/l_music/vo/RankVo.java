package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.RankListEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class RankVo implements Serializable {
    private RankEntity rank;

    private List<RankDetailVo> rankDetais;
}
