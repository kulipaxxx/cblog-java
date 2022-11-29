package com.cheng.controller.additional;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheng.common.dto.CommentDto;
import com.cheng.common.lang.Result;
import com.cheng.entity.Comment;
import com.cheng.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户评论表 前端控制器
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-29
 */
@RestController
@RequestMapping("/api/Comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 获取博客所有评论
     *
     * @param blogId 博客id
     * @return {@link Result}
     */
    @RequestMapping(method = RequestMethod.GET,path = "/getComments")
    public Result getComments(Long blogId){
        List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("blog_id", blogId));
        ArrayList<CommentDto> list = new ArrayList<>();
        //循环集合
        for (Comment comment : comments) {
            CommentDto dto = new CommentDto();
            BeanUtil.copyProperties(comment,dto);
            list.add(dto);
        }
        processComments(list);
        return Result.success(list);
    }

    /**
     * 添加评论
     *
     * @param commentDto 评论dto
     * @return {@link Result}
     */
    @RequestMapping(method = RequestMethod.POST,path = "/alterComment")
    public Result alterComment(CommentDto commentDto){
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentDto,comment);
        boolean save = commentService.save(comment);
        String msg;
        if (save){
            msg = "评论成功";
        }else {
            msg = "评论失败";
        }

        return Result.success(msg);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/removeComment")
    public Result removeComent(CommentDto commentDto){
        boolean flag = commentService.removeComment(commentDto);
        String msg;
        if (flag){
            msg = "删除失败";
        }else{
            msg = "删除成功";
        }

        return Result.success(msg);
    }


    /**
     * 构建评论树
     * @param list
     * @return
     */
    public static List<CommentDto> processComments(List<CommentDto> list) {
        Map<Long, CommentDto> map = new HashMap<>();   // (id, CommentDto)
        List<CommentDto> result = new ArrayList<>();
        // 将所有根评论加入 map
        for(CommentDto CommentDto : list) {
            if(CommentDto.getParentId() == null)//根节点
                result.add(CommentDto);
            map.put(CommentDto.getId(), CommentDto);
        }
        // 子评论加入到父评论的 child 中
        for(CommentDto CommentDto : list) {
            Long id = CommentDto.getParentId();
            if(id != null) {   // 当前评论为子评论
                CommentDto p = map.get(id);
                if(p.getChild() == null)    // child 为空，则创建
                    p.setChild(new ArrayList<>());
                p.getChild().add(CommentDto);
            }
        }
        return result;
    }

}
