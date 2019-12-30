package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.CommentsDao;
import com.zkl.l_music.entity.CommentsEntity;
import com.zkl.l_music.service.CommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService {

    @Resource
    CommentsDao commentsDao;

    @Override
    public boolean addComments(CommentsEntity commentsEntity) {
        int res = commentsDao.insert(commentsEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateComments(CommentsEntity commentsEntity) {
        int res = commentsDao.updateById(commentsEntity);
        if( res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteComments(String id) {
        int res = commentsDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public CommentsEntity getCommentById(String id) {
        return commentsDao.selectById(id);
    }
}
