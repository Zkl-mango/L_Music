package com.zkl.l_music.service;

import com.zkl.l_music.bo.CommentsBo;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.entity.CommentsEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.CommentsVo;

import java.util.List;

public interface CommentsService {

    boolean addComments(CommentsBo commentsBo, UserEntity userEntity);

    boolean updateCommentsLike(String id,int type);

    //批量更新redis中的点赞数
    boolean updateCommentsLikeRedis(List<CommentsEntity> list);

    boolean deleteComments(String id, String userId);

    CommentsEntity getCommentById(String id);

    CommentsVo getCommentsBySong(PageBo page, String songId);


}
