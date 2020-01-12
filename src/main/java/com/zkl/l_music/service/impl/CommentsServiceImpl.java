package com.zkl.l_music.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.CommentsBo;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.dao.CommentsDao;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.entity.CommentsEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.CommentsService;
import com.zkl.l_music.util.PageUtils;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.CommentsVo;
import com.zkl.l_music.vo.PageInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService {

    @Resource
    CommentsDao commentsDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    SongDao songDao;

    @Override
    public boolean addComments(CommentsBo commentsBo, UserEntity userEntity) {
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setId(uuidGenerator.generateUUID());
        commentsEntity.setComment(commentsBo.getComment());
        commentsEntity.setLikes(0);
        commentsEntity.setSongId(songDao.selectById(commentsBo.getSongId()));
        commentsEntity.setTime(new Date());
        commentsEntity.setUserId(userEntity);
        commentsEntity.setIsHot(0);
        int res = commentsDao.insert(commentsEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCommentsLike(String id) {
        CommentsEntity commentsEntity = commentsDao.selectById(id);
        if(commentsEntity == null) {
            return false;
        }
        commentsEntity.setLikes(commentsEntity.getLikes()+1);
        int res = commentsDao.updateById(commentsEntity);
        if( res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteComments(String id, String userId) {
        CommentsEntity commentsEntity = commentsDao.selectById(id);
        if(commentsEntity == null) {
            return false;
        }
        if(!commentsEntity.getUserId().getId().equals(userId)) {
            return false;
        }
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

    @Override
    public CommentsVo getCommentsBySong(PageBo pageBo,String songId) {
        //先把所有评论是否热度字段设为0
        commentsDao.updateAll(songId);
        //获取热度最高的
        List<CommentsEntity> list = commentsDao.selectCommentsByLike(songId);
        //把热度最高的isHot字段修改
        for(int i=0;i<list.size();i++) {
            list.get(i).setIsHot(1);
            commentsDao.updateById(list.get(i));
        }
        Page page = new Page(pageBo.getPage(),pageBo.getSize());
        IPage iPage = commentsDao.selectComments(page,songId);
        PageInfoVo pageInfoVo = PageUtils.generatePageVo(iPage);
        CommentsVo commentsVo = new CommentsVo();
        commentsVo.setCommentsEntityList(list);
        commentsVo.setCommentsPage(pageInfoVo);
        return commentsVo;
    }


}
