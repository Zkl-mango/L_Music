package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.CommentsLikeDao;
import com.zkl.l_music.entity.CommentsLikeEntity;
import com.zkl.l_music.service.CommentsLikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentsLikeServiceImpl implements CommentsLikeService {

    @Resource
    CommentsLikeDao commentsLikeDao;

    @Override
    public boolean addCommentsLike(CommentsLikeEntity commentsLikeEntity) {
        int res = commentsLikeDao.insert(commentsLikeEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }
}
