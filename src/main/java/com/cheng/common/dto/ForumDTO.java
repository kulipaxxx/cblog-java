package com.cheng.common.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ForumDTO implements Serializable {


    @Min(groups = {ForumDTO.SearchComment.class, SearchMyReply.class, searchMyLikePost.class, SearchMyCreatePost.class, SearchPostByBlogId.class, SearchCommentList.class, SearchPostList.class}, value = 1, message = "每页数据最少一条")
    @NotNull(groups = {ForumDTO.SearchComment.class, SearchMyReply.class, searchMyLikePost.class, SearchMyCreatePost.class, SearchPostByBlogId.class, SearchCommentList.class, SearchPostList.class}, message = "[pageSize]字段不能为空")
    private Integer pageSize;

    @Min(groups = {ForumDTO.SearchComment.class, SearchMyReply.class, searchMyLikePost.class, SearchMyCreatePost.class, SearchCommentList.class, SearchPostByBlogId.class, SearchPostList.class}, value = 1, message = "页码必须大于零")
    @NotNull(groups = {ForumDTO.SearchComment.class, SearchMyReply.class, searchMyLikePost.class, SearchMyCreatePost.class, SearchCommentList.class, SearchPostByBlogId.class, SearchPostList.class}, message = "[pageNum]字段不能为空")
    private Integer pageNum;

    /**
     * 排序 1 更新日期  2 最近一天 3 最近三天  4 最近三个月  默认 1
     */
//    @EnumValue(groups = {ForumDTO.SearchPostList.class}, byteValues = {1, 2, 3, 4}, message = "更新日期必须为指定值")
    private Byte updateTime;

    /**
     * 帖子名称
     */
    @NotEmpty(groups = {AddTopic.class}, message = "帖子名称不能为空")
    @Size(groups = {AddTopic.class}, max = 30, min = 1, message = "帖子名称长度最长不能超过30个字符 最短不能小于1个字符")
    private String name;
    /**
     * 专题父id
     */
    @NotNull(groups = {Comment.class, DeleteMyComment.class}, message = "父id不能为空")
    private Long pid;


    /**
     * postId 帖子id
     */
    @NotNull(groups = {SearchCommentList.class, UpdateInit.class, DeleteMyPost.class, KeepNum.class, Comment.class, SearchLatestNum.class, SearchPostById.class, SearchPostDetailById.class}, message = "贴子id不能为空")
    private Long postId;

    /**
     * 专区id
     */
    @NotNull(groups = {AddTopic.class, Comment.class, SearchPostByBlogId.class}, message = "所属专题id不能为空")
    private Long blogId;

    /**
     * 帖子分类 1 技术问答 2 经验分享 3 大赛公告 4 大赛组队 5 全部 6 精华区  注:大赛组队只有选择大赛专区才能选择
     */
    @NotNull(groups = {AddTopic.class}, message = "帖子分类不能为空")
//    @EnumValue(groups = {SearchPostList.class, SearchPostByBlogId.class}, byteValues = {1, 2, 3, 4, 5, 6}, message = "帖子分类必须为指定值")
    private Byte classFy;

    /**
     * 专区名称
     */
    @NotEmpty(groups = {AddTopic.class}, message = "所属专题名不能为空")
    private String title;
    /**
     * 评论内容
     */
    @NotEmpty(groups = {Comment.class, AddTopic.class}, message = "评论内容不能为空")
    private String content;


    private String markText;
    /**
     * 用户id  发帖人id
     */
    @NotEmpty(groups = {AddTopic.class, SearchMyReply.class, KeepNum.class, searchMyLikePost.class, SearchMyCreatePost.class, UpdatePostById.class, DeleteMyComment.class, Comment.class, DeleteMyPost.class, SearchLatestNum.class}, message = "用户id不能为空")
    private String userId;

    /**
     * operationType 1 浏览 2 帖子点赞 3  收藏  4评论-点赞
     */
//    @EnumValue(groups = {ForumDTO.KeepNum.class}, byteValues = {1, 2, 3, 4, 5, 6}, message = "[operationType]字段必须为指定值")
    @NotNull(groups = {ForumDTO.KeepNum.class}, message = "[operationType]字段不能为空")
    private Byte operationType;

    /**
     * 评论id
     */
    @NotNull(groups = {DeleteMyComment.class}, message = "评论id不能为空")
    private Long commentId;
    /**
     * 0 增加操作  1 取消操作
     */
//    @EnumValue(groups = {ForumDTO.KeepNum.class}, byteValues = {0, 1})
    private Byte clickType;
    /**
     * 模糊查询字段
     */
    private String keyword;


    /**
     * ########################### 评论使用
     */

    /**
     * 头像地址
     */
    private String photoPath;


    /**
     * 用户名
     */
    private String userName;


    public interface AddTopic {
    }

    public interface SearchPostDetailById {
    }

    public interface SearchComment {
    }

    public interface SearchCommentList {
    }

    public interface KeepNum {
    }

    public interface SearchPostList {
    }

    public interface SearchBlogs {
    }

    public interface Comment {
    }

    public interface SearchLatestNum {
    }

    public interface SearchAddTopicData {
    }

    public interface DeleteMyPost {
    }

    public interface DeleteMyComment {
    }

    public interface SearchPostById {
    }

    public interface UpdatePostById {
    }

    public interface SearchMyReply {
    }

    public interface SearchMyCreatePost {
    }

    public interface SearchPostByBlogId {
    }


    public interface searchMyLikePost {
    }

    public interface UpdateInit {
    }
}
