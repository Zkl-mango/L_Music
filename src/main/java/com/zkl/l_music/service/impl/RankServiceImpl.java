package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.RankDao;
import com.zkl.l_music.dao.RankListDao;
import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.RankListEntity;
import com.zkl.l_music.service.RankService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.vo.RankDetailVo;
import com.zkl.l_music.vo.RankVo;
import com.zkl.l_music.vo.SongVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RankServiceImpl implements RankService {

    @Resource
    RankDao rankDao;
    @Resource
    RankListDao rankListDao;
    @Resource
    SongService songService;

    @Override
    public boolean addRank(RankEntity rankEntity) {
        if(rankEntity == null) {
            return false;
        }
        int res = rankDao.insert(rankEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRank(String id) {
        RankEntity rankEntity = rankDao.selectById(id);
        if(rankEntity == null) {
            return false;
        }
        RankEntity rank = new RankEntity();
        rank.setId(id);
        rank.setPlayNum( rankEntity.getPlayNum()+1);
        rank.setRecomment(rankEntity.getRecomment());
        rank.setType(rankEntity.getType());
        rankDao.updateById(rank);
        return true;
    }

    @Override
    public Map<Object, Object> getRankList() {
        List<RankEntity> rankList = rankDao.selectRankByRecomment(1);
        List<RankEntity> highs = rankDao.selectRankByType(ConstantUtil.dianfeng);
        List<RankVo> highList = new ArrayList<>();
        //巅峰榜详情
        for(int i=0;i<highs.size();i++) {
            RankVo rankVo = new RankVo();
            List<RankListEntity> highDetails = rankListDao.selectRankListByRecomment(highs.get(i).getId(),1);
            List<RankDetailVo> rankDetailVo = this.changeRankDetailVo(highDetails);

            rankVo.setRank(highs.get(i));
            rankVo.setRankDetais(rankDetailVo);
            highList.add(rankVo);
        }
        List<RankEntity> regionList = rankDao.selectRankByType(ConstantUtil.diqu);
        List<RankEntity> specialList = rankDao.selectRankByType(ConstantUtil.tese);
        List<RankEntity> wholeList = rankDao.selectRankByType(ConstantUtil.quanqiu);
        Map<Object,Object> res = new HashMap<>();
        res.put("rankList",rankList);
        res.put("highList",highList);
        res.put("regionList",regionList);
        res.put("specialList",specialList);
        res.put("wholeList",wholeList);
        return res;
    }

    @Override
    public RankEntity getRankById(String id) {
        return rankDao.selectById(id);
    }

    @Override
    public List<RankDetailVo> getRankListById(String id) {
        List<RankListEntity> rankListEntities = rankListDao.selectRankListByRank(id);
        List<RankDetailVo> list = this.changeRankDetailVo(rankListEntities);
        return list;
    }

    private List<RankDetailVo> changeRankDetailVo(List<RankListEntity> rankListEntities) {
        List<RankDetailVo> list = new ArrayList<>();
        for(int i=0;i<rankListEntities.size();i++) {
            RankDetailVo rankDetailVo = new RankDetailVo();
            rankDetailVo.setId(rankListEntities.get(i).getId());
            rankDetailVo.setRecomment(rankListEntities.get(i).getRecomment());
            SongVo songVo = songService.songChangeVo(rankListEntities.get(i).getSongId());
            rankDetailVo.setSongVo(songVo);
            list.add(rankDetailVo);
        }
        return list;
    }
}
