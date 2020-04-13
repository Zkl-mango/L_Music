package com.zkl.l_music.vo;

import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.RankListEntity;
import com.zkl.l_music.entity.SongEntity;
import lombok.Data;

import java.util.List;

@Data
public class RankDetailVo {

    private String id;
    private SongVo songVo;
    private int recomment;
}
