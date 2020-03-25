package com.zkl.l_music.controller;

import com.zkl.l_music.service.TagService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.vo.TagVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/tag")
public class TagController {

    @Resource
    TagService tagService;

    @GetMapping("")
    public ResponseEntity getTag() {
        List<TagVo> categories = tagService.getTagList();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(categories));
    }
}
