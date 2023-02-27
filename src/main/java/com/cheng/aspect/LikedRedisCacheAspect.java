package com.cheng.aspect;

import com.cheng.annotation.LikedRedisCache;
import com.cheng.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Description: LikedRedisCacheAspect
 * @Author cheng
 * @Date: 2023/2/27 16:29
 * @Version 1.0
 */
@Component
@Slf4j
@Aspect
public class LikedRedisCacheAspect {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisService redisService;
    @Pointcut("@annotation(com.cheng.annotation.LikedRedisCache)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获取到目标方法
        Method method = pjp.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
        //获取方法注解
        LikedRedisCache redisCache = method.getAnnotation(LikedRedisCache.class);
        String keyEl = redisCache.key();
        //创建解析器 解析EL表达式
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(keyEl);
        //设置解析上下文（这些占位符的值 来自）
        EvaluationContext context = new StandardEvaluationContext();
        //参数值 获取到参数实际的值
        Object[] args = pjp.getArgs();
        //我们还需要获取实际的参数名，而不是agrs0,agrs1这种形式，通过下面的方式可以获取
        // public DefaultParameterNameDiscoverer() {
        //        if (standardReflectionAvailable) {
        //            this.addDiscoverer(new StandardReflectionParameterNameDiscoverer());
        //        }
        //
        //        this.addDiscoverer(new LocalVariableTableParameterNameDiscoverer());
        //    }
        //他会调用LocalVariableTableParameterNameDiscoverer去实现
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for(int i = 0; i < parameterNames.length; i++) {
            //name-value : userId-10010
            context.setVariable(parameterNames[i],args[i]);
        }
        //解析出key的真实值
        String key = expression.getValue(context).toString();
        System.out.println(key);
        String object = redisService.getLikedCount(key).toString();
        if (object == null) {
            //获取方法执行结果
            Object data = pjp.proceed();
            System.out.println("从数据库获取结果");
            //缓存时间
            long expireTime = redisCache.expire();
            if (expireTime == -1) {
                redisTemplate.opsForValue().set(key, data);
            }else {
                redisTemplate.opsForValue().set(key, data, expireTime, TimeUnit.SECONDS);
            }
            return data;
        }else {
            System.out.println("从redis获取");
            //这里object套一层JSON.parse()是因为有时候存入redis的json字符串get出来后会多一个“\”转义符号导致直接parse失败
            return object;
        }
    }
}
