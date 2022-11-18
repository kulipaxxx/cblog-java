package com.cheng.service.impl;

import com.cheng.entity.Video;
import com.cheng.mapper.VideoMapper;
import com.cheng.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author: cheng
 * @since 2022-11-18
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
