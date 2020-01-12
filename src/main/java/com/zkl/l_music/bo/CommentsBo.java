package com.zkl.l_music.bo;

import com.zkl.l_music.entity.SongEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CommentsBo implements Serializable {

    @NotBlank(message = "comment is null")
    private String comment;
    @NotBlank(message = "songId is null")
    private String songId;

}
