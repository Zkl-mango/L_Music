package com.zkl.l_music.service;

import com.zkl.l_music.entity.CommentsEntity;

public interface CommentsService {

    boolean addComments(CommentsEntity commentsEntity);

    boolean updateComments(CommentsEntity commentsEntity);

    boolean deleteComments(String id);

    CommentsEntity getCommentById(String id);
}
