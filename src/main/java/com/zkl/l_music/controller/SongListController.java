package com.zkl.l_music.controller;

import com.zkl.l_music.bo.SongDetailBo;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.SongListVo;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/songList")
public class SongListController {

    @Resource
    SongListService songListService;
    @Resource
    SongDetailsService songDetailsService;

    /**
     * 创建歌单
     * @param request
     * @param songListEntity
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity addSongList(HttpServletRequest request,@RequestBody @Valid SongListEntity songListEntity) {
        UserEntity userEntity = (UserEntity) request.getSession().getAttribute("userEntity");
        if(userEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        String res = songListService.addSongList(songListEntity,userEntity);
       if(StringUtils.isBlank(res)) {
           return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
       }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 修改歌单名称
     * @param id
     * @param name
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity updateSongList(@PathVariable String id,@RequestBody  String name) {
        boolean res = songListService.updateSongList(id,name);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("修改歌单名成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 删除歌单
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteSongList(@PathVariable String id) {
        //删除歌单中的歌曲
        boolean res = songDetailsService.deleteSongDetailsByList(id);
        //删除歌单
        boolean res1 = songListService.deleteSongList(id);
        if(res&&res1) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("删除歌单成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    @GetMapping(value = "")
    public ResponseEntity getSongListByUser(HttpServletRequest request) {
        String  userId = (String) request.getSession().getAttribute("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
//        List<SongListEntity> list = songListService.getSongListByUser(userId,1);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
    }

    /**
     * 获取歌单详情
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getSongList(@PathVariable String id) {
        SongListVo songListVo = songListService.getSongListById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(songListVo));
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
