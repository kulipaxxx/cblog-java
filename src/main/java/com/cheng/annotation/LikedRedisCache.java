package com.cheng.annotation;

import java.lang.annotation.*;

/**
 * @Description: LikedRedisCache
 * @Author cheng
 * @Date: 2023/2/27 16:17
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LikedRedisCache {
    String key() default "";

    long expire() default -1;
}
