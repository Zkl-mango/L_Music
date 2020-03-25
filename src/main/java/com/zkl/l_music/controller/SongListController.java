package com.zkl.l_music.controller;

import com.zkl.l_music.bo.SongDetailBo;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.service.TagService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.SongListVo;
import com.zkl.l_music.vo.TagVo;
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
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(list));
    }

    /**
     * 获取歌单详情
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getSongList(@PathVariable String id) {
        SongListVo songListVo = songListService.getSongListById(id);
        List<TagVo> tagList = tagService.getTagList();
        Map<Object,Object> res = new HashMap<>();
        res.put("detail",songListVo);
        res.put("tagList",tagList);
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
    @PutMapping(value = "/details/{songId}")
    public ResponseEntity deteledSongDetails(@PathVariable String songId) {
        boolean res = songDetailsService.updateSongDetails(songId);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("删除成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

}
