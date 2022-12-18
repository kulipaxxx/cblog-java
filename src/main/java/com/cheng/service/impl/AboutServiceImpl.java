package com.cheng.service.impl;

import com.cheng.entity.About;
import com.cheng.mapper.AboutMapper;
import com.cheng.service.AboutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author: cheng
 * @since 2022-12-18
 */
@Service
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements AboutService {

}
