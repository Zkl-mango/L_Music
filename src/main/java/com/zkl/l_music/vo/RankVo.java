package com.zkl.l_music.vo;

import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.RankListEntity;
import lombok.Data;

import java.util.List;

@Data
public class RankVo {
    private RankEntity rank;

    private List<RankDetailVo> rankDetais;
}
