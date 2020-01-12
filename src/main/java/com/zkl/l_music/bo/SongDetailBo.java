package com.zkl.l_music.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.entity.SongListEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SongDetailBo implements Serializable {

    @NotBlank(message = "songId is null")
    private String songId;          /*歌曲id*/
    @NotBlank(message = "songList is null")
    private String songList;    /*所属列表id*/

}
