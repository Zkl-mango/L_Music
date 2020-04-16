package com.zkl.l_music.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.CommentsBo;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.dao.CommentsDao;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.data.SingerData;
import com.zkl.l_music.entity.CommentsEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.CommentsLikeService;
import com.zkl.l_music.service.CommentsService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.PageUtils;
import com.zkl.l_music.util.RequestHolder;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.CommentsDetailVo;
import com.zkl.l_music.vo.CommentsVo;
import com.zkl.l_music.vo.MyCommentVo;
import com.zkl.l_music.vo.PageInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.stream.events.Comment;
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
    @Resource
    SingerDao singerDao;
    @Resource
    UserDao userDao;
    @Resource
    CommentsLikeService commentsLikeService;

    @Override
    public boolean addComments(CommentsBo commentsBo, String userId) {
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setId(uuidGenerator.generateUUID());
        commentsEntity.setComment(commentsBo.getComment());
        commentsEntity.setLikes(0);
        commentsEntity.setSongId(songDao.selectById(commentsBo.getSongId()));
        commentsEntity.setTime(new Date());
        UserEntity userEntity = userDao.selectById(userId);
        commentsEntity.setUserId(userEntity);
        commentsEntity.setIsHot(0);
        int res = commentsDao.insert(commentsEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCommentsLike(String id,int type) {
        CommentsEntity commentsEntity = commentsDao.selectById(id);
        if(commentsEntity == null) {
            return false;
        }
        commentsEntity.setLikes(commentsEntity.getLikes()+type);
        int res = commentsDao.updateById(commentsEntity);
        if( res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateCommentsLikeRedis(List<CommentsEntity> list) {
        for(int i=0;i<list.size();i++) {
            CommentsEntity commentsEntity = commentsDao.selectById(list.get(i).getId());
            commentsEntity.setLikes(list.get(i).getLikes());
            commentsDao.updateById(commentsEntity);
        }
        return true;
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
    public CommentsVo getCommentsBySong(PageBo pageBo,String songId,String userId) {
        //更新评论的点赞数
        List<CommentsEntity> commentsEntities = commentsLikeService.getLikedCountFromRedis();
        this.updateCommentsLikeRedis(commentsEntities);
        //先把所有评论是否热度字段设为0
        commentsDao.updateAll(songId);
        List<CommentsDetailVo> hotList = new ArrayList<>();
        //获取热度最高的
        List<CommentsEntity> list = commentsDao.selectCommentsByLike(songId);
        //把热度最高的isHot字段修改
        for(int i=0;i<list.size();i++) {
            list.get(i).setIsHot(1);
            UserEntity userEntity = list.get(i).getUserId();
            list.get(i).setUserId(null);
            list.get(i).setSongId(null);
            commentsDao.updateById(list.get(i));
            list.get(i).setUserId(userEntity);
            CommentsDetailVo commentsDetailVo = commentsLikeService.getCommentByUser(userId,list.get(i));
            hotList.add(commentsDetailVo);
        }
        Page page = new Page(pageBo.getPage(),pageBo.getSize());
        IPage iPage = commentsDao.selectComments(page,songId);
        PageInfoVo pageInfoVo = PageUtils.generatePageVo(iPage);
        List<CommentsDetailVo> reslist = new ArrayList<>();
        for(int i=0;i<pageInfoVo.getList().size();i++) {
            List<CommentsEntity> pageList = pageInfoVo.getList();
            CommentsDetailVo commentsDetailVo = commentsLikeService.getCommentByUser(userId,pageList.get(i));
            reslist.add(commentsDetailVo);
        }
        pageInfoVo.setList(reslist);
        CommentsVo commentsVo = new CommentsVo();
        commentsVo.setCommentsEntityList(hotList);
        commentsVo.setCommentsPage(pageInfoVo);
        return commentsVo;
    }

    @Override
    public List<MyCommentVo> getCommentsByUser(String userId) {
        List<CommentsEntity> list = commentsDao.selectCommentsByUser(userId);
        List<MyCommentVo> res = new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            MyCommentVo myCommentVo = new MyCommentVo();
            myCommentVo.setId(list.get(i).getId());
            myCommentVo.setContent(list.get(i).getComment());
            myCommentVo.setNum(list.get(i).getLikes());
            myCommentVo.setTime(list.get(i).getTime());
            SingerEntity singerEntity = singerDao.selectById(list.get(i).getSongId().getSingerId());
            String name = singerEntity.getSinger()+"-"+list.get(i).getSongId().getName();
            myCommentVo.setSongs(name);
            res.add(myCommentVo);
        }
        return res;
    }

}
