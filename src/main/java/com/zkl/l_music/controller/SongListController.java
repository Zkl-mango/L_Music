package com.zkl.l_music.controller;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.bo.SongDetailBo;
import com.zkl.l_music.entity.SongDetailsEntity;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.*;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.*;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/songList")
public class SongListController {

    @Resource
    SongListService songListService;
    @Resource
    SongDetailsService songDetailsService;
    @Resource
    TagService tagService;
    @Resource
    SongService songService;
    @Resource
    HistoryListService historyListService;
    @Resource
    RecommentService recommentService;
    /**
     * 创建歌单
     * @param request
     * @param songListEntity
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity addSongList(HttpServletRequest request,@RequestBody SongListEntity songListEntity) {
        String id = request.getHeader("userId");
        String res = songListService.addSongList(songListEntity.getListName(),id,1);
        if(!StringUtils.isBlank(res)) {
           return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
       }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 修改歌单
     * @param songListEntity
     * @return
     */
    @PutMapping(value = "")
    public ResponseEntity updateSongList(@RequestBody SongListEntity songListEntity) {
        boolean res = songListService.updateSongList(songListEntity);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("修改成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 更新歌单封面
     * @param id
     * @return
     */
    @PostMapping(value="/{id}/uploadImage")
    public ResponseEntity uploadImage(@PathVariable String id,@RequestParam("file") MultipartFile file) {
        boolean res = songListService.updateSongListPicture(id,file);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(id));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    @PutMapping(value = "/{id}/{type}/{flag}")
    public ResponseEntity likeSongList(HttpServletRequest request,@PathVariable String id,@PathVariable int flag,@PathVariable int type) {
        String userId = request.getHeader("userId");
        boolean res = songListService.updateSongListNum(id,flag,type,userId);
        if(flag == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("已收藏到我的喜欢"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("取消收藏成功"));
        }
    }

    /**
     * 删除歌单
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/{ids}")
    public ResponseEntity deleteSongList(@PathVariable String ids) {
        String[] idArray = ids.split(",");
        for(int i=0;i<idArray.length;i++) {
            //删除歌单中的歌曲
            boolean res = songDetailsService.deleteSongDetailsByList(idArray[i]);
            //删除歌单
            boolean res1 = songListService.deleteSongList(idArray[i]);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("删除歌单成功"));
    }

    @GetMapping(value = "")
    public ResponseEntity getSongListByUser(HttpServletRequest request) {
        String  userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        List<SongListVo> list = songListService.getSongListByUser(userId,1);
        List<SongListVo> likeList = songListService.getSongListByUser(userId,2);
        Map res = new HashMap();
        res.put("list",list);
        res.put("likeList",likeList);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }



    /**
     * 修改时获取歌单详情和标签列表
     * @param id
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    public ResponseEntity getUpdateSongList(HttpServletRequest request,@PathVariable String id) {
        String userId = request.getHeader("userId");
        SongListVo songListVo = songListService.getSongListById(id,userId);
        List<TagVo> tagList = tagService.getTagList();
        Map<Object,Object> res = new HashMap<>();
        res.put("detail",songListVo);
        res.put("tagList",tagList);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }

    /**
     * 获取歌单信息以及歌单下歌曲信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getSongList(HttpServletRequest request,@PathVariable String id) {
        String userId = request.getHeader("userId");
        Map<Object,Object> res = new HashMap<>();
        if(userId.equals("undefined")) {
            userId = null;
        }
        if(id.equals("hot")) {
            List<SongListDetailVo> list = songService.getSongsByHot();
            res.put("songs",list);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
        }
        if(id.equals("history")) {
            List<HistoryListVo> historyListVos = historyListService.getHistoryListByUser(userId);
            res.put("songs",historyListVos);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
        }
        if(id.equals("recomment")) {
            List<SongListDetailVo> list = recommentService.getRecommentsSong(userId);
            res.put("songs",list);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
        }
        SongListVo songListVo = songListService.getSongListById(id,userId);
        List<SongListDetailVo> songListDetail = songDetailsService.getSongDetailsByList(id);
        res.put("detail",songListVo);
        res.put("songs",songListDetail);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }

    /**
     * 通过标签获取歌单
     * @param tag
     * @param pageBo
     * @return
     */
    @GetMapping(value = "/all/{tag}")
    public ResponseEntity getAllSongList(@PathVariable String tag, PageBo pageBo) {
        Map<Object,Object> res = new HashMap<>();
        if(tag.equals("推荐")) {
            List<SongListVo> hotList = songListService.getLikeSongList();
            res.put("hotList",hotList);
        }
        PageInfoVo songPage = songListService.getSongListByTag(pageBo,tag);
        res.put("songPage",songPage);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }

    /**
     * 添加歌曲到对应的歌单
     * @param songDetailBo
     * @return
     */
    @PostMapping(value = "/details")
    public ResponseEntity addSongDetails(@RequestBody @Valid SongDetailBo songDetailBo) {
        boolean res = songDetailsService.addSongDetails(songDetailBo);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("添加成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 删除歌单中的某一首歌曲
     * @param songId
     * @return
     */
    @DeleteMapping(value = "/delete/{songId}")
    public ResponseEntity deteledSongDetails(@PathVariable String songId) {
        boolean res = songDetailsService.updateSongDetails(songId);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("删除成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 收藏/取消收藏歌曲
     * @param request
     * @param songDetailBo
     * @return
     */
    @PostMapping(value = "/like/{type}")
    public ResponseEntity addSongDetails(HttpServletRequest request,@RequestBody @Valid SongDetailBo songDetailBo,
                                         @PathVariable int type) {
        String userId = request.getHeader("userId");
        if(userId.equals("undefined")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        List<SongListVo> songListEntities = songListService.getSongListByUser(userId,2);
        //收藏
        if(type == 1) {
            songDetailBo.setSongList(songListEntities.get(0).getId());
            boolean res = songDetailsService.addSongDetails(songDetailBo);
            if(res) {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("收藏成功"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
        }
        //取消收藏
        SongDetailsEntity songDetailsEntity = songDetailsService.getSongDetailsBySongAndList(
                songListEntities.get(0).getId(),songDetailBo.getSongId());
        boolean res = songDetailsService.updateSongDetails(songDetailsEntity.getId());
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("取消收藏成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }
}
