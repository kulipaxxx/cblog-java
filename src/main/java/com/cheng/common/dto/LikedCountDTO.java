package com.cheng.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikedCountDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String infoId;
    private Integer value;

    public LikedCountDTO(String infoId, Integer value) {
        this.infoId = infoId;
        this.value = value;
    }
}
