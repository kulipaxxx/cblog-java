package com.cheng.controller.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: FileVo
 * @Author cheng
 * @Date: 2022/11/25 22:44
 * @Version 1.0
 */
@Data
public class FileVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文章标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("文章创建时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime created;

}
