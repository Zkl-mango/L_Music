package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(value = {"handler"})
public class SmallTagVo implements Serializable {

    private String name;
    private boolean inverted;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted() {
        this.inverted = false;
    }

    public String getType() {
        return type;
    }

    public void setType() {
        this.type = "default";
    }
}
