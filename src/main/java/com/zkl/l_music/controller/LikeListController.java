package com.zkl.l_music.controller;

import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.service.LikeListService;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.AlbumVo;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/likeList")
public class  LikeListController {

    @Resource
    LikeListService likeListService;
    @Resource
    SongDetailsService songDetailsService;
    @Resource
    SongListService songListService;

    @DeleteMapping(value = "/{type}/{ids}")
    public ResponseEntity deleteSongList(@PathVariable int type,@PathVariable String ids) {
        String[] idArray = ids.split(",");
        for(int i=0;i<idArray.length;i++) {
            //删除收藏的歌单
            boolean res1 = likeListService.deleteLikeList(idArray[i],type);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("删除歌单成功"));
    }

    @GetMapping(value = "/{type}")
    public ResponseEntity getLikeListByUser(HttpServletRequest request,@PathVariable int type) {
        String  userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        Map res = new HashMap();
        if(type == 3) {
            List<SongListVo> list = likeListService.getLikeListByUser(userId,type);
            res.put("list",list);
        }
        if(type ==2) {
            List<AlbumVo> albumList = likeListService.getLikeListAlbumByUser(userId,type);
            res.put("list",albumList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAllLikeListByUser(HttpServletRequest request) {
        String  userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        //喜欢的歌曲
        List<SongListVo> songList = songListService.getSongListByUser(userId,2);
        List<SongListDetailVo> likeSongList = songDetailsService.getSongDetailsByList(songList.get(0).getId());
        //喜欢的专辑
        List<AlbumVo> albumList = likeListService.getLikeListAlbumByUser(userId, ConstantUtil.albumType);
        //喜欢的歌单
        List<SongListVo> songlist = likeListService.getLikeListByUser(userId,ConstantUtil.listType);
        Map<Object,Object> res = new HashMap<>();
        res.put("likeSong",likeSongList);
        res.put("albumList",albumList);
        res.put("songList",songlist);
        res.put("likeSongId",songList.get(0).getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }
}
