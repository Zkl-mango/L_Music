package com.zkl.l_music.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PageBo {

    @NotNull(message = "page is null")
    private int page;
    @NotNull(message = "page is null")
    private int size;
}
